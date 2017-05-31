package sr.unasat.bewakingsbedrijf.repositories;

import sr.unasat.bewakingsbedrijf.entities.Rol;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mitchel on 5/30/17.
 */
public class RolRepository {
    private Connection connect;

    public RolRepository() {
        initialize();
    }

    public void initialize() {
        try {
            // Onderstaande zoekt de juist class op uit de library en laad het in JVM
            Class.forName("com.mysql.jdbc.Driver");
            // De connectie wordt vervolgens gemaakt naar de database middels de juiste authenticatie
            connect = DriverManager.getConnection("jdbc:mysql://localhost/bewakingsbedrijf?user=root&password=PAWIR0MITCHEL");
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

    public List<Rol> selectAll() {

        List<Rol> outputList = new ArrayList();
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            // Construeer een statement voor het uitvoeren van een SQL Query
            statement = connect.createStatement();
            // Voer de SQL statement uit en verzamel de output in de resultset
            resultSet = statement.executeQuery("select * from rollen");

            while (resultSet.next()) {

                int id = resultSet.getInt("id");
                String naam = resultSet.getString("naam");

                // Maak een student instantie en print deze instantie
                //maak een constructor van Student
                Rol rol = new Rol(id, naam);

                System.out.println(rol);
                outputList.add(rol);
            }
        } catch (SQLException e) {
            System.out.println("Er is een SQL fout ontstaan tijdens de select * statement!");
        }

        return outputList;
    }
    public Rol selectRecord(int recordId) {

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Rol rol = new Rol();

        try {
            // Statements allow to issue SQL queries to the database
            preparedStatement = connect.prepareStatement("select * from bewakingsbedrijf.rollen where id = ?");
            // Result set get the result of the SQL query
            preparedStatement.setInt(1, recordId);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                // It is possible to get the columns via name
                // also possible to get the columns via the column number
                // which starts at 1
                // e.g. resultSet.getSTring(2);

                int id = resultSet.getInt("id");
                String naam = resultSet.getString("naam");

                // Maak een student instantie en print deze instantie
                rol.setId(id);
                rol.setNaam(naam);

                System.out.println(rol);
            }
        } catch (SQLException e) {
            System.out.println("Er is een SQL fout ontstaan tijdens de select statement!");
        }

        return rol;
    }
    public int updateRecord(Rol rol) {

        PreparedStatement preparedStatement = null;
        int result = 0;

        try {
            preparedStatement = connect.prepareStatement("update rollen set " +
                    " naam = ? where id = ?");
            preparedStatement.setString(1, rol.getNaam());
            preparedStatement.setInt(2, rol.getId());

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
            preparedStatement = connect.prepareStatement("delete from rollen where id = ?");
            preparedStatement.setInt(1, recordId);

            // Voer de statement uit en haal het resultaat op
            result = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Er is een SQL fout tijdens deleten van een record!");
            e.printStackTrace();
        }

        return result;
    }

    public int insertRecord(Rol rol) {

        PreparedStatement preparedStatement = null;
        int result = 0;

        try {
            preparedStatement = connect.prepareStatement("insert into rollen values (NULL, ?)");
            preparedStatement.setString(1, rol.getNaam());

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
