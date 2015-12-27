package com.duzenz.recommender.web.controllers;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.ThresholdUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.CityBlockSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.EuclideanDistanceSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.LogLikelihoodSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.SpearmanCorrelationSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.TanimotoCoefficientSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.UncenteredCosineSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.UserBasedRecommender;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.duzenz.recommender.dao.UserTrackDao;
import com.duzenz.recommender.entities.UserTrack;

@Controller
@RequestMapping("/rest/usertrack/")
public class UserTrackResource {

    @Autowired
    private UserTrackDao userTrackDao;

    @RequestMapping(value = "{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public UserTrack findUserTrack(@PathVariable("id") int userTrackId) {
        return userTrackDao.findUserTrack(userTrackId);
    }

    @RequestMapping(value = "/recommend/item/{itemId}/{itemCount}/{algorithmCode}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<UserTrack> trackItemRecommend(@PathVariable("itemId") int itemId, @PathVariable("itemCount") int itemCount, @PathVariable("algorithmCode") int algorithmCode) {
        try {
            DataModel dm = new FileDataModel(new File("D:\\thesis\\recommenderApp\\data\\user_track.csv"));
            ItemSimilarity sim = this.getItemSimilarityFunction(algorithmCode, dm);
            System.out.println(sim);
            GenericItemBasedRecommender recommender = new GenericItemBasedRecommender(dm, sim);
            List<RecommendedItem> recommendations = recommender.mostSimilarItems(itemId, itemCount);
            List<UserTrack> items = new ArrayList<UserTrack>();
            for (RecommendedItem recommendation : recommendations) {
                UserTrack selected = userTrackDao.findUserTrack((int) recommendation.getItemID());
                selected.setRecommendationValue(recommendation.getValue());
                items.add(selected);
            }
            return items;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @RequestMapping(value = "/recommend/user/{userId}/{itemCount}/{algorithmCode}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<UserTrack> trackUserRecommend(@PathVariable("userId") int userId, @PathVariable("itemCount") int itemCount, @PathVariable("algorithmCode") int algorithmCode) {
        try {
            DataModel dm = new FileDataModel(new File("D:\\thesis\\recommenderApp\\data\\user_track.csv"));
            UserSimilarity similarity = this.getUserSimilarityFunction(algorithmCode, dm);
            System.out.println(similarity);
            UserNeighborhood neighborhood = new ThresholdUserNeighborhood(1, similarity, dm);
            UserBasedRecommender recommender = new GenericUserBasedRecommender(dm, neighborhood, similarity);
            List<RecommendedItem> recommendations = recommender.recommend(userId, itemCount);
            List<UserTrack> items = new ArrayList<UserTrack>();
            for (RecommendedItem recommendation : recommendations) {
                UserTrack selected = userTrackDao.findUserTrack((int) recommendation.getItemID());
                selected.setRecommendationValue(recommendation.getValue());
                items.add(selected);
            }
            return items;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @RequestMapping(value = "/user/{userId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<UserTrack> getUserTracks(@PathVariable("userId") int userId) {
        try {
            return userTrackDao.findTracksOfUser(userId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @RequestMapping(value = "/user/trackCount/{userId}/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public int getUserTrackCount(@PathVariable("userId") int userId) {
        try {
            return userTrackDao.findListenedTrackCountOfUser(userId);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    private ItemSimilarity getItemSimilarityFunction(int algorithmCode, DataModel dm) {
        try {
            if (algorithmCode == 1) {
                return new LogLikelihoodSimilarity(dm);
            } else if (algorithmCode == 2) {
                return new TanimotoCoefficientSimilarity(dm);
            } else if (algorithmCode == 3) {
                return new CityBlockSimilarity(dm);
            } else if (algorithmCode == 4) {
                return new UncenteredCosineSimilarity(dm);
            } else if (algorithmCode == 5) {
                return new EuclideanDistanceSimilarity(dm);
            } else if (algorithmCode == 6) {
                return new PearsonCorrelationSimilarity(dm);
            } else {
                return new LogLikelihoodSimilarity(dm);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private UserSimilarity getUserSimilarityFunction(int algorithmCode, DataModel dm) {
        try {
            if (algorithmCode == 1) {
                return new LogLikelihoodSimilarity(dm);
            } else if (algorithmCode == 2) {
                return new TanimotoCoefficientSimilarity(dm);
            } else if (algorithmCode == 3) {
                return new CityBlockSimilarity(dm);
            } else if (algorithmCode == 4) {
                return new UncenteredCosineSimilarity(dm);
            } else if (algorithmCode == 5) {
                return new EuclideanDistanceSimilarity(dm);
            } else if (algorithmCode == 6) {
                return new PearsonCorrelationSimilarity(dm);
            } else if (algorithmCode == 7) {
                return new SpearmanCorrelationSimilarity(dm);
            } else {
                return new LogLikelihoodSimilarity(dm);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
