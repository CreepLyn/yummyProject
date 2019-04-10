<template>
  <div style="padding: 20px">
    <div class="topWrapper">
      我的等级：
      <span class="balanceText">{{level}}</span>
    </div>
    <div class="topWrapper">
      我的余额：￥
      <span class="balanceText">{{balance}}</span>
    </div>
    <div class="topWrapper rechargeWrapper">
      充值金额：￥
      <el-input
        placeholder="请输入金额"
        v-model="recharge"
        style="width: 300px"
        clearable>
      </el-input>
    </div>
    <div class="topWrapper rechargeWrapper">
      <el-button type="primary" @click="doRecharge">确认充值</el-button>
    </div>
  </div>
</template>

<script>
export default {
  name: 'cus-account',
  data () {
    return {
      level: 1,
      balance: 989,
      recharge: 0
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
    doRecharge: function () {
      let thisVue = this
      let username = this.$route.query.name
      let loading = this.$loading({
        lock: true,
        text: 'Loading',
        spinner: 'el-icon-loading',
        background: 'rgba(0, 0, 0, 0.7)'
      })
      this.$axios.post('/user/recharge', {
        username: username,
        money: thisVue.recharge
      })
        .then(function (response) {
          console.log(response.data)
          thisVue.balance = response.data
          loading.close()
          thisVue.$message({
            message: '恭喜你，充值成功！',
            type: 'success'
          })
          thisVue.recharge = 0
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
