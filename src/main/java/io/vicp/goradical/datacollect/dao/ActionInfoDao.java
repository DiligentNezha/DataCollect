package io.vicp.goradical.datacollect.dao;

import io.vicp.goradical.datacollect.model.ActionInfo;
import io.vicp.goradical.datacollect.tools.JDBCTools;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActionInfoDao {
	private static Map<Integer, ActionInfo> allActionInfoMap;

	public ActionInfoDao(){
		init();
	}

	private void init(){
		List<ActionInfo> actionInfoList = getAllActionInfo();
		if (allActionInfoMap == null) {
			allActionInfoMap = new HashMap<>(actionInfoList.size() * 4 / 3);
		} else {
			allActionInfoMap.clear();
		}
		for (ActionInfo actionInfo : actionInfoList) {
			allActionInfoMap.put(actionInfo.getActionInfoId(), actionInfo);
		}
	}

	public Map<Integer, ActionInfo> getAllActionInfoMap() {
		return allActionInfoMap;
	}

	public void updateActionInfoMap(){
		init();
	}

	public ActionInfo getActionInfoWithActionInfoId(int actionInfoId) {
		return allActionInfoMap.get(actionInfoId);
	}

	private List<ActionInfo> getAllActionInfo(){
		List<ActionInfo> actionInfoList = null;
		Connection conn;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String selectAllActionInfoSql = "select * from t_action_info";
		conn = JDBCTools.getConnection();
		try {
			pstmt = conn.prepareStatement(selectAllActionInfoSql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = pstmt.executeQuery();
			rs.last();
			actionInfoList = new ArrayList<>(rs.getRow());
			rs.beforeFirst();
			while (rs.next()) {
				ActionInfo actionInfo = new ActionInfo();
				actionInfo.setActionInfoId(rs.getInt(1));
				actionInfo.setFileInfoId(rs.getInt(2));
				actionInfo.setTotalPlay(rs.getInt(3));
				actionInfo.setTotalGlance(rs.getInt(4));
				actionInfo.setTotalComment(rs.getInt(5));
				actionInfo.setTotalMark(rs.getInt(6));
				actionInfo.setTotalLike(rs.getInt(7));
				actionInfo.setTotalDislike(rs.getInt(8));
				actionInfo.setTotalCollect(rs.getInt(9));
				actionInfo.setTotalSearch(rs.getInt(10));
				actionInfo.setTotalRecommend(rs.getInt(11));
				actionInfoList.add(actionInfo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTools.closeRsAndStmtAndConn(rs, pstmt, conn);
		}
		return actionInfoList;
	}
}
