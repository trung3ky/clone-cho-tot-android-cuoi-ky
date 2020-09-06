package hqtrung.hqt.cuoi_ky.model;

public class SanPham {
    private String id;
    private String NameSP;
    private String GiaSP;
    private String AnhSP;
    private boolean tym;

    public SanPham(String id, String nameSP, String giaSP, String anhSP, boolean tym) {
        this.id = id;
        NameSP = nameSP;
        GiaSP = giaSP;
        AnhSP = anhSP;
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

    public boolean isTym() {
        return tym;
    }

    public void setTym(boolean tym) {
        this.tym = tym;
    }
}
