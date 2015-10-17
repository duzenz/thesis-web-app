package com.duzenz.recommender.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.duzenz.recommender.entities.UserArtist;

@Repository
@Transactional
public class UserArtistImpl implements UserArtistDao 
{
	@PersistenceContext
	private EntityManager em;
	
	@Override
	@Transactional(readOnly=true)
	public UserArtist findUserArtist(int userArtistId) {
		return em.find(UserArtist.class, userArtistId);
	}

	@Override
	public List<UserArtist> findArtistsOfUser(int userId) {
		return em.createQuery("select u from UserArtist u where u.user.id = :userId", UserArtist.class)
				.setParameter("userId", userId).getResultList();
	}
	
	@Override
	@Transactional(readOnly=true)
	public long findListenedArtistCountOfUser(int userId) {
		Query query = em.createQuery("select count(*) from UserArtist u where u.user.id = :userId");
		query.setParameter("userId", userId);
		long count = (long) query.getSingleResult();
		return count;
	}

}

