package org.example.Data_Access;

import org.example.Model.Client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientDAO {

    protected static final Logger LOGGER = Logger.getLogger(ClientDAO.class.getName());
    public static final String insertStatementString = "INSERT INTO WarehouseClient (name,email,address,phone)"
            + " VALUES (?,?,?,?)";
    private final static String updateStatementString = "UPDATE WarehouseClient SET name=?, email=?, address=?, phone=? WHERE ClientID=?";
    private final static String deleteStatementString = "DELETE FROM WarehouseClient WHERE ClientID=?";

    private final static String findStatementString = "SELECT * FROM WarehouseClient WHERE ClientID = ?";
    private final static String getAllClientsStatement = "SELECT * FROM WarehouseClient";

    public static Client findById(int clientId) {
        Client toReturn = null;

        try (Connection dbConnection = ConnectionFactory.getConnection();
             PreparedStatement findStatement = dbConnection.prepareStatement(findStatementString)) {

            findStatement.setInt(1, clientId);
            try (ResultSet rs = findStatement.executeQuery()) {
                if (rs.next()) {
                    String name = rs.getString("name");
                    String email = rs.getString("email");
                    String address = rs.getString("address");
                    String phone = rs.getString("phone");
                    toReturn = new Client(clientId, name, email, address, phone);
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "ClientDAO:findById " + e.getMessage());
        }

        return toReturn;
    }

    public static int insert(Client client) {
        int insertedId = -1;

        try (Connection dbConnection = ConnectionFactory.getConnection();
             PreparedStatement insertStatement = dbConnection.prepareStatement(insertStatementString, PreparedStatement.RETURN_GENERATED_KEYS)) {

            insertStatement.setString(1, client.getName());
            insertStatement.setString(2, client.getEmail());
            insertStatement.setString(3, client.getAddress());
            insertStatement.setString(4, client.getPhone());
            insertStatement.executeUpdate();

            ResultSet rs = insertStatement.getGeneratedKeys();
            if (rs.next()) {
                insertedId = rs.getInt(1);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "ClientDAO:insert " + e.getMessage());
        }

        return insertedId;
    }

    public static List<Client> getAllClients() {
        List<Client> clients = new ArrayList<>();

        try (Connection dbConnection = ConnectionFactory.getConnection();
             PreparedStatement statement = dbConnection.prepareStatement(getAllClientsStatement);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("ClientID");
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                String address = resultSet.getString("address");
                String phone = resultSet.getString("phone");

                clients.add(new Client(id, name, email, address, phone));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "ClientDAO:getAllClients " + e.getMessage());
        }

        return clients;
    }
    public static void update(Client client) {
        try (Connection dbConnection = ConnectionFactory.getConnection();
             PreparedStatement updateStatement = dbConnection.prepareStatement(updateStatementString)) {

            updateStatement.setString(1, client.getName());
            updateStatement.setString(2, client.getEmail());
            updateStatement.setString(3, client.getAddress());
            updateStatement.setString(4, client.getPhone());
            updateStatement.setInt(5, client.getId());

            updateStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "ClientDAO:update " + e.getMessage());
        }
    }

    public static void delete(int clientId) {
        try (Connection dbConnection = ConnectionFactory.getConnection();
             PreparedStatement deleteStatement = dbConnection.prepareStatement(deleteStatementString)) {

            deleteStatement.setInt(1, clientId);
            deleteStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "ClientDAO:delete " + e.getMessage());
        }
    }
}
