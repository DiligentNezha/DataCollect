package io.vicp.goradical.datacollect.dao;

import io.vicp.goradical.datacollect.entity.FileInfo;
import org.junit.Test;

import java.util.List;

public class FileInfoDaoTest {
	FileInfoDao fileInfoDao = DaoManager.getDao(FileInfoDao.class);

	@Test
	public void getFileInfoWithFileInfoId() throws Exception {
		FileInfo fileInfo = fileInfoDao.getFileInfoWithFileInfoId(1);
		System.out.println(fileInfo);
	}

	@Test
	public void getAllFileInfo() throws Exception {
		List<FileInfo> fileInfoList = fileInfoDao.getAllFileInfo();
//		for (int i = 0; i < fileInfoList.size(); i++) {
//			System.out.println(fileInfoList.get(i));
//		}
		System.out.println(fileInfoList.size());
	}

}