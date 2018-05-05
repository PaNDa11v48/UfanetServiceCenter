package com.example.panda.ufanetservicecenter;

public class FirebaseClassRepairs {
    public String date;
    public String cause;
    public String QR;
    public String Mname;
    public String Mlastname;
    public String name;


    public FirebaseClassRepairs() {
    }

    public FirebaseClassRepairs(String date, String cause, String QR, String Mname, String Mlastname, String name) {
        this.date = date;
        this.cause = cause;
        this.QR = QR;
        this.Mname = Mname;
        this.Mlastname = Mlastname;
        this.name = name;
    }

}