/*
 路由
 */
let api = {

    /**
     * 头像列表 -- 本地json文件加载
     */
    getAvatarList() {
        return '/avatar/avatar.json'
    },

    /**
     * 用户登录
     */
    login() {
        return '/user/login'
    },

    /**
     *用户注册
     */
    regster() {
        return '/user/register'
    },

    /**
     * 跳转到 index.html
     * @returns {string}
     */
    toIndex() {
        return '/'
    }

}
