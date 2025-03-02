package org.example.Presentation;
import org.example.Business_Logic.ClientBLL;
import org.example.Business_Logic.OrderBLL;
import org.example.Business_Logic.ProductBLL;
import org.example.Model.Client;
import org.example.Model.Product;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class Controller {
    private View view;
    private ClientBLL clientBLL;
    private ProductBLL productBLL;
    private OrderBLL orderBLL;
    private DefaultTableModel clientTableModel;
    private DefaultTableModel productTableModel;

    public Controller(View view, ClientBLL clientBLL, ProductBLL productBLL, OrderBLL orderBLL) {
        this.view = view;
        this.clientBLL = clientBLL;
        this.productBLL = productBLL;
        this.orderBLL = orderBLL;
        this.clientTableModel = new DefaultTableModel();
        this.productTableModel = new DefaultTableModel();
        initView();
        initListeners();
    }
    private void initView() {
        view.setVisible(true);
        refreshClientTable();
        refreshProductTable();
    }
    private void initListeners() {
        // Client operations
        view.setAddClientButtonListener(new AddClientListener());
        view.setEditClientButtonListener(new EditClientListener());
        view.setDeleteClientButtonListener(new DeleteClientListener());
        // Product operations
        view.setAddProductButtonListener(new AddProductListener());
        view.setEditProductButtonListener(new EditProductListener());
        view.setDeleteProductButtonListener(new DeleteProductListener());
    }
    private void refreshClientTable() {
        List<Client> clients = clientBLL.getAllClients();
        clientTableModel.setRowCount(0); // Clear existing data
        clientTableModel.setColumnIdentifiers(new Object[] {"ID", "Name", "Email", "Address", "Phone"});
        for (Client client : clients) {
            clientTableModel.addRow(new Object[] {client.getId(), client.getName(), client.getEmail(), client.getAddress(), client.getPhone()});
        }
        view.setClientTableModel(clientTableModel);
    }
    private void refreshProductTable() {
        List<Product> products = productBLL.getAllProducts();
        productTableModel.setRowCount(0); // Clear existing data
        productTableModel.setColumnIdentifiers(new Object[] {"ID", "Name", "Description", "Price", "Quantity"});
        for (Product product : products) {
            productTableModel.addRow(new Object[] {product.getId(), product.getName(), product.getDescription(), product.getPrice(), product.getStockQuantity()});
        }
        view.setProductTableModel(productTableModel);
    }

    private class AddClientListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String[] clientData = view.showAddClientDialog();
            if (clientData == null) {
                return;
            }
            String name = clientData[0];
            String email = clientData[1];
            String address = clientData[2];
            String phone = clientData[3];
            if (name.isEmpty() || email.isEmpty() || address.isEmpty() || phone.isEmpty()) {
                JOptionPane.showMessageDialog(view, "All fields must be filled!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                Client client = new Client(name, email, address, phone);
                ClientBLL.addClient(client);
                refreshClientTable();
                JOptionPane.showMessageDialog(view, "Client added successfully!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view, "Error adding client: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    private class EditClientListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int clientId = view.getSelectedClientId();
            if (clientId == -1) {
                JOptionPane.showMessageDialog(view, "Please select a client to edit.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            Client selectedClient = getClientById(clientId);
            String[] clientData = view.showEditClientDialog(selectedClient);
            if (clientData == null) {
                return;
            }
            String name = clientData[0];
            String email = clientData[1];
            String address = clientData[2];
            String phone = clientData[3];
            if (name.isEmpty() || email.isEmpty() || address.isEmpty() || phone.isEmpty()) {
                JOptionPane.showMessageDialog(view, "All fields must be filled!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                Client client = new Client(clientId, name, email, address, phone);
                ClientBLL.editClient(client);
                refreshClientTable();
                JOptionPane.showMessageDialog(view, "Client updated successfully!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view, "Error updating client: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    private class DeleteClientListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int clientId = view.getSelectedClientId();
            if (clientId == -1) {
                JOptionPane.showMessageDialog(view, "Please select a client to delete.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int confirm = JOptionPane.showConfirmDialog(view, "Are you sure you want to delete this client?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    clientBLL.deleteClient(clientId);
                    refreshClientTable();
                    JOptionPane.showMessageDialog(view, "Client deleted successfully!");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(view, "Error deleting client: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
    private class AddProductListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String[] productData = view.showAddProductDialog();
            if (productData == null) {
                return;
            }
            String name = productData[0];
            String description = productData[1];
            double price;
            int quantity;
            try {
                price = Double.parseDouble(productData[2]);
                quantity = Integer.parseInt(productData[3]);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(view, "Price and Quantity must be valid numbers!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (name.isEmpty() || description.isEmpty()) {
                JOptionPane.showMessageDialog(view, "Name and Description must be filled!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (price <= 0 || quantity <= 0) {
                JOptionPane.showMessageDialog(view, "Price and Quantity must be greater than zero!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                Product product = new Product(name, description, price, quantity);
                ProductBLL.addProduct(product);
                refreshProductTable();
                JOptionPane.showMessageDialog(view, "Product added successfully!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view, "Error adding product: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    private class EditProductListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int productId = view.getSelectedProductId();
            if (productId == -1) {
                JOptionPane.showMessageDialog(view, "Please select a product to edit.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            Product selectedProduct = getProductById(productId);
            String[] productData = view.showEditProductDialog(selectedProduct);
            if (productData == null) {
                return;
            }
            String name = view.getProductNameField();
            String description = view.getProductDescriptionField();
            double price;
            int quantity;
            try {
                name = productData[0];
                description = productData[1];
                price = Double.parseDouble(productData[2]);
                quantity = Integer.parseInt(productData[3]);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(view, "Price and Quantity must be valid numbers!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (price <= 0 || quantity <= 0) {
                JOptionPane.showMessageDialog(view, "Price and Quantity must be greater than zero!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                Product product = new Product(productId, name, description, price, quantity);
                ProductBLL.editProduct(product);
                refreshProductTable();
                JOptionPane.showMessageDialog(view, "Product updated successfully!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view, "Error updating product: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    private class DeleteProductListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int productId = view.getSelectedProductId();
            if (productId == -1) {
                JOptionPane.showMessageDialog(view, "Please select a product to delete.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int confirm = JOptionPane.showConfirmDialog(view, "Are you sure you want to delete this product?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    productBLL.deleteProduct(productId);
                    refreshProductTable();
                    JOptionPane.showMessageDialog(view, "Product deleted successfully!");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(view, "Error deleting product: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
    public Client getClientById(int clientId) {
        DefaultTableModel model = (DefaultTableModel) view.getClientTable().getModel();
        for (int i = 0; i < model.getRowCount(); i++) {
            int id = (int) model.getValueAt(i, 0);
            if (id == clientId) {
                String name = (String) model.getValueAt(i, 1);
                String email = (String) model.getValueAt(i, 2);
                String address = (String) model.getValueAt(i, 3);
                String phone = (String) model.getValueAt(i, 4);
                return new Client(id, name, email, address, phone);
            }
        }
        return null;
    }
    public Product getProductById(int productId) {
        DefaultTableModel model = (DefaultTableModel) view.getProductTable().getModel();
        for (int i = 0; i < model.getRowCount(); i++) {
            int id = (int) model.getValueAt(i, 0);
            if (id == productId) {
                String name = (String) model.getValueAt(i, 1);
                String description = (String) model.getValueAt(i, 2);
                double price = (double) model.getValueAt(i, 3);
                int quantity = (int) model.getValueAt(i, 4);
                return new Product(id, name, description, price, quantity);
            }
        }
        return null;
    }

}
