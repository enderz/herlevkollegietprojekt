package Model;

/**
 * Created by Janus on 17-05-2017.
 */
public class KlageStatus {
    private int værelse;
    private String navn;
    private int antalKlager;

    public KlageStatus(){
        værelse = 0;
        navn = "";
        antalKlager = 0;
    }
    public KlageStatus(int værelse, String navn, int antalKlager){
        this.værelse = værelse;
        this.navn = navn;
        this.antalKlager = antalKlager;
    }

    public int getVærelse() {
        return værelse;
    }

    public void setVærelse(int værelse) {
        this.værelse = værelse;
    }

    public String getNavn() {
        return navn;
    }

    public void setNavn(String navn) {
        this.navn = navn;
    }

    public int getAntalKlager() {
        return antalKlager;
    }

    public void setAntalKlager(int antalKlager) {
        this.antalKlager = antalKlager;
    }
}
