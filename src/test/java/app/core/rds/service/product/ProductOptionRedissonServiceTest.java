package app.core.rds.service.product;

import app.core.rds.entity.place.Place;
import app.core.rds.entity.product.Product;
import app.core.rds.entity.product.ProductOption;
import app.core.rds.repository.product.ProductOptionRepository;
import app.core.rds.repository.product.ProductRepository;
import app.core.rds.repository.product.query.ProductRepositoryQuery;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.time.LocalDateTime;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
@ExtendWith(value = SpringExtension.class)
class ProductOptionRedissonServiceTest {
    @Autowired private RedissonClient redissonClient;
    @Autowired private ProductRepository productRepository;
    @Autowired private ProductOptionRepository productOptionRepository;
    @Autowired private ProductRepositoryQuery productRepositoryQuery;
    @Autowired private ProductOptionRedissonService productOptionRedissonService;

    @BeforeEach
    void beforeEach() {
        log.info("상품 옵션 저장");
        var dummyProduct = productRepository.save(createDummyProduct());
        var dummyProductOption = productOptionRepository.save(createDummyProductOption(dummyProduct));
    }

    @AfterEach
    void afterEach() {
        log.info("상품 옵션 삭제");
        productOptionRepository.deleteById(1L);
    }

//    @Test
//    @DisplayName(value = "재고_수정_테스트")
//    void 재고_수정_테스트() {
//        // [When]
//        log.info("재고 수정");
//        productOptionRedissonService.updateProductOptionBy(1L, -10);
//
//        // [Then]
//        log.info("재고 수정 종료");
//        var productOption = productRepositoryQuery.getByProductOptionId(1L).orElseThrow();
//        System.out.println(productOption.getQuantity());
//        assertThat(productOption.getQuantity()).isEqualTo(90);
//    }

    @Test
    @DisplayName(value = "재고_동시_수정_테스트")
    void 재고_동시_수정_테스트() throws InterruptedException {
        var latch = new CountDownLatch(20);
        var executorService = Executors.newFixedThreadPool(5);

        for (int i = 0; i < 20; i++) {
            executorService.submit(() -> {
                try {
                    log.info("재고 수정");
                    productOptionRedissonService.updateProductOptionBy(1L, -5);
                } catch (RuntimeException e) {
                    e.printStackTrace();
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
        log.info("재고 수정 종료");
        var productOption = productRepositoryQuery.getByProductOptionId(1L).orElseThrow();
        System.out.println(productOption.getQuantity());
        assertThat(productOption.getQuantity()).isEqualTo(0);
    }

    static Product createDummyProduct() {
        return Product.builder()
                .place(Place.builder().id(1L).build())
                .id(1L)
                .name("상품")
                .dateTo(LocalDateTime.now())
                .dateFrom(LocalDateTime.now())
                .build();
    }

    static ProductOption createDummyProductOption(Product dummyProduct) {
        return ProductOption.builder()
                .id(1L)
                .product(dummyProduct)
                .name("상품 옵션1")
                .price(1000)
                .quantity(100)
                .build();
    }
}