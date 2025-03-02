package org.example.Model;
import java.math.BigDecimal;
/*public record Bill(int orderId, BigDecimal totalPrice) {
    public Bill {
        if (orderId <= 0) {
            throw new IllegalArgumentException("Order ID must be positive");
        }
        if (totalPrice.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Total price must be non-negative");
        }
    }
    @Override
    public String toString() {
        return "Bill{" +
                "orderId=" + orderId +
                ", totalPrice=" + totalPrice +
                '}';
    }
}*/
