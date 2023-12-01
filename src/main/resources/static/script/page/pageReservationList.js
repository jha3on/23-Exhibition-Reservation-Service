/* -------------------------------------------------------------------------------------------------------------------*/
document.addEventListener('DOMContentLoaded', function (event) {
  // [Fix] 페이지 메뉴 세팅
  page.setPageNavigatorMenu();
  // [Fix] 페이지 이름 세팅
  pageSub.setPageName();
  pageSub.setReservationList().then();
  pageSub.setReservationCountList().then();

  // [Fix] 예매 탭 선택
  [...document.querySelectorAll('.js-reservation-tab')].forEach(function (element) {
    element.addEventListener('click', function (event) {
      pageSub.setReservationTab(event.target);
    });
  });
  // [Fix] 전체 예매 탭 선택 이벤트
  document.querySelector('.js-reservation-all-tab')?.addEventListener('click', function (event) {
    pageSub.setReservationList().then();
  });
  // [Fix] 이용 예정 탭 선택 이벤트
  document.querySelector('.js-reservation-ready-tab')?.addEventListener('click', function (event) {
    pageSub.setReservationList().then();
  });
  // [Fix] 이용 완료 탭 선택 이벤트
  document.querySelector('.js-reservation-complete-tab')?.addEventListener('click', function (event) {
    pageSub.setReservationList().then();
  });
  // [Fix] 예매 취소 탭 선택 이벤트
  document.querySelector('.js-reservation-cancel-tab')?.addEventListener('click', function (event) {
    pageSub.setReservationList().then();
  });
  // [Fix] 마우스 클릭 이벤트 처리
  document.addEventListener('click', function (event) {
    // 예매 이용 취소 이벤트 처리 (e.target === doc.query('xxx') X)
    if (event.target.classList.contains('js-reservation-cancel-action')) {
      pageSub.handleReservationCancel(event.target).then(() => setTimeout(() => location.replace('/reservation/list'), 3000));
    }
    // 예매 이용 완료 이벤트 처리
    if (event.target.classList.contains('js-reservation-complete-action')) {
      pageSub.handleReservationComplete(event.target).then(() => setTimeout(() => location.replace('/reservation/list'), 3000));
    }
  });
});
/* -------------------------------------------------------------------------------------------------------------------*/
const pageSub = {
  // [Fix] 페이지 이름 세팅
  setPageName: function () {
    page.setPageTextElement('.js-header-name', '예매 내역');
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

  // [Fix] 예매 탭 세팅
  setReservationTab: function (target) {
    [...document.querySelectorAll('.js-reservation-tab')].forEach(function (element) {
      if (target === element) {
        target.setAttribute('aria-selected', 'true');
      } else element.setAttribute('aria-selected', 'false');
    });
  },

  // [Fix] 예매 목록 세팅
  setReservationList: async function () {
    const statusType = document.querySelector('.js-reservation-tab[aria-selected=\'true\']').getAttribute('data-symbol');
    const { code, result } = await pageApi.reservationListByGet(statusType);
    const target = document.querySelector('.js-reservation-list');

    if (code !== '+100') {
      target.replaceChildren();
      target.insertAdjacentHTML('afterbegin', pageSub.createReservationListEmptyElement('로그인 후 이용해주세요.'));
    }
    if (code === '+100' && result.length === 0) {
      target.replaceChildren();
      target.insertAdjacentHTML('afterbegin', pageSub.createReservationListEmptyElement('예매 내역이 없습니다.'));
    }
    if (code === '+100' && result.length !== 0) {
      target.replaceChildren();
      result.forEach((resultItem, i) => {
        target.insertAdjacentHTML('afterbegin', pageSub.createReservationListElement(resultItem));
      });
    }
  },

  setReservationCountList: async function () {
    const { code, result } = await pageApi.reservationCountListByGet();

    if (code === '+100') {
      document.querySelector('.js-reservation-all').innerText = pageSub.filterAllCount(result, '전체 예매');
      document.querySelector('.js-reservation-ready').innerText = pageSub.filterCount(result, '이용 예정');
      document.querySelector('.js-reservation-complete').innerText = pageSub.filterCount(result, '이용 완료');
      document.querySelector('.js-reservation-cancel').innerText = pageSub.filterCount(result, '예매 취소');
    }
  },

  /* -----------------------------------------------------------------------------------------------------------------*/
  filterAllCount: function (reservations) {
    return reservations.reduce((prev, next) => {
      return prev + next.reservationCount;
    }, 0);
  },
  // [Fix] 티켓 총 매수를 필터링한다.
  filterCount: function (reservations, reservationStatus) {
    const result = reservations.filter((reservation) => {
      if (reservation.reservationStatusType === reservationStatus) {
        return reservation;
      }
    });

    return result[0].reservationCount;
  },
  // [Fix] 티켓 총 매수를 계산한다.
  reduceQuantity: function (reservationOptions) {
    const result = reservationOptions.reduce((sum, reservationOption) => {
      return sum + reservationOption.reservationOptionQuantity;
    }, 0);

    return utils.setPriceFormat(result);
  },
  // [Fix] 티켓 총 금액을 계산한다.
  reducePrice: function (reservationOptions) {
    const result = reservationOptions.reduce((sum, reservationOption) => {
      return sum + (reservationOption.reservationOptionPrice * reservationOption.reservationOptionQuantity);
    }, 0);

    return utils.setPriceFormat(result);
  },
  handleReservationComplete: async function (target) {
    const reservationId = target.closest('.js-reservation-list-item').getAttribute('data-key');
    const { code } = await pageApi.reservationStatusByPut(reservationId, 'completed');
    if (code === '+100') {
      pageSub.setPageModal('알림', '이용 완료되었습니다.');
    } else {
      pageSub.setPageModal('알림', '예매 상태를 수정할 수 없습니다.');
    }
  },
  handleReservationCancel: async function (target) {
    const reservationId = target.closest('.js-reservation-list-item').getAttribute('data-key');
    const { code } = await pageApi.reservationStatusByPut(reservationId, 'canceled');
    if (code === '+100') {
      pageSub.setPageModal('알림', '예매 취소되었습니다.');
    } else {
      pageSub.setPageModal('알림', '예매 상태를 수정할 수 없습니다.');
    }
  },
  /* -----------------------------------------------------------------------------------------------------------------*/
  // [Fix]
  createReservationListElement: function (resultItem) {
    return `
      <li class="grid-list-item js-reservation-list-item" data-key="${resultItem.reservationId}">
        <div class="grid-item-card">
          <div class="reservation-info-tag">
            <div class="reservation-info-code">
              <span class="text-label">예매 번호:</span>
              <span class="text js-reservation-code">${resultItem.reservationId}</span>
            </div>
            <div class="reservation-info-state">
              <span class="text-label">사용 여부:</span>
              <span class="text js-reservation-state">${resultItem.reservationStatusType}</span>
            </div>
          </div>
          <div class="reservation-info-detail">
            <div class="reservation-info-name">
              <span class="text">${resultItem.productName}</span>
            </div>
            <div class="reservation-info-guide">
              <ul class="reservation-info-guide-list">
                <li class="reservation-info-guide-list-item">
                  <span class="text-label">티켓 매수</span>
                  <span class="text">${pageSub.reduceQuantity(resultItem.reservationOptions)}매</span>
                </li>
                <li class="reservation-info-guide-list-item">
                  <span class="text-label">티켓 금액</span>
                  <span class="text">${pageSub.reducePrice(resultItem.reservationOptions)}원</span>
                </li>
                <li class="reservation-info-guide-list-item">
                  <span class="text-label">상세 내역</span>
                  <ul class="">
                    ${resultItem.reservationOptions.map((reservationOption, i) => `
                      <li class="" style="display: flex; gap: 4px;">
                        <span class="text">${reservationOption.reservationOptionName}</span>
                        <span class="text">${reservationOption.reservationOptionQuantity}매</span>
                        <span class="text">${utils.setPriceFormat(reservationOption.reservationOptionPrice) + `원`}</span>
                      </li>
                    `).join('')}
                  </ul>
                </li>
                <li class="reservation-info-guide-list-item">
                  <span class="text-label">사용 기간</span>
                  <span class="text">${utils.setDateFormat(resultItem.productDateFrom)} ~ ${utils.setDateFormat(resultItem.productDateTo)}</span>
                </li>
              </ul>
            </div>
            <div class="reservation-info-action">
              <button class="button js-reservation-complete-action" type="button">이용 완료</button>
              <button class="button js-reservation-cancel-action" type="button">예매 취소</button>
            </div>
          </div>
        </div>
      </li>
    `;
  },
  // [Fix]
  createReservationListEmptyElement: function (message) {
    return `
      <li class="grid-list-item-empty">
        <img class="image" src="../../image/icon/icon_stroke_data_empty.svg" alt="">
        <span class="text">${message}</span>
      </li>
    `;
  },
}
/* -------------------------------------------------------------------------------------------------------------------*/