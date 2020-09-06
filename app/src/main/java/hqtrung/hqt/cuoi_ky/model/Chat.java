package hqtrung.hqt.cuoi_ky.model;

public class Chat {
    private String gender;
    private String receiver;
    private String message;
    private String idProduct;

    public Chat(String gender, String receiver, String message, String idProduct) {
        this.gender = gender;
        this.receiver = receiver;
        this.message = message;
        this.idProduct = idProduct;
    }

    public Chat() {
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(String idProduct) {
        this.idProduct = idProduct;
    }
}
