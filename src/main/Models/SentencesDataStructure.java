package main.Models;

import main.Utils.Helper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SentencesDataStructure {

    private Map<Integer, List<SentenceSimilarity>> numberOfWordsToSentenceSimilarity;
    private Map<String, List<SentenceSimilarity>> keyGroupsToSentenceSimilarityList;

    public SentencesDataStructure() {
        keyGroupsToSentenceSimilarityList = new HashMap<>();
        numberOfWordsToSentenceSimilarity = new HashMap();
    }

    public Map<Integer, List<SentenceSimilarity>> getNumberOfWordsToSentenceSimilarity() {
        return numberOfWordsToSentenceSimilarity;
    }

    public Map<String, List<SentenceSimilarity>> getKeyGroupsToSentenceSimilarityList() {
        return keyGroupsToSentenceSimilarityList;
    }

    public void buildDataStructure(){
        Helper.loadDataStructure(this, "info.text");
    }
}
