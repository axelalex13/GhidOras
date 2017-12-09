package com.example.alex.ghidoras.utils;

/**
 * Created by alex on 06.12.2017.
 */

public class Location {
    private String id;
    private String nume_locatie;
    private String descriere_locatie;
    private String adresa;

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public String getDescriere() {
        return descriere_locatie;
    }

    public void setDescriere(String descriere_locatie) {
        this.descriere_locatie = descriere_locatie;
    }

    public String getNume() {
        return nume_locatie;
    }

    public void setNume(String nume) {
        this.nume_locatie = nume;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
