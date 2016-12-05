package io.vicp.goradical.datacollect.hot;

import io.vicp.goradical.datacollect.tools.JDBCTools;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class DataGenerateTest implements Runnable{
	private static List<Integer> fileIdsList;
	private static Integer index;
	static {
		fileIdsList = CommentManager.getFileIdsList();
		index = 0;
	}
	
	@Override
	public void run() {
		while (index < fileIdsList.size()) {
			synchronized (index) {
				if (index < fileIdsList.size()) {
					int temp = index++;
					Thread.yield();
					work(fileIdsList.get(temp));
//					System.out.println(Thread.currentThread() + ":" + fileIdsList.get(temp));
				}
			}
		}
	}
	
	private void work(int fileId) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Map<Integer, String> commentIdsMap = new HashMap<>(500);
		String selectCommentIdsSql = "select comment_record_id from t_comment_record where file_id = ?";
		String selectFilePublishedSql = "select published from t_file_info where file_info_id=?";
		conn = JDBCTools.getConnection();
		try {
			//1.获取电影发布日期
			pstmt = conn.prepareStatement(selectFilePublishedSql);
			pstmt.setInt(1, fileId);
			rs = pstmt.executeQuery();
			String pubStr = null;
			if (rs.next()) {
				pubStr = rs.getString(1);
			}
			//2.获取电影的评论列表
			pstmt = conn.prepareStatement(selectCommentIdsSql);
			pstmt.setInt(1, fileId);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				commentIdsMap.put(rs.getInt(1), null);
			}
			//3.产生电影的评论时间
			commentDataGenerate(conn, pstmt, rs, fileId, pubStr, commentIdsMap);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			JDBCTools.closeRsAndStmtAndConn(rs, pstmt, conn);
		}
	}
	
	private void commentDataGenerate(Connection conn, PreparedStatement pstmt, ResultSet rs, int fileId, String pubStr, Map<Integer, String> commentIdsMap) {
		try {
			long pubTime = new SimpleDateFormat("yyyy-MM-dd").parse(pubStr).getTime();
			long currentTime = System.currentTimeMillis();
			int days = getDays(pubTime, currentTime);
			List<Integer> commentIdsList = new ArrayList<>(commentIdsMap.size());
			commentIdsList.addAll(commentIdsMap.keySet());
			Collections.sort(commentIdsList);
			//70%的评论
			int seventyPercentComments = commentIdsList.size() * 70 / 100;
			//30%的评论
			int thirtyPercentComments = commentIdsList.size() - seventyPercentComments;
			Calendar cal = Calendar.getInstance(Locale.CHINA);
			cal.setTimeInMillis(pubTime);
			cal.add(cal.WEEK_OF_MONTH, 2);
			long twoWeeksTime = cal.getTimeInMillis();
			System.out.println(pubStr + ":" + new Date(twoWeeksTime).toLocaleString() + ":" + (currentTime - pubTime) + ":" + getDays(pubTime, currentTime));
			for(int i = 0, len = commentIdsList.size(); i < len; i++) {
				
			}
			
//			System.out.println(commentIdsList.size() + ":" + seventyPercentComments + "+" + thirtyPercentComments);
//			System.out.println(Thread.currentThread() + ", fileId:" + fileId + ", published:" + pubStr + "<-->days:" + days + ", " + commentIdsList.size() + ", " + commentIdsList);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
	}
	
	private int getDays(long start, long end) {
		long times = end - start;
		int days = (int) (times / (24 * 3600 * 1000));
		return days;
	}
}
