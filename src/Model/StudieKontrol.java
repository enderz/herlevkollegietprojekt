package Model;

import java.util.Date;

/**
 * Created by Ender on 26-05-2017.
 */
public class StudieKontrol
{
    private int vaerelseNr;
    private String navn;
    private Date indflytdato;
    private String uddannelsested;
    private Date uddannelsestart;
    private Date uddannelseafslut;
    private String uddannelseretning;
    private String kontrolStatus;

    public StudieKontrol(){
    }

    public StudieKontrol(int vaerelseNr, String navn, Date indflytdato, String uddannelsested, Date uddannelsestart,
                  Date uddannelseafslut, String uddannelseretning, String kontrolStatus)
    {
        this.vaerelseNr = vaerelseNr;
        this.navn = navn;
        this.indflytdato = indflytdato;
        this.uddannelsested = uddannelsested;
        this.uddannelsestart = uddannelsestart;
        this.uddannelseafslut = uddannelseafslut;
        this.uddannelseretning = uddannelseretning;
        this.kontrolStatus = kontrolStatus;

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

    public String getKontrolStatus() {
        return kontrolStatus;
    }

    public void setKontrolStatus(String email) {
        this.kontrolStatus = kontrolStatus;
    }

}
