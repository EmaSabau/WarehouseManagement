package org.example.Start;

import org.example.Business_Logic.ClientBLL;
import org.example.Business_Logic.OrderBLL;
import org.example.Business_Logic.ProductBLL;
//import org.example.Data_Access.BillDAO;
import org.example.Model.Client;
import org.example.Model.Product;
import org.example.Presentation.Controller;
import org.example.Presentation.View;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Start {
    protected static final Logger LOGGER = Logger.getLogger(Start.class.getName());
    public static void main(String[] args) throws SQLException {
        // Start the client GUI
        javax.swing.SwingUtilities.invokeLater(() -> {
            createAndShowClientGUI();
        });
    }
    private static void createAndShowClientGUI() {
        View view = new View();
        ClientBLL clientBLL = new ClientBLL();
        ProductBLL productBLL = new ProductBLL();
        OrderBLL orderBLL = new OrderBLL();

        List<Client> clients = null;
        List<Product> products = null;
        clients = clientBLL.getAllClients();
        products = productBLL.getAllProducts();
        if (clients != null && products != null) {
            view.setClientComboBoxData(clients);
            view.setProductComboBoxData(products);
        }
        Controller controller = new Controller(view, clientBLL, productBLL, orderBLL);
        view.setVisible(true);
    }
}
