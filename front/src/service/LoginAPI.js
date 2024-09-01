/* service/loginAPI.js */
import axios from 'axios'

const getUserInfo = (userId, userPw) => {
    const reqData = {
        'user_id': userId,
        'user_pw': userPw,
    }
    let serverUrl = '//localhost:8000'

    return axios.post(serverUrl + '/user/login', reqData,{
        header:{
            'Content-Type' : 'application/json'
        }
    })
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
            console.error(err)
        }
    }
}