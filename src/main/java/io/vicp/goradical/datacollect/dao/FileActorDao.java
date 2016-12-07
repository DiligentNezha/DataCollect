package io.vicp.goradical.datacollect.dao;

import io.vicp.goradical.datacollect.entity.Actor;
import io.vicp.goradical.datacollect.entity.FileInfo;
import io.vicp.goradical.datacollect.tools.JDBCTools;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FileActorDao {
	private FileInfoDao fileInfoDao;
	private ActorDao actorDao;

	public FileActorDao(){
	}

	public FileActorDao(FileInfoDao fileInfoDao, ActorDao actorDao) {
		this.fileInfoDao = fileInfoDao;
		this.actorDao = actorDao;
	}

	public List<Actor> getFileInfoActorListWithFileId(Connection conn, int fileId) {
		String selectFileInfoActorListWithFileIdSql = "select * from t_file_actor where file_id = " + fileId;
		List<Actor> fileInfoList = getFileListWithSql(conn, Actor.class, selectFileInfoActorListWithFileIdSql);
		return fileInfoList;
	}

	public List<FileInfo> getFileInfoActorListWithActorId(Connection conn, int actorId) {
		String selectFileInfoActorListWithActorId = "select * from t_file_actor where actor_id = " + actorId;
		List<FileInfo> actorList = getFileListWithSql(conn, FileInfo.class, selectFileInfoActorListWithActorId);
		return actorList;
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
				int actorId = rs.getInt(3);
				T t;
				if (Actor.class.equals(c)) {
					t = (T) actorDao.getActorWithActorId(actorId);
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

	public ActorDao getActorDao() {
		return actorDao;
	}

	public void setActorDao(ActorDao actorDao) {
		this.actorDao = actorDao;
	}
}
