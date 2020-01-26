package jku.dke.prmetaservice.service.impl;

import java.util.List;

public class SparqlQueryBuilder {

    private SparqlQueryBuilder() {
    }

    ;

    public static class Builder {
        private String select = "SELECT ?subject ?predicate ?object WHERE {";

        public Builder() {
        }

        public Builder withFilter(List<FilterPraedicate> filterDto) {
            for (FilterPraedicate dto : filterDto) {
                select += " ?subject " + SparqlUtil.addUriToFilter(dto.getSearchPredicate())
                        + " "
                        + SparqlUtil.addUriToSearchSubject(dto) + ".";
            }
            select += "}";

            return this;
        }

        public String build() {
            System.out.println("finished query=" + select);
            return select;
        }
    }
}
