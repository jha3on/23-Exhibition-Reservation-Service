package app.core.rds.repository.product;

import app.core.rds.entity.product.ProductOption;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductOptionRepository extends JpaRepository<ProductOption, Long> {
    // ...
}