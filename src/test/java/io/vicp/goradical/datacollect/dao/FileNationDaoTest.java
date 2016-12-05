package io.vicp.goradical.datacollect.dao;

import io.vicp.goradical.datacollect.model.FileInfo;
import io.vicp.goradical.datacollect.model.Nation;
import org.junit.Test;
import io.vicp.goradical.datacollect.tools.JDBCTools;

import java.sql.Connection;
import java.util.List;

public class FileNationDaoTest {
	private FileNationDao fileNationDao = DaoManager.getDao(FileNationDao.class);

	@Test
	public void getFileInfoNationListWithFileId() throws Exception {
		Connection conn = JDBCTools.getConnection();
		List<Nation> nationList = fileNationDao.getFileInfoNationListWithFileId(conn, 1);
		JDBCTools.closeConnection(conn);
		System.out.println(nationList);
	}

	@Test
	public void getFileInfoNationListWithNationId() throws Exception {
		Connection conn = JDBCTools.getConnection();
		List<FileInfo> fileInfoList = fileNationDao.getFileInfoNationListWithNationId(conn, 3);
		JDBCTools.closeConnection(conn);
		for (int i = 0; i < fileInfoList.size(); i++) {
			System.out.println(fileInfoList.get(i));
		}
		System.out.println(fileInfoList.size());
	}

}