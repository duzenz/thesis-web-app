package com.duzenz.recommender.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.duzenz.recommender.entities.Track;

@Repository
@Transactional
public class TrackImpl implements TrackDao {
	@PersistenceContext
	private EntityManager em;

	@Override
	@Transactional(readOnly = true)
	public List<Track> findAll() {
		return em.createQuery("select t from Track t where t.isRequestedBlob = 0", Track.class).getResultList();
		        //.setMaxResults(10)
	}

	@Override
	@Transactional(readOnly = true)
	public Track findTrack(int trackId) {
		return em.find(Track.class, trackId);
	}

	@Override
	@Transactional(readOnly = true)
	public Track findTrackWithLastFmId(String trackId) {
		return em
				.createQuery(
						"select t from Track t where t.trackId = :trackId",
						Track.class).setParameter("trackId", trackId)
				.getSingleResult();
	}

	@Override
	@Transactional(readOnly = true)
	public long getTrackCount() {
		Query query = em.createQuery("select count(*) from Track t");
		long count = (long) query.getSingleResult();
		return count;
	}
	
	@Override
    @Transactional
    public Track updateTrack(Track track) {
        em.merge(track);
        return track;
    }

	@Override
	public List<Track> searchTrack(String label) {
		return em.createQuery("select t from Track t where t.trackName like :label", Track.class)
				.setParameter("label", "%" + label + "%")
				.setMaxResults(10)
				.getResultList();
	}
	
	
	

}
