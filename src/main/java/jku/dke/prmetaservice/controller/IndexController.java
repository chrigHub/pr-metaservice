package jku.dke.prmetaservice.controller;

import jku.dke.prmetaservice.entity.SparqlTriple;
import jku.dke.prmetaservice.service.impl.SparqlServiceImpl;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.ResultSet;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.slf4j.Logger;

import javax.xml.transform.Result;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Controller
public class IndexController {
    private static final Logger log = LoggerFactory.getLogger(IndexController.class);
    private SparqlServiceImpl sparqlService = new SparqlServiceImpl();


    @GetMapping("/")
    public String index(Model model){
        model.addAttribute("sparqlTriple", new SparqlTriple());
        log.info("Entered get index");
        return "index";
    }

    @PostMapping("/")
    public String querySubmit(@ModelAttribute SparqlTriple triple){
        String ds = "audi";
        String endpoint = "http://localhost:3030//"+ds+"/query";
        ResultSet results = sparqlService.getAllTriples(endpoint);
        log.info(results.toString());
        /*
        log.info("Subject: " + triple.getSubject());
        log.info("Predicate: " + triple.getPredicate());
        log.info("Object: " + triple.getObject());

        String endpoint = "http://localhost:303/ds/query";
        String query = "Select ?a ?b ? where {?a ?b ?c} limit 10";
        String format = "format=application/json";

        try{
            query = "?query="+ URLEncoder.encode(query, StandardCharsets.UTF_8.toString());
            URL url = new URL(endpoint + query + format);
            List<String> res = this.sparqlService.query(url);
            log.info(res.stream().reduce("", String::concat));
        } catch (UnsupportedEncodingException | MalformedURLException e) {
            e.printStackTrace();
        }
        */
        return "index";
    }
}
