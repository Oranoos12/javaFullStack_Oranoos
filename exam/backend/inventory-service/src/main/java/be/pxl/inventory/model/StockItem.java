package be.pxl.inventory.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "stock_items")
@Getter
@Setter
@NoArgsConstructor
public class StockItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private Long ingredientId;

    private String ingredientName;
    private String unit;
    private double quantityAvailable;
    private double minimumLevel;

    public boolean canReserve(double quantity) {
        return quantity <= quantityAvailable;
    }

    public void reserve(double quantity) {
        quantityAvailable = quantityAvailable - quantity;
    }

    public void release(double quantity) {
        quantityAvailable = quantityAvailable + quantity;
    }

    public void replenish(double quantity) {
        quantityAvailable = quantityAvailable + quantity;
    }

    public boolean isLow() {
        return quantityAvailable < minimumLevel;
    }
}
