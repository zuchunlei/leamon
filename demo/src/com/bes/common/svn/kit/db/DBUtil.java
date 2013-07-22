package com.bes.common.svn.kit.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.tmatesoft.svn.core.SVNDirEntry;

public class DBUtil {
static SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	static Connection conn;
	
	static{
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url="jdbc:mysql://localhost:3306/test";
			conn = DriverManager.getConnection(url);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void insert(SVNDirEntry dentry){
		StringBuffer sql = new StringBuffer();
		sql.append("insert into svn_dentry(author,name,path,revision,size,date,kind,root,url) ");
		sql.append("values(?,?,?,?,?,?,?,?,?)");
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, dentry.getAuthor());
			pstmt.setString(2, dentry.getName());
			pstmt.setString(3, dentry.getRelativePath());
			pstmt.setLong(4, dentry.getRevision());
			pstmt.setLong(5, dentry.getSize());
			pstmt.setString(6, formater.format(dentry.getDate()));
			pstmt.setString(7, dentry.getKind().toString());
			pstmt.setString(8, dentry.getRepositoryRoot().toString());
			pstmt.setString(9, dentry.getURL().toString());
			
			pstmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void addSVNProperties(LogMessage properties){
		StringBuffer sql = new StringBuffer();
		sql.append("insert into  svn_annotate(author,log,date,revision) ");
		sql.append("values(?,?,?,?)");
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, properties.getAuthor());
			pstmt.setString(2, properties.getLog());
			pstmt.setString(3, properties.getDate());
			pstmt.setLong(4, properties.getRevision());
			
			pstmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	public static void addFileDiff(FileDiff diff){
		StringBuffer sql = new StringBuffer();
		sql.append("insert into  svn_diff(path,op,type,copied_path,copied_revision,line,revision,log) ");
		sql.append("values(?,?,?,?,?,?,?,?)");
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, diff.getPath());
			pstmt.setString(2, diff.getOp());
			pstmt.setString(3, diff.getType());
			pstmt.setString(4, diff.getCopiedPath());
			pstmt.setLong(5, diff.getCopiedRevision());
			pstmt.setInt(6, diff.getLine());
			pstmt.setLong(7, diff.getRevision());
			pstmt.setString(8, diff.getLog());
			
			pstmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void addFileDiffs(List list){
		for(int i=0,size=list.size();i<size;i++){
			addFileDiff((FileDiff) list.get(i));
		}
	}
	
}
