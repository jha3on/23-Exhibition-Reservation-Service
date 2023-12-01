const utils = {
  // [Fix] 가격 단위 구분자 추가
  setPriceFormat: function (value) {
    value = String(value);
    return value.replace(/(\d)(?=(?:\d{3})+(?!\d))/g, '$1,');
  },
  // [Fix] 가격 단위 구분자 삭제
  unsetPriceFormat: function (value) {
    value = String(value);
    return value.replace(/\D+/g, '');
  },

  setPromotionTextColor: function (target, image) {
    const colors = new FastAverageColor();
    console.log(target)
    colors.getColorAsync(image)
      .then((value) => {
        target.style.color = value.isDark ? '#ffffff' : '#000000';
      })
      .catch(e => {
        console.log(e);
      });
  },

  // getPromotionTextColor: function (image) {
  //   const colors = new FastAverageColor();
  //
  //   return colors.getColorAsync(image).then((value) => {
  //     return value.isDark ? '#ffffff' : '#000000';
  //   });
  // },

  getPromotionTextColor: async function (image) {
    const colors = new FastAverageColor();

    return await colors.getColorAsync(image).then((color) => {
      // return color.isDark ? '#ffffff' : '#000000';
      return color;
    });
  },

  // [Fix] 날짜 포맷
  setDateFormat: function (value) {
    const date = new Date(value);
    const dateYear = date.getFullYear();
    const dateMonth = (1 + date.getMonth()).toString().padStart(2, '0');
    const dateDay = date.getDate().toString().padStart(2, '0'); // or date.getDate() >= 10 ? date.getDate() : `0${date.getDate()}`;

    return `${dateYear}.${dateMonth}.${dateDay}`;
  },

  // [Fix] 날짜/시간 포맷
  setDateTimeFormat: function (value) {
    const date = new Date(value);
    const dateYear = date.getFullYear();
    const dateMonth = (1 + date.getMonth()).toString().padStart(2, '0');
    const dateDay = date.getDate().toString().padStart(2, '0'); // or date.getDate() >= 10 ? date.getDate() : `0${date.getDate()}`;
    const dateHour = date.getHours().toString().padStart(2, '0');
    const dateMinute = date.getMinutes().toString().padStart(2, '0');
    const dateSecond = date.getSeconds().toString().padStart(2, '0');

    return `${dateYear}.${dateMonth}.${dateDay} ${dateHour}:${dateMinute}:${dateSecond}`;
  },

  // [Fix] 날짜 기간 포맷
  setDatePeriodFormat: function (fromValue, toValue) {
    return `${utils.setDateFormat(fromValue)} ~ ${utils.setDateFormat(toValue)}`;
  },


  // [Fix] 빈 문자열 검사
  validateTextEmpty: function (value) {
    return (!(value === '' || value.length === 0));
  },

  // [Fix] 회원 이름 정규식 검사
  validateUserName: function (value) {
    return /^[가-힣]{2,5}$/.test(value.trim());
  },
  // [Fix] 회원 이메일 정규식 검사
  validateUserEmail: function (value) {
    return /^[a-zA-Z0-9+-_.]+@naver.com|@kakao.com|@gmail.com/.test(value.trim());
  },
  // [Fix] 회원 연락처 정규식 검사
  validateUserContact: function (value) {
    return /^01([0|1])-?(\d{3,4})-?(\d{4})$/.test(value.trim());
  },
  // [Fix] 회원 비밀번호 정규식 검사
  validateUserPassword: function (value) {
    return /^(?=.*[a-zA-Z])(?=.*[0-9]).{8,16}$/.test(value.trim());
  },

  // [Fix] 상품 ID 확인
  getProductId: function () {
    return location.pathname.replace(/[^0-9]/g, '');
  },
}