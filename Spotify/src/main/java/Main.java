import java.io.File;

public class Main {
    public static void main(String[] args) {
        // novej playlist
        Playlist sracka = new Playlist("pl", 20);

        // novej soubor typu File na data
        File soubor = new File("spotifydata.csv");
        sracka.nacistPlaylistZeSouboru(soubor);

        // test výpisu a zamíchání
        sracka.vypisSongy();
        System.out.println("");
        sracka.nahodneZamichejSongy();
        sracka.vypisSongy();
        System.out.println("");
        sracka.vypisSongy("rock");

        // zjistění delky playlistu
        int delkaPl = sracka.delkaPlaylist();
        System.out.println("Celková délka playlistu = " + String.format("%02d:%02d", (delkaPl/60), (delkaPl%60)));

        // delka/60 pro získání minut, délka%60 je pro získání zbytku sekund
        System.out.println("Celková délka playlistu = " + (delkaPl/60) + ":" + (delkaPl%60));
        System.out.println(sracka.najdiNejdelsiSkladbu());

        // zapis do souboru
        File novy = new File("novy.csv");
        sracka.ulozDoSouboru(novy);
    }
}