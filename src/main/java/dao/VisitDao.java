package dao;

import model.Visit;
import utils.ConnectionPool;

import java.sql.*;
import java.util.Date;

public class VisitDao {
    public Visit getVisit(int id) {
        String query = "SELECT id, start_time, end_time, card_number_clients FROM visits WHERE id = ?";
        int visitId = 0;
        Date startTime = null;
        Date endTime = null;
        int cardNumber = 0;
        ResultSet resultSet;
        Connection conn = ConnectionPool.getConnection();
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                visitId = resultSet.getInt("id");
                startTime = resultSet.getTimestamp("start_time");
                endTime = resultSet.getTimestamp("end_time");
                cardNumber = resultSet.getInt("card_number_clients");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (visitId != 0) {
            Visit visit = new Visit(startTime, endTime, visitId);
            return visit;
        } else return null;

    }

    public int createVisit(Visit visit) {
        String query = "INSERT INTO visits (start_time, end_time, card_number_clients) VALUES (?, ?, ?) RETURNING id;";
        Date startTime = visit.getStart();
        Date endTime = visit.getEnd();
        int cNumber = visit.getCardNumber();
        
        int visitId = 0;
        Timestamp startTimestamp = startTime == null ? null : new Timestamp(startTime.getTime());
        Timestamp endTimestamp = endTime == null ? null : new Timestamp(endTime.getTime());
        ResultSet resultSet;
        Connection conn = ConnectionPool.getConnection();
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setTimestamp(1, startTimestamp);
            preparedStatement.setTimestamp(2, endTimestamp);
            preparedStatement.setInt(3, cNumber);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                visitId = resultSet.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return visitId;
    }

    public int updateVisit(Visit visit) {
        String query = "UPDATE visits SET start_time = ?, end_time = ?, card_number_clients = ? WHERE id = ?";
        Date startTime = visit.getStart();
        Date endTime = visit.getEnd();
        int cNumber = visit.getCardNumber();
        int visitId = visit.getId();
        int rowCount = 0;

        Timestamp startTimestamp = startTime == null ? null : new Timestamp(startTime.getTime());
        Timestamp endTimestamp = endTime == null ? null : new Timestamp(endTime.getTime());
        Connection conn = ConnectionPool.getConnection();
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setTimestamp(1, startTimestamp);
            preparedStatement.setTimestamp(2, endTimestamp);
            preparedStatement.setInt(3, cNumber);
            preparedStatement.setInt(4, visitId);
            rowCount = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowCount;
    }
}
