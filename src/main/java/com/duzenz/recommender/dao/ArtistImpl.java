package com.duzenz.recommender.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.duzenz.recommender.entities.Artist;

@Repository
@Transactional
public class ArtistImpl implements ArtistDao 
{
	@PersistenceContext
	private EntityManager em;
	
	@Override
	@Transactional(readOnly=true)
	public List<Artist> findAll() {
		return em.createQuery("select u from Artist u", Artist.class).getResultList();
	}
	
	@Override
	@Transactional(readOnly=true)
	public Artist findArtist(int artistId) {
		return em.find(Artist.class, artistId);
	}
	
	@Override
	@Transactional(readOnly=true)
	public Artist findArtistWithLastFmId(String artistId) {
		return em.createQuery("select u from Artist u where u.artistId = :artistId", Artist.class)
				.setParameter("artistId", artistId).getSingleResult();
	}
	
	@Override
	@Transactional(readOnly=true)
	public long getArtistCount() {
		Query query = em.createQuery("select count(*) from Artist a");
		long count = (long) query.getSingleResult();
		return count;
	}
	
	@Override
    public List<Artist> searchArtist(String label) {
        return em.createQuery("select a from Artist a where a.artistName like :label", Artist.class)
                .setParameter("label", "%" + label + "%")
                .setMaxResults(10)
                .getResultList();
    }

}

