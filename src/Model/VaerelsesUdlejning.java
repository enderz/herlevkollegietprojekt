package Model;

import java.util.Date;

/**
 * Created by Herlev Kollegiet Udvikler on 07-05-2017..
 */
public class VaerelsesUdlejning extends Vaerelse
{

    public Date udlejningsdato;
    public Date behandlingsdato;
    public String behandlerInitialer;

    public VaerelsesUdlejning(int vaerelseNr, String sal, Date udlejningsdato, Date behandlingsdato, String behandlerInitialer){
        super(vaerelseNr, sal);
        this.udlejningsdato = udlejningsdato;
        this.behandlingsdato = behandlingsdato;
        this.behandlerInitialer = behandlerInitialer;
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
