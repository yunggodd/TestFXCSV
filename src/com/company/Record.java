package com.company;

public class Record {

    private String id;
    private String f2;
    private String f3;

    public Record(){
        this.id = "";
        this.f2 = "";
        this.f3 = "";
    }

    public Record(String id) {
        this.id = id;
        this.f2 = f2;
        this.f3 = f3;

    }

    public Record(String key, String value) {
        this.id = key;
        this.f2 = value;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getF2() {
        return f2;
    }

    public void setF2(String f2) {
        this.f2 = f2;
    }

    public String getF3() {
        return f3;
    }

    public void setF3(String f3) {
        this.f3 = f3;
    }
}