new Vue({
    el: '#app',
    data(){
        return{

            //当前登录用户
            //currentUserId: '',
            attType: false, // 对该用户关注状态

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

            /**
             * 每次刷新页面，主动链接WebSocket
             */
            this.initWebSocket();

        },

        initWebSocket(){
            let $this = this;  //声明一个变量指向Vue实例this，保证作用域一致
            this.websocket = new WebSocket(api.chat.websocket(this.form.id))
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
                $this.clean()
                let entity = JSON.parse(event.data);

                //上线提醒
                if (entity.data == undefined) {
                    $this.initUser()
                    $this._notify('消息', entity.msg, 'info')
                    $this.scroll()
                    return;
                }

                //消息接收  JSON.parse(event.data).data  == R.data
                let data = JSON.parse(event.data).data
                if (data.to != undefined) {
                    //单个窗口发送，仅推送到指定的窗口
                    if (data.from.id == $this.current_window_id) {
                        $this.messageList.push(data) // 使 接受者 看到消息。。
                    }
                } else {
                    //群发，推送到官方群组窗口
                    $this.messageList.push(data)
                }
                $this.scroll()
            }
            //链接关闭时调用
            this.websocket.onclose = function () {
                $this._notify('提醒', 'WebSocket链接关闭', 'info')
            }

        },


        initCurrentUser(){

            this.$http.get(api.user.getUser).then(res =>{
                if(res.body.code == 200){
                    this.form.user = res.body.data;
                }else{
                    this._message(res.body.message, "error");
                }
            })
        },

        initUser(){
            this.$http.get(api.user.getUserById(this.user.id)).then(res =>{
                console.log("res", res);
                if(res.body.code == 200){
                    this.user = res.body.data;
                }else{
                    this._message(res.body.message, "error");
                }
            })
        },

        /*关注该用户 */
        attPeople(){


        },

        /*关注 或则取消 */
        unAttType(){
            if(this.attType){
                this.attType = false;
            }else{
                this.attType = true;
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
                if(res.body.code == 200){
                    this.initSelfMessage() //使自己的页面显示发送的消息。
                    this.clean()
                    this._notify('提醒', '消息推送成功', 'success')
            }else{
                    this._notify('提醒', res.body.msg, 'error')
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
                    this._message("消息发送失败:" + res.body.message, "warning");
                }

            })
        },

        /*显示聊天记录框 */
        showMessageVisible(){
            this.initSelfMessage();
            this.messageVisible = true;
        },





    }

})

