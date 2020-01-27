package jku.dke.prmetaservice.utils;

import jku.dke.prmetaservice.controller.IndexController;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.RDFNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class JenaUtils {
    private static final Logger log = LoggerFactory.getLogger(JenaUtils.class);

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

    public static List<List<String>> convertJenaResultSetToList(ResultSet results){
        log.info("Entered convertJenaResultSetToList()");
        List<String> varList = new ArrayList<String>();
        List<List<String>> resultList = new ArrayList<>();
        varList = results.getResultVars();
        while(results.hasNext()){
            QuerySolution solution = results.nextSolution();
            List<String> entry = new ArrayList<String>();
            varList.forEach(var -> {
                RDFNode a = solution.get(var);
                if(a.isURIResource()){
                    entry.add(a.asNode().getLocalName());
                }else if(a.isLiteral()){
                    entry.add(a.asLiteral().toString());
                }

            });
            resultList.add(entry);
        };
        log.info("convertJenaResultSetToList()");
        return resultList;
    }
}
