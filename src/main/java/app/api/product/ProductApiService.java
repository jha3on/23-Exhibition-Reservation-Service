package app.api.product;

import app.core.rds.service.product.ProductOptionService;
import app.core.rds.service.place.PlaceService;
import app.core.rds.service.product.ProductFileService;
import app.core.rds.service.product.ProductLikeService;
import app.core.rds.service.product.ProductService;
import app.api.product.request.ProductCreateDto;
import app.api.product.response.ProductLikeResultDto;
import app.api.product.response.ProductOptionResultDto;
import app.api.product.response.ProductPathResultDto;
import app.api.product.response.ProductResultDto;
import app.core.rds.service.user.UserService;
import app.core.rds.entity.place.type.PlaceGroupSubtype;
import app.core.rds.entity.place.type.PlaceGroupType;
import app.core.rds.entity.product.Product;
import app.core.rds.entity.product.type.ProductGroupType;
import app.share.app.config.redis.RedisCacheKey;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductApiService {
    private final UserService userService;
    private final PlaceService placeService;
    private final ProductService productService;
    private final ProductFileService productFileService;
    private final ProductOptionService productOptionService;
    private final ProductLikeService productLikeService;

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * [Fix] 상품을 생성한다.
     */
    @Transactional
    public ProductPathResultDto createProductBy(ProductCreateDto productDto, List<MultipartFile> attachments) {
        var place = placeService.getBy(productDto.getPlaceId());
        var product = productService.saveBy(place, productDto);
        var productFiles = productFileService.saveAllBy(product, attachments);
        var productOptions = productOptionService.saveAllBy(product, productDto.getProductOptions());

        return ProductPathResultDto.of(product);
    }

    /**
     * [Fix] 상품을 조회한다.
     */
    @Transactional(readOnly = true)
    public ProductResultDto getProductBy(Long userId, Long productId) {
        var place = placeService.getByProductId(productId);
        var product = productService.getBy(productId);
        var productLike = (userId != null) && productLikeService.existsBy(userId, productId);
        var productFiles = productFileService.getAllBy(productId);
        var productOptions = productOptionService.getAllBy(productId);

        return ProductResultDto.of(place, product, productFiles, productOptions, productLike);
    }

    /**
     * [Fix] 상품 옵션 목록을 조회한다.
     */
    @Transactional(readOnly = true)
    public List<ProductOptionResultDto> getProductOptionsBy(Long productId) {
        var product = productService.getBy(productId);
        var productOptions = productOptionService.getAllBy(productId);

        return ProductOptionResultDto.of(productOptions);
    }

    /**
     * [Fix] 상품 목록을 검색 조회한다.
     */
    @Transactional(readOnly = true)
    public List<ProductResultDto> getProductsBy(String productName) {
        var products = productService.getAllBy(productName);
        var productFiles = productFileService.getAllBy(getProductIds(products));
        var productOptions = productOptionService.getAllBy(getProductIds(products));
        var places = placeService.getAllBy(getProductIds(products));

        return ProductResultDto.of(places, products, productFiles, productOptions);
    }

    /**
     * [Fix] 상품 목록을 필터링 조회한다.
     */
    @Transactional(readOnly = true)
    @Cacheable(value = RedisCacheKey.PRODUCT_LIST, key = "{#placeGroupType, #placeGroupSubtype, #productGroupType}", cacheManager = "redisCacheManager")
    public List<ProductResultDto> getProductsBy(PlaceGroupType placeGroupType, PlaceGroupSubtype placeGroupSubtype, ProductGroupType productGroupType) {
        var products = productService.getAllBy(placeGroupType, placeGroupSubtype, productGroupType);
        var productFiles = productFileService.getAllBy(getProductIds(products));
        // var productOptions = productOptionService.getAllBy(getProductIds(products));
        var places = placeService.getAllBy(getProductIds(products));

        // return ProductResultDto.of(places, products, productFiles, productOptions);
        return ProductResultDto.of(places, products, productFiles);
    }

    @Transactional(readOnly = true)
    public List<ProductResultDto> getProductLikesBy(Long userId) {
        var user = userService.getBy(userId);

        return null;
    }

    /**
     * [Fix] 관심 상품을 생성/삭제한다.
     */
    @Transactional
    public ProductLikeResultDto toggleProductLikeBy(Long userId, Long productId) {
        var user = userService.getBy(userId);
        var product = productService.getBy(productId);
        var productLike = productLikeService.toggleBy(user, product);

        return ProductLikeResultDto.of(productLike);
    }

    private List<Long> getProductIds(List<Product> products) {
        return products.stream()
                .map(Product::getId)
                .collect(Collectors.toList());
    }

    // -----------------------------------------------------------------------------------------------------------------
}