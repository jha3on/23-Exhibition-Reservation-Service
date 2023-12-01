package app.core.rds.service.product;

import app.core.rds.entity.product.ProductOption;
import app.share.app.exception.AppException;
import app.share.app.exception.AppExceptionType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductOptionRedissonService {
    private final RedissonClient redissonClient;
    private final ProductOptionService productOptionService;

    protected static final String PRODUCT_OPTION_UPDATE_LOCK_PATTERN = "LOCK:PRODUCT-OPTION:%s:%s";

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public ProductOption updateProductOptionBy(Long productOptionId, Integer productOptionQuantity) {
        var redissonLockKey = String.format(PRODUCT_OPTION_UPDATE_LOCK_PATTERN, productOptionId, productOptionQuantity);
        var redissonLock = redissonClient.getLock(redissonLockKey);
        // var threadName = Thread.currentThread().getName();

        try {
            // wait time: 락 획득까지의 대기 시간
            // lease time: 락 획득 이후부터 락 해제까지의 점유 시간
            var available = redissonLock.tryLock(30, 5, TimeUnit.SECONDS);
            if (!available) {
                log.info("[INFO] LOCK 획득 실패");
                throw AppException.of500(AppExceptionType.REDISSON_TRANSACTION_LOCK_ERROR);
            }
            // var productOption = productOptionService.getBy(productOptionId);
            // log.info("[INFO] Thread Name: {}, Product Option ID: {}, Product Option Update Stock Quantity: {}", threadName, productOptionId, productOption.getQuantity() + productOptionQuantity);
            return productOptionService.updateBy(productOptionId, productOptionQuantity);
        } catch (InterruptedException e) {
            e.printStackTrace();
            throw AppException.of500(AppExceptionType.REDISSON_TRANSACTION_LOCK_ERROR);
        } finally {
            if (redissonLock.isLocked() && redissonLock.isHeldByCurrentThread()) {
                redissonLock.unlock();
            }
        }
    }

//    @Transactional(propagation = Propagation.REQUIRES_NEW)
//    public ProductOption updateProductOptionBy(Long productOptionId, Integer productOptionQuantity) {
//        var redissonLockKey = String.format(PRODUCT_OPTION_UPDATE_LOCK_PATTERN, productOptionId, productOptionQuantity);
//        var redissonLock = redissonClient.getLock(redissonLockKey);
//        var threadName = Thread.currentThread().getName();
//
//        try {
//            // wait time: 락 획득까지의 대기 시간
//            // lease time: 락 획득 이후부터 락 해제까지의 점유 시간
//            var available = redissonLock.tryLock(5, 1, TimeUnit.SECONDS);
//            if (!available) {
//                log.info("[INFO] LOCK 획득 실패");
//                throw AppException.of500(AppExceptionType.REDISSON_TRANSACTION_LOCK_ERROR);
//            }
//            var productOption = productOptionService.getBy(productOptionId);
//            log.info("[INFO] Thread Name: {}, Product Option ID: {}, Product Option Update Stock Quantity: {}", threadName, productOptionId, productOption.getQuantity() + productOptionQuantity);
//            return productOptionService.updateBy(productOptionId, productOptionQuantity);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//            throw AppException.of500(AppExceptionType.REDISSON_TRANSACTION_LOCK_ERROR);
//        } finally {
//            if (redissonLock.isLocked() && redissonLock.isHeldByCurrentThread()) {
//                redissonLock.unlock();
//            }
//        }
//    }
}