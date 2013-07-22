package com.bes.common.svnkit;

import org.tmatesoft.svn.core.ISVNDirEntryHandler;
import org.tmatesoft.svn.core.SVNDirEntry;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.BasicAuthenticationManager;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.internal.io.svn.SVNRepositoryFactoryImpl;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;
import org.tmatesoft.svn.core.wc.ISVNOptions;
import org.tmatesoft.svn.core.wc.SVNClientManager;
import org.tmatesoft.svn.core.wc.SVNLogClient;
import org.tmatesoft.svn.core.wc.SVNRevision;
import org.tmatesoft.svn.core.wc.SVNWCUtil;


public class SVNKitClinet {

	static{
		SVNRepositoryFactoryImpl.setup();
	}
	
	
	public static void main(String[] args) throws Exception  {
		String address = "svn://127.0.0.1";
		final SVNURL url = SVNURL.parseURIEncoded(address);
		final SVNRepository repository = SVNRepositoryFactory.create(url);
		ISVNAuthenticationManager auth = new BasicAuthenticationManager("zuchunlei", "123456");
		repository.setAuthenticationManager(auth);
		
		ISVNOptions options = SVNWCUtil.createDefaultOptions(true);
		
		SVNClientManager manager = SVNClientManager.newInstance(options,auth);
		final long lastRevision = repository.getLatestRevision();
		
		final SVNLogClient logCli = manager.getLogClient();
		
		for(long i=0;i<=lastRevision;i++){
			ISVNDirEntryHandler handler ;
			try{
				handler = new DentryHandler(i);
				logCli.doList(url, SVNRevision.create(i), SVNRevision.create(i), false, true, handler);
			}catch(SVNException e){
				e.printStackTrace();
				continue ;
			}finally{
				handler = null;
			}
		}
		
		/*for(long i=0;i<30;i++){
			
			try {
				logCli.doAnnotate(url, SVNRevision.create(i), SVNRevision.create(i), SVNRevision.create(i), new ISVNAnnotateHandler(){
					@Override
					public void handleLine(Date date, long revision, String author,
							String line) throws SVNException {
						
					}
					@Override
					public void handleLine(Date date, long revision, String author,
							String line, Date mergedDate, long mergedRevision,
							String mergedAuthor, String mergedPath, int lineNumber)
							throws SVNException {
						
					}
					@Override
					public boolean handleRevision(Date date, long revision,
							String author, File contents) throws SVNException {
						return false;
					}
					@Override
					public void handleEOF() throws SVNException {
						
					}
				});
			} catch (SVNException e) {
				e.printStackTrace();
				continue;
			}
		}
		
		Collection collection = repository.getDir("//svn.apache.org/repos/asf", lastRevision, null, (Collection)null);
		
		System.out.println(collection.size());
		
		
		logCli.doList(url, SVNRevision.HEAD, SVNRevision.create(lastRevision), false, false, new ISVNDirEntryHandler() {
			@Override
			public void handleDirEntry(SVNDirEntry dirEntry) throws SVNException {
				
				
				
				System.out.println("author:"+dirEntry.getAuthor());
				System.out.println("message:"+dirEntry.getCommitMessage());
				System.out.println("name:"+dirEntry.getName());
				System.out.println("path:"+dirEntry.getPath());
				System.out.println("relativepath:"+dirEntry.getRelativePath());
				System.out.println("revision:"+dirEntry.getRevision());
				System.out.println("size:"+dirEntry.getSize());
				System.out.println("date:"+dirEntry.getDate());
				System.out.println("kind:"+dirEntry.getKind());
				System.out.println("root:"+dirEntry.getRepositoryRoot());
				System.out.println("url:"+dirEntry.getURL());
				
				System.out.println("++++++++++++++++++++++++++++++++++++++");
			}
		});
		
		
		
		
		
		System.out.println("-----------------------------------");
		
		
		
		
		logCli.doList(url, SVNRevision.HEAD, SVNRevision.create(lastRevision), false, true, new ISVNDirEntryHandler() {
			@Override
			public void handleDirEntry(SVNDirEntry dirEntry) throws SVNException {
				if(!dirEntry.getKind().equals(SVNNodeKind.FILE)){
					return ;
				}
				
				System.out.println("author:"+dirEntry.getAuthor());
				System.out.println("message:"+dirEntry.getCommitMessage());
				System.out.println("name:"+dirEntry.getName());
				System.out.println("path:"+dirEntry.getPath());
				System.out.println("relativepath:"+dirEntry.getRelativePath());
				System.out.println("revision:"+dirEntry.getRevision());
				System.out.println("size:"+dirEntry.getSize());
				System.out.println("date:"+dirEntry.getDate());
				System.out.println("kind:"+dirEntry.getKind());
				System.out.println("root:"+dirEntry.getRepositoryRoot());
				System.out.println("url:"+dirEntry.getURL());
				
				Collection<SVNFileRevision> list = repository.getFileRevisions("/"+dirEntry.getRelativePath(), null, 0, dirEntry.getRevision());
				for(SVNFileRevision vision :list){
					System.out.println(vision.getRevision());
					logCli.doAnnotate(url, SVNRevision.HEAD, SVNRevision.create(vision.getRevision()), SVNRevision.create(vision.getRevision()), new ISVNAnnotateHandler() {
						@Override
						public boolean handleRevision(Date date, long revision, String author,
								File contents) throws SVNException {
							return false;
						}
						@Override
						public void handleLine(Date date, long revision, String author,
								String line, Date mergedDate, long mergedRevision,
								String mergedAuthor, String mergedPath, int lineNumber)
								throws SVNException {
						}
						
						@Override
						public void handleLine(Date date, long revision, String author, String line)
								throws SVNException {
						}
						@Override
						public void handleEOF() throws SVNException {
							
						}
					});
					
					
				}
				System.out.println("======================================================");
			}
		});
		
				 */

		
	/*	logCli.doLog(svnurl, new String[]{"/mxl"}, SVNRevision.HEAD, SVNRevision.create(0), SVNRevision.create(lastRevision),false,false,0L,new ISVNLogEntryHandler(){

			@Override
			public void handleLogEntry(SVNLogEntry logEntry)
					throws SVNException {
				System.out.println(logEntry.getRevision());
			}
			
		} );*/
		
		
		
		
		/*Collection<SVNFileRevision> list = repository.getFileRevisions("/package.html", null, 0, lastRevision);
		for(SVNFileRevision revision:list){
			System.out.println(revision.getRevision());
		}*/
		
		
		/*repository.getFileRevisions("/package.html", 0, lastRevision, new ISVNFileRevisionHandler() {
			
			@Override
			public void textDeltaEnd(String path) throws SVNException {
				System.out.println(path);
			}
			
			@Override
			public OutputStream textDeltaChunk(String path, SVNDiffWindow diffWindow)
					throws SVNException {
				System.out.println(path);
				return null;
			}
			
			@Override
			public void applyTextDelta(String path, String baseChecksum)
					throws SVNException {
				
			}
			
			@Override
			public void openRevision(SVNFileRevision fileRevision) throws SVNException {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void closeRevision(String token) throws SVNException {
				// TODO Auto-generated method stub
				
			}
		});
		
		
		SVNURL fileUrl = SVNURL.parseURIEncoded("http://svn.apache.org/repos/asf/logging/log4j/trunk/src/main/java/org/apache/log4j/or/package.html");
		
		SVNDiffClient diffcli = manager.getDiffClient();
//		diffcli.doDiff(fileUrl, SVNRevision.create(568441), fileUrl, SVNRevision.create(537960), true, true, new FileOutputStream("C:\\2.txt"));
		
		diffcli.doDiff(fileUrl, SVNRevision.HEAD, SVNRevision.create(565523), SVNRevision.create(537960), SVNDepth.INFINITY, false, new FileOutputStream("C:\\4.txt"));*/
		
	}
}

class DentryHandler implements ISVNDirEntryHandler{

	long revision ;
	
	protected DentryHandler(long num){
		this.revision = num;
	}
	
	@Override
	public void handleDirEntry(SVNDirEntry dirEntry) throws SVNException {
		if(dirEntry.getRevision()!=revision){
			return ;
		}
		System.out.println(dirEntry.getRelativePath());
		System.out.println(dirEntry.getName());
		System.out.println("------------------");
	}
	
}
