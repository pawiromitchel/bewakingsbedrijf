package sr.unasat.bewakingsbedrijf.entities;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by mitchel on 5/25/17.
 */
public class Rol {
    public int id;
    public String naam;

    public Rol(int id, String naam) {
        this.id = id;
        this.naam = naam;
    }

    public Rol(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    @Override
    public String toString()
    {
        return ToStringBuilder.reflectionToString(this);
    }
}
