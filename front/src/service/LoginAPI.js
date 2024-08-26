const getUserInfo = (userId, userPw) => {
    const reqData = {
        'user_id': userId,
        'user_pw': userPw,
    }
    return {
        'data': {
            'user_id': reqData.userId,
            'user_token': 'user_test_token',
            'user_role': 'ADM'
        }
    }
}
export default {
    async doLogin(userId, userPw) {
        try {
            const getUserInfoPromise = getUserInfo(userId, userPw);
            const [userInfoResponse] = await Promise.all([getUserInfoPromise])
            if (userInfoResponse === 0) {
                return 'notFound'
            } else {
                localStorage.setItem('user_token', userInfoResponse.data.user_role)
                localStorage.setItem('user_role', userInfoResponse.data.user_role)
                return userInfoResponse;
            }
        } catch (err) {
            console.log(err)
        }
    }
}