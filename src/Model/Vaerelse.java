package Model;
/**
 * Created by Herlev Kollegiet Udvikler on 12-05-2017.
 */
public class Vaerelse
{
    public int vaerelseNr;
    public String sal;

    public Vaerelse(int roomNr, String floor)
    {
        vaerelseNr = roomNr;
        sal = floor;
    }

    public int getVaerelseNr() {
        return vaerelseNr;
    }

    public void setVaerelseNr(int vaerelseNr) {
        this.vaerelseNr = vaerelseNr;
    }

    public String getSal() {
        return sal;
    }

    public void setSal(String sal) {
        this.sal = sal;
    }


}
