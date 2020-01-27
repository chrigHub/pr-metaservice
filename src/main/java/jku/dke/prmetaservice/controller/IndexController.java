package jku.dke.prmetaservice.controller;

import jku.dke.prmetaservice.entity.SparqlTriple;
import jku.dke.prmetaservice.service.SparqlService;
import jku.dke.prmetaservice.service.impl.SparqlServiceImpl;
import jku.dke.prmetaservice.service.impl.SparqlUtil;
import jku.dke.prmetaservice.utils.JenaUtils;
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
    private JenaUtils util = new JenaUtils();
    private Set<String> brandSet = new HashSet<>();
    private Set<String> modelSet = new HashSet<>();
    private Set<List<String>> resultSet = new HashSet<>();
    private SparqlTriple triple = new SparqlTriple();
    private List<List<String>> resultList = new ArrayList<>();


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
        model.addAttribute("models", this.modelSet);
        model.addAttribute("model_choice", this.model_choice);
        log.info(this.brandSet.toString());
        log.info("Entered get index");
        return "index";
    }

    @PostMapping("/")
    public String querySubmit(@ModelAttribute SparqlTriple triple, Model model){
        String ds = "audi";
        String endpoint = "http://localhost:3030//"+ds+"/query";
        this.resultList = sparqlService.getAllTriples(endpoint);
        model.addAttribute("resultList", this.resultList);
        log.info(resultList.toString());
        return "index";
    }

    @GetMapping("/search")
    public String searchDB(Model model){
        this.resultList = null;
        this.resultSet = null;
        List<List<List<String>>> listsToCombine = new ArrayList<>();
        if(this.brand_choice == null){
            //TODO
            //Get all?
        }else if(this.brand_choice.equals("Audi")){
            listsToCombine.add(sparqlService.getModelsFromAudi());
            listsToCombine.add(sparqlService.getModelsForBrandFromJoe(this.brand_choice));
            listsToCombine.add(sparqlService.getModelsForBrandFromGs(this.brand_choice));
            this.resultSet = JenaUtils.combineResultListsToRows(listsToCombine);
        }else{
            listsToCombine.add(sparqlService.getModelsForBrandFromGs(this.brand_choice));
            listsToCombine.add(sparqlService.getModelsForBrandFromJoe(this.brand_choice));
            this.resultSet = JenaUtils.combineResultListsToRows(listsToCombine);
        }

        model.addAttribute("resultList", this.resultSet);
        return "index";
    }

    @PostMapping("/selectBrand")
    public String selectBrand (@RequestParam(name="brand") String brand, Model model){
        this.sparqlService.setEndpoint("http://localhost:3030//");
        this.brand_choice = brand;

        if (this.brand_choice != null) {
            this.sparqlService.getModelsFromAudi().forEach(row -> {
                row.forEach(entry -> {
                    this.modelSet.add(entry);
                });
            });
            this.sparqlService.getModelsForBrandFromGs(brand_choice).forEach(row -> {
                row.forEach(entry -> {
                    this.modelSet.add(entry);
                });
            });
            this.sparqlService.getModelsForBrandFromJoe(brand_choice).forEach(row -> {
                row.forEach(entry -> {
                    this.modelSet.add(entry);
                });
            });
        }
        model.addAttribute("sparql_triple", this.triple);
        model.addAttribute("brands", this.brandSet);
        model.addAttribute("brand_choice", this.brand_choice);
        model.addAttribute("models", this.modelSet);
        model.addAttribute("model_choice", this.model_choice);
        return "index";
    }

    @PostMapping("/selectModel")
    public String selectModel (@RequestParam(name="model") String carModel, Model model){
        this.model_choice = carModel;
        model.addAttribute("sparql_triple", this.triple);
        model.addAttribute("brands", this.brandSet);
        model.addAttribute("brand_choice", this.brand_choice);
        model.addAttribute("models", this.modelSet);
        model.addAttribute("model_choice", this.model_choice);
        return "index";
    }
}
