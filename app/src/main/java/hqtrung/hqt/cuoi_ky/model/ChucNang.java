package hqtrung.hqt.cuoi_ky.model;

public class ChucNang {
    private int Icon;
    private String TenChucNang;
    private int id;

    public ChucNang(int icon, String tenChucNang, int id) {
        Icon = icon;
        TenChucNang = tenChucNang;
        this.id = id;
    }

    public int getIcon() {
        return Icon;
    }

    public void setIcon(int icon) {
        Icon = icon;
    }

    public String getTenChucNang() {
        return TenChucNang;
    }

    public void setTenChucNang(String tenChucNang) {
        TenChucNang = tenChucNang;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
