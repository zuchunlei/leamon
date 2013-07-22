package com.bes.common.svn.kit.test;

import org.tmatesoft.svn.core.SVNDirEntry;

class Handler {
	
	long revision ;
	
	public Handler(long num){
		this.revision = num;
	}
	
	public void handle(SVNDirEntry entry){
		if(entry.getRevision()!=revision){
			return ;
		}
//		System.out.println(entry.getAuthor()+"-"+entry.getName()+"-"+entry.getRelativePath()+"-"+entry.getRevision());
//		System.out.println("=================================================================");
		DB.insert(entry);
	}
	
}
