new Vue({
    el: '#app',
    data(){
        return{

            //当前登录用户
            //currentUserId: '',
            attType: '', // 对该用户关注状态

            showOperational: true, //当前用户与登录用户是否一致

            /*当前用户主页*/
            user: {
                id: '',
                email: '',
                password: '',
                bio: '',
                name: '',
                avatarUrl: '',
                gender: '',

            },

            /*聊天窗口*/
            messageVisible: false,

            /*当前登录用户*/
            form: {
                user:{
                    id: '',
                    name: '',
                    avatarUrl:'',

                },
                message: '',
            },

            //推送消息列表
            messageList: []



        }
    },

    created() {
        this.user.id = this.subURL();
        console.log("user.id == ", this.user.id);
    },

    mounted(){

        this.init();
        console.log("user  == ", this.user);
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

        /**
         * Get Rest URL params
         * for example: http://localhost:8887/321/people
         *
         * return 321
         */
        subURL() {
            //   window.location.pathname ==  /321/people
            //return window.location.pathname.substring(window.location.pathname.indexOf('/') + 1, window.location.pathname.lastIndexOf('/'))
            return window.location.pathname.substring(window.location.pathname.lastIndexOf('/') + 1, window.location.pathname.length);

        },


        init(){

            //获取用户信息
            this.initUser();

            //获取当前的登录用户信息
            this.initCurrentUser();

            //判断是否关注该用户 attType
            this.initAttType();
        },

        initAttType(){
            this.$http.get(api.follow.getFollow(this.user.id)).then(res =>{
                if(res.body.code == 200){
                this.attType = res.body.data.follow;
                }else{
                    this._message(res.body.message, "error");
                }
            })
        },

        initWebSocket(){
            let $this = this;  //声明一个变量指向Vue实例this，保证作用域一致
            this.websocket = new WebSocket(api.chat.websocket(this.form.user.id))
            //链接发送错误时调用
            this.websocket.onerror = function () {
                $this._notify('提醒', 'WebSocket链接错误', 'error')
            }
            //链接成功时调用
            this.websocket.onopen = function () {
                $this._notify('提醒', 'WebSocket链接成功', 'success')
            }
            //接收到消息时回调
            this.websocket.onmessage = function (event) {

                //消息接收  JSON.parse(event.data).data  == ResponseVO.data
                let data = JSON.parse(event.data).data;
                $this.messageList.push(data)
                $this._notify('提醒', '您有新的消息', 'success')
            }
            //链接关闭时调用
            this.websocket.onclose = function () {
                $this._notify('提醒', 'WebSocket链接关闭', 'info')
            }

        },


        /*获取当前登录用户*/
        initCurrentUser(){
            this.$http.get(api.user.getUser).then(res =>{
                if(res.body.code == 200){
                    this.form.user = res.body.data;
                    //需要判断 当前用户与登录用户是否一致, 如果相等隐藏
                    if(this.form.user.id === this.user.id){
                        this.showOperational = false;
                    }
                }else{
                    this._message(res.body.message, "error");
                }
            })
        },

        initUser(){
            this.$http.get(api.user.getUserById(this.user.id)).then(res =>{
                if(res.body.code == 200){
                    this.user = res.body.data;
                }else{
                    this._message(res.body.message, "error");
                }
            })
        },


        /*关注 或则取消 */
        unAttType(){
            if(this.attType){
                //取消关注
                this.$http.delete(api.follow.unFollow(this.user.id)).then(res =>{
                    if(res.body.code == 200){
                        this.attType = false;
                    }else{
                        this._message(res.body.message, "error");
                    }
                })

            }else{
                //关注
                this.$http.put(api.follow.follow(this.user.id)).then(res =>{
                    if(res.body.code == 200){
                        this.attType = true;
                    }else{
                        this._message(res.body.message, "error");
                    }
                })
            }
            /*修改数据库 */

        },

        send(){
            if (this.form.message == null || this.form.message.trim() == '') {
                this._message('请输入消息内容', 'warning')
                return;
            }
            let data = {
                /*发送的 消息 以及接收者 */
                message: this.form.message,
                to: this.user
            }
            this.$http.post(api.chat.sendMessateByUser, JSON.stringify(data)).then(res =>{
                if(res.body.code == 6){
                    //表示对方不在线
                    this.initSelfMessage() //使自己的页面显示发送的消息。
                    this.clean()
                    this._notify('提醒', res.body.message, 'success')
                }else if(res.body.code == 200){
                    this.initSelfMessage() //使自己的页面显示发送的消息。
                    this.clean()
                    this._notify('提醒', '消息推送成功', 'success')
                }else{
                    this._notify('提醒', res.body.message, 'error')
                }
            })
        },

        clean(){
            this.form.message = ''
        },

        /* 加载用户之间聊天消息 */
        initSelfMessage() {
            this.$http.get(api.chat.getMessageByToId(this.user.id)).then(res => {
                if(res.body.code == 200){
                    this.messageList = res.body.data;
                }else{
                    this._message(res.body.message, "error");
                }

            })
        },

        /*显示聊天记录框 */
        showMessageVisible(){
            if(this.form.user.id == null || this.form.user.id == ''){
                this._message("您还未登录", "error");
                return;
            }
            this.initSelfMessage();
            this.messageVisible = true;
            /**
             * 每次刷新页面，主动链接WebSocket
             */
            this.initWebSocket();
        },


    },

    watch: {
        messageVisible:function(){
            if(this.messageVisible == false){
                console.log("watch: messageVisible")
                this.websocket.close()
            }
        }
    },


})

