package com.duzenz.recommender.entities;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "lastfm")
public class Listening {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private DataUser user;

    @ManyToOne
    @JoinColumn(name = "track_id")
    private Track track;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(nullable = false)
    private Date time;

    @Transient
    private String recommendSource;

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

    public String getTime() {
        return (String) new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(time);
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getRecommendSource() {
        return recommendSource;
    }

    public void setRecommendSource(String recommendSource) {
        this.recommendSource = recommendSource;
    }

    @Override
    public String toString() {
        return "Listening [id=" + id + ", user=" + user + ", track=" + track + ", time=" + time + ", recommendSource=" + recommendSource + "]";
    }

}
