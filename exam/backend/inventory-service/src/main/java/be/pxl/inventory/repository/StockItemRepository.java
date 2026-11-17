package be.pxl.inventory.repository;

import be.pxl.inventory.model.StockItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StockItemRepository extends JpaRepository<StockItem, Long> {

    Optional<StockItem> findByIngredientId(Long ingredientId);
}
