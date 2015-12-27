package com.duzenz.recommender.web.controllers;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.CachingUserNeighborhood;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
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
        // recommends.addAll(getCbrRecommendations(age, country, gender, tag,
        // register, Integer.parseInt(userId)));

        return recommends;
    }

    @RequestMapping(value = "/updateEngines", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public boolean updateEngines(@RequestBody List<Listening> listenings) {

        // load cbr model
        OntModel model = cbrRecommender.loadCbrModel(CbrRecommender.cbrDataFilePath);
        System.out.println("Cbr model loaded");

        // save to users listening history
        for (Listening listening : listenings) {

            listeningDao.insertListening(listening);

            Track track = trackDao.findTrack(listening.getTrack().getId());
            listening.setTrack(track);
            DataUser dUser = dataUserDao.find(listening.getUser().getId());
            listening.setUser(dUser);
            List<UserTrack> userTracks = userTrackDao.findUserTrackRow(dUser.getId(), track.getId());

            // cf operations
            if (userTracks.size() == 0) {
                UserTrack ut = new UserTrack();
                ut.setListenCount(1);
                ut.setUser(dUser);
                ut.setTrack(track);
                userTrackDao.insert(ut);
            } else {
                UserTrack ut = userTracks.get(0);
                ut.setListenCount(ut.getListenCount() + 1);
                userTrackDao.insert(ut);
            }

            // cbr operations
            Individual ind = model.getIndividual(CbrRecommender.namespace + "I" + track.getId());
            if (ind != null) {
                System.out.println("individual var");
                cbrRecommender.createIndividualForUser(model, listening);
            } else {
                System.out.println("individual yok");

            }
        }

        // remove user file and create again
        // util.deleteFile(cfDataFilePath);
        // userTrackDao.saveUserTracksAsCsv();

        // find tracks

        return true;
    }

    private List<UserTrack> getUserBasedRecommends(int userId) {
        List<UserTrack> recommends = new ArrayList<UserTrack>();
        try {
            DataModel dm = new FileDataModel(new File(cfDataFilePath));
            UserSimilarity similarity = new CachingUserSimilarity(new LogLikelihoodSimilarity(dm), dm);
            UserNeighborhood neighborhood = new CachingUserNeighborhood(new NearestNUserNeighborhood(10, similarity, dm), dm);// TODO
                                                                                                                              // constant
            UserBasedRecommender recommender = new GenericUserBasedRecommender(dm, neighborhood, similarity);
            List<RecommendedItem> recommendations = recommender.recommend(userId, 5);// TODO
                                                                                     // constant
            for (RecommendedItem recommendation : recommendations) {
                UserTrack selected = userTrackDao.findUserTrack((int) recommendation.getItemID());
                Track track = trackDao.findTrack((int) recommendation.getItemID());
                selected.setRecommendationValue(recommendation.getValue());
                selected.setRecommendationSource("user-based");
                selected.setArtistId(track.getArtistMbid());
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
}
