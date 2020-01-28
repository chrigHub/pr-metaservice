package jku.dke.prmetaservice.entity;

public class Order {

    public String getPart() {
        return part;
    }

    public String getSupplier() {
        return supplier;
    }

    public double getPrice() {
        return price;
    }

    public String getfName() {
        return fName;
    }

    public String getlName() {
        return lName;
    }

    public String getAddress() {
        return address;
    }

    public String getZip() {
        return zip;
    }

    public String getCity() {
        return city;
    }

    String supplier;
    double price;
    String part;

    String fName;
    String lName;
    String address;
    String zip;
    String city;

    public Order(String part, String supplier, double price, String fName, String lName, String address, String zip, String city) {
        this.part = part;
        this.supplier = supplier;
        this.price = price;
        this.fName = fName;
        this.lName = lName;
        this.address = address;
        this.zip = zip;
        this.city = city;
    }
}
