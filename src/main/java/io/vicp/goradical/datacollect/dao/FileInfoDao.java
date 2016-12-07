package io.vicp.goradical.datacollect.dao;

import io.vicp.goradical.datacollect.entity.*;
import io.vicp.goradical.datacollect.tools.JDBCTools;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 影片信息数据访问对象
 */
public class FileInfoDao {
	//影片的导演信息访问对象
	private FileDirectorDao fileDirectorDao;
	//影片的演员信息访问对象
	private FileActorDao fileActorDao;
	//影片的类型信息访问对象
	private FileTypeDao fileTypeDao;
	//影片的国家信息访问对象
	private FileNationDao fileNationDao;
	//影片的分类信息访问对象
	private CategoryDao categoryDao;
	//影片的活动信息访问对象
	private ActionInfoDao actionInfoDao;
	//所有的影片组成的Map
	private static Map<Integer, FileInfo> allFileInfoMap;
	public FileInfoDao() {
	}

	public FileInfoDao(FileDirectorDao fileDirectorDao, FileActorDao fileActorDao, FileTypeDao fileTypeDao, FileNationDao fileNationDao, CategoryDao categoryDao, ActionInfoDao actionInfoDao) {
		this.fileDirectorDao = fileDirectorDao;
		this.fileActorDao = fileActorDao;
		this.fileTypeDao = fileTypeDao;
		this.fileNationDao = fileNationDao;
		this.categoryDao = categoryDao;
		this.actionInfoDao = actionInfoDao;
	}

	public void init(){
		List<FileInfo> fileInfoList = getAllFileInfo();
		if (allFileInfoMap == null) {
			allFileInfoMap = new HashMap<>(fileInfoList.size() * 4 / 3);
		} else {
			allFileInfoMap.clear();
		}
		for (FileInfo fileInfo : fileInfoList) {
			allFileInfoMap.put(fileInfo.getFileInfoId(), fileInfo);
		}
	}

	public static Map<Integer, FileInfo> getAllFileInfoMap() {
		return allFileInfoMap;
	}

	public void updateFileInfoMap(){
		init();
	}

	/**
	 * 通过影片信息的Id获取该影片的导演列表
	 * @param fileInfoId 影片信息Id
	 * @return 影片的导演列表
	 */
	private List<Director> getFileDirectorListWithFileInfoId(Connection conn, int fileInfoId) {
		return getFileAdditionalInfoWithFileInfoId(Director.class, conn, "getFileInfoDirectorListWithFileId", fileInfoId);
	}

	/**
	 * 通过影片信息的Id获取该影片的演员列表
	 * @param fileInfoId 影片信息Id
	 * @return 影片的演员列表
	 */
	private List<Actor> getFileActorListWithFileInfoId(Connection conn, int fileInfoId) {
		return getFileAdditionalInfoWithFileInfoId(Actor.class, conn, "getFileInfoActorListWithFileId", fileInfoId);
	}

	/**
	 * 通过影片信息的Id获取该影片的类型列表
	 * @param fileInfoId 影片信息Id
	 * @return 影片的类型列表
	 */
	private List<Type> getFileTypeListWithFileInfoId(Connection conn, int fileInfoId) {
		return getFileAdditionalInfoWithFileInfoId(Type.class, conn, "getFileInfoTypeListWithFileId", fileInfoId);
	}

	/**
	 * 通过影片信息的Id获取该影片的国家列表
	 * @param fileInfoId 影片信息Id
	 * @return 影片的国家列表
	 */
	private List<Nation> getFileNationLIstWithFileInfoId(Connection conn, int fileInfoId) {
		return getFileAdditionalInfoWithFileInfoId(Nation.class, conn, "getFileInfoNationListWithFileId", fileInfoId);
	}

	/**
	 * 一个泛型方法，通过影片信息的Id,要获取影片的那种类型的列表，例如:Director, Actor, Type, Nation,以及对应的方法名
0	 * @param clz 需要获取的类型
	 * @param fileInfoId 影片信息Id
	 * @param methodName 类型名
	 * @param <T>
	 * @return 需要获取的类型组成的列表
	 */
	private <T> List<T> getFileAdditionalInfoWithFileInfoId(Class<T> clz, Connection conn, String methodName, int fileInfoId) {
		List<T> fileAdditionalInfoList = new ArrayList<>();
		try {
			Method method;
			if (Director.class.equals(clz)) {
				method = FileDirectorDao.class.getMethod(methodName, Connection.class, int.class);
				fileAdditionalInfoList = (List<T>) method.invoke(fileDirectorDao, conn, fileInfoId);
			} else if (Actor.class.equals(clz)) {
				method = FileActorDao.class.getMethod(methodName, Connection.class, int.class);
				fileAdditionalInfoList = (List<T>) method.invoke(fileActorDao, conn, fileInfoId);
			} else if (Type.class.equals(clz)) {
				method = FileTypeDao.class.getMethod(methodName, Connection.class, int.class);
				fileAdditionalInfoList = (List<T>) method.invoke(fileTypeDao, conn, fileInfoId);
			} else {
				method = FileNationDao.class.getMethod(methodName, Connection.class, int.class);
				fileAdditionalInfoList = (List<T>) method.invoke(fileNationDao, conn, fileInfoId);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fileAdditionalInfoList;
	}

	/**
	 * 通过影片信息Id获取影片信息
	 * @param fileInfoId 影片信息Id
	 * @return 影片信息
	 */
	public FileInfo getFileInfoWithFileInfoId(int fileInfoId) {
		return allFileInfoMap.get(fileInfoId);
	}

	/**
	 * 获取所有的影片信息
	 * @return 所有的影片信息组成的列表
	 */
	public List<FileInfo> getAllFileInfo() {
		List<FileInfo> fileInfoList = null;
		Connection conn;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String selectAllFileInfoSql = "select * from t_file_info";
		conn = JDBCTools.getConnection();
		try {
			pstmt = conn.prepareStatement(selectAllFileInfoSql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = pstmt.executeQuery();
			rs.last();
			fileInfoList = new ArrayList<>(rs.getRow());
			rs.beforeFirst();
			while (rs.next()) {
				FileInfo fileInfo = new FileInfo();
				int fileInfoId = rs.getInt(1);
				fileInfo.setFileInfoId(fileInfoId);
				fileInfo.setFileId(rs.getString(2));
				fileInfo.setFileName(rs.getString(3));
				fileInfo.setCover(rs.getString(4));
				fileInfo.setDirectorList(getFileDirectorListWithFileInfoId(conn, fileInfoId));
				fileInfo.setActorList(getFileActorListWithFileInfoId(conn, fileInfoId));
				fileInfo.setCategory(categoryDao.getCategoryWithCategoryId(rs.getInt(7)));
				fileInfo.setTypeList(getFileTypeListWithFileInfoId(conn, fileInfoId));
				fileInfo.setNationList(getFileNationLIstWithFileInfoId(conn, fileInfoId));
				fileInfo.setPublished(rs.getDate(10));
				fileInfo.setDuration(rs.getInt(11));
				fileInfo.setAlias(rs.getString(12));
				fileInfo.setSummary(rs.getString(13));
				fileInfo.setYoukuSrcUrl(rs.getString(14));
				fileInfo.setYoukuPlayUrl(rs.getString(15));
				fileInfo.setYoukuDetailUrl(rs.getString(16));
				fileInfo.setDetailUrl(rs.getString(17));
				fileInfo.setDownloadUrl(rs.getString(18));
				fileInfo.setActionInfo(actionInfoDao.getActionInfoWithActionInfoId(19));
				fileInfoList.add(fileInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCTools.closeRsAndStmtAndConn(rs, pstmt, conn);
		}
		return fileInfoList;
	}

	public FileDirectorDao getFileDirectorDao() {
		return fileDirectorDao;
	}

	public void setFileDirectorDao(FileDirectorDao fileDirectorDao) {
		this.fileDirectorDao = fileDirectorDao;
	}

	public FileActorDao getFileActorDao() {
		return fileActorDao;
	}

	public void setFileActorDao(FileActorDao fileActorDao) {
		this.fileActorDao = fileActorDao;
	}

	public FileTypeDao getFileTypeDao() {
		return fileTypeDao;
	}

	public void setFileTypeDao(FileTypeDao fileTypeDao) {
		this.fileTypeDao = fileTypeDao;
	}

	public FileNationDao getFileNationDao() {
		return fileNationDao;
	}

	public void setFileNationDao(FileNationDao fileNationDao) {
		this.fileNationDao = fileNationDao;
	}

	public CategoryDao getCategoryDao() {
		return categoryDao;
	}

	public void setCategoryDao(CategoryDao categoryDao) {
		this.categoryDao = categoryDao;
	}

	public ActionInfoDao getActionInfoDao() {
		return actionInfoDao;
	}

	public void setActionInfoDao(ActionInfoDao actionInfoDao) {
		this.actionInfoDao = actionInfoDao;
	}

	public static void setAllFileInfoMap(Map<Integer, FileInfo> allFileInfoMap) {
		FileInfoDao.allFileInfoMap = allFileInfoMap;
	}
}
