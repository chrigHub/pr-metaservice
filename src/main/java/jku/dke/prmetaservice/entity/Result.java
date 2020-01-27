package jku.dke.prmetaservice.entity;

public class Result {
    private String dataset;
    private String part;
    private String price;

    public Result(String dataset, String part, String price) {
        this.dataset = dataset;
        this.part = part;
        this.price = price;
    }

    public Result() {
        this.dataset = null;
        this.part = null;
        this.price = null;
    }

    public String getDataset() {
        return dataset;
    }

    public void setDataset(String dataset) {
        this.dataset = dataset;
    }

    public String getPart() {
        return part;
    }

    public void setPart(String part) {
        this.part = part;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void map(String val, String key){
        switch(key){
            case "price":
                this.setPrice(val);
                break;
            case "part":
                this.setPart(val);
                break;
        }
    }

}
