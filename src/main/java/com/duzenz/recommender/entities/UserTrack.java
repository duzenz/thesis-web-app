package com.duzenz.recommender.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "user_track")
public class UserTrack {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private DataUser user;

    @ManyToOne
    @JoinColumn(name = "track_id")
    private Track track;

    @Column(name = "listen_count")
    private int listenCount;

    @Transient
    private float recommendationValue;

    @Transient
    private String recommendationSource;

    @Transient
    private String artistId;

    @Override
    public String toString() {
        return "UserTrack [id=" + id + ", user=" + user + ", track=" + track + ", listenCount=" + listenCount + ", recommendationValue=" + recommendationValue + ", recommendationSource=" + recommendationSource + ", artistId=" + artistId + "]";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public DataUser getUser() {
        return user;
    }

    public void setUser(DataUser user) {
        this.user = user;
    }

    public Track getTrack() {
        return track;
    }

    public void setTrack(Track track) {
        this.track = track;
    }

    public int getListenCount() {
        return listenCount;
    }

    public void setListenCount(int listenCount) {
        this.listenCount = listenCount;
    }

    public float getRecommendationValue() {
        return recommendationValue;
    }

    public void setRecommendationValue(float recommendationValue) {
        this.recommendationValue = recommendationValue;
    }

    public String getRecommendationSource() {
        return recommendationSource;
    }

    public void setRecommendationSource(String recommendationSource) {
        this.recommendationSource = recommendationSource;
    }

    public String getArtistId() {
        return artistId;
    }

    public void setArtistId(String artistId) {
        this.artistId = artistId;
    }

}
