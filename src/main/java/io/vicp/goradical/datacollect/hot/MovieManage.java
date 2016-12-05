package io.vicp.goradical.datacollect.hot;

import io.vicp.goradical.datacollect.dao.DaoManager;
import io.vicp.goradical.datacollect.dao.FileInfoDao;
import io.vicp.goradical.datacollect.model.FileInfo;
import io.vicp.goradical.datacollect.tools.JDBCTools;
import io.vicp.goradical.datacollect.utils.DbUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

public class MovieManage {
	public static void main(String[] args) {
		createActionInfo();;
	}

	public static void createActionInfo(){
		FileInfoDao fileInfoDao = DaoManager.getDao(FileInfoDao.class);
		Map<Integer, FileInfo> fileInfoMap = fileInfoDao.getAllFileInfoMap();
		Connection conn = JDBCTools.getConnection();
		PreparedStatement pstmt = null;
		for (Map.Entry<Integer, FileInfo> entry : fileInfoMap.entrySet()) {
			try {
				System.out.println(entry.getValue().getYoukuPlayUrl());
				Document doc = Jsoup.connect("http://index.youku.com/vr_keyword/id_" + entry.getValue().getYoukuPlayUrl())
						.ignoreHttpErrors(true)
						.ignoreContentType(true)
						.get();
				Element playInfo = doc.select("ul.player_info").first();
				Elements li  = playInfo.children();
				System.out.println(li);
				String insertSql = "insert into t_action_info values (null, ?, ?, ?, ?, ?, ?, ?)";

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				JDBCTools.closeStatement(pstmt);
				JDBCTools.closeConnection(conn);
			}
		}
	}

	public static void updateFileInfoPlayUrl(){
		FileInfoDao fileInfoDao = DaoManager.getDao(FileInfoDao.class);
		Map<Integer, FileInfo> fileInfoMap = fileInfoDao.getAllFileInfoMap();
		for (Map.Entry<Integer, FileInfo> entry : fileInfoMap.entrySet()) {
			if (entry.getValue().getYoukuPlayUrl().contains("cps")) {
				Connection conn = JDBCTools.getConnection();
				PreparedStatement pstmt = null;
				try {
					String updateSql = "update t_file_info set youku_play_url = '" + URLDecoder.decode(entry.getValue().getYoukuPlayUrl(), "UTF-8") + "' where file_info_id = " + entry.getKey();
					pstmt = conn.prepareStatement(updateSql);
					pstmt.executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					JDBCTools.closeStatement(pstmt);
					JDBCTools.closeConnection(conn);
				}
			}
		}
	}

	public static void createFileNation() {
		DbUtil dbUtil = null;
		String searchNationFromNationSql = "select nation_id, nation_name from t_nation";
		String searchNationFromFileInfoSql = "select file_info_id, nation from t_file_info";
		String insertFileNationSql = "insert into t_file_nation (file_id, nation_id) values (?, ?)";
		PreparedStatement pstmt = null;
		Connection connection = null;
		ResultSet rs = null;
		Map<String, Integer> nationMap = new HashMap<>();
		Map<Integer, List<Integer>> fileNationMap = new HashMap<>();
		try {
			dbUtil = new DbUtil();
			connection = dbUtil.getCon();
			pstmt = connection.prepareStatement(searchNationFromNationSql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				int nationId = rs.getInt("nation_id");
				String nationName = rs.getString("nation_name");
				nationMap.put(nationName, nationId);
			}
			
			pstmt = connection.prepareStatement(searchNationFromFileInfoSql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				int fileInfoId = rs.getInt("file_info_id");
				String[] nations = rs.getString("nation").split("/");
				List<Integer> nationIds = new ArrayList<>();
				for (String str : nations) {
					nationIds.add(nationMap.get(str.trim()));
				}
				fileNationMap.put(fileInfoId, nationIds);
			}
			for (Map.Entry<Integer, List<Integer>> entry : fileNationMap.entrySet()) {
				System.out.println(entry.getKey() + ":" + entry.getValue());
			}
			for (Map.Entry<Integer, List<Integer>> entry : fileNationMap.entrySet()) {
				int fileInfoId = entry.getKey();
				for (Integer nationId : entry.getValue()) {
					pstmt = connection.prepareStatement(insertFileNationSql);
					pstmt.setInt(1, fileInfoId);
					pstmt.setInt(2, nationId);
					pstmt.execute();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				dbUtil.closeCon(connection);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void createFileType() {
		DbUtil dbUtil = null;
		String searchTypeFromTypeSql = "select type_id, type_name from t_type";
		String searchTypeFromFileInfoSql = "select file_info_id, type from t_file_info";
		String insertFileTypeSql = "insert into t_file_type (file_id, type_id) values (?, ?)";
		PreparedStatement pstmt = null;
		Connection connection = null;
		ResultSet rs = null;
		Map<String, Integer> typeMap = new HashMap<>();
		Map<Integer, List<Integer>> fileTypeMap = new HashMap<>();
		try {
			dbUtil = new DbUtil();
			connection = dbUtil.getCon();
			pstmt = connection.prepareStatement(searchTypeFromTypeSql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				int typeId = rs.getInt("type_id");
				String typeName = rs.getString("type_name");
				typeMap.put(typeName, typeId);
			}
			
			pstmt = connection.prepareStatement(searchTypeFromFileInfoSql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				int fileInfoId = rs.getInt("file_info_id");
				String[] types = rs.getString("type").split("/");
				List<Integer> typeIds = new ArrayList<>();
				for (String str : types) {
					typeIds.add(typeMap.get(str.trim()));
				}
				fileTypeMap.put(fileInfoId, typeIds);
			}
			for (Map.Entry<Integer, List<Integer>> entry : fileTypeMap.entrySet()) {
				System.out.println(entry.getKey() + ":" + entry.getValue());
			}
			for (Map.Entry<Integer, List<Integer>> entry : fileTypeMap.entrySet()) {
				int fileInfoId = entry.getKey();
				for (Integer typeId : entry.getValue()) {
					pstmt = connection.prepareStatement(insertFileTypeSql);
					pstmt.setInt(1, fileInfoId);
					pstmt.setInt(2, typeId);
					pstmt.execute();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				dbUtil.closeCon(connection);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void createFileActor() {
		DbUtil dbUtil = null;
		String searchActorFromActorSql = "select actor_id, actor_name from t_actor";
		String searchActorFromFileInfoSql = "select file_info_id, actor from t_file_info";
		String insertFileActorSql = "insert into t_file_actor (file_id, actor_id) values (?, ?)";
		PreparedStatement pstmt = null;
		Connection connection = null;
		ResultSet rs = null;
		Map<String, Integer> actorMap = new HashMap<>();
		Map<Integer, List<Integer>> fileActorMap = new HashMap<>();
		try {
			dbUtil = new DbUtil();
			connection = dbUtil.getCon();
			pstmt = connection.prepareStatement(searchActorFromActorSql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				int actorId = rs.getInt("actor_id");
				String actorName = rs.getString("actor_name");
				actorMap.put(actorName, actorId);
			}
			
			pstmt = connection.prepareStatement(searchActorFromFileInfoSql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				int fileInfoId = rs.getInt("file_info_id");
				String[] actors = rs.getString("actor").split("/");
				List<Integer> actorIds = new ArrayList<>();
				for (String str : actors) {
					actorIds.add(actorMap.get(str.trim()));
				}
				fileActorMap.put(fileInfoId, actorIds);
			}
//			for (Map.Entry<Integer, List<Integer>> entry : fileActorMap.entrySet()) {
//				System.out.println(entry.getKey() + ":" + entry.getValue());
//			}
			for (Map.Entry<Integer, List<Integer>> entry : fileActorMap.entrySet()) {
				int fileInfoId = entry.getKey();
				for (Integer actorId : entry.getValue()) {
					pstmt = connection.prepareStatement(insertFileActorSql);
					pstmt.setInt(1, fileInfoId);
					pstmt.setInt(2, actorId);
					pstmt.execute();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				dbUtil.closeCon(connection);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void createFileDirector() {
		DbUtil dbUtil = null;
		String searchDirectorFromDirectorSql = "select director_id, director_name from t_director";
		String searchDirectorFromFileInfoSql = "select file_info_id, director from t_file_info";
		String insertFileDirectorSql = "insert into t_file_director (file_id, director_id) values (?, ?)";
		PreparedStatement pstmt = null;
		Connection connection = null;
		ResultSet rs = null;
		Map<String, Integer> directorMap = new HashMap<>();
		Map<Integer, List<Integer>> fileDirectorMap = new HashMap<>();
		try {
			dbUtil = new DbUtil();
			connection = dbUtil.getCon();
			pstmt = connection.prepareStatement(searchDirectorFromDirectorSql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				int directorId = rs.getInt("director_id");
				String directorName = rs.getString("director_name");
				directorMap.put(directorName, directorId);
			}
			
			pstmt = connection.prepareStatement(searchDirectorFromFileInfoSql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				int fileInfoId = rs.getInt("file_info_id");
				String[] directors = rs.getString("director").split("/");
				List<Integer> directorIds = new ArrayList<>();
				for (String str : directors) {
					directorIds.add(directorMap.get(str.trim()));
				}
				fileDirectorMap.put(fileInfoId, directorIds);
			}
//			for (Map.Entry<Integer, List<Integer>> entry : fileDirectorMap.entrySet()) {
//				System.out.println(entry.getKey() + ":" + entry.getValue());
//			}
			for (Map.Entry<Integer, List<Integer>> entry : fileDirectorMap.entrySet()) {
				int fileInfoId = entry.getKey();
				for (Integer directorId : entry.getValue()) {
					pstmt = connection.prepareStatement(insertFileDirectorSql);
					pstmt.setInt(1, fileInfoId);
					pstmt.setInt(2, directorId);
					pstmt.execute();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				dbUtil.closeCon(connection);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void getId() {
		DbUtil dbUtil = null;
		String searchYoukuPlayUrlSql = "select id, youku_play_url from t_file_movie_info_copy_copy";
		String updateIdSql = "update t_file_movie_info_copy_copy set file_id=? where id=?";
		PreparedStatement pstmt = null;
		Connection connection = null;
		ResultSet rs = null;
		Map<Integer, String> idUrlMap = new HashMap<>();
		try {
			dbUtil = new DbUtil();
			connection = dbUtil.getCon();
			pstmt = connection.prepareStatement(searchYoukuPlayUrlSql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				String youkuPlayUrl = rs.getString("youku_play_url");
				int id = rs.getInt("id");
				if (youkuPlayUrl.contains("cps")) {
					youkuPlayUrl = URLDecoder.decode(youkuPlayUrl, "UTF-8");
				}
				int start = youkuPlayUrl.indexOf("id_") + 3;
				int end = youkuPlayUrl.lastIndexOf(".html");
				String fileId = youkuPlayUrl.substring(start, end);
				idUrlMap.put(id, fileId);
			}
			for (Map.Entry<Integer, String> entry : idUrlMap.entrySet()) {
				int id = entry.getKey();
				String fileId = entry.getValue();
				pstmt = connection.prepareStatement(updateIdSql);
				pstmt.setString(1, fileId);
				pstmt.setInt(2, id);
				pstmt.executeUpdate();
			}
			
			System.out.println(idUrlMap.size());
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				dbUtil.closeCon(connection);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void createNation() {
		DbUtil dbUtil = null;
		Set<String> nationSet = new HashSet<>();
		String searchNationSql = "select nation from t_file_movie_info_copy_copy";
		String createNationSql = "insert into t_nation (nation_name) values (?)";
		PreparedStatement pstmt = null;
		Connection connection = null;
		ResultSet rs = null;
		try {
			dbUtil = new DbUtil();
			connection = dbUtil.getCon();
			pstmt = connection.prepareStatement(searchNationSql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				String nation = rs.getString("nation");
				String[] temp = nation.split("/");
				for (String string : temp) {
					nationSet.add(string.trim());
				}
			}
			for (String string : nationSet) {
				pstmt = connection.prepareStatement(createNationSql);
				pstmt.setString(1, string);
				pstmt.execute();
			}
			System.out.println(nationSet.size());
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				dbUtil.closeCon(connection);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void createType() {
		DbUtil dbUtil = null;
		Set<String> typeSet = new HashSet<>();
		String searchTypeSql = "select type from t_file_movie_info_copy_copy";
		String createTypeSql = "insert into t_type (type_name) values (?)";
		PreparedStatement pstmt = null;
		Connection connection = null;
		ResultSet rs = null;
		try {
			dbUtil = new DbUtil();
			connection = dbUtil.getCon();
			pstmt = connection.prepareStatement(searchTypeSql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				String type = rs.getString("type");
				String[] temp = type.split("/");
				for (String string : temp) {
					typeSet.add(string.trim());
				}
			}
			for (String string : typeSet) {
				pstmt = connection.prepareStatement(createTypeSql);
				pstmt.setString(1, string);
				pstmt.execute();
			}
			System.out.println(typeSet.size());
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				dbUtil.closeCon(connection);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void createActor() {
		DbUtil dbUtil = null;
		Set<String> actorSet = new HashSet<>();
		String searchActorSql = "select actor from t_file_movie_info_copy_copy";
		String createActorSql = "insert into t_actor (actor_name) values (?)";
		PreparedStatement pstmt = null;
		Connection connection = null;
		ResultSet rs = null;
		try {
			dbUtil = new DbUtil();
			connection = dbUtil.getCon();
			pstmt = connection.prepareStatement(searchActorSql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				String director = rs.getString("actor");
				String[] temp = director.split("/");
				for (String string : temp) {
					actorSet.add(string.trim());
				}
			}
			for (String string : actorSet) {
				pstmt = connection.prepareStatement(createActorSql);
				pstmt.setString(1, string);
				pstmt.execute();
			}
			System.out.println(actorSet.size());
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				dbUtil.closeCon(connection);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	public static void createDirector() {
		DbUtil dbUtil = null;
		Set<String> directorSet = new HashSet<>();
		String searchDirectorSql = "select director from t_file_movie_info_copy_copy";
		String createDirectorSql = "insert into t_director (director_name) values (?)";
		PreparedStatement pstmt = null;
		Connection connection = null;
		ResultSet rs = null;
		try {
			dbUtil = new DbUtil();
			connection = dbUtil.getCon();
			pstmt = connection.prepareStatement(searchDirectorSql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				String director = rs.getString("director");
				String[] temp = director.split("/");
				for (String string : temp) {
					directorSet.add(string.trim());
				}
			}
			for (String string : directorSet) {
				pstmt = connection.prepareStatement(createDirectorSql);
				pstmt.setString(1, string);
				pstmt.execute();
			}
			System.out.println(directorSet.size());
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				dbUtil.closeCon(connection);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
