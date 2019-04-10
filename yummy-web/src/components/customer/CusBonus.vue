<template>
  <div style="padding: 20px">
    <div class="noWrapper" v-show="redPacketList.length === 0">暂无红包</div>
    <div class="redCard" v-for="(redPacket, index) in redPacketList" :key="index">
      <div class="topWrapper">
        <div class="leftWrapper">
          <div class="bonusWrapper">
            <span>￥</span>
            <span style="font-size: 28px">{{redPacket.bonus}}</span>
          </div>
          <div class="priceWrapper">
            满{{redPacket.price}}元可用
          </div>
        </div>
        <div class="textWrapper">
          <div class="bonusTitle">{{redPacket.type}}专享红包</div>
          <div class="bonusText">限在线支付使用</div>
          <div class="bonusText">限收货手机号为{{redPacket.telephone}}</div>
        </div>
        <div class="rightWrapper">
          <div class="dayWrapper">限今日</div>
          <div class="useBtn" @click="useRedPackage">去使用</div>
        </div>
      </div>
      <div class="typeWrapper">
        限品类：{{redPacket.type}}
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'cus-bonus',
  data () {
    return {
      redPacketList: [
        {
          bonus: 5,
          price: 30,
          type: '快餐便当',
          telephone: '13808697241'
        },
        {
          bonus: 7,
          price: 45,
          type: '甜品饮品',
          telephone: '13808697242'
        },
        {
          bonus: 4,
          price: 25,
          type: '全品类',
          telephone: '13808697243'
        }
      ]
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
    this.$axios.post('/user/getRedPackge', {
      username: username
    })
      .then(function (response) {
        console.log(response.data)
        thisVue.redPacketList = response.data
        loading.close()
      })
      .catch(function (error) {
        console.log(error)
      })
  },
  methods: {
    useRedPackage: function () {
      this.$router.push({path: '/customer/home', query: {name: this.$route.query.name}})
    }
  }
}
</script>

<style scoped>
  .noWrapper{
    width: 100%;
    height: 100px;
    line-height: 100px;
    font-size: 20px;
    font-weight: bold;
    color: #7e7e7e;
    text-align: center;
  }
  .redCard{
    width: 45%;
    height: 110px;
    margin-left: 10px;
    margin-right: 10px;
    margin-bottom: 10px;
    border: 1px solid #ccc;
    padding: 10px 15px;
    box-sizing: border-box;
    float: left;
    font-size: 12px;
    color: #7e7e7e;
    cursor: default;
  }
  .redCard:hover {
    border-color: #d64b29;
  }
  .topWrapper{
    width: 100%;
    height: 65px;
    overflow: hidden;
  }
  .leftWrapper{
    float: left;
    width: 30%;
    height: 65px;
    /*border: 1px solid #008de8;*/
    font-size: 14px;
  }
  .bonusWrapper{
    width: 100%;
    height: 40px;
    line-height: 40px;
    text-align: center;
    font-weight: 600;
    color: #d64b29;
  }
  .priceWrapper{
    width: 100%;
    height: 25px;
    line-height: 15px;
    font-size: 10px;
    text-align: center;
  }
  .bonusTitle{
    width: 100%;
    height: 30px;
    line-height: 30px;
    font-size: 14px;
    font-weight: 600;
    color: black;
  }
  .bonusText{
    width: 100%;
    height: 15px;
    line-height: 15px;
    color: #7e7e7e;
    font-size: 9px;
  }
  .typeWrapper{
    width: 100%;
    height: 20px;
    border-top: 1px dashed #CCCCCC;
    margin-top: 5px;
    line-height: 20px;
    font-size: 10px;
    color: #7e7e7e;
  }
  .textWrapper{
    float: left;
    width: 45%;
    height: 65px;
    /*border: 1px solid #008de8;*/
    margin-left: 5px;
  }
  .rightWrapper{
    float: right;
    width: 20%;
    height: 65px;
    /*border: 1px solid #008de8;*/
  }
  .dayWrapper{
    width: 100%;
    height: 30px;
    line-height: 40px;
    text-align: center;
    /*font-weight: bold;*/
    font-size: 10px;
    color: #d64b29;
  }
  .useBtn{
    width: 90%;
    height: 25px;
    line-height: 25px;
    margin: 0 auto;
    background-color: #d64b29;
    color: white;
    font-size: 10px;
    font-weight: bold;
    text-align: center;
    border-radius: 12px;
    cursor: pointer;
  }
</style>
