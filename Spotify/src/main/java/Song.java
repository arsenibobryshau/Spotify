public class Song {
    private String interpret;
    private String nazev;
    private Cas delka;
    private String zanr;

    public Song(String interpret, String nazev, Cas delka, String zanr) {
        this.interpret = interpret;
        this.nazev = nazev;
        this.delka = delka;
        this.zanr = zanr;
    }

    public String getInterpret() {
        return interpret;
    }

    public String getNazev() {
        return nazev;
    }

    public Cas getDelka() {
        return delka;
    }

    public String getZanr() {
        return zanr;
    }

    @Override
    public String toString() {
        return "Song {" +
                "interpret = '" + interpret + '\'' +
                ", nazev = '" + nazev + '\'' +
                ", delka = " + delka +
                ", zanr = '" + zanr + '\'' +
                '}';
    }

    public int casSekundy() {
        return this.delka.prevedNaSekundy();
    }
}
