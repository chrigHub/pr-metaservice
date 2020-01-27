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
        if(this.brandSet.isEmpty()){
            List<List<List<String>>> listsToCombine = new ArrayList<>();
            listsToCombine.add(this.sparqlService.getBrandsFromAudi());
            listsToCombine.add(this.sparqlService.getBrandsFromGs());
            listsToCombine.add(this.sparqlService.getBrandsFromJoe());
            this.brandSet = JenaUtils.combineResultListsToEntries(listsToCombine);
        }

        model.addAttribute("brands", this.brandSet);
        model.addAttribute("models", this.modelSet);
        model.addAttribute("brand_choice", this.brand_choice);
        model.addAttribute("model_choice", this.model_choice);
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
            listsToCombine.add(sparqlService.getPartsForAudiModel(this.model_choice));
            listsToCombine.add(sparqlService.getPartsForModelFromGs(brand_choice +"_" + this.model_choice));
            listsToCombine.add(sparqlService.getModelsForBrandFromJoe(this.model_choice));
            this.resultSet = JenaUtils.combineResultListsToRows(listsToCombine);
        }else{
            listsToCombine.add(sparqlService.getPartsForModelFromJoe(this.model_choice));
            listsToCombine.add(sparqlService.getPartsForModelFromGs(brand_choice + "_" + this.model_choice));
            this.resultSet = JenaUtils.combineResultListsToRows(listsToCombine);
        }

        model.addAttribute("resultList", this.resultSet);
        return "index";
    }

    @PostMapping("/selectBrand")
    public String selectBrand (@RequestParam(name="brand") String brand, Model model){
        this.brand_choice = brand;
        this.modelSet.clear();
        this.model_choice = null;
        List<List<List<String>>> listsToCombine = new ArrayList<>();
        if (this.modelSet.isEmpty()) {
            if(this.brand_choice.equals("Audi")){
                listsToCombine.add(sparqlService.getModelsFromAudi());
                listsToCombine.add(sparqlService.getModelsForBrandFromJoe(this.brand_choice));
                listsToCombine.add(sparqlService.getModelsForBrandFromGs(this.brand_choice));
            }else{
                listsToCombine.add(sparqlService.getModelsForBrandFromJoe(this.brand_choice));
                listsToCombine.add(sparqlService.getModelsForBrandFromGs(this.brand_choice));
            }
            this.modelSet = JenaUtils.combineResultListsToEntries(listsToCombine);
        }

        model.addAttribute("brands", this.brandSet);
        model.addAttribute("brand_choice", this.brand_choice);
        model.addAttribute("models", this.modelSet);
        model.addAttribute("model_choice", this.model_choice);
        return "index";
    }

    @PostMapping("/selectModel")
    public String selectModel (@RequestParam(name="model") String carModel, Model model){
        this.model_choice = carModel;
        model.addAttribute("brands", this.brandSet);
        model.addAttribute("brand_choice", this.brand_choice);
        model.addAttribute("models", this.modelSet);
        model.addAttribute("model_choice", this.model_choice);
        return "index";
    }
}
