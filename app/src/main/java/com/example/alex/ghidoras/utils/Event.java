package com.example.alex.ghidoras.utils;

/**
 * Created by alex on 08.12.2017.
 */

public class Event {


    private int id;
    private String nume;
    private String descriere;
    private int numar_persoane;
    private String data_sfarsit;
    private String data_inceput;
    private int id_locatie;
    private int id_organizator;
    private String nume_locatie;
    private String descriere_locatie;
    private String adresa;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return nume;
    }

    public void setName(String name) {
        this.nume = name;
    }

    public String getDescriere() {
        return descriere;
    }

    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }

    public int getNumar_persoane() {
        return numar_persoane;
    }

    public void setNumar_persoane(int numar_persoane) {
        this.numar_persoane = numar_persoane;
    }

    public String getData_sfarsit() {
        return data_sfarsit;
    }

    public String getData_inceput() {
        return data_inceput;
    }

    public void setData_inceput(String data_inceput) {
        this.data_inceput = data_inceput;
    }

    public void setData_sfarsit(String data_sfarsit) {
        this.data_sfarsit = data_sfarsit;
    }

    public int getId_locatie() {
        return id_locatie;
    }

    public void setId_locatie(int id_locatie) {
        this.id_locatie = id_locatie;
    }

    public int getId_organizator() {
        return id_organizator;
    }

    public void setId_organizator(int id_organizator) {
        this.id_organizator = id_organizator;
    }

    public String getNume_locatie() {
        return nume_locatie;
    }

    public void setNume_locatie(String nume_locatie) {
        this.nume_locatie = nume_locatie;
    }

    public String getDescriere_locatie() {
        return descriere_locatie;
    }

    public void setDescriere_locatie(String descriere_locatie) {
        this.descriere_locatie = descriere_locatie;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }
}
