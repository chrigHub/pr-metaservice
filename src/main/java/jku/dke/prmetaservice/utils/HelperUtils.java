package jku.dke.prmetaservice.utils;

import jku.dke.prmetaservice.entity.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class HelperUtils {
    private static final Logger log = LoggerFactory.getLogger(HelperUtils.class);

    public static Set<String> combineResultListsForBrand(List<List<Result>> resultLists){
        Set<String> combinedSet = new HashSet<>();
        resultLists.forEach(singleList -> {
            singleList.forEach(result -> {
                combinedSet.add(result.getBrand());
            });
        });
        return combinedSet;
    }

    public static Set<String> combineResultListsForModel(List<List<Result>> resultLists){
        Set<String> combinedSet = new HashSet<>();
        resultLists.forEach(singleList -> {
            singleList.forEach(result -> {
                combinedSet.add(result.getModel());
            });
        });
        return combinedSet;
    }

    public static List<Result> combineResultListsToRows(List<List<Result>> resultLists){
        List<Result> combinedList = new ArrayList<>();
        resultLists.forEach(singleList -> {
            singleList.forEach(result -> {
                combinedList.add(result);
            });
        });
        return combinedList;
    }


}
