package com.example.duangiaohang.Models;

import java.io.Serializable;

public class Shipper implements Serializable {
    private int status;
    private String idShipper;
    private String passwordShipper;

    public void setPasswordShipper(String passwordShipper) {
        this.passwordShipper = passwordShipper;
    }

    private String hoTenShipper;
    private String emailShipper;
    private String sdtShipper;
    private String diaChiShipper;
    private String nguyenQuanShipper;
    private String khuVucGHShipper;

    public String getPasswordShipper() {
        return passwordShipper;
    }

    public String getMatsauCCCD() {
        return matsauCCCD;
    }

    public void setMatsauCCCD(String matsauCCCD) {
        this.matsauCCCD = matsauCCCD;
    }

    private String matTruocCCCD;
    private String matsauCCCD;
    private String  urlImgShopAvatar;

    public Shipper() {
        this.idShipper = sdtShipper;
        this.status = 0;
        this.passwordShipper = null;
    }

    @Override
    public String toString() {
        return "Shipper{" +
                "status=" + status +
                ", idShipper='" + idShipper + '\'' +
                ", passwordShipper='" + passwordShipper + '\'' +
                ", hoTenShipper='" + hoTenShipper + '\'' +
                ", emailShipper='" + emailShipper + '\'' +
                ", sdtShipper='" + sdtShipper + '\'' +
                ", diaChiShipper='" + diaChiShipper + '\'' +
                ", nguyenQuanShipper='" + nguyenQuanShipper + '\'' +
                ", khuVucGHShipper='" + khuVucGHShipper + '\'' +
                ", matTruocCCCD='" + matTruocCCCD + '\'' +
                ", urlImgShopAvatar='" + urlImgShopAvatar + '\'' +
                '}';
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

    public String getMatTruocCCCD() {
        return matTruocCCCD;
    }

    public void setMatTruocCCCD(String matTruocCCCD) {
        this.matTruocCCCD = matTruocCCCD;
    }

    public String getUrlImgShopAvatar() {
        return urlImgShopAvatar;
    }

    public void setUrlImgShopAvatar(String urlImgShopAvatar) {
        this.urlImgShopAvatar = urlImgShopAvatar;
    }

    public Shipper( String hoTenShipper, String emailShipper, String sdtShipper, String diaChiShipper, String nguyenQuanShipper, String khuVucGHShipper) {

        this.hoTenShipper = hoTenShipper;
        this.emailShipper = emailShipper;
        this.sdtShipper = sdtShipper;
        this.diaChiShipper = diaChiShipper;
        this.nguyenQuanShipper = nguyenQuanShipper;
        this.khuVucGHShipper = khuVucGHShipper;

    }

}