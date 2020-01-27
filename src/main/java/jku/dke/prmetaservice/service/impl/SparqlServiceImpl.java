package jku.dke.prmetaservice.service.impl;

import jku.dke.prmetaservice.controller.IndexController;
import jku.dke.prmetaservice.service.SparqlService;
import jku.dke.prmetaservice.utils.JenaUtils;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
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
        log.info("GetAllTriples Start");
        String query = "Select ?a ?b ?c where {?a ?b ?c}";
        QueryExecution queryExecution = QueryExecutionFactory.sparqlService(endpoint, query);
        log.info("GetAllTriples End");
        return JenaUtils.convertJenaResultSetToList(queryExecution.execSelect());
    }

    @Override
    public List<List<String>> getBrandsFromAudi() {
        log.info("getBrandFromAudi Start");
        List<List<String>> returnList = new ArrayList<>();
        List<String> audiList = new ArrayList<>();
        audiList.add("Audi");
        returnList.add(audiList);
        log.info("getBrandFromAudi End");
        return returnList;
    }

    @Override
    public List<List<String>> getModelsFromAudi() {
        log.info("getModelsFromAudi Start");
        String query = "prefix audi: <http://www.jku.at/dke/praktikumdke/gruppe6/autohersteller1_audi#>\n" +
                "\n" +
                "SELECT ?modelstring\n" +
                "WHERE {\n" +
                "  ?model a audi:Model.\n" +
                "  BIND(strafter(strafter(STR(?model), \"#\"), \"_\") as ?modelstring).\n" +
                "}";
        QueryExecution queryExecution = QueryExecutionFactory.sparqlService(this.endpoint+"audi/query", query);
        log.info("getModelsFromAudi Ende");
        return JenaUtils.convertJenaResultSetToList(queryExecution.execSelect());
    }

    @Override
    public List<List<String>> getPartsForAudiModel(String model) {
        log.info("getPartsFromAudi Start");
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
        log.info("getPartsFromAudi End");
        return JenaUtils.convertJenaResultSetToList(queryExecution.execSelect());
    }

    @Override
    public List<List<String>> getBrandsFromGs() {
        log.info("getBrandsFromGs Start");
        String query = "prefix gs: <http://www.jku.at/dke/praktikumdke/gruppe6/ersatzteilhersteller1#>\n" +
                "\n" +
                "    SELECT ?brand\n" +
                "    WHERE {\n" +
                "  ?brand a gs:Brand\n" +
                "    }";
        QueryExecution queryExecution = QueryExecutionFactory.sparqlService(this.endpoint+"genericsupply/query", query);
        log.info("getBrandsFromGs End");
        return JenaUtils.convertJenaResultSetToList(queryExecution.execSelect());
    }

    @Override
    public List<List<String>> getModelsForBrandFromGs(String brand) {
        log.info("getModelsForBrandGS Start");
        String query = "prefix gs: <http://www.jku.at/dke/praktikumdke/gruppe6/ersatzteilhersteller1#>\n" +
                "\n" +
                "SELECT ?model\n" +
                "WHERE {\n" +
                "  ?model gs:hasBrand gs:" + brand + ".\n" +
                "  ?model a gs:Model.\n" +
                "}";
        QueryExecution queryExecution = QueryExecutionFactory.sparqlService(this.endpoint+"genericsupply/query", query);
        log.info("getModelsForBrandGS End");
        return JenaUtils.convertJenaResultSetToList(queryExecution.execSelect());
    }

    @Override
    public List<List<String>> getPartsForModelFromGs(String model) {
        log.info("getPartsForModelFromGs Start");
        String query = "prefix gs: <http://www.jku.at/dke/praktikumdke/gruppe6/ersatzteilhersteller1#>\n" +
                "\n" +
                "SELECT DISTINCT ?part ?price\n" +
                "WHERE {\n" +
                "  ?part a gs:Product.\n" +
                "  ?part gs:hasPrice ?price.\n" +
                "  ?part gs:fitsFor "+ model + ".\n" +
                "}";
        QueryExecution queryExecution = QueryExecutionFactory.sparqlService(this.endpoint+"genericsupply/query", query);
        log.info("getPartsForModelFromGs End");
        return JenaUtils.convertJenaResultSetToList(queryExecution.execSelect());
    }

    @Override
    public List<List<String>> getBrandsFromJoe() {
        log.info("getBrandsFromJoe Start");
        String query = "prefix jcp: <http://www.semanticweb.org/johannes/ontologies/2019/9/untitled-ontology-9#>\n" +
                "\n" +
                "SELECT DISTINCT ?brand\n" +
                "WHERE {\n" +
                "  ?brand <http://www.w3.org/2000/01/rdf-schema#subClassOf> jcp:car.\n" +
                "}";
        QueryExecution queryExecution = QueryExecutionFactory.sparqlService(this.endpoint+"joescarparts/query", query);
        log.info("getBrandsFromJoe End");
        return JenaUtils.convertJenaResultSetToList(queryExecution.execSelect());
    }

    @Override
    public List<List<String>> getModelsForBrandFromJoe(String brand) {
        log.info("getModelsForBrandFromJoe Start");
        String query = "prefix jcp: <http://www.semanticweb.org/johannes/ontologies/2019/9/untitled-ontology-9#>\n" +
                "\n" +
                "SELECT DISTINCT ?model\n" +
                "WHERE {\n" +
                "  ?model <http://www.w3.org/2000/01/rdf-schema#subClassOf> jcp:"+brand+".\n" +
                "}";
        QueryExecution queryExecution = QueryExecutionFactory.sparqlService(this.endpoint+"joescarparts/query", query);
        log.info("getModelsForBrandFromJoe End");
        return JenaUtils.convertJenaResultSetToList(queryExecution.execSelect());
    }

    //Test
    @Override
    public List<List<String>> getPartsForModelFromJoe(String model) {
        log.info("getPartsForModelFromJoe Start");
        String query = "prefix jcp: <http://www.semanticweb.org/johannes/ontologies/2019/9/untitled-ontology-9#>\n" +
                "\n" +
                "SELECT DISTINCT ?part ?price\n" +
                "WHERE {\n" +
                "  ?part jcp:belongsTo jcp:"+model+".\n" +
                "  ?part jcp:hasPrice ?price.\n" +
                "}";
        QueryExecution queryExecution = QueryExecutionFactory.sparqlService(this.endpoint+"joescarparts/query", query);
        log.info("getPartsForModelFromJoe End");
        return JenaUtils.convertJenaResultSetToList(queryExecution.execSelect());
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

}
