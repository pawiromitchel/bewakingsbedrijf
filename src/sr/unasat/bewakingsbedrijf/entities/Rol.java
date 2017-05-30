package sr.unasat.bewakingsbedrijf.entities;

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
}
