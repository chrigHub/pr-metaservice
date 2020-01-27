package jku.dke.prmetaservice.service;

import org.apache.jena.query.ResultSet;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public interface SparqlService {
    ResultSet getAllTriples(String endpoint);

    ResultSet getBrandsFromAudi();
    ResultSet getModelsFromAudi();
    ResultSet getPartsForAudiModel(String model);

    ResultSet getBrandsFromJoe();
    ResultSet getModelsForBrandFromJoe(String brand);
    ResultSet getPartsForModelFromJoe(String model);

    ResultSet getBrandsFromGs();
    ResultSet getModelsForBrandFromGs(String brand);
    ResultSet getPartsForModelFromGs(String model);
}
