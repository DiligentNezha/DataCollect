package io.vicp.goradical.datacollect.dao;

import io.vicp.goradical.datacollect.model.CollectRecord;
import io.vicp.goradical.datacollect.tools.JDBCTools;
import org.junit.Test;

import java.sql.Connection;
import java.util.List;

public class CollectRecordDaoTest {
	private CollectRecordDao collectRecordDao = DaoManager.getDao(CollectRecordDao.class);

	@Test
	public void getCollectRecordListWithUserId() throws Exception {
		Connection conn = JDBCTools.getConnection();
		System.out.println(collectRecordDao);
		List<CollectRecord> collectRecordList = collectRecordDao.getCollectRecordListWithUserId(conn, 1);
		JDBCTools.closeConnection(conn);
	}

	@Test
	public void getCollectRecordListWithFileInfoId() throws Exception {
		Connection conn = JDBCTools.getConnection();
		List<CollectRecord> collectRecordList = collectRecordDao.getCollectRecordListWithFileInfoId(conn, 1);
		JDBCTools.closeConnection(conn);
	}

}