package jku.dke.prmetaservice.controller;

import jku.dke.prmetaservice.entity.Result;
import jku.dke.prmetaservice.service.impl.SparqlServiceImpl;
import jku.dke.prmetaservice.utils.HelperUtils;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import org.slf4j.Logger;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

@Controller
public class IndexController {
    private static final String ENDPOINT = "http://localhost:3030//";
    private static final Logger log = LoggerFactory.getLogger(IndexController.class);
    private SparqlServiceImpl sparqlService = new SparqlServiceImpl(ENDPOINT);
    private HelperUtils util = new HelperUtils();
    private Set<String> brandSet = new HashSet<>();
    private Set<String> modelSet = new HashSet<>();
    private Set<Result> resultSet = new HashSet<>();
    private List<Result> resultList = new ArrayList<>();


    private String brand_choice;
    private String model_choice;

    @GetMapping("/")
    public String index(Model model){
        this.sparqlService.setEndpoint("http://localhost:3030//");
        if(this.brandSet.isEmpty()){
            List<List<Result>> listsToCombine = new ArrayList<>();
            listsToCombine.add(this.sparqlService.getBrandsFromAudi());
            listsToCombine.add(this.sparqlService.getBrandsFromGs());
            listsToCombine.add(this.sparqlService.getBrandsFromJoe());
            this.brandSet = HelperUtils.combineResultListsForBrand(listsToCombine);
        }

        this.updateModel(model);
        return "index";
    }

    @GetMapping("test")
    public String testConnection(Model model){
        this.resultSet.clear();
        List<List<Result>> listsToCombine = new ArrayList<>();
        listsToCombine.add(sparqlService.getAllTriples("audi"));
        listsToCombine.add(sparqlService.getAllTriples("genericsupply"));
        listsToCombine.add(sparqlService.getAllTriples("joescarparts"));
        this.resultSet = HelperUtils.combineResultListsToRows(listsToCombine);
        this.updateModel(model);
        return "index";
    }

    @GetMapping("/search")
    public String searchDB(Model model){
        this.resultList.clear();
        this.resultSet.clear();
        List<List<Result>> listsToCombine = new ArrayList<>();
        if(this.brand_choice == null || this.model_choice == null){
            //TODO
            //Get all?
        }else if(this.brand_choice.equals("Audi")){
            listsToCombine.add(sparqlService.getPartsForAudiModel(this.model_choice));
            listsToCombine.add(sparqlService.getPartsForModelFromGs(brand_choice +"_" + this.model_choice));
            listsToCombine.add(sparqlService.getPartsForModelFromJoe(this.model_choice));
            this.resultSet = HelperUtils.combineResultListsToRows(listsToCombine);
        }else{
            listsToCombine.add(sparqlService.getPartsForModelFromJoe(this.model_choice));
            listsToCombine.add(sparqlService.getPartsForModelFromGs(brand_choice + "_" + this.model_choice));
            this.resultSet = HelperUtils.combineResultListsToRows(listsToCombine);
        }

        this.updateModel(model);
        return "index";
    }

    @PostMapping("/selectBrand")
    public String selectBrand (@RequestParam(name="brand") String brand, Model model){
        this.brand_choice = brand;
        this.modelSet.clear();
        this.model_choice = null;
        List<List<Result>> listsToCombine = new ArrayList<>();
        if (this.modelSet.isEmpty()) {
            if(this.brand_choice.equals("Audi")){
                listsToCombine.add(sparqlService.getModelsFromAudi());
                listsToCombine.add(sparqlService.getModelsForBrandFromJoe(this.brand_choice));
                listsToCombine.add(sparqlService.getModelsForBrandFromGs(this.brand_choice));
            }else{
                listsToCombine.add(sparqlService.getModelsForBrandFromJoe(this.brand_choice));
                listsToCombine.add(sparqlService.getModelsForBrandFromGs(this.brand_choice));
            }
            this.modelSet = HelperUtils.combineResultListsForModel(listsToCombine);
        }
        log.info("Selected Brand: "+this.brand_choice);
        this.updateModel(model);
        return "index";
    }

    @PostMapping("/selectModel")
    public String selectModel (@RequestParam(name="model") String carModel, Model model){
        this.model_choice = carModel;
        log.info("Selected Model: "+this.model_choice);
        this.updateModel(model);
        return "index";
    }

    private void updateModel(Model model){
        model.addAttribute("brands", this.brandSet);
        model.addAttribute("brand_choice", this.brand_choice);
        model.addAttribute("models", this.modelSet);
        model.addAttribute("model_choice", this.model_choice);
        model.addAttribute("resultList", this.resultSet);
    }

}
