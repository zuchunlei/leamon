package com.bes.common.svnkit;

import java.util.Collection;
import java.util.Iterator;

import org.tmatesoft.svn.core.SVNDirEntry;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNNodeKind;
import org.tmatesoft.svn.core.SVNProperties;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.internal.io.dav.DAVRepositoryFactory;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;
import org.tmatesoft.svn.core.wc.SVNWCUtil;

public class DisplayRepositoryTree {
	
	public static SVNRepository getRepository() throws SVNException{
		String address = "http://svn.apache.org/repos/asf";
		String name = "zuchunlei";
		String password = "123456";
		
		SVNURL url = SVNURL.parseURIEncoded(address);
		SVNRepository repository = SVNRepositoryFactory.create(url);
		
		ISVNAuthenticationManager auth = SVNWCUtil.createDefaultAuthenticationManager(name, password);
		repository.setAuthenticationManager(auth);
		
		return repository;
	}
	

	/**
	 * @param args
	 */
	public static void main(String[] args) throws SVNException{
		DAVRepositoryFactory.setup();
		
		SVNRepository repository = getRepository();
		
		try{
			System.out.println("repository root is :"+repository.getRepositoryRoot(true));
			System.out.println("repository uuid is :"+repository.getRepositoryUUID(true));
			
			final long lastRevision = repository.getLatestRevision();
			
			//获取提交时的注释信息(一个线程去读)
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						DisplayRepositoryTree.getCommitAnnotate(lastRevision);
					} catch (SVNException e) {
						e.printStackTrace();
					}
				}
			},"log message scaning").start();
			
			//获取版本之间的文件/目录信息(多线程去读)
			new Thread(new Task(1,20)).start();
			new Thread(new Task(21,40)).start();
			new Thread(new Task(41,60)).start();
			new Thread(new Task(61,80)).start();
			new Thread(new Task(81,100)).start();
			new Thread(new Task(101,120)).start();
			
		}catch(SVNException e){
			e.printStackTrace();
		}
		
	}

	
	public static void getCommitAnnotate(long lastRevision) throws SVNException {
		SVNRepository repository = getRepository();
		for(long i=0;i<=lastRevision;i++){
			CommitAnnotateHandler handler = new CommitAnnotateHandler(i);
			SVNProperties properties = null;
			try {
				properties = repository.getRevisionProperties(i,null);
				handler.handle(properties);
			} catch (SVNException e) {
				e.printStackTrace();
			}
//			System.out.println(properties.toString());
		}
	}


	public static void listEntry(String path,long revision,Handler handler)throws SVNException{
		SVNRepository repository = getRepository();
		Collection entrys = repository.getDir(path, revision, null, (Collection)null);
		Iterator iter = entrys.iterator();
		
		while(iter.hasNext()){
			SVNDirEntry dentry = (SVNDirEntry) iter.next();
			if(dentry.getRevision()==revision){
				handler.handle(dentry);
				if(dentry.getKind()==SVNNodeKind.DIR){
					String tmp = path.equals("")?dentry.getName():path+"/"+dentry.getName();
					listEntry(tmp,revision,handler);
				}
			}
		}
	}
	
}
class Task implements Runnable{
	
	private long begin;
	
	private long end;
	
	
	public Task(long begin,long end){
		this.begin = begin;
		this.end = end;
	}

	@Override
	public void run() {
		for(long i=begin;i<=end;i++){
			try {
				DisplayRepositoryTree.listEntry( "",i,new Handler(i));
			} catch (SVNException e) {
				e.printStackTrace();
				continue;
			}
			System.out.println("----------loop"+i+"----------");
		}
	}
	
	
}
