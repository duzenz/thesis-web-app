package com.duzenz.recommender.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "track")
public class Track {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(nullable = false, unique = true, name = "track_id")
    private String trackId;

    @Column(name = "track_name")
    private String trackName;

    @Column(name = "duration")
    private String duration;

    @Column(name = "listener")
    private String listener;

    @Column(name = "play_count")
    private String playCount;

    @Column(name = "tags")
    private String tags;

    @Column(name = "track_url")
    private String trackUrl;

    @Column(name = "artist_name")
    private String artistName;

    @Column(name = "artist_mbid")
    private String artistMbid;

    @Column(name = "artist_url")
    private String artistUrl;

    @Column(name = "album_title")
    private String albumTitle;

    @Column(name = "album_mbid")
    private String albumMbid;

    @Column(name = "album_url")
    private String albumUrl;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTrackId() {
        return trackId;
    }

    public void setTrackId(String trackId) {
        this.trackId = trackId;
    }

    public String getTrackName() {
        return trackName;
    }

    public void setTrackName(String trackName) {
        this.trackName = trackName;
    }

    public String getArtistMbid() {
        return artistMbid;
    }

    public void setArtistMbid(String artistMbid) {
        this.artistMbid = artistMbid;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

}
