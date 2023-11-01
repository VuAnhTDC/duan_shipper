package com.example.duangiaohang.Models;

import java.io.Serializable;

public class ShipperData implements Serializable {
    private int status;
    private String idShipper;
    private String hoTenShipper;
    private String emailShipper;
    private String sdtShipper;
    private String diaChiShipper;
    private String nguyenQuanShipper;
    private String khuVucGHShipper;
    private String urlmatTruocCCCD;
    private String urlmatSauCCCD;
    private String passWordShipper;
    private String  urlImgShopAvatar;

    public ShipperData() {

            this.sdtShipper = sdtShipper;
            this.passWordShipper= passWordShipper;
        }



    public ShipperData( String idShipper, String hoTenShipper, String emailShipper, String sdtShipper, String diaChiShipper, String nguyenQuanShipper, String khuVucGHShipper) {

        this.idShipper = idShipper;
        this.hoTenShipper = hoTenShipper;
        this.emailShipper = emailShipper;
        this.sdtShipper = sdtShipper;
        this.diaChiShipper = diaChiShipper;
        this.nguyenQuanShipper = nguyenQuanShipper;
        this.khuVucGHShipper = khuVucGHShipper;




    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getIdShipper() {
        return idShipper;
    }

    public void setIdShipper(String idShipper) {
        this.idShipper = idShipper;
    }

    public String getHoTenShipper() {
        return hoTenShipper;
    }

    public void setHoTenShipper(String hoTenShipper) {
        this.hoTenShipper = hoTenShipper;
    }

    public String getEmailShipper() {
        return emailShipper;
    }

    public void setEmailShipper(String emailShipper) {
        this.emailShipper = emailShipper;
    }

    public String getSdtShipper() {
        return sdtShipper;
    }

    public void setSdtShipper(String sdtShipper) {
        this.sdtShipper = sdtShipper;
    }

    public String getDiaChiShipper() {
        return diaChiShipper;
    }

    public void setDiaChiShipper(String diaChiShipper) {
        this.diaChiShipper = diaChiShipper;
    }

    public String getNguyenQuanShipper() {
        return nguyenQuanShipper;
    }

    public void setNguyenQuanShipper(String nguyenQuanShipper) {
        this.nguyenQuanShipper = nguyenQuanShipper;
    }

    public String getKhuVucGHShipper() {
        return khuVucGHShipper;
    }

    public void setKhuVucGHShipper(String khuVucGHShipper) {
        this.khuVucGHShipper = khuVucGHShipper;
    }

    public String getUrlmatTruocCCCD() {
        return urlmatTruocCCCD;
    }

    public void setUrlmatTruocCCCD(String urlmatTruocCCCD) {
        this.urlmatTruocCCCD = urlmatTruocCCCD;
    }

    public String getUrlmatSauCCCD() {
        return urlmatSauCCCD;
    }

    public void setUrlmatSauCCCD(String urlmatSauCCCD) {
        this.urlmatSauCCCD = urlmatSauCCCD;
    }

    public String getPassWordShipper() {
        return passWordShipper;
    }

    public void setPassWordShipper(String passWordShipper) {
        this.passWordShipper = passWordShipper;
    }

    public String getUrlImgShopAvatar() {
        return urlImgShopAvatar;
    }

    public void setUrlImgShopAvatar(String urlImgShopAvatar) {
        this.urlImgShopAvatar = urlImgShopAvatar;
    }

    @Override
    public String toString() {
        return "ShipperData{" +
                "status=" + status +
                ", idShipper='" + idShipper + '\'' +
                ", hoTenShipper='" + hoTenShipper + '\'' +
                ", emailShipper='" + emailShipper + '\'' +
                ", sdtShipper='" + sdtShipper + '\'' +
                ", diaChiShipper='" + diaChiShipper + '\'' +
                ", nguyenQuanShipper='" + nguyenQuanShipper + '\'' +
                ", khuVucGHShipper='" + khuVucGHShipper + '\'' +

                ", passWordShipper='" + passWordShipper + '\'' +
                ", urlImgShopAvatar='" + urlImgShopAvatar + '\'' +
                '}';
    }
}
