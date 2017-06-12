package sr.unasat.bewakingsbedrijf.entities;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by mitchel on 6/11/17.
 */
public class Shift {
    private int id;
    private String type, begintijd, eindtijd;

    public Shift(int id, String type, String begintijd, String eindtijd) {
        this.id = id;
        this.type = type;
        this.begintijd = begintijd;
        this.eindtijd = eindtijd;
    }

    public Shift() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBegintijd() {
        return begintijd;
    }

    public void setBegintijd(String begintijd) {
        this.begintijd = begintijd;
    }

    public String getEindtijd() {
        return eindtijd;
    }

    public void setEindtijd(String eindtijd) {
        this.eindtijd = eindtijd;
    }

    @Override
    public String toString()
    {
        return ToStringBuilder.reflectionToString(this);
    }
}
