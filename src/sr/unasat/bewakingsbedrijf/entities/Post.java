package sr.unasat.bewakingsbedrijf.entities;

/**
 * Created by mitchel on 5/25/17.
 */
public class Post {
    private int id;
    private String locatie;

    public Post(int id, String locatie) {
        this.id = id;
        this.locatie = locatie;
    }

    public Post() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLocatie() {
        return locatie;
    }

    public void setLocatie(String locatie) {
        this.locatie = locatie;
    }
}
