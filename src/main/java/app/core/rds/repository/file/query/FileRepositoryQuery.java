package app.core.rds.repository.file.query;

import app.core.rds.entity.file.File;

import java.util.List;

public interface FileRepositoryQuery {

    /** [Fix] File 컬렉션 조회 */
    List<File> getAllByPlaceId(Long placeId);

    /** [Fix] File 컬렉션 조회 */
    List<File> getAllByPlaceIds(List<Long> placeIds);

    /** [Fix] File 컬렉션 조회 */
    List<File> getAllByProductId(Long productId);

    /** [Fix] File 컬렉션 조회 */
    List<File> getAllByProductIds(List<Long> productIds);

//    /** [Fix] File 컬렉션 조회 */
//    List<File> getAllByPromotionId(Long promotionId);
//
//    /** [Fix] File 컬렉션 조회 */
//    List<File> getAllByPromotionIds(List<Long> promotionIds);
}