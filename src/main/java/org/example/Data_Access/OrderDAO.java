package org.example.Data_Access;

import org.example.Model.Order;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OrderDAO {

    protected static final Logger LOGGER = Logger.getLogger(OrderDAO.class.getName());

    private static final String insertStatementString = "INSERT INTO WarehouseOrder (ClientID, ProductID, Quantity, TotalPrice) VALUES (?, ?, ?, ?)";
    private static final String findStatementString = "SELECT * FROM WarehouseOrder WHERE OrderID = ?";
    private static final String getAllOrdersStatement = "SELECT * FROM WarehouseOrder";
    private static final String updateStatementString = "UPDATE WarehouseOrder SET ClientID=?, ProductID=?, Quantity=?, TotalPrice=? WHERE OrderID=?";
    private static final String deleteStatementString = "DELETE FROM WarehouseOrder WHERE OrderID=?";

    public static Order findById(int orderId) {
        Order toReturn = null;
        try (Connection dbConnection = ConnectionFactory.getConnection();
             PreparedStatement findStatement = dbConnection.prepareStatement(findStatementString)) {
            findStatement.setInt(1, orderId);
            try (ResultSet rs = findStatement.executeQuery()) {
                if (rs.next()) {
                    int clientID = rs.getInt("ClientID");
                    int productID = rs.getInt("ProductID");
                    int quantity = rs.getInt("Quantity");
                    java.math.BigDecimal totalPrice = rs.getBigDecimal("TotalPrice");
                    toReturn = new Order(clientID, productID, quantity, totalPrice);
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error while finding order by ID", e);
        }
        return toReturn;
    }

    public static int insert(Order order) {
        int insertedId = -1;
        try (Connection dbConnection = ConnectionFactory.getConnection();
             PreparedStatement insertStatement = dbConnection.prepareStatement(insertStatementString, PreparedStatement.RETURN_GENERATED_KEYS)) {
            insertStatement.setInt(1, order.getClientID());
            insertStatement.setInt(2, order.getProductID());
            insertStatement.setInt(3, order.getQuantity());
            insertStatement.setBigDecimal(4, order.getTotalPrice());
            System.out.println("DAO " + order.getProductID());
            int rowsInserted = insertStatement.executeUpdate();
            if (rowsInserted > 0) {
                try (ResultSet resultSet = insertStatement.getGeneratedKeys()) {
                    if (resultSet.next()) {
                        insertedId = resultSet.getInt(1);
                    }
                }
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.WARNING, "ProductDAO:insert " + ex.getMessage());
        }
        return insertedId;
    }
}
