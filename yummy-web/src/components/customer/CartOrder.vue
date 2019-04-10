<template>
  <div>
    <div class="cartOrderTopWrapper">
      <img src="../../assets/img/shop.png" class="classificationIcon">
      <div class="label">{{shop.shopname}}&nbsp;&nbsp;</div>
    </div>
    <div class="cartMain">
      <div class="orderDetail">
        <div class="foodItem orderCartTitle">
          <p class="foodText foodName">商品名称</p>
          <p class="foodText foodNum">数量</p>
          <p class="foodText foodPrice">单价</p>
        </div>
        <div class="orderFoodList" id="orderFoodList">
          <div class="foodItem" v-for="(food, index) in foodList" :key="index">
            <p class="foodText foodName">{{food.name}}</p>
            <p class="foodText foodNum">{{food.menge}}</p>
            <p class="foodText foodPrice">￥{{food.price}}</p>
          </div>
        </div>
        <div class="otherList">
          <div class="foodItem">包装费：
            <span class="priceSpan">￥{{shop.packingPrice}}</span>
          </div>
          <div class="foodItem">配送费：
            <span class="priceSpan">￥{{shop.deliveryPrice}}</span>
          </div>
          <div class="foodItem">满减：
            <span class="priceSpan offSpan">-￥{{shop.off}}</span>
          </div>
        </div>
        <div class="bonusItemWrapper">
          <span>红包：</span>
          <span v-show="redPacketList.length === 0" class="noRedText">暂无可用红包</span>
          <span v-show="redPacketList.length > 0" class="redBtn" @click="openRedDialog" id="bonusRed">选择红包</span>
        </div>
        <el-dialog
          title="选择红包"
          :visible.sync="dialogRedVisible"
          width="30%">
          <div>
            <div class="unuseRed" @click="unuseRedPacket" id="unuseBonus">不使用红包</div>
            <div :id="'bonusCard' + index" class="redCard" v-for="(redPacket, index) in redPacketList" :key="index">
              <div class="topWrapper" @click="choooseRedPacket(index)">
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
              </div>
              <div class="typeWrapper">
                限品类：{{redPacket.type}}
              </div>
              <div class="noAddMask noRedMask" v-show="!redPacket.valid">不可用</div>
            </div>
          </div>
          <span slot="footer" class="dialog-footer">
            <el-button @click="cancelBonus">取 消</el-button>
            <el-button type="primary" @click="checkBonus">确 定</el-button>
          </span>
        </el-dialog>
        <div class="finalWrapper">
          实际支付：
          <span class="priceText">￥{{totalPrice}}</span>
        </div>
      </div>
      <div class="cartDetail">
        <div class="cartTopWrapper">请选择您的收餐地址
          <div class="addNewWrapper" @click="dialogNewAddrVisible = true">
            <svg class="icon moneyIcon" aria-hidden="true" >
              <use xlink:href="#icon-xinzeng"></use>
            </svg>
            添加新的地址
          </div>
        </div>
        <el-dialog
          title="添加新地址"
          :visible.sync="dialogNewAddrVisible"
          width="50%">
          <el-form :model="newAddr" style="height: 220px" :label-position="labelPosition" label-width="130px">
            <el-form-item label="收货人姓名：" >
              <el-input v-model="newAddr.name" autocomplete="off" style="width: 80%"></el-input>
            </el-form-item>
            <el-form-item label="收货人性别：" >
              <el-radio-group v-model="newAddr.sex">
                <el-radio label="女士" value="女士"></el-radio>
                <el-radio label="男士" value="男士"></el-radio>
              </el-radio-group>
            </el-form-item>
            <el-form-item label="收货人联系方式：" >
              <el-input v-model="newAddr.telephone" autocomplete="off" style="width: 80%"></el-input>
            </el-form-item>
            <el-form-item label="详细收货地址：" >
              <el-input v-model="newAddr.description" autocomplete="off" style="width: 80%"></el-input>
            </el-form-item>
          </el-form>
          <span slot="footer" class="dialog-footer">
          <el-button @click="cancelAddNewAddr">取 消</el-button>
          <el-button type="primary" @click="addNewAddr">确 定</el-button>
          </span>
        </el-dialog>
        <div class="addressCard" v-for="(addr, index) in addressList" :key="index" :id="'addressCard' + index">
          <div class="addrInfo" @click="chooseAddr(index)">
            {{addr.name}}&nbsp;&nbsp;{{addr.sex}}&nbsp;&nbsp;：{{addr.telephone}}
          </div>
          <div class="addrInfo" @click="chooseAddr(index)">{{addr.description}}</div>
          <div class="noAddMask" v-show="!addr.valid">地址超出配送范围</div>
        </div>
        <div class="otherInfoWrapper">
          <el-form :label-position="labelPosition" label-width="80px" :model="other">
            <el-form-item label="订单备注">
              <el-input v-model="other.remark"></el-input>
            </el-form-item>
            <el-form-item label="配送方式">
              <div class="otherLabel">{{other.delivery}}</div>
            </el-form-item>
            <el-form-item label="支付方式">
              <el-select v-model="other.pay">
                <el-option label="在线支付" value="在线支付"></el-option>
                <el-option label="货到付款" value="货到付款"></el-option>
              </el-select>
            </el-form-item>
          </el-form>
        </div>
        <div class="cartBottomWrapper">
          <span>您需支付</span>
          <span class="priceText">￥{{totalPrice}}</span>
          <div class="payBtn" @click="submitOrder">确认下单</div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'cart-order',
  data () {
    return {
      labelPosition: 'right',
      shop: {
        shopname: '肯德基（广州路店）',
        rate: 3.8,
        miniPrice: 30,
        deliveryPrice: 9,
        time: 45,
        packingPrice: 4,
        off: 2
      },
      totalPrice: 15.99,
      totalPriceBack: 15.99,
      foodList: [
        {
          name: '小米粥',
          menge: 1,
          price: 9.99
        },
        {
          name: '南瓜粥',
          menge: 1,
          price: 12
        }
      ],
      addressList: [
        {
          name: '张寅',
          sex: '女士',
          telephone: 13805103211,
          description: '123bobhbjlwdbflwaedf',
          valid: true
        },
        {
          name: '张寅',
          sex: '女士',
          telephone: 13805103211,
          description: '123bobhbjlwdbflwaedf',
          valid: true
        },
        {
          name: '张寅',
          sex: '女士',
          telephone: 13805103211,
          description: '123bobhbjlwdbflwae123bobhbjlwdbflwaedfdf',
          valid: false
        }
      ],
      redPacketList: [
        {
          bonus: 5,
          price: 30,
          type: '快餐便当',
          telephone: '13808697241',
          valid: true
        },
        {
          bonus: 7,
          price: 45,
          type: '甜品饮品',
          telephone: '13808697242',
          valid: false
        },
        {
          bonus: 4,
          price: 25,
          type: '全品类',
          telephone: '13808697243',
          valid: true
        }
      ],
      other: {
        remark: '',
        delivery: '官方配送',
        pay: '在线支付'
      },
      dialogRedVisible: false,
      dialogNewAddrVisible: false,
      formLabelWidth: 100,
      newAddr: {
        name: '',
        sex: '',
        telephone: '',
        description: '',
        valid: true
      },
      addressIndex: 0,
      bonusIndex: -1
    }
  },
  mounted () {
    this.shop = this.$route.query.shopInfo
    this.foodList = this.$route.query.cart
    this.shop.packingPrice = this.shop.packingPrice * this.foodList.length
    this.calcuTotalPrice()
    let thisVue = this
    let loading = this.$loading({
      lock: true,
      text: 'Loading',
      spinner: 'el-icon-loading',
      background: 'rgba(0, 0, 0, 0.7)'
    })
    this.$axios.post('/address/getAllAddressByUsername',
      {
        username: thisVue.$route.query.name
      })
      .then(function (response) {
        console.log(response.data)
        thisVue.addressList = response.data
        let addressCard = document.getElementById('addressCard' + 0)
        addressCard.style.border = '1px solid #008de8'
        let orderFoodList = document.getElementById('orderFoodList')
        console.log(thisVue.foodList.length)
        orderFoodList.style.height = thisVue.foodList.length * 30 + 10 + 'px'
        loading.close()
      })
      .catch(function (error) {
        console.log(error)
      })
  },
  methods: {
    calcuTotalPrice: function () {
      let sum = 0.0
      for (let food in this.foodList) {
        sum += this.foodList[food].menge * this.foodList[food].price
      }
      let offList = this.$route.query.off
      let off = 0
      let price = 0
      for (let i in offList) {
        if (sum > Number(offList[i].price) && price < Number(offList[i].price)) {
          price = Number(offList[i].price)
          off = Number(offList[i].off)
        }
      }
      this.shop.off = off
      sum += this.shop.deliveryPrice + this.shop.packingPrice - off
      this.totalPrice = sum
      this.totalPriceBack = sum
      let thisVue = this
      this.$axios.post('/user/getRedPackge', {
        username: this.$route.query.name
      })
        .then(function (response) {
          console.log(response.data)
          thisVue.redPacketList = response.data
          for (let j in thisVue.redPacketList) {
            if (thisVue.redPacketList[j].type === thisVue.shop.type || thisVue.redPacketList[j].type === '全品类') {
              if (sum >= Number(thisVue.redPacketList[j].price)) {
                thisVue.redPacketList[j].valid = true
              } else {
                thisVue.redPacketList[j].valid = false
              }
            } else {
              thisVue.redPacketList[j].valid = false
            }
          }
        })
        .catch(function (error) {
          console.log(error)
        })
    },
    cancelAddNewAddr: function () {
      this.dialogNewAddrVisible = false
      this.newAddr.sex = ''
      this.newAddr.name = ''
      this.newAddr.telephone = ''
      this.newAddr.description = ''
    },
    addNewAddr: function () {
      this.addressList.push(this.newAddr)
      let thisVue = this
      let loading = this.$loading({
        lock: true,
        text: 'Loading',
        spinner: 'el-icon-loading',
        background: 'rgba(0, 0, 0, 0.7)'
      })
      this.$axios.post('/address/addAddress',
        {
          username: thisVue.$route.query.name,
          name: thisVue.newAddr.name,
          sex: thisVue.newAddr.sex,
          telephone: thisVue.newAddr.telephone,
          description: thisVue.newAddr.description
        })
        .then(function (response) {
          console.log(response.data)
          thisVue.addressList = response.data
          thisVue.cancelAddNewAddr()
          loading.close()
        })
        .catch(function (error) {
          console.log(error)
        })
    },
    chooseAddr: function (index) {
      this.addressIndex = index
      for (let i in this.addressList) {
        let addressCard = document.getElementById('addressCard' + i)
        if (i === index + '') {
          addressCard.style.border = '1px solid #008de8'
        } else {
          addressCard.style.border = '1px dashed #bbb'
        }
      }
    },
    openRedDialog: function () {
      this.dialogRedVisible = true
      if (this.bonusIndex >= 0) {
        for (let i in this.redPacketList) {
          let addressCard = document.getElementById('bonusCard' + i)
          if (i === this.bonusIndex + '') {
            addressCard.style.border = '1px solid #008de8'
          } else {
            addressCard.style.border = '1px solid #ccc'
          }
        }
      }
    },
    unuseRedPacket: function () {
      this.bonusIndex = -1
      for (let i in this.redPacketList) {
        document.getElementById('bonusCard' + i).style.border = '1px solid #ccc'
      }
      document.getElementById('unuseBonus').style.border = '1px solid #008de8'
    },
    choooseRedPacket: function (index) {
      this.bonusIndex = index
      for (let i in this.redPacketList) {
        let addressCard = document.getElementById('bonusCard' + i)
        if (i === index + '') {
          addressCard.style.border = '1px solid #008de8'
        } else {
          addressCard.style.border = '1px solid #ccc'
        }
      }
      document.getElementById('unuseBonus').style.border = '1px dashed #ccc'
    },
    cancelBonus: function () {
      this.dialogRedVisible = false
      this.bonusIndex = -1
    },
    checkBonus: function () {
      this.dialogRedVisible = false
      if (this.bonusIndex >= 0) {
        document.getElementById('bonusRed').innerText = '-￥' + this.redPacketList[this.bonusIndex].bonus
        this.totalPrice = this.totalPriceBack - this.redPacketList[this.bonusIndex].bonus
      } else {
        document.getElementById('bonusRed').innerText = '选择红包'
        this.totalPrice = this.totalPriceBack
      }
    },
    submitOrder: function () {
      this.$confirm('此操作将直接从余额扣除, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.payOrder('进行中')
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消支付'
        })
        this.payOrder('待支付')
      })
    },
    payOrder: function (state) {
      let thisVue = this
      let redid = -1
      if (this.bonusIndex >= 0) {
        redid = this.redPacketList[this.bonusIndex].id
      }
      let loading = this.$loading({
        lock: true,
        text: 'Loading',
        spinner: 'el-icon-loading',
        background: 'rgba(0, 0, 0, 0.7)'
      })
      this.$axios.post('/order/payOrder',
        {
          username: thisVue.$route.query.name,
          shopid: thisVue.shop.id,
          total: thisVue.totalPrice,
          addressid: thisVue.addressList[thisVue.addressIndex].id,
          off: thisVue.shop.off,
          state: state
        })
        .then(function (response) {
          console.log(response.data)
          loading.close()
          if (response.data === 'FAILURE') {
            thisVue.$message.error('您的余额已不足！无法支付！')
          } else {
            let code = response.data
            thisVue.$axios.post('/order/saveFoodList',
              {
                code: code,
                redid: redid,
                foodList: thisVue.foodList
              })
              .then(function (response) {
                console.log(response.data)
                if (response.data === 'SUCCESS') {
                  if (state === '待支付') {
                    thisVue.$message({
                      message: '恭喜你，下单成功！请尽快去支付！',
                      type: 'success'
                    })
                  } else {
                    thisVue.$message({
                      message: '恭喜你，下单成功！',
                      type: 'success'
                    })
                  }
                  thisVue.$router.replace({
                    path: '/customer/myOrder',
                    name: 'cus-order',
                    query: {
                      name: thisVue.$route.query.name
                    }
                  })
                }
              })
              .catch(function (error) {
                console.log(error)
              })
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
  .cartOrderTopWrapper{
    width: 70%;
    height: 20px;
    margin: 20px 15% 0 15%;
    /*background-color: white;*/
    /*border: 1px solid #e5e5e5;*/
    padding: 17px;
    overflow: hidden;
  }
  .classificationIcon{
    width: 25px;
    height: auto;
    margin-right: 10px;
    float: left;
  }
  .label{
    float: left;
    font-size: 14px;
    height: 20px;
    font-weight: 600;
    line-height: 20px;
    margin-right: 10px;
    cursor: pointer;
  }
  .cartMain{
    width: 72%;
    height: 600px;
    margin: 0 15% 30px 15%;
    /*background-color: white;*/
    /*border: 1px solid #e5e5e5;*/
    /*padding: 17px;*/
    overflow: hidden;
  }
  .orderDetail{
    float: left;
    width: 35%;
    height: 400px;
    background-color: white;
    border: 1px solid #e5e5e5;
    overflow: hidden;
  }
  .cartDetail{
    float: right;
    width: 60%;
    height: 100%;
    background-color: white;
    border: 1px solid #e5e5e5;
    overflow: hidden;
  }
  .orderFoodList{
    width: 80%;
    height: 70px;
    margin: 10px 10%;
    padding-bottom: 15px;
    border-bottom: 1px dashed #ccc;
    /*float: left;*/
  }
  .foodItem{
    height: 30px;
    width: 100%;
    /*border: 1px solid #55a532;*/
    font-size: 13px;
    line-height: 30px;
    margin: 5px 0;
  }
  .foodText{
    float: left;
    /*border: 1px solid #55a532;*/
  }
  .foodName{
    width: 60%;
  }
  .foodNum{
    width: 20%;
  }
  .foodPrice{
    width: 20%;
    text-align: right;
    line-height: 27px;
  }
  .orderCartTitle{
    margin: 10px 10% 0 10%;
    width: 80%;
    padding-bottom: 10px;
    border-bottom: 1px solid #ccc;
  }
  .otherList{
    /*float: left;*/
    width: 80%;
    height: 100px;
    margin: 10px 10%;
    padding-bottom: 15px;
    border-bottom: 1px dashed #ccc;
  }
  .bonusItemWrapper{
    /*float: left;*/
    width: 80%;
    height: 20px;
    margin: 10px 10%;
    padding-bottom: 15px;
    border-bottom: 1px dashed #ccc;
    line-height: 20px;
    font-size: 13px;
  }
  .noRedText{
    float: right;
    color: #7e7e7e;
  }
  .redBtn{
    float: right;
    cursor: pointer;
  }
  .redBtn:hover {
    color: #008de8;
  }
  .finalWrapper{
    margin: 15px 30px 15px 60px;
    padding-bottom: 15px;
    border-bottom: 1px solid #ccc;
    text-align: right;
  }
  .priceText{
    font-size: 20px;
    color: #d64b29;
    font-weight: 600;
  }
  .priceSpan{
    display: inline-block;
    float: right;
  }
  .offSpan{
    color: #ff5a5a;
  }
  .cartTopWrapper{
    width: 90%;
    margin: 15px 5%;
    height: 20px;
    line-height: 20px;
    font-size: 14px;
  }
  .addNewWrapper{
    float: right;
    color: #78787a;
    cursor: pointer;
  }
  .addressCard{
    width: 90%;
    margin: 0 5%;
    height: 70px;
    box-sizing: border-box;
    border: 1px dashed #bbb;
    padding: 10px 20px;
    position: relative;
  }
  .addressCard:hover {
    border-color: #d64b29;
  }
  .addrInfo{
    width: 100%;
    height: 25px;
    box-sizing: border-box;
    line-height: 25px;
    font-size: 14px;
    cursor: pointer;
  }
  .noAddMask{
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background-color: rgba(0,0,0,0.6);
    font-weight: 600;
    text-align: center;
    line-height: 70px;
    color: white;
    z-index: 3;
  }
  .otherInfoWrapper{
    width: 90%;
    margin: 20px 5%;
    padding-bottom: 20px;
    border-bottom: 1px dashed #bbb;
  }
  .otherLabel{
    width: 70px;
    height: 30px;
    margin-top: 5px;
    line-height: 30px;
    font-size: 13px;
    border-radius: 10px;
    text-align: center;
    background-color: #008de8;
    color: white;
  }
  .cartBottomWrapper{
    width: 90%;
    margin: 10px 5%;
    height: 60px;
    /*border: 1px solid #55a532;*/
    line-height: 60px;
    font-size: 14px;
    font-weight: 600;
  }
  .payBtn{
    float: right;
    width: 95px;
    height: 35px;
    margin-top: 13px;
    border-radius: 10px;
    line-height: 35px;
    background-color: #008de8;
    color: white;
    text-align: center;
    cursor: pointer;
  }
  .unuseRed{
    width: 95%;
    height: 30px;
    line-height: 10px;
    font-size: 12px;
    cursor: pointer;
    margin-left: 10px;
    margin-right: 10px;
    margin-bottom: 10px;
    border: 1px dashed #ccc;
    padding: 10px 15px;
    box-sizing: border-box;
    color: black;
  }
  .redCard{
    width: 95%;
    height: 110px;
    position: relative;
    margin-left: 10px;
    margin-right: 10px;
    margin-bottom: 10px;
    border: 1px solid #ccc;
    padding: 10px 15px;
    box-sizing: border-box;
    font-size: 12px;
    color: #7e7e7e;
    cursor: pointer;
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
  .noRedMask{
    line-height: 110px;
    z-index: 10;
    font-size: 22px;
    cursor: default;
  }
</style>
