package jku.dke.prmetaservice.service;

import jku.dke.prmetaservice.entity.SparqlTriple;
import jku.dke.prmetaservice.service.impl.FilterPredicate;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public interface SparqlService {
    List<String> query(URL url) throws IOException;

    List<SparqlTriple> findSparepartByFilter(List<FilterPredicate> filters);
}
