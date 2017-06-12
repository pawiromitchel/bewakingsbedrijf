package sr.unasat.bewakingsbedrijf.repositories;

import sr.unasat.bewakingsbedrijf.entities.Rol;
import sr.unasat.bewakingsbedrijf.entities.Shift;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mitchel on 6/11/17.
 */
public class ShiftRepository {
    private Connection connect;

    public ShiftRepository() {
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

    public List<Shift> selectAll() {

        List<Shift> outputList = new ArrayList();
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            // Construeer een statement voor het uitvoeren van een SQL Query
            statement = connect.createStatement();
            // Voer de SQL statement uit en verzamel de output in de resultset
            resultSet = statement.executeQuery("select * from shiften");

            while (resultSet.next()) {

                int id = resultSet.getInt("id");
                String type = resultSet.getString("type");
                String begintijd = resultSet.getString("begintijd");
                String eindtijd = resultSet.getString("eindtijd");

                Shift shift = new Shift(id, type, begintijd, eindtijd);

                System.out.println(shift);
                outputList.add(shift);
            }
        } catch (SQLException e) {
            System.out.println("Er is een SQL fout ontstaan tijdens de select * statement!");
        }

        return outputList;
    }
    public Shift selectRecord(int recordId) {

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Shift shift = new Shift();

        try {
            // Statements allow to issue SQL queries to the database
            preparedStatement = connect.prepareStatement("select * from bewakingsbedrijf.shiften where id = ?");
            // Result set get the result of the SQL query
            preparedStatement.setInt(1, recordId);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                // It is possible to get the columns via name
                // also possible to get the columns via the column number
                // which starts at 1
                // e.g. resultSet.getSTring(2);

                int id = resultSet.getInt("id");
                String type = resultSet.getString("type");
                String begintijd = resultSet.getString("begintijd");
                String eindtijd = resultSet.getString("eindtijd");

                // Maak een student instantie en print deze instantie
                shift.setId(id);
                shift.setType(type);
                shift.setBegintijd(begintijd);
                shift.setEindtijd(eindtijd);

                System.out.println(shift);
            }
        } catch (SQLException e) {
            System.out.println("Er is een SQL fout ontstaan tijdens de select statement!");
        }

        return shift;
    }
    public int updateRecord(Shift shift) {

        PreparedStatement preparedStatement = null;
        int result = 0;

        try {
            preparedStatement = connect.prepareStatement("update shiften set " +
                    " type = ?, begintijd = ?, eindtijd = ? where id = ?");
            preparedStatement.setString(1, shift.getType());
            preparedStatement.setString(2, shift.getBegintijd());
            preparedStatement.setString(3, shift.getEindtijd());
            preparedStatement.setInt(4, shift.getId());

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
            preparedStatement = connect.prepareStatement("delete from shiften where id = ?");
            preparedStatement.setInt(1, recordId);

            // Voer de statement uit en haal het resultaat op
            result = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Er is een SQL fout tijdens deleten van een record!");
            e.printStackTrace();
        }

        return result;
    }

    public int insertRecord(Shift shift) {

        PreparedStatement preparedStatement = null;
        int result = 0;

        try {
            preparedStatement = connect.prepareStatement("insert into rollen values (NULL, ?, ?, ?)");
            preparedStatement.setString(1, shift.getType());
            preparedStatement.setString(2, shift.getBegintijd());
            preparedStatement.setString(3, shift.getEindtijd());

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
