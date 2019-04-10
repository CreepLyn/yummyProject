<template>
  <div class="initialWrapper">
    <div class="topWrapper">
      <img src="../../assets/img/shop.png" class="classificationIcon">
      <div class="label">您的商店(待审核)：</div>
    </div>
    <div class="formWrapper">
      <el-form :model="shopInfo" width="650px">
        <el-form-item label="商店名称" :label-width="formLabelWidth">
          {{shopInfo.shopname}}
        </el-form-item>
        <el-form-item label="商店类型" :label-width="formLabelWidth">
          {{shopInfo.type}}
        </el-form-item>
        <el-form-item label="营业时间" :label-width="formLabelWidth">
          {{shopInfo.startTime}}--{{shopInfo.endTime}}
        </el-form-item>
        <el-form-item label="商店地址" :label-width="formLabelWidth">
          {{shopInfo.address}}
        </el-form-item>
        <el-form-item label="联系方式" :label-width="formLabelWidth">
          {{shopInfo.telephone}}
        </el-form-item>
        <el-form-item style="padding-left: 80px">
          <el-form :inline="true" :model="shopInfo" class="demo-form-inline">
            <el-form-item label="起送" >
              ￥{{shopInfo.miniPrice}}
            </el-form-item>
            <el-form-item label="配送" >
              ￥{{shopInfo.deliveryPrice}}
            </el-form-item>
          </el-form>
        </el-form-item>
        <el-form-item label="商店简介" :label-width="formLabelWidth">
          {{shopInfo.description}}
        </el-form-item>
        <el-form-item label="商店头像" :label-width="formLabelWidth">
          <img id="foodCover" :src="shopInfo.url" class="foodCoverImg"/>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
export default {
  name: 'mer-shop-checking',
  data () {
    return {
      shopInfo: {
        shopname: '',
        type: '',
        miniPrice: 0,
        deliveryPrice: 0,
        address: '',
        description: '',
        telephone: '',
        startTime: '',
        endTime: '',
        url: ''
      },
      formLabelWidth: '120px'
    }
  },
  mounted () {
    this.shopInfo = this.$route.query.shop
    this.shopInfo.startTime = this.$route.query.shop.startTime.toString()
    this.shopInfo.endTime = this.$route.query.shop.endTime.toString()
  }
}
</script>

<style scoped>
  .initialWrapper {
    width: 70%;
    margin: 20px 15%;
    background-color: white;
    /*padding-top: 15px;*/
  }
  .topWrapper{
    height: 80px;
    width: 100%;
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
  .formWrapper{
    width: 80%;
    padding-bottom: 15px;
  }
  .foodCoverImg{
    width: 200px;
    height: 150px;
  }
</style>
