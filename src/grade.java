
import java.io.Serializable;

public class grade implements Serializable {

    public String masv, name;
    public double anh, tin, gdtc, avg;
    public int id;

    public grade(int id,String masv, String name, double anh, double tin, double gdtc, double avg) {
        this.name = name;
        this.masv = masv;
        this.id = id;
        this.anh = anh;
        this.tin = tin;
        this.gdtc = gdtc;
        this.avg = avg;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public grade() {
    }

    public String getMasv() {
        return masv;
    }

    public void setMasv(String masv) {
        this.masv = masv;
    }

    public double getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getAnh() {
        return anh;
    }

    public void setAnh(double anh) {
        this.anh = anh;
    }

    public double getTin() {
        return tin;
    }

    public void setTin(double tin) {
        this.tin = tin;
    }

    public double getGdtc() {
        return gdtc;
    }

    public void setGdtc(double gdtc) {
        this.gdtc = gdtc;
    }

    public double getAvg() {
        avg = (anh + tin + gdtc) / 3 ;
        return avg;
    }

    public void setAvg(double avg) {
        this.avg = avg;
    }

}
