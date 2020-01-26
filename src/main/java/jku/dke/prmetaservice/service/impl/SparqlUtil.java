package jku.dke.prmetaservice.service.impl;

import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;

public class SparqlUtil {

    private static final String URI_TEXT = "http://localhost:8080/";
    private static final String SPAREPART = "Sparepart/";
    private static final String CAR = "Car/";
    private static final String SPAREPARTSUPPLIER = "Sparepartsupplier/";

    /**
     * Strips away all unneccessary URI components contained in the data
     *
     * @param predicate the predicate to be stripped
     * @return naked predixcate
     */
    static String stripURI(String predicate) {
        String s = predicate.replace(URI_TEXT, "");
        s = s.replace(SPAREPART, "");
        s = s.replace(CAR, "");
        s = s.replace(SPAREPARTSUPPLIER, "");
        return s;
    }

    /**
     * adds the service-specific URI to the search predicate
     *
     * @param filter
     * @return search predicate with URI
     */
    static String addUriToFilter(String filter) {
        return "<" + URI_TEXT + filter + ">";
    }

    /**
     * Adds the URI to the searched value depending on the filter value.
     * Some domains reside in a certain sub domain, eg. /Topics or /StudyDirection
     *
     * @param pred
     * @return search subject with URI if needed
     */
    static String addUriToSearchSubject(FilterPredicate pred) {
        String filterWithUri;

        if ("sparePartSupplier".equalsIgnoreCase(pred.getSearchPredicate()) || "hasSortimentFor".equalsIgnoreCase(pred.getSearchPredicate())) {
            filterWithUri = "<" + URI_TEXT + CAR + pred.getSearchValue() + ">";
        } else if ("sparePart".equalsIgnoreCase(pred.getSearchPredicate()) || "belongsTo".equalsIgnoreCase(pred.getSearchPredicate())) {
            filterWithUri = "<" + URI_TEXT + CAR + pred.getSearchValue() + ">";
        } else if ("sparePartSupplier".equalsIgnoreCase(pred.getSearchPredicate())) {
            filterWithUri = "<" + URI_TEXT + SPAREPART + pred.getSearchValue() + ">";
        } else {
            filterWithUri = "\"" + pred.getSearchValue() + "\"";
        }

        return filterWithUri;
    }

    static String extractInfoFromNode(RDFNode node) {
        if (node != null) {
            Resource r = node.asResource();
            return stripURI(r.getURI());
        }
        return " ";
    }
}
