package app.core.rds.service.product;

import app.core.rds.entity.product.Product;
import app.core.rds.entity.product.ProductLike;
import app.core.rds.entity.user.User;
import app.core.rds.repository.product.ProductLikeRepository;
import app.core.rds.repository.product.query.ProductRepositoryQuery;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductLikeService {
    private final ProductLikeRepository productLikeRepository;
    private final ProductRepositoryQuery productRepositoryQuery;

    /**
     * [Fix] ProductLike 객체를 조회한다.
     */
    @Transactional
    public Optional<ProductLike> getOptBy(Long userId, Long productId) {
        return productRepositoryQuery.getBy(userId, productId);
    }

    /**
     * [Fix] ProductLike 객체를 조회한다.
     */
    @Transactional
    public Boolean existsBy(Long userId, Long productId) {
        return productRepositoryQuery.existsBy(userId, productId);
    }

    /**
     * [Fix] ProductLike 객체를 저장/삭제한다.
     */
    @Transactional
    public Boolean toggleBy(User user, Product product) {
        var userProductOpt = productRepositoryQuery.getBy(user.getId(), product.getId());

        if (userProductOpt.isEmpty()) {
            productLikeRepository.save(createBy(user, product));
            return true;
        } else {
            productLikeRepository.delete(userProductOpt.get());
            return false;
        }
    }

    private ProductLike createBy(User user, Product product) {
        return ProductLike.of(user, product);
    }
}