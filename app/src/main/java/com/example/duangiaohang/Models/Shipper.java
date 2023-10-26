package com.example.duangiaohang.Models;

import android.provider.ContactsContract;

import java.io.Serializable;

public class Shipper implements Serializable {
    private String IdS;
    private String hoTenS;
    private String EmailS;
    private String sdtS;
    private String diaChiS;
    private String nguyenQuans;
    private String khuVucGH;
    private String CMNDT;
    private String CMNDS;

    public Shipper() {
    }

    public Shipper(String idS, String hoTenS, String emailS, String sdtS, String diaChiS, String nguyenQuans, String khuVucGH, String CMNDT, String CMNDS) {
        IdS = idS;
        this.hoTenS = hoTenS;
        EmailS = emailS;
        this.sdtS = sdtS;
        this.diaChiS = diaChiS;
        this.nguyenQuans = nguyenQuans;
        this.khuVucGH = khuVucGH;
        this.CMNDT = CMNDT;
        this.CMNDS = CMNDS;
    }



    public String getIdS() {
        return IdS;
    }

    public void setIdS(String idS) {
        IdS = idS;
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

    public String getNguyenQuans() {
        return nguyenQuans;
    }

    public void setNguyenQuans(String nguyenQuans) {
        this.nguyenQuans = nguyenQuans;
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
                "IdS='" + IdS + '\'' +
                ", hoTenS='" + hoTenS + '\'' +
                ", EmailS='" + EmailS + '\'' +
                ", sdtS='" + sdtS + '\'' +
                ", diaChiS='" + diaChiS + '\'' +
                ", nguyenQuans='" + nguyenQuans + '\'' +
                ", khuVucGH='" + khuVucGH + '\'' +
                ", CMNDT='" + CMNDT + '\'' +
                ", CMNDS='" + CMNDS + '\'' +
                '}';
    }
}
