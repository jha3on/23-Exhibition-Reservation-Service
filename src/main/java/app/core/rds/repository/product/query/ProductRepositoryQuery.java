package app.core.rds.repository.product.query;

import app.core.rds.entity.place.type.PlaceGroupSubtype;
import app.core.rds.entity.place.type.PlaceGroupType;
import app.core.rds.entity.product.Product;
import app.core.rds.entity.product.ProductLike;
import app.core.rds.entity.product.ProductOption;
import app.core.rds.entity.product.type.ProductGroupType;
import java.util.List;
import java.util.Optional;

public interface ProductRepositoryQuery {

    /** [Fix] Product 객체 조회 */
    Optional<Product> getByProductId(Long productId);

    /** [Fix] Product 컬렉션 조회 */
    List<Product> getAllByProductName(String productName);

    /** [Fix] Product 컬렉션 조회 */
    List<Product> getAllByFilterTypes(PlaceGroupType placeGroupType, PlaceGroupSubtype placeGroupSubtype, ProductGroupType productGroupType);

    /** [Fix] ProductOption 객체 조회 */
    Optional<ProductOption> getByProductOptionId(Long productOptionId);

    /** [Fix] ProductOption 컬렉션 조회 */
    List<ProductOption> getAllByProductId(Long productId);

    /** [Fix] ProductOption 컬렉션 조회 */
    List<ProductOption> getAllByProductIds(List<Long> productIds);

    /** [Fix] ProductLike 객체 중복 조회 */
    Boolean existsBy(Long userId, Long productId);

    /** [Fix] ProductLike 객체 조회 */
    Optional<ProductLike> getBy(Long userId, Long productId);
}