<template>
  <div class="examinaWrapper">
    <div class="noShopText" v-show="!listShow">暂无商店</div>
    <div v-show="listShow">
      <div class="addrCard" v-for="(shop, index) in shopList" :key="index">
      <div class="leftWrapper">
        <img :src="shop.url" class="shopCoverImg"/>
      </div>
      <div class="rightWrapper">
        <div class="topAddr">
          <div class="nameText">{{shop.shopname}}</div>
          <div class="btnText">
            <span class="btnSpan" @click="openShopInfo(index)">查看</span>
            <span class="btnSpan" @click="examShop(0)">驳回</span>
          </div>
        </div>
        <div class="addrText">{{shop.address}}</div>
        <div class="addrText">{{shop.telephone}}</div>
      </div>
    </div>
    </div>
    <el-dialog title="商店信息" :visible.sync="dialogFormVisible">
      <el-form :model="shopList[shopIndex]" width="650px">
        <el-form-item label="商店名称：" :label-width="formLabelWidth">
          {{shopList[shopIndex].shopname}}
        </el-form-item>
        <el-form-item label="商店类型：" :label-width="formLabelWidth">
          {{shopList[shopIndex].type}}
        </el-form-item>
        <el-form-item label="营业时间：" :label-width="formLabelWidth">
          {{shopList[shopIndex].startTime}}
          ---
          {{shopList[shopIndex].endTime}}
        </el-form-item>
        <el-form-item label="商店地址：" :label-width="formLabelWidth">
          {{shopList[shopIndex].address}}
        </el-form-item>
        <el-form-item label="联系方式：" :label-width="formLabelWidth">
          {{shopList[shopIndex].telephone}}
        </el-form-item>
        <el-form-item style="padding-left: 50px">
          <el-form :inline="true" :model="shopList[shopIndex]" class="demo-form-inline">
            <el-form-item label="起送价：" >
              ￥{{shopList[shopIndex].miniPrice}}
            </el-form-item>
            <el-form-item label="配送费：" >
              ￥{{shopList[shopIndex].deliveryPrice}}
            </el-form-item>
          </el-form>
        </el-form-item>
        <el-form-item label="商店简介：" :label-width="formLabelWidth">
          {{shopList[shopIndex].description}}
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="examShop(0)">驳 回</el-button>
        <el-button type="primary" @click="examShop(1)">通 过</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: 'admin-exami',
  data () {
    return {
      shopList: [
        {
          shopname: '肯德基',
          phone: 13805103211,
          address: '123bobhbjlwdbflwaedf'
        }
      ],
      dialogFormVisible: false,
      form: {
        shopname: '',
        miniPrice: 0,
        deliveryPrice: 0,
        packingPrice: 0,
        type: '',
        address: '',
        startTime: '00:08:00',
        endTime: '21:00:00',
        telephone: '',
        description: ''
      },
      listShow: true,
      shopIndex: 0,
      formLabelWidth: '120px'
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
    this.$axios.get('/admin/getCheckingList')
      .then(function (response) {
        console.log(response.data)
        thisVue.shopList = response.data
        if (thisVue.shopList.length === 0) {
          thisVue.shopList[0] = thisVue.form
          thisVue.listShow = false
        }
        loading.close()
      })
      .catch(function (error) {
        console.log(error)
      })
  },
  methods: {
    openShopInfo: function (index) {
      this.shopIndex = index
      this.dialogFormVisible = true
    },
    examShop: function (index) {
      this.dialogFormVisible = false
      let thisVue = this
      let result = ''
      switch (index) {
        case 0:
          result = '已驳回'
          break
        case 1:
          result = '已通过'
          break
      }
      let loading = this.$loading({
        lock: true,
        text: 'Loading',
        spinner: 'el-icon-loading',
        background: 'rgba(0, 0, 0, 0.7)'
      })
      this.$axios.post('/admin/checkShop', {
        id: thisVue.shopList[thisVue.shopIndex].id,
        result: result
      })
        .then(function (response) {
          console.log(response.data)
          thisVue.shopIndex = 0
          thisVue.shopList.splice(thisVue.shopIndex, 1)
          if (thisVue.shopList.length === 0) {
            thisVue.shopList[0] = thisVue.form
            thisVue.listShow = false
          }
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
  .el-dialog__body{
    padding-top: 15px;
  }
  .el-form-item{
    line-height: 40px;
    padding-left: 15px;
    color: #000;
    font-weight: 600;
    margin-bottom: 10px;
  }
  .el-form-item__label{
    font-weight: 400;
  }
  .examinaWrapper{
    padding: 20px;
  }
  .noShopText{
    width: 100%;
    height: 100px;
    line-height: 100px;
    font-size: 20px;
    font-weight: bold;
    color: #7e7e7e;
    text-align: center;
  }
  .addrCard{
    width: 30%;
    height: 110px;
    margin-left: 10px;
    margin-right: 10px;
    margin-bottom: 10px;
    border: 1px dashed #ccc;
    padding: 13px 15px 10px 15px;
    box-sizing: border-box;
    float: left;
    font-size: 14px;
  }
  .addrCard:hover {
    border-color: #d64b29;
  }
  .addrCard:hover .btnSpan{
    color: #d64b29;
  }
  .leftWrapper{
    float: left;
  }
  .shopCoverImg{
    width: 80px;
    height: 80px;
  }
  .rightWrapper{
    width: 60%;
    height: 90px;
    float: right;
    overflow: hidden;
  }
  .topAddr{
    height: 20px;
    margin-bottom: 15px;
  }
  .nameText{
    width: 75px;
    float: left;
    font-weight: 600;
    text-overflow:ellipsis;
    white-space:nowrap;
    overflow:hidden;
  }
  .btnText{
    float: right;
  }
  .btnSpan{
    font-size: 12px;
    cursor: pointer;
  }
  .btnSpan:hover {
    color: #d64b29;
  }
  .addrText{
    margin-top: 5px;
    width: 100%;
    text-overflow:ellipsis;
    white-space:nowrap;
    overflow:hidden;
  }
</style>
