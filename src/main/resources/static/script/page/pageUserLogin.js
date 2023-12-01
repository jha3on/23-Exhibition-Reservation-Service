/* [Fix] */

/* -------------------------------------------------------------------------------------------------------------------*/
document.addEventListener('DOMContentLoaded', function (event) {
  // [Fix] 페이지 네비게이션 메뉴 세팅
  page.setPageNavigatorMenu();
  // [Fix] 페이지 이름 세팅
  pageSub.setPageName();
  // [Fix] 회원 로그인 이벤트 처리
  document.querySelector('.js-user-login')?.addEventListener('click', function (event) {
    pageSub.handleUserLogin().then();
  });
});
/* -------------------------------------------------------------------------------------------------------------------*/
const pageSub = {
  // [Fix] 페이지 이름 세팅
  setPageName: function () {
    page.setPageTextElement('.js-header-name', '멤버십 로그인');
  },
  // [Fix] 페이지 모달 세팅
  setPageModal: function (name, message) {
    page.setPageModal(document.querySelector('.js-modal'));
    page.setPageModalName(document.querySelector('.js-modal-name'), name);
    page.setPageModalMessage(document.querySelector('.js-modal-message'), message);
  },
  /* -----------------------------------------------------------------------------------------------------------------*/
  // [Fix] 회원 로그인 핸들러
  handleUserLogin: async function () {
    const { code } = await pageApi.userLoginByPost(
      document.querySelector('.js-user-email').value,
      document.querySelector('.js-user-email-option').value,
      document.querySelector('.js-user-password').value
    );
    if (code !== '+100') {
      pageSub.setPageModal('알림', '이메일 또는 비밀번호를 확인해주세요.');
    } else {
      location.replace('/user');
    }
  },
}
/* -------------------------------------------------------------------------------------------------------------------*/