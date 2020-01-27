package jku.dke.prmetaservice.service;

import jku.dke.prmetaservice.entity.Result;
import org.apache.jena.query.ResultSet;

import java.util.List;

public interface SparqlService {
    List<Result> getAllTriples(String datastore);
    List<Result> getBrandsFromAudi();
    List<Result> getModelsFromAudi();
    List<Result> getPartsForAudiModel(String model);
    List<Result> getBrandsFromJoe();
    List<Result> getModelsForBrandFromJoe(String brand);
    List<Result> getPartsForModelFromJoe(String model);
    List<Result> getBrandsFromGs();
    List<Result> getModelsForBrandFromGs(String brand);
    List<Result> getPartsForModelFromGs(String model);
}
