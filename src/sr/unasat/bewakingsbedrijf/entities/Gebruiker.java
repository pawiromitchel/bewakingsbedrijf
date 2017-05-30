package sr.unasat.bewakingsbedrijf.entities;

/**
 * Created by mitchel on 5/25/17.
 */
public class Gebruiker {
    private int id;
    private Rol rol;
    private String voornaam,achternaam,adres,woonplaats,idnummer,geslacht;

    public Gebruiker(int id, Rol rol, String voornaam, String achternaam, String adres, String woonplaats, String idnummer, String geslacht) {
        this.id = id;
        this.rol = rol;
        this.voornaam = voornaam;
        this.achternaam = achternaam;
        this.adres = adres;
        this.woonplaats = woonplaats;
        this.idnummer = idnummer;
        this.geslacht = geslacht;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public String getVoornaam() {
        return voornaam;
    }

    public void setVoornaam(String voornaam) {
        this.voornaam = voornaam;
    }

    public String getAchternaam() {
        return achternaam;
    }

    public void setAchternaam(String achternaam) {
        this.achternaam = achternaam;
    }

    public String getAdres() {
        return adres;
    }

    public void setAdres(String adres) {
        this.adres = adres;
    }

    public String getWoonplaats() {
        return woonplaats;
    }

    public void setWoonplaats(String woonplaats) {
        this.woonplaats = woonplaats;
    }

    public String getIdnummer() {
        return idnummer;
    }

    public void setIdnummer(String idnummer) {
        this.idnummer = idnummer;
    }

    public String getGeslacht() {
        return geslacht;
    }

    public void setGeslacht(String geslacht) {
        this.geslacht = geslacht;
    }
}
