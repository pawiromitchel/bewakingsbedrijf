package sr.unasat.bewakingsbedrijf.entities;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by mitchel on 5/25/17.
 */
public class Rooster {
    public int id;
    public Gebruiker gebruiker;
    public Post post;
    public Shift shift;
    public String datum;

    public Rooster(int id, Gebruiker gebruiker, Post post, Shift shift, String datum) {
        this.id = id;
        this.gebruiker = gebruiker;
        this.post = post;
        this.shift = shift;
        this.datum = datum;
    }


    public Rooster(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Gebruiker getGebruiker() {
        return gebruiker;
    }

    public void setGebruiker(Gebruiker gebruiker) {
        this.gebruiker = gebruiker;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public Shift getShift() {
        return shift;
    }

    public void setShift(Shift shift) {
        this.shift = shift;
    }

    public String getDatum() {
        return datum;
    }

    public void setDatum(String datum) {
        this.datum = datum;
    }

    @Override
    public String toString()
    {
        return ToStringBuilder.reflectionToString(this);
    }
}
