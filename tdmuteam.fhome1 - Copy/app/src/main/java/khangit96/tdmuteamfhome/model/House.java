package khangit96.tdmuteamfhome.model;

/**
 * Created by Administrator on 11/4/2016.
 */

public class House implements Comparable<House> {
    public String diaChi;
    public String giaDien;
    public String giaNuoc;
    public String giaPhong;
    public double kinhDo;
    public String sdt;
    public String tenChuHo;
    public String tinhTrang;
    public boolean verified;
    public double viDo;
    public double distance;

    public House(String diaChi, String giaDien, String giaNuoc, String giaPhong,
                 double kinhDo, String sdt, String tenChuHo, String tinhTrang, boolean verified, double viDo) {
        this.diaChi = diaChi;
        this.giaDien = giaDien;
        this.giaNuoc = giaNuoc;
        this.giaPhong = giaPhong;
        this.kinhDo = kinhDo;
        this.sdt = sdt;
        this.tenChuHo = tenChuHo;
        this.tinhTrang = tinhTrang;
        this.verified = verified;
        this.viDo = viDo;
    }

    @Override
    public int compareTo(House house) {
        return (int) (this.distance - house.distance);
    }
}
