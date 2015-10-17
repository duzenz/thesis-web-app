package com.duzenz.recommender.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.duzenz.recommender.entities.UserTrack;

@Repository
@Transactional
public class UserTrackImpl implements UserTrackDao 
{
	@PersistenceContext
	private EntityManager em;
	
	@Override
	@Transactional(readOnly=true)
	public UserTrack findUserTrack(int userTrackId) {
		return em.find(UserTrack.class, userTrackId);
	}
	
	@Override
	public List<UserTrack> findTracksOfUser(int userId) {
		return em.createQuery("select u from UserTrack u where u.user.id = :userId", UserTrack.class)
				.setParameter("userId", userId).getResultList();
	}
	
	@Override
	public int findListenedTrackCountOfUser(int userId) {
		return em.createQuery("select u from UserTrack u where u.user.id = :userId", UserTrack.class)
				.setParameter("userId", userId).getResultList().size();
	}

	@Override
	public UserTrack findRandomTrackOfUser(int userId) {
		String query = "SELECT u FROM UserTrack u where u.user.id = :userId ORDER BY RAND() ";
		Query q = em.createQuery(query);
		q.setParameter("userId", userId);
		q.setMaxResults(1);
		return (UserTrack) q.getSingleResult();
	}
	
}

