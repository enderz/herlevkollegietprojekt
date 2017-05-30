package Model;

/**
 * Created by Herlev Kollegiet Udvikler on 08-05-2017.
 */
public class Bruger {

    private int brugerId;
    private String navn;
    private String email;
    private String brugernavn;
    private String password;
    private String rolle;

    public Bruger(){}

    public int getBrugerId() {
        return brugerId;
    }

    public void setBrugerId(int brugerId) {
        this.brugerId = brugerId;
    }

    public String getNavn() {
        return navn;
    }

    public void setNavn(String navn) {
        this.navn = navn;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBrugernavn(){
        return brugernavn;
    }
    public void setBrugernavn(String brugernavn){
        this.brugernavn = brugernavn;
    }

    public String getRolle(){
        return rolle;
    }
    public void setRolle(String rolle){
        this.rolle = rolle;
    }

}
