package com.bes.common.svn.kit;

import java.io.IOException;
import java.util.Properties;

import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.internal.io.dav.DAVRepositoryFactory;
import org.tmatesoft.svn.core.internal.io.svn.SVNRepositoryFactoryImpl;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;
import org.tmatesoft.svn.core.wc.SVNWCUtil;

public class Context {

	private static final String REPO_ADDRESS = "svn.repository.address";
	private static final String AUTH_USERNAME = "svn.auth.username";
	private static final String AUTH_PASSWORD = "svn.auth.password";
	private static final String LOG_PNUM = "svn.log.pnum";
	private static final String ENTRY_PNUM = "svn.entry.pnum";
	private static final String JDBC_URL = "jdbc.url";
	private static final String JDBC_DRIVER = "jdbc.driver";
	private static final String JDBC_USERNAME = "jdbc.username";
	private static final String JDBC_PASSWORD = "jdbc.password";
	
	private static final int DEFAULT_LOG_PNUM = 2;
	
	private static final int DEFAULT_ENTRY_PNUM = 4;
	
	private static Properties prop = new Properties() ;
	
	static{
		try {
			prop.load(Context.class.getResourceAsStream("/context.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static SVNRepository getRepository(){
		String address = "svn://127.0.0.1";
		String name = "zuchunlei";
		String password = "123456";
		
		SVNURL url = null;
		SVNRepository repository = null ;
		try {
			url = SVNURL.parseURIEncoded(address);
			repository = SVNRepositoryFactory.create(url);
			ISVNAuthenticationManager auth = SVNWCUtil.createDefaultAuthenticationManager(name, password);
			repository.setAuthenticationManager(auth);
		} catch (SVNException e) {
			e.printStackTrace();
		}
		
		return repository; 
	}
	
	public static void initContext(){
		DAVRepositoryFactory.setup();
		SVNRepositoryFactoryImpl.setup();
	}
	
	public static int getProcessorNum(){
		int result = DEFAULT_LOG_PNUM;
		String num = prop.getProperty(LOG_PNUM);
		if(num!=null){
			result = Integer.valueOf(num);
		}
		return result;
	}
	
	public static long getLatestRevision(){
		return 800;
	}
	
}
