/* -------------------------------------------------------------------------------------------------------------------*/
document.addEventListener('DOMContentLoaded', function (event) {
  // [Fix] 마우스 이벤트 처리
  document.addEventListener('mouseup', function (event) {
    // [Fix] 페이지 모달 닫기 이벤트 처리
    if (event.target === document.querySelector('.js-modal-overlay')) {
      page.unsetPageModal(document.querySelector('.js-modal'));
    }
    // [Fix] 페이지 하단 시트 닫기 이벤트 처리
    if (event.target === document.querySelector('.js-sheet-overlay')) {
      page.unsetPageBottomSheet(document.querySelector('.js-sheet'));
    }
  });
  // [Fix] 페이지 모달 닫기 이벤트 처리
  document.querySelector('.js-modal-close')?.addEventListener('click', function (event) {
    page.unsetPageModal(document.querySelector('.js-modal'));
  });
  // [Fix] 페이지 하단 시트 닫기 이벤트 처리
  document.querySelector('.js-sheet-close')?.addEventListener('click', function (event) {
    page.unsetPageBottomSheet(document.querySelector('.js-sheet'));
  });
  // [Fix] 페이지 뒤로 가기 이벤트 처리
  document.querySelector('.js-header-path')?.addEventListener('click', function (event) {
    history.back();
  });
});
/* -------------------------------------------------------------------------------------------------------------------*/
const page = {
  // [Fix] 페이지 텍스트 세팅
  setPageTextElement: function (selector, text) {
    const target = document.querySelector(selector);
    target.innerText = text;
  },
  // [Fix] 페이지 슬라이드 세팅
  setPageSlideElement: function (slider, paginator) {
    return new Swiper(slider, {
      direction: 'horizontal',
      loop: true,
      autoplay: {
        delay: 3000,
        disableOnInteraction: false,
      },
      pagination: {
        el: paginator,
        type: 'bullets',
        clickable: false,
      },
      slidesPerView: 'auto',
      observer: true,
      observerParents: true,
      updateOnImageReady: true,
    });
  },
  // [Fix] 페이지 네비게이션 메뉴 세팅
  setPageNavigatorMenu: function () {
    const path = location.pathname;
    const pageNavigatorHome = document.querySelector('.js-navigator-home');
    const pageNavigatorUser = document.querySelector('.js-navigator-user');
    const pageNavigatorTicket = document.querySelector('.js-navigator-ticket');

    if (path === '/') {
      pageNavigatorHome.querySelector('img').src = '/image/icon/icon_fill_home_on.svg';
      pageNavigatorHome.querySelector('span').style.color = '#212121';
    }
    if (path.includes('/user') || path.includes('/reservation/list')) {
      pageNavigatorUser.querySelector('img').src = '/image/icon/icon_fill_user_on.svg';
      pageNavigatorUser.querySelector('span').style.color = '#212121';
    }
    if (path.includes('/product')) {
      pageNavigatorTicket.querySelector('img').src = '/image/icon/icon_fill_ticket_on.svg';
      pageNavigatorTicket.querySelector('span').style.color = '#212121';
    }
  },
  // [Fix] 페이지 모달 열기
  setPageModal: function (element) {
    element.classList.toggle('modal-staged'); // or add()
    element.classList.toggle('modal-unstaged'); // or remove()
  },
  // [Fix] 페이지 모달 닫기
  unsetPageModal: function (element) {
    element.classList.toggle('modal-staged'); // or remove()
    element.addEventListener('transitionend', function (e) {
      this.classList.toggle('modal-unstaged'); // or add()
      this.removeEventListener('transitionend', arguments.callee);
    });
  },
  // [Fix] 페이지 모달 이름 세팅
  setPageModalName: function (element, name) {
    element.innerText = name;
  },
  // [Fix] 페이지 모달 메시지 세팅
  setPageModalMessage: function (element, message) {
    element.innerText = message;
  },
  // [Fix] 페이지 하단 시트 열기
  setPageBottomSheet: function (element) {
    element.classList.toggle('sheet-staged'); // or add()
    element.classList.toggle('sheet-unstaged'); // or remove()
  },
  // [Fix] 페이지 하단 시트 닫기
  unsetPageBottomSheet: function (element) {
    element.classList.toggle('sheet-staged'); // or remove()
    element.addEventListener('transitionend', function (e) {
      this.classList.toggle('sheet-unstaged'); // or add()
      this.removeEventListener('transitionend', arguments.callee);
    });
  },
};
/* -------------------------------------------------------------------------------------------------------------------*/