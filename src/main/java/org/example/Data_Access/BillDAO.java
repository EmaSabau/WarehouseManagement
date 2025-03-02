package org.example.Data_Access;
//import org.example.Model.Bill;
import java.sql.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
/*public class BillDAO {
    private final Connection connection;

    public BillDAO(Connection connection) {
        this.connection = connection;
    }
    public void insertBill(Bill bill) throws SQLException {
        String query = "INSERT INTO Log (OrderID, TotalPrice) VALUES (?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, bill.orderId());
            preparedStatement.setBigDecimal(2, bill.totalPrice());
            preparedStatement.executeUpdate();
        }
    }
    public List<Bill> getAllBills() throws SQLException {
        List<Bill> bills = new ArrayList<>();
        String query = "SELECT OrderID, TotalPrice FROM Log";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                int orderId = resultSet.getInt("OrderID");
                BigDecimal totalPrice = resultSet.getBigDecimal("TotalPrice");
                Bill bill = new Bill(orderId, totalPrice);
                bills.add(bill);
            }
        }
        return bills;
    }
}*/
