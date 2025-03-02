package org.example.Data_Access;

import org.example.Model.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProductDAO {

    protected static final Logger LOGGER = Logger.getLogger(ProductDAO.class.getName());
    private static final String SELECT_ALL_PRODUCTS = "SELECT * FROM Product";
    private static final String SELECT_PRODUCT_BY_ID = "SELECT * FROM Product WHERE ProductID = ?";
    private static final String INSERT_PRODUCT = "INSERT INTO Product (Name, Description, Price, StockQuantity) VALUES (?, ?, ?, ?)";

    public static Product findById(int productId) {
        Product product = null;
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_PRODUCT_BY_ID)) {
            statement.setInt(1, productId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    product = new Product(
                            resultSet.getInt("ProductID"),
                            resultSet.getString("Name"),
                            resultSet.getString("Description"),
                            resultSet.getDouble("Price"),
                            resultSet.getInt("StockQuantity")
                    );
                }
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.WARNING, "ProductDAO:findById " + ex.getMessage());
        }
        return product;
    }

    public static List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL_PRODUCTS);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Product product = new Product(
                        resultSet.getInt("ProductID"),
                        resultSet.getString("Name"),
                        resultSet.getString("Description"),
                        resultSet.getDouble("Price"),
                        resultSet.getInt("StockQuantity")
                );
                products.add(product);
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.WARNING, "ProductDAO:getAllProducts " + ex.getMessage());
        }
        return products;
    }

    public static int insert(Product product) {
        int productId = -1;
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_PRODUCT, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, product.getName());
            statement.setString(2, product.getDescription());
            statement.setDouble(3, product.getPrice());
            statement.setInt(4, product.getStockQuantity());
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                try (ResultSet resultSet = statement.getGeneratedKeys()) {
                    if (resultSet.next()) {
                        productId = resultSet.getInt(1);
                    }
                }
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.WARNING, "ProductDAO:insert " + ex.getMessage());
        }
        return productId;
    }
    public static void update(Product product) {
        String UPDATE_PRODUCT = "UPDATE Product SET Name = ?, Description = ?, Price = ?, StockQuantity = ? WHERE ProductID = ?";
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_PRODUCT)) {
            statement.setString(1, product.getName());
            statement.setString(2, product.getDescription());
            statement.setDouble(3, product.getPrice());
            statement.setInt(4, product.getStockQuantity());
            statement.setInt(5, product.getId());
            statement.executeUpdate();
        } catch (SQLException ex) {
            LOGGER.log(Level.WARNING, "ProductDAO:update " + ex.getMessage());
        }
    }

    public static void delete(int productId) {
        String DELETE_PRODUCT = "DELETE FROM Product WHERE ProductID = ?";
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_PRODUCT)) {
            statement.setInt(1, productId);
            statement.executeUpdate();
        } catch (SQLException ex) {
            LOGGER.log(Level.WARNING, "ProductDAO:delete " + ex.getMessage());
        }
    }
    public static void updateStockQuantity(int productId, int quantity) {
        String UPDATE_STOCK_QUANTITY = "UPDATE Product SET StockQuantity = StockQuantity - ? WHERE ProductID = ?";
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_STOCK_QUANTITY)) {
            statement.setInt(1, quantity);
            statement.setInt(2, productId);
            statement.executeUpdate();
        } catch (SQLException ex) {
            LOGGER.log(Level.WARNING, "ProductDAO:updateStockQuantity " + ex.getMessage());
        }
    }
}
