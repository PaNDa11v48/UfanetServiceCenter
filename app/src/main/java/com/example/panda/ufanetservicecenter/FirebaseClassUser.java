package com.example.panda.ufanetservicecenter;

public class FirebaseClassUser {
    public String email;
    public String password;
    public String name;
    public String lastname;

    public FirebaseClassUser() {
    }
    public FirebaseClassUser(String email, String password, String name, String lastname) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.lastname = lastname;
    }

}