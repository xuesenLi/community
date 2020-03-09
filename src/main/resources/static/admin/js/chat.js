new Vue({
    el: '#app',
    data(){
        return{
            websocket: undefined,
            user: {
                id: '',
                email: '',
                password: '',
                bio: '',
                name: '',
                avatarUrl: ''
            },

            form: {message: ''},

            //当前激活窗口ID
            current_window_id: 0,

            //在线用户列表
            userList: [],
            //推送消息列表
            messageList  : []


        }
    },
    mounted() {
        this.init();
        this.$refs.loader.style.display = 'none';
    },

    methods: {
        _message(message, type) {
            this.$message({
                message: message,
                type: type
            })
        },
        _notify(title, message, type) {
            this.$notify({
                title: title,
                message: message,
                type: type
            });
        },

        init(){
            console.log(this.messageList + "调用init()")

            //获取用户信息
            this.initUser();

        },

        initUser(){
            //加载当前用户信息
            this.$http.get(api.user.getUser).then(res =>{
                console.log(res)
                if(res.body.code == 200){
                    this.user = res.body.data;
                }else{
                    this._message(res.body.message, "error");
                }
            })

            //加载聊天用户, 以及当前点击聊天的用户


        },

        send(){
            console.log("调用send()")
        },

        clean(){
            console.log("调用clear()")
        }
    }

})
