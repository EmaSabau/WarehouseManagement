package org.example.Business_Logic;

import org.example.Data_Access.ProductDAO;
import org.example.Model.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class ProductBLL {
    private static List<Validator<Product>> validators;

    public ProductBLL() {
        validators = new ArrayList<>();
        validators.add(new PriceValidator());
        validators.add(new QuantityValidator());
    }

    public static Product findProductById(int id) {
        Product product = ProductDAO.findById(id);
        if (product == null) {
            throw new NoSuchElementException("The product with id " + id + " was not found!");
        }
        return product;
    }

    public static void addProduct(Product product) {
        for (Validator<Product> v : validators) {
            v.validate(product);
        }
        ProductDAO.insert(product);
    }
    public static void editProduct(Product product) {
        for (Validator<Product> v : validators) {
            v.validate(product);
        }
        if (product.getId() == 0) {
            throw new IllegalArgumentException("Product ID cannot be zero!");
        }
        ProductDAO.update(product);
    }
    public void deleteProduct(int productId) {
        if (productId <= 0) {
            throw new IllegalArgumentException("Invalid product ID!");
        }
        ProductDAO.delete(productId);
    }
    public static List<Product> getAllProducts() {
        return ProductDAO.getAllProducts();
    }
    public static class PriceValidator implements Validator<Product> {
        public void validate(Product product) {
            if (product.getPrice() <= 0) {
                throw new IllegalArgumentException("Price must be greater than zero!");
            }
        }
    }
    public static class QuantityValidator implements Validator<Product> {
        public void validate(Product product) {
            if (product.getStockQuantity() < 0) {
                throw new IllegalArgumentException("Quantity cannot be negative!");
            }
        }
    }
}
