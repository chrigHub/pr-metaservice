package jku.dke.prmetaservice.utils;

import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.RDFNode;

import java.util.ArrayList;
import java.util.List;

public class JenaUtils {

    public static List convertJenaResultSetToList(ResultSet results){
        List<String> varList = new ArrayList<String>();
        List<List> resultList = new ArrayList<List>();
        varList = results.getResultVars();
        while(results.hasNext()){
            QuerySolution solution = results.nextSolution();
            List<String> entry = new ArrayList<String>();
            varList.forEach(var -> {
                RDFNode a = solution.get(var);
                entry.add(a.asNode().getLocalName());
            });
            resultList.add(entry);
        };
        return resultList;
    }
}
