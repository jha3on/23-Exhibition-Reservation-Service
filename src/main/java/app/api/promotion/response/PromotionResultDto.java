package app.api.promotion.response;

import app.core.rds.entity.file.File;
import app.core.rds.entity.product.Product;
import app.core.rds.entity.promotion.Promotion;
import lombok.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter @Setter @ToString @Builder
public class PromotionResultDto implements Serializable {
    private Long productId;
    private String placeName;
    private String productName;
    private LocalDateTime productDateTo;
    private LocalDateTime productDateFrom;
    private List<PromotionFileResultDto> promotionFiles;

    public static PromotionResultDto of(Product product, List<File> promotionFiles) {
        var place = product.getPlace();

        return PromotionResultDto.builder()
                .productId(product.getId())
                .placeName(place.getName())
                .productName(product.getName())
                .productDateTo(product.getDateTo())
                .productDateFrom(product.getDateFrom())
                .promotionFiles(PromotionFileResultDto.of(promotionFiles))
                .build();
    }

    public static List<PromotionResultDto> of(List<Promotion> promotions, List<File> promotionFiles) {
        var results = new ArrayList<PromotionResultDto>();
        var promotionProducts = promotions.stream()
                .map(Promotion::getProduct)
                .toList();

        for (var targetPromotionProduct : promotionProducts) {
            var targetPromotionProductFiles = promotionFiles.stream()
                    // 프로모션 이미지 대신 상품 이미지 조회로 변경
                    .filter(e -> e.getProduct().getId().equals(targetPromotionProduct.getId()))
                    .collect(Collectors.toList());

            results.add(of(targetPromotionProduct, targetPromotionProductFiles));
        }

        return results;
    }

//    public static PromotionResultDto of(Promotion promotion, List<File> promotionFiles) {
//        var product = promotion.getProduct();
//        var place = product.getPlace();
//
//        return PromotionResultDto.builder()
//                .productId(product.getId())
//                .placeName(place.getName())
//                .productName(product.getName())
//                .productDateTo(product.getDateTo())
//                .productDateFrom(product.getDateFrom())
//                .promotionFiles(PromotionFileResultDto.of(promotionFiles))
//                .build();
//    }

//    public static List<PromotionResultDto> of(List<Promotion> promotions, List<File> promotionFiles) {
//        var results = new ArrayList<PromotionResultDto>();
//
//        for (var targetPromotion : promotions) {
//            var targetPromotionFiles = promotionFiles.stream()
//                    .filter(e -> e.getPromotion().getId().equals(targetPromotion.getId()))
//                    .collect(Collectors.toList());
//
//            results.add(of(targetPromotion, targetPromotionFiles));
//        }
//
//        return results;
//    }
}