package com.example.panda.ufanetservicecenter;

public class FirebaseClassMechanism {
    public String name;
    public String date;
    public String firm;
    public String kind;
    public String address;
    public String cause;
    public String QR;
    public String masterLastname;
    public String masterName;

    public FirebaseClassMechanism() {
    }
    public FirebaseClassMechanism(String name, String date, String firm, String kind, String address,String cause, String QR, String masterLastname, String masterName) {
        this.name = name;
        this.date = date;
        this.firm = firm;
        this.kind = kind;
        this.address = address;
        this.cause = cause;
        this.QR = QR;
        this.masterLastname = masterLastname;
        this.masterName = masterName;
    }
}