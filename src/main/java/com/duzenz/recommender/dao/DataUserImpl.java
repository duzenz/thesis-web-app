package com.duzenz.recommender.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.duzenz.recommender.entities.DataUser;

@Repository
@Transactional
public class DataUserImpl implements DataUserDao {
    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional(readOnly = true)
    public DataUser findRandom() {
        String query = "SELECT u FROM DataUser u where u.gender != '' and u.age != '' and u.country != '' ORDER BY RAND() ";
        Query q = em.createQuery(query);
        q.setMaxResults(1);
        return (DataUser) q.getSingleResult();
    }

    @Override
    @Transactional(readOnly = true)
    public DataUser find(int id) {
        return em.find(DataUser.class, id);
    }

    @Override
    @Transactional(readOnly = true)
    public DataUser findwithLastFmId(String lastFmId) {
        return em.createQuery("select d from DataUser d where d.userId = :lastfmId", DataUser.class).setParameter("lastfmId", lastFmId).getSingleResult();
    }

    @Override
    @Transactional(readOnly = true)
    public long getUserCount() {
        Query query = em.createQuery("select count(*) from DataUser d");
        long count = (long) query.getSingleResult();
        return count;
    }

    @Override
    @Transactional(readOnly = true)
    public long getUserCountBetweenAgeInterval(int ageMin, int ageMax) {
        Query query = em.createQuery("select count(*) from DataUser d where d.age >= :ageMin and d.age <= :ageMax");
        query.setParameter("ageMin", ageMin);
        query.setParameter("ageMax", ageMax);
        long count = (long) query.getSingleResult();
        return count;
    }

    @Override
    @Transactional
    public DataUser createLastFmUser(DataUser user) {
        em.persist(user);
        return user;
    }

}
