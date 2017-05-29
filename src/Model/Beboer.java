package Model;

import java.util.Date;

/**
 * Created by Ender on 05-05-2017.
 */
public class Beboer {

    private int vaerelseNr;
    private String navn;
    private Date indflytdato;
    private String uddannelsested;
    private Date uddannelsestart;
    private Date uddannelseafslut;
    private String uddannelseretning;
    private String email;
    private String kontrolStatus;
    private String slutStudieMaaned;
    private String indflytMaaned;

    public Beboer(){
    }

    public Beboer(int vaerelseNr, String navn, Date indflytdato, String uddannelsested, Date uddannelsestart,
                  Date uddannelseafslut, String uddannelseretning, String email, String kontrolStatus, String slutStudieMaaned, String indflytMaaned )
    {
        this.vaerelseNr = vaerelseNr;
        this.navn = navn;
        this.indflytdato = indflytdato;
        this.uddannelsested = uddannelsested;
        this.uddannelsestart = uddannelsestart;
        this.uddannelseafslut = uddannelseafslut;
        this.uddannelseretning = uddannelseretning;
        this.email = email;
        this.kontrolStatus = kontrolStatus;
        this.slutStudieMaaned = slutStudieMaaned;
        this.indflytMaaned = indflytMaaned;


    }

    public int getVaerelseNr() {
        return vaerelseNr;
    }

    public void setVaerelseNr(int vaerelseNr) {
        this.vaerelseNr = vaerelseNr;
    }

    public String getNavn() {
        return navn;
    }

    public void setNavn(String navn) {
        this.navn = navn;
    }

    public Date getIndflytdato() {
        return indflytdato;
    }

    public void setIndflytdato(Date indflytdato) {
        this.indflytdato = indflytdato;
    }

    public String getUddannelsested() {
        return uddannelsested;
    }

    public void setUddannelsested(String uddannelsested) {
        this.uddannelsested = uddannelsested;
    }

    public Date getUddannelsestart() {
        return uddannelsestart;
    }

    public void setUddannelsestart(Date uddannelsestart) {
        this.uddannelsestart = uddannelsestart;
    }

    public Date getUddannelseafslut() {
        return uddannelseafslut;
    }

    public void setUddannelseafslut(Date uddannelseafslut) {
        this.uddannelseafslut = uddannelseafslut;
    }

    public String getUddannelseretning() {
        return uddannelseretning;
    }

    public void setUddannelseretning(String uddannelseretning) {
        this.uddannelseretning = uddannelseretning;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getKontrolStatus() {
        return kontrolStatus;
    }

    public void setKontrolStatus(String kontrolStatus) {
        this.kontrolStatus = kontrolStatus;
    }

    public String getSlutStudieMaaned() {
        return slutStudieMaaned;
    }

    public void setSlutStudieMaaned(String slutStudieMaaned) {
        this.slutStudieMaaned = slutStudieMaaned;
    }

    public String getIndflytMaaned() {
        return indflytMaaned;
    }

    public void setIndflytMaaned(String indflytMaaned) {
        this.indflytMaaned = indflytMaaned;
    }
}
