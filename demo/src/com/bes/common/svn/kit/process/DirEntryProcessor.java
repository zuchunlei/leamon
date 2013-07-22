package com.bes.common.svn.kit.process;

import java.util.Collection;
import java.util.Iterator;

import org.tmatesoft.svn.core.SVNDirEntry;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNNodeKind;
import org.tmatesoft.svn.core.io.SVNRepository;

import com.bes.common.svn.kit.Context;
import com.bes.common.svn.kit.db.DBUtil;

public class DirEntryProcessor {

	public static final String BASICPATH = "";

	public static String WORKERNAME = "dir entry scan ";

	/**
	 * 处理线程数
	 */
	int num;

	/**
	 * 当前库最大版本号
	 */
	long revision;

	long[] ranges;

	public DirEntryProcessor(int num, long revision) {
		this.num = num;
		this.revision = revision;
		init();
	}

	public void process() {
		for (int i = 0; i < num; i++) {
			String name = WORKERNAME + "[" + (ranges[i] + 1) + "-"
					+ ranges[i + 1] + "]";
			new Thread(new DirEntryWorker(ranges[i] + 1, ranges[i + 1]), name)
					.start();
		}
	}

	protected void init() {
		if (revision < num) {
			num = (int) revision;
		}
		ranges = new long[num + 1];
		long unit = revision / num;
		for (int i = 0; i <= num; i++) {
			ranges[i] = unit * i;
		}
		ranges[num] = revision;
	}

	public void listDirEntry(SVNRepository repository, String path,
			long revision, DirEntryHandler handler) {
		try {
			Collection entrys = repository.getDir(path, revision, null,
					(Collection) null);
			Iterator iter = entrys.iterator();

			while (iter.hasNext()) {
				SVNDirEntry dentry = (SVNDirEntry) iter.next();
				if (dentry.getRevision() == revision) {
					handler.handle(dentry);
					if (dentry.getKind() == SVNNodeKind.DIR) {
						String tmp = path.equals(BASICPATH) ? dentry.getName()
								: path + "/" + dentry.getName();
						listDirEntry(repository, tmp, revision, handler);
					}
				}
			}
		} catch (SVNException e) {
			e.printStackTrace();
		}

	}

	class DirEntryWorker implements Runnable {
		private long begin;
		private long end;

		public DirEntryWorker(long begin, long end) {
			this.begin = begin;
			this.end = end;
		}

		@Override
		public void run() {
			SVNRepository repository = Context.getRepository();
			DirEntryHandler handler = new DirEntryHandler();
			for (long i = begin; i <= end; i++) {
				listDirEntry(repository, BASICPATH, i, handler);
			}
		}
	}

	class DirEntryHandler {
		public void handle(SVNDirEntry dentry) {
			DBUtil.insert(dentry);
		}
	}
}
