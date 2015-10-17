package com.duzenz.recommender.web.controllers;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.ThresholdUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.LogLikelihoodSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.UserBasedRecommender;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.springframework.beans.factory.annotation.Autowired;

import com.duzenz.recommender.dao.UserTrackDao;
import com.duzenz.recommender.entities.UserTrack;

public class CollaborativeFiltering {
    
    @Autowired
    private UserTrackDao userTrackDao;
    
    public List <UserTrack> getUserBasedRecommends(int userId) {
        try {
            DataModel dm = new FileDataModel(new File("D:\\thesis\\recommenderApp\\data\\user_track.csv"));
            UserSimilarity similarity = new LogLikelihoodSimilarity(dm);
            UserNeighborhood neighborhood = new ThresholdUserNeighborhood(0.9, similarity, dm);
            UserBasedRecommender recommender = new GenericUserBasedRecommender(dm, neighborhood, similarity);
            List<RecommendedItem> recommendations = recommender.recommend(userId, 10);
            List <UserTrack> items = new ArrayList<UserTrack>();
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
    
    public List <UserTrack> getItemBasedRecommends(int trackId) {
        try {
            DataModel dm = new FileDataModel(new File("D:\\thesis\\recommenderApp\\data\\user_track.csv"));
            ItemSimilarity sim = new LogLikelihoodSimilarity(dm);
            GenericItemBasedRecommender recommender = new GenericItemBasedRecommender(dm, sim);
            List<RecommendedItem> recommendations = recommender.mostSimilarItems(trackId, 10);
            List <UserTrack> items = new ArrayList<UserTrack>();
            for (RecommendedItem recommendation: recommendations) {
                UserTrack selected = userTrackDao.findUserTrack((int) recommendation.getItemID());
                selected.setRecommendationValue(recommendation.getValue());
                items.add(selected);
            }
            return items;
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
