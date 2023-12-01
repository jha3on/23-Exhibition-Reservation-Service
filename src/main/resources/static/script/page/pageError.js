/* -------------------------------------------------------------------------------------------------------------------*/
document.addEventListener('DOMContentLoaded', function (event) {
  // [Fix] 페이지 제목 세팅
  pageSub.setPageName();
  // [Fix] 홈 화면 이벤트 처리
  document.querySelector('.js-error')?.addEventListener('click', function (event) {
    location.replace('/');
    // history.back();
  });
});
/* -------------------------------------------------------------------------------------------------------------------*/
const pageSub = {
  // [Fix] 페이지 제목 세팅
  setPageName: function () {
    page.setPageTextElement('.js-header-name', '아티켓');
  },
}
/* -------------------------------------------------------------------------------------------------------------------*/