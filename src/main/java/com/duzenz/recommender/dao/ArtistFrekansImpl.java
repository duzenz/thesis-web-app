package com.duzenz.recommender.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.duzenz.recommender.entities.ArtistFrekans;

@Repository
@Transactional
public class ArtistFrekansImpl implements ArtistFrekansDao 
{
	@PersistenceContext
	private EntityManager em;
	
	@Override
	@Transactional(readOnly=true)
	public ArtistFrekans findArtistFrekans(int artistFrekansId) {
		return em.find(ArtistFrekans.class, artistFrekansId);
	}
	
	@Override
	@Transactional(readOnly=true)
	public ArtistFrekans findArtistFrekansWithArtistId(int artistId) {
		return em.createQuery("select a from ArtistFrekans a where a.artist.id = :artistId", ArtistFrekans.class)
				.setParameter("artistId" , artistId).getSingleResult();
	}

	@Override
	@Transactional(readOnly=true)
	public List<ArtistFrekans> findTopArtists(int artistCount) {
		return em.createQuery("select a from ArtistFrekans a order by a.listenCount desc", ArtistFrekans.class)
				.setMaxResults(artistCount).getResultList();
	}
	
	@Override
	public List<ArtistFrekans> filterArtitstWithListenCount(int minCount, int maxCount) {
		return em.createQuery("select a from ArtistFrekans a where a.listenCount >= :minCount and a.listenCount <= :maxCount order by a.listenCount asc", ArtistFrekans.class)
		.setParameter("minCount", minCount).setParameter("maxCount", maxCount).getResultList();
	}

	@Override
	public long getCountOfFilteredArtists(int minCount, int maxCount) {
		Query query = em.createQuery("select count(*) from ArtistFrekans t where t.listenCount >= :minCount and t.listenCount <= :maxCount");
		query.setParameter("minCount", minCount);
		query.setParameter("maxCount", maxCount);
		long count = (long) query.getSingleResult();
		return count;
	}
	
	
}

