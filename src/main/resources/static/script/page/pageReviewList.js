/* -------------------------------------------------------------------------------------------------------------------*/
document.addEventListener('DOMContentLoaded', function (event) {
  // [Fix] 페이지 네비게이션 메뉴 세팅
  page.setPageNavigatorMenu();
  // [Fix] 페이지 이름 세팅
  pageSub.setPageName();
  // [Fix] 리뷰 목록 세팅
  pageSub.setReviewList().then();
});
/* -------------------------------------------------------------------------------------------------------------------*/
const pageSub = {
  // [Fix] 페이지 이름 세팅
  setPageName: function () {
    page.setPageTextElement('.js-header-name', '아티켓');
  },
  // [Fix] 리뷰 목록 세팅
  setReviewList: async function () {
    const { result } = await pageApi.reviewListByGet();
    const target = document.querySelector('.js-review-list');

    result.forEach((resultItem, i) => {
      target.insertAdjacentHTML('afterbegin', pageSub.createReviewListElement(resultItem));
    });
  },
  /* -----------------------------------------------------------------------------------------------------------------*/
  // [Fix] 리뷰 목록 태그 생성
  createReviewListElement: function (resultItem) {
    return `
      <li class="grid-list-item">
        <div class="grid-item-card">
          <div class="review-info">
            <span class="text">${resultItem.reviewId}</span>
            <span class="text">${resultItem.reviewScore}</span>
            <span class="text">${resultItem.reviewContent}</span>
          </div>
        </div>
      </li>
    `;
  },
  /* -----------------------------------------------------------------------------------------------------------------*/
};
/* -------------------------------------------------------------------------------------------------------------------*/