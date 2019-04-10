<template>
  <div class="initialWrapper">
    <div class="topWrapper">
      <img src="../../assets/img/shop.png" class="classificationIcon">
      <div class="label">请先输入您的商店信息：</div>
    </div>
    <div class="formWrapper">
      <el-form :model="shopInfo" width="650px">
        <el-form-item label="商店名称" :label-width="formLabelWidth">
          <el-input type="text" v-model="shopInfo.shopname" style="width: 80%"></el-input>
        </el-form-item>
        <el-form-item label="商店类型" :label-width="formLabelWidth">
          <el-select v-model="shopInfo.type" placeholder="请选择活动区域">
            <el-option label="快餐便当" value="快餐便当"></el-option>
            <el-option label="特色菜系" value="特色菜系"></el-option>
            <el-option label="甜品饮品" value="甜品饮品"></el-option>
            <el-option label="果蔬生鲜" value="果蔬生鲜"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="营业时间" :label-width="formLabelWidth">
          <el-col :span="8">
            <el-time-picker type="fixed-time" placeholder="选择时间" v-model="shopInfo.startTime" style="width: 100%;" value-format="HH:mm:ss"></el-time-picker>
          </el-col>
          <el-col class="line" :span="2" style="text-align: center">---</el-col>
          <el-col :span="8">
            <el-time-picker type="fixed-time" placeholder="选择时间" v-model="shopInfo.endTime" style="width: 100%;" value-format="HH:mm:ss"></el-time-picker>
          </el-col>
        </el-form-item>
        <el-form-item label="商店地址" :label-width="formLabelWidth">
          <el-input v-model="shopInfo.address" autocomplete="off" style="width: 80%"></el-input>
        </el-form-item>
        <el-form-item label="联系方式" :label-width="formLabelWidth">
          <el-input v-model="shopInfo.telephone" autocomplete="off" style="width: 80%"></el-input>
        </el-form-item>
        <el-form-item style="padding-left: 80px">
          <el-form :inline="true" :model="shopInfo" class="demo-form-inline">
            <el-form-item label="起送" >
              <el-input v-model="shopInfo.miniPrice" placeholder="起送价" style="width: 40%"></el-input>
            </el-form-item>
            <el-form-item label="配送" >
              <el-input v-model="shopInfo.deliveryPrice" placeholder="配送费" style="width: 40%"></el-input>
            </el-form-item>
          </el-form>
        </el-form-item>
        <el-form-item label="商店简介" :label-width="formLabelWidth">
          <el-input type="textarea" v-model="shopInfo.description"></el-input>
        </el-form-item>
        <el-form-item label="商店头像" :label-width="formLabelWidth">
          <div class="uploadFoodCover">+
            <input id="fileInput" type="file" accept=".png,.jpg,.jpeg,image/png,image/jpg,image/jpeg" @change="coverLoad">
          </div>
          <img id="foodCover" src="" class="foodCoverImg"/>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitShop" class="submitBtn">提 交</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
export default {
  name: 'mer-shop-initial',
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
  methods: {
    coverLoad () {
      let thisVue = this
      let file = document.getElementById('fileInput').files[0]
      let reader = new FileReader()
      reader.readAsDataURL(file)
      reader.fileName = file.name
      reader.onload = function () {
        let foodCover = document.getElementById('foodCover')
        foodCover.src = this.result
        foodCover.style.display = 'block'
        thisVue.shopInfo.url = this.result
      }
    },
    submitShop () {
      let thisVue = this
      let name = this.$route.query.name
      let param = new FormData()
      param.append('file', this.getBlobBydataURI(thisVue.shopInfo.url, 'image/jpg'))
      param.append('shopname', thisVue.shopInfo.shopname)
      param.append('type', thisVue.shopInfo.type)
      param.append('miniPrice', thisVue.shopInfo.miniPrice)
      param.append('deliveryPrice', thisVue.shopInfo.deliveryPrice)
      param.append('address', thisVue.shopInfo.address)
      param.append('description', thisVue.shopInfo.description)
      param.append('telephone', thisVue.shopInfo.telephone)
      param.append('startTime', thisVue.shopInfo.startTime)
      param.append('endTime', thisVue.shopInfo.endTime)
      param.append('username', name)
      let config = {
        headers: {'Content-Type': 'multipart/form-data'}
      }
      let loading = this.$loading({
        lock: true,
        text: 'Loading',
        spinner: 'el-icon-loading',
        background: 'rgba(0, 0, 0, 0.7)'
      })
      thisVue.$axios.post('/shop/addShop', param, config)
        .then(function (response) {
          loading.close()
          if (response.data === 'EXIST') {
            thisVue.$message.error('该商店名已被注册！请重新修改！')
          } else {
            thisVue.$router.push({path: '/merchant/myShopChecking', query: {name: name, shop: thisVue.shopInfo}})
          }
        })
        .catch(function (error) {
          console.log(error)
        })
    },
    /* 将base64字符串转成图片 */
    getBlobBydataURI: function (dataURI, type) {
      let binary = atob(dataURI.split(',')[1])
      let array = []
      for (let i = 0; i < binary.length; i++) {
        array.push(binary.charCodeAt(i))
      }
      return new Blob([new Uint8Array(array)], { type: type })
    }
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
  .uploadFoodCover{
    width: 200px;
    height: 150px;
    border: 1px dashed #c0c4cc;
    line-height: 150px;
    font-size: 20px;
    text-align: center;
    cursor: pointer;
    float: left;
  }
  .uploadFoodCover:hover {
    color: #008de8;
    border-color: #008de8;
  }
  .uploadFoodCover input{
    cursor: pointer;
    width: 200px;
    height: 100%;
    position: absolute;
    top: 0;
    left: 0;
    opacity: 0;
    -ms-filter: 'alpha(opacity=0)';
  }
  .foodCoverImg{
    width: 200px;
    height: 150px;
    float: left;
    margin-left: 20px;
    display: none;
  }
  .submitBtn{
    width: 100px;
    float: right;
  }
</style>
