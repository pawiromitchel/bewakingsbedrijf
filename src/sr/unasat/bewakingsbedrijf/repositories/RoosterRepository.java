package sr.unasat.bewakingsbedrijf.repositories;

import sr.unasat.bewakingsbedrijf.entities.Rooster;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mitchel on 5/30/17.
 */
public class RoosterRepository {

    Connection connect;

    public RoosterRepository() {
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

    public List<Rooster> selectAll() {

        List<Rooster> outputList = new ArrayList();
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            // Construeer een statement voor het uitvoeren van een SQL Query
            statement = connect.createStatement();
            // Voer de SQL statement uit en verzamel de output in de resultset
            resultSet = statement.executeQuery("select * from roosters");

            while (resultSet.next()) {
                Rooster rooster = new Rooster();

                int id = resultSet.getInt("id");
                int gebruiker_id = resultSet.getInt("gebruiker_id");
                int post_id = resultSet.getInt("post_id");
                int shift_id = resultSet.getInt("shift_id");
                String datum = resultSet.getString("datum");

                // Maak een rooster instantie en print deze instantie
                rooster.setId(id);
                if (gebruiker_id > 0) {
                    rooster.setGebruiker(new GebruikerRepository().selectRecord(gebruiker_id));
                }
                if (post_id > 0) {
                    rooster.setPost(new PostRepository().selectRecord(post_id));
                }
                if (shift_id > 0){
                    rooster.setShift(new ShiftRepository().selectRecord(shift_id));
                }
                rooster.setDatum(datum);

                System.out.println(rooster);
                outputList.add(rooster);
            }
        } catch (SQLException e) {
            System.out.println("Er is een SQL fout ontstaan tijdens de select * statement!");
        }

        return outputList;
    }

    public Rooster selectRecord(int recordId) {

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Rooster rooster = new Rooster();

        try {
            // Statements allow to issue SQL queries to the database
            preparedStatement = connect.prepareStatement("select * from roosters where id = ?");
            // Result set get the result of the SQL query
            preparedStatement.setInt(1, recordId);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                // It is possible to get the columns via name
                // also possible to get the columns via the column number
                // which starts at 1
                // e.g. resultSet.getSTring(2);

                int id = resultSet.getInt("id");
                int gebruiker_id = resultSet.getInt("gebruiker_id");
                int post_id = resultSet.getInt("post_id");
                int shift_id = resultSet.getInt("shift_id");
                String datum = resultSet.getString("datum");

                // Maak een rooster instantie en print deze instantie
                rooster.setId(id);
                if (gebruiker_id > 0) {
                    rooster.setGebruiker(new GebruikerRepository().selectRecord(gebruiker_id));
                }
                if (post_id > 0) {
                    rooster.setPost(new PostRepository().selectRecord(post_id));
                }
                if (shift_id > 0){
                    rooster.setShift(new ShiftRepository().selectRecord(shift_id));
                }
                rooster.setDatum(datum);

                System.out.println(rooster);
            }
        } catch (SQLException e) {
            System.out.println("Er is een SQL fout ontstaan tijdens de select statement!");
        }

        return rooster;
    }

    public int updateRecord(Rooster rooster) {

        PreparedStatement preparedStatement = null;
        int result = 0;

        try {
            preparedStatement = connect.prepareStatement("update roosters set " +
                    " gebruiker_id = ?," +
                    " post_id = ?, " +
                    " shift_id = ?, " +
                    " datum = ? " +
                    " where id = ?");
            if (rooster.getGebruiker() != null) {
                preparedStatement.setInt(1, rooster.getGebruiker().getId());
            } else {
                preparedStatement.setInt(1, 0);
            }

            if (rooster.getPost() != null) {
                preparedStatement.setInt(2, rooster.getPost().getId());
            } else {
                preparedStatement.setInt(2, 0);
            }

            if (rooster.getShift() != null){
                preparedStatement.setInt(3, rooster.getShift().getId());
            } else {
                preparedStatement.setInt(3, 0);
            }

            preparedStatement.setString(4, rooster.getDatum());
            preparedStatement.setInt(5, rooster.getId());

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
            preparedStatement = connect.prepareStatement("delete from roosters where id = ?");
            preparedStatement.setInt(1, recordId);

            // Voer de statement uit en haal het resultaat op
            result = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Er is een SQL fout tijdens deleten van een record!");
            e.printStackTrace();
        }

        return result;
    }

    public int insertRecord(Rooster rooster) {

        PreparedStatement preparedStatement = null;
        int result = 0;

        try {
            preparedStatement = connect.prepareStatement("insert into roosters values (NULL,?,?,?,?)");
            if (rooster.getGebruiker() != null) {
                preparedStatement.setInt(1, rooster.getGebruiker().getId());
            } else {
                preparedStatement.setInt(1, 0);
            }

            if (rooster.getPost() != null) {
                preparedStatement.setInt(2, rooster.getPost().getId());
            } else {
                preparedStatement.setInt(2, 0);
            }

            if (rooster.getShift() != null){
                preparedStatement.setInt(3, rooster.getShift().getId());
            } else {
                preparedStatement.setInt(3, 0);
            }

            preparedStatement.setString(4, rooster.getDatum());

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
