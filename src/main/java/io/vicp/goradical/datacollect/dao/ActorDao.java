package io.vicp.goradical.datacollect.dao;

import io.vicp.goradical.datacollect.entity.Actor;
import io.vicp.goradical.datacollect.tools.JDBCTools;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActorDao {
	private static Map<Integer, Actor> allActorMap;

	public ActorDao(){
		init();
	}

	private void init(){
		List<Actor> actorList = getAllActor();
		if (allActorMap == null) {
			allActorMap = new HashMap<>(actorList.size() * 4 / 3);
		} else {
			allActorMap.clear();
		}
		for (Actor actor : actorList) {
			allActorMap.put(actor.getActorId(), actor);
		}
	}

	public Map<Integer, Actor> getAllActorMap() {
		return allActorMap;
	}

	public void updateActorMap(){
		init();
	}

	public Actor getActorWithActorId(int actorId) {
		return allActorMap.get(actorId);
	}

	private List<Actor> getAllActor(){
		List<Actor> actorList = null;
		Connection conn;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String selectAllActorSql = "select * from t_actor";
		conn = JDBCTools.getConnection();
		try {
			pstmt = conn.prepareCall(selectAllActorSql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = pstmt.executeQuery();
			rs.last();
			actorList = new ArrayList<>(rs.getRow());
			rs.beforeFirst();
			while (rs.next()) {
				Actor actor = new Actor();
				actor.setActorId(rs.getInt(1));
				actor.setActorName(rs.getString(2));
				actor.setDescription(rs.getString(3));
				actorList.add(actor);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTools.closeRsAndStmtAndConn(rs, pstmt, conn);
		}
		return actorList;
	}
}
