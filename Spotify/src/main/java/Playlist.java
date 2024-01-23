import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

public class Playlist {
    private String jmeno;
    private Song [] seznam;
    private int index = 0;

    public Playlist(String jmeno, int kapacita) {
        this.jmeno = jmeno;
        this.seznam = new Song[kapacita];
    }

    public void vypisSongy() {
        for (int i = 0; i < seznam.length; i++) {
            if (seznam[i] != null) {
                System.out.println(seznam[i]);
            }
        }
    }

    /*
     * Přetížená metoda který vypisuje klasicky songy, jen kontroluje jestli ta songa má požadovaný žánr
     * @param zanr
     */
    public void vypisSongy(String zanr) {
        for (int i = 0; i < seznam.length; i++) {
            if (seznam[i] != null) {
                if (seznam[i].getZanr().equals(zanr)) {
                    System.out.println(seznam[i]);
                }
            }
        }
    }

    /*
     * Klasika přidání, když je plno ukončí to metodu
     * @param song
     */
    public void pridejSongu(Song song) {
        if (index >= seznam.length) {
            System.out.println("Plno");
            return;
        }

        seznam[index] = song;
        index++;
    }

    /*
     * Metoda pro zamíchání playlistu, nejjednodušší řešení je, pokud se použije ArrayList už jako výchozí struktura
     * pro uchovávání písniček. Nicméně vytvoříme si nový arraylist do kterého přidáme všechny písničky ze stávajícího
     * seznamu, následně pomocí třídy collections a její metody shuffle můžeme List zamíchat(náhodně). Následně je pak
     * zpátky do našeho seznamu přepíšeme songy v novém pořadí. PS funguje to
     */
    public void nahodneZamichejSongy() {
        // vytvoření listu, <Song> je datový typ dat který uchovává
        ArrayList<Song> list = new ArrayList<>();

        // přidání písniček do listu
        for (Song song : seznam) {
            list.add(song);
        }

        // zamíchání
        Collections.shuffle(list);

        // přepsání a vrácení písniček v novém pořadí
        for (int i = 0; i < list.size(); i++) {
            seznam[i] = list.get(i);
        }
    }

    /*
     * Metoda pro získání celkové délky playlistu
     * @return - vrací délku playlistu v sekundách
     */
    public int delkaPlaylist() {
        int delka = 0;

        for (int i = 0; i < seznam.length; i++) {
            if (seznam[i] != null) {
                // přičtení délky písničky do celkové délky - getDelka vrací rádoby třídu, díky tomu můžeš zavolat její
                // metodu prevedNaSekundy
                delka += seznam[i].getDelka().prevedNaSekundy();
            }
        }

        return delka;
    }

    /*
     * Prakticky to samé jako delkaPlaylist akorát jen hledáš jednu skladbu která je nejdelší
     * @return - vrací Song instanci
     */
    public Song najdiNejdelsiSkladbu() {
        int delka = 0;
        // song = null, pouze počáteční stav který je následně přepsán, pokud by však byl seznam prázdný vrátí null
        Song song = null;

        for (int i = 0; i < seznam.length; i++) {
            if (seznam[i] != null) {
                if (delka < seznam[i].getDelka().prevedNaSekundy()) {
                    delka = seznam[i].getDelka().prevedNaSekundy();
                    song = seznam[i];
                }
            }
        }

        return song;
    }

    public void ulozDoSouboru(File Soubor) {
        BufferedWriter bw = null;

        try {
            // vytvoření nového writeru, musí být v závorce FileWriter který má jako parametr název souboru a to false
            // je pouze pro to aby se přepisoval soubor při zavolání metody
            bw = new BufferedWriter(new FileWriter(Soubor, false));
            // nahoru se zapíše název playlistu
            bw.write(this.jmeno);
            // slouží k zapisování na nový řádek
            bw.write(System.lineSeparator());

            // projití celého seznamu
            for (int i = 0; i < seznam.length; i++) {
                if (seznam[i] != null) {
                    // pomocí getteru sestavím string který se zapíše do excelu, středním odděluje hodnoty aby byly v
                    // samostatných buňkách
                    bw.write(seznam[i].getInterpret() + ";" + seznam[i].getNazev() + ";" + seznam[i].getDelka() + ";" + seznam[i].getZanr());
                    bw.write(System.lineSeparator());
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
            // v bloku finally je vždy potřeba uzavřít proud dat pomocí metody close() - funguje jak na writer tak na
            // reader
        } finally {
            try {
                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public void nacistPlaylistZeSouboru(File soubor) {
        BufferedReader br = null;
        // počáteční inicializace řádku který čte reader
        String line = "";
        // pole, do kterého se dělí hodnoty načtené z jednoho řádku, potřebujeme ho pro abychom pak z hodnot vytvořili
        // novou písničku
        String [] values;
        // upřímně nemám ponětí k čemu mi to kurva bude
        String nazevPlaylistu;

        try {
            // zase vytvoření novýho readeru
            br = new BufferedReader(new FileReader(soubor));
            // hodnota pro kontrolu prvního řádku ve kterém je napsán pouze název playlistu
            boolean prvniRadek = true;

            // while cyklus ve kterém dokud br.readLine najde řádek co není prázdný tak pojede
            while ((line = br.readLine()) != null) {
                // funguje pro skipnutí rozdělování hodnot na prvním řádku
                if (prvniRadek == true) {
                    nazevPlaylistu = line;
                    prvniRadek = false;
                    continue;
                }

                // tyto dvě bylo potřeba dát sem aby byly vidět protože na tom jednom řádku nebyla délka skladby
                String delkaSkladby = "";
                String zanr = "";

                // tímhle do pole values rozdělím hodnoty z jednoho řádku, oddělovač je středník ; a uloží se hodnoty jednolitvě
                // do pole, pole je vždy nové s každým řádkem
                values = line.split(";");
                //získání hodnot pomocí indexu z pole
                String zpevak = values[0];
                String skladba = values[1];
                // pokud tam teda chybí ta délka skladby tak se nastaví na 0
                if (values.length == 3) {
                    delkaSkladby = "0:0";
                    zanr = values[2];
                } else {
                    delkaSkladby = values[2];
                    zanr = values[3];
                }

                // cas je spojený jako jeden string ve formátu např 3:45 takže z toho číslo nevyčtu ani kdybych chtěl
                // proto zase rozdělím ten čas jen ted pomocí oddělovače :
                // to se uloží do nového pole pouze pro cas
                String [] casy = delkaSkladby.split(":");
                // první tam jsou minuty ty přetypuju na int
                int minuty = Integer.parseInt(casy[0]);
                // to samé se sekundami
                int sekundy = Integer.parseInt(casy[1]);

                // musím si vytvořit pomocí minut a sekund nový objekt z třídy čas, který potřebuju do konstruktoru
                // nové pisničky
                Cas cas = new Cas(minuty, sekundy);
                // už jen vytvořím novou písničku
                Song song = new Song(zpevak, skladba, cas, zanr);

                // a přidám ji do seznamu
                pridejSongu(song);

            }

            // tohle už je stejný jako vejš
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
