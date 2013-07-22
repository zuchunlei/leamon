package com.bes.common.svnkit;

import org.tmatesoft.svn.core.SVNProperties;

public class CommitAnnotateHandler {
	
	public static final String DATE_PREFIX = "svn:date";
	public static final String LOG_PREFIX = "svn:log";
	public static final String AUTHOR_PREFIX = "svn:author";
	
	long revision ;
	
	public CommitAnnotateHandler(){
		
	}
	
	public CommitAnnotateHandler(long num){
		this.revision = num;
	}

	public void handle(SVNProperties properties){
		String date = properties.getStringValue(DATE_PREFIX);
		String log = properties.getStringValue(LOG_PREFIX);
		String author = properties.getStringValue(AUTHOR_PREFIX);
		
		DB.addAnnotate(new Annotate(log, author, date,revision));
		
	}

}
