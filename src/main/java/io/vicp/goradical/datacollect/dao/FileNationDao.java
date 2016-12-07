package io.vicp.goradical.datacollect.dao;

import io.vicp.goradical.datacollect.entity.FileInfo;
import io.vicp.goradical.datacollect.entity.Nation;
import io.vicp.goradical.datacollect.tools.JDBCTools;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FileNationDao {
	private FileInfoDao fileInfoDao;
	private NationDao nationDao;

	public FileNationDao(){
	}

	public FileNationDao(FileInfoDao fileInfoDao, NationDao nationDao) {
		this.fileInfoDao = fileInfoDao;
		this.nationDao = nationDao;
	}

	public List<Nation> getFileInfoNationListWithFileId(Connection conn, int fileId) {
		String selectFileInfoNationListWithFileIdSql = "select * from t_file_nation where file_id = " + fileId;
		List<Nation> fileInfoList = getFileListWithSql(Nation.class, conn, selectFileInfoNationListWithFileIdSql);
		return fileInfoList;
	}

	public List<FileInfo> getFileInfoNationListWithNationId(Connection conn, int nationId) {
		String selectFileInfoNationListWithNationId = "select * from t_file_nation where nation_id = " + nationId;
		List<FileInfo> nationList = getFileListWithSql(FileInfo.class, conn, selectFileInfoNationListWithNationId);
		return nationList;
	}

	private <T> List<T> getFileListWithSql(Class<T> c, Connection conn, String sql) {
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
				int nationId = rs.getInt(3);
				T t;
				if (Nation.class.equals(c)) {
					t = (T) nationDao.getNationWithNationId(nationId);
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

	public NationDao getNationDao() {
		return nationDao;
	}

	public void setNationDao(NationDao nationDao) {
		this.nationDao = nationDao;
	}
}
