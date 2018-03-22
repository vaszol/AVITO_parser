package ru.vaszol.AVITO_parser.model;

public class Parser {

    public String url;
    public String name;
    public String price;
    public String district;
    public String address;
    public String date;

    public Parser(String url, String name, String price, String district, String address, String date) {
        this.url = url;
        this.name = name;
        this.price = price;
        this.district = district;
        this.address = address;
        this.date = date;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Parser{" +
                "url='" + url + '\'' +
                ", name='" + name + '\'' +
                ", price='" + price + '\'' +
                ", district='" + district + '\'' +
                ", address='" + address + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
