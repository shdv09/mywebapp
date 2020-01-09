package dao;

import model.AccessLevel;
import model.Client;
import model.Visit;
import utils.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class ClientDao {
    public Client createClient(Client client) {
        String insertQuery = "INSERT INTO clients (first_name, last_name, is_active, access_level) VALUES (?, ?, ?, ?) RETURNING card_number";
        int cNumber = 0;
        ResultSet resultSet = null;
        Connection conn = ConnectionPool.getConnection();
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(insertQuery);
            preparedStatement.setString(1, client.getFirstName());
            preparedStatement.setString(2, client.getLastName());
            preparedStatement.setBoolean(3, client.isActive());
            preparedStatement.setString(4, client.getAccessLevel().name());
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                cNumber = resultSet.getInt("card_number");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        client = getClient(cNumber);
        return client;
    }

    public int updateClient(Client client) {
        String query = "UPDATE clients SET first_name = ?, last_name = ?, is_active = ?, last_visit = ?, access_level = ? WHERE card_number = ?;";
        int rowCount = 0;
        String firstName = client.getFirstName();
        String lastName = client.getLastName();
        boolean isActive = client.isActive();
        int lastVisitId = client.getLastVisit().getId();
        int cNumber = client.getCardNumber();
        AccessLevel accessLevel = client.getAccessLevel();

        Connection conn = ConnectionPool.getConnection();
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setBoolean(3, isActive);
            preparedStatement.setInt(4, lastVisitId);
            preparedStatement.setString(5, accessLevel.name());
            preparedStatement.setInt(6, cNumber);

            rowCount = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowCount;
    }

    public int deleteClient(int cardNumber) {
        String query = "DELETE FROM clients WHERE card_number = ?";
        int rowCount = 0;
        Connection conn = ConnectionPool.getConnection();
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, cardNumber);
            rowCount = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowCount;
    }

    public Client getClient(int cNumber) {
        String query = "SELECT clients.first_name, clients.last_name, clients.card_number, clients.is_active, clients.access_level, visits.id, visits.start_time, visits.end_time\n" +
                "FROM clients LEFT JOIN visits ON clients.last_visit = visits.id AND visits.card_number_clients = clients.card_number\n" +
                "WHERE clients.card_number = ?;";
        ResultSet resultSet;
        String firstName = null;
        String lastName = null;
        int cardNumber = 0;
        boolean isActive = false;
        int visitId = 0;
        Date visitStartTime = null;
        Date visitEndTime = null;
        AccessLevel accessLevel = null;

        Connection conn = ConnectionPool.getConnection();
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, cNumber);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                firstName = resultSet.getString("first_name");
                lastName = resultSet.getString("last_name");
                cardNumber = resultSet.getInt("card_number");
                isActive = resultSet.getBoolean("is_active");
                visitId = resultSet.getInt("id");
                visitStartTime = resultSet.getTimestamp("start_time");
                visitEndTime = resultSet.getTimestamp("end_time");
                accessLevel = AccessLevel.valueOf(resultSet.getString("access_level"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (cardNumber != 0) {
            Client client = new Client(firstName, lastName, accessLevel);
            client.setActive(isActive);
            client.setCardNumber(cardNumber);
            if (visitId != 0) {
                Visit lastVisit = new Visit(visitStartTime, visitEndTime, cardNumber);
                lastVisit.setId(visitId);
                client.setLastVisit(lastVisit);
            }
            return client;
        } else return null;
    }
}
