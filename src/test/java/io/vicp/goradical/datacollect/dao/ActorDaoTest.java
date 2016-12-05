package io.vicp.goradical.datacollect.dao;

import io.vicp.goradical.datacollect.model.Actor;
import org.junit.Test;

import java.util.Map;

public class ActorDaoTest {
	private ActorDao actorDao = DaoManager.getDao(ActorDao.class);

	@Test
	public void getAllActorMap() throws Exception {
		Map<Integer, Actor> allActorMap = actorDao.getAllActorMap();
		for (Map.Entry<Integer, Actor> entry : allActorMap.entrySet()) {
			System.out.println("actorId:" + entry.getKey() + ", actor:" + entry.getValue());
		}
	}

	@Test
	public void getActorWithActorId() throws Exception {
		Actor actor = actorDao.getActorWithActorId(1);
		System.out.println(actor);
	}

}