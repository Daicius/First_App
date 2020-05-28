package com.swufe.first_app;

public class RateItem {

    private int id;
    private String curRate;
    private String curName;

    public RateItem() {
        this.curRate = "";
        this.curName = "";
    }

    public RateItem(String curName,String curRate) {
        this.curRate = curRate;
        this.curName = curName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCurRate() {
        return curRate;
    }

    public void setCurRate(String curRate) {
        this.curRate = curRate;
    }

    public String getCurName() {
        return curName;
    }

    public void setCurName(String curName) {
        this.curName = curName;
    }
}
