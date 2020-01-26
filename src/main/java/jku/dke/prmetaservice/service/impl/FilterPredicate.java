package jku.dke.prmetaservice.service.impl;

public class FilterPredicate {

    private String searchPredicate, searchValue;

    public FilterPredicate(String searchPredicate, String searchValue) {
        this.searchPredicate = searchPredicate;
        this.searchValue = searchValue;
    }

    public String getSearchPredicate() {
        return searchPredicate;
    }

    public void setSearchPredicate(String searchPredicate) {
        this.searchPredicate = searchPredicate;
    }

    public String getSearchValue() {
        return searchValue;
    }

    public void setSearchValue(String searchValue) {
        this.searchValue = searchValue;
    }
}