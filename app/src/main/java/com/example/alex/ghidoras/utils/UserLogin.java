package com.example.alex.ghidoras.utils;

/**
 * Created by alex on 14.11.2017.
 */

public class UserLogin {
    private String id_utilizator;
    private String nume;
    private String prenume;
    private String email;
    private String sex;
    private String data_nasterii;
    private String status;
    private String id_organizator = String.valueOf(Integer.MIN_VALUE);

    public String getData_nasterii() {
        return data_nasterii;
    }

    public void setData_nasterii(String data_nasterii) {
        this.data_nasterii = data_nasterii;
    }

    public String getSex() {
        return sex;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPrenume() {
        return prenume;
    }

    public void setPrenume(String prenume) {
        this.prenume = prenume;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getId_organizator() {
        return id_organizator;
    }

    public void setId_organizator(String id_organizator) {
        this.id_organizator = id_organizator;
    }

    public String getId_utilizator() {
        return id_utilizator;
    }

    public void setId_utilizator(String id_utilizator) {
        this.id_utilizator = id_utilizator;
    }
}
