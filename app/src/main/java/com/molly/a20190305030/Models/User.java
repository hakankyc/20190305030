package com.molly.a20190305030.Models;

public class User {
    //User Sınıfımız. Burada kullanıcıda olmasını istediğimiz özellikleri yazıyoruz.
    private int id;
    private String email;
    private String password;
    private String phoneNumber;

    public User(){

    }
    public User(int id, String email, String password, String phoneNumber) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }

    //Aşağıda getter ve setter'lar var. Otomatik oluşturulabilir:
    //Windows için -> Alt + Insert -> Getter and Setter - Mac İçin -> Command + N -> Getter and Setter
    //Sınıftaki değişkenleri çağırmak için kullanıyoruz.
    public void setId(int id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
