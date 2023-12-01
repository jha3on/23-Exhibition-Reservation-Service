package app.core.rds.service.product;

import app.core.rds.entity.product.Product;
import app.core.rds.entity.product.ProductOption;
import app.api.product.request.ProductOptionCreateDto;
import app.core.rds.repository.product.ProductOptionRepository;
import app.core.rds.repository.product.query.ProductRepositoryQuery;
import app.share.app.exception.AppException;
import app.share.app.exception.AppExceptionType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductOptionService {
    private final ProductRepositoryQuery productRepositoryQuery;
    private final ProductOptionRepository productOptionRepository;

    /**
     * [Fix] ProductOption 객체를 저장한다.
     */
    @Transactional
    public ProductOption saveBy(Product product, ProductOptionCreateDto productOptionDto) {
        var productOption = createBy(product, productOptionDto);

        return productOptionRepository.save(productOption);
    }

    /**
     * [Fix] ProductOption 컬렉션을 저장한다.
     */
    @Transactional
    public List<ProductOption> saveAllBy(Product product, List<ProductOptionCreateDto> productOptionDtos) {
        var productOptions = createAllBy(product, productOptionDtos);

        return productOptionRepository.saveAll(productOptions);
    }

    /**
     * ProductOption 객체를 수정한다.
     */
    @Transactional
    public ProductOption updateBy(Long productOptionId, Integer productOptionQuantity) {
        var productOption = getBy(productOptionId);

        productOption.updateQuantity(productOptionQuantity);

        return productOption;
    }

    /**
     * [Fix] ProductOption 객체를 가져온다.
     */
    @Transactional(readOnly = true)
    public ProductOption getBy(Long productOptionId) {
        var productOptionOpt = productRepositoryQuery.getByProductOptionId(productOptionId);

        if (productOptionOpt.isEmpty()) {
            throw AppException.of404(AppExceptionType.PRODUCT_OPTION_GET_ERROR);
        } else return productOptionOpt.get();
    }

    /**
     * [Fix] ProductOption 컬렉션을 가져온다.
     */
    @Transactional(readOnly = true)
    public List<ProductOption> getAllBy(Long productId) {
        return productRepositoryQuery.getAllByProductId(productId);
    }

    /**
     * [Fix] ProductOption 컬렉션을 가져온다.
     */
    @Transactional(readOnly = true)
    public List<ProductOption> getAllBy(List<Long> productIds) {
        return productRepositoryQuery.getAllByProductIds(productIds);
    }

    private ProductOption createBy(Product product, ProductOptionCreateDto productOptionDto) {
        return ProductOption.of(product, productOptionDto.getProductOptionName(), productOptionDto.getProductOptionPrice(), productOptionDto.getProductOptionQuantity());
    }

    private List<ProductOption> createAllBy(Product product, List<ProductOptionCreateDto> productOptionDtos) {
        return productOptionDtos.stream()
                .map(productOptionDto -> createBy(product, productOptionDto))
                .collect(Collectors.toList());
    }
}