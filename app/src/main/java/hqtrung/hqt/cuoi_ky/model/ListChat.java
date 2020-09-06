package hqtrung.hqt.cuoi_ky.model;

public class ListChat {
    private String NameUser;
    private String NameSP;
    private String AnhUser;
    private String AnhSP;
    private String Message;
    private String idsp;
    private String idsender;
    private String phone;

    public ListChat(String nameUser, String nameSP, String anhUser, String anhSP, String message, String idsp, String idsender, String phone) {
        NameUser = nameUser;
        NameSP = nameSP;
        AnhUser = anhUser;
        AnhSP = anhSP;
        Message = message;
        this.idsp = idsp;
        this.idsender = idsender;
        this.phone = phone;
    }

    public ListChat() {
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIdsender() {
        return idsender;
    }

    public void setIdsender(String idsender) {
        this.idsender = idsender;
    }

    public String getIdsp() {
        return idsp;
    }

    public void setIdsp(String idsp) {
        this.idsp = idsp;
    }

    public String getNameUser() {
        return NameUser;
    }

    public void setNameUser(String nameUser) {
        NameUser = nameUser;
    }

    public String getNameSP() {
        return NameSP;
    }

    public void setNameSP(String nameSP) {
        NameSP = nameSP;
    }

    public String getAnhUser() {
        return AnhUser;
    }

    public void setAnhUser(String anhUser) {
        AnhUser = anhUser;
    }

    public String getAnhSP() {
        return AnhSP;
    }

    public void setAnhSP(String anhSP) {
        AnhSP = anhSP;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }
}
