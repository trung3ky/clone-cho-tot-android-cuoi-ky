package hqtrung.hqt.cuoi_ky.model;

public class DanhMuc {
    private int Id;
    private String ImgDanhMuc;
    private String TenDanhMuc;

    public DanhMuc(int id, String imgDanhMuc, String tenDanhMuc) {
        Id = id;
        ImgDanhMuc = imgDanhMuc;
        TenDanhMuc = tenDanhMuc;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getImgDanhMuc() {
        return ImgDanhMuc;
    }

    public void setImgDanhMuc(String imgDanhMuc) {
        ImgDanhMuc = imgDanhMuc;
    }

    public String getTenDanhMuc() {
        return TenDanhMuc;
    }

    public void setTenDanhMuc(String tenDanhMuc) {
        TenDanhMuc = tenDanhMuc;
    }
}
