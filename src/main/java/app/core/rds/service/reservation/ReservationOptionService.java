package app.core.rds.service.reservation;

import app.api.reservation.request.ReservationOptionCreateDto;
import app.core.rds.entity.product.Product;
import app.core.rds.entity.product.ProductOption;
import app.core.rds.entity.reservation.Reservation;
import app.core.rds.entity.reservation.ReservationOption;
import app.core.rds.repository.reservation.ReservationOptionRepository;
import app.core.rds.repository.reservation.query.ReservationRepositoryQuery;
import app.share.app.exception.AppException;
import app.share.app.exception.AppExceptionType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReservationOptionService {
    private final ReservationRepositoryQuery reservationRepositoryQuery;
    private final ReservationOptionRepository reservationOptionRepository;

    /**
     * [Fix] ReservationOption 객체를 저장한다.
     */
    @Transactional
    public ReservationOption saveBy(Product product, ProductOption productOption, Reservation reservation, ReservationOptionCreateDto reservationOptionDto) {
        var reservationOption = createBy(product, productOption, reservation, reservationOptionDto);

        return reservationOptionRepository.save(reservationOption);
    }

    /**
     * [Fix] ReservationOption 컬렉션을 저장한다.
     */
    @Transactional
    public List<ReservationOption> saveAllBy(Product product, List<ProductOption> productOptions, Reservation reservation, List<ReservationOptionCreateDto> reservationOptionDtos) {
        var reservationOptions = createAllBy(product, productOptions, reservation, reservationOptionDtos);

        return reservationOptionRepository.saveAll(reservationOptions);
    }

    /**
     * [Fix] ReservationOption 컬렉션을 가져온다.
     */
    @Transactional(readOnly = true)
    public List<ReservationOption> getAllBy(List<Long> reservationIds) {
        return reservationRepositoryQuery.getAllBy(reservationIds);
    }

    private ReservationOption createBy(Product product, ProductOption productOption, Reservation reservation, ReservationOptionCreateDto reservationOptionDto) {
        if (productOption.getQuantity() < reservationOptionDto.getProductOptionQuantity()) {
            throw AppException.of400(AppExceptionType.PRODUCT_OPTION_STOCK_ERROR);
        }

        return ReservationOption.of(
                product,
                productOption,
                reservation,
                reservationOptionDto.getProductOptionQuantity()
        );
    }

    private List<ReservationOption> createAllBy(Product product, List<ProductOption> productOptions, Reservation reservation, List<ReservationOptionCreateDto> reservationOptionDtos) {
        var results = new ArrayList<ReservationOption>();

        for (var reservationOptionDto : reservationOptionDtos) {
            var productOptionId = reservationOptionDto.getProductOptionId();
            var productOption = productOptions.stream()
                    .filter(e -> e.getId().equals(productOptionId))
                    .findAny().orElseThrow();

            results.add(createBy(product, productOption, reservation, reservationOptionDto));
        }

        return results;
    }
}