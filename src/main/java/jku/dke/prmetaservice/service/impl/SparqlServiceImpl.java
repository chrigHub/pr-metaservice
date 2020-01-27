package jku.dke.prmetaservice.service.impl;

import jku.dke.prmetaservice.service.SparqlService;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.ResultSet;

public class SparqlServiceImpl implements SparqlService {

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    private String endpoint;

    @Override
    public ResultSet getAllTriples(String endpoint) {
        String query = "Select ?a ?b ?c where {?a ?b ?c} limit 10";
        QueryExecution queryExecution = QueryExecutionFactory.sparqlService(endpoint, query);
        return queryExecution.execSelect();
    }

    @Override
    public ResultSet getBrandsFromAudi() {
        return null;
    }

    @Override
    public ResultSet getModelsFromAudi() {
        String query = "prefix audi: <http://www.jku.at/dke/praktikumdke/gruppe6/autohersteller1_audi#>\n" +
                "\n" +
                "SELECT ?model ?modelstring\n" +
                "WHERE {\n" +
                "  ?model a audi:Model.\n" +
                "  BIND(strafter(strafter(STR(?model), \"#\"), \"_\") as ?modelstring).\n" +
                "}";
        QueryExecution queryExecution = QueryExecutionFactory.sparqlService(this.endpoint+"/audi", query);
        return queryExecution.execSelect();
    }

    @Override
    public ResultSet getPartsForAudiModel(String model) {
        String query = "prefix audi: <http://www.jku.at/dke/praktikumdke/gruppe6/autohersteller1_audi#>\n" +
                "prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                "\n" +
                "SELECT ?part ?partstring ?category\n" +
                "WHERE {\n" +
                "  ?part a ?category.\n" +
                "  ?category rdfs:subClassOf* audi:Part.\n" +
                "  BIND(strafter(strafter(STR(?part), \"#\"), \"_\") as ?partstring).\n" +
                "  audi:Audi_" + model + " audi:hasComponent ?part.\n" +
                "}";
        QueryExecution queryExecution = QueryExecutionFactory.sparqlService(this.endpoint+"/audi", query);
        return queryExecution.execSelect();
    }

    @Override
    public ResultSet getBrandsFromGs() {
        String query = "    prefix gs: <http://www.jku.at/dke/praktikumdke/gruppe6/ersatzteilhersteller1#>\n" +
                "\n" +
                "    SELECT ?brand\n" +
                "    WHERE {\n" +
                "  ?brand a gs:Brand\n" +
                "    }";
        QueryExecution queryExecution = QueryExecutionFactory.sparqlService(this.endpoint+"/genericsupply", query);
        return queryExecution.execSelect();
    }

    @Override
    public ResultSet getModelsForBrandFromGs(String brand) {
        String query = "prefix gs: <http://www.jku.at/dke/praktikumdke/gruppe6/ersatzteilhersteller1#>\n" +
                "\n" +
                "SELECT ?model\n" +
                "WHERE {\n" +
                "  ?model gs:hasBrand gs:" + brand + ".\n" +
                "  ?model a gs:Model.\n" +
                "}";
        QueryExecution queryExecution = QueryExecutionFactory.sparqlService(this.endpoint+"/genericsupply", query);
        return queryExecution.execSelect();
    }

    @Override
    public ResultSet getPartsForModelFromGs(String model) {
        String query = "prefix gs: <http://www.jku.at/dke/praktikumdke/gruppe6/ersatzteilhersteller1#>\n" +
                "\n" +
                "SELECT DISTINCT ?part ?price\n" +
                "WHERE {\n" +
                "  ?part a gs:Product.\n" +
                "  ?part gs:hasPrice ?price.\n" +
                "  ?part gs:fitsFor "+ model + ".\n" +
                "}";
        QueryExecution queryExecution = QueryExecutionFactory.sparqlService(this.endpoint+"/genericsupply", query);
        return queryExecution.execSelect();
    }

    @Override
    public ResultSet getBrandsFromJoe() {
        String query = "prefix jcp: <http://www.semanticweb.org/johannes/ontologies/2019/9/untitled-ontology-9#>\n" +
                "\n" +
                "SELECT DISTINCT ?brand\n" +
                "WHERE {\n" +
                "  ?brand <http://www.w3.org/2000/01/rdf-schema#subClassOf> jcp:car.\n" +
                "}";
        QueryExecution queryExecution = QueryExecutionFactory.sparqlService(this.endpoint+"/joescarparts", query);
        return queryExecution.execSelect();
    }

    @Override
    public ResultSet getModelsForBrandFromJoe(String brand) {
        String query = "prefix jcp: <http://www.semanticweb.org/johannes/ontologies/2019/9/untitled-ontology-9#>\n" +
                "\n" +
                "SELECT DISTINCT ?model\n" +
                "WHERE {\n" +
                "  ?model <http://www.w3.org/2000/01/rdf-schema#subClassOf> jcp:"+brand+".\n" +
                "}";
        QueryExecution queryExecution = QueryExecutionFactory.sparqlService(this.endpoint+"/joescarparts", query);
        return queryExecution.execSelect();
    }

    @Override
    public ResultSet getPartsForModelFromJoe(String model) {
        String query = "prefix jcp: <http://www.semanticweb.org/johannes/ontologies/2019/9/untitled-ontology-9#>\n" +
                "\n" +
                "SELECT DISTINCT ?part ?price\n" +
                "WHERE {\n" +
                "  ?part jcp:belongsTo jcp:"+model+".\n" +
                "  ?part jcp:hasPrice ?price.\n" +
                "}";
        QueryExecution queryExecution = QueryExecutionFactory.sparqlService(this.endpoint+"/joescarparts", query);
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
