package com.example.oop_project;

import java.util.ArrayList;
import java.util.List;

/**
 * Kohviautomaadi klass, mis implementeerib müügiautomaadi liidest.
 */

public class Kohviautomaat implements Müügiautomaat {
    private List<Kohv> pakutavadKohvid;
    private double piimaKogusLiitrites;
    private double kohviSeguKogusLiitrites;
    private boolean kasMasinOnRikkis; //true-masin on rikkis, false-masin on terve
    private int mituKordaKasutatud;

    /**
     * Kohviautomaadi konstruktor
     * @param piimaKogusLiitrites Piima kogus liitrites
     * @param kohviSeguKogusLiitrites Kohvi segu kogus liitrites
     */
    public Kohviautomaat(double piimaKogusLiitrites, double kohviSeguKogusLiitrites) {
        this.piimaKogusLiitrites = piimaKogusLiitrites;
        this.kohviSeguKogusLiitrites = kohviSeguKogusLiitrites;
        this.pakutavadKohvid = new ArrayList<>();
        this.kasMasinOnRikkis = false;
        this.mituKordaKasutatud = 0;
    }

    // Lisame kohvi masinasse
    public void lisaKohv(Kohv kohv) {
        pakutavadKohvid.add(kohv);
    }


    // Get ja set meetodid
    public double getPiimaKogusLiitrites() {
        return piimaKogusLiitrites;
    }

    public void setPiimaKogusLiitrites(double piimaKogusLiitrites) {
        this.piimaKogusLiitrites = piimaKogusLiitrites;
    }

    public double getKohviSeguKogusLiitrites() {
        return kohviSeguKogusLiitrites;
    }

    public void setKohviSeguKogusLiitrites(double kohviSeguKogusLiitrites) {
        this.kohviSeguKogusLiitrites = kohviSeguKogusLiitrites;
    }

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

    private void masinaOlek() {  //Määrab ära, kas masin on rikkis või mitte tõenäosuse järgi.
        if (getMituKordaKasutatud() > 3 && Math.random() > 0.9) setKasMasinOnRikkis(true); //Kui masinat on üle 3 korra kasutatud ja kui Math.Random on suurem kui 0.9(võiks olla 10% tõenäosus), siis määratakse, et masin on rikkis.
    }

    // Meetod, mis väljastab kohvide hinnad
    @Override
    public void väljastaToodeteHinnad() {
        if (getKasMasinOnRikkis()) { // Kui masin on rikkis, siis väljume meetodist
            System.out.println("Masin on rikkis! Vabandame");
            return;
        }
        int number = 1;
        for (Kohv elem : pakutavadKohvid) {
            if (elem.getPiimaKogusL() > getPiimaKogusLiitrites()) // Kui pole piisavalt piima, siis väljastame vastava teate
                System.out.println(elem.getKohviNimi() + " - " + elem.getKohviHind() + " Vabandame, aga hetkel pole piisavalt piima selle kohvi valmistamiseks.");
            else if (elem.getKohviSeguKogusL() > getKohviSeguKogusLiitrites()) // Kui pole piisavalt segu, siis väljastame vastava teate
                System.out.println(elem.getKohviNimi() + " - " + elem.getKohviHind() + " Vabandame, aga hetkel pole piisavalt segu kohvi valmistamiseks.");
            else System.out.println(number + ". " + elem.getKohviNimi() + " - " + elem.getKohviHind()); // Väljastame kohvi nime ja hinna
            number++;
        }
    }

    // Meetod, mis valmistab kohvi vastavalt valitud kohvile
    @Override
    public double sooritaOst(int tooteNumber, double raha) {
        if (getKasMasinOnRikkis()) { // Kui masin on rikkis, siis väljume meetodist
            System.out.println("Masin on rikkis! Vabandame");
            return raha;
        }

        if (tooteNumber < 1 || tooteNumber > pakutavadKohvid.size()) { // Kontrollime, kas valitud toode on olemas
            System.out.println("Sellist toodet ei ole olemas!");
            return raha;
        } else {
            Kohv valitudKohv = pakutavadKohvid.get(tooteNumber - 1);
            if (raha < valitudKohv.getKohviHind()) { // Kontrollime, kas raha on piisavalt
                System.out.println("Raha ei ole piisavalt!");
                return raha;
            } else if (valitudKohv.getKohviSeguKogusL() > getKohviSeguKogusLiitrites()) { // Kontrollime, kas on piisavalt segu ja piima
                System.out.println("Pole piisavalt segu kohvi valmistamiseks. Valige mõni muu kohv.");
                return raha;
            } else if (valitudKohv.getPiimaKogusL() > getPiimaKogusLiitrites()) {
                System.out.println("Pole piisavalt piima kohvi valmistamiseks. Valige mõni muu kohv.");
                return raha;
            } else { // Kui kõik on korras, siis valmistame kohvi
                setKohviSeguKogusLiitrites(getKohviSeguKogusLiitrites() - valitudKohv.getKohviSeguKogusL());
                setPiimaKogusLiitrites(getPiimaKogusLiitrites() - valitudKohv.getPiimaKogusL());
                System.out.println("Kohv valmistatud!");
                System.out.println("Raha tagasi: " + (raha - valitudKohv.getKohviHind()));
                setMituKordaKasutatud(getMituKordaKasutatud() + 1);
                masinaOlek();
                return raha - valitudKohv.getKohviHind();
            }
        }
    }

    @Override
    public boolean kontroll(double raha) {   //Kas on piisavalt raha, et midagi osta. Kui ei ole tagastab true.
        for (Kohv elem : pakutavadKohvid) {
            if (raha > elem.getKohviHind()) return false;
        }
        return true;
    }

    public List<Kohv> getPakutavadKohvid() {
        return pakutavadKohvid;
    }
}
