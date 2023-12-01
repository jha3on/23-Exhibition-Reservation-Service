package app.core.rds.service.reservation;

import app.api.reservation.request.ReservationOptionCreateDto;
import app.core.rds.entity.product.Product;
import app.core.rds.entity.product.ProductOption;
import app.core.rds.entity.reservation.Reservation;
import app.core.rds.entity.reservation.ReservationOption;
import app.core.rds.entity.user.User;
import app.share.app.exception.AppException;
import app.share.app.exception.AppExceptionType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReservationRedissonService {
    private final RedissonClient redissonClient;
    private final ReservationService reservationService;
    private final ReservationOptionService reservationOptionService;

    protected static final String RESERVATION_SAVE_LOCK_PATTERN = "LOCK:RESERVATION:%s:%s";
    protected static final String RESERVATION_OPTIONS_SAVE_LOCK_PATTERN = "LOCK:RESERVATION-OPTIONS:%s:%s";

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<ReservationOption> saveAllReservationOptionBy(Product product, List<ProductOption> productOptions, Reservation reservation, List<ReservationOptionCreateDto> reservationOptionDtos) {
        var redissonLockKey = String.format(RESERVATION_OPTIONS_SAVE_LOCK_PATTERN, product.getId(), reservation.getId());
        var redissonLock = redissonClient.getLock(redissonLockKey);

        try {
            var available = redissonLock.tryLock(5, 1, TimeUnit.SECONDS);
            if (!available) {
                log.info("[INFO] LOCK 획득 실패");
                throw AppException.of500(AppExceptionType.REDISSON_TRANSACTION_LOCK_ERROR);
            }
            return reservationOptionService.saveAllBy(product, productOptions, reservation, reservationOptionDtos);
        } catch (InterruptedException e) {
            e.printStackTrace();
            throw AppException.of500(AppExceptionType.REDISSON_TRANSACTION_LOCK_ERROR);
        } finally {
            if (redissonLock.isLocked() && redissonLock.isHeldByCurrentThread()) {
                redissonLock.unlock();
            }
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Reservation saveReservationBy(User user, Product product) {
        var redissonLockKey = String.format(RESERVATION_SAVE_LOCK_PATTERN, user.getId(), product.getId());
        var redissonLock = redissonClient.getLock(redissonLockKey);

        try {
            var available = redissonLock.tryLock(10, 2, TimeUnit.SECONDS);
            if (!available) {
                log.info("[INFO] LOCK 획득 실패");
                throw AppException.of500(AppExceptionType.REDISSON_TRANSACTION_LOCK_ERROR);
            }
            return reservationService.saveBy(user, product);
        } catch (InterruptedException e) {
            e.printStackTrace();
            throw AppException.of500(AppExceptionType.REDISSON_TRANSACTION_LOCK_ERROR);
        } finally {
            if (redissonLock.isLocked() && redissonLock.isHeldByCurrentThread()) {
                redissonLock.unlock();
            }
        }
    }
}