//设置全局表单提交格式
/*Vue.http.options.emulateJSON = true;
const api = {
    login: '/admin/login'
}*/
// Vue实例
new Vue({
    el: '#app',
    data() {
        return {
            //github请求地址
            githubGETString: 'https://github.com/login/oauth/authorize?client_id=67e02325958dedeaf220&redirect_uri=http://localhost:8887/callback&scope=user&state=1',
            user: {
                id: '',
                email: '',
                password: '',
                bio: '',
                name: '',
                avatarUrl: ''
            },

            //用户头像List
            avatarList: [],

            registerDialog: false,
            avatarDialog: false
        };
    },

    mounted() {
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

        login() {
            let loginForm = {
                email: this.user.email,
                password: this.user.password
            }
            //参数校验
            this.$http.post(api.user.login, JSON.stringify(loginForm)).then(res => {
                if(res.body.code == 200){
                this._message("登录成功", "success");
                window.location.href = api.toIndex();
            }else{
                this._message("登录失败:" + res.body.message, "warning");
            }
        });

        },

        /**
         * github账号授权
         */
        gitHubBtn() {
            window.location.href = this.githubGETString;
            this._message("github授权登录成功", "success");
        },

        /**
         * 用户注册
         */
        register() {
            /* //表单验证
             if(this.user.email == null || this.user.email == ''){
                 this._message("请输入正确的邮箱信息", "error");
                 return;
             }
             if(this.user.password == null || this.user.password == ''){
                 this._message("您的密码不能为空", "error");
                 return;
             }
             if(this.user.name == null || this.user.name == ''){
                 this._message("您的昵称不能为空", "error");
                 return;
             }
             if(this.user.password.length > 18 || this.user.password.length < 6){
                 this._message("请输入6~18位的密码", "error");
                 return;
             }
             if(this.user.avatarUrl == null || this.user.avatarUrl == ''){
                 this._message("请选择您的头像", "error");
                 return;
             }*/

            this.$http.post(api.user.register, JSON.stringify(this.user)).then(res => {
                if(res.body.code == 200){
                //注册成功
                //登录
                this._message("注册成功,已为你登录", "success");
                //this.registerDialog = false;
                //跳转到主界面
                window.location.href = api.toIndex();

            }else{
                this._message("注册失败: " + res.body.message, "error");
            }
        })

        },

        /**
         * 用户选择头像
         */
        handleEditAvatar() {
            this.$http.get(api.getAvatarList()).then(res => {
                this.avatarList = res.body;
        });
            this.avatarDialog = true;
        },

        /**
         * 确认头像
         */
        changeAvatar(url) {
            this.user.avatarUrl = url;
            this.avatarDialog = false;
        },

        /**
         * 显示registerDialog
         */
        registerDialogshow() {
            this.user.email = '';
            this.user.password = '';

            this.registerDialog = true;
        },

    }

});
