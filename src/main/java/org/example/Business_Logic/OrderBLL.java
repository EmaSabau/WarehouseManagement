package org.example.Business_Logic;
import org.example.Data_Access.OrderDAO;
import org.example.Model.Client;
import org.example.Model.Order;
import org.example.Model.Product;
import org.example.Data_Access.ProductDAO;

import java.math.BigDecimal;
import java.util.NoSuchElementException;

public class OrderBLL {
    public Order findOrderById(int id) {
        Order order = OrderDAO.findById(id);
        if (order == null) {
            throw new NoSuchElementException("The order with id " + id + " was not found!");
        }
        return order;
    }
    public static int insertOrder(Product product, Client client, int quantity) {

        BigDecimal totalPrice = BigDecimal.valueOf(calculateTotalPrice(product, quantity));

        System.out.println("BLL " + product.getId());

        Order order = new Order(client.getId(), product.getId(), quantity, totalPrice);

        ProductDAO.updateStockQuantity(product.getId(), quantity);

        System.out.println("BLL2 " + product.getId());

        return OrderDAO.insert(order);
    }
    private static double calculateTotalPrice(Product product, int quantity) {
        return product.getPrice() * quantity;
    }
}
