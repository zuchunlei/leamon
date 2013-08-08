package com.bes.common.svn.kit.test;

import java.util.Collection;

import org.tmatesoft.svn.core.ISVNDirEntryHandler;
import org.tmatesoft.svn.core.SVNDirEntry;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.BasicAuthenticationManager;
import org.tmatesoft.svn.core.internal.io.svn.SVNRepositoryFactoryImpl;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;
import org.tmatesoft.svn.core.wc.SVNClientManager;
import org.tmatesoft.svn.core.wc.SVNLogClient;
import org.tmatesoft.svn.core.wc.SVNRevision;

public class SVNKitLocalServer {
	
	static{
		SVNRepositoryFactoryImpl.setup();
	}

	/**
	 * @param args
	 * @throws SVNException 
	 */
	public static void main(String[] args) throws SVNException {
		String address = "svn://localhost";
		SVNURL url = SVNURL.parseURIEncoded(address);
		
		SVNRepository repository = SVNRepositoryFactory.create(url);
		repository.setAuthenticationManager(new BasicAuthenticationManager("zuchunlei", "123456"));
		
		long lastRevision = repository.getLatestRevision();
		System.out.println(lastRevision);
		
		SVNClientManager manager = SVNClientManager.newInstance();
		SVNLogClient logCli = manager.getLogClient();
		
		logCli.doList(url, SVNRevision.HEAD, SVNRevision.HEAD, false, false, new ISVNDirEntryHandler() {
			@Override
			public void handleDirEntry(SVNDirEntry dirEntry) throws SVNException {
				System.out.println(dirEntry.getName());
			}
		});
		
		Collection dentrys = repository.getDir("bin", lastRevision, null, (Collection)null);
		System.out.println(dentrys);
	}
}
