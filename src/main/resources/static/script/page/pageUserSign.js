/* [Fix] */

/* -------------------------------------------------------------------------------------------------------------------*/
document.addEventListener('DOMContentLoaded', function (event) {
  // [Fix] 페이지 이름 세팅
  pageSub.setPageName();
  // [Fix] 회원 가입 단계 세팅
  pageSub.setUserSignStep();
  // [Fix] 회원 이름 입력 이벤트 처리
  document.querySelector('.js-user-name')?.addEventListener('change', function (event) {
    pageSub.checkUserSignName();
  });
  // [Fix] 회원 이메일 입력 이벤트 처리
  document.querySelector('.js-user-email')?.addEventListener('change', function (event) {
    pageSub.checkUserSignEmail().then();
  });
  // [Fix] 회원 이메일 선택 이벤트 처리
  document.querySelector('.js-user-email-option')?.addEventListener('change', function (event) {
    pageSub.checkUserSignEmail().then();
  });
  // [Fix] 회원 연락처 입력 이벤트 처리
  document.querySelector('.js-user-contact-1')?.addEventListener('change', function (event) {
    pageSub.checkUserSignContact().then();
  });
  // [Fix] 회원 연락처 입력 이벤트 처리
  document.querySelector('.js-user-contact-2')?.addEventListener('change', function (event) {
    pageSub.checkUserSignContact().then();
  });
  // [Fix] 회원 연락처 선택 이벤트 처리
  document.querySelector('.js-user-contact-option')?.addEventListener('change', function (event) {
    pageSub.checkUserSignContact().then();
  });
  // [Fix] 회원 비밀번호 입력 이벤트 처리
  document.querySelector('.js-user-password')?.addEventListener('change', function (event) {
    pageSub.checkUserSignPassword();
  });
  // [Fix] 회원 가입 이벤트 처리
  document.querySelector('.js-user-sign')?.addEventListener('click', function (event) {
    this.setAttribute('disabled', 'disabled'); // 중복 클릭 방지
    pageSub.setUserSignStep();
  });
});
/* -------------------------------------------------------------------------------------------------------------------*/
const pageSub = {
  // [Fix] 회원 가입 단계 변수
  userSignStep: {
    name: false, // 이름, 이메일, 연락처, 비밀번호 입력 단계 (정상 입력 시, true 값 세팅)
    email: false,
    contact: false,
    password: false,
    nameState: false, // 이름, 이메일, 연락처, 비밀번호 입력 상태 (입력 시, true 값 세팅)
    emailState: false,
    contactState: false,
    passwordState: false,
  },
  // [Fix] 페이지 이름 세팅
  setPageName: function () {
    page.setPageTextElement('.js-header-name', '멤버십 가입');
  },
  // [Fix] 페이지 모달 세팅
  setPageModal: function (title, message) {
    page.setPageModal(document.querySelector('.js-modal'));
    page.setPageModalName(document.querySelector('.js-modal-name'), name);
    page.setPageModalMessage(document.querySelector('.js-modal-message'), message);
  },
  // [Fix] 회원 가입 단계 세팅
  setUserSignStep: function () {
    const userSign = document.querySelector('.js-user-sign');
    const userSignName = document.querySelector('.js-user-name-field');
    const userSignEmail = document.querySelector('.js-user-email-field');
    const userSignContact = document.querySelector('.js-user-contact-field');
    const userSignPassword = document.querySelector('.js-user-password-field');
    console.log(pageSub.userSignStep);

    if (pageSub.userSignStep.name === false) {
      userSign.disabled = true;
      pageSub.setUserSignField('다음 단계', userSignName);
      if (pageSub.userSignStep.nameState === true) {
        pageSub.setPageModal('알림', '이름을 확인해주세요.');
      }
      return;
    }

    if (pageSub.userSignStep.email === false) {
      userSign.disabled = true;
      pageSub.setUserSignField('다음 단계', userSignEmail);
      if (pageSub.userSignStep.emailState === true) {
        pageSub.setPageModal('알림', '이메일을 확인해주세요.');
      }
      return;
    }

    if (pageSub.userSignStep.contact === false) {
      userSign.disabled = true;
      pageSub.setUserSignField('다음 단계', userSignContact);
      if (pageSub.userSignStep.contactState === true) {
        pageSub.setPageModal('알림', '연락처를 확인해주세요.');
      }
      return;
    }

    if (pageSub.userSignStep.password === false) {
      userSign.disabled = true;
      pageSub.setUserSignField('멤버십 가입', userSignPassword);
      if (pageSub.userSignStep.passwordState === true) {
        pageSub.setPageModal('알림', '비밀번호를 확인해주세요.');
      }
      return;
    }

    pageSub.userSignHandler().then(() => setTimeout(() => location.replace('/user/login'), 3000));
  },
  /* -----------------------------------------------------------------------------------------------------------------*/
  // [Fix] 회원 가입 핸들러
  userSignHandler: async function () {
    const { code, message } = await pageApi.userSignByPost(
      document.querySelector('.js-user-name').value,
      document.querySelector('.js-user-email').value,
      document.querySelector('.js-user-email-option').value,
      document.querySelector('.js-user-contact-1').value,
      document.querySelector('.js-user-contact-2').value,
      document.querySelector('.js-user-contact-option').value,
      document.querySelector('.js-user-password').value
    );
    if (code !== '+100') {
      pageSub.setPageModal('알림', message);
    } else {
      pageSub.setPageModal('알림', '회원 가입이 완료되었습니다.');
    }
  },
  /* -----------------------------------------------------------------------------------------------------------------*/
  // [Fix] 회원 가입 단계 상태 세팅
  setUserSignState: function (target, message, disabled) {
    const userSign = document.querySelector('.js-user-sign');
    userSign.disabled = disabled;
    target.innerText = message;
    target.style.color = disabled ? '#ff3399' : '#212121';
  },
  // [Fix] 회원 가입 단계 영역 세팅
  setUserSignField: function (message, ...elements) {
    const userSign = document.querySelector('.js-user-sign');
    userSign.innerText = message;
    elements.map((element) => {
      element.style.display = 'flex';
    });
  },
  // [Fix] 회원 가입 이름 확인
  checkUserSignName: function () {
    const userName = document.querySelector('.js-user-name');
    const userNameMessage = document.querySelector('.js-user-name-message');

    pageSub.userSignStep.nameState = true;
    if (utils.validateUserName(userName.value) === false) {
      pageSub.setUserSignState(userNameMessage, '이름을 확인해주세요.', true);
      pageSub.userSignStep.name = false;
    } else {
      pageSub.setUserSignState(userNameMessage, '사용할 수 있는 이름입니다.', false);
      pageSub.userSignStep.name = true;
    }
  },
  // [Fix] 회원 가입 이메일 확인
  checkUserSignEmail: async function () {
    const userEmail = document.querySelector('.js-user-email');
    const userEmailOption = document.querySelector('.js-user-email-option');
    const userEmailMessage = document.querySelector('.js-user-email-message');
    const { code, message, result } = await pageApi.userSignEmailByGet(userEmail.value, userEmailOption.value);
    pageSub.userSignStep.emailState = true;

    if (code !== '+100') {
      pageSub.setPageModal('알림', message);
    }
    if (userEmail.value.length === 0) {
      pageSub.setUserSignState(userEmailMessage, '이메일을 입력해주세요.', true);
      pageSub.userSignStep.email = false;
      return;
    }
    if (userEmailOption.value.length === 0 || userEmailOption.value === '선택') {
      pageSub.setUserSignState(userEmailMessage, '이메일 선택을 확인해주세요.', true);
      pageSub.userSignStep.email = false;
      return;
    }
    if (utils.validateUserEmail(`${userEmail.value}@${userEmailOption.value}`) === false) {
      pageSub.setUserSignState(userEmailMessage, '이메일 형식을 확인해주세요.', true);
      pageSub.userSignStep.email = false;
      return;
    }
    if (result.userResult === true) {
      pageSub.setUserSignState(userEmailMessage, '이미 사용 중인 이메일입니다.', true);
      pageSub.userSignStep.email = false;
      return;
    }
    pageSub.setUserSignState(userEmailMessage, '알맞은 이메일입니다.', false);
    pageSub.userSignStep.email = true;
  },
  // [Fix] 회원 가입 연락처 확인
  checkUserSignContact: async function () {
    const userContact1 = document.querySelector('.js-user-contact-1');
    const userContact2 = document.querySelector('.js-user-contact-2');
    const userContactOption = document.querySelector('.js-user-contact-option');
    const userContactMessage = document.querySelector('.js-user-contact-message');
    const { code, message, result } = await pageApi.userSignContactByGet(userContact1.value, userContact2.value, userContactOption.value);
    pageSub.userSignStep.contactState = true;

    if (code !== '+100') {
      pageSub.setPageModal('알림', message);
    }
    if (userContactOption.value === '선택') {
      pageSub.setUserSignState(userContactMessage, '연락처 선택을 확인해주세요.', true);
      pageSub.userSignStep.contact = false;
      return;
    }
    if (userContact1.value.length === 0 || userContact2.value.length === 0) {
      pageSub.setUserSignState(userContactMessage, '연락처를 확인해주세요.', true);
      pageSub.userSignStep.contact = false;
      return;
    }
    if (utils.validateUserContact(`${userContactOption.value}-${userContact1.value}-${userContact2.value}`) === false) {
      pageSub.setUserSignState(userContactMessage, '연락처 형식을 확인해주세요.', true);
      pageSub.userSignStep.contact = false;
      return;
    }
    if (result.userResult === true) {
      pageSub.setUserSignState(userContactMessage, '이미 사용 중인 연락처입니다.', true);
      pageSub.userSignStep.contact = false;
      return;
    }
    pageSub.setUserSignState(userContactMessage, '알맞은 연락처입니다.', false);
    pageSub.userSignStep.contact = true;
  },
  // [Fix] 회원 가입 비밀번호 확인
  checkUserSignPassword: function () {
    const userPassword = document.querySelector('.js-user-password');
    const userPasswordMessage = document.querySelector('.js-user-password-message');
    pageSub.userSignStep.passwordState = true;

    if (utils.validateUserPassword(userPassword.value) === false) {
      pageSub.setUserSignState(userPasswordMessage, '비밀번호를 확인해주세요.', true);
      pageSub.userSignStep.password = false;
      return;
    }
    pageSub.setUserSignState(userPasswordMessage, '알맞은 비밀번호입니다.', false);
    pageSub.userSignStep.password = true;
  },
  /* -----------------------------------------------------------------------------------------------------------------*/
};
/* -------------------------------------------------------------------------------------------------------------------*/