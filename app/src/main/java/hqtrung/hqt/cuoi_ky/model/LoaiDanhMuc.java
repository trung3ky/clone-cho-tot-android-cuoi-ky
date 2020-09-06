package hqtrung.hqt.cuoi_ky.model;

public class LoaiDanhMuc {
    private int Id;
    private String Anh;
    private String Name;

    public LoaiDanhMuc(int id, String anh, String name) {
        Id = id;
        Anh = anh;
        Name = name;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getAnh() {
        return Anh;
    }

    public void setAnh(String anh) {
        Anh = anh;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
