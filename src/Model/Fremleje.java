package Model;

import java.util.Date;

/**
 * Created by Herlev Kollegiet Udvikler on 16-05-2017.
 */
public class Fremleje {

    private int værelse;
    private String fremlejetager;
    private String fremlejer;
    private Date startDato;
    private Date slutDato;


    public Fremleje(int værelse, String fremlejetager, String fremlejer, Date startDato, Date slutDato){
        this.værelse = værelse;
        this.fremlejetager = fremlejetager;
        this.fremlejer = fremlejer;
        this.startDato = startDato;
        this.slutDato = slutDato;
    }

    public int getVærelse() {
        return værelse;
    }

    public void setVærelse(int værelse) {
        this.værelse = værelse;
    }

    public String getFremlejetager() {
        return fremlejetager;
    }

    public void setFremlejetager(String fremlejetager) {
        this.fremlejetager = fremlejetager;
    }

    public String getFremlejer() {
        return fremlejer;
    }

    public void setFremlejer(String fremlejer) {
        this.fremlejer = fremlejer;
    }

    public Date getStartDato() {
        return startDato;
    }

    public void setStartDato(Date startDato) {
        this.startDato = startDato;
    }

    public Date getSlutDato() {
        return slutDato;
    }

    public void setSlutDato(Date slutDato) {
        this.slutDato = slutDato;
    }
}
