package com.bes.common.svn.kit;

import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNProperties;
import org.tmatesoft.svn.core.io.SVNRepository;

public class LogMessageProcessor {

	public static String WORKERNAME = "log message scan ";
	
	private int num;
	
	private long revision;
	
	private long[] ranges;
	
	public LogMessageProcessor(int num,long revision){
		this.num = num;
		this.revision = revision;
		init();
	}
	
	protected void init(){
		ranges = new long[num+1];
		long unit = revision/num;
		for(int i=0;i<=num;i++){
			ranges[i]=unit*i;
		}
		ranges[num] = revision;
	}
	
	public void process(){
		for(int i=0;i<num;i++){
			String name = WORKERNAME+"["+(ranges[i]+1)+"-"+ranges[i+1]+"]";
			new Thread(new LogMessageWorker(ranges[i]+1, ranges[i+1]),name).start();
		}
	}
	
	public void getLogMsg(SVNRepository repository,long revision,LogMessageHandler handler){
		try {
			SVNProperties properties = repository.getRevisionProperties(revision, null);
			handler.handle(properties);
		} catch (SVNException e) {
			e.printStackTrace();
		}
	}

	class LogMessageWorker implements Runnable{

		long begin;
		
		long end;
		
		LogMessageWorker(long begin,long end){
			this.begin = begin;
			this.end = end;
		}
		
		@Override
		public void run() {
			SVNRepository repository = Context.getRepository();
			for(long i=begin;i<=end;i++){
				LogMessageHandler handler = new LogMessageHandler(i);
				getLogMsg(repository,i,handler);
			}
		}
	}
	
	class LogMessageHandler{
		public static final String DATE_PREFIX = "svn:date";
		public static final String LOG_PREFIX = "svn:log";
		public static final String AUTHOR_PREFIX = "svn:author";
		
		private long revision;
		
		public LogMessageHandler(long num){
			this.revision = num;
		}
		
		public void handle(SVNProperties properties){
			String date = properties.getStringValue(DATE_PREFIX);
			String log = properties.getStringValue(LOG_PREFIX);
			String author = properties.getStringValue(AUTHOR_PREFIX);
			
			LogMessage msg = new LogMessage(log, author, date, revision);
			DBUtil.addSVNProperties(msg);
		}
	}
	
	
}
