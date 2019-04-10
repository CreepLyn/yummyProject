<template>
  <div style="padding: 20px">
    <div class="topWrapper">
      我的余额：￥
      <span class="balanceText">{{balance}}</span>
    </div>
    <div class="topWrapper rechargeWrapper">
      提现金额：￥
      <el-input
        placeholder="请输入金额"
        v-model="withdraw"
        style="width: 300px"
        clearable>
      </el-input>
    </div>
    <div class="topWrapper rechargeWrapper">
      <el-button type="primary" @click="doWithdraw">确认提现</el-button>
    </div>
  </div>
</template>

<script>
export default {
  name: 'mer-account',
  data () {
    return {
      balance: 989,
      withdraw: 0
    }
  },
  mounted () {
    let thisVue = this
    let loading = this.$loading({
      lock: true,
      text: 'Loading',
      spinner: 'el-icon-loading',
      background: 'rgba(0, 0, 0, 0.7)'
    })
    let username = this.$route.query.name
    this.$axios.post('/user/getInfo', {
      username: username
    })
      .then(function (response) {
        console.log(response.data)
        thisVue.balance = response.data.balance
        loading.close()
      })
      .catch(function (error) {
        console.log(error)
      })
  },
  methods: {
    doWithdraw: function () {
      let thisVue = this
      let username = this.$route.query.name
      this.$axios.post('/user/withdraw', {
        username: username,
        money: thisVue.withdraw
      })
        .then(function (response) {
          console.log(response.data)
          thisVue.balance = response.data
          thisVue.$message({
            message: '恭喜你，提现成功！',
            type: 'success'
          })
          thisVue.withdraw = 0
        })
        .catch(function (error) {
          console.log(error)
        })
    }
  }
}
</script>

<style scoped>
  .topWrapper{
    width: 80%;
    height: 50px;
    padding-left: 20px;
    font-size: 14px;
    line-height: 50px;
  }
  .balanceText{
    font-size: 26px;
    font-weight: 600;
    color: #008de8;
  }
  .rechargeWrapper{
    margin-top: 20px;
  }
</style>
