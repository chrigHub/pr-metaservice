package jku.dke.prmetaservice.service;

import org.apache.jena.query.ResultSet;

import java.util.List;

public interface SparqlService {
    List getAllTriples(String endpoint);

    List getBrandsFromAudi();
    List getModelsFromAudi();
    List getPartsForAudiModel(String model);

    List getBrandsFromJoe();
    List getModelsForBrandFromJoe(String brand);
    List getPartsForModelFromJoe(String model);

    List getBrandsFromGs();
    List getModelsForBrandFromGs(String brand);
    List getPartsForModelFromGs(String model);
}
