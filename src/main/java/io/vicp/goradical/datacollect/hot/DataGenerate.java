package io.vicp.goradical.datacollect.hot;

import io.vicp.goradical.datacollect.dao.CommentRecordDao;
import io.vicp.goradical.datacollect.dao.DaoManager;
import io.vicp.goradical.datacollect.dao.FileInfoDao;
import io.vicp.goradical.datacollect.model.CommentRecord;
import io.vicp.goradical.datacollect.model.FileInfo;
import io.vicp.goradical.datacollect.tools.JDBCTools;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

public class DataGenerate implements Runnable{
	private static CommentRecordDao commentRecordDao = DaoManager.getDao(CommentRecordDao.class);
	private static FileInfoDao fileInfoDao = DaoManager.getDao(FileInfoDao.class);
	private static List<FileInfo> fileInfoList = new ArrayList<>(3600);
	private static List<TempResult> tempResultList = new Vector<>(3600);
	private static Integer index = 0;
	static {
		initFileInfoList();
	}
	@Override
	public void run() {
		while (index < fileInfoList.size()) {
			synchronized (index) {
				if (index < fileInfoList.size()) {
					FileInfo fileInfo;
					synchronized (fileInfoList) {
						fileInfo = fileInfoList.get(index++);
					}
//					System.out.println(Thread.currentThread() + ", fileInfoId:" + fileInfo.getFileInfoId() + ", fileInfoName:" + fileInfo.getFileName());
					collectDataGenerate(fileInfo);
//					System.out.println(Thread.currentThread() + ", fileInfoId:" + fileInfo.getFileInfoId() + ", fileInfoName:" + fileInfo.getFileName());
				}
			}
		}
	}

	private void collectDataGenerate(FileInfo fileInfo){
		List<CommentRecord> commentRecordList = getFileCommentWithFileInfoId(fileInfo.getFileInfoId());
		if (commentRecordList == null) {
			return;
		}
		String sql;
		FileOutputStream fosCollect = Printer.getFosCollect();
		int totalCollect = fileInfo.getActionInfo().getTotalCollect();
		int halfTotalCollect = 0;
		int needToRandomTotal = totalCollect;
		Date startTime = fileInfo.getPublished();
		try {
			for (int i = 0; i < commentRecordList.size(); i += 2) {
				halfTotalCollect++;
				sql = "insert into t_collect_record values (null, " + commentRecordList.get(i).getUserProfile().getUserProfileId() + ", " + commentRecordList.get(i).getFileInfo().getFileInfoId() + ", '" + commentRecordList.get(i).getCommentDate().toLocaleString() + "');\n";
				fosCollect.write(sql.getBytes());
			}
			needToRandomTotal = totalCollect - halfTotalCollect;
			Random random = new Random();
			for(int i = 0; i < needToRandomTotal; i++) {
				sql = "insert into t_collect_record values (null, " + random.nextInt(1916954) + 1 + ", " + random.nextInt(3575) + 1 + ", '" + new Date(new Date().getTime() - random.nextInt(1000 * 3600 * 24 * 31)).toLocaleString() + "');\n";
				fosCollect.write(sql.getBytes());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

//		System.out.println(Thread.currentThread() + ", fileInfoId:" + fileInfo.getFileInfoId() + ", commentListSize:" + commentRecordList.size());
//		System.out.println("hello : " + fileInfo.getFileInfoId());
	}

	private List<CommentRecord> getFileCommentWithFileInfoId(int fileInfoId) {
		List<CommentRecord> commentRecordList = commentRecordDao.getCommentRecordListWithFileInfoId(JDBCTools.getConnection(), fileInfoId);
		tempResultList.add(new TempResult(fileInfoId, commentRecordList.size()));
//			System.out.println("fileInfoId:" + fileInfoId + ", commentListSize:" + commentRecordList.size());
		return commentRecordList;
	}

	public void showTempResultList(){
		for(int i = 0, len = tempResultList.size(); i < len; i++) {
			System.out.println(tempResultList.get(i));
		}
		System.out.println("listSize:" + tempResultList.size());
	}

	public void showMissFileInfoIdList(){
		List<Integer> missFileInfoIdList = new ArrayList<>();
		for(int i = 1, len = tempResultList.size(); i < len; i++) {
			int t = tempResultList.get(i).fileInfoId - tempResultList.get(i - 1).fileInfoId;
			if (t > 1) {
				missFileInfoIdList.add(tempResultList.get(i - 1).fileInfoId + 1);
			}
		}
		System.out.println("missFileInfoIdList:" + missFileInfoIdList);
	}

	public void showDuplicateFileInfoIdList(){
		List<Integer> duplicateFileInfoIdList = new ArrayList<>();
		for(int i = 1, len = tempResultList.size(); i < len; i++) {
			int t = tempResultList.get(i).fileInfoId - tempResultList.get(i - 1).fileInfoId;
			if (t == 0){
				duplicateFileInfoIdList.add(tempResultList.get(i - 1).fileInfoId);
			}
		}
		System.out.println("duplicateFileInfoIdList:" + duplicateFileInfoIdList);
	}

	public static void initFileInfoList(){
		fileInfoList = fileInfoDao.getAllFileInfo();
	}

	public List<TempResult> getTempResultList() {
		return tempResultList;
	}

	private class TempResult implements Comparable<TempResult>{
		private int fileInfoId;
		private int commentListSize;

		public TempResult(int fileInfoId, int commentListSize) {
			this.fileInfoId = fileInfoId;
			this.commentListSize = commentListSize;
		}

		@Override
		public String toString() {
			return "TempResult{" +
					"fileInfoId=" + fileInfoId +
					", commentListSize=" + commentListSize +
					'}';
		}

		@Override
		public int compareTo(TempResult o) {
			if (fileInfoId > o.fileInfoId){
				return 1;
			}
			if (fileInfoId < o.fileInfoId){
				return -1;
			}
			return 0;
		}
	}
}
