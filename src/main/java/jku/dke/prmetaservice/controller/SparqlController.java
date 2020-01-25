package jku.dke.prmetaservice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SparqlController {

    @GetMapping("/sparqlQuery")
    public String sparql(@RequestParam(name="sub", required = false, defaultValue = "") String sub, @RequestParam(name="pred", required = false, defaultValue = "") String pred, @RequestParam(name="obj", required = false, defaultValue = "")String obj){

        return "index";
    }
}
