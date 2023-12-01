/* 페이지 API 스크립트 -----------------------------------------------------------------------------------------------*/
const pageApi = {
  // [Fix] 회원 정보 조회
  userByGet: async function () {
    try {
      const response = await axios.get(`/api/users/session`);
      return pageApi.toObject(response.data);
    } catch (error) {
      return pageApi.toObject(error.response.data);
    }
  },
  // [Fix] 회원 가입 이메일 조회
  userSignEmailByGet: async function (userEmail, userEmailOption) {
    try {
      const response = await axios.get(`/api/users/search`, {
        params: {
          type: `email`,
          value: `${userEmail}@${userEmailOption}`,
        },
      });
      return pageApi.toObject(response.data);
    } catch (error) {
      return pageApi.toObject(error.response.data);
    }
  },
  // [Fix] 회원 가입 연락처 조회
  userSignContactByGet: async function (userContact1, userContact2, userContactOption) {
    try {
      const response = await axios.get(`/api/users/search`, {
        params: {
          type: `contact`,
          value: `${userContactOption}-${userContact1}-${userContact2}`,
        },
      });
      return pageApi.toObject(response.data);
    } catch (error) {
      return pageApi.toObject(error.response.data);
    }
  },
  // [Fix] 회원 가입
  userSignByPost: async function (userName, userEmail, userEmailOption, userContact1, userContact2, userContactOption, userPassword) {
    try {
      const requestBody = {
        userName: userName,
        userEmail: `${userEmail}@${userEmailOption}`,
        userContact: `${userContactOption}-${userContact1}-${userContact2}`,
        userPassword: userPassword,
      };
      const response = await axios.post(`/api/users/sign`, requestBody);
      return pageApi.toObject(response.data);
    } catch (error) {
      return pageApi.toObject(error.response.data);
    }
  },
  // [Fix] 회원 로그인
  userLoginByPost: async function (userEmail, userEmailOption, userPassword) {
    try {
      const requestBody = {
        userEmail: `${userEmail}@${userEmailOption}`,
        userPassword: userPassword,
      };
      const response = await axios.post(`/api/users/login`, requestBody);
      return pageApi.toObject(response.data);
    } catch (error) {
      return pageApi.toObject(error.response.data);
    }
  },
  // [Fix] 회원 로그아웃
  userLogoutByPost: async function () {
    try {
      const response = await axios.post(`/api/users/logout`);
      return pageApi.toObject(response.data);
    } catch (error) {
      return pageApi.toObject(error.response.data);
    }
  },
  // [Fix] 상품 조회
  productByGet: async function (productId) {
    try {
      const response = await axios.get(`/api/products/${productId}`);
      return pageApi.toObject(response.data);
    } catch (error) {
      return pageApi.toObject(error.response.data);
    }
  },
  // [Fix] 상품 목록 조회
  productListByGet: async function (placeGroup, placeSubgroup, productGroup) {
    try {
      const response = await axios.get(`/api/products/filter?type1=${placeGroup}&type2=${placeSubgroup}&type3=${productGroup}`);
      return pageApi.toObject(response.data);
    } catch (error) {
      return pageApi.toObject(error.response.data);
    }
  },
  // [Fix] 관심 상품 처리
  productLikeByPost: async function (productId) {
    try {
      const response = await axios.post(`/api/products/${productId}/like`);
      return pageApi.toObject(response.data);
    } catch (error) {
      return pageApi.toObject(error.response.data);
    }
  },
  // [Fix] 상품 장소 조회
  productPlaceByGet: async function (productId) {
    try {
      const response = await axios.get(`/api/products/${productId}/place`);
      return pageApi.toObject(response.data);
    } catch (error) {
      return pageApi.toObject(error.response.data);
    }
  },
  // [Fix] 상품 옵션 조회
  productOptionListByGet: async function (productId) {
    try {
      const response = await axios.get(`/api/products/${productId}/options`);
      return pageApi.toObject(response.data);
    } catch (error) {
      return pageApi.toObject(error.response.data);
    }
  },
  // [Fix] 리뷰 목록 조회
  productReviewListByGet: async function (productId) {
    try {
      const response = await axios.get(`/api/products/${productId}/reviews`);
      return pageApi.toObject(response.data);
    } catch (error) {
      return pageApi.toObject(error.response.data);
    }
  },
  // [Fix] 리뷰 목록 점수 조회
  productReviewListScoreByGet: async function (productId) {
    try {
      const response = await axios.get(`/api/products/${productId}/score`);
      return pageApi.toObject(response.data);
    } catch (error) {
      return pageApi.toObject(error.response.data);
    }
  },
  // [Fix] 프로모션 목록 조회
  promotionListByGet: async function (promotionGroup) {
    try {
      const response = await axios.get(`/api/promotions/filter?type1=${promotionGroup}`);
      return pageApi.toObject(response.data);
    } catch (error) {
      return pageApi.toObject(error.response.data);
    }
  },
  // [Fix] 예매 생성
  reservationCreateByPost: async function (productId, reservationOptions) {
    try {
      const requestBody = {
        reservationOptions: reservationOptions,
      }
      const response = await axios.post(`/api/reservations/${productId}`, requestBody);
      return pageApi.toObject(response.data);
    } catch (error) {
      return pageApi.toObject(error.response.data);
    }
  },
  // [Fix] 예매 목록 조회
  reservationListByGet: async function (reservationStatus) {
    try {
      const response = await axios.get(`/api/reservations/filter?status=${reservationStatus}`);
      return pageApi.toObject(response.data);
    } catch (error) {
      return pageApi.toObject(error.response.data);
    }
  },
  // [Fix] 예매 개수 목록 조회
  reservationCountListByGet: async function () {
    try {
      const response = await axios.get(`/api/reservations/count`);
      return pageApi.toObject(response.data);
    } catch (error) {
      return pageApi.toObject(error.response.data);
    }
  },
  // 예매 상태 수정
  reservationStatusByPut: async function (reservationId, reservationStatus) {
    try {
      const requestBody = {
        reservationStatusType: reservationStatus,
      }
      const response = await axios.put(`/api/reservations/${reservationId}/status`, requestBody);
      return pageApi.toObject(response.data);
    } catch (error) {
      return pageApi.toObject(error.response.data);
    }
  },
  // [Fix] 리뷰 생성
  reviewCreateByPost: async function (productId, reviewScore, reviewContent) {
    try {
      const requestBody = {
        reviewScore: reviewScore,
        reviewContent: reviewContent
      }
      const response = await axios.post(`/api/products/${productId}/reviews`, requestBody);
      return pageApi.toObject(response.data);
    } catch (error) {
      return pageApi.toObject(error.response.data);
    }
  },
  // [Fix] 리뷰 목록 조회
  reviewListByGet: async function () {
    try {
      const response = await axios.get(`/api/reviews`);
      return pageApi.toObject(response.data);
    } catch (error) {
      return pageApi.toObject(error.response.data);
    }
  },
  // [Fix] 응답 결과 처리
  toObject: function (data) {
    console.log(data);
    return {
      code: data.code,
      message: data.message,
      result: data.result,
    };
  },
};
/* 페이지 API 스크립트 -----------------------------------------------------------------------------------------------*/