package com.example.duangiaohang.Models;

import androidx.annotation.NonNull;

import java.util.Objects;

public class ListItemUserTC {
    private String userShipperImageTC;
    private String maDonHangTC;

    @Override
    public String toString() {
        return "ListItemUserTC{" +
                "userShipperImageTC=" + userShipperImageTC +
                ", maDonHangTC='" + maDonHangTC + '\'' +
                ", diaChiGiaoHangTC='" + diaChiGiaoHangTC + '\'' +
                ", diaChiNhanHangTC='" + diaChiNhanHangTC + '\'' +
                ", giaCaTC='" + giaCaTC + '\'' +
                '}';
    }

    public String getMaDonHangTC() {
        return maDonHangTC;
    }

    public void setMaDonHangTC(String maDonHangTC) {
        this.maDonHangTC = maDonHangTC;
    }

    public ListItemUserTC(String userShipperImageTC, String maDonHangTC, String diaChiGiaoHangTC, String diaChiNhanHangTC, String giaCaTC) {
        this.userShipperImageTC = userShipperImageTC;
        this.maDonHangTC = maDonHangTC;
        this.diaChiGiaoHangTC = diaChiGiaoHangTC;
        this.diaChiNhanHangTC = diaChiNhanHangTC;
        this.giaCaTC = giaCaTC;
    }

    private  String diaChiGiaoHangTC;
    private String diaChiNhanHangTC;
    private String giaCaTC;

    public ListItemUserTC(String  userShipperImageTC, String diaChiGiaoHangTC, String diaChiNhanHangTC, String giaCaTC) {
        this.userShipperImageTC = userShipperImageTC;
        this.diaChiGiaoHangTC = diaChiGiaoHangTC;
        this.diaChiNhanHangTC = diaChiNhanHangTC;
        this.giaCaTC = giaCaTC;
    }

    public ListItemUserTC() {
    }

    public String getUserShipperImageTC() {
        return userShipperImageTC;
    }

    public void setUserShipperImageTC(String userShipperImageTC) {
        this.userShipperImageTC = userShipperImageTC;
    }

    public String getDiaChiGiaoHangTC() {
        return diaChiGiaoHangTC;
    }

    public void setDiaChiGiaoHangTC(String diaChiGiaoHangTC) {
        this.diaChiGiaoHangTC = diaChiGiaoHangTC;
    }

    public String getDiaChiNhanHangTC() {
        return diaChiNhanHangTC;
    }

    public void setDiaChiNhanHangTC(String diaChiNhanHangTC) {
        this.diaChiNhanHangTC = diaChiNhanHangTC;
    }

    public String getGiaCaTC() {
        return giaCaTC;
    }

    public void setGiaCaTC(String giaCaTC) {
        this.giaCaTC = giaCaTC;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ListItemUserTC that = (ListItemUserTC) o;
        return userShipperImageTC == that.userShipperImageTC && Objects.equals(diaChiGiaoHangTC, that.diaChiGiaoHangTC) && Objects.equals(diaChiNhanHangTC, that.diaChiNhanHangTC) && Objects.equals(giaCaTC, that.giaCaTC);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userShipperImageTC, diaChiGiaoHangTC, diaChiNhanHangTC, giaCaTC);
    }
}
