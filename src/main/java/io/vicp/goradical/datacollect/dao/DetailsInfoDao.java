package io.vicp.goradical.datacollect.dao;

import io.vicp.goradical.datacollect.model.DetailsInfo;
import io.vicp.goradical.datacollect.tools.JDBCTools;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DetailsInfoDao {

	public DetailsInfo getDetailsInfoWithDetailsInfoId(Connection conn, int detailsInfoId) {
		DetailsInfo detailsInfo = null;
		Statement stmt = null;
		ResultSet rs = null;
		String selectDetailsInfoWithDetailsInfoIdSql = "select * from t_details_info where details_info_id = " + detailsInfoId;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(selectDetailsInfoWithDetailsInfoIdSql);
			if (rs.next()) {
				detailsInfo = new DetailsInfo();
				detailsInfo.setDetailsInfoId(rs.getInt(1));
				detailsInfo.setNickName(rs.getString(2));
				detailsInfo.setGender(rs.getString(3));
				detailsInfo.setRegion(rs.getString(4));
				detailsInfo.setBirthday(rs.getDate(5));
				detailsInfo.setConstellation(rs.getString(6));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTools.closeResultSetAndStatement(rs, stmt);
		}
		return detailsInfo;
	}

	public List<DetailsInfo> getAllDetailsInfo(Connection conn){
		List<DetailsInfo> detailsInfoList = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String selectAllDetailsInfoSql = "select * from t_details_info";
		try {
			pstmt = conn.prepareCall(selectAllDetailsInfoSql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = pstmt.executeQuery();
			rs.last();
			detailsInfoList = new ArrayList<>(rs.getRow());
			rs.beforeFirst();
			while (rs.next()) {
				DetailsInfo detailsInfo = new DetailsInfo();
				detailsInfo.setDetailsInfoId(rs.getInt(1));
				detailsInfo.setNickName(rs.getString(2));
				detailsInfo.setGender(rs.getString(3));
				detailsInfo.setRegion(rs.getString(4));
				detailsInfo.setBirthday(rs.getDate(5));
				detailsInfo.setConstellation(rs.getString(6));
				detailsInfo.setPersonalizedSignatures(rs.getString(7));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTools.closeResultSetAndStatement(rs, pstmt);
		}
		return detailsInfoList;
	}
}
