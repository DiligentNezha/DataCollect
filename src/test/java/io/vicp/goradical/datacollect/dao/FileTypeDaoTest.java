package io.vicp.goradical.datacollect.dao;

import io.vicp.goradical.datacollect.entity.FileInfo;
import io.vicp.goradical.datacollect.entity.Type;
import org.junit.Test;
import io.vicp.goradical.datacollect.tools.JDBCTools;

import java.sql.Connection;
import java.util.List;

public class FileTypeDaoTest {
	private FileTypeDao fileTypeDao = DaoManager.getDao(FileTypeDao.class);

	@Test
	public void getFileInfoTypeListWithFileId() throws Exception {
		Connection conn = JDBCTools.getConnection();
		List<Type> typeList = fileTypeDao.getFileInfoTypeListWithFileId(conn, 2);
		JDBCTools.closeConnection(conn);
		System.out.println(typeList);
	}

	@Test
	public void getFileInfoTypeListWithTypeId() throws Exception {
		Connection conn = JDBCTools.getConnection();
		List<FileInfo> fileInfoList = fileTypeDao.getFileInfoTypeListWithTypeId(conn, 7);
		JDBCTools.closeConnection(conn);
		for (int i = 0; i < fileInfoList.size(); i++) {
			System.out.println(fileInfoList.get(i));
		}
		System.out.println(fileInfoList.size());
	}

}