public class Cas {
    private int minuty;
    private int sekundy;

    public Cas(int minuty, int sekundy) {
        this.minuty = minuty;
        this.sekundy = sekundy;
    }

    public int getMinuty() {
        return minuty;
    }

    public int getSekundy() {
        return sekundy;
    }

    @Override
    public String toString() {
        // String format pouze pro vizuální stránku, pokud by bylo třeba jen 5 sekund vypíše se jako 05
        return (minuty + ":" + String.format("%02d", + sekundy));
    }

    public int prevedNaSekundy() {
        // vynásobení minut 60 pro převedení na sekundy a přičtení zbývajících sekund
        return ((minuty*60) + sekundy);
    }
}
