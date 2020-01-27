package jku.dke.prmetaservice.controller;

import jku.dke.prmetaservice.entity.ConnectionInfo;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import org.slf4j.Logger;

import java.util.*;

@Controller
public class ConnectionListController {
    private static final Logger log = LoggerFactory.getLogger(IndexController.class);
    private Set<String> brandSet = new HashSet<>();
    private Set<String> modelSet = new HashSet<>();



    private String brand_choice;
    private String model_choice;

    @GetMapping("/connections")
    public String index(Model model){
        List<ConnectionInfo> connections = new ArrayList<>();
        connections.add(new ConnectionInfo("AudiService", "http://localhost:3030/audi/", "This service represents the car manufacturer Audi."));
        connections.add(new ConnectionInfo("GenericSupply", "http://localhost:3030/genericsupply/", "Generic Supply is a third party supplier for spare parts for vehicles of various manufacturers."));
        connections.add(new ConnectionInfo("Joe's Car Parts", "http://localhost:3030/joescarparts/", "Joe's Car Parts is a small local business specilized in repairing cars and trading car parts."));
        model.addAttribute("connections", connections);
        return "connections";
    }

}
