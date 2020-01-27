package jku.dke.prmetaservice.service;

import org.apache.jena.query.ResultSet;

import java.util.List;

public interface SparqlService {
    List<List<String>> getAllTriples(String datastore);

    List<List<String>> getBrandsFromAudi();
    List<List<String>> getModelsFromAudi();
    List<List<String>> getPartsForAudiModel(String model);

    List<List<String>> getBrandsFromJoe();
    List<List<String>> getModelsForBrandFromJoe(String brand);
    List<List<String>> getPartsForModelFromJoe(String model);

    List<List<String>> getBrandsFromGs();
    List<List<String>> getModelsForBrandFromGs(String brand);
    List<List<String>> getPartsForModelFromGs(String model);
}
