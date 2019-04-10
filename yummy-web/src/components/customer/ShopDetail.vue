<template>
  <div style="padding-top: 15px">
    <div class="shopTopWrapper">
      <img :src="shop.url" class="shopCover"/>
      <div class="shopTitle">
        <p class="titleText">{{shop.shopname}}</p>
        <div class="shopRate">
          <el-rate
            value="4"
            disabled
            show-score
            text-color="#ff9900"
            score-template="{value}">
          </el-rate>
        </div>
      </div>
      <div class="shopTopRight">
        <div class="rightTitle">包装费</div>
        <div class="rightText">
          <span class="rightNum">{{shop.packingPrice}}</span>
          元
        </div>
      </div>
      <div class="shopTopRight">
        <div class="rightTitle">配送费</div>
        <div class="rightText">
          <span class="rightNum">{{shop.deliveryPrice}}</span>
          元
        </div>
      </div>
      <div class="shopTopRight">
        <div class="rightTitle">起送价</div>
        <div class="rightText">
          <span class="rightNum">{{shop.miniPrice}}</span>
          元
        </div>
      </div>
    </div>
    <div class="shopMain">
      <div class="menuWrapper">
        <div class="menuTitle">
          <svg class="icon" aria-hidden="true" >
            <use xlink:href="#icon-fenlei"></use>
          </svg>
          &nbsp;&nbsp;菜单
        </div>
        <div class="menuItem" v-for="(menu, index) in shop.menuList" :key="index" @click="chooseMenu(index)">{{menu.name}}</div>
      </div>
      <div class="foodList">
        <div class="foodMenu">{{shop.menuList[menuIndex].name}}</div>
        <div class="foodCard" v-for="(food, index) in shop.menuList[menuIndex].foodList" :key="index">
          <img :src="food.url" class="foodImg"/>
          <div class="foodNameTitle">{{food.name}}</div>
          <div class="foodBottom">
            <span class="foodPrice">￥{{food.price}}</span>
            <div style="float: right" @click="changeFoodMenge(index)"><el-input-number size="mini" v-model="food.menge" @change="handleFoodChange" :min="0" :max="food.num"></el-input-number></div>
          </div>
        </div>
      </div>
      <div class="shopInfoWrapper">
        <div class="menuTitle">
          <svg class="icon shopIcon" aria-hidden="true" >
            <use xlink:href="#icon-hehuoren"></use>
          </svg>
          &nbsp;&nbsp;商家信息
        </div>
        <div class="shopInfoItem">{{shop.description}}</div>
        <div class="">
          <div class="shopInfoTitel">
            <svg class="icon" aria-hidden="true" >
              <use xlink:href="#icon-lishisousuo1"></use>
            </svg>
            &nbsp;营业时间
          </div>
          <div class="shopInfoText">{{shop.startTime}} - {{shop.endTime}}</div>
          <div class="shopInfoTitel">
            <svg class="icon" aria-hidden="true" >
              <use xlink:href="#icon-dizhi"></use>
            </svg>
            &nbsp;商家地址
          </div>
          <div class="shopInfoText">{{shop.address}}</div>
          <div class="shopInfoTitel">
            <svg class="icon" aria-hidden="true" >
              <use xlink:href="#icon-dianhua"></use>
            </svg>
            &nbsp;联系方式
          </div>
          <div class="shopInfoText">{{shop.telephone}}</div>
          <div class="shopInfoTitel">
            <svg class="icon" aria-hidden="true" >
              <use xlink:href="#icon-baojia"></use>
            </svg>
            &nbsp;满减
          </div>
          <div class="shopInfoText" v-show="offList.length === 0">暂无满减</div>
          <div class="shopInfoText" v-for="(offITem, index) in offList" :key="index">满{{offITem.price}}减{{offITem.off}}</div>
        </div>
      </div>
    </div>
    <div class="shoppingCartWrapper">
      <div class="orderList" id="orderList">
        <div class="orderTitle">
          <div style="float: left">购物车</div>
          <div style="float: right; cursor: pointer" @click="clearCart">清空菜品</div>
        </div>
        <div class="cartItem" v-for="(food, index) in cart" :key="index">
          <div class="foodItemName">{{food.name}}</div>
          <div class="foodItemNum">
            <el-input-number size="mini" v-model="food.menge"></el-input-number>
          </div>
          <div class="foodItemPrice">￥{{food.price}}</div>
        </div>
      </div>
      <div class="cartFooter">
        <el-badge :value="cart.length" class="cartBadge">
        <div class="cartIconWrapper">
          <svg class="icon cartIcon" aria-hidden="true" >
            <use xlink:href="#icon-gouwuche"></use>
          </svg>
        </div>
        </el-badge>
        <div id="settleWrapper" class="settleWrapper" @click="orderCheck">立即下单</div>
        <div id="noSettleWrapper" class="noSettleWrapper">购物车为空</div>
        <div class="cartTotal">￥{{totalPrice}}</div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'shop-detail',
  data () {
    return {
      shop: {
        shopname: '肯德基（广州路店）',
        rate: 3.8,
        miniPrice: 30,
        deliveryPrice: 9,
        packingPrice: 1.5,
        time: 45,
        menuList: [
          {
            name: '热销',
            foodList: [
              {
                name: '原味吮指鸡',
                price: 11,
                menge: 0,
                num: 10
              },
              {
                name: '黄金鸡块',
                price: 11.5,
                menge: 0,
                num: 10
              },
              {
                name: '香辣鸡翅',
                price: 10,
                menge: 0,
                num: 10
              },
              {
                name: '新奥尔良鸡腿堡',
                price: 17,
                menge: 0,
                num: 5
              },
              {
                name: '原味吮指鸡',
                price: 11,
                menge: 0,
                num: 5
              }
            ]
          },
          {
            name: '汉堡',
            foodList: [
              {
                name: '原味吮指鸡',
                price: 11,
                num: 0
              }
            ]
          },
          {
            name: '小吃',
            foodList: [
              {
                name: '原味吮指鸡',
                price: 11,
                num: 0
              }
            ]
          },
          {
            name: '优惠套餐',
            foodList: [
              {
                name: '原味吮指鸡',
                price: 11,
                num: 0
              }
            ]
          }
        ]
      },
      offList: [
        {
          off: 12,
          price: 20
        },
        {
          off: 22,
          price: 30
        },
        {
          off: 40,
          price: 80
        }
      ],
      menuIndex: 0,
      foodIndex: 0,
      totalPrice: 0.0,
      cart: []
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
    thisVue.shop.id = JSON.parse(this.$route.query.shopInfo).id
    this.$axios.post('/shop/getMenuInfo', {
      shopid: thisVue.shop.id
    })
      .then(function (response) {
        console.log(response.data)
        let result = response.data
        thisVue.shop = JSON.parse(thisVue.$route.query.shopInfo)
        thisVue.shop.menuList = result
        loading.close()
      })
      .catch(function (error) {
        console.log(error)
      })
    this.$axios.post('/shop/getOffInfo', {
      shopid: thisVue.shop.id
    })
      .then(function (response) {
        console.log(response.data)
        thisVue.offList = response.data
      })
      .catch(function (error) {
        console.log(error)
      })
  },
  methods: {
    chooseMenu: function (index) {
      this.menuIndex = index
    },
    changeFoodMenge: function (index) {
      this.foodIndex = index
      let food = this.shop.menuList[this.menuIndex].foodList[this.foodIndex]
      let value = food.menge
      let cartIndex = this.isFoodInCart(food.name)
      if (cartIndex >= 0) {
        if (value === 0) {
          console.log('delete')
          this.cart.splice(cartIndex, 1)
        }
      } else {
        console.log('new')
        if (value > 0) {
          this.cart.push(food)
        }
      }
      this.adjustCart()
    },
    clearCart: function () {
      this.cart = []
      this.adjustCart()
    },
    handleFoodChange: function (value) {

    },
    orderCheck: function () {
      let sum = 0.0
      for (let i in this.cart) {
        sum += Number(this.cart[i].menge) * Number(this.cart[i].price)
      }
      if (sum >= this.shop.miniPrice) {
        this.$router.replace({
          path: '/customer/orderCheck',
          query: {
            name: this.$route.query.name,
            shopInfo: {
              id: this.shop.id,
              shopname: this.shop.shopname,
              deliveryPrice: this.shop.deliveryPrice,
              packingPrice: this.shop.packingPrice,
              type: this.shop.type,
              time: 45
            },
            off: this.offList,
            cart: this.cart
          }
        })
      } else {
        this.$message.error('未达到起送价！请继续选择食品！')
      }
    },
    isFoodInCart: function (name) {
      for (let i = 0; i < this.cart.length; i++) {
        if (this.cart[i].name === name) {
          return i
        }
      }
      return -1
    },
    adjustCart: function () {
      let orderList = document.getElementById('orderList')
      let distance = 45 + 46 * this.cart.length
      orderList.style.height = distance + 'px'
      orderList.style.top = '-' + distance + 'px'
      let sum = 0.0
      for (let i = 0; i < this.cart.length; i++) {
        sum += this.cart[i].price * this.cart[i].menge
      }
      this.totalPrice = sum

      let noSettleWrapper = document.getElementById('noSettleWrapper')
      let settleWrapper = document.getElementById('settleWrapper')
      if (sum > 0) {
        settleWrapper.style.display = 'block'
        noSettleWrapper.style.display = 'none'
      } else {
        settleWrapper.style.display = 'none'
        noSettleWrapper.style.display = 'block'
      }
    }
  }
}
</script>

<style scoped>
  .shopIcon{
    width: 22px;
    height: 22px;
    vertical-align: middle;
  }
  .cartIcon{
    width: 25px;
    height: 25px;
    color: white;
    vertical-align: middle;
  }
  .el-badge__content{
    height: 14px;
    line-height: 14px;
    font-size: 10px;
  }
  .el-badge__content.is-fixed{
    top: 16px
  }
  .shopTopWrapper{
    width: 70%;
    height: 100px;
    margin: 20px 15%;
    background-color: white;
    /*border: 1px solid #e5e5e5;*/
    padding: 17px;
    overflow: hidden;
  }
  .shopCover{
    width: 100px;
    height: 100px;
    float: left;
    display: inline-block;
    margin-left: 15px;
  }
  .shopTitle{
    float: left;
    height: 100px;
  }
  .titleText{
    font-size: 18px;
    font-weight: 600;
    line-height: 25px;
    text-indent: 2em;
    margin-top: 15px;
  }
  .shopRate{
    margin-left: 35px;
    margin-top: 10px;
    float: left;
  }
  .shopTopRight{
    width: 120px;
    height: 100px;
    box-sizing: border-box;
    /*border: 1px solid #55a532;*/
    float: right;
  }
  .rightTitle{
    height: 40px;
    width: 100%;
    line-height: 55px;
    font-size: 12px;
    color: #78787a;
    text-align: center;
  }
  .rightText{
    width: 100%;
    height: 60px;
    line-height: 45px;
    font-size: 12px;
    text-align: center;
  }
  .rightNum{
    color: #008de8;
    font-weight: 600;
    font-size: 24px;
  }
  .shopMain{
    width: 72%;
    /*height: 500px;*/
    margin: 20px 15%;
    /*border: 1px solid #e5e5e5;*/
    padding: 17px 0;
    overflow: hidden;
  }
  .menuWrapper{
    width: 20%;
    height: 500px;
    float: left;
    background-color: white;
  }
  .menuTitle{
    margin: 15px 0 10px 20px;
    font-size: 16px;
    font-weight: 600;
  }
  .menuItem{
    height: 40px;
    line-height: 40px;
    font-size: 14px;
    border-bottom: 1px dashed #bbb;
    text-align: center;
    margin: 0 20px 0 48px;
    cursor: pointer;
  }
  .menuItem:hover {
    color: #d64b29;
  }
  .foodList{
    width: 53%;
    /*height: 100%;*/
    float: left;
    background-color: white;
    margin: 0 10px;
    padding: 15px;
  }
  .foodMenu{
    font-size: 16px;
    line-height: 20px;
    font-weight: 600;
    padding-left: 10px;
    height: 30px;
  }
  .foodCard{
    float: left;
    height: 200px;
    width: 45%;
    border: 1px dashed #e9e9e9;
    margin: 5px;
  }
  .foodCard:hover{
    border-color: #d64b29;
  }
  .foodImg{
    width: 90%;
    height: 110px;
    margin: 10px auto;
    display: block;
  }
  .foodNameTitle{
    height: 20px;
    width: 90%;
    margin: 0 auto;
    line-height: 20px;
    font-size: 14px;
    font-weight: 600;
  }
  .foodBottom{
    height: 20px;
    width: 90%;
    margin: 10px auto;
  }
  .foodPrice{
    line-height: 20px;
    font-size: 14px;
    font-weight: 600;
    color: #d64b29;
  }
  .shopInfoWrapper {
    width: 20%;
    height: 500px;
    float: right;
    background-color: white;
  }
  .shopInfoItem{
    width: 90%;
    margin: 15px auto;
    padding-bottom: 10px;
    font-size: 13px;
    text-indent: 2em;
    border-bottom: 1px dashed #bbb;
    color: #78787a;
  }
  .shopInfoTitel{
    font-size: 13px;
    margin-left: 5%;
  }
  .shopInfoText{
    font-size: 12px;
    margin: 10px 0;
    width: 100%;
    text-align: center;
    color: #78787a;
  }
  .shoppingCartWrapper{
    position: fixed;
    bottom: 0;
    left: 50%;
    margin-left: 300px;
    width: 336px;
    /*height: 200px;*/
    /*border: 1px solid #999;*/
    z-index: 100;
    font-size: 14px;
  }
  .orderList{
    width: 336px;
    background-color: white;
    position: absolute;
    height: 45px;
    top: -45px
  }
  .orderTitle{
    color: #333;
    background-color: #e9e9e9;
    height: 45px;
    line-height: 45px;
    padding: 0 10px;
    font-size: 14px;
  }
  .cartItem{
    padding: 0 10px;
    font-size: 14px;
    height: 45px;
    line-height: 45px;
    border-bottom: 1px solid #e5e5e5;
    margin: 0 10px;
  }
  .foodItemName{
    width: 128px;
    float: left;
    text-overflow:ellipsis;
    white-space:nowrap;
    overflow:hidden;
    margin-right: 10px;
  }
  .foodItemNum{
    width: 100px;
    height: 45px;
    line-height: 45px;
    float: left;
  }
  .foodItemNum .el-input-number--mini .el-input-number__decrease, .el-input-number--mini .el-input-number__increase{
    width: 20px;
  }
  .foodItemNum .el-input-number--mini{
    width: 90px;
  }
  .foodItemNum .el-input-number--mini .el-input__inner{
    padding: 0 15px;
  }
  .foodItemPrice{
    float: right;
    color: #d64b29;
  }
  .cartFooter{
    background-color: #333;
    cursor: pointer;
    z-index: 1;
    position: relative;
    height: 50px;
    margin-top: -1px;
  }
  .cartIconWrapper{
    height: 50px;
    /*width: 60%;*/
    padding-left: 15px;
    line-height: 50px;
    float: left;
  }
  .cartTotal{
    float: right;
    font-size: 18px;
    font-weight: bold;
    color: white;
    height: 50px;
    line-height: 50px;
    width: 80px;
    margin-right: 50px;
    text-align: right;
  }
  .settleWrapper{
    float: right;
    width: 37%;
    line-height: 50px;
    text-align: center;
    font-size: 16px;
    font-weight: bold;
    height: 50px;
    color: white;
    background-color: rgb(247, 186, 42);
    display: none;
  }
  .noSettleWrapper{
    float: right;
    width: 37%;
    line-height: 50px;
    text-align: center;
    font-size: 16px;
    font-weight: bold;
    height: 50px;
    color: white;
    background-color: rgb(247, 186, 42);
  }
</style>
