package com.duzenz.recommender.components;

import java.io.FileInputStream;
import java.io.FileWriter;
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
import com.duzenz.recommender.entities.Listening;
import com.duzenz.recommender.entities.UserTrack;
import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.ObjectProperty;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;

@Component
public class CbrRecommender {

    @Autowired
    private UserTrackDao userTrackDao;

    public static final String cbrDataFilePath = "D:\\thesis\\recommenderApp\\data\\training_cbr.owl";
    public static final String namespace = "http://www.example.com/ontologies/recommend.owl#";

    private String age;
    private String country;
    private String gender;
    private String register;
    private int userId;
    private List<String> tag;

    public ObjectProperty ageProp;
    public ObjectProperty registerProp;
    public ObjectProperty genderProp;
    public ObjectProperty countryProp;

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
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

    public List<String> getTag() {
        return tag;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setTag(String tag) {
        List<String> items = Arrays.asList(tag.split("\\s*,\\s*"));
        this.tag = items;
    }

    public String getRegister() {
        return register;
    }

    public void setRegister(String register) {
        this.register = register;
    }

    @Override
    public String toString() {
        return "CbrRecommender [userTrackDao=" + userTrackDao + ", age=" + age + ", country=" + country + ", gender=" + gender + ", register=" + register + ", tag=" + tag + "]";
    }

    public List<UserTrack> getRecommendations(String age, String country, String gender, String register, String tag, int userId) {
        setAge(age);
        setCountry(country);
        setGender(gender);
        setRegister(register);
        setTag(tag);
        setUserId(userId);
        OntModel model = loadCbrModel(cbrDataFilePath);
        OntClass caseObj = model.createClass(namespace + "RECOMMEND_CASE");
        List<Individual> modelInstances = getInstanceList(model, caseObj);
        Map<String, Integer> distanceMap = sortByComparator(compareInstanceWithModelInstances(model, modelInstances), false);

        int counter = 1;
        List<UserTrack> recommends = new ArrayList<UserTrack>();
        for (Map.Entry<String, Integer> entry : distanceMap.entrySet()) {
            UserTrack selected = userTrackDao.findUserTrack(Integer.parseInt(entry.getKey().substring(1)));
            selected.setRecommendationValue(entry.getValue());
            selected.setRecommendationSource("cbr-based");
            recommends.add(selected);
            if (counter == 5) {
                break;
            }
            counter++;
        }
        return recommends;
    }

    public void createIndividualForUser(OntModel model, Listening listening) {
        OntClass obj = model.createClass(namespace + "RECOMMEND_CASE");
        Individual instance = obj.createIndividual(namespace + "I" + listening.getTrack().getId());

        System.out.println(listening.getUser().getGender());
        System.out.println(listening.getUser().getCountry());
        System.out.println(listening.getUser().getAgeCol());
        System.out.println(listening.getUser().getRegisterCol());
        System.out.println(listening.getTrack().getTags());

        obj = null;

        // instance.addProperty(genderProp,
        // model.createTypedLiteral(listening.getUser().getGender()));
        // instance.addProperty(countryProp,
        // model.createTypedLiteral(listening.getUser().getCountry()));
        // instance.addProperty(ageProp,
        // model.createTypedLiteral(listening.getUser().getAgeCol()));
        // instance.addProperty(registerProp,
        // model.createTypedLiteral(listening.getUser().getRegisterCol()));

    }

    public OntModel loadCbrModel(String filePath) {
        OntModel model = null;
        try {
            model = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
            InputStream in = new FileInputStream(filePath);
            model.read(in, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        ageProp = model.getObjectProperty(namespace + "HAS-AGE");
        countryProp = model.getObjectProperty(namespace + "HAS-COUNTRY");
        genderProp = model.getObjectProperty(namespace + "HAS-GENDER");
        registerProp = model.getObjectProperty(namespace + "HAS-REGISTER");
        return model;
    }

    public void saveIntances(OntModel model, String filepath) {
        FileWriter out;
        try {
            out = new FileWriter(filepath);
            model.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Map<String, Integer> compareInstanceWithModelInstances(OntModel model, List<Individual> modelInstances) {
        Map<String, Integer> distanceMap = new HashMap<String, Integer>();
        for (Individual selectedIndividual : modelInstances) {
            int value = compareProperties(selectedIndividual);
            distanceMap.put(selectedIndividual.getLocalName(), value);
        }
        return distanceMap;
    }

    public int compareProperties(Individual selectedIndividual) {
        int value = 0;

        StmtIterator comparedProps = selectedIndividual.listProperties(ageProp);

        while (comparedProps.hasNext()) {
            Statement comparedStatement = (Statement) comparedProps.next();
            Object comparedObj = comparedStatement.getLiteral().getValue();
            if (getAge().length() > 0 && getAge().equals(comparedObj.toString())) {
                value += 2;
            }
        }

        comparedProps = selectedIndividual.listProperties(genderProp);
        while (comparedProps.hasNext()) {
            Statement comparedStatement = (Statement) comparedProps.next();
            Object comparedObj = comparedStatement.getLiteral().getValue();
            if (getGender().length() > 0 && getGender().equals(comparedObj.toString())) {
                value += 2;
            }
        }

        comparedProps = selectedIndividual.listProperties(countryProp);
        while (comparedProps.hasNext()) {
            Statement comparedStatement = (Statement) comparedProps.next();
            Object comparedObj = comparedStatement.getLiteral().getValue();
            if (getCountry().length() > 0 && getCountry().equals(comparedObj.toString())) {
                value += 2;
            }
        }

        comparedProps = selectedIndividual.listProperties(registerProp);
        while (comparedProps.hasNext()) {
            Statement comparedStatement = (Statement) comparedProps.next();
            Object comparedObj = comparedStatement.getLiteral().getValue();
            if (getRegister().length() > 0 && getRegister().equals(comparedObj.toString())) {
                value += 2;
            }
        }

        comparedProps = null;

        StmtIterator testedProps = selectedIndividual.listProperties();
        while (testedProps.hasNext()) {
            Statement testedStatement = (Statement) testedProps.next();
            String localName = testedStatement.getPredicate().getLocalName();
            if (!localName.equals("HAS-AGE") && !localName.equals("HAS-COUNTRY") && !localName.equals("HAS-REGISTER") && !localName.equals("HAS-ARTIST") && !localName.equals("HAS-SELF_VIEW") && !localName.equals("HAS-PLAY_COUNT") && !localName.equals("HAS-LISTENER") && !localName.equals("HAS-DURATION") && !localName.equals("type") && !localName.equals("HAS-GENDER")) {
                for (String tag : getTag()) {
                    if (localName.equals(tag)) {
                        value += 1;
                        break;
                    }
                }
            }
        }
        testedProps = null;
        return value;
    }

    public List<Individual> getInstanceList(OntModel model, OntClass caseObj) {
        ExtendedIterator instances = caseObj.listInstances();
        List<Individual> individualList = new ArrayList<Individual>();
        while (instances.hasNext()) {
            individualList.add(model.getIndividual(instances.next().toString()));
        }
        System.out.println("instance list size: " + individualList.size());
        return individualList;
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

}
