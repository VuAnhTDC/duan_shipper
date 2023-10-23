package com.example.duangiaohang.Models;

import java.io.Serializable;

public class Shipper implements Serializable {
    private  String hoTenS;
    private String EmailS;
    private String sdtS;
    private String diaChiS;
    private String khuVucS;
    private String khuVucGH;
    private String CMNDT;
    private String CMNDS;

    public Shipper(String hoTenS, String emailS, String sdtS, String diaChiS, String khuVucS, String khuVucGH, String CMNDT, String CMNDS) {
        this.hoTenS = hoTenS;
        EmailS = emailS;
        this.sdtS = sdtS;
        this.diaChiS = diaChiS;
        this.khuVucS = khuVucS;
        this.khuVucGH = khuVucGH;
        this.CMNDT = CMNDT;
        this.CMNDS = CMNDS;
    }

    public Shipper() {
    }

    public String getHoTenS() {
        return hoTenS;
    }

    public void setHoTenS(String hoTenS) {
        this.hoTenS = hoTenS;
    }

    public String getEmailS() {
        return EmailS;
    }

    public void setEmailS(String emailS) {
        EmailS = emailS;
    }

    public String getSdtS() {
        return sdtS;
    }

    public void setSdtS(String sdtS) {
        this.sdtS = sdtS;
    }

    public String getDiaChiS() {
        return diaChiS;
    }

    public void setDiaChiS(String diaChiS) {
        this.diaChiS = diaChiS;
    }

    public String getKhuVucS() {
        return khuVucS;
    }

    public void setKhuVucS(String khuVucS) {
        this.khuVucS = khuVucS;
    }

    public String getKhuVucGH() {
        return khuVucGH;
    }

    public void setKhuVucGH(String khuVucGH) {
        this.khuVucGH = khuVucGH;
    }

    public String getCMNDT() {
        return CMNDT;
    }

    public void setCMNDT(String CMNDT) {
        this.CMNDT = CMNDT;
    }

    public String getCMNDS() {
        return CMNDS;
    }

    public void setCMNDS(String CMNDS) {
        this.CMNDS = CMNDS;
    }

    @Override
    public String toString() {
        return "Shipper{" +
                "hoTenS='" + hoTenS + '\'' +
                ", EmailS='" + EmailS + '\'' +
                ", sdtS='" + sdtS + '\'' +
                ", diaChiS='" + diaChiS + '\'' +
                ", khuVucS='" + khuVucS + '\'' +
                ", khuVucGH='" + khuVucGH + '\'' +
                ", CMNDT='" + CMNDT + '\'' +
                ", CMNDS='" + CMNDS + '\'' +
                '}';
    }

}
