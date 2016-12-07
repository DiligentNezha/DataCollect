package io.vicp.goradical.datacollect.dao;

import io.vicp.goradical.datacollect.entity.FileInfo;
import io.vicp.goradical.datacollect.tools.JDBCTools;
import io.vicp.goradical.datacollect.entity.Director;
import org.junit.Test;

import java.sql.Connection;
import java.util.List;

public class FileDirectorDaoTest {
	private FileDirectorDao fileDirectorDao = DaoManager.getDao(FileDirectorDao.class);

	@Test
	public void getFileInfoDirectorListWithFileId() throws Exception {
		Connection conn = JDBCTools.getConnection();
		List<Director> directorList = fileDirectorDao.getFileInfoDirectorListWithFileId(conn, 4);
		JDBCTools.closeConnection(conn);
		System.out.println(directorList);
	}

	@Test
	public void getFileInfoDirectorListWithDirectorId() throws Exception {
		Connection conn = JDBCTools.getConnection();
		List<FileInfo> fileList = fileDirectorDao.getFileInfoDirectorListWithDirectorId(conn, 2345);
		JDBCTools.closeConnection(conn);
		for (int i = 0; i < fileList.size(); i++) {
			System.out.println(fileList.get(i));
		}
		System.out.println(fileList.size());
	}

}