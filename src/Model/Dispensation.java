package Model;

import java.util.Date;

/**
 * Created by Herlev Kollegiet Udvikler on 15-05-2017.
 */
public class Dispensation {
    private int værelse;
    private String navn;
    private Date startDato;
    private String deadline1;
    private String deadline2;
    private String deadline3;
    private Date ophørsDato;

    public Dispensation(){
        værelse = 0;
        navn = "";
        startDato = new Date();
        deadline1 = "";
        deadline2 = "";
        deadline3 = "";
        ophørsDato = new Date();
    }
    public Dispensation(int værelse, String navn, Date startDato, Date ophørsDato){
        this.værelse = værelse;
        this.startDato = startDato;
        this.navn = navn;
        this.ophørsDato = ophørsDato;

    }
    public Dispensation(int værelse, String navn,Date startDato, Date ophørsDato, String deadline1){
        this.værelse = værelse;
        this.navn = navn;
        this.startDato = startDato;
        this.ophørsDato = ophørsDato;
        this.deadline1 = deadline1;
    }
    public Dispensation(int værelse, String navn,Date startDato, Date ophørsDato, String deadline1, String deadline2){
        this.værelse = værelse;
        this.navn = navn;
        this.startDato = startDato;
        this.ophørsDato = ophørsDato;
        this.deadline1 = deadline1;
        this.deadline2 = deadline2;
    }
    public Dispensation(int værelse, String navn,Date startDato, Date ophørsDato, String deadline1, String deadline2, String deadline3){
        this.værelse = værelse;
        this.navn = navn;
        this.startDato = startDato;
        this.ophørsDato = ophørsDato;
        this.deadline1 = deadline1;
        this.deadline2 = deadline2;
        this.deadline3 = deadline3;
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

    public Date getStartDato() {
        return startDato;
    }

    public void setStartDato(Date startDato) {
        this.startDato = startDato;
    }

    public String getDeadline1() {
        return deadline1;
    }

    public void setDeadline1(String deadline1) {
        this.deadline1 = deadline1;
    }

    public String getDeadline2() {
        return deadline2;
    }

    public void setDeadline2(String deadline2) {
        this.deadline2 = deadline2;
    }

    public String getDeadline3() {
        return deadline3;
    }

    public void setDeadline3(String deadline3) {
        this.deadline3 = deadline3;
    }

    public Date getOphørsDato() {
        return ophørsDato;
    }

    public void setOphørsDato(Date ophørsDato) {
        this.ophørsDato = ophørsDato;
    }
}
