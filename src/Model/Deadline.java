package Model;

/**
 * Created by Janus on 04-05-2017.
 */

//Get and set metoder for påmindelses/deadlines fanen
public class Deadline {

    private int værelse;
    private String deadline;
    private String dato;

    public Deadline(){
        this.værelse = 0;
        this.deadline = "";
        this.dato = "";

    }
    public Deadline( String dato, String deadline){
        this.deadline = deadline;
        this.dato = dato;
    }
    public Deadline( String dato, int værelse, String påmindelse){
        this.værelse = værelse;
        this.deadline = påmindelse;
        this.dato = dato;
    }

    public int getVærelse() {
        return værelse;
    }

    public void setVærelse(int værelse) {
        this.værelse = værelse;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String navn) {
        this.deadline = navn;
    }

    public String getDato() {
        return dato;
    }

    public void setDato(String indflytningsdato) {
        this.dato = dato;
    }
}
