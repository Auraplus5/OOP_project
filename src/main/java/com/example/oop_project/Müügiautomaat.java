package com.example.oop_project;

/**
 * Liides, mis kirjeldab müügiautomaadi funktsionaalsust.
 */
public interface Müügiautomaat {
    void väljastaToodeteHinnad() throws MasinRikkisErind;
    double sooritaOst(int tooteNumber, double raha) throws MasinRikkisErind, KohvimasinaErind, SnäkiautomaatErind;
    boolean kontroll(double raha);
}
