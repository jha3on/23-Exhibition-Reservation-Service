/* -------------------------------------------------------------------------------------------------------------------*/
document.addEventListener('DOMContentLoaded', function (event) {
  // [Fix] 페이지 네비게이션 메뉴 세팅
  page.setPageNavigatorMenu();
  // [Fix] 페이지 이름 세팅
  pageSub.setPageName();
  // [Fix] 상품 목록 세팅
  pageSub.setPageProductList().then();
  // [Fix] 상품 그룹 탭 선택
  [...document.querySelectorAll('.js-product-type-tab')].forEach(function (element) {
    element.addEventListener('click', function (event) {
      pageSub.setProductGroupType(event.target);
      pageSub.deleteProductListElement();
      pageSub.setPageProductList().then();
    });
  });
  // [Fix] 장소 그룹 탭 선택
  [...document.querySelectorAll('.js-place-type-tab')].forEach(function (element) {
    element.addEventListener('click', function (event) {
      pageSub.setPlaceGroupType(event.target);
      pageSub.deleteProductListElement();
      pageSub.setPageProductList().then();
    });
  });
  // 장소 하위 그룹 탭 선택
  document.querySelector('.js-place-subtype-list')?.addEventListener('click', function (event) {
    pageSub.setPageBottomSheet();
    // [TODO] ...
  });
});
/* -------------------------------------------------------------------------------------------------------------------*/
const pageSub = {
  // [Fix] 페이지 이름 세팅
  setPageName: function () {
    page.setPageTextElement('.js-header-name', '아티켓');
  },
  // [Fix] 페이지 모달 세팅
  setPageModal: function (name, message) {
    const pageModal = document.querySelector('.js-modal');
    const pageModalName = document.querySelector('.js-modal-name');
    const pageModalMessage = document.querySelector('.js-modal-message');

    page.setPageModal(pageModal);
    page.setPageModalName(pageModalName, name);
    page.setPageModalMessage(pageModalMessage, message);
  },
  // [Fix] 페이지 하단 시트 세팅
  setPageBottomSheet: function () {
    const pageBottomSheet = document.querySelector('.v1.js-sheet');

    page.setPageBottomSheet(pageBottomSheet);
  },
  // [Fix] 장소 그룹 탭 선택
  setPlaceGroupType: function (target) {
    [...document.querySelectorAll('.js-place-type-tab')].forEach(function (tab) {
      if (target === tab) {
        target.setAttribute('aria-selected', 'true');
      } else tab.setAttribute('aria-selected', 'false');
    });
  },
  // 장소 하위 그룹 탭 선택
  setPlaceSubGroupType: function (target) {
    // [TODO] ...
  },
  // [Fix] 상품 그룹 탭 선택
  setProductGroupType: function (target) {
    [...document.querySelectorAll('.js-product-type-tab')].forEach(function (tab) {
      if (target === tab) {
        target.setAttribute('aria-selected', 'true');
      } else tab.setAttribute('aria-selected', 'false');
    });
  },
  // [Fix] 상품 목록 세팅
  setPageProductList: async function () {
    const target = document.querySelector('.js-product-list');
    const { code, message, result } = await pageApi.productListByGet(
      document.querySelector('.js-place-type-tab[aria-selected=\'true\']').getAttribute('data-symbol'),
      document.querySelector('.js-place-subtype-tab[aria-selected=\'true\']').getAttribute('data-symbol'),
      document.querySelector('.js-product-type-tab[aria-selected=\'true\']').getAttribute('data-symbol')
    );

    if (code !== '+100') {
      pageSub.setPageModal('알림', message);
    }
    if (code === '+100' && result.length === 0) {
      target.insertAdjacentHTML('afterbegin', pageSub.createProductListEmptyElement());
    }
    if (code === '+100' && result.length !== 0) {
      result.forEach((resultItem, i) => {
        target.insertAdjacentHTML('afterbegin', pageSub.createProductListElement(resultItem));
      });
    }
  },

  /* -----------------------------------------------------------------------------------------------------------------*/
  // [Fix] 상품 목록 태그 생성
  createProductListElement: function (resultItem) {
    return `
      <li class="grid-list-item">
        <a class="grid-item-link" href=${`/product/${resultItem.productId}`}>
          <div class="grid-item-card">
            <div class="product-info-image">
              <img class="image" src=${resultItem.productFiles[0].productFilePath} alt="상품 사진">
            </div>
            <div class="product-info-tag">
              <span class="badge">${resultItem.place.placeGroupSubtype}</span>
              <span class="badge">${resultItem.place.placeGroupType}</span>
            </div>
            <div class="product-info-detail">
              <span class="title">${resultItem.productName}</span>
              <span class="text">${resultItem.place.placeName}</span>
              <span class="text">${utils.setDateFormat(resultItem.productDateFrom)} ~ ${utils.setDateFormat(resultItem.productDateTo)}</span>
            </div>
          </div>
        </a>
      </li>
    `;
  },
  // [Fix] 빈 상품 목록 태그 생성
  createProductListEmptyElement: function () {
    return `
      <li class="grid-list-item-empty">
        <img class="image" src="/image/icon/icon_stroke_data_empty.svg" alt="">
        <span class="text">등록된 상품이 없습니다.</span>
      </li>
    `;
  },
  // [Fix] 상품 목록 태그 삭제
  deleteProductListElement: function () {
    document.querySelector('.js-product-list').replaceChildren();
  },
  /* -----------------------------------------------------------------------------------------------------------------*/
};
/* -------------------------------------------------------------------------------------------------------------------*/