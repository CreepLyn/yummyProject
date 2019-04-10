<template>
  <div class="personMain">
    <div class="infoItem">
      <div class="infoLabel">我的账号：</div>
      <div class="infoText">{{user.id}}</div>
      <div class="reInfo">
        <el-button type="warning" icon="el-icon-error" size="small" @click="deleteOpen">注销</el-button>
      </div>
    </div>
    <div class="infoItem">
      <div class="infoLabel">我的昵称：</div>
      <div class="infoText">
        <el-input
          placeholder="请输入内容"
          v-model="user.username"
          size="small"
          :disabled="userFlag"
          clearable>
        </el-input>
      </div>
      <div class="reInfo">
        <el-button type="primary" icon="el-icon-edit" size="small" @click="modify(0)" :disabled="user.type === 'merchant'">修改</el-button>
      </div>
    </div>
    <div class="infoItem">
      <div class="infoLabel">我的邮箱：</div>
      <div class="infoText">
        <el-input
          placeholder="请输入内容"
          v-model="user.email"
          size="small"
          :disabled="emailFlag"
          clearable>
        </el-input>
      </div>
      <div class="reInfo">
        <el-button type="primary" icon="el-icon-edit" size="small" @click="modify(1)">修改</el-button>
      </div>
    </div>
    <div class="infoItem">
      <div class="infoLabel">我的手机：</div>
      <div class="infoText">
        <el-input
          placeholder="请输入内容"
          v-model="user.telephone"
          size="small"
          :disabled="telFlag"
          clearable>
        </el-input>
      </div>
      <div class="reInfo">
        <el-button type="primary" icon="el-icon-edit" size="small" @click="modify(2)">修改</el-button>
      </div>
    </div>
    <div class="infoItem">
      <div class="infoLabel">我的密码：</div>
      <div class="infoText">
        <el-input
          placeholder="请输入内容"
          v-model="user.password"
          size="small"
          type="password"
          :disabled="pswFlag"
          clearable>
        </el-input>
      </div>
      <div class="reInfo">
        <el-button type="primary" icon="el-icon-edit" size="small" @click="modify(3)">修改</el-button>
      </div>
      <el-dialog title="请输入旧密码:" :visible.sync="dialogFormVisible" width="30%">
        <el-input
          placeholder="请输入旧密码"
          v-model="password"
          type="password"
          clearable>
        </el-input>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogFormVisible = false">取 消</el-button>
          <el-button type="primary" @click="checkPsw">确 定</el-button>
        </div>
      </el-dialog>
    </div>
    <div class="btnWrapper">
      <el-button type="warning" plain icon="el-icon-edit" size="medium" @click="resetInfo">重置</el-button>
      <el-button type="success" plain icon="el-icon-edit" size="medium" @click="doModify">确认</el-button>
    </div>
  </div>
</template>

<script>
export default {
  name: 'cus-person',
  data () {
    return {
      user: {
        id: 123134651,
        username: 'creepLyn',
        telephone: 13805103211,
        email: '123@123.com'
      },
      password: '',
      dialogFormVisible: false,
      userFlag: true,
      telFlag: true,
      emailFlag: true,
      pswFlag: true
    }
  },
  mounted () {
    this.resetInfo()
  },
  methods: {
    deleteOpen: function () {
      let thisVue = this
      this.$confirm('此操作将不可恢复, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$message({
          type: 'success',
          message: '注销成功!'
        })
        let username = thisVue.$route.query.name
        this.$axios.post('/user/logout', {
          username: username
        })
          .then(function (response) {
            thisVue.$router.replace({path: '/', name: 'login'})
          })
          .catch(function (error) {
            console.log(error)
          })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消注销'
        })
      })
    },
    modify: function (index) {
      switch (index) {
        case 0:
          this.userFlag = false
          break
        case 1:
          this.emailFlag = false
          break
        case 2:
          this.telFlag = false
          break
        case 3:
          this.dialogFormVisible = true
          break
      }
    },
    resetInfo: function () {
      let thisVue = this
      let username = this.$route.query.name
      let loading = this.$loading({
        lock: true,
        text: 'Loading',
        spinner: 'el-icon-loading',
        background: 'rgba(0, 0, 0, 0.7)'
      })
      this.$axios.post('/user/getInfo', {
        username: username
      })
        .then(function (response) {
          console.log(response.data)
          thisVue.user = response.data
          loading.close()
        })
        .catch(function (error) {
          console.log(error)
        })
    },
    checkPsw: function () {
      let thisVue = this
      this.$axios.post('/user/login', {
        email: thisVue.user.email,
        password: thisVue.password
      })
        .then(function (response) {
          console.log(response.data)
          if (response.data.split(',')[0] === thisVue.user.type) {
            thisVue.dialogFormVisible = false
            thisVue.pswFlag = false
          } else {
            thisVue.$message.error('密码错误！请重新输入！')
          }
        })
        .catch(function (error) {
          console.log(error)
        })
    },
    doModify: function () {
      let thisVue = this
      let password = 'an817qwhvfdjigdf76293487bhjsdbf2y3t'
      if (thisVue.user.password !== undefined) {
        password = thisVue.user.password
      }
      let loading = this.$loading({
        lock: true,
        text: 'Loading',
        spinner: 'el-icon-loading',
        background: 'rgba(0, 0, 0, 0.7)'
      })
      this.$axios.post('/user/modify', {
        username: thisVue.user.username,
        email: thisVue.user.email,
        telephone: thisVue.user.telephone,
        password: password
      })
        .then(function (response) {
          console.log(response.data)
          loading.close()
          if (response.data === 'SUCCESS') {
            thisVue.$message({
              message: '恭喜你，修改成功！',
              type: 'success'
            })
            thisVue.userFlag = true
            thisVue.telFlag = true
            thisVue.emailFlag = true
            thisVue.pswFlag = true
          }
        })
        .catch(function (error) {
          console.log(error)
        })
    }
  }
}
</script>

<style scoped>
  .personMain{
    padding: 0 35px;
  }
  .infoItem {
    width: 80%;
    height: 40px;
    line-height: 40px;
    /*border: 1px solid #55a532;*/
    margin: 10px;
  }
  .infoLabel{
    font-size: 16px;
    float: left;
    width: 130px;
  }
  .infoText{
    width: 180px;
    margin-right: 20px;
    float: left;
    font-size: 14px;
    color: #7e7e7e;
  }
  .reInfo{
    float: left;
    width: 60px;
  }
  .btnWrapper{
    margin-top: 30px;
    /*width: 100%;*/
    /*height: 90px;*/
    padding-left: 300px;
    /*border: 1px solid #55a532;*/
  }
</style>
