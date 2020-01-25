package jku.dke.prmetaservice.service;

import jku.dke.prmetaservice.entity.SparqlTriple;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.List;

public interface SparqlService {
    List<String> query(URL url) throws IOException;
}
