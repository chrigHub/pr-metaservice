package jku.dke.prmetaservice.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RequestController {
    @RequestMapping("/")
    public String index() {
        return "Hello mate";
    }
}
