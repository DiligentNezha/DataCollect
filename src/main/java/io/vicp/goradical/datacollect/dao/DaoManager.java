package io.vicp.goradical.datacollect.dao;

import java.util.HashMap;
import java.util.Map;

public class DaoManager {
	private static final Map<Class, Object> daoMap = new HashMap<>();

	static {
		init();
	}

	private static void init(){
		CategoryDao categoryDao = new CategoryDao();
		daoMap.put(CategoryDao.class, categoryDao);

		DirectorDao directorDao = new DirectorDao();
		daoMap.put(DirectorDao.class, directorDao);

		ActorDao actorDao = new ActorDao();
		daoMap.put(ActorDao.class, actorDao);

		TypeDao typeDao = new TypeDao();
		daoMap.put(TypeDao.class, typeDao);

		NationDao nationDao = new NationDao();
		daoMap.put(NationDao.class, nationDao);

		FileInfoDao fileInfoDao = new FileInfoDao();
		daoMap.put(FileInfoDao.class, fileInfoDao);

		ActionInfoDao actionInfoDao = new ActionInfoDao();
		daoMap.put(ActionInfoDao.class, actionInfoDao);

		DetailsInfoDao detailsInfoDao = new DetailsInfoDao();
		daoMap.put(DetailsInfoDao.class, detailsInfoDao);

		UserProfileDao userProfileDao = new UserProfileDao(detailsInfoDao);
		daoMap.put(UserProfileDao.class, userProfileDao);

		FileDirectorDao fileDirectorDao = new FileDirectorDao(fileInfoDao, directorDao);
		daoMap.put(FileDirectorDao.class, fileDirectorDao);

		FileActorDao fileActorDao = new FileActorDao(fileInfoDao, actorDao);
		daoMap.put(FileActorDao.class, fileActorDao);

		FileTypeDao fileTypeDao = new FileTypeDao(fileInfoDao, typeDao);
		daoMap.put(FileTypeDao.class, fileTypeDao);

		FileNationDao fileNationDao = new FileNationDao(fileInfoDao, nationDao);
		daoMap.put(FileNationDao.class, fileNationDao);

		fileInfoDao.setFileDirectorDao(fileDirectorDao);
		fileInfoDao.setFileActorDao(fileActorDao);
		fileInfoDao.setFileTypeDao(fileTypeDao);
		fileInfoDao.setFileNationDao(fileNationDao);
		fileInfoDao.setCategoryDao(categoryDao);
		fileInfoDao.setActionInfoDao(actionInfoDao);
		fileInfoDao.init();

		CommentRecordDao commentRecordDao = new CommentRecordDao(userProfileDao, fileInfoDao);
		daoMap.put(CommentRecordDao.class, commentRecordDao);

		CollectRecordDao collectRecordDao = new CollectRecordDao(userProfileDao, fileInfoDao);
		daoMap.put(CollectRecordDao.class, collectRecordDao);
	}

	public static <T> T getDao(Class<T> clz){
		return (T) daoMap.get(clz);
	}

	public static Map<Class, Object> getDaoMap() {
		return daoMap;
	}
}
