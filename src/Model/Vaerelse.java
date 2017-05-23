package Model;
/**
 * Created by Ender on 15-05-2017.
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
