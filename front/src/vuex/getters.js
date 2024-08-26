export default{
    getUserId: state=>state.userId,
    getErrorState: state => state.errorState,
    getIsAuth: state => state.IsAuth,
    loggedIn(state) {
        return !!state.user;
    },
}