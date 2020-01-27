package jku.dke.prmetaservice.service.impl;

import jku.dke.prmetaservice.entity.Result;
import jku.dke.prmetaservice.service.SparqlService;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.RDFNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class SparqlServiceImpl implements SparqlService {

    private static final Logger log = LoggerFactory.getLogger(SparqlServiceImpl.class);

    private String endpoint;

    public SparqlServiceImpl(String endpoint){
        this.endpoint = endpoint;
    }


    @Override
    public List<Result> getAllTriples(String datastore) {
        log.info("Formulating Query: getAllTriples from "+datastore);
        String query = "Select ?a ?b ?c where {?a ?b ?c}";
        QueryExecution queryExecution = QueryExecutionFactory.sparqlService(this.endpoint+datastore+"/query", query);
        return runQuery(queryExecution, datastore);
    }

    @Override
    public List<Result> getBrandsFromAudi() {
        List<Result> returnList = new ArrayList<>();
        Result result = new Result();
        result.setBrand("Audi");
        returnList.add(result);
        return returnList;
    }

    @Override
    public List<Result> getModelsFromAudi() {
        log.info("Formulating Query: getModelsFromAudi");
        String query = "prefix audi: <http://www.jku.at/dke/praktikumdke/gruppe6/autohersteller1_audi#>\n" +
                "\n" +
                "SELECT ?model\n" +
                "WHERE {\n" +
                "  ?modeluri a audi:Model.\n" +
                "  BIND(strafter(strafter(STR(?modeluri), \"#\"), \"_\") as ?model).\n" +
                "}";
        QueryExecution queryExecution = QueryExecutionFactory.sparqlService(this.endpoint+"audi/query", query);
        return runQuery(queryExecution, "audi");
    }

    @Override
    public List<Result> getPartsForAudiModel(String model) {
        log.info("Formulating Query: getPartsForAudiModel");
        String query = "prefix audi: <http://www.jku.at/dke/praktikumdke/gruppe6/autohersteller1_audi#>\n" +
                "prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                "\n" +
                "SELECT ?part ?price\n" +
                "WHERE {\n" +
                "  ?part a ?category.\n" +
                "  ?part audi:hasListPrice ?pricenr.\n" +
                "  Bind(STR(?pricenr) as ?price).\n" +
                "  ?category rdfs:subClassOf* audi:Part.\n" +
                "  audi:Audi_" + model + " audi:hasComponent ?part.\n" +
                "}";
        QueryExecution queryExecution = QueryExecutionFactory.sparqlService(this.endpoint+"audi/query", query);
        return runQuery(queryExecution, "audi");
    }

    @Override
    public List<Result> getBrandsFromGs() {
        log.info("Formulating Query: getBrandsFromGs");
        String query = "prefix gs: <http://www.jku.at/dke/praktikumdke/gruppe6/ersatzteilhersteller1#>\n" +
                "\n" +
                "    SELECT ?brand\n" +
                "    WHERE {\n" +
                "  ?brand a gs:Brand\n" +
                "    }";
        QueryExecution queryExecution = QueryExecutionFactory.sparqlService(this.endpoint+"genericsupply/query", query);
        return runQuery(queryExecution, "genericsupply");
    }

    @Override
    public List<Result> getModelsForBrandFromGs(String brand) {
        log.info("Formulating Query: getModelsForBrandFromGs");
        String query = "prefix gs: <http://www.jku.at/dke/praktikumdke/gruppe6/ersatzteilhersteller1#>\n" +
                "\n" +
                "SELECT ?model\n" +
                "WHERE {\n" +
                "  ?modeluri gs:hasBrand gs:" + brand + ".\n" +
                "  ?modeluri a gs:Model.\n" +
                "  BIND(strafter(strafter(STR(?modeluri), \"#\"), \"_\") as ?model).\n" +
                "}";
        QueryExecution queryExecution = QueryExecutionFactory.sparqlService(this.endpoint+"genericsupply/query", query);
        return runQuery(queryExecution, "genericsupply");
    }

    @Override
    public List<Result> getPartsForModelFromGs(String model) {
        log.info("Formulating Query: getPartsForModelFromGs");
        String query = "prefix gs: <http://www.jku.at/dke/praktikumdke/gruppe6/ersatzteilhersteller1#>\n" +
                "\n" +
                "SELECT DISTINCT ?part ?price\n" +
                "WHERE {\n" +
                "  ?part a gs:Product.\n" +
                "  ?part gs:hasPrice ?pricenr.\n" +
                "  Bind(STR(?pricenr) as ?price).\n" +
                "  ?part gs:fitsFor gs:"+ model+ ".\n" +
                "}";
        QueryExecution queryExecution = QueryExecutionFactory.sparqlService(this.endpoint+"genericsupply/query", query);
        return runQuery(queryExecution, "genericsupply");
    }

    @Override
    public List<Result> getBrandsFromJoe() {
        log.info("Formulating Query: getBrandsFromJoe");
        String query = "prefix jcp: <http://www.semanticweb.org/johannes/ontologies/2019/9/untitled-ontology-9#>\n" +
                "\n" +
                "SELECT DISTINCT ?brand\n" +
                "WHERE {\n" +
                "  ?brand <http://www.w3.org/2000/01/rdf-schema#subClassOf> jcp:car.\n" +
                "}";
        QueryExecution queryExecution = QueryExecutionFactory.sparqlService(this.endpoint+"joescarparts/query", query);
        return runQuery(queryExecution, "joescarparts");
    }

    @Override
    public List<Result> getModelsForBrandFromJoe(String brand) {
        log.info("Formulating Query: getModelsForBrandFromJoe");
        String query = "prefix jcp: <http://www.semanticweb.org/johannes/ontologies/2019/9/untitled-ontology-9#>\n" +
                "\n" +
                "SELECT DISTINCT ?model\n" +
                "WHERE {\n" +
                "  ?model <http://www.w3.org/2000/01/rdf-schema#subClassOf> jcp:"+brand+".\n" +
                "}";
        QueryExecution queryExecution = QueryExecutionFactory.sparqlService(this.endpoint+"joescarparts/query", query);
        return runQuery(queryExecution, "joescarparts");
    }

    //Test
    @Override
    public List<Result> getPartsForModelFromJoe(String model) {
        log.info("Formulating Query: getPartsForModelFromJoe");
        String query = "prefix jcp: <http://www.semanticweb.org/johannes/ontologies/2019/9/untitled-ontology-9#>\n" +
                "\n" +
                "SELECT DISTINCT ?part ?price\n" +
                "WHERE {\n" +
                "  ?part jcp:belongsTo jcp:"+model+".\n" +
                "  ?part jcp:hasPrice ?pricenr.\n" +
                "  Bind(strbefore(STR(?pricenr), \"e\") as ?price).\n" +
                "}";
        QueryExecution queryExecution = QueryExecutionFactory.sparqlService(this.endpoint+"joescarparts/query", query);
        return runQuery(queryExecution, "joescarparts");
    }

    private static List<Result> runQuery(QueryExecution qe, String dataset){
        ResultSet results = qe.execSelect();
        log.info("Query Engine opened for "+dataset);
        List<String> varList = new ArrayList<>();
        List<Result> resultList = new ArrayList<>();
        varList = results.getResultVars();
        while(results.hasNext()){
            QuerySolution solution = results.nextSolution();
            Result result = new Result();
            varList.forEach(var -> {
                RDFNode node = solution.get(var);
                if(node.isLiteral()){
                    result.map(node.asLiteral().toString(), var);
                }else if(node.isURIResource()){
                    result.map(node.asNode().getLocalName(),var);
                }
            });
            resultList.add(result);
        };
        qe.close();
        log.info("Query Engine closed for "+dataset);
        return resultList;
    }



    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

}
