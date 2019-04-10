<template>
  <div>
    <div class="topWrapper">
      <div class="topDiv">
        <svg class="icon orderIcon" aria-hidden="true" >
          <use xlink:href="#icon-ticket"></use>
        </svg>
      </div>
      <div class="topDiv">
        近三个月订单列表
      </div>
    </div>
    <div class="listWrapper">
      <el-table
        :data="orderList"
        height="800"
        style="width: 100%">
        <el-table-column type="expand">
          <template slot-scope="props">
            <div class="orderItem">
              <div class="orderInfo">
                <div class="orderInfoTitle">
                  <svg class="icon orderIcon" aria-hidden="true" >
                    <use xlink:href="#icon-piliangcaigouguanli"></use>
                  </svg>
                  订单详情
                </div>
                <div class="shopInfo">
                  <img :src="props.row.shop.url" class="shopImg"/>
                  <div class="shopText">
                    <p class="shopTitle">{{props.row.shop.shopname}}</p>
                    <p class="shopSpan">订单号：{{props.row.code}}</p>
                    <p class="shopSpan">商家电话：{{props.row.shop.telephone}}</p>
                  </div>
                </div>
                <div class="foodList">
                  <div class="foodItem" v-for="(food, index) in props.row.foodList" :key="index">
                    <p class="foodText foodName">{{food.name}}</p>
                    <p class="foodText foodNum">{{food.menge}}</p>
                    <p class="foodText foodPrice">￥{{food.price}}</p>
                  </div>
                </div>
                <div class="otherList">
                  <div class="foodItem">包装费：
                    <span class="priceSpan">￥{{props.row.shop.packingPrice}}</span>
                  </div>
                  <div class="foodItem">配送费：
                    <span class="priceSpan">￥{{props.row.shop.deliveryPrice}}</span>
                  </div>
                  <div class="foodItem">满减：
                    <span class="priceSpan offSpan">-￥{{props.row.off}}</span>
                  </div>
                  <div class="foodItem">红包：
                    <span class="priceSpan offSpan">-￥{{props.row.redoff}}</span>
                  </div>
                </div>
                <div class="finalWrapper">
                  实际支付：
                  <span class="priceText">￥{{props.row.price}}</span>
                </div>
                <div class="dispatchList">
                  <div class="dispatchItem">地址：{{props.row.address.description}}</div>
                  <div class="dispatchItem">姓名：{{props.row.address.name}}</div>
                  <div class="dispatchItem">电话：{{props.row.address.telephone}}</div>
                  <div class="dispatchItem">备注：{{props.row.remark}}</div>
                </div>
              </div>
              <div class="stateInfo">
                <div class="orderInfoTitle">
                  <svg class="icon orderIcon" aria-hidden="true" >
                    <use xlink:href="#icon-daifukuan"></use>
                  </svg>
                  订单状态
                </div>
                <div class="stateWrapper" :id="'stateWrapper' + props.$index">
                  <el-steps direction="vertical" finish-status="wait">
                    <el-step :title="state.description" v-for="(state, index) in props.row.stateList" :key="index"></el-step>
                  </el-steps>
                </div>
                <div class="timeWrapper">
                  <div class="timeItem" v-for="(state, index) in props.row.stateList" :key="index">{{state.time}}</div>
                </div>
                <div class="tipWrapper">本餐厅所列商家所有信息、服务均来自合作方，其信息的真实性、准确性、合法性和服务的质量由信息拥有者（商家）负责。</div>
              </div>
            </div>
          </template>
        </el-table-column>
        <el-table-column
          label="下单时间"
          width="100"
          prop="date">
        </el-table-column>
        <el-table-column
          label="订单内容"
          prop="shop.shopname">
        </el-table-column>
        <el-table-column
          label="支付金额(元)"
          width="120"
          prop="price">
        </el-table-column>
        <el-table-column
          label="订单状态"
          width="80"
          prop="state">
        </el-table-column>
        <el-table-column label="操作" width="250">
          <template slot-scope="props">
            <el-button
              size="mini"
              type="success"
              :disabled="props.row.state !== '待支付'"
              @click="handlePay(props.$index, props.row)">支付</el-button>
            <el-button
              size="mini"
              type="primary"
              :disabled="props.row.state !== '已送达'"
              @click="handleCheck(props.$index, props.row)">确认收货</el-button>
            <el-tooltip class="item" effect="dark" content="取消将收取一定费用！" placement="right" :show="props.row.state !== '已完成'">
              <el-button
                size="mini"
                type="warning"
                :disabled="props.row.state === '已完成' || props.row.state === '已取消'"
                @click="handleCancel(props.$index, props.row)">取消</el-button>
            </el-tooltip>
          </template>
        </el-table-column>
      </el-table>
    </div>
  </div>
</template>

<script>
import myFooter from '../MyBottom'
export default {
  name: 'cus-order',
  components: {
    myFooter
  },
  data () {
    return {
      activeIndex: '2',
      orderList: [{
        date: '2018.12.12',
        name: '【陈记粥铺】——小米粥1份',
        price: 12.88,
        state: '进行中',
        shop: {
          shopname: '肯德基',
          telephone: '1239865',
          packingPrice: 2,
          deliveryPrice: 2.5,
          off: 0,
          url: ''
        },
        code: 21874680724501,
        foodList: [
          {
            name: '小米粥',
            menge: 1,
            price: 9.99
          },
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
        address: {
          name: '张寅（女士）',
          telephone: 13805103211,
          description: '南京大学鼓楼校区桃源南楼'
        },
        remark: '无',
        stateList: [
          {
            time: '',
            description: ''
          },
          {
            time: '',
            description: ''
          },
          {
            time: '',
            description: ''
          }
        ]
      },
      {
        date: '2018.12.02',
        name: '【汉堡王】——厚牛堡1份',
        price: 32,
        state: '已完成',
        shop: {
          shopname: '肯德基',
          telephone: '1239865',
          packingPrice: 2,
          deliveryPrice: 2.5,
          off: 0,
          url: ''
        },
        code: 21874680724501,
        foodList: [{
          name: '小米粥',
          menge: 1,
          price: 9.99
        },
        {
          name: '南瓜粥',
          menge: 1,
          price: 12
        }],
        address: {
          name: '张寅（女士）',
          telephone: 13805103211,
          description: '南京大学鼓楼校区桃源南楼'
        },
        remark: '无',
        stateList: [
          {
            time: '',
            description: ''
          },
          {
            time: '',
            description: ''
          },
          {
            time: '',
            description: ''
          }
        ]
      }],
      stateList: [
        {
          desciption: '订单提交成功，等待付款',
          time: '2018-12-12 13:11'
        },
        {
          desciption: '支付成功，等待商家接单',
          time: '2018-12-12 13:11'
        },
        {
          desciption: '商家接单，美食制作中',
          time: '2018-12-12 13:11'
        },
        {
          desciption: '派送员已到点，正在配送中',
          time: '2018-12-12 13:11'
        },
        {
          desciption: '订单完成',
          time: '2018-12-12 13:11'
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
    this.$axios.post('/order/getAllOrderByUsername',
      {
        username: thisVue.$route.query.name
      })
      .then(function (response) {
        console.log(response.data)
        thisVue.orderList = response.data
        loading.close()
      })
      .catch(function (error) {
        console.log(error)
      })
  },
  methods: {
    handlePay (index, row) {
      console.log(index, row)
      let thisVue = this
      this.$axios.post('/order/doPayOrder',
        {
          id: row.id
        })
        .then(function (response) {
          console.log(response.data)
          if (response.data === 'SUCCESS') {
            thisVue.orderList[index].state = '进行中'
          } else {
            thisVue.$message.error('您的余额已不足！无法支付！')
          }
        })
        .catch(function (error) {
          console.log(error)
        })
    },
    handleCheck (index, row) {
      console.log(index, row)
      let thisVue = this
      this.$axios.post('/order/checkOrder',
        {
          id: row.id
        })
        .then(function (response) {
          console.log(response.data)
          thisVue.orderList[index].state = '已完成'
        })
        .catch(function (error) {
          console.log(error)
        })
    },
    handleCancel (index, row) {
      console.log(index, row)
      let thisVue = this
      this.$axios.post('/order/cancelOrder',
        {
          id: row.id
        })
        .then(function (response) {
          console.log(response.data)
          thisVue.orderList[index].state = '已取消'
        })
        .catch(function (error) {
          console.log(error)
        })
    }
  }
}
</script>

<style scoped>
  .el-step__icon{
    width: 20px;
    height: 20px;
  }
  .el-step.is-vertical .el-step__line{
    left: 10px;
  }
  .el-step.is-vertical .el-step__title{
    line-height: 18px;
  }
  .el-step__title{
    font-size: 14px;
  }
  .el-step__title.is-wait{
    color: #7e7e7e;
  }
  .el-step__line{
    background-color: #ffd161;
  }
  .el-step__head.is-wait{
    color: #ffd161;
  }
  .el-step__icon.is-text{
    border-color: #ffd161;
  }
  .orderIcon{
    width: 19px;
    height: 19px;
    color: #008de8;
  }
  .topWrapper{
    margin-top: 10px;
    width: 100%;
    height: 60px;
    padding: 0 15%;
    /*background-color: white;*/
    overflow: hidden;
    box-sizing: border-box;
  }
  .listWrapper{
    height: 800px;
    width: 70%;
    margin: 10px 15%;
    /*box-sizing: border-box;*/
    background-color: white;
    border: 1px solid #e5e5e5;
    padding: 17px;
    overflow: hidden;
  }
  .topDiv{
    float: left;
    height: 60px;
    line-height: 60px;
    margin-right: 10px;
    font-size: 18px;
    font-weight: 600;
  }
  .orderItem{
    width: 100%;
    /*height: 500px;*/
    overflow: hidden;
    border: 1px solid #bbb;
  }
  .orderInfo{
    float: left;
    width: 45%;
    height: 100%;
    border-right: 1px solid #bbb;
  }
  .orderInfoTitle{
    height: 19px;
    font-size: 16px;
    line-height: 19px;
    font-weight: 600;
    color: #787878;
    margin: 15px 20px;
  }
  .shopInfo{
    margin-left: 30px;
    height: 105px;
    padding-bottom: 15px;
    margin-right: 30px;
    border-bottom: 1px solid #ccc;
  }
  .shopImg{
    width: 90px;
    height: 90px;
    display: block;
    float: left;
  }
  .shopText{
    float: left;
    width: 200px;
    height: 90px;
    margin-left: 20px;
    /*border: 1px solid #999;*/
  }
  .shopTitle{
    font-size: 16px;
    color: black;
    width: 100%;
    font-weight: 600;
    text-overflow:ellipsis;
    white-space:nowrap;
    overflow:hidden;
  }
  .shopSpan{
    font-size: 14px;
    margin-top: 15px;
    color: #7e7e7e;
  }
  .foodList{
    margin: 20px 30px 0 30px;
    padding-bottom: 15px;
    border-bottom: 1px dashed #ccc;
  }
  .foodItem{
    height: 30px;
    width: 100%;
    /*border: 1px solid #55a532;*/
    font-size: 13px;
    line-height: 30px;
    padding: 0 20px;
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
  }
  .otherList{
    margin: 15px 30px;
    padding-bottom: 15px;
    border-bottom: 1px dashed #ccc;
  }
  .finalWrapper{
    margin: 15px 30px;
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
  .dispatchList{
    margin: 15px 30px;
  }
  .dispatchItem{
    width: 100%;
    height: 20px;
    /*border: 1px solid #55a532;*/
    padding: 0 20px;
    font-size: 12px;
    color: #7e7e7e;
    line-height: 20px;
  }
  .stateInfo{
    float: left;
    width: 55%;
    height: 100%;
  }
  .stateWrapper{
    padding: 20px 30px;
    height: 300px;
    width: 70%;
    float: left;
  }
  .timeWrapper{
    padding: 20px 30px;
    height: 300px;
    width: 30%;
    float: left;
  }
  .timeItem{
    height: 60px;
    font-size: 14px;
    color: #7e7e7e;
  }
  .tipWrapper{
    float: left;
    width: 100%;
    margin-top: 50px;
    border-top: 1px solid #bbb;
    padding: 20px 30px;
    font-size: 14px;
    color: #7e7e7e;
  }
</style>
