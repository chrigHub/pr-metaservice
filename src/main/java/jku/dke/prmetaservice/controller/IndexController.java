package jku.dke.prmetaservice.controller;

import jku.dke.prmetaservice.entity.SparqlTriple;
import jku.dke.prmetaservice.service.impl.SparqlServiceImpl;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import org.slf4j.Logger;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

@Controller
public class IndexController {
    private static final Logger log = LoggerFactory.getLogger(IndexController.class);
    private SparqlServiceImpl sparqlService = new SparqlServiceImpl();
    private Set<String> brandSet = new HashSet<>();
    private Set<String> modelSet = new HashSet<>();
    private SparqlTriple triple = new SparqlTriple();


    private String brand_choice;
    private String model_choice;

    @GetMapping("/")
    public String index(Model model){
        this.sparqlService.setEndpoint("http://localhost:3030//");

        this.sparqlService.getBrandsFromAudi().forEach(row -> {
            row.forEach(entry -> {
                this.brandSet.add(entry);
            });
        });
        this.sparqlService.getBrandsFromGs().forEach(row -> {
            row.forEach(entry -> {
                this.brandSet.add(entry);
            });
        });
        this.sparqlService.getBrandsFromJoe().forEach(row -> {
            row.forEach(entry -> {
                this.brandSet.add(entry);
            });
        });

        model.addAttribute("sparql_triple", this.triple);
        model.addAttribute("brands", this.brandSet);
        model.addAttribute("brand_choice", this.brand_choice);
        model.addAttribute("model", this.modelSet);
        model.addAttribute("model_choice", this.model_choice);
        log.info(this.brandSet.toString());
        log.info("Entered get index");
        return "index";
    }

    @PostMapping("/")
    public String querySubmit(@ModelAttribute SparqlTriple triple, Model model){
        String ds = "audi";
        String endpoint = "http://localhost:3030//"+ds+"/query";
        List<List<String>> resultList = new ArrayList<>();
        resultList = sparqlService.getAllTriples(endpoint);
        model.addAttribute("resultList", resultList);
        log.info(resultList.toString());
        return "index";
    }

    @PostMapping("/selectBrand")
    public String selectBrand (@RequestParam(name="brand") String brand, Model model){
        this.brand_choice = brand;
        model.addAttribute("sparql_triple", this.triple);
        model.addAttribute("brands", this.brandSet);
        model.addAttribute("brand_choice", this.brand_choice);
        model.addAttribute("model", this.modelSet);
        model.addAttribute("model_choice", this.model_choice);
        return "index";
    }
}
