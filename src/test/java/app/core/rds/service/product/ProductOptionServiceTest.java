package app.core.rds.service.product;

import app.core.rds.entity.product.ProductOption;
import app.core.rds.repository.product.ProductOptionRepository;
import app.core.rds.repository.product.query.ProductRepositoryQuery;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@Slf4j
@ExtendWith(value = MockitoExtension.class)
class ProductOptionServiceTest {
    @Mock private ProductRepositoryQuery productRepositoryQuery;
    @Mock private ProductOptionRepository productOptionRepository;
    @InjectMocks private ProductOptionService productOptionService;

    private ProductOption dummyProductOption;

    @BeforeEach
    void beforeEach() {
        dummyProductOption = createDummyProductOption();
    }

    @Test
    @DisplayName(value = "재고_수정_테스트")
    void 재고_수정_테스트() {
        // [Given]
        given(productRepositoryQuery.getByProductOptionId(1L)).willReturn(Optional.of(dummyProductOption));

        // [When]
        productOptionService.updateBy(1L, -10);

        // [Then]
        System.out.println(dummyProductOption.getQuantity()); // 90
        assertThat(dummyProductOption.getQuantity()).isEqualTo(90);
    }

    @Test
    @DisplayName(value = "재고_동시_수정_테스트")
    void 재고_동시_수정_테스트() throws InterruptedException {
        // [Given]
        given(productRepositoryQuery.getByProductOptionId(1L)).willReturn(Optional.of(dummyProductOption));

        var latch = new CountDownLatch(20);
        var executorService = Executors.newFixedThreadPool(5);

        for (int i = 0; i < 20; i++) {
            executorService.submit(() -> {
                try {
                    productOptionService.updateBy(1L, -5);
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
        System.out.println(dummyProductOption.getQuantity()); // 0
        assertThat(dummyProductOption.getQuantity()).isEqualTo(0);
    }

    static ProductOption createDummyProductOption() {
        return ProductOption.builder()
                .id(1L)
                .product(null)
                .price(1000)
                .quantity(100)
                .build();
    }
}