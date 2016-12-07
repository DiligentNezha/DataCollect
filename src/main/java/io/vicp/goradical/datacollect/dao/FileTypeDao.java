package io.vicp.goradical.datacollect.dao;

import io.vicp.goradical.datacollect.entity.FileInfo;
import io.vicp.goradical.datacollect.entity.Type;
import io.vicp.goradical.datacollect.tools.JDBCTools;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FileTypeDao {
	private FileInfoDao fileInfoDao;
	private TypeDao typeDao;

	public FileTypeDao(){
	}

	public FileTypeDao(FileInfoDao fileInfoDao, TypeDao typeDao) {
		this.fileInfoDao = fileInfoDao;
		this.typeDao = typeDao;
	}

	public List<Type> getFileInfoTypeListWithFileId(Connection conn, int fileId) {
		String selectFileInfoTypeListWithFileIdSql = "select * from t_file_type where file_id = " + fileId;
		List<Type> fileInfoList = getFileListWithSql(Type.class, conn, selectFileInfoTypeListWithFileIdSql);
		return fileInfoList;
	}

	public List<FileInfo> getFileInfoTypeListWithTypeId(Connection conn, int typeId) {
		String selectFileInfoTypeListWithTypeId = "select * from t_file_type where type_id = " + typeId;
		List<FileInfo> typeList = getFileListWithSql(FileInfo.class, conn, selectFileInfoTypeListWithTypeId);
		return typeList;
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
				int typeId = rs.getInt(3);
				T t;
				if (Type.class.equals(c)) {
					t = (T) typeDao.getTypeWithTypeId(typeId);
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

	public TypeDao getTypeDao() {
		return typeDao;
	}

	public void setTypeDao(TypeDao typeDao) {
		this.typeDao = typeDao;
	}
}
