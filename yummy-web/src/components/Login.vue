<template>
  <div>
    <div class="nav">
      <img src="../assets/img/logo.png" class="logoImg"/>
      <img src="../assets/img/title.png" class="titleImg"/>
    </div>
    <div class="main">
      <div class="wrapper" id="wrapper">
        <div class="avatar">
          <img src="../assets/img/user.png" class="avatarImg"/>
          <img src="../assets/img/welcome.png" class="welcomeImg"/>
        </div>
        <div class="loginWrapper" id="loginWrapper">
          <el-form :label-position="labelPosition" label-width="80px" :model="userInfo">
            <el-form-item label="邮箱">
              <el-input v-model="userInfo.email"></el-input>
            </el-form-item>
            <el-form-item label="密码">
              <el-input v-model="userInfo.password" type="password"></el-input>
            </el-form-item>
            <el-form-item>
              <el-button round class="loginBtn" @click="login">登录</el-button>
            </el-form-item>
          </el-form>
          <div class="loginTip" @click="changeToRegister">还没有账号？点击注册！</div>
        </div>
        <div class="registerWrapper" id="registerWrapper">
          <el-form :label-position="labelPosition" label-width="80px" :model="newUser" :rules="rules" ref="newUser" size="small">
            <el-form-item label="邮箱" prop="email">
              <el-input v-model="newUser.email"></el-input>
            </el-form-item>
            <el-form-item label="类型" prop="type">
              <el-radio-group v-model="newUser.type">
                <el-radio label="普通用户" value="customer"></el-radio>
                <el-radio label="外卖商家" value="merchant"></el-radio>
              </el-radio-group>
            </el-form-item>
            <el-form-item label="密码" prop="password">
              <el-input v-model="newUser.password" type="password"></el-input>
            </el-form-item>
            <el-form-item label="确认密码" prop="repeat">
              <el-input v-model="newUser.repeat" type="password"></el-input>
            </el-form-item>
            <el-form-item label="验证码" prop="code">
              <el-form :inline="true" :model="newUser" class="demo-form-inline">
                <el-form-item prop="code">
                  <el-input v-model="newUser.code" placeholder="验证码" size="small" style="width: 150px;"></el-input>
                </el-form-item>
                <el-form-item>
                  <el-button type="primary" @click="onSubmit" size="mini" :disabled="isCode">获取</el-button>
                </el-form-item>
              </el-form>
            </el-form-item>
            <el-form-item>
              <el-button round class="loginBtn" @click="register" >注册</el-button>
            </el-form-item>
          </el-form>
        </div>
      </div>
    </div>
    <div class="mask"></div>
  </div>
</template>

<script>
export default {
  name: 'login',
  data () {
    let validatePass = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入密码'))
      } else {
        if (this.newUser.repeat !== '') {
          this.$refs['newUser'].validateField('repeat')
        }
        callback()
      }
    }
    let validatePass2 = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请再次输入密码'))
      } else if (value !== this.newUser.password) {
        callback(new Error('两次输入密码不一致!'))
      } else {
        callback()
      }
    }
    return {
      labelPosition: 'right',
      userInfo: {
        email: '',
        password: ''
      },
      newUser: {
        password: '',
        repeat: '',
        email: '',
        type: '',
        code: ''
      },
      rules: {
        email: [
          { required: true, message: '请输入邮箱地址', trigger: 'blur' },
          { type: 'email', message: '请输入正确的邮箱地址', trigger: ['blur', 'change'] }
        ],
        type: [
          { required: true, message: '请选择类型', trigger: 'change' }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          {validator: validatePass, trigger: 'blur'}
        ],
        repeat: [
          { required: true, message: '请再次输入密码', trigger: 'blur' },
          { validator: validatePass2, trigger: 'blur' }
        ],
        code: [
          { required: true, message: '请输入验证码', trigger: 'blur' }
        ]
      },
      isCode: false,
      code: ''
    }
  },
  methods: {
    changeToRegister: function () {
      let loginWrapper = document.getElementById('loginWrapper')
      loginWrapper.style.display = 'none'
      let registerWrapper = document.getElementById('registerWrapper')
      registerWrapper.style.display = 'block'
      let wrapper = document.getElementById('wrapper')
      wrapper.style.height = '480px'
    },
    onSubmit: function () {
      this.$notify({
        title: '成功',
        dangerouslyUseHTMLString: true,
        message: '验证码已发送至您的邮箱，<strong>一分钟后可重新发送</strong>，请尽快前往认证！',
        type: 'success'
      })
      this.isCode = true
      let count = 60
      let timer = setInterval(() => {
        if (count > 0 && count <= 60) {
          count--
        } else {
          this.isCode = false
          clearInterval(timer)
        }
      }, 1000)
      let thisVue = this
      thisVue.$axios.post('/user/getCode', thisVue.newUser)
        .then(function (response) {
          console.log(response.data)
          thisVue.code = response.data
        })
        .catch(function (error) {
          console.log(error)
        })
    },
    login: function () {
      let thisVue = this
      let loading = this.$loading({
        lock: true,
        text: 'Loading',
        spinner: 'el-icon-loading',
        background: 'rgba(0, 0, 0, 0.7)'
      })
      this.$axios.post('/user/login', {
        email: thisVue.userInfo.email,
        password: thisVue.userInfo.password
      })
        .then(function (response) {
          loading.close()
          let list = response.data.split(',')
          let result = list[0]
          if (result === 'FAILURE') {
            thisVue.$message.error('密码错误！')
          } else if (result === 'NOT_EXIST') {
            thisVue.$message({
              type: 'warning',
              message: '该用户不存在！请先注册！'
            })
          } else {
            if (result === '普通用户') {
              thisVue.$router.push({
                path: '/customer',
                name: 'cus-home',
                query: {
                  name: list[1]
                }
              })
            } else if (result === '外卖商家') {
              thisVue.$router.push({
                path: '/merchant',
                name: 'mer-home',
                query: {
                  name: list[1]
                }
              })
            } else {
              thisVue.$router.push({
                path: '/admin',
                name: 'admin-home',
                query: {
                  name: list[1]
                }
              })
            }
          }
        })
        .catch(function (error) {
          console.log(error)
        })
    },
    register: function () {
      let thisVue = this
      let loading = this.$loading({
        lock: true,
        text: 'Loading',
        spinner: 'el-icon-loading',
        background: 'rgba(0, 0, 0, 0.7)'
      })
      this.$refs['newUser'].validate((valid) => {
        if (valid) {
          if (thisVue.newUser.code.toString() === thisVue.code.toString()) {
            thisVue.$axios.post('/user/register', thisVue.newUser)
              .then(function (response) {
                loading.close()
                if (response.data === 'EXIST') {
                  thisVue.$message.error('该邮箱已被注册！')
                } else {
                  let loginWrapper = document.getElementById('loginWrapper')
                  loginWrapper.style.display = 'block'
                  let registerWrapper = document.getElementById('registerWrapper')
                  registerWrapper.style.display = 'none'
                  let wrapper = document.getElementById('wrapper')
                  wrapper.style.height = '440px'
                  thisVue.$refs.newUser.resetFields()
                }
              })
              .catch(function (error) {
                console.log(error)
              })
          } else {
            loading.close()
            thisVue.$message.error('验证码错误！')
          }
        } else {
          console.log('error submit!!')
          return false
        }
      })
    }
  }
}
</script>

<style scoped>
  .nav{
    height: 70px;
    width: 100%;
    background-color: #d64b29;
    box-sizing: border-box;
  }
  .logoImg{
    margin-left: 35px;
    margin-right: 20px;
    vertical-align: middle;
  }
  .titleImg{
    height: 35px;
    width: auto;
    margin-top: 6px;
    vertical-align: middle;
  }
  .main{
    width: 100%;
    height: 737px;
    background: url("../assets/img/hotpot.jpg") no-repeat center -25px fixed;
    -webkit-background-size: cover;
    -moz-background-size: cover;
    -o-background-size: cover;
    background-size: cover;
    position: relative;
  }
  .wrapper{
    position: absolute;
    left: 30%;
    top: 120px;
    width: 40%;
    height: 440px;
    /*opacity: 0.7;*/
    background-color: white;
    z-index: 2;
    box-sizing: border-box;
    padding-top: 35px;
  }
  .avatar{
    width: 100%;
    height: 100px;
    box-sizing: border-box;
  }
  .avatarImg{
    width: 65px;
    height: auto;
    margin: 5px auto;
    display: block;
  }
  .welcomeImg{
    height: 30px;
    width: auto;
    margin: 0 auto;
    display: block;
  }
  .loginWrapper{
    width: 100%;
    height: 240px;
    box-sizing: border-box;
    padding-top: 30px;
    padding-left: 80px;
    padding-right: 130px;
    /*border: 1px solid #d64b29;*/
    /*display: none;*/
  }
  .loginBtn{
    width: 125px;
    margin-left: 50px;
    background-color: #d64b29;
    color: white;
  }
  .loginBtn:hover{
    background-color: #fe5f3d;
    color: white;
  }
  .loginTip{
    margin: 0 auto;
    font-size: 12px;
    text-align: center;
    color: #424242;
    cursor: pointer;
    text-indent: 5em;
  }
  .loginTip:hover{
    color: #4fa7f0;
  }
  .registerWrapper{
    width: 100%;
    height: 240px;
    box-sizing: border-box;
    padding-left: 80px;
    padding-right: 130px;
    margin-top: 5px;
    display: none;
    /*border: 1px solid #d64b29;*/
  }
  .mask{
    position: absolute;
    top: 70px;
    left: 0;
    width: 100%;
    height: 737px;
    background-color: black;
    opacity: 0.6;
  }
</style>
