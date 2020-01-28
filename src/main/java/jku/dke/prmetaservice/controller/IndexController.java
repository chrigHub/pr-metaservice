package jku.dke.prmetaservice.controller;

import com.github.andrewoma.dexx.collection.internal.redblack.Tree;
import jku.dke.prmetaservice.entity.Order;
import jku.dke.prmetaservice.entity.Result;
import jku.dke.prmetaservice.service.impl.SparqlServiceImpl;
import jku.dke.prmetaservice.utils.HelperUtils;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import org.slf4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

@Controller
public class IndexController {
    private static final String ENDPOINT = "http://localhost:3030//";
    private static final Logger log = LoggerFactory.getLogger(IndexController.class);
    private SparqlServiceImpl sparqlService = new SparqlServiceImpl(ENDPOINT);
    private HelperUtils util = new HelperUtils();
    private Set<String> brandSet = new HashSet<>();
    private Set<String> modelSet = new HashSet<>();
    private Set<Result> resultSet = new TreeSet<>();
    private List<Result> resultList = new ArrayList<>();
    private List<Order> orders = new ArrayList<>();

    private String brand_choice;
    private String model_choice;
    private Integer min_filter;
    private Integer max_filter;

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



    @GetMapping("/search")
    public String searchDB(Model model){
        this.resultList.clear();
        this.resultSet.clear();
        int minFilter; int maxFilter;
        if (this.min_filter == null) minFilter = 0;
        else minFilter = this.min_filter;
        if (this.max_filter == null) maxFilter = Integer.MAX_VALUE;
        else maxFilter = this.max_filter;

        List<List<Result>> listsToCombine = new ArrayList<>();
        if(this.brand_choice == null || this.model_choice == null){
            log.info("No brand and model entered. No effect");
        }
        else if(this.brand_choice.equals("Audi")){
            listsToCombine.add(sparqlService.getPartsForAudiModel(this.model_choice, minFilter, maxFilter));
            listsToCombine.add(sparqlService.getPartsForModelFromGs(brand_choice +"_" + this.model_choice, minFilter, maxFilter));
            listsToCombine.add(sparqlService.getPartsForModelFromJoe(this.model_choice, minFilter, maxFilter));
            this.resultList = HelperUtils.combineResultListsToRows(listsToCombine);
            this.resultList.forEach(result -> {
                this.resultSet.add(result);
            });
        }else{
            listsToCombine.add(sparqlService.getPartsForModelFromGs(brand_choice +"_" + this.model_choice, minFilter, maxFilter));
            listsToCombine.add(sparqlService.getPartsForModelFromJoe(this.model_choice, minFilter, maxFilter));
            this.resultList = HelperUtils.combineResultListsToRows(listsToCombine);
            this.resultList.forEach(result -> {
                this.resultSet.add(result);
            });
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

    @PostMapping("/selectMinFilter")
    public String selectMinFilter(@RequestParam(name="minFilter") Integer minFilter, Model model){
        if(minFilter == null) {
            updateModel(model);
            return "index";
        }
        if(this.max_filter == null || minFilter < max_filter){
            this.min_filter = minFilter;
            log.info("Selected min-price: "+this.min_filter);
        }
        updateModel(model);
        return "index";
    }

    @PostMapping("/selectMaxFilter")
    public String selectMaxFilter(@RequestParam(name="maxFilter") Integer maxFilter, Model model){
        if (maxFilter == null){
            updateModel(model);
            return "index";
        }
        if(this.min_filter == null || maxFilter > this.min_filter){
            this.max_filter = maxFilter;
            log.info("Selected max-price: "+this.max_filter);
        }
        updateModel(model);
        return "index";
    }

    @PostMapping("clear")
    public String testConnection(Model model){
        this.resultList.clear();
        this.resultSet.clear();
        this.brand_choice = null;
        this.model_choice = null;
        this.min_filter = null;
        this.max_filter = null;
        this.modelSet.clear();
        this.updateModel(model);
        return "index";
    }

    private void updateModel(Model model){
        model.addAttribute("brands", this.brandSet);
        model.addAttribute("brand_choice", this.brand_choice);
        model.addAttribute("models", this.modelSet);
        model.addAttribute("model_choice", this.model_choice);
        model.addAttribute("resultList", this.resultSet);
        model.addAttribute("min_filter", this.min_filter);
        model.addAttribute("max_filter", this.max_filter);
    }

    @GetMapping("/order")
    public String placeOrder(Model model, @RequestParam(name="part") String part, @RequestParam(name="price") double price, @RequestParam(name="dataset") String dataset) {
        this.updateModel(model);
        model.addAttribute("part", part);
        model.addAttribute("price", price);
        model.addAttribute("dataset", dataset);
        return ("order");
    }

    @GetMapping("/confirm_order")
    public String placeOrder(Model model, @RequestParam(name="part") String part, @RequestParam(name="price") double price, @RequestParam(name="supplier") String supplier, @RequestParam(name="fName") String fName, @RequestParam(name="lName") String lName, @RequestParam(name="zip") String zip, @RequestParam(name="city") String city, @RequestParam(name="address") String address) {
        this.updateModel(model);
        Order new_order = new Order(part, supplier, price, fName, lName, address, zip, city);
        this.orders.add(new_order);
        //this.SendMail("chrigro96@gmx.at", "Your Order of " + part + "at a price of " + price + " was confirmed!");
        model.addAttribute("part", part);
        model.addAttribute("price", price);
        model.addAttribute("fName", fName);
        model.addAttribute("lName", lName);
        model.addAttribute("address", address);
        model.addAttribute("city", city);
        model.addAttribute("zip", zip);
        return ("order_confirmed");
    }

    private void SendMail(String recipient, String mail_text) {
        // Recipient's email ID needs to be mentioned.
        String to = recipient;

        // Sender's email ID needs to be mentioned
        String from = "felix.winterleitner@gmail.com";

        // Assuming you are sending email from localhost
        String host = "smtp.gmail.com";

        // Get system properties
        Properties properties = System.getProperties();

        // Setup mail server

        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");

        // Get the Session object.// and pass username and password
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {

            protected PasswordAuthentication getPasswordAuthentication() {

                return new PasswordAuthentication("felix.winterleitner@gmail.com", "*******");

            }

        });

        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            // Set Subject: header field
            message.setSubject("Order Confirmed!");

            // Now set the actual message
            message.setText(mail_text);

            System.out.println("sending...");
            // Send message
            Transport.send(message);
            System.out.println("Sent message successfully....");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }

}
