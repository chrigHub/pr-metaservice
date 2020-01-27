package jku.dke.prmetaservice.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class HelperUtils {
    private static final Logger log = LoggerFactory.getLogger(HelperUtils.class);

    public static Set<String> combineResultListsToEntries(List<List<List<String>>> resultLists){
        Set<String> combinedSet = new HashSet<>();
        resultLists.forEach(singleList -> {
            singleList.forEach(row -> {
                row.forEach(entry -> {
                    combinedSet.add(entry);
                });
            });
        });
        return combinedSet;
    }

    public static Set<List<String>> combineResultListsToRows(List<List<List<String>>> resultLists){
        Set<List<String>> combinedSet = new HashSet<>();
        resultLists.forEach(singleList -> {
            singleList.forEach(row -> {
                combinedSet.add(row);
            });
        });
        return combinedSet;
    }


}