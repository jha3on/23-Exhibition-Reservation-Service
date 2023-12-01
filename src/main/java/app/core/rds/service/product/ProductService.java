package app.core.rds.service.product;

import app.api.product.request.ProductCreateDto;
import app.core.rds.entity.place.Place;
import app.core.rds.entity.place.type.PlaceGroupSubtype;
import app.core.rds.entity.place.type.PlaceGroupType;
import app.core.rds.entity.product.Product;
import app.core.rds.entity.product.type.ProductGroupType;
import app.core.rds.repository.product.ProductRepository;
import app.core.rds.repository.product.query.ProductRepositoryQuery;
import app.share.app.exception.AppException;
import app.share.app.exception.AppExceptionType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductRepositoryQuery productRepositoryQuery;

    /**
     * [Fix] Product 객체를 저장한다.
     */
    @Transactional
    public Product saveBy(Place place, ProductCreateDto productDto) {
        var product = createBy(place, productDto);

        return productRepository.save(product);
    }

    /**
     * [Fix] Product 객체를 가져온다.
     */
    @Transactional(readOnly = true)
    public Product getBy(Long productId) {
        var productOpt = productRepositoryQuery.getByProductId(productId);

        if (productOpt.isEmpty()) {
            throw AppException.of404(AppExceptionType.PRODUCT_GET_ERROR);
        } else return productOpt.get();
    }

    /**
     * [Fix] Product 컬렉션을 가져온다.
     */
    @Transactional(readOnly = true)
    public List<Product> getAllBy(String productName) {
        return productRepositoryQuery.getAllByProductName(productName);
    }

    /**
     * [Fix] Product 컬렉션을 가져온다.
     */
    @Transactional(readOnly = true)
    public List<Product> getAllBy(PlaceGroupType placeGroupType, PlaceGroupSubtype placeGroupSubtype, ProductGroupType productGroupType) {
        return productRepositoryQuery.getAllByFilterTypes(placeGroupType, placeGroupSubtype, productGroupType);
    }

    private Product createBy(Place place, ProductCreateDto productDto) {
        return Product.of(
                place,
                productDto.getProductName(),
                productDto.getProductDateTo(),
                productDto.getProductDateFrom(),
                productDto.getProductGroupType(),
                productDto.getProductGroupSubtype()
        );
    }
}