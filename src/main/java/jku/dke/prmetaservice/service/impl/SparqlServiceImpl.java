package jku.dke.prmetaservice.service.impl;

import jku.dke.prmetaservice.entity.SparqlTriple;
import jku.dke.prmetaservice.service.SparqlService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

public class SparqlServiceImpl implements SparqlService {

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

    @Override
    public List<SparqlTriple> findSparepartByFilter(List<FilterPredicate> filters) {
        String queryString = new SparqlQueryBuilder.Builder().withFilter(filters).build();

        return performQeryAndExtractResultSet(queryString);
    }

}
