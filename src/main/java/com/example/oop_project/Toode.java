package com.example.oop_project;

/**
 * Klass Toode
 */
public class Toode {
    private String nimi;
    private double hind;
    private int kogus;

    /**
     * Toote konstruktor
     * @param nimi Toote nimi
     * @param hind Toote hind
     * @param kogus Toote kogus
     */
    public Toode(String nimi, double hind, int kogus) {
        this.nimi = nimi;
        this.hind = hind;
        this.kogus = kogus;
    }

    // Get ja set meetodid
    public String getNimi() {
        return nimi;
    }

    public double getHind() {
        return hind;
    }

    public void setNimi(String nimi) {
        this.nimi = nimi;
    }

    public void setHind(double hind) {
        this.hind = hind;
    }

    public int getKogus() {
        return kogus;
    }

    public void setKogus(int kogus) {
        this.kogus = kogus;
    }
}
