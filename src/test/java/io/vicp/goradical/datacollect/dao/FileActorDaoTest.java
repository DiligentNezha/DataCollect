package io.vicp.goradical.datacollect.dao;

import io.vicp.goradical.datacollect.model.Actor;
import io.vicp.goradical.datacollect.model.FileInfo;
import io.vicp.goradical.datacollect.tools.JDBCTools;
import org.junit.Test;

import java.sql.Connection;
import java.util.List;

public class FileActorDaoTest {
	private FileActorDao fileActorDao = DaoManager.getDao(FileActorDao.class);

	@Test
	public void getFileInfoActorListWithFileId() throws Exception {
		Connection conn = JDBCTools.getConnection();
		List<Actor> actorList = fileActorDao.getFileInfoActorListWithFileId(conn, 2);
		JDBCTools.closeConnection(conn);
		System.out.println(actorList);
	}

	@Test
	public void getFileInfoActorListWithActorId() throws Exception {
		Connection conn = JDBCTools.getConnection();
		List<FileInfo> fileInfoList = fileActorDao.getFileInfoActorListWithActorId(conn, 3618);
		JDBCTools.closeConnection(conn);
		for (int i = 0; i < fileInfoList.size(); i++) {
			System.out.println(fileInfoList.get(i));
		}
	}

}