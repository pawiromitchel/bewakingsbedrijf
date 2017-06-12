package sr.unasat.bewakingsbedrijf.app;

import sr.unasat.bewakingsbedrijf.entities.Gebruiker;
import sr.unasat.bewakingsbedrijf.entities.Rol;
import sr.unasat.bewakingsbedrijf.repositories.GebruikerRepository;
import sr.unasat.bewakingsbedrijf.repositories.RolRepository;
import sr.unasat.bewakingsbedrijf.repositories.ShiftRepository;

/**
 * Created by mitchel on 5/30/17.
 */
public class App {
    public static void main(String[] args) {
//        RolRepository rolRepository = new RolRepository();
//        Rol rol = rolRepository.selectRecord(1);
//
//        Gebruiker gebruiker = new Gebruiker(0, rol, "Admin", "Admin", "Para", "Domburg", "FO 003090", "M", "1996-09-01");
//
//        GebruikerRepository gebruikerRepository = new GebruikerRepository();
//        gebruikerRepository.insertRecord(gebruiker);

        ShiftRepository shiftRepository = new ShiftRepository();
        shiftRepository.selectAll();
    }
}
