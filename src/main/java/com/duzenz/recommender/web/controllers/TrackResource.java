package com.duzenz.recommender.web.controllers;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.CachingUserNeighborhood;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericBooleanPrefUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.CachingUserSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.LogLikelihoodSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.UserBasedRecommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.duzenz.recommender.components.CbrRecommender;
import com.duzenz.recommender.components.Util;
import com.duzenz.recommender.dao.DataUserDao;
import com.duzenz.recommender.dao.ListeningDao;
import com.duzenz.recommender.dao.TrackDao;
import com.duzenz.recommender.dao.UserTrackDao;
import com.duzenz.recommender.entities.DataUser;
import com.duzenz.recommender.entities.Listening;
import com.duzenz.recommender.entities.Track;
import com.duzenz.recommender.entities.UserTrack;
import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.OntModel;

@Controller
@RequestMapping("/rest/track/")
public class TrackResource {

    public static String cfDataFilePath = "D:\\thesis\\recommenderApp\\data\\training_cf.csv";
    private static int neighborhoodThreshold = 25;
    private static int recommendationCount = 5;

    @Autowired
    private TrackDao trackDao;
    @Autowired
    private UserTrackDao userTrackDao;
    @Autowired
    private CbrRecommender cbrRecommender;
    @Autowired
    private ListeningDao listeningDao;
    @Autowired
    private DataUserDao dataUserDao;
    @Autowired
    private Util util;

    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<Track> findAll() {
        return trackDao.findAll();
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Track findTrack(@PathVariable("id") int trackId) {
        return trackDao.findTrack(trackId);
    }

    @RequestMapping(value = "lastfm/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Track findWithTrackId(@PathVariable("id") String trackId) {
        return trackDao.findTrackWithLastFmId(trackId);
    }

    @RequestMapping(value = "/getTrackCount", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public long getTrackCount() {
        return trackDao.getTrackCount();
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<Track> searchLabel(@RequestParam String query) {
        return trackDao.searchTrack(query);
    }

    @RequestMapping(value = "/recommendTrack", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<UserTrack> getUserBasedRecommendations(@RequestParam String userId, @RequestParam String age, @RequestParam String country, @RequestParam String gender, @RequestParam String register, @RequestParam String tag) {
        List<UserTrack> recommends = new ArrayList<UserTrack>();

        // get user based recommendations
        recommends.addAll(getUserBasedRecommends(Integer.parseInt(userId)));

        // get cbr recommendations
        List<UserTrack> cbrRecommends = getCbrRecommendations(age, country, gender, tag, register, Integer.parseInt(userId));
        
        //add cbr recommendations from recommends which are not same
        for (UserTrack cbrUserTrack : cbrRecommends) {
            if (!recommends.contains(cbrUserTrack)) {
                recommends.add(cbrUserTrack);
            }
        }
        
        Collections.shuffle(recommends);
        
        return recommends;
    }

    @RequestMapping(value = "/updateEngines", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public boolean updateEngines(@RequestBody List<Listening> listenings) {

        OntModel model = cbrRecommender.loadCbrModel(CbrRecommender.cbrDataFilePath);
        System.out.println("Cbr model loaded");

        // save to users listening history
        for (Listening listening : listenings) {

            Track track = trackDao.findTrack(listening.getTrack().getId());
            listening.setTrack(track);
            DataUser dUser = dataUserDao.find(listening.getUser().getId());
            listening.setUser(dUser);
            
            //insert new listening
            listeningDao.insertListening(listening);

            //update user listening tables
            updateUserTracks(listening);

            //cbr operations
            Individual ind = model.getIndividual(CbrRecommender.namespace + "I" + track.getId());
            if (ind != null) {
                cbrRecommender.updateIndividualForUser(model, listening);
            } else {
                cbrRecommender.setTag(track.getTags());
                cbrRecommender.createIndividualForUser(model, listening);
            }
        }
        
        cbrRecommender.saveIntances(model, CbrRecommender.cbrDataFilePath);
        
        // remove cf base file
        util.deleteFile(cfDataFilePath);
        userTrackDao.saveUserTracksAsCsv();

        model = null;
        return true;
    }

    private List<UserTrack> getUserBasedRecommends(int userId) {
        List<UserTrack> recommends = new ArrayList<UserTrack>();
        try {
            DataModel dm = new FileDataModel(new File(cfDataFilePath));
            UserSimilarity similarity = new CachingUserSimilarity(new LogLikelihoodSimilarity(dm), dm);
            UserNeighborhood neighborhood = new CachingUserNeighborhood(new NearestNUserNeighborhood(neighborhoodThreshold, similarity, dm), dm);
            UserBasedRecommender recommender = new GenericBooleanPrefUserBasedRecommender(dm, neighborhood, similarity);
            List<RecommendedItem> recommendations = recommender.recommend(userId, recommendationCount);
                                                                                     
            for (RecommendedItem recommendation : recommendations) {
                Track track = trackDao.findTrack((int) recommendation.getItemID());
                UserTrack selected = new UserTrack();
                selected.setId(track.getId());
                selected.setTrack(track);
                selected.setRecommendationValue(recommendation.getValue());
                selected.setRecommendationSource("user-based");
                recommends.add(selected);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.gc();
        return recommends;
    }

    private List<UserTrack> getCbrRecommendations(String age, String country, String gender, String tag, String register, int userId) {
        return cbrRecommender.getRecommendations(age, country, gender, register, tag, userId);
    }
    
    private void updateUserTracks(Listening listening) {
        List<UserTrack> userTracks = userTrackDao.findUserTrackRow(listening.getUser().getId(), listening.getTrack().getId());
        if (userTracks.size() == 0) {
            UserTrack ut = new UserTrack();
            ut.setListenCount(1);
            ut.setUser(listening.getUser());
            ut.setTrack(listening.getTrack());
            userTrackDao.insert(ut);
        } else {
            UserTrack ut = userTracks.get(0);
            ut.setListenCount(ut.getListenCount() + 1);
            userTrackDao.insert(ut);
        }
    }
}
