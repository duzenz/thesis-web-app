package com.duzenz.recommender.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.duzenz.recommender.entities.Listening;

@Repository
@Transactional
public class ListeningImpl implements ListeningDao {
    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional(readOnly = true)
    public Listening findListening(int id) {
        return em.find(Listening.class, id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Listening> findListeningOfTrack(int trackId) {
        return em.createQuery("select l from Listening l where l.track.id = :trackId", Listening.class).setParameter("trackId", trackId).getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    public long getAgeIntervalListenCount(int ageMin, int ageMax) {
        Query query = em.createQuery("select count(*) from Listening l where l.user.age >= :ageMin and l.user.age <= :ageMax and l.user.age != '' ");
        query.setParameter("ageMin", ageMin);
        query.setParameter("ageMax", ageMax);
        Long count = (Long) query.getSingleResult();
        return count;
    }

    @Override
    public long getUserCountWithListeningCount(long minListenCount, long maxListenCount) {
        Query query = em.createQuery("select l.user.id, count(l.track.id) as cnt from Listening l group by l.user.id having count(l.track.id) >= :minListenCount and count(l.track.id) <= :maxListenCount");
        query.setParameter("minListenCount", minListenCount);
        query.setParameter("maxListenCount", maxListenCount);
        @SuppressWarnings("unchecked")
        // TODO
        List<Object[]> resultList = query.getResultList();
        return resultList.size();
    }

    @Override
    public long getTrackListeningCountBetweenDates(Date minDate, Date maxDate) {
        Query query = em.createQuery("select count(*) from Listening l where l.time >= :minDate and l.time <= :maxDate");
        query.setParameter("minDate", minDate);
        query.setParameter("maxDate", maxDate);
        Long count = (Long) query.getSingleResult();
        return count;
    }

    @Override
    public long getListeningCountOfTrackBetweenDates(int trackId, Date minDate, Date maxDate) {
        Query query = em.createQuery("select count(*) from Listening l where l.time >= :minDate and l.time <= :maxDate and l.track.id= :trackId");
        query.setParameter("minDate", minDate);
        query.setParameter("maxDate", maxDate);
        query.setParameter("trackId", trackId);
        Long count = (Long) query.getSingleResult();
        return count;
    }

    @Override
    public List<Listening> getListeningOfTrackBetweenDates(int trackId, Date minDate, Date maxDate) {
        return em.createQuery("select l from Listening l where l.time >= :minDate and l.time <= :maxDate and l.track.id= :trackId", Listening.class).setParameter("minDate", minDate).setParameter("maxDate", maxDate).setParameter("trackId", trackId).getResultList();
    }

    @Override
    public long getListeningCountOfUserBetweenDates(int userId, Date minDate, Date maxDate) {
        Query query = em.createQuery("select count(*) from Listening l where l.user.id = :userId and l.time >= :minDate and l.time <= :maxDate");
        query.setParameter("minDate", minDate);
        query.setParameter("maxDate", maxDate);
        query.setParameter("userId", userId);
        Long count = (Long) query.getSingleResult();
        return count;
    }

    @Override
    public List<Listening> getListeningOfUserBetweenDates(int userId, Date minDate, Date maxDate) {
        return em.createQuery("select l from Listening l where l.user.id = :userId and l.time >= :minDate and l.time <= :maxDate", Listening.class).setParameter("minDate", minDate).setParameter("maxDate", maxDate).setParameter("userId", userId).getResultList();
    }

    @Override
    @Transactional
    public Listening insertListening(Listening listening) {
        em.persist(listening);
        return listening;
    }

}
