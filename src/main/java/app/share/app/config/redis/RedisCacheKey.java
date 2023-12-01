package app.share.app.config.redis;

public class RedisCacheKey {
    public static final Integer DEFAULT_TTL = 60 * 60; // 1시간

    public static final String PRODUCT_LIST = "product_list";
    public static final Integer PRODUCT_LIST_TTL = 60 * 60 * 3; // 3시간

    public static final String PROMOTION_LIST = "promotion_list";
    public static final Integer PROMOTION_LIST_TTL = 60 * 60 * 3; // 3시간
}