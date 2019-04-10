<template>
  <div>
    <div class="topWrapper">
      <div class="locationWrapper">
        <img src="../../assets/img/location-city.png" class="locationIcon"/>
        <div class="locationText">南京市 南京大学鼓楼校区</div>
        <span>|</span>
        <div class="changeLocation">切换地址</div>
      </div>
      <div class="searchWrapper">
        <el-input
          size="medium"
          placeholder="请输入内容"
          suffix-icon="el-icon-search"
          @keyup.enter.native="searchShop"
          v-model="searchText">
        </el-input>
      </div>
    </div>
    <div class="classificationWrapper">
      <img src="../../assets/img/shop.png" class="classificationIcon">
      <div class="label">商家分类&nbsp;&nbsp;|&nbsp;&nbsp;</div>
      <div class="label">
        <span class="classification activeClass" @click="getShopList" id="allType">全部商家</span>
        <span class="classification" @click="chooseByType(0)" id="type0">快餐便当</span>
        <span class="classification" @click="chooseByType(1)" id="type1">特色菜系</span>
        <span class="classification" @click="chooseByType(2)" id="type2">甜品饮品</span>
        <span class="classification" @click="chooseByType(3)" id="type3">果蔬生鲜</span>
      </div>
    </div>
    <div class="main">
      <div class="listItem" @click="getShopInfo(index)" v-for="(shop, index) in shopList" :key="index">
        <div class="coverWrapper">
          <img :src="shop.url" class="coverImg"/>
        </div>
        <div class="itemInfo">
          <p class="itemTitle">{{shop.shopname}}</p>
          <el-rate
            value="4"
            disabled
            show-score
            text-color="#ff9900"
            score-template="{value}">
          </el-rate>
          <p class="priceInfo">
            <svg class="icon moneyIcon" aria-hidden="true" >
              <use xlink:href="#icon-swticonkuaidi1"></use>
            </svg>
            起送:
            <svg class="icon moneyIcon" aria-hidden="true" >
              <use xlink:href="#icon-money"></use>
            </svg>
            {{shop.miniPrice}}
            配送:
            <svg class="icon moneyIcon" aria-hidden="true" >
              <use xlink:href="#icon-money"></use>
            </svg>
            {{shop.deliveryPrice}}
          </p>
          <p class="priceInfo">
            <svg class="icon moneyIcon" aria-hidden="true" >
              <use xlink:href="#icon-qunliao"></use>
            </svg>
            人均
            15
            <svg class="icon moneyIcon" aria-hidden="true" >
              <use xlink:href="#icon-money"></use>
            </svg>
          </p>
          <p class="priceInfo">
            <svg class="icon moneyIcon" aria-hidden="true" >
              <use xlink:href="#icon-lishisousuo"></use>
            </svg>
            送达
            45
            分钟
          </p>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'cus-list',
  data () {
    return {
      searchText: '',
      shopList: [{
        id: 1,
        shopname: 'sdf',
        deliveryPrice: 9,
        miniPrice: 25
      }],
      type: ['快餐便当', '特色菜系', '甜品饮品', '果蔬生鲜'],
      rate: 3.0
    }
  },
  mounted () {
    this.getParam()
  },
  methods: {
    getParam: function () {
      let thisVue = this
      let loading = this.$loading({
        lock: true,
        text: 'Loading',
        spinner: 'el-icon-loading',
        background: 'rgba(0, 0, 0, 0.7)'
      })
      this.$axios.get('/shop/getShopList')
        .then(function (response) {
          console.log(response.data)
          thisVue.shopList = response.data
          loading.close()
        })
        .catch(function (error) {
          console.log(error)
        })
    },
    getShopInfo: function (index) {
      let thisVue = this
      let routeData = this.$router.resolve({
        name: 'shopDetail',
        path: '/customer/shopDetail',
        query: {
          name: thisVue.$route.query.name,
          initial: 'no',
          shopInfo: JSON.stringify(thisVue.shopList[index])
        }
      })
      window.open(routeData.href, '_blank')
    },
    getShopList: function () {
      for (let i = 0; i < 4; i++) {
        document.getElementById('type' + i).className = 'classification'
      }
      document.getElementById('allType').className = 'classification activeClass'
      this.getParam()
    },
    searchShop: function () {
      console.log(this.searchText)
      let thisVue = this
      let loading = this.$loading({
        lock: true,
        text: 'Loading',
        spinner: 'el-icon-loading',
        background: 'rgba(0, 0, 0, 0.7)'
      })
      this.$axios.post('/shop/searchShop', {
        text: thisVue.searchText
      })
        .then(function (response) {
          console.log(response.data)
          thisVue.shopList = response.data
          loading.close()
        })
        .catch(function (error) {
          console.log(error)
        })
    },
    chooseByType: function (index) {
      for (let i = 0; i < 4; i++) {
        if (i === index) {
          document.getElementById('type' + index).className = 'classification activeClass'
        } else {
          document.getElementById('type' + i).className = 'classification'
        }
      }
      document.getElementById('allType').className = 'classification'
      let thisVue = this
      let loading = this.$loading({
        lock: true,
        text: 'Loading',
        spinner: 'el-icon-loading',
        background: 'rgba(0, 0, 0, 0.7)'
      })
      this.$axios.post('/shop/getShopListByType', {
        type: thisVue.type[index]
      })
        .then(function (response) {
          console.log(response.data)
          thisVue.shopList = response.data
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
  .topWrapper{
    margin-top: 10px;
    width: 100%;
    height: 60px;
    padding: 0 15%;
    /*background-color: white;*/
    overflow: hidden;
    box-sizing: border-box;
  }
  .locationWrapper{
    float: left;
    height: 100%;
    width: 210px;
    line-height: 60px;
  }
  .locationIcon{
    width: 25px;
    height: auto;
    margin-top: 16px;
    float: left;
    margin-right: 3px;
  }
  .locationText{
    font-size: 12px;
    width: 120px;
    text-overflow:ellipsis;
    white-space:nowrap;
    overflow:hidden;
    float: left;
  }
  .changeLocation{
    float: right;
    font-size: 12px;
    color: #008de8;
    cursor: pointer;
  }
  .changeLocation:hover {
    color: #d64b29;
  }
  .searchWrapper{
    width: 330px;
    height: 60px;
    line-height: 60px;
    float: right;
  }
  .classificationWrapper{
    width: 70%;
    margin: 10px 15%;
    box-sizing: border-box;
    height: 80px;
    background-color: white;
    border: 1px solid #e5e5e5;
  }
  .classificationIcon{
    width: 25px;
    height: auto;
    margin-top: 27px;
    margin-left: 27px;
    margin-right: 10px;
    float: left;
  }
  .label{
    float: left;
    font-size: 14px;
    height: 80px;
    font-weight: 600;
    line-height: 80px;
    margin-right: 10px;
  }
  .classification{
    font-weight: 400;
    margin: 0 10px;
    cursor: pointer;
  }
  .classification:hover {
    color: #d64b29;
  }
  .activeClass{
    color: #d64b29;
  }
  .main{
    height: 415px;
    margin: 20px 15%;
    width: 70%;
    box-sizing: border-box;
    background-color: white;
    border: 1px solid #e5e5e5;
    padding: 17px;
    overflow: hidden;
  }
  .listItem{
    width: 300px;
    height: 150px;
    /*border: 1px solid #999;*/
    float: left;
    box-sizing: border-box;
    padding: 15px;
    cursor: pointer;
  }
  .listItem:hover{
    background-color: #fafafa;
  }
  .coverWrapper{
    width: 110px;
    height: 110px;
    border: 1px solid #e5e5e5;
    float: left;
  }
  .coverImg{
    width: 110px;
    height: 110px;
  }
  .itemInfo{
    width: 150px;
    height: 120px;
    /*border: 1px solid #999;*/
    float: left;
    padding-left: 13px;
    padding-top: 5px;
    box-sizing: border-box;
  }
  .itemTitle{
    font-size: 16px;
    font-weight: 600;
    width: 135px;
    text-overflow:ellipsis;
    white-space:nowrap;
    overflow:hidden;
    margin-bottom: 3px;
  }
  .el-rate__icon{
    font-size: 13px;
    margin-right: 3px;
  }
  .el-rate__text{
    font-size: 13px;
  }
  .priceInfo{
    font-size: 12px;
    margin: 3px 0;
    color: #7e7e7e;
  }
</style>
