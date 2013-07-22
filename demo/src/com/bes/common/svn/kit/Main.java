package com.bes.common.svn.kit;

import com.bes.common.svn.kit.process.DirEntryProcessor;
import com.bes.common.svn.kit.process.LogMessageProcessor;


public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Context.initContext();
		int num = Context.getProcessorNum();
		long revision = Context.getLatestRevision();
		DirEntryProcessor dentryProcessor = new DirEntryProcessor(num, revision);
		LogMessageProcessor logMsgProcessor = new LogMessageProcessor(num, revision);
		
		dentryProcessor.process();
		logMsgProcessor.process();
	}

}
