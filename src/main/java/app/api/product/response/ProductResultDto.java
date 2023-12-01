package app.api.product.response;

import app.api.place.response.PlaceResultDto;
import app.core.rds.entity.file.File;
import app.core.rds.entity.place.Place;
import app.core.rds.entity.product.Product;
import app.core.rds.entity.product.ProductOption;
import app.core.rds.entity.product.type.ProductGroupSubtype;
import app.core.rds.entity.product.type.ProductGroupType;
import app.share.app.exception.AppException;
import app.share.app.exception.AppExceptionType;
import lombok.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter @Setter @ToString @Builder
public class ProductResultDto implements Serializable {
    private PlaceResultDto place;
    private Long productId;
    private String productName; // 상품 이름
    private LocalDateTime productDateTo; // 상품 기간
    private LocalDateTime productDateFrom; // 상품 기간
    private ProductGroupType productGroupType; // 상품 그룹 유형
    private ProductGroupSubtype productGroupSubtype; // 상품 그룹 유형
    private ProductLikeResultDto productLike;
    private List<ProductFileResultDto> productFiles;
    private List<ProductOptionResultDto> productOptions;

    public static ProductResultDto of(Place place, Product product, List<File> productFiles) {
        return ProductResultDto.builder()
                .place(PlaceResultDto.of(place))
                .productId(product.getId())
                .productName(product.getName())
                .productDateTo(product.getDateTo())
                .productDateFrom(product.getDateFrom())
                .productGroupType(product.getGroupType())
                .productGroupSubtype(product.getGroupSubtype())
                .productFiles(ProductFileResultDto.of(productFiles))
                .build();
    }

    public static ProductResultDto of(Place place, Product product, List<File> productFiles, List<ProductOption> productOptions) {
        return ProductResultDto.builder()
                .place(PlaceResultDto.of(place))
                .productId(product.getId())
                .productName(product.getName())
                .productDateTo(product.getDateTo())
                .productDateFrom(product.getDateFrom())
                .productGroupType(product.getGroupType())
                .productGroupSubtype(product.getGroupSubtype())
                .productFiles(ProductFileResultDto.of(productFiles))
                .productOptions(ProductOptionResultDto.of(productOptions))
                .build();
    }

    public static ProductResultDto of(Place place, Product product, List<File> productFiles, List<ProductOption> productOptions, Boolean productLike) {
        return ProductResultDto.builder()
                .place(PlaceResultDto.of(place))
                .productId(product.getId())
                .productName(product.getName())
                .productDateTo(product.getDateTo())
                .productDateFrom(product.getDateFrom())
                .productGroupType(product.getGroupType())
                .productGroupSubtype(product.getGroupSubtype())
                .productLike(ProductLikeResultDto.of(productLike))
                .productFiles(ProductFileResultDto.of(productFiles))
                .productOptions(ProductOptionResultDto.of(productOptions))
                .build();
    }

    public static List<ProductResultDto> of(List<Place> places, List<Product> products, List<File> productFiles) {
        var results = new ArrayList<ProductResultDto>();

        for (var targetProduct : products) {
            // var targetPlace = product.getPlace();
            var targetPlace = places.stream()
                    .filter(e -> e.equals(targetProduct.getPlace()))
                    .findAny().orElseThrow(() -> AppException.of404(AppExceptionType.PLACE_GET_ERROR));
            var targetProductFiles = productFiles.stream()
                    .filter(e -> e.getProduct().getId().equals(targetProduct.getId()))
                    .collect(Collectors.toList());

            results.add(of(targetPlace, targetProduct, targetProductFiles));
        }

        return results;
    }

    public static List<ProductResultDto> of(List<Place> places, List<Product> products, List<File> productFiles, List<ProductOption> productOptions) {
        var results = new ArrayList<ProductResultDto>();

        for (var targetProduct : products) {
            // var targetPlace = product.getPlace();
            var targetPlace = places.stream()
                    .filter(e -> e.equals(targetProduct.getPlace()))
                    .findAny().orElseThrow(() -> AppException.of404(AppExceptionType.PLACE_GET_ERROR));
            var targetProductFiles = productFiles.stream()
                    .filter(e -> e.getProduct().getId().equals(targetProduct.getId()))
                    .collect(Collectors.toList());
            var targetProductOptions = productOptions.stream()
                    .filter(e -> e.getProduct().getId().equals(targetProduct.getId()))
                    .collect(Collectors.toList());

            results.add(of(targetPlace, targetProduct, targetProductFiles, targetProductOptions));
        }

        return results;
    }
}