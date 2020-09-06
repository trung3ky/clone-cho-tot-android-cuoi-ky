package hqtrung.hqt.cuoi_ky.model;

public class TimKiem {
    private int id;
    private String noidung;

    public TimKiem(int id, String noidung) {
        this.id = id;
        this.noidung = noidung;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNoidung() {
        return noidung;
    }

    public void setNoidung(String noidung) {
        this.noidung = noidung;
    }
}
