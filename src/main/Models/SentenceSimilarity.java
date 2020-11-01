package main.Models;

import java.util.HashSet;
import java.util.Set;

public class SentenceSimilarity {

    private String sentence;
    private String[] splittedSentence;
    private int numberOfWords;
    private Set<String> indexesOfSimilarity;


    public SentenceSimilarity(String sentence) {
        this.sentence = sentence;
        this.splittedSentence = sentence.split(" ");
        this.numberOfWords = splittedSentence.length;
        this.indexesOfSimilarity = new HashSet<>();
    }

    public String getSentence() {
        return sentence;
    }

    public int getNumberOfWords() {
        return numberOfWords;
    }

    public Set<String> getIndexesOfSimilarity() {
        return indexesOfSimilarity;
    }

    public String[] getSplittedSentence() {
        return splittedSentence;
    }

}
