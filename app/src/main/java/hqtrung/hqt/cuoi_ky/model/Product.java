package hqtrung.hqt.cuoi_ky.model;

public class Product {
    private String receiver;
    private String idSender;
    private String idProduct;
    private String mes;

    public Product() {

    }

    public Product(String receiver, String idProduct, String mes, String idSender) {
        this.receiver = receiver;
        this.idProduct = idProduct;
        this.mes = mes;
        this.idSender = idSender;
    }

    public String getIdSender() {
        return idSender;
    }

    public void setIdSender(String idSender) {
        this.idSender = idSender;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(String idProduct) {
        this.idProduct = idProduct;
    }
}
