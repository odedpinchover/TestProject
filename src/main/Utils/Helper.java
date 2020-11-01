package main.Utils;

import main.Models.SentenceSimilarity;
import main.Models.SentencesDataStructure;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Helper {


    public static void loadDataStructure(SentencesDataStructure sentencesDataStructure, String fileName) {
        try {
            ClassLoader classloader = Thread.currentThread().getContextClassLoader();
            InputStream inputStream = classloader.getResourceAsStream(fileName);
            InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            BufferedReader reader = new BufferedReader(streamReader);

            for (String sentence; (sentence = reader.readLine()) != null; ) {

                SentenceSimilarity sentenceSimilarity = new SentenceSimilarity(sentence);

                if (sizeGroupNotExist(sentencesDataStructure, sentenceSimilarity)) {
                    createAndAddNewSizeGroup(sentencesDataStructure, sentenceSimilarity);
                }
                else {
                    List<SentenceSimilarity> sentenceSimilaritiesGroup = getGroupSentenceSimilarities(sentencesDataStructure, sentenceSimilarity);
                    addNewSentenceSimilarityToGroup(sentencesDataStructure, sentenceSimilarity);
                    for (SentenceSimilarity sentenceSimilarityItem : sentenceSimilaritiesGroup) {
                        differenceOfTwoSentenceSimilarity(sentenceSimilarity, sentenceSimilarityItem, sentencesDataStructure);
                    }
                }
            }

            StringBuffer sbSentences = createOutput(sentencesDataStructure);
            writeOutputToScreen(sbSentences);
            writeOutputToFile(sbSentences);

            inputStream.close();
            streamReader.close();
            reader.close();
        } catch (IOException err) {

        }
    }

    private static StringBuffer createOutput(SentencesDataStructure sentencesDataStructure) {
        Map<String, List<SentenceSimilarity>> keyGroupsToSentenceSimilarityList = sentencesDataStructure.getKeyGroupsToSentenceSimilarityList();
        Set<String> keys = keyGroupsToSentenceSimilarityList.keySet();
        StringBuffer sbSentences = new StringBuffer();
        for (String key : keys) {
            StringBuffer sbChangeWords = new StringBuffer();
            sbChangeWords.append("The changing word was: ");
            List<SentenceSimilarity> sentenceSimilarities = keyGroupsToSentenceSimilarityList.get(key);
            String[] str = key.split("_");
            int index = Integer.valueOf(str[str.length - 1]);
            for (int i=0 ; i<sentenceSimilarities.size() ; i++ ) {
                sbSentences.append(sentenceSimilarities.get(i).getSentence());
                sbSentences.append(System.lineSeparator());
                sbChangeWords.append(sentenceSimilarities.get(i).getSplittedSentence()[index]);
                if(i<sentenceSimilarities.size()-1 ){
                    sbChangeWords.append(", ");
                }
            }
            sbChangeWords.append(System.lineSeparator());
            sbSentences.append(sbChangeWords);
        }
        return sbSentences;
    }

    private static void writeOutputToScreen(StringBuffer sbSentences) {
        System.out.print(sbSentences.toString());
    }

    private static void writeOutputToFile(StringBuffer sbSentences) {
        try {
            FileWriter myWriter = new FileWriter("filename.txt");
            myWriter.write(sbSentences.toString());
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    private static void addNewSentenceSimilarityToGroup(SentencesDataStructure sentencesDataStructure, SentenceSimilarity sentenceSimilarity) {
        sentencesDataStructure.getNumberOfWordsToSentenceSimilarity().get(sentenceSimilarity.getNumberOfWords()).add(sentenceSimilarity);
    }

    private static List<SentenceSimilarity> getGroupSentenceSimilarities(SentencesDataStructure sentencesDataStructure, SentenceSimilarity sentenceSimilarity) {
        return sentencesDataStructure.getNumberOfWordsToSentenceSimilarity().get(sentenceSimilarity.getNumberOfWords());
    }

    private static void createAndAddNewSizeGroup(SentencesDataStructure sentencesDataStructure, SentenceSimilarity sentenceSimilarity) {
        List<SentenceSimilarity> newSentenceSimilarity = new ArrayList();
        newSentenceSimilarity.add(sentenceSimilarity);
        sentencesDataStructure.getNumberOfWordsToSentenceSimilarity().put(sentenceSimilarity.getNumberOfWords(), newSentenceSimilarity);
    }

    private static boolean sizeGroupNotExist(SentencesDataStructure sentencesDataStructure, SentenceSimilarity sentenceSimilarity) {
        return !sentencesDataStructure.getNumberOfWordsToSentenceSimilarity().containsKey(sentenceSimilarity.getNumberOfWords());
    }


    private static void differenceOfTwoSentenceSimilarity(SentenceSimilarity sentenceSimilarity, SentenceSimilarity sentenceSimilarityItem, SentencesDataStructure sentencesDataStructure) {
        String[] split = sentenceSimilarity.getSplittedSentence();
        String[] splitItem = sentenceSimilarityItem.getSplittedSentence();

        int numOfChanges = 0;
        int indexOfChange = -1;

        for (int currentWordIndex = 2; currentWordIndex < split.length; currentWordIndex++) {
            if (!split[currentWordIndex].toLowerCase().equals(splitItem[currentWordIndex].toLowerCase())) {
                if (indexOfChange == -1) {
                    indexOfChange = currentWordIndex;
                }
                numOfChanges++;
            }
        }

        if (numOfChanges == 1) {
            String sentenceGroupKey = createSentenceGroupKey(sentenceSimilarity, split, indexOfChange);
            if (sentenceGroupKeyNotExist(sentenceSimilarityItem, sentenceGroupKey)) {
                createAndAddTwoSentencesToSentenceGroup(sentenceSimilarity, sentenceSimilarityItem, sentencesDataStructure, sentenceGroupKey);
            } else if (sentenceGroupKeyNotExist(sentenceSimilarity, sentenceGroupKey)) {
                addSentenceToSentenceGroup(sentenceSimilarity, sentencesDataStructure, sentenceGroupKey);
            }
        }
    }

    private static void addSentenceToSentenceGroup(SentenceSimilarity sentenceSimilarity, SentencesDataStructure sentencesDataStructure, String sentenceGroupKey) {
        sentenceSimilarity.getIndexesOfSimilarity().add(sentenceGroupKey);
        sentencesDataStructure.getKeyGroupsToSentenceSimilarityList().get(sentenceGroupKey).add(sentenceSimilarity);
    }

    private static void createAndAddTwoSentencesToSentenceGroup(SentenceSimilarity sentenceSimilarity, SentenceSimilarity sentenceSimilarityItem, SentencesDataStructure sentencesDataStructure, String sentenceGroupKey) {
        sentenceSimilarity.getIndexesOfSimilarity().add(sentenceGroupKey);
        sentenceSimilarityItem.getIndexesOfSimilarity().add(sentenceGroupKey);
        List<SentenceSimilarity> items = new ArrayList<>();
        items.add(sentenceSimilarity);
        items.add(sentenceSimilarityItem);
        sentencesDataStructure.getKeyGroupsToSentenceSimilarityList().put(sentenceGroupKey, items);
    }

    private static boolean sentenceGroupKeyNotExist(SentenceSimilarity sentenceSimilarityItem, String sentenceGroupKey) {
        return !sentenceSimilarityItem.getIndexesOfSimilarity().contains(sentenceGroupKey);
    }

    private static String createSentenceGroupKey(SentenceSimilarity sentenceSimilarity, String[] split, int indexOfChange) {
        StringBuffer sentenceKey = new StringBuffer();
        for (int wordIndex = 2; wordIndex < split.length; wordIndex++) {
            if (wordIndex != indexOfChange) {
                sentenceKey.append(split[wordIndex]);
                sentenceKey.append(" ");
            }
        }
        sentenceKey.append(sentenceSimilarity.getNumberOfWords()).append("_").append(indexOfChange);
        return sentenceKey.toString();
    }
}
