package jku.dke.prmetaservice.controller;

import jku.dke.prmetaservice.entity.SparqlTriple;
import jku.dke.prmetaservice.service.impl.SparqlServiceImpl;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
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
import java.util.*;

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
    public String querySubmit(@ModelAttribute SparqlTriple triple, Model model){
        String ds = "audi";
        String endpoint = "http://localhost:3030//"+ds+"/query";
        List<String> resultList = new ArrayList<String>();
        resultList = sparqlService.getAllTriples(endpoint);
        model.addAttribute("resultList", resultList);
        log.info(resultList.toString());
        return "index";
    }
}
