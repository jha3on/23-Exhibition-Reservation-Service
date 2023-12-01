/* -------------------------------------------------------------------------------------------------------------------*/
document.addEventListener('DOMContentLoaded', function (event) {
  // [Fix] 상품 세팅
  pageSub.setProduct().then();
  // [Fix] 상품 이미지 세팅
  pageSub.setProductImage().then();
  // [Fix] 상품 옵션 세팅
  pageSub.setProductOption().then();
  // [Fix] 상품 탭 선택 이벤트
  [...document.querySelectorAll('.js-product-tab')].forEach(function (element) {
    element.addEventListener('click', function (event) {
      pageSub.setPageProductTab(event.target);
    });
  });
  // [Fix] 상품 장소 탭 선택 이벤트
  document.querySelector('.js-product-place-tab')?.addEventListener('click', function (event) {
    pageSub.setProductPlace().then();
  });
  // [Fix] 상품 옵션 탭 선택 이벤트
  document.querySelector('.js-product-price-tab')?.addEventListener('click', function (event) {
    pageSub.setProductOption().then();
  });
  // [Fix] 상품 후기 탭 선택 이벤트
  document.querySelector('.js-product-review-tab')?.addEventListener('click', function (event) {
    pageSub.setProductReviewList().then();
  });
  // [Fix] 입력 이벤트 처리
  document.addEventListener('input', function (event) {
    if (event.target === document.querySelector('.js-review-form-score')) {
      pageSub.updateReviewScoreElement(event.target);
    }
    if (event.target === document.querySelector('.js-review-form-comment')) {
      pageSub.updateReviewContentLengthElement(event.target);
    }
  });
  // [Fix] 클릭 이벤트 처리
  document.addEventListener('click', function (event) {
    if (event.target === document.querySelector('.js-review-form')) {
      pageSub.createReviewFormElement().then();
    }
    if (event.target === document.querySelector('.js-review-form-cancel')) {
      pageSub.deleteReviewFormElement();
    }
    if (event.target === document.querySelector('.js-review-form-complete')) {
      pageSub.handleReviewCreate().then(() => location.reload());
    }
    if (event.target === document.querySelector('.js-review-update')) {
      // ...
    }
    if (event.target === document.querySelector('.js-review-delete')) {
      // ...
    }
    if (event.target === document.querySelector('.js-product-like')) {
      pageSub.handleProductLike().then();
    }
    if (event.target === document.querySelector('.js-product-reservation')) {
      pageSub.setReservation().then();
    }
  });
});
/* -------------------------------------------------------------------------------------------------------------------*/
const pageSub = {
  // [Fix] 페이지 이름 세팅
  setPageName: function (name) {
    page.setPageTextElement('.js-header-name', name);
  },
  // [Fix] 페이지 모달 세팅
  setPageModal: function (name, message) {
    page.setPageModal(document.querySelector('.js-modal'));
    page.setPageModalName(document.querySelector('.js-modal-name'), name);
    page.setPageModalMessage(document.querySelector('.js-modal-message'), message);
  },

  // [Fix] 페이지 상품 탭 세팅
  setPageProductTab: function (target) {
    [...document.querySelectorAll('.js-product-tab')].forEach(function (tab) {
      if (target === tab) {
        target.setAttribute('aria-selected', 'true');
      } else tab.setAttribute('aria-selected', 'false');
    });
  },


  setReservation: async function () {
    const { code } = await pageApi.userByGet();
    if (code !== '+100') {
      pageSub.setPageModal('알림', '로그인 후 이용해주세요.');
      return;
    }
    location.replace(`/reservation/${utils.getProductId()}`);
  },


  // [Fix] 상품 세팅
  setProduct: async function () {
    const target = document.querySelector('.js-product');
    const { result } = await pageApi.productByGet(utils.getProductId());

    pageSub.setPageName(result.productName);
    pageSub.updateProductLikeIconElement(result.productLike);
    target.insertAdjacentHTML('afterbegin', pageSub.createProductElement(result));
  },
  // [Fix] 상품 이미지 세팅
  setProductImage: async function () {
    const target = document.querySelector('.js-product-image');
    const { result } = await pageApi.productByGet(utils.getProductId());

    target.insertAdjacentHTML('afterbegin', pageSub.createProductImageElement(result));
  },
  // [Fix] 상품 장소 탭
  setProductPlace: async function () {
    const target = document.querySelector('.js-product-content');
    const { result } = await pageApi.productPlaceByGet(utils.getProductId());

    target.replaceChildren();
    target.insertAdjacentHTML('afterbegin', pageSub.createPlaceElement(result));
  },
  // [Fix] 상품 옵션 세팅
  setProductOption: async function () {
    const target = document.querySelector('.js-product-content');
    const { result } = await pageApi.productOptionListByGet(utils.getProductId());

    target.replaceChildren();
    target.insertAdjacentHTML('afterbegin', pageSub.createProductOptionElement(result));
  },
  // [Fix] 상품 리뷰 목록 세팅
  setProductReviewList: async function () {
    const target = document.querySelector('.js-product-content');
    const { result: reviewListResult } = await pageApi.productReviewListByGet(utils.getProductId());
    const { result: reviewListScoreResult } = await pageApi.productReviewListScoreByGet(utils.getProductId());

    target.replaceChildren();
    target.insertAdjacentHTML('afterbegin', pageSub.createReviewElement(reviewListResult, reviewListScoreResult));
  },
  handleReviewCreate: async function () {
    const { code } = await pageApi.reviewCreateByPost(
      utils.getProductId(),
      document.querySelector('.js-review-form-score').value / 2,
      document.querySelector('.js-review-form-comment').value
    );
  },
  handleProductLike: async function () {
    const { code, result } = await pageApi.productLikeByPost(utils.getProductId());
    if (code !== '+100') {
      pageSub.setPageModal('알림', '로그인 후 이용해주세요.');
    } else {
      pageSub.updateProductLikeIconElement(result);
    }
  },
  /* -----------------------------------------------------------------------------------------------------------------*/
  // 리뷰 점수 확인
  getReviewScore: function (scoreSum, scoreCount) {
    const scoreAverage = (scoreSum !== 0 || scoreCount !== 0)
      ? String(Math.floor(scoreSum / scoreCount * 100) / 100)
      : String(0);

    return parseFloat(scoreAverage).toFixed(1);
  },
  // 리뷰 별점 확인
  getReviewScoreStar: function (scoreSum, scoreCount) {
    const scoreAverage = Number(pageSub.getReviewScore(scoreSum, scoreCount));
    return scoreAverage * 20;
  },
  /* -----------------------------------------------------------------------------------------------------------------*/
  // [Fix] 리뷰 폼 태그 생성
  createReviewFormElement: async function () {
    const target = document.querySelector('.js-review-form');
    const { code } = await pageApi.userByGet();
    if (code !== '+100') {
      pageSub.setPageModal('알림', '로그인 후 이용해주세요.');
      return;
    }

    target.replaceChildren();
    target.insertAdjacentHTML('afterbegin', pageSub.reviewFormFocus());
  },
  // [Fix] 리뷰 폼 태그 삭제
  deleteReviewFormElement: function () {
    const target = document.querySelector('.js-review-form');

    target.replaceChildren();
    target.insertAdjacentHTML('afterbegin', pageSub.reviewForm());
  },
  // [Fix] 리뷰 별점 태그 수정
  updateReviewScoreElement: function (target) {
    document.querySelector('.js-review-form-score-star').style.width = `${target.value * 10}%`;
  },
  // [Fix] 리뷰 내용 글자수 태그 수정
  updateReviewContentLengthElement: function (target) {
    const length = target.value.length;
    if (length <= 100) {
      document.querySelector('.js-review-form-length').innerText = target.value.length;
    }
  },
  // [Fix] 상품 태그 생성
  createProductElement: function (result) {
    return `
      <div class="product-info-tag">
        <span class="badge js-place-subtype">${result.place.placeGroupSubtype}</span>
        <span class="badge js-place-type">${result.place.placeGroupType}</span>
      </div>
      <div class="product-info-name">
        <span class="title js-product-name">${result.productName}</span>
      </div>
      <div class="product-info-place">
        <span class="text js-place">${result.place.placeName}</span>
      </div>
      <div class="product-info-guide">
        <ul class="grid-list">
          <li class="grid-list-item">
            <span class="text grid-item-label">관람 기간</span>
            <span class="text js-product-period">${utils.setDatePeriodFormat(result.productDateFrom, result.productDateTo)}</span>
          </li>
          <li class="grid-list-item">
            <span class="text grid-item-label">관람 등급</span>
            <span class="text js-product-subtype">${result.productGroupSubtype}</span>
          </li>
        </ul>
      </div>
    `;
  },
  // [Fix] 상품 이미지 태그 생성
  createProductImageElement: function (result) {
    return `
      <div class="product-info-image">
        <img class="image js-product-image" src="${result.productFiles[0].productFilePath}" alt="상품 이미지">
      </div>
    `;
  },
  // 관심 상품 아이콘 태그 수정
  updateProductLikeIconElement: function (productLike) {
    if (productLike.productResult === true) {
      document.querySelector('.js-product-like').style.setProperty('--product-like', 'url(../../image/icon/icon_fill_heart.svg)');
    } else {
      document.querySelector('.js-product-like').style.setProperty('--product-like', 'url(../../image/icon/icon_stroke_heart.svg)');
    }
  },
  // [Fix] 장소 안내 태그 세팅
  createPlaceElement: function (result) {
    return `
      <div class="product-detail-place">
        <div class="product-detail-place-label">
          <span class="text">장소 정보</span>
        </div>
        <div class="product-detail-place-label">
          <a class="text" target="_blank" href="https://map.naver.com/p/search/${result.placeAddress}">빠른 길찾기</a>
        </div>
      </div>
      <div class="product-detail-place-content">
        <ul class="grid-list">
          <li class="grid-list-item">
            <span class="text grid-item-label">장소</span>
            <span class="text">${result.placeName}</span>
          </li>
          <li class="grid-list-item">
            <span class="text grid-item-label">주소</span>
            <span class="text">${result.placeAddress}</span>
          </li>
          <li class="grid-list-item">
            <span class="text grid-item-label">대표 번호</span>
            <span class="text">${result.placeContact}</span>
          </li>
        </ul>
      </div>
    `;
  },
  // [Fix] 상품 옵션 안내 태그 세팅
  createProductOptionElement: function (result) {
    return `
      <div class="product-detail-price">
        <div class="product-detail-price-label">
          <span class="text">가격 안내</span>
        </div>
        <div class="product-detail-price-label">
          <span class="text">이용 정보</span>
        </div>
      </div>
      <div class="product-detail-price-content">
        <ul class="grid-list">
          ${result.map((resultItem, i) => `
            <li class="grid-list-item">
              <span class="text price-name">${resultItem.productOptionName}</span>
              <span class="text price">${utils.setPriceFormat(resultItem.productOptionPrice) + `원`}</span>
            </li>
          `).join('')}
        </ul>
      </div>
    `;
  },
  // [Fix] 관람 후기 태그 세팅
  createReviewElement: function (reviewListResult, reviewListScoreResult) {
    return `
      <div class="product-detail-review">
        <div class="product-detail-review-label">
          <span class="text">관람 후기</span>
          <span class="text">${reviewListScoreResult.reviewSumCount}개</span>
          <span class="text">(${pageSub.getReviewScore(reviewListScoreResult.reviewSumScore, reviewListScoreResult.reviewSumCount)}점)</span>
        </div>
        <div class="product-detail-review-label product-detail-review-score-label">
          <img class="image" src="/image/icon/icon_fill_star.svg" alt="상품 별점">
          <span class="text product-detail-review-star" style="width: ${pageSub.getReviewScoreStar(reviewListScoreResult.reviewSumScore, reviewListScoreResult.reviewSumCount)}%;">
            <img class="image" src="/image/icon/icon_fill_star_on.svg" alt="상품 별점">
          </span>
<!--          <img class="image" src="/image/ic-star-${pageSub.getReviewScoreStar(reviewListScoreResult.reviewSumScore, reviewListScoreResult.reviewSumCount)}.svg" alt="상품 평점">-->
<!--          <span class="text">${pageSub.getReviewScore(reviewListScoreResult.reviewSumScore, reviewListScoreResult.reviewSumCount)}</span>-->
        </div>
      </div>
      <div class="product-detail-review-user">
        <div class="product-detail-review-user-form js-review-form">
          <form class="review-user-form">
            <span class="text">관람 후기를 남겨보세요!</span>
            <button class="button js-review-write" type="button">작성</button>
          </form>
        </div>
        <div class="product-detail-review-user-list">
          <ul class="grid-list">
            ${reviewListResult.length === 0
              ? `<li class="grid-list-item-empty">등록된 관람 후기가 없습니다.</li>`
              : `${reviewListResult.map(reviewResult => `
                  <li class="grid-list-item">
                    <div class="review-user-score">
                      <img class="image" src="/image/icon/icon_fill_star.svg" alt="상품 별점">
                      <span class="text review-user-score-star" style="width: ${reviewResult.reviewScore * 20}%;">
                        <img class="image" src="/image/icon/icon_fill_star_on.svg" alt="상품 별점">
                      </span>
<!--                      <img class="image" src="/image/ic-star-${reviewResult.reviewScore}.svg" alt="상품 평점">-->
                    </div>
                    <div class="review-user-comment">
                      <span class="text">${reviewResult.reviewContent}</span>
                    </div>
                    <div class="review-user-comment-info">
                      <div class="review-user-info">
                        <span class="text">${reviewResult.reviewUserEmail}</span>
                        <span class="text point-center">${utils.setDateTimeFormat(reviewResult.reviewCreatedAt)}</span>
                      </div>
                      <div class="review-user-action">
                        <button class="button js-review-update" type="button">수정</button>
                        <button class="button js-review-delete point-center" type="button">삭제</button>
                      </div>
                    </div>
                  </li>
                `).join('')}
             `}
          </ul>
        </div>
      </div>
    `;
  },
  // [Fix] 관람 후기 폼 태그 세팅
  reviewForm: function () {
    return `
      <form class="review-user-form">
        <span class="text">관람 후기를 남겨보세요!</span>
        <button class="button" type="button">작성</button>
      </form>
    `;
  },
  // [Fix] 관람 후기 폼 태그 세팅
  reviewFormFocus: function () {
    return `
      <form class="review-user-form-focus">
        <div class="review-user-form-rate">
          <p class="review-user-form-rate-label">별점을 선택해주세요.</p>
          <div class="review-user-form-score">
            <img class="image" src="/image/icon/icon_fill_star.svg" alt="상품 별점">
            <span class="text js-review-form-score-star">
              <img class="image" src="/image/icon/icon_fill_star_on.svg" alt="상품 별점">
            </span>
            <!-- <input class="field js-review-form-score" type="range" value="1" step="1" min="0" max="10"> -->
            <input class="field js-review-form-score" type="range" value="0" step="2" min="2" max="10">
          </div>
        </div>
        <div class="review-user-form-write">
          <textarea class="field js-review-form-comment" placeholder="관람 후기를 남겨보세요!" maxlength="100"></textarea>
          <div class="review-user-form-write-info">
            <div class="review-user-form-write-length">
              <span class="text js-review-form-length">0</span>
              <span class="text js-review-form-length-limit">/ 100</span>
            </div>
            <div class="review-user-form-write-action">
              <button class="button js-review-form-cancel" type="button">취소</button>
              <button class="button js-review-form-complete" type="button">작성</button>
            </div>
          </div>
        </div>
      </form>
    `;
  },
  /* -----------------------------------------------------------------------------------------------------------------*/
};
/* -------------------------------------------------------------------------------------------------------------------*/