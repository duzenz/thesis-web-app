package com.duzenz.recommender.components;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.duzenz.recommender.dao.UserTrackDao;
import com.duzenz.recommender.entities.UserTrack;
import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.ObjectProperty;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;

@Component
public class CbrRecommender {
    
    @Autowired
    private UserTrackDao userTrackDao;

    final String OWL_FILE_URL = "D:\\thesis\\recommenderApp\\data\\test_cbr.owl";
    final String namespace = "http://www.example.com/ontologies/recommend.owl#";
    final String caseClassName = "RECOMMEND_CASE";
    public static boolean ASC = true;
    public static boolean DESC = false;

    private String age;
    private String country;
    private String gender;
    private String duration;
    private String selfview;
    private List<String> tag;
    private String release;
    private String artistId;

    OntModel model = null;
    OntClass caseClass = null;
    List<String> propertyList = new ArrayList<String>();
    List<String> instanceList = new ArrayList<String>();
    Map<String, Integer> distanceMap = new HashMap<String, Integer>();
    InputStream in = null;
    List <UserTrack> cbrRecommends = new ArrayList<UserTrack>();

    public List <UserTrack> getCbrRecommendations() {
        model = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
        try {
            in = new FileInputStream(OWL_FILE_URL);
        } catch (Exception e) {
            e.printStackTrace();
        }
        model.read(in, null);
        caseClass = (OntClass) model.getOntClass(namespace + caseClassName);
        setPropertyList(caseClass);
        setInstanceList(caseClass);
        getCaseBasedReasonings();
        return cbrRecommends;
    }

    public void setPropertyList(OntClass caseClass) {
        ExtendedIterator props = caseClass.listDeclaredProperties();
        while (props.hasNext()) {
            propertyList.add(props.next().toString());
        }
    }

    public void setInstanceList(OntClass caseClass) {
        ExtendedIterator instances = caseClass.listInstances();
        while (instances.hasNext()) {
            instanceList.add(instances.next().toString());
        }
    }

    public Individual createNewIndividual(OntClass caseClass) {
        Individual newIndividual = model.createIndividual(namespace + "I" + instanceList.size() + 1, caseClass);
        if (age.length() > 0) {
            newIndividual.addProperty(model.getProperty(namespace + "HAS-AGE"), model.createTypedLiteral(age));
        }
        if (country.length() > 0) {
            newIndividual.addProperty(model.getProperty(namespace + "HAS-COUNTRY"), model.createTypedLiteral(country));
        }
        if (gender.length() > 0) {
            newIndividual.addProperty(model.getProperty(namespace + "HAS-GENDER"), model.createTypedLiteral(gender));
        }
        if (selfview.length() > 0) {
            newIndividual.addProperty(model.getProperty(namespace + "HAS-SELF_VIEW"), model.createTypedLiteral(selfview));
        }
        if (duration.length() > 0) {
            newIndividual.addProperty(model.getProperty(namespace + "HAS-DURATION"), model.createTypedLiteral(duration));
        }
        if (artistId.length() > 0) {
            newIndividual.addProperty(model.getObjectProperty(namespace + "HAS-ARTIST"), model.createTypedLiteral(artistId));
        }
        if (release.length() > 0) {
            newIndividual.addProperty(model.getObjectProperty(namespace + "HAS-RELEASE_DATE"), model.createTypedLiteral(release));
        }
        if (tag.size() > 0) {
            for (String t : tag) {
                ObjectProperty tagProp = model.getObjectProperty(namespace + t.toString().replace("\"", "").replaceAll("\\s+", "_").replaceAll("#", ""));
                newIndividual.addProperty(tagProp, "true");
            }
        }

        return newIndividual;
    }

    public void getCaseBasedReasonings() {

        try {
            Individual newIndividual = createNewIndividual(caseClass);
            int counter = 0;
            for (String instance : instanceList) {
                Individual selectedInstance = model.getIndividual(instance);
                if (!selectedInstance.equals(namespace + "I" + instanceList.size() + 1)) {
                    counter++;
                    compareProperties(selectedInstance, newIndividual);
                }
                //System.out.println(counter);
                //if (counter > 10) {
                //    break;
                //}
            }

            //printDistances();
            cbrRecommends = getUserTracksOfRecommends();

            // TODO inference usage
            // TODO after save selected one

            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void compareProperties(Individual selectedIndividual, Individual newIndividual) {

        int value = 0;
        for (String prop : propertyList) {

            Property p = model.getOntProperty(prop);

            RDFNode selectedInstancePropVal = selectedIndividual.getPropertyValue(p);
            RDFNode newInstancePropVal = newIndividual.getPropertyValue(p);

            if (selectedInstancePropVal != null && newInstancePropVal != null && selectedInstancePropVal.equals(newInstancePropVal)) {
                // System.out.println(prop);
                // System.out.println(selectedInstancePropVal);
                // System.out.println(newInstancePropVal);
                String propName = p.getLocalName();
                if (propName.equals("HAS-ARTIST")) {
                    value += 5;
                } else if (propName.equals("HAS-AGE") || propName.equals("HAS-GENDER") || propName.equals("HAS-COUNTRY")) {
                    value += 1;
                } else {
                    value += 3;
                }
            }
        }
        distanceMap.put(selectedIndividual.getLocalName(), value);
    }

    public void printDistances() {
        //System.out.println("After sorting descendeng order......");
        Map<String, Integer> sortedMapDesc = sortByComparator(distanceMap, DESC);
        printMap(sortedMapDesc, 10);
    }
    
    
    
    public List <UserTrack> getUserTracksOfRecommends() {
        Map<String, Integer> sortedMapDesc = sortByComparator(distanceMap, DESC);
        List <UserTrack> recommends = new ArrayList<UserTrack>();
        int counter = 0;
        for (Map.Entry<String, Integer> entry : sortedMapDesc.entrySet()) {
            UserTrack selected = userTrackDao.findUserTrack(Integer.parseInt(entry.getKey().substring(1)));
            selected.setRecommendationSource("cbr");
            selected.setRecommendationValue(entry.getValue());
            recommends.add(selected);
            counter++;
            if (counter > 10) {
                break;
            }
        }
        return recommends;
    }

    public String getAge() {
        return age;
    }

    public void setAge(int age) {
        if (age == 0) {
            this.age = "";
        } else if (age <= 17) {
            this.age = "0-17";
        } else if (age > 17 && age <= 24) {
            this.age = "18-24";
        } else if (age > 24 && age <= 30) {
            this.age = "25-30";
        } else if (age > 30 && age <= 40) {
            this.age = "31-40";
        } else if (age > 40 && age <= 50) {
            this.age = "41-50";
        } else {
            this.age = "51-100";
        }
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        if (duration.equals("duration_short")) {
            this.duration = "short";
        } else if (duration.equals("duration_normal")) {
            this.duration = "normal";
        } else if (duration.equals("duration_long")) {
            this.duration = "long";
        } else {
            this.duration = "";
        }
    }

    public String getSelfview() {
        return selfview;
    }

    public void setSelfview(String selfview) {
        if (selfview.equals("selfview_few")) {
            this.selfview = "few";
        } else if (selfview.equals("selfview_average")) {
            this.selfview = "normal";
        } else if (selfview.equals("selview_many")) {
            this.selfview = "many";
        } else {
            this.selfview = "";
        }
    }

    public List<String> getTag() {
        return tag;
    }

    public void setTag(String tag) {
        List<String> items = Arrays.asList(tag.split("\\s*,\\s*"));
        this.tag = items;
    }

    public String getRelease() {
        return release;
    }

    public void setRelease(int release) {
        if (release == 0) {
            this.release = "";
        } else {
            this.release = "" + release;
        }
    }

    public String getArtistId() {
        return artistId;
    }

    public void setArtistId(int artistId) {
        if (artistId == 0) {
            this.artistId = "";
        } else {
            this.artistId = "" + artistId;
        }
    }

    @Override
    public String toString() {
        return "CbrRecommender [age=" + age + ", country=" + country + ", gender=" + gender + ", duration=" + duration + ", tag=" + tag + ", release=" + release + ", artistId=" + artistId + "]";
    }
    
    public Map<String, Integer> sortByComparator(Map<String, Integer> unsortMap, final boolean order) {

        List<Entry<String, Integer>> list = new LinkedList<Entry<String, Integer>>(unsortMap.entrySet());

        // Sorting the list based on values
        Collections.sort(list, new Comparator<Entry<String, Integer>>() {
            public int compare(Entry<String, Integer> o1, Entry<String, Integer> o2) {
                if (order) {
                    return o1.getValue().compareTo(o2.getValue());
                } else {
                    return o2.getValue().compareTo(o1.getValue());

                }
            }
        });

        // Maintaining insertion order with the help of LinkedList
        Map<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();
        for (Entry<String, Integer> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        return sortedMap;
    }

    public void printMap(Map<String, Integer> map, int limit) {
        int value = 0;
        for (Entry<String, Integer> entry : map.entrySet()) {
            value += 1;
            System.out.println("Key : " + entry.getKey() + " Value : " + entry.getValue());
            if (value == limit) {
                break;
            }
        }
    }

}
