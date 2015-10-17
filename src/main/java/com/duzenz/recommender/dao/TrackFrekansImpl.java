package com.duzenz.recommender.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.duzenz.recommender.entities.TrackFrekans;

@Repository
@Transactional
public class TrackFrekansImpl implements TrackFrekansDao 
{
	@PersistenceContext
	private EntityManager em;
	
	@Override
	@Transactional(readOnly=true)
	public TrackFrekans findTrackFrekans(int trackFrekansId) {
		return em.find(TrackFrekans.class, trackFrekansId);
	}
	
	@Override
	@Transactional(readOnly=true)
	public TrackFrekans findTrackFrekansWithTrackId(int trackId) {
		return em.createQuery("select a from TrackFrekans a where a.track.id = :trackId", TrackFrekans.class)
				.setParameter("trackId" , trackId).getSingleResult();
	}

	@Override
	@Transactional(readOnly=true)
	public List<TrackFrekans> findTopTracks(int trackCount) {
		return em.createQuery("select a from TrackFrekans a order by a.listenCount desc", TrackFrekans.class)
				.setMaxResults(trackCount).getResultList();
	}

	@Override
	public List<TrackFrekans> filterTracksWithListenCount(int minCount, int maxCount) {
		return em.createQuery("select a from TrackFrekans a where a.listenCount >= :minCount and a.listenCount <= :maxCount order by a.listenCount asc", TrackFrekans.class)
		.setParameter("minCount", minCount).setParameter("maxCount", maxCount).getResultList();
	}

	@Override
	public long getCountOfFilteredTracks(int minCount, int maxCount) {
		Query query = em.createQuery("select count(*) from TrackFrekans t where t.listenCount >= :minCount and t.listenCount <= :maxCount");
		query.setParameter("minCount", minCount);
		query.setParameter("maxCount", maxCount);
		long count = (long) query.getSingleResult();
		return count;
	}
	
}

