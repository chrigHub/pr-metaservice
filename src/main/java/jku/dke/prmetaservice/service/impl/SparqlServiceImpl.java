package jku.dke.prmetaservice.service.impl;

import jku.dke.prmetaservice.controller.IndexController;
import jku.dke.prmetaservice.service.SparqlService;
import jku.dke.prmetaservice.utils.JenaUtils;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.sparql.engine.http.QueryEngineHTTP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.LinkOption;
import java.util.ArrayList;
import java.util.List;

public class SparqlServiceImpl implements SparqlService {

    private static final Logger log = LoggerFactory.getLogger(SparqlServiceImpl.class);

    private String endpoint;

    @Override
    public List<List<String>> getAllTriples(String endpoint) {
        log.info("Formulating Query: getAllTriples");
        String query = "Select ?a ?b ?c where {?a ?b ?c}";
        QueryExecution queryExecution = QueryExecutionFactory.sparqlService(endpoint, query);
        return runQuery(queryExecution);
    }

    @Override
    public List<List<String>> getBrandsFromAudi() {
        List<List<String>> returnList = new ArrayList<>();
        List<String> audiList = new ArrayList<>();
        audiList.add("Audi");
        returnList.add(audiList);
        return returnList;
    }

    @Override
    public List<List<String>> getModelsFromAudi() {
        log.info("Formulating Query: getModelsFromAudi");
        String query = "prefix audi: <http://www.jku.at/dke/praktikumdke/gruppe6/autohersteller1_audi#>\n" +
                "\n" +
                "SELECT ?modelstring\n" +
                "WHERE {\n" +
                "  ?model a audi:Model.\n" +
                "  BIND(strafter(strafter(STR(?model), \"#\"), \"_\") as ?modelstring).\n" +
                "}";
        QueryExecution queryExecution = QueryExecutionFactory.sparqlService(this.endpoint+"audi/query", query);
        return runQuery(queryExecution);
    }

    @Override
    public List<List<String>> getPartsForAudiModel(String model) {
        log.info("Formulating Query: getPartsForAudiModel");
        String query = "prefix audi: <http://www.jku.at/dke/praktikumdke/gruppe6/autohersteller1_audi#>\n" +
                "prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                "\n" +
                "SELECT ?partstring ?price ?category\n" +
                "WHERE {\n" +
                "  ?part a ?category.\n" +
                "  ?part audi:hasListPrice ?price.\n" +
                "  ?category rdfs:subClassOf* audi:Part.\n" +
                "  BIND(strafter(strafter(STR(?part), \"#\"), \"_\") as ?partstring).\n" +
                "  audi:Audi_" + model + " audi:hasComponent ?part.\n" +
                "}";
        QueryExecution queryExecution = QueryExecutionFactory.sparqlService(this.endpoint+"audi/query", query);
        return runQuery(queryExecution);
    }

    @Override
    public List<List<String>> getBrandsFromGs() {
        log.info("Formulating Query: getBrandsFromGs");
        String query = "prefix gs: <http://www.jku.at/dke/praktikumdke/gruppe6/ersatzteilhersteller1#>\n" +
                "\n" +
                "    SELECT ?brand\n" +
                "    WHERE {\n" +
                "  ?brand a gs:Brand\n" +
                "    }";
        QueryExecution queryExecution = QueryExecutionFactory.sparqlService(this.endpoint+"genericsupply/query", query);
        return runQuery(queryExecution);
    }

    @Override
    public List<List<String>> getModelsForBrandFromGs(String brand) {
        log.info("Formulating Query: getModelsForBrandFromGs");
        String query = "prefix gs: <http://www.jku.at/dke/praktikumdke/gruppe6/ersatzteilhersteller1#>\n" +
                "\n" +
                "SELECT ?model\n" +
                "WHERE {\n" +
                "  ?model gs:hasBrand gs:" + brand + ".\n" +
                "  ?model a gs:Model.\n" +
                "}";
        QueryExecution queryExecution = QueryExecutionFactory.sparqlService(this.endpoint+"genericsupply/query", query);
        return runQuery(queryExecution);
    }

    @Override
    public List<List<String>> getPartsForModelFromGs(String model) {
        log.info("Formulating Query: getPartsForModelFromGs");
        String query = "prefix gs: <http://www.jku.at/dke/praktikumdke/gruppe6/ersatzteilhersteller1#>\n" +
                "\n" +
                "SELECT DISTINCT ?part ?price\n" +
                "WHERE {\n" +
                "  ?part a gs:Product.\n" +
                "  ?part gs:hasPrice ?price.\n" +
                "  ?part gs:fitsFor gs:"+ model+ ".\n" +
                "}";
        QueryExecution queryExecution = QueryExecutionFactory.sparqlService(this.endpoint+"genericsupply/query", query);
        return runQuery(queryExecution);
    }

    @Override
    public List<List<String>> getBrandsFromJoe() {
        log.info("Formulating Query: getBrandsFromJoe");
        String query = "prefix jcp: <http://www.semanticweb.org/johannes/ontologies/2019/9/untitled-ontology-9#>\n" +
                "\n" +
                "SELECT DISTINCT ?brand\n" +
                "WHERE {\n" +
                "  ?brand <http://www.w3.org/2000/01/rdf-schema#subClassOf> jcp:car.\n" +
                "}";
        QueryExecution queryExecution = QueryExecutionFactory.sparqlService(this.endpoint+"joescarparts/query", query);
        return runQuery(queryExecution);
    }

    @Override
    public List<List<String>> getModelsForBrandFromJoe(String brand) {
        log.info("Formulating Query: getModelsForBrandFromJoe");
        String query = "prefix jcp: <http://www.semanticweb.org/johannes/ontologies/2019/9/untitled-ontology-9#>\n" +
                "\n" +
                "SELECT DISTINCT ?model\n" +
                "WHERE {\n" +
                "  ?model <http://www.w3.org/2000/01/rdf-schema#subClassOf> jcp:"+brand+".\n" +
                "}";
        QueryExecution queryExecution = QueryExecutionFactory.sparqlService(this.endpoint+"joescarparts/query", query);
        return runQuery(queryExecution);
    }

    //Test
    @Override
    public List<List<String>> getPartsForModelFromJoe(String model) {
        log.info("Formulating Query: getPartsForModelFromJoe");
        String query = "prefix jcp: <http://www.semanticweb.org/johannes/ontologies/2019/9/untitled-ontology-9#>\n" +
                "\n" +
                "SELECT DISTINCT ?part ?price\n" +
                "WHERE {\n" +
                "  ?part jcp:belongsTo jcp:"+model+".\n" +
                "  ?part jcp:hasPrice ?price.\n" +
                "}";
        QueryExecution queryExecution = QueryExecutionFactory.sparqlService(this.endpoint+"joescarparts/query", query);
        return runQuery(queryExecution);
    }

    private static List<List<String>> runQuery(QueryExecution qe){
        ResultSet results = qe.execSelect();
        log.info("Query Engine opened");
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
        qe.close();
        log.info("Query Engine closed");
        return resultList;
    }



    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

}
