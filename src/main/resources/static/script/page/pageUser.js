/* [Fix] */

/* -------------------------------------------------------------------------------------------------------------------*/
document.addEventListener('DOMContentLoaded', function (event) {
  // [Fix] 페이지 메뉴 세팅
  page.setPageNavigatorMenu();
  // [Fix] 페이지 이름 세팅
  pageSub.setPageName();
  // [Fix] 회원 세팅
  pageSub.setUser().then();
  // [Fix] 마우스 이벤트 처리
  document.addEventListener('click', function (event) {
    // [Fix] 회원 로그아웃 이벤트 처리
    if (event.target === document.querySelector('.js-user-logout')) {
      pageSub.handleUserLogout().then();
    }
  });
});
/* -------------------------------------------------------------------------------------------------------------------*/
const pageSub = {
  // [Fix] 페이지 이름 세팅
  setPageName: function () {
    page.setPageTextElement('.js-header-name', '마이페이지');
  },
  // [Fix] 페이지 모달 세팅
  setPageModal: function (name, message) {
    page.setPageModal(document.querySelector('.js-modal'));
    page.setPageModalName(document.querySelector('.js-modal-name'), name);
    page.setPageModalMessage(document.querySelector('.js-modal-message'), message);
  },
  // [Fix] 회원 세팅
  setUser: async function () {
    const { result } = await pageApi.userByGet();

    if (result !== null) {
      pageSub.setLoginUser(result);
      pageSub.setLoginUserReservationCount().then();
      pageSub.setLoginUserCategory();
    } else {
      pageSub.setLogoutUser();
      pageSub.setLogoutUserReservationCount();
    }
  },
  // [Fix] 로그인 회원 세팅
  setLoginUser: function (result) {
    const target = document.querySelector('.js-user');

    target.insertAdjacentHTML('afterbegin', pageSub.createLoginUserElement(result));
  },
  // [Fix] 로그인 회원 예매 개수 세팅
  setLoginUserReservationCount: async function () {
    const { result } = await pageApi.reservationCountListByGet();
    const target = document.querySelector('.js-user-ticket');

    target.insertAdjacentHTML('afterbegin', pageSub.createLoginUserReservationCountElement(result));
  },
  // [Fix] 로그인 회원 카테고리 세팅
  setLoginUserCategory: function () {
    const target = document.querySelector('.js-user-category');

    target.insertAdjacentHTML('afterbegin', pageSub.createLoginUserCategoryElement());
  },
  // [Fix] 비로그인 회원 세팅
  setLogoutUser: function () {
    const target = document.querySelector('.js-user');

    target.insertAdjacentHTML('afterbegin', pageSub.createLogoutUserElement());
  },
  // [Fix] 비로그인 회원 티켓 세팅
  setLogoutUserReservationCount: function () {
    const target = document.querySelector('.js-user-ticket');

    target.insertAdjacentHTML('afterbegin', pageSub.createLogoutUserReservationCountElement());
  },
  /* -----------------------------------------------------------------------------------------------------------------*/
  // [Fix] 회원 로그아웃 핸들러
  handleUserLogout: async function () {
    const { code } = await pageApi.userLogoutByPost();
    if (code === '+100') {
      location.reload();
    }
  },
  /* -----------------------------------------------------------------------------------------------------------------*/
  // [Fix] 예매 개수
  filterReservationCount: function (result, type) {
    const count = result.filter((resultItem) => {
      if (resultItem.reservationStatusType === type) {
        return resultItem;
      }
    });
    return count[0].reservationCount;
  },
  // [Fix] 예매 개수 합계
  reduceReservationCount: function (result) {
    return result.reduce((prev, next) => {
      return prev + next.reservationCount;
    }, 0);
  },
  /* -----------------------------------------------------------------------------------------------------------------*/
  // [Fix] 로그인 회원 태그 생성
  createLoginUserElement: function (result) {
    return `
      <div class="user-info">
        <div class="user-member">
          <p class="text">${result.userName}<span class="small">님</span></p>
        </div>
        <div class="user-logout">
          <a class="link js-user-logout" href="#">로그아웃하기</a>
        </div>
      </div>
      <div class="user-profile">
        <img class="image" src="/image/icon/icon_fill_user_on.svg" alt="회원 사진">
      </div>
    `;
  },
  // [Fix] 로그인 회원 예매 개수 태그 생성
  createLoginUserReservationCountElement: function (result) {
    return `
      <div class="user-ticket-list">
        <ul class="group-list">
          <li class="group-list-item">
            <span class="text-label">전체 예매</span>
            <span class="text">${pageSub.reduceReservationCount(result)}</span>
          </li>
          <li class="group-list-item">
            <span class="text-label">이용 예정</span>
            <span class="text">${pageSub.filterReservationCount(result, '이용 예정')}</span>
          </li>
          <li class="group-list-item">
            <span class="text-label">이용 완료</span>
            <span class="text">${pageSub.filterReservationCount(result, '이용 완료')}</span>
          </li>
          <li class="group-list-item">
            <span class="text-label">예매 취소</span>
            <span class="text">${pageSub.filterReservationCount(result, '예매 취소')}</span>
          </li>
        </ul>
      </div>
    `;
  },
  // [Fix] 로그인 회원 카테고리 생성
  createLoginUserCategoryElement: function () {
    return `
      <div class="user-category-list">
        <p class="group-list-label">마이 메뉴</p>
        <ul class="group-list">
          <li class="group-list-item">
            <a class="group-item-link" href="/reservation/list">나의 티켓</a>
          </li>
          <li class="group-list-item">
            <a class="group-item-link" href="/review/list">나의 리뷰</a>
          </li>
          <li class="group-list-item">
            <a class="group-item-link" href="/product/like/list">관심 상품</a>
          </li>
        </ul>
      </div>
    `;
  },
  // [Fix] 비로그인 회원 태그 생성
  createLogoutUserElement: function () {
    return `
      <div class="user-info">
        <div class="user-login">
          <a class="link" href="/user/login">로그인하기</a>
        </div>
        <div class="user-guest">
          <span class="text">로그인 후 티켓을 예약할 수 있습니다.</span>
        </div>
      </div>
      <div class="user-profile">
        <img class="image" src="/image/icon/icon_fill_user.svg" alt="회원 사진">
      </div>
    `;
  },
  // [Fix] 비로그인 회원 예매 개수 태그 생성
  createLogoutUserReservationCountElement: function () {
    return `
      <div class="user-ticket-list">
        <ul class="group-list">
          <li class="group-list-item-empty">
            <span class="text">로그인 후 예매 내역을 확인하세요.</span>
          </li>
        </ul>
      </div>
    `;
  },
}
/* -------------------------------------------------------------------------------------------------------------------*/