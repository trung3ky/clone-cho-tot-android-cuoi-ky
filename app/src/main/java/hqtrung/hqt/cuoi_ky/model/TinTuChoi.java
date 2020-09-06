package hqtrung.hqt.cuoi_ky.model;

public class TinTuChoi {
    private String id;
    private String NameSP;
    private String GiaSP;
    private String AnhSP;
    private String Date;
    private String NoiDung;
    private boolean tym;

    public TinTuChoi(String id, String nameSP, String giaSP, String anhSP, String date, String noiDung, boolean tym) {
        this.id = id;
        NameSP = nameSP;
        GiaSP = giaSP;
        AnhSP = anhSP;
        Date = date;
        NoiDung = noiDung;
        this.tym = tym;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNameSP() {
        return NameSP;
    }

    public void setNameSP(String nameSP) {
        NameSP = nameSP;
    }

    public String getGiaSP() {
        return GiaSP;
    }

    public void setGiaSP(String giaSP) {
        GiaSP = giaSP;
    }

    public String getAnhSP() {
        return AnhSP;
    }

    public void setAnhSP(String anhSP) {
        AnhSP = anhSP;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getNoiDung() {
        return NoiDung;
    }

    public void setNoiDung(String noiDung) {
        NoiDung = noiDung;
    }

    public boolean isTym() {
        return tym;
    }

    public void setTym(boolean tym) {
        this.tym = tym;
    }
}
