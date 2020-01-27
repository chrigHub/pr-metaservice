package jku.dke.prmetaservice.entity;

public class Result {
    private String dataset;
    private String part;
    private String price;
    private String brand;
    private String model;

    public Result(String dataset, String part, String price, String brand, String model) {
        this.dataset = dataset;
        this.part = part;
        this.price = price;
        this.brand = brand;
        this.model = model;
    }

    public Result() {
        this.dataset = null;
        this.part = null;
        this.price = null;
        this.brand = null;
        this.model = null;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
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
            case "model":
                this.setModel(val);
                break;
            case "brand":
                this.setBrand(val);
                break;
        }
    }

}
