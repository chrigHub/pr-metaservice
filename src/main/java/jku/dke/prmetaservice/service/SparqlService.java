package jku.dke.prmetaservice.service;

import org.apache.jena.query.ResultSet;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public interface SparqlService {
    ResultSet getAllTriples(String endpoint);
}
