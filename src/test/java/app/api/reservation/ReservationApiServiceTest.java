//package app.sjh.api.reservation;
//
//import app.sjh.api.reservation.request.ReservationCreateDto;
//import app.sjh.api.reservation.request.ReservationOptionCreateDto;
//import app.sjh.api.utility.ConcurrencyExecutorService;
//import app.sjh.core.rds.entity.product.Product;
//import app.sjh.core.rds.entity.product.ProductOption;
//import app.sjh.core.rds.entity.reservation.Reservation;
//import app.sjh.core.rds.entity.reservation.ReservationOption;
//import app.sjh.core.rds.entity.reservation.type.ReservationStatusType;
//import app.sjh.core.rds.entity.user.User;
//import app.sjh.core.rds.service.product.ProductOptionService;
//import app.sjh.core.rds.service.product.ProductService;
//import app.sjh.core.rds.service.reservation.ReservationOptionService;
//import app.sjh.core.rds.service.reservation.ReservationService;
//import app.sjh.core.rds.service.user.UserService;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.concurrent.atomic.AtomicInteger;
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.mockito.BDDMockito.given;
//
//@Slf4j
//@ExtendWith(value = MockitoExtension.class)
//class ReservationApiServiceTest {
//    @Mock private UserService userService;
//    @Mock private ProductService productService;
//    @Mock private ProductOptionService productOptionService;
//    @Mock private ReservationService reservationService;
//    @Mock private ReservationOptionService reservationOptionService;
//    @InjectMocks private ReservationApiService reservationApiService;
//
//    private User dummyUser;
//    private Product dummyProduct;
//    private List<ProductOption> dummyProductOptions;
//    private Reservation dummyReservation;
//    private ReservationCreateDto dummyReservationCreateDto;
//    private List<ReservationOption> dummyReservationOptions;
//    private List<ReservationOptionCreateDto> dummyReservationOptionCreateDtos;
//
//    @BeforeEach
//    void beforeEach() {
//        dummyUser = createDummyUser();
//        dummyProduct = createDummyProduct();
//        dummyProductOptions = createDummyProductOptions(dummyProduct);
//        dummyReservation = createDummyReservation(dummyUser, dummyProduct);
//        dummyReservationOptions = createDummyReservationOptions(dummyProduct, dummyProductOptions, dummyReservation);
//        dummyReservationOptionCreateDtos = createReservationOptionCreateDtos();
//        dummyReservationCreateDto = createDummyReservationCreateDto(dummyReservationOptionCreateDtos);
//    }
//
//    @Test
//    @DisplayName(value = "예매하기_테스트")
//    void 예매하기_테스트() throws InterruptedException {
//        // [Given]
//        given(userService.getBy(1L)).willReturn(dummyUser);
//        given(productService.getBy(1L)).willReturn(dummyProduct);
//        given(productOptionService.getAllBy(1L)).willReturn(dummyProductOptions);
//        given(reservationService.saveBy(dummyUser, dummyProduct)).willReturn(dummyReservation);
//        given(reservationOptionService.saveAllBy(dummyProduct, dummyProductOptions, dummyReservation, dummyReservationOptionCreateDtos)).willReturn(dummyReservationOptions);
//
//        // [When]
//        var result = reservationApiService.createReservationBy(1L, 1L, dummyReservationCreateDto);
//
//        // [Then]
//        System.out.println(result);
//        assertThat(result.getReservationId()).isEqualTo(1L);
//    }
//
//    @Test
//    @DisplayName(value = "예매하기_동시성_테스트")
//    void 예매하기_동시성_테스트() throws InterruptedException {
//        var successCount = new AtomicInteger();
//        ConcurrencyExecutorService.execute(() -> 예매하기_테스트(), successCount);
//
//        assertThat(successCount.get()).isEqualTo(1);
//    }
//
//    static User createDummyUser() {
//        return User.builder().id(1L).build();
//    }
//
////    static User createDummyUser() {
////        return User.builder()
////                .id(1L)
////                .name("테스트 회원")
////                .email("test1@test.com")
////                .contact("010-0000-0000")
////                .password("test1")
////                .build();
////    }
//
//    static Product createDummyProduct() {
//        return Product.builder().id(1L).build();
//    }
//
////    static Product createDummyProduct() {
////        return Product.builder()
////                .id(1L)
////                .place(null)
////                .name("테스트 상품")
////                .dateTo(LocalDateTime.now())
////                .dateFrom(LocalDateTime.now())
////                .groupType(ProductGroupType.PRODUCTION)
////                .groupSubtype(ProductGroupSubtype.ALL)
////                .build();
////    }
//
//    static List<ProductOption> createDummyProductOptions(Product dummyProduct) {
//        var productOptions = new ArrayList<ProductOption>();
//        for (int i = 1; i <= 3; i++) {
//            productOptions.add(ProductOption.builder()
//                    .id((long) i) // 1, 2, 3
//                    .product(dummyProduct)
//                    .price(i * 1000) // 1000, 2000, 3000
//                    .quantity(100) // 100, 100, 100
//                    .build()
//            );
//        }
//
//        return productOptions;
//    }
//
////    static List<ProductOption> createDummyProductOptions(Product dummyProduct) {
////        var productOptions = new ArrayList<ProductOption>();
////        for (int i = 1; i <= 3; i++) {
////            productOptions.add(ProductOption.builder()
////                    .id((long) i)
////                    .product(dummyProduct)
////                    .name("테스트 상품 옵션" + i)
////                    .price(i * 1000)
////                    .quantity(100)
////                    .build()
////            );
////        }
////
////        return productOptions;
////    }
//
//    static Reservation createDummyReservation(User dummyUser, Product dummyProduct) {
//        return Reservation.builder()
//                .id(1L)
//                .user(dummyUser)
//                .product(dummyProduct)
//                .statusType(ReservationStatusType.READY)
//                .build();
//    }
//
//    static List<ReservationOption> createDummyReservationOptions(Product dummyProduct, List<ProductOption> dummyProductOptions, Reservation dummyReservation) {
//        var reservationOptions = new ArrayList<ReservationOption>();
//        for (int i = 1; i <= 1; i++) {
//            reservationOptions.add(ReservationOption.builder()
//                    .id((long) i)
//                    .product(dummyProduct)
//                    .reservation(dummyReservation)
//                    .productOption(dummyProductOptions.get(0))
//                    .price(dummyProductOptions.get(0).getPrice())
//                    .quantity(1)
//                    .build()
//            );
//        }
//
//        return reservationOptions;
//    }
//
//    static ReservationCreateDto createDummyReservationCreateDto(List<ReservationOptionCreateDto> dummyReservationOptionCreateDtos) {
//        return ReservationCreateDto.builder()
//                .reservationOptions(dummyReservationOptionCreateDtos)
//                .build();
//    }
//
//    static List<ReservationOptionCreateDto> createReservationOptionCreateDtos() {
//        var reservationOptionCreateDtos = new ArrayList<ReservationOptionCreateDto>();
//        for (int i = 1; i <= 1; i++) {
//            reservationOptionCreateDtos.add(ReservationOptionCreateDto.builder()
//                    .productOptionId(1L)
//                    .productOptionQuantity(3)
//                    .build()
//            );
//        }
//
//        return reservationOptionCreateDtos;
//    }
//}