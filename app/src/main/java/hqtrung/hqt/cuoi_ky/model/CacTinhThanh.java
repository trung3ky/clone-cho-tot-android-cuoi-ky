package hqtrung.hqt.cuoi_ky.model;

public class CacTinhThanh {
    private String Name;
    private int id;

    public CacTinhThanh(String name, int id) {
        Name = name;
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
