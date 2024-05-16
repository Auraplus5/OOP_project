package com.example.oop_project;

import java.util.Scanner;

public class Peaklass {
    public static void main(String[] args) throws MasinRikkisErind, KohimasinaErind, SnäkiautomaatErind {

        // loome snäkiautomaadi ja lisame tooted
        Toode snickers = new Toode("Snickers", 1.99, 4);
        Toode chips = new Toode("Potato Chips", 1.49, 10);
        Toode coca = new Toode("Coca-Cola", 1.29, 3);

        Snäkiautomaat snäkiautomaat = new Snäkiautomaat();
        snäkiautomaat.lisaToode(snickers);
        snäkiautomaat.lisaToode(chips);
        snäkiautomaat.lisaToode(coca);

        // loome kohviautomaadi ja lisame kohvid
        Kohv latte = new Kohv("Latte", 0.15, 0.25, 2.49);
        Kohv espresso = new Kohv("Espresso", 0, 0.2, 0.99);
        Kohv Americano = new Kohv("Americano", 0, 0.4, 1.99);

        Kohviautomaat kohviautomaat = new Kohviautomaat(0.5, 1);
        kohviautomaat.lisaKohv(latte);
        kohviautomaat.lisaKohv(espresso);
        kohviautomaat.lisaKohv(Americano);


        // loome skänneri ja küsime kasutajalt raha
        Scanner scanner = new Scanner(System.in);
        System.out.println("Sisestage palju teil raha on: ");
        double raha;
        // kontrollime, kas kasutaja sisestas numbri
        while (!scanner.hasNextDouble()) {
            System.out.println("Vale sisend");
            System.out.println("Sisestage palju teil raha on: ");
            scanner.next();
        }
        raha = scanner.nextDouble();
        if (raha < 0) {
            System.out.println("Sisestasite negatiivse summa, proovige uuesti: ");
            raha = scanner.nextDouble();
        }

        // küsime kasutajalt, kas ta soovib kasutada kohvi- või snäkiautomaati või lõpetada
        while (true) {
            System.out.println("Sisestage '1' kui soovite kasutada kohviautomaati");
            System.out.println("Sisestage '2' kui soovite kasutada snäkiautomaati");
            System.out.println("Sisestage '3' kui soovite lõpetada");

            int valik;

            // kontrollime, kas kasutaja sisestas numbri
            while (!scanner.hasNextInt()) {
                System.out.println("Vale sisend");
                System.out.println("Sisestage '1' kui soovite kasutada kohviautomaati");
                System.out.println("Sisestage '2' kui soovite kasutada snäkiautomaati");
                System.out.println("Sisestage '3' kui soovite lõpetada");
                scanner.next();
            }
            valik = scanner.nextInt();

            // kui kasutaja soovib lõpetada, siis lõpetame programmi
            if (valik == 3) break;

            // kui kasutaja soovib kasutada kohviautomaati, siis küsime kasutajalt, millist kohvi ta soovib
            else if (valik == 1) {
                while (true) {
                    // kui masin on rikkis, siis lõpetame programmi
                    if (kohviautomaat.getKasMasinOnRikkis()) {
                        System.out.println("Masin on rikkis!");
                        break;
                    }
                    // väljastame kohvide hinnad
                    System.out.println("Valik: ");
                    kohviautomaat.väljastaToodeteHinnad();
                    System.out.println();

                    // kontrollime, kas kasutajal on piisavalt raha
                    if (kohviautomaat.kontroll(raha)) {
                        System.out.println("Teil pole piisavalt raha ühegi kohvi jaoks");
                        break;
                    }

                    // küsime kasutajalt, millist kohvi ta soovib
                    System.out.println("Sisestage valiku number, mida soovite osta: ");
                    int kohviNr = scanner.nextInt();
                    raha = kohviautomaat.sooritaOst(kohviNr, raha);
                    System.out.println();

                    // küsime kasutajalt, kas ta soovib veel kohvi osta
                    System.out.println("Kas soovite veel kohvi osta? jah/ei");
                    String vastus = scanner.next();
                    if (vastus.equals("ei")) break;
                    if (!vastus.equals("jah")) {
                        System.out.println("Vale sisend");
                        break;
                    }
                }
                // kui kasutaja soovib kasutada snäkiautomaati, siis küsime kasutajalt, millist toodet ta soovib
            } else if (valik == 2) {
                while (true) {
                    // kui masin on rikkis, siis lõpetame programmi
                    if (snäkiautomaat.getKasMasinOnRikkis()) {
                        System.out.println("Masin on rikkis!");
                        break;
                    }

                    // väljastame toodete hinnad
                    System.out.println("Olemasolevad tooted: ");
                    snäkiautomaat.väljastaToodeteHinnad();

                    // kontrollime, kas kasutajal on piisavalt raha
                    if (snäkiautomaat.kontroll(raha)) {
                        System.out.println("Teil pole raha ühegi toote jaoks, kas soovite oma õnne proovida ja masinat raputada?");
                        System.out.println("Sisesta 1 kui soovid, 0 kui ei soovi.");

                        // kasutaja soovib masinat raputada
                        int soovidEiSoovi = scanner.nextInt();
                        if (soovidEiSoovi == 0) break;
                        else {
                            while (!snäkiautomaat.getKasMasinOnRikkis()) {
                                // kasutaja raputab masinat
                                if (snäkiautomaat.raputa()) {
                                    System.out.println("Vedas. Saite endale ühe " + snäkiautomaat.misTooteSaid());
                                    System.out.println("Soovite korra veel proovida?");
                                    System.out.println("Sisesta 1 kui soovid, 0 kui ei soovi.");
                                    soovidEiSoovi = scanner.nextInt();
                                    if (soovidEiSoovi == 0) break;
                                } else {
                                    System.out.println("Seekord ei vedanud, kas soovid veel proovida?");
                                    System.out.println("Sisesta 1 kui soovid, 0 kui ei soovi.");
                                    soovidEiSoovi = scanner.nextInt();
                                    if (soovidEiSoovi == 0) break;
                                }
                            }
                            // kui masin on rikkis, siis lõpetame programmi
                            if (snäkiautomaat.getKasMasinOnRikkis())
                                System.out.println("Raputasite liiga palju masin on rikkis!");
                            break;
                        }
                    }

                    // küsime kasutajalt, millist toodet ta soovib
                    System.out.println("Sisestage toote number, mida soovite osta: ");
                    int tooteNumber = scanner.nextInt();
                    raha = snäkiautomaat.sooritaOst(tooteNumber, raha);

                    // küsime kasutajalt, kas ta soovib veel osta
                    System.out.println("Kas soovite veel osta? jah/ei");
                    String vastus = scanner.next();
                    if (vastus.equals("ei")) break;
                    if (!vastus.equals("jah")) {
                        System.out.println("Vale sisend");
                        break;
                    }
                }
            } else System.out.println("Vale sisend");
        }
        scanner.close();

    }

}
