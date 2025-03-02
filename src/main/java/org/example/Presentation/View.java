package org.example.Presentation;

import org.example.Model.Client;
import org.example.Model.Order;
import org.example.Model.Product;
import org.example.Business_Logic.ClientBLL;
import org.example.Business_Logic.ProductBLL;
import org.example.Business_Logic.OrderBLL;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class View extends JFrame {
    private JButton addClientButton;
    private JButton editClientButton;
    private JButton deleteClientButton;
    private JTable clientTable;
    private JScrollPane clientScrollPane;
    private JButton addProductButton;
    private JButton editProductButton;
    private JButton deleteProductButton;
    private JTable productTable;
    private JTable orderTable;
    private JScrollPane productScrollPane;
    private JComboBox<Client> clientComboBox;
    private JComboBox<Product> productComboBox;
    private String[] productDialogData;
    private JTextField quantityField = new JTextField();
    private JTextField productNameField=new JTextField();
    private JTextField productDescriptionField=new JTextField();
    private DefaultTableModel orderTableModel;

    public View() {
        setTitle("Warehouse Management System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Client operations panel
        JPanel clientPanel = createPanel();
        createClientPanel(clientPanel);
        // Product operations panel
        JPanel productPanel = createPanel();
        createProductPanel(productPanel);
        // Order creation panel
        JPanel orderPanel = createPanel();
        createOrderPanel(orderPanel);
        // Add panels to tabbed panel
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Clients", clientPanel);
        tabbedPane.addTab("Products", productPanel);
        tabbedPane.addTab("Orders", orderPanel);
        add(tabbedPane);
    }
    private JPanel createPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        return panel;
    }
    private void createClientPanel(JPanel clientPanel) {
        // Create buttons
        addClientButton = new JButton("Add Client");
        editClientButton = new JButton("Edit Client");
        deleteClientButton = new JButton("Delete Client");
        // Add buttons to panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addClientButton);
        buttonPanel.add(editClientButton);
        buttonPanel.add(deleteClientButton);
        clientPanel.add(buttonPanel, BorderLayout.NORTH);
        // Create table
        clientTable = new JTable();
        clientScrollPane = new JScrollPane(clientTable);
        clientPanel.add(clientScrollPane, BorderLayout.CENTER);
        // Listener pentru butonul "Add Client"
        addClientButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAddClientDialog();
            }
        });
    }
    private void createProductPanel(JPanel productPanel) {
        // Create buttons
        addProductButton = new JButton("Add Product");
        editProductButton = new JButton("Edit Product");
        deleteProductButton = new JButton("Delete Product");
        // Add buttons to panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addProductButton);
        buttonPanel.add(editProductButton);
        buttonPanel.add(deleteProductButton);
        productPanel.add(buttonPanel, BorderLayout.NORTH);
        productTable = new JTable();
        productScrollPane = new JScrollPane(productTable);
        productPanel.add(productScrollPane, BorderLayout.CENTER);
        addProductButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAddProductDialog();
            }
        });
    }
    private void createOrderPanel(JPanel orderPanel) {
        JPanel mainPanel = new JPanel(new GridLayout(4, 2));
        mainPanel.add(new JLabel("Product:"));

        productComboBox = new JComboBox<>();
        mainPanel.add(productComboBox);

        mainPanel.add(new JLabel("Client:"));

        clientComboBox = new JComboBox<>();
        mainPanel.add(clientComboBox);

        mainPanel.add(new JLabel("Quantity:"));
        mainPanel.add(quantityField);

        JButton createOrderButton = new JButton("Create Order");
        createOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createOrder();
            }
        });
        mainPanel.add(createOrderButton);

        orderPanel.add(mainPanel);
    }
    private void createOrder() {
        Product selectedProduct = (Product) productComboBox.getSelectedItem();
        Client selectedClient = (Client) clientComboBox.getSelectedItem();
        int quantity;
        try {
            quantity = Integer.parseInt(quantityField.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid quantity format!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (selectedProduct.getStockQuantity() < quantity) {
            JOptionPane.showMessageDialog(this, "Not enough products in stock!", "Under-stock", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (quantity<0) {
            JOptionPane.showMessageDialog(this, "Invalid quantity!", "Invalid quantity", JOptionPane.WARNING_MESSAGE);
            return;
        }
        System.out.println("View " + selectedProduct.getId());
        OrderBLL.insertOrder(selectedProduct, selectedClient, quantity);

        selectedProduct.setStockQuantity(selectedProduct.getStockQuantity() - quantity);

        List<Product> updatedProducts = ProductBLL.getAllProducts();
        updateProductTable(updatedProducts);

    }
    public void updateProductTable(List<Product> products) {
        DefaultTableModel productTableModel = new DefaultTableModel();
        productTableModel.addColumn("ID");
        productTableModel.addColumn("Name");
        productTableModel.addColumn("Description");
        productTableModel.addColumn("Price");
        productTableModel.addColumn("Stock Quantity");

        for (Product product : products) {
            Object[] rowData = {
                    product.getId(),
                    product.getName(),
                    product.getDescription(),
                    product.getPrice(),
                    product.getStockQuantity()
            };
            productTableModel.addRow(rowData);
        }

        setProductTableModel(productTableModel);
    }
    public String[] showAddClientDialog() {

        JDialog addClientDialog = new JDialog(this, "Add New Client", true);
        addClientDialog.setLayout(new BorderLayout());
        JPanel inputPanel = new JPanel(new GridLayout(4, 2));

        inputPanel.add(new JLabel("Name:"));
        JTextField nameField = new JTextField();
        inputPanel.add(nameField);

        inputPanel.add(new JLabel("Email:"));
        JTextField emailField = new JTextField();
        inputPanel.add(emailField);

        inputPanel.add(new JLabel("Address:"));
        JTextField addressField = new JTextField();
        inputPanel.add(addressField);

        inputPanel.add(new JLabel("Phone:"));
        JTextField phoneField = new JTextField();
        inputPanel.add(phoneField);

        JButton addButton = new JButton("Add");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Extract entered data
                String name = nameField.getText();
                String email = emailField.getText();
                String address = addressField.getText();
                String phone = phoneField.getText();
                Client newClient = new Client(name, email, address, phone);
                ClientBLL.addClient(newClient);
                addClientDialog.dispose();
            }
        });

        addClientDialog.add(inputPanel, BorderLayout.CENTER);
        addClientDialog.add(addButton, BorderLayout.SOUTH);

        addClientDialog.setSize(300, 200);
        addClientDialog.setVisible(true);

        return new String[]{nameField.getText(), emailField.getText(), addressField.getText(), phoneField.getText()};
    }
    public String[] showAddProductDialog() {
        JDialog addProductDialog = new JDialog(this, "Add New Product", true);
        addProductDialog.setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(4, 2));

        inputPanel.add(new JLabel("Name:"));
        JTextField nameField = new JTextField();
        inputPanel.add(nameField);

        inputPanel.add(new JLabel("Description:"));
        JTextField descriptionField = new JTextField();
        inputPanel.add(descriptionField);

        inputPanel.add(new JLabel("Price:"));
        JTextField priceField = new JTextField();
        inputPanel.add(priceField);

        inputPanel.add(new JLabel("Quantity:"));
        JTextField quantityField = new JTextField();
        inputPanel.add(quantityField);

        JButton addButton = new JButton("Add");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String description = descriptionField.getText();
                String price = priceField.getText();
                String quantity = quantityField.getText();
                productDialogData = new String[] { name, description, price, quantity };
                addProductDialog.dispose();
            }
        });
        addProductDialog.add(inputPanel, BorderLayout.CENTER);
        addProductDialog.add(addButton, BorderLayout.SOUTH);
        addProductDialog.setSize(300, 200);
        addProductDialog.setVisible(true);
        return productDialogData;
    }
    public void setClientComboBoxData(List<Client> clients) {
        DefaultComboBoxModel<Client> model = new DefaultComboBoxModel<>(clients.toArray(new Client[0]));
        clientComboBox.setModel(model);
    }
    public void setProductComboBoxData(List<Product> products) {
        DefaultComboBoxModel<Product> model = new DefaultComboBoxModel<>(products.toArray(new Product[0]));
        productComboBox.setModel(model);
    }
    public String[] showEditClientDialog(Client client) {
        JDialog editClientDialog = new JDialog(this, "Edit Client", true);
        editClientDialog.setLayout(new BorderLayout());
        JPanel inputPanel = new JPanel(new GridLayout(4, 2));
        inputPanel.add(new JLabel("Name:"));
        JTextField nameField = new JTextField(client.getName());
        inputPanel.add(nameField);

        inputPanel.add(new JLabel("Email:"));
        JTextField emailField = new JTextField(client.getEmail());
        inputPanel.add(emailField);

        inputPanel.add(new JLabel("Address:"));
        JTextField addressField = new JTextField(client.getAddress());
        inputPanel.add(addressField);

        inputPanel.add(new JLabel("Phone:"));
        JTextField phoneField = new JTextField(client.getPhone());
        inputPanel.add(phoneField);

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String email = emailField.getText();
                String address = addressField.getText();
                String phone = phoneField.getText();

                client.setName(name);
                client.setEmail(email);
                client.setAddress(address);
                client.setPhone(phone);

                ClientBLL.editClient(client);
                editClientDialog.dispose();
            }
        });
        editClientDialog.add(inputPanel, BorderLayout.CENTER);
        editClientDialog.add(saveButton, BorderLayout.SOUTH);

        editClientDialog.setSize(300, 200);
        editClientDialog.setVisible(true);

        return new String[]{nameField.getText(), emailField.getText(), addressField.getText(), phoneField.getText()};
    }
    public String[] showEditProductDialog(Product product) {
        JDialog editProductDialog = new JDialog(this, "Edit Product", true);
        editProductDialog.setLayout(new BorderLayout());
        JPanel inputPanel = new JPanel(new GridLayout(4, 2));
        inputPanel.add(new JLabel("Name:"));
        JTextField nameField = new JTextField(product.getName());
        inputPanel.add(nameField);

        inputPanel.add(new JLabel("Description:"));
        JTextField descriptionField = new JTextField(product.getDescription());
        inputPanel.add(descriptionField);

        inputPanel.add(new JLabel("Price:"));
        JTextField priceField = new JTextField(String.valueOf(product.getPrice()));
        inputPanel.add(priceField);

        inputPanel.add(new JLabel("Quantity:"));
        JTextField quantityField = new JTextField(String.valueOf(product.getStockQuantity()));
        inputPanel.add(quantityField);

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String description = descriptionField.getText();
                double price = Double.parseDouble(priceField.getText());
                int quantity = Integer.parseInt(quantityField.getText());

                product.setName(name);
                product.setDescription(description);
                product.setPrice(price);
                product.setStockQuantity(quantity);

                ProductBLL.editProduct(product);
                editProductDialog.dispose();
            }
        });
        editProductDialog.add(inputPanel, BorderLayout.CENTER);
        editProductDialog.add(saveButton, BorderLayout.SOUTH);

        editProductDialog.setSize(300, 200);
        editProductDialog.setVisible(true);

        return new String[]{nameField.getText(), descriptionField.getText(), priceField.getText(), quantityField.getText()};
    }
    public void setAddClientButtonListener(ActionListener listener) {
        addClientButton.addActionListener(listener);
    }
    public void setEditClientButtonListener(ActionListener listener) {
        editClientButton.addActionListener(listener);
    }
    public void setDeleteClientButtonListener(ActionListener listener) {
        deleteClientButton.addActionListener(listener);
    }
    public void setAddProductButtonListener(ActionListener listener) {
        addProductButton.addActionListener(listener);
    }
    public void setEditProductButtonListener(ActionListener listener) {
        editProductButton.addActionListener(listener);
    }
    public void setDeleteProductButtonListener(ActionListener listener) {
        deleteProductButton.addActionListener(listener);
    }
    public void setProductTableData(List<Product> products, DefaultTableModel tableModel) {
        org.example.Start.Reflection.generateTableFromList(products, tableModel);
        productTable.setModel(tableModel);
    }
    public String getProductDescriptionField() {
        return productDescriptionField.getText();
    }
    public int getSelectedClientId() {
        int selectedRow = clientTable.getSelectedRow();
        if (selectedRow != -1) {
            return (int) clientTable.getValueAt(selectedRow, 0);
        } else {
            return -1;
        }
    }
    public int getSelectedProductId() {
        int selectedRow = productTable.getSelectedRow();
        if (selectedRow != -1) {
            return (int) productTable.getValueAt(selectedRow, 0);
        } else {
            return -1;
        }
    }
    public void setClientTableModel(DefaultTableModel clientTableModel) {
        clientTable.setModel(clientTableModel);
    }
    public JTable getClientTable() {
        return clientTable;
    }
    public String getProductNameField() {
        return productNameField.getText();
    }
    public JTable getProductTable() {
        return productTable;
    }
    public void setProductTableModel(DefaultTableModel productTableModel) {
        productTable.setModel(productTableModel);
    }
    public void setOrderTableModel(DefaultTableModel orderTableModel) {
        productTable.setModel(orderTableModel);
    }
}

