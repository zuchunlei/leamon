package com.bes.common.svnkit;

import java.io.File;

import org.tmatesoft.svn.core.ISVNDirEntryHandler;
import org.tmatesoft.svn.core.SVNDirEntry;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.internal.io.dav.DAVRepositoryFactory;
import org.tmatesoft.svn.core.internal.io.svn.SVNRepositoryFactoryImpl;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;
import org.tmatesoft.svn.core.wc.ISVNPropertyHandler;
import org.tmatesoft.svn.core.wc.SVNClientManager;
import org.tmatesoft.svn.core.wc.SVNLogClient;
import org.tmatesoft.svn.core.wc.SVNPropertyData;
import org.tmatesoft.svn.core.wc.SVNRevision;
import org.tmatesoft.svn.core.wc.SVNWCClient;

public class SVNKitListDentry {
	
	static{
		DAVRepositoryFactory.setup();
		SVNRepositoryFactoryImpl.setup();
	}

	/**
	 * @param args
	 * @throws Exception 
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception  {
		
		// 0 - 10000
		
		
		/*
		new Thread(new DentryListProcessor(0, 2000)).start();
		new Thread(new DentryListProcessor(2001, 4000)).start();
		new Thread(new DentryListProcessor(4001, 6000)).start();
		new Thread(new DentryListProcessor(6001, 8000)).start();
		new Thread(new DentryListProcessor(8001, 10000)).start();
		*/
		
		String address = "svn://127.0.0.1";
		final SVNURL url = SVNURL.parseURIEncoded(address);
		final SVNRepository repository = SVNRepositoryFactory.create(url);
		
		SVNClientManager manager = SVNClientManager.newInstance();
		final long lastRevision = repository.getLatestRevision();
		
		SVNWCClient wccli = manager.getWCClient();
		
		
		for(long i=0;i<=lastRevision;i++){
			wccli.doGetRevisionProperty(url, null, SVNRevision.create(i), new ISVNPropertyHandler() {
				
				@Override
				public void handleProperty(long revision, SVNPropertyData property)
						throws SVNException {
					
				}
				
				@Override
				public void handleProperty(SVNURL url, SVNPropertyData property)
						throws SVNException {
					
				}
				
				@Override
				public void handleProperty(File path, SVNPropertyData property)
						throws SVNException {
					
				}
			});
		}
		
	}

}

class DentryListProcessor implements Runnable{
	
	String address = "http://svn.apache.org/repos/asf";
	
	long begin ;
	
	long end;
	
	SVNLogClient logCli;
	
	SVNURL url ;
	
	protected DentryListProcessor(long begin,long end){
		this.begin = begin;
		this.end = end;
		try {
			url = SVNURL.parseURIEncoded(address);
		} catch (SVNException e) {
			e.printStackTrace();
		}
		SVNClientManager manager = SVNClientManager.newInstance();
		logCli = manager.getLogClient();
	}
	
	@Override
	public void run() {
		for(long i=begin;i<=end;i++){
			ISVNDirEntryHandler handler = new DentryListHandler(i);
			try {
				logCli.doList(url, SVNRevision.create(i), SVNRevision.create(i), false, true, handler);
			} catch (SVNException e) {
				handler = null;
				continue;
			} 
			handler = null;
			System.out.println("----------------  "+i+"  ----------------------");
		}
	}
	
	
}


class DentryListHandler implements ISVNDirEntryHandler{

	long revision ;
	
	public DentryListHandler(long number){
		this.revision = number;
	}
	
	@Override
	public void handleDirEntry(SVNDirEntry dirEntry) throws SVNException {
//		if(dirEntry.getRevision()!=revision){
//			return ;
//		}
//		System.out.println("author:"+dirEntry.getAuthor());
//		System.out.println("message:"+dirEntry.getCommitMessage());
//		System.out.println("name:"+dirEntry.getName());
//		System.out.println("relativepath:"+dirEntry.getRelativePath());
//		System.out.println("revision:"+dirEntry.getRevision());
//		System.out.println("size:"+dirEntry.getSize());
//		System.out.println("date:"+dirEntry.getDate());
//		System.out.println("kind:"+dirEntry.getKind());
//		System.out.println("root:"+dirEntry.getRepositoryRoot());
//		System.out.println("url:"+dirEntry.getURL());
//		System.out.println("++++++++++++++++++++++++++");
		
		DB.insert(dirEntry);
		
	}
	
}








