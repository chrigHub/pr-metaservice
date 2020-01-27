package jku.dke.prmetaservice.service.impl;

import jku.dke.prmetaservice.service.SparqlService;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.ResultSet;

public class SparqlServiceImpl implements SparqlService {

    @Override
    public ResultSet getAllTriples(String endpoint) {
        String query = "Select ?a ?b ?c where {?a ?b ?c} limit 10";
        QueryExecution queryExecution = QueryExecutionFactory.sparqlService(endpoint, query);
        return queryExecution.execSelect();
    }
/*
    @Override
    public List<String> query(URL url) {
        HttpURLConnection conn = null;
        String line;
        BufferedReader br = null;
        List<String> list = new LinkedList<>();

        try {
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            while ((line = br.readLine()) != null) {
                list.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(conn != null) {
                conn.disconnect();
            }
        }
        return list;
    }
    */

}
