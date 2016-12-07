package io.vicp.goradical.datacollect.dao;

import io.vicp.goradical.datacollect.entity.Director;
import io.vicp.goradical.datacollect.entity.FileInfo;
import io.vicp.goradical.datacollect.tools.JDBCTools;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FileDirectorDao {
	private FileInfoDao fileInfoDao;
	private DirectorDao directorDao;

	public FileDirectorDao(){
	}

	public FileDirectorDao(FileInfoDao fileInfoDao, DirectorDao directorDao) {
		this.fileInfoDao = fileInfoDao;
		this.directorDao = directorDao;
	}

	public List<Director> getFileInfoDirectorListWithFileId(Connection conn, int fileId) {
		String selectFileInfoDirectorListWithFileIdSql = "select * from t_file_Director where file_id = " + fileId;
		return getFileListWithSql(conn, Director.class, selectFileInfoDirectorListWithFileIdSql);
	}

	public List<FileInfo> getFileInfoDirectorListWithDirectorId(Connection conn, int DirectorId) {
		String selectFileInfoDirectorListWithDirectorId = "select * from t_file_Director where Director_id = " + DirectorId;
		return getFileListWithSql(conn, FileInfo.class, selectFileInfoDirectorListWithDirectorId);
	}

	private <T> List<T> getFileListWithSql(Connection conn, Class<T> c, String sql) {
		List<T> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = pstmt.executeQuery();
			rs.last();
			list = new ArrayList<>(rs.getRow());
			rs.beforeFirst();
			while (rs.next()) {
				int fileId =  rs.getInt(2);
				int DirectorId = rs.getInt(3);
				T t;
				if (Director.class.equals(c)) {
					t = (T) directorDao.getDirectorWithDirectorId(DirectorId);
				} else {
					t = (T) fileInfoDao.getFileInfoWithFileInfoId(fileId);
				}
				list.add(t);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTools.closeResultSetAndStatement(rs, pstmt);
		}
		return list;
	}

	public FileInfoDao getFileInfoDao() {
		return fileInfoDao;
	}

	public void setFileInfoDao(FileInfoDao fileInfoDao) {
		this.fileInfoDao = fileInfoDao;
	}

	public DirectorDao getDirectorDao() {
		return directorDao;
	}

	public void setDirectorDao(DirectorDao directorDao) {
		this.directorDao = directorDao;
	}
}
