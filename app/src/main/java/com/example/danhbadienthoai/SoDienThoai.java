package com.example.danhbadienthoai;

import java.io.Serializable;

public class SoDienThoai implements Serializable {

    private String ten;
    private String sdt;
    private final String id;

    public SoDienThoai(String ten, String sdt, String id) {
        this.ten = ten;
        this.sdt = sdt;
        this.id = id;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getId() {
        return id;
    }
}


