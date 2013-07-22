package com.bes.common.svn.kit.diff;

import java.io.OutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import org.tmatesoft.svn.core.ISVNLogEntryHandler;
import org.tmatesoft.svn.core.SVNCommitInfo;
import org.tmatesoft.svn.core.SVNDepth;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNLogEntry;
import org.tmatesoft.svn.core.SVNLogEntryPath;
import org.tmatesoft.svn.core.SVNPropertyValue;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.io.ISVNEditor;
import org.tmatesoft.svn.core.io.ISVNReporter;
import org.tmatesoft.svn.core.io.ISVNReporterBaton;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.diff.SVNDeltaProcessor;
import org.tmatesoft.svn.core.io.diff.SVNDiffWindow;

import com.bes.common.svn.kit.Context;
import com.bes.common.svn.kit.db.DBUtil;
import com.bes.common.svn.kit.db.FileDiff;

public class SVNDiff {
	static String address = "svn://127.0.0.1/";

	static SVNURL url;

	static {
		try {
			url = SVNURL.parseURIEncoded(address);
		} catch (SVNException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws SVNException {
		SVNRepository repository = Context.getRepository();
		long revision = repository.getLatestRevision();
		final SVNLogEntry[] entrys = new SVNLogEntry[1];
		for (long i = 1; i <= revision; i++) {
			repository.log(new String[] { "" }, i, i, true, false, 1,
					new ISVNLogEntryHandler() {
						public void handleLogEntry(SVNLogEntry logEntry)
								throws SVNException {
							entrys[0] = logEntry;
						}
					});
			repository.diff(url, i, i-1, null, false, SVNDepth.INFINITY, true,
					new ExportReporterBaton(i-1), new SVNEditor(repository, entrys[0]));
		}

	}

}

class ExportReporterBaton implements ISVNReporterBaton {
	
	private long revision;
	
	public ExportReporterBaton(long num){
		this.revision = num;
	}

	@Override
	public void report(ISVNReporter reporter) {
		try {
			reporter.setPath("", null, revision, SVNDepth.INFINITY, false);
			reporter.finishReport();
		} catch (SVNException svne) {
			try {
				reporter.abortReport();
			} catch (SVNException e) {
				e.printStackTrace();
			}
		}
	}

}

class SVNEditor implements ISVNEditor {

	private SVNRepository repo;
	
	private long previousRevision ;
	
	private long targetRevision;
	
	private Stack dirs;
	
	private Map changedPaths;
	
	private Map copiedPaths;
	
	private Map addPaths;
	
	private Map deletPaths;
	
	private List<FileDiff> list;
	
	SVNDeltaProcessor deltaProcessor = new SVNDeltaProcessor();
	
	public SVNEditor(SVNRepository repository, SVNLogEntry entry) {
		this.repo = repository;
		dirs = new Stack();
		changedPaths = entry.getChangedPaths();
		copiedPaths = new HashMap();
		addPaths = new HashMap();
		deletPaths = new HashMap();
		FileDiff obj = new FileDiff();
		
		for (Iterator paths = changedPaths.keySet().iterator(); paths.hasNext();) {
			String path = (String) paths.next();
			SVNLogEntryPath entryPath = (SVNLogEntryPath) changedPaths
					.get(path);
			
			char type = entryPath.getType();

			if ((type == SVNLogEntryPath.TYPE_REPLACED || type == SVNLogEntryPath.TYPE_ADDED)
					&& entryPath.getCopyPath() != null
					&& entryPath.getCopyRevision() >= 0) {
				copiedPaths.put(path, entryPath);
			}
			if (type == SVNLogEntryPath.TYPE_ADDED
					&& entryPath.getCopyPath() == null
					&& entryPath.getCopyRevision() == -1) {
				addPaths.put(path, entryPath);
			}
			if (type == SVNLogEntryPath.TYPE_DELETED) {
				deletPaths.put(path, entryPath);
			}
		}

	}
	
	@Override
	public void targetRevision(long revision) throws SVNException {
		this.previousRevision = revision-1;
		this.targetRevision = revision;
		System.out.println("targetRevision\t" + revision);
	}

	@Override
	public void openRoot(long revision) throws SVNException {
		System.out.println("openRoot" + revision);
	}
	
	
	@Override
	public void applyTextDelta(String path, String baseChecksum)
			throws SVNException {
		System.out.println("applyTextDelta:" + path + "\t" + baseChecksum);
	}

	@Override
	public OutputStream textDeltaChunk(String path, SVNDiffWindow window)
			throws SVNException {
		System.out.println("textDeltaChunk:" + path);

		return null;
	}

	@Override
	public void textDeltaEnd(String path) throws SVNException {
		System.out.println("textDeltaEnd:" + path);
	}

	
	@Override
	public void deleteEntry(String path, long revision) throws SVNException {
		path = "/"+path;
		FileDiff obj = new FileDiff();
		if(deletPaths.containsKey(path)){
			obj.setPath(path);
			SVNLogEntryPath entry = (SVNLogEntryPath) deletPaths.get(path);
			obj.setRevision(targetRevision);
			obj.setOp(OP.DELETE.getStatus());
			obj.setType(entry.getKind().toString());
			list.add(obj);
		}
		
		System.out.println("deleteEntry" + path + "\trevision" + revision);
	}

	@Override
	public void absentDir(String path) throws SVNException {
		System.out.println("absentDir" + path);
	}

	@Override
	public void absentFile(String path) throws SVNException {
		System.out.println("absentFile" + path);
	}

	@Override
	public void addDir(String path, String copyFromPath, long copyFromRevision)
			throws SVNException {
		path = "/"+path;		
		FileDiff obj = new FileDiff();
		list.add(obj);
		if(copiedPaths.containsKey(path)){
			obj.setPath(path);
			obj.setRevision(targetRevision);
			SVNLogEntryPath entry = (SVNLogEntryPath) copiedPaths.get(path);
			obj.setCopiedPath(entry.getCopyPath());
			obj.setCopiedRevision(entry.getCopyRevision());
			obj.setOp(OP.COPY.getStatus());
			obj.setType(entry.getKind().toString());
		}else if(addPaths.containsKey(path)){
			obj.setPath(path);
			obj.setRevision(targetRevision);
			obj.setOp(OP.ADD.getStatus());
			SVNLogEntryPath entry = (SVNLogEntryPath) addPaths.get(path);
			obj.setType(entry.getKind().toString());
		}
	}

	@Override
	public void openDir(String path, long revision) throws SVNException {
		System.out.println("openDir" + path + "\t" + revision);
	}

	@Override
	public void changeDirProperty(String name, SVNPropertyValue value)
			throws SVNException {
		System.out.println("changeDirProperty" + name + "\t" + value);
	}

	@Override
	public void closeDir() throws SVNException {
		System.out.println("closeDir");
	}

	@Override
	public void addFile(String path, String copyFromPath, long copyFromRevision)
			throws SVNException {
		System.out.println("addFile" + path + "\t" + copyFromPath + "\t"
				+ copyFromRevision);
	}

	@Override
	public void openFile(String path, long revision) throws SVNException {
		System.out.println("openFile" + path + "\t" + revision);
	}

	@Override
	public void changeFileProperty(String path, String propertyName,
			SVNPropertyValue propertyValue) throws SVNException {
		System.out.println("changeFileProperty" + path + "\t" + propertyName
				+ "\t" + propertyValue);

	}

	@Override
	public void closeFile(String path, String textChecksum) throws SVNException {
		System.out.println("closeFile" + path + "\t" + textChecksum);
	}

	@Override
	public SVNCommitInfo closeEdit() throws SVNException {
		//统一处理diff对象  handle(obj)
		DBUtil.addFileDiffs(list);
		return null;
	}

	@Override
	public void abortEdit() throws SVNException {
		System.out.println("abortEdit");
	}

}

enum OP {

	ADD("add"), DELETE("delete"), MODIFY("modify"), COPY("copy");

	private String status;

	private OP(String op) {
		this.status = op;
	}

	public String getStatus() {
		return this.status;
	}
}

