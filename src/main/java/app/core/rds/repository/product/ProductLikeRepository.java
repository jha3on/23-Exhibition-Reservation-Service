package app.core.rds.repository.product;

import app.core.rds.entity.product.ProductLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductLikeRepository extends JpaRepository<ProductLike, Long> {
    // ...
}