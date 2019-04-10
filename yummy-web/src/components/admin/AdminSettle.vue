<template>
  <div class="adminSettleWrapper">
    <div class="settleRuleWrapper">
      <div class="settleRuleTitle">*结算规则：</div>
      <div class="settleRuleContent">
        <div class="settleRuleText">1.支付金额小于&nbsp;10&nbsp;元的订单网站只收取&nbsp;1&nbsp;元手续费。</div>
        <div class="settleRuleText">2.支付金额大于&nbsp;10&nbsp;元的订单网站按成交金额的&nbsp;10%&nbsp;收取手续费。</div>
      </div>
    </div>
    <div class="settleInfoWrapper">
      <div class="settleInfoItem">
        <div class="settleInfoLabel">上一次结算：</div>
        <div class="settleInfoText">{{settleInfo.date}}</div>
      </div>
      <div class="settleInfoItem">
        <div class="settleInfoLabel">期间订单数：</div>
        <div class="settleInfoText">{{settleInfo.orderNum}}</div>
      </div>
      <div class="settleInfoItem">
        <div class="settleInfoLabel">期间营业额：</div>
        <div class="settleInfoText">￥{{settleInfo.orderMoney}}</div>
      </div>
      <div class="settleInfoItem">
        <div class="settleInfoLabel">预计盈利：</div>
        <div class="settleInfoText">￥{{settleInfo.profit}}</div>
      </div>
      <div class="settleInfoItem">
        <el-button type="primary" round class="settleBtn" @click="doSettle">确认结算</el-button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'admin-settle',
  data () {
    return {
      settleInfo: {
        date: '2018-12-12',
        orderNum: 241,
        orderMoney: 5123.78,
        profit: 512,
        orderid: 10
      }
    }
  },
  mounted () {
    this.getInfo()
  },
  methods: {
    getInfo: function () {
      let thisVue = this
      let loading = this.$loading({
        lock: true,
        text: 'Loading',
        spinner: 'el-icon-loading',
        background: 'rgba(0, 0, 0, 0.7)'
      })
      this.$axios.get('/admin/getSettleInfo')
        .then(function (response) {
          console.log(response.data)
          thisVue.settleInfo = response.data
          loading.close()
        })
        .catch(function (error) {
          console.log(error)
        })
    },
    doSettle: function () {
      let thisVue = this
      let loading = this.$loading({
        lock: true,
        text: 'Loading',
        spinner: 'el-icon-loading',
        background: 'rgba(0, 0, 0, 0.7)'
      })
      this.$axios.post('/admin/settle', {
        orderid: thisVue.settleInfo.orderid,
        profit: thisVue.settleInfo.profit
      })
        .then(function (response) {
          console.log(response.data)
          thisVue.getInfo()
          loading.close()
        })
        .catch(function (error) {
          console.log(error)
        })
    }
  }
}
</script>

<style scoped>
  .adminSettleWrapper{
    padding: 5px 20px;
  }
  .settleRuleWrapper{
    width: 500px;
    height: 40px;
    /*border: 1px solid #55a532;*/
    font-size: 12px;
    padding: 5px 15px;
    color: #7e7e7e;
  }
  .settleRuleTitle{
    float: left;
    line-height: 20px;
  }
  .settleRuleContent{
    float: left;
  }
  .settleRuleText{
    width: 400px;
    height: 20px;
    line-height: 20px;
  }
  .settleInfoWrapper{
    width: 500px;
    height: 300px;
    padding: 15px;
    /*border: 1px solid #55a532;*/
  }
  .settleInfoItem{
    width: 100%;
    height: 50px;
    margin-bottom: 10px;
    /*border: 1px solid #55a532;*/
  }
  .settleInfoLabel{
    float: left;
    width: 120px;
    text-align: right;
    line-height: 50px;
    font-size: 13px;
  }
  .settleInfoText{
    line-height: 50px;
    float: left;
    font-size: 20px;
    font-weight: 600;
    margin-left: 20px;
  }
  .settleBtn{
    margin: 10px 30px;
    float: right;
  }
</style>
