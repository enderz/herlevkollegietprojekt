package Model;

import java.util.Date;

/**
 * Created by Janus on 15-05-2017.
 */
public class VaerelsesUdlejning {
    private int værelse;
    private Date udlejningsdato;
    private Date behandlingsdato;
    private String behandlerInitialer;

    public VaerelsesUdlejning(){
        værelse = 0;
        udlejningsdato = new Date();
        behandlingsdato = new Date();
        behandlerInitialer = "";
    }

    public VaerelsesUdlejning(int værelse, Date udlejningsdato, Date behandlingsdato, String behandlerInitialer){
        this.værelse = værelse;
        this.udlejningsdato = udlejningsdato;
        this.behandlingsdato = behandlingsdato;
        this.behandlerInitialer = behandlerInitialer;
    }
    public VaerelsesUdlejning(int værelse, Date udlejningsdato){
        this.værelse = værelse;
        this.udlejningsdato = udlejningsdato;
    }

    public int getVærelse() {
        return værelse;
    }

    public void setVærelse(int værelse) {
        this.værelse = værelse;
    }

    public Date getUdlejningsdato() {
        return udlejningsdato;
    }

    public void setUdlejningsdato(Date udlejningsdato) {
        this.udlejningsdato = udlejningsdato;
    }

    public Date getBehandlingsdato() {
        return behandlingsdato;
    }

    public void setBehandlingsdato(Date behandlingsdato) {
        this.behandlingsdato = behandlingsdato;
    }

    public String getBehandlerInitialer() {
        return behandlerInitialer;
    }

    public void setBehandlerInitialer(String behandlerInitialer) {
        this.behandlerInitialer = behandlerInitialer;
    }
}
