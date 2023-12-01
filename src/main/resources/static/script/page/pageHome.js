/* -------------------------------------------------------------------------------------------------------------------*/
document.addEventListener('DOMContentLoaded', function (event) {
  // [Fix] 페이지 네비게이션 메뉴 세팅
  page.setPageNavigatorMenu();
  // [Fix] 페이지 이름 세팅
  pageSub.setPageName();
  // [Fix] 프로모션 목록 세팅
  pageSub.setPromotionList().then(() => pageSub.createPromotionListSlideElement());
  // pageSub.setPromotionList().then();
});
/* -------------------------------------------------------------------------------------------------------------------*/
const pageSub = {
  // [Fix] 페이지 이름 세팅
  setPageName: function () {
    page.setPageTextElement('.js-header-name', '아티켓');
  },
  // [Fix] 프로모션 목록 세팅
  setPromotionList: async function () {
    const { result } = await pageApi.promotionListByGet('daily_best');

    // result.forEach((resultItem, i) => {
    //   document.querySelector('.js-promotion-list').insertAdjacentHTML('afterbegin', pageSub.createPromotionListElement(resultItem));
    // });
    for (const resultItem of result) {
      const i = result.indexOf(resultItem);
      document.querySelector('.js-promotion-list').insertAdjacentHTML('afterbegin', await pageSub.createPromotionListElement(resultItem));
    }
  },
  /* -----------------------------------------------------------------------------------------------------------------*/
  // [Fix] 프로모션 목록 태그 생성
  createPromotionListElement: async function (resultItem) {
    const color = await utils.getPromotionTextColor(resultItem.promotionFiles[0].promotionFilePath);
    const textColor = color.isDark ? '#ffffff' : '#000000';
    const bgColor = `rgba(${color.value[0]}, ${color.value[1]}, ${color.value[2]}, 0.7)`; // or color.hex

    return `
      <li class="swiper-slide grid-list-item">
        <a class="grid-item-link" href="/product/${resultItem.productId}">
          <div class="grid-item-card">
            <div class="promotion-image">
              <img class="image promotion-image" alt="프로모션 이미지" src="${resultItem.promotionFiles[0].promotionFilePath}">
            </div>
            <div class="promotion-info" style="background: ${bgColor};">
              <span class="text-label" style="color: ${textColor}">${resultItem.productName}</span>
              <span class="text" style="color: ${textColor}">${utils.setDateFormat(resultItem.productDateFrom)} ~ ${utils.setDateFormat(resultItem.productDateTo)}</span>
              <span class="text" style="color: ${textColor}">${resultItem.placeName}</span>
            </div>
          </div>
        </a>
      </li>
    `;
  },
  // [Fix] 프로모션 목록 슬라이드 태그 생성
  createPromotionListSlideElement: function () {
    page.setPageSlideElement('.swiper', '.swiper-pagination');
  },
  /* -----------------------------------------------------------------------------------------------------------------*/
};
/* -------------------------------------------------------------------------------------------------------------------*/