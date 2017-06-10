package sr.unasat.bewakingsbedrijf.repositories;

import sr.unasat.bewakingsbedrijf.entities.Gebruiker;
import sr.unasat.bewakingsbedrijf.entities.Rol;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mitchel on 5/30/17.
 */
public class GebruikerRepository {
    private Connection connect;

    public GebruikerRepository() {
        initialize();
    }

    public void initialize() {
        try {
            // Onderstaande zoekt de juist class op uit de library en laad het in JVM
            Class.forName("com.mysql.jdbc.Driver");
            // De connectie wordt vervolgens gemaakt naar de database middels de juiste authenticatie
            connect = DriverManager.getConnection("jdbc:mysql://localhost/bewakingsbedrijf?user=root&password=");
        } catch (ClassNotFoundException e) {
            System.out.println("De class is niet gevonden!");
        } catch (SQLException e) {
            System.out.println("Er is een SQL fout ontstaan!");
            e.printStackTrace();
        }
    }

    public boolean isInitialised() {
        if (connect != null) {
            return true;
        }
        return false;
    }

    public List<Gebruiker> selectAll() {

        List<Gebruiker> outputList = new ArrayList();
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            // Construeer een statement voor het uitvoeren van een SQL Query
            statement = connect.createStatement();
            // Voer de SQL statement uit en verzamel de output in de resultset
            resultSet = statement.executeQuery("select * from gebruikers");

            while (resultSet.next()) {
                Gebruiker gebruiker = new Gebruiker();

                int id = resultSet.getInt("id");
                String achternaam = resultSet.getString("achternaam");
                String voornaam = resultSet.getString("voornaam");
                String adres = resultSet.getString("adres");
                String woonplaats = resultSet.getString("woonplaats");
                String idnummer = resultSet.getString("idnummer");
                String geslacht = resultSet.getString("geslacht");
                String geboortedatum = resultSet.getString("geboortedatum");
                int rol_id = resultSet.getInt("rol_id");

                // Maak een student instantie en print deze instantie
                gebruiker.setId(id);
                gebruiker.setAchternaam(achternaam);
                gebruiker.setVoornaam(voornaam);
                gebruiker.setAdres(adres);
                gebruiker.setWoonplaats(woonplaats);
                gebruiker.setIdnummer(idnummer);
                gebruiker.setGeslacht(geslacht);
                gebruiker.setGeslacht(geboortedatum);

                if(rol_id > 0){
                    gebruiker.setRol(new RolRepository().selectRecord(rol_id));
                }

                System.out.println(gebruiker);
                outputList.add(gebruiker);
            }
        } catch (SQLException e) {
            System.out.println("Er is een SQL fout ontstaan tijdens de select * statement!");
        }

        return outputList;
    }
    public Gebruiker selectRecord(int recordId) {

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Gebruiker gebruiker = new Gebruiker();

        try {
            // Statements allow to issue SQL queries to the database
            preparedStatement = connect.prepareStatement("select * from gebruikers where id = ?");
            // Result set get the result of the SQL query
            preparedStatement.setInt(1, recordId);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                // It is possible to get the columns via name
                // also possible to get the columns via the column number
                // which starts at 1
                // e.g. resultSet.getSTring(2);

                int id = resultSet.getInt("id");
                String achternaam = resultSet.getString("achternaam");
                String voornaam = resultSet.getString("voornaam");
                String adres = resultSet.getString("adres");
                String woonplaats = resultSet.getString("woonplaats");
                String idnummer = resultSet.getString("idnummer");
                String geslacht = resultSet.getString("geslacht");
                String geboortedatum = resultSet.getString("geboortedatum");
                int rol_id = resultSet.getInt("rol_id");

                // Maak een student instantie en print deze instantie
                gebruiker.setId(id);
                gebruiker.setAchternaam(achternaam);
                gebruiker.setVoornaam(voornaam);
                gebruiker.setAdres(adres);
                gebruiker.setWoonplaats(woonplaats);
                gebruiker.setIdnummer(idnummer);
                gebruiker.setGeslacht(geslacht);
                gebruiker.setGeslacht(geboortedatum);

                if(rol_id > 0){
                    gebruiker.setRol(new RolRepository().selectRecord(rol_id));
                }

                System.out.println(gebruiker);
            }
        } catch (SQLException e) {
            System.out.println("Er is een SQL fout ontstaan tijdens de select statement!");
        }

        return gebruiker;
    }
    public int updateRecord(Gebruiker gebruiker) {

        PreparedStatement preparedStatement = null;
        int result = 0;

        try {
            preparedStatement = connect.prepareStatement("update gebruikers set " +
                    " achternaam = ?, " +
                    " voornaam = ?,"+
                    " adres = ?," +
                    " woonplaats = ?," +
                    " idnummer=?," +
                    " geslacht=?," +
                    " geboortedatum=?," +
                    " rol_id=? " +
                    "where id = ?");
            preparedStatement.setString(1, gebruiker.getAchternaam());
            preparedStatement.setString(2, gebruiker.getVoornaam());
            preparedStatement.setString(3, gebruiker.getAdres());
            preparedStatement.setString(4, gebruiker.getWoonplaats());
            preparedStatement.setString(5, gebruiker.getIdnummer());
            preparedStatement.setString(6, gebruiker.getGeslacht());
            preparedStatement.setString(7, gebruiker.getGeboortedatum());

            if (gebruiker.getRol() != null){
                preparedStatement.setInt(8, gebruiker.getRol().getId());
            }

            preparedStatement.setInt(9, gebruiker.getId());

            // Voer de statement uit en haal het resultaat op
            result = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Er is een SQL fout tijdens het updaten van een record!");
            e.printStackTrace();
        }

        return result;
    }

    public int deleteRecord(int recordId) {

        PreparedStatement preparedStatement = null;
        int result = 0;

        try {
            preparedStatement = connect.prepareStatement("delete from gebruikers where id = ?");
            preparedStatement.setInt(1, recordId);

            // Voer de statement uit en haal het resultaat op
            result = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Er is een SQL fout tijdens deleten van een record!");
            e.printStackTrace();
        }

        return result;
    }

    public int insertRecord(Gebruiker gebruiker) {

        PreparedStatement preparedStatement = null;
        int result = 0;

        try {
            preparedStatement = connect.prepareStatement("insert into gebruikers values (NULL, ?,?,?,?,?,?,?,?)");
            preparedStatement.setString(1, gebruiker.getAchternaam());
            preparedStatement.setString(2, gebruiker.getVoornaam());
            preparedStatement.setString(3, gebruiker.getAdres());
            preparedStatement.setString(4, gebruiker.getWoonplaats());
            preparedStatement.setString(5, gebruiker.getIdnummer());
            preparedStatement.setString(6, gebruiker.getGeslacht());
            preparedStatement.setString(7, gebruiker.getGeboortedatum());

            if (gebruiker.getRol() != null){
                preparedStatement.setInt(8, gebruiker.getRol().getId());
            }


            // Voer de statement uit en haal het resultaat op
            result = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Er is een SQL fout tijdens het inserten van een nieuwe record!");
            e.printStackTrace();
        }

        return result;
    }

    public void terminate() {
        try {
            if (connect != null) {
                connect.close();
            }
        } catch (SQLException e) {
            System.out.println("Er is een SQL fout tijdens sluiten van de connectie!");
            e.printStackTrace();
        }
    }

}
