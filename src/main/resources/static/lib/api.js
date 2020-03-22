/*
 路由
 */
let api = {

    user: {
        login: '/user/login',    //用户登录
        register: '/user/register',   //用户注册
        getUser: '/user/get',   //获取当前登录用户信息
        getUserById(id){
            return '/user/' + id;   //根据ID 获取用户信息
        }

    },

    follow: {
        getFollow(fId){
            return '/follow/' + fId; //获取用户关注状态
        },
        follow(fId){
            return '/follow/' + fId;
        },
        unFollow(fId){
            return '/follow/' + fId;
        },

    },

    chat: {
        /* 获取当前用户与指定用户聊天记录Id */
        getMessageByToId(toId){
            return '/chat/self/' + toId;
        },
        sendMessateByUser: '/chat/push',

        websocket(id){
            return 'ws://localhost:8887/chat/' + id;
        },

    },



    /**
     * 头像列表 -- 本地json文件加载
     */
    getAvatarList() {
        return '/avatar/avatar.json'
    },

    /**
     * 跳转到 index.html
     * @returns {string}
     */
    toIndex() {
        return '/'
    },


}
