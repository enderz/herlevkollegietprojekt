package Model;
import java.util.Date;

/**
 * Created by Janus on 18-05-2017.
 */
public class Protokol {
    private Date dato;
    private String andenSalsTilstedeværelse;
    private String tredjeSalsTilstedeværelse;
    private String fjerdeSalsTilstedeværelse;
    private String femteSalsTilstedeværelse;
    private String sjetteSalsTilstedeværelse;


    public Protokol(){
        dato = new Date();
        andenSalsTilstedeværelse = "";
        tredjeSalsTilstedeværelse = "";
        fjerdeSalsTilstedeværelse = "";
        femteSalsTilstedeværelse = "";
        sjetteSalsTilstedeværelse = "";
    }
    public Protokol(Date dato,String andenSalsTilstedeværelse, String tredjeSalsTilstedeværelse, String fjerdeSalsTilstedeværelse, String femteSalsTilstedeværelse, String sjetteSalsTilstedeværelse) {
        this.dato = dato;
        this.andenSalsTilstedeværelse = andenSalsTilstedeværelse;
        this.tredjeSalsTilstedeværelse = tredjeSalsTilstedeværelse;
        this.fjerdeSalsTilstedeværelse = fjerdeSalsTilstedeværelse;
        this.femteSalsTilstedeværelse = femteSalsTilstedeværelse;
        this.sjetteSalsTilstedeværelse = sjetteSalsTilstedeværelse;
    }

    public Date getDato() {
        return dato;
    }

    public void setDato(Date dato) {
        this.dato = dato;
    }

    public String getAndenSalsTilstedeværelse() {
        return andenSalsTilstedeværelse;
    }

    public void setAndenSalsTilstedeværelse(String andenSalsTilstedeværelse) {
        this.andenSalsTilstedeværelse = andenSalsTilstedeværelse;
    }

    public String getTredjeSalsTilstedeværelse() {
        return tredjeSalsTilstedeværelse;
    }

    public void setTredjeSalsTilstedeværelse(String tredjeSalsTilstedeværelse) {
        this.tredjeSalsTilstedeværelse = tredjeSalsTilstedeværelse;
    }

    public String getFjerdeSalsTilstedeværelse() {
        return fjerdeSalsTilstedeværelse;
    }

    public void setFjerdeSalsTilstedeværelse(String fjerdeSalsTilstedeværelse) {
        this.fjerdeSalsTilstedeværelse = fjerdeSalsTilstedeværelse;
    }

    public String getFemteSalsTilstedeværelse() {
        return femteSalsTilstedeværelse;
    }

    public void setFemteSalsTilstedeværelse(String femteSalsTilstedeværelse) {
        this.femteSalsTilstedeværelse = femteSalsTilstedeværelse;
    }

    public String getSjetteSalsTilstedeværelse() {
        return sjetteSalsTilstedeværelse;
    }

    public void setSjetteSalsTilstedeværelse(String sjetteSalsTilstedeværelse) {
        this.sjetteSalsTilstedeværelse = sjetteSalsTilstedeværelse;
    }
}
