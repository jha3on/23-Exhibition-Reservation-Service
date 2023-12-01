/* -------------------------------------------------------------------------------------------------------------------*/
document.addEventListener('DOMContentLoaded', function (event) {
  // [Fix] 페이지 이름 세팅
  pageSub.setPageName();
  // [Fix] 상품 세팅
  pageSub.setProduct().then();
  // [Fix] 상품 가격 이벤트 처리
  document.querySelector('.js-product-price-list')?.addEventListener('click', function (event) {
    if (event.target.classList.contains('js-option-plus')) {
      pageSub.setProductCountPlus(event.target);
    }
    if (event.target.classList.contains('js-option-minus')) {
      pageSub.setProductCountMinus(event.target);
    }
  });
  // [Fix] 상품 예매 이벤트 처리
  document.querySelector('.js-reservation')?.addEventListener('click', function (event) {
    pageSub.handleReservationCreate().then(() => setTimeout(() => location.replace('/reservation/list'), 3000));
  });
  // [Fix] 상품 예매 취소 이벤트 처리
  document.querySelector('.js-reservation-cancel')?.addEventListener('click', function (event) {
    history.back();
  });
});
/* -------------------------------------------------------------------------------------------------------------------*/
const pageSub = {
  // [Fix] 페이지 이름 세팅
  setPageName: function () {
    page.setPageTextElement('.js-header-name', '예매');
  },
  // [Fix] 페이지 모달 세팅
  setPageModal: function (name, message) {
    page.setPageModal(document.querySelector('.js-modal'));
    page.setPageModalName(document.querySelector('.js-modal-name'), name);
    page.setPageModalMessage(document.querySelector('.js-modal-message'), message);
  },
  // [Fix] 상품 정보 세팅
  setProduct: async function () {
    const { result } = await pageApi.productByGet(utils.getProductId());
    const target = document.querySelector('.js-product-price-list');
    document.querySelector('.js-product-name').innerText = result.productName;
    document.querySelector('.js-product-place').innerText = result.place.placeName;
    document.querySelector('.js-product-period').innerText = utils.setDatePeriodFormat(result.productDateFrom, result.productDateTo);

    result.productOptions.forEach(productOption => {
      target.insertAdjacentHTML('afterbegin', pageSub.createProductOptionListElement(productOption));
    });
  },
  // [Fix] 상품 가격 세팅
  setProductCountPlus: function (target) {
    const item = target.closest('.js-price-list-item');
    const itemCount = item.querySelector('.js-option-count');
    const itemPrice = item.querySelector('.js-option-price');
    const itemCountPrice = item.querySelector('.js-option-count-price');
    const itemCountResult = Number(itemCount.innerHTML) + 1;
    const itemCountPriceResult = Number(utils.unsetPriceFormat(itemCountPrice.innerHTML)) + Number(utils.unsetPriceFormat(itemPrice.innerHTML));

    if (itemCountResult > 10) {
      pageSub.setPageModal('알림', '10매 이하로 선택해주세요.');
      return;
    }

    itemCount.innerHTML = String(itemCountResult);
    itemCountPrice.innerHTML = String(utils.setPriceFormat(itemCountPriceResult) + '원');
    pageSub.setProductTotalCountPlus(utils.unsetPriceFormat(itemPrice.innerHTML));
  },
  // [Fix] 상품 가격 세팅
  setProductCountMinus: function (target) {
    const item = target.closest('.js-price-list-item');
    const itemCount = item.querySelector('.js-option-count');
    const itemPrice = item.querySelector('.js-option-price');
    const itemCountPrice = item.querySelector('.js-option-count-price');
    const itemCountResult = Number(itemCount.innerHTML) - 1;
    const itemCountPriceResult = Number(utils.unsetPriceFormat(itemCountPrice.innerHTML)) - Number(utils.unsetPriceFormat(itemPrice.innerHTML));

    if (itemCountResult < 0) {
      pageSub.setPageModal('알림', '1매 이상으로 선택해주세요.');
      return;
    }

    itemCount.innerHTML = String(itemCountResult);
    itemCountPrice.innerHTML = String(utils.setPriceFormat(itemCountPriceResult) + '원');
    pageSub.setProductTotalCountMinus(utils.unsetPriceFormat(itemPrice.innerHTML));
  },
  // [Fix] 상품 총 가격 세팅
  setProductTotalCountPlus: function (itemPrice) {
    const itemTotalCount = document.querySelector('.js-option-total-count');
    const itemTotalPrice = document.querySelector('.js-option-total-price');

    itemTotalCount.innerHTML = String(utils.setPriceFormat(Number(utils.unsetPriceFormat(itemTotalCount.innerHTML)) + 1) + '매');
    itemTotalPrice.innerHTML = String(utils.setPriceFormat(Number(utils.unsetPriceFormat(itemTotalPrice.innerHTML)) + Number(itemPrice)) + '원');
  },
  // [Fix] 상품 총 가격 세팅
  setProductTotalCountMinus: function (itemPrice) {
    const itemTotalCount = document.querySelector('.js-option-total-count');
    const itemTotalPrice = document.querySelector('.js-option-total-price');

    itemTotalCount.innerHTML = String(utils.setPriceFormat(Number(utils.unsetPriceFormat(itemTotalCount.innerHTML)) - 1) + '매');
    itemTotalPrice.innerHTML = String(utils.setPriceFormat(Number(utils.unsetPriceFormat(itemTotalPrice.innerHTML)) - Number(itemPrice)) + '원');
  },
  /* -----------------------------------------------------------------------------------------------------------------*/
  handleReservationCreate: async function () {
    const productId = utils.getProductId();
    const productOptions = document.querySelectorAll('.js-price-list-item');
    const reservationOptions = [...productOptions].map((productOption) => {
      const productOptionId = productOption.getAttribute('data-key');
      const productOptionQuantity = productOption.querySelector('.js-option-count').innerText;
      return {
        productOptionId: productOptionId,
        productOptionQuantity: productOptionQuantity,
      };
    });

    console.log(reservationOptions);

    const { code } = await pageApi.reservationCreateByPost(productId, reservationOptions);
    if (code === '+100') {
      pageSub.setPageModal('알림', '예매가 완료되었습니다.');
      return;
    }
    if (code === '-206') {
      pageSub.setPageModal('알림', '로그인 후 예매해주세요.');
      return;
    }
  },
  /* -----------------------------------------------------------------------------------------------------------------*/
  // [Fix] 상품 정보 세팅
  createProductOptionListElement: function (productOption) {
    return `
      <li class="grid-list-item js-price-list-item" data-key="${productOption.productOptionId}">
        <div class="product-option-name">
          <span class="text">${productOption.productOptionName}</span>
          <span class="price js-option-price">${utils.setPriceFormat(productOption.productOptionPrice)}원</span>
        </div>
        <div class="product-option-count">
          <div class="product-option-choice">
            <button class="button js-option-minus">
              <img class="image" alt="1매 빼기" src="/image/icon/icon_stroke_minus.svg">
            </button>
            <span class="text js-option-count">0</span>
            <button class="button js-option-plus">
              <img class="image" alt="1매 더하기" src="/image/icon/icon_stroke_plus.svg">
            </button>
          </div>
          <div class="product-option-choice-result">
            <span class="price js-option-count-price">0원</span>
          </div>
        </div>
      </li>
    `;
  },
  /* -----------------------------------------------------------------------------------------------------------------*/
}
/* -------------------------------------------------------------------------------------------------------------------*/