package jku.dke.prmetaservice.service.impl;

import jku.dke.prmetaservice.service.SparqlService;
import jku.dke.prmetaservice.utils.JenaUtils;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;

import java.util.List;

public class SparqlServiceImpl implements SparqlService {

    private String endpoint;

    @Override
    public List getAllTriples(String endpoint) {
        String query = "Select ?a ?b ?c where {?a ?b ?c} limit 10";
        QueryExecution queryExecution = QueryExecutionFactory.sparqlService(endpoint, query);
        return JenaUtils.convertJenaResultSetToList(queryExecution.execSelect());
    }

    @Override
    public List getBrandsFromAudi() {
        return null;
    }

    @Override
    public List getModelsFromAudi() {
        String query = "prefix audi: <http://www.jku.at/dke/praktikumdke/gruppe6/autohersteller1_audi#>\n" +
                "\n" +
                "SELECT ?model ?modelstring\n" +
                "WHERE {\n" +
                "  ?model a audi:Model.\n" +
                "  BIND(strafter(strafter(STR(?model), \"#\"), \"_\") as ?modelstring).\n" +
                "}";
        QueryExecution queryExecution = QueryExecutionFactory.sparqlService(this.endpoint+"audi/query", query);
        return JenaUtils.convertJenaResultSetToList(queryExecution.execSelect());
    }

    @Override
    public List getPartsForAudiModel(String model) {
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
        QueryExecution queryExecution = QueryExecutionFactory.sparqlService(this.endpoint+"audi/query", query);
        return JenaUtils.convertJenaResultSetToList(queryExecution.execSelect());
    }

    @Override
    public List getBrandsFromGs() {
        String query = "    prefix gs: <http://www.jku.at/dke/praktikumdke/gruppe6/ersatzteilhersteller1#>\n" +
                "\n" +
                "    SELECT ?brand\n" +
                "    WHERE {\n" +
                "  ?brand a gs:Brand\n" +
                "    }";
        QueryExecution queryExecution = QueryExecutionFactory.sparqlService(this.endpoint+"genericsupply/query", query);
        return JenaUtils.convertJenaResultSetToList(queryExecution.execSelect());
    }

    @Override
    public List getModelsForBrandFromGs(String brand) {
        String query = "prefix gs: <http://www.jku.at/dke/praktikumdke/gruppe6/ersatzteilhersteller1#>\n" +
                "\n" +
                "SELECT ?model\n" +
                "WHERE {\n" +
                "  ?model gs:hasBrand gs:" + brand + ".\n" +
                "  ?model a gs:Model.\n" +
                "}";
        QueryExecution queryExecution = QueryExecutionFactory.sparqlService(this.endpoint+"genericsupply/query", query);
        return JenaUtils.convertJenaResultSetToList(queryExecution.execSelect());
    }

    @Override
    public List getPartsForModelFromGs(String model) {
        String query = "prefix gs: <http://www.jku.at/dke/praktikumdke/gruppe6/ersatzteilhersteller1#>\n" +
                "\n" +
                "SELECT DISTINCT ?part ?price\n" +
                "WHERE {\n" +
                "  ?part a gs:Product.\n" +
                "  ?part gs:hasPrice ?price.\n" +
                "  ?part gs:fitsFor "+ model + ".\n" +
                "}";
        QueryExecution queryExecution = QueryExecutionFactory.sparqlService(this.endpoint+"genericsupply/query", query);
        return JenaUtils.convertJenaResultSetToList(queryExecution.execSelect());
    }

    @Override
    public List getBrandsFromJoe() {
        String query = "prefix jcp: <http://www.semanticweb.org/johannes/ontologies/2019/9/untitled-ontology-9#>\n" +
                "\n" +
                "SELECT DISTINCT ?brand\n" +
                "WHERE {\n" +
                "  ?brand <http://www.w3.org/2000/01/rdf-schema#subClassOf> jcp:car.\n" +
                "}";
        QueryExecution queryExecution = QueryExecutionFactory.sparqlService(this.endpoint+"joescarparts/query", query);
        return JenaUtils.convertJenaResultSetToList(queryExecution.execSelect());
    }

    @Override
    public List getModelsForBrandFromJoe(String brand) {
        String query = "prefix jcp: <http://www.semanticweb.org/johannes/ontologies/2019/9/untitled-ontology-9#>\n" +
                "\n" +
                "SELECT DISTINCT ?model\n" +
                "WHERE {\n" +
                "  ?model <http://www.w3.org/2000/01/rdf-schema#subClassOf> jcp:"+brand+".\n" +
                "}";
        QueryExecution queryExecution = QueryExecutionFactory.sparqlService(this.endpoint+"joescarparts/query", query);
        return JenaUtils.convertJenaResultSetToList(queryExecution.execSelect());
    }

    //Test
    @Override
    public List getPartsForModelFromJoe(String model) {
        String query = "prefix jcp: <http://www.semanticweb.org/johannes/ontologies/2019/9/untitled-ontology-9#>\n" +
                "\n" +
                "SELECT DISTINCT ?part ?price\n" +
                "WHERE {\n" +
                "  ?part jcp:belongsTo jcp:"+model+".\n" +
                "  ?part jcp:hasPrice ?price.\n" +
                "}";
        QueryExecution queryExecution = QueryExecutionFactory.sparqlService(this.endpoint+"joescarparts/query", query);
        return JenaUtils.convertJenaResultSetToList(queryExecution.execSelect());
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

}
