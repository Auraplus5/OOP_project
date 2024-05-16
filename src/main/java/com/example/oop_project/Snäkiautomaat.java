package com.example.oop_project;

import java.util.ArrayList;
import java.util.List;

/**
 * Snäkiautomaadi klass, mis implementeerib müügiautomaadi liidest.
 */

public class Snäkiautomaat implements Müügiautomaat {
    List<Toode> pakutavadSnäkid;
    private boolean kasMasinOnRikkis; //true-masin on rikkis, false-masin on terve
    private int mituKordaKasutatud;
    private int mituKordaRaputatud;

    /**
     * Snäkiautomaadi konstruktor
     */
    public Snäkiautomaat() {
        this.mituKordaKasutatud = 0;
        this.mituKordaRaputatud = 0;
        this.pakutavadSnäkid = new ArrayList<>();
        this.kasMasinOnRikkis = false;
    }

    // Lisame toote masinasse
    public void lisaToode(Toode toode) {
        pakutavadSnäkid.add(toode);
    }

    // Get ja set meetodid
    public boolean getKasMasinOnRikkis() {
        return kasMasinOnRikkis;
    }

    public void setKasMasinOnRikkis(boolean kasMasinOnRikkis) {
        this.kasMasinOnRikkis = kasMasinOnRikkis;
    }

    public int getMituKordaKasutatud() {
        return mituKordaKasutatud;
    }

    public void setMituKordaKasutatud(int mituKordaKasutatud) {
        this.mituKordaKasutatud = mituKordaKasutatud;
    }

    public int getMituKordaRaputatud() {
        return mituKordaRaputatud;
    }

    public void setMituKordaRaputatud(int mituKordaRaputatud) {
        this.mituKordaRaputatud = mituKordaRaputatud;
    }

    private void masinaOlek() {  //Määrab ära kas masin on rikkis või mitte
        if (getMituKordaRaputatud() > 5) {  //Kui masinat on üle 5 korra raputatud, siis määratakse, et masin on rikkis
            setKasMasinOnRikkis(true);
        } else if (getMituKordaKasutatud() > 3 && Math.random() > 0.9) setKasMasinOnRikkis(true); //Kui masinat on üle 3 korra kasutatud, ja kui Math.Random on suurem kui 0.9(võiks olla 10% tõenäosus), siis määratakse, et masin on rikkis.
    }

    // Raputab masinat ja määrab ära, kas masinast tuleb midagi välja või mitte
    public boolean raputa() {
        if (Math.random() < 0.3) {
            setMituKordaRaputatud(getMituKordaRaputatud() + 1);
            masinaOlek();
            return true;
        }
        setMituKordaRaputatud(getMituKordaRaputatud() + 1);
        masinaOlek();
        return false;
    }

    // Määrab ära, milline toode saadi pärast raputamist
    public String misTooteSaid() {
        Toode milline = pakutavadSnäkid.get((int) Math.round(pakutavadSnäkid.size() * Math.random()));
        milline.setKogus(milline.getKogus() - 1);
        return milline.getNimi();
    }

    // Väljastab toodete hinnad
    @Override
    public void väljastaToodeteHinnad() throws MasinRikkisErind {
        if (getKasMasinOnRikkis()) { // Kontrollib, kas masin on rikkis
            throw new MasinRikkisErind("Masin on rikkis! Vabandame", 0);
        }
        int number = 1;
        // Väljastab toodete nimed ja hinnad
        for (Toode elem : pakutavadSnäkid) {
            System.out.println(number + ". " + elem.getNimi() + " - " + elem.getHind() + "€ - " + elem.getKogus());
            number++;
        }
    }

    // Sooritab ostu
    @Override
    public double sooritaOst(int tooteNumber, double raha) throws MasinRikkisErind, SnäkiautomaatErind {
        if (getKasMasinOnRikkis()) { // Kontrollib, kas masin on rikkis
            throw new MasinRikkisErind("Masin on rikkis! Vabandame", raha);
        }

        if (tooteNumber < 1 || tooteNumber > pakutavadSnäkid.size()) { // Kontrollib, kas toode on olemas
            throw new SnäkiautomaatErind("Toodet ei eksisteeri!", raha);
        } else {
            Toode valitudToode = pakutavadSnäkid.get(tooteNumber - 1);
            if (valitudToode.getKogus() == 0) { // Kontrollib, kas toode on otsas
                throw new SnäkiautomaatErind("Toode on otsas!", raha);
            } else if (raha < valitudToode.getHind()) { // Kontrollib, kas raha on piisavalt
                throw new SnäkiautomaatErind("Raha ei ole piisav!", raha);
            } else { // Kui kõik on korras, siis ostetakse toode
                valitudToode.setKogus(valitudToode.getKogus() - 1);
                System.out.println("Toode ostetud!");
                System.out.println("Raha tagasi: " + (raha - valitudToode.getHind()));
                setMituKordaKasutatud(getMituKordaKasutatud() + 1);
                masinaOlek();
                return raha - valitudToode.getHind();
            }
        }
    }

    // Kontrollib, kas kasutajal on piisavalt raha
    @Override
    public boolean kontroll(double raha) {
        for (Toode elem : pakutavadSnäkid) {
            if (raha > elem.getHind()) return false;
        }
        return true;
    }

    public List<Toode> getTooted() {
        return pakutavadSnäkid;
    }
}
