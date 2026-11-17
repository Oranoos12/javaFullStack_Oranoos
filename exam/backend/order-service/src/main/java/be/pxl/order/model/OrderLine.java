package be.pxl.order.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "order_lines")
@Getter
@Setter
@NoArgsConstructor
public class OrderLine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long recipeId;
    private String recipeName;

    public OrderLine(Long recipeId, String recipeName) {
        this.recipeId = recipeId;
        this.recipeName = recipeName;
    }
}