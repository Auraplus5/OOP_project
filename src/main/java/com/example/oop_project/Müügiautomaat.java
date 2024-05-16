package com.example.oop_project;

/**
 * Liides, mis kirjeldab müügiautomaadi funktsionaalsust.
 */
public interface Müügiautomaat {
    void väljastaToodeteHinnad();
    double sooritaOst(int tooteNumber, double raha);
    boolean kontroll(double raha);
}
