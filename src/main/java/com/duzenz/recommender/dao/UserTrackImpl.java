package com.duzenz.recommender.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.jdbc.Work;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.duzenz.recommender.entities.UserTrack;

@Repository
@Transactional
public class UserTrackImpl implements UserTrackDao {
    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional(readOnly = true)
    public UserTrack findUserTrack(int userTrackId) {
        return em.find(UserTrack.class, userTrackId);
    }

    @Override
    public List<UserTrack> findTracksOfUser(int userId) {
        return em.createQuery("select u from UserTrack u where u.user.id = :userId", UserTrack.class).setParameter("userId", userId).getResultList();
    }

    @Override
    public int findListenedTrackCountOfUser(int userId) {
        return em.createQuery("select u from UserTrack u where u.user.id = :userId", UserTrack.class).setParameter("userId", userId).getResultList().size();
    }

    @Override
    public UserTrack findRandomTrackOfUser(int userId) {
        String query = "SELECT u FROM UserTrack u where u.user.id = :userId ORDER BY RAND() ";
        Query q = em.createQuery(query);
        q.setParameter("userId", userId);
        q.setMaxResults(1);
        return (UserTrack) q.getSingleResult();
    }

    @Override
    public List<UserTrack> findUserTrackRow(int userId, int trackId) {
        return em.createQuery("SELECT u FROM UserTrack u where u.user.id = :userId and u.track.id = :trackId", UserTrack.class).setParameter("userId", userId).setParameter("trackId", trackId).getResultList();
    }

    @Override
    @Transactional
    public UserTrack insert(UserTrack userTrack) {
        em.persist(userTrack);
        return userTrack;
    }

    /**
     * TODO make query constant
     */
    @Override
    public void saveUserTracksAsCsv() {
        final String query = "SELECT user_id,track_id,listen_count INTO OUTFILE 'D:/thesis/recommenderApp/data/training_cf.csv' FIELDS TERMINATED BY ',' FROM user_track order by user_id,track_id,listen_count";

        Session session = (Session) em.getDelegate();
        session.doWork(new Work() {
            @Override
            public void execute(Connection connection) throws SQLException {
                PreparedStatement pStmt = null;
                try {
                    pStmt = connection.prepareStatement(query);
                    pStmt.execute();
                    System.out.println("Csv file created");
                } finally {
                    if (pStmt != null) {
                        pStmt.close();
                    }
                }
            }
        });
    }
}
