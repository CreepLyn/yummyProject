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
      <div class="shopTopRight editShop" @click="openEditWrapper">修改信息</div>
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
    <el-dialog title="商店信息" :visible.sync="dialogFormVisible">
      <el-form :model="shopInfo" width="650px">
        <el-form-item label="商店名称" :label-width="formLabelWidth">
          {{shopInfo.shopname}}
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
            <el-time-picker type="fixed-time" placeholder="选择时间" v-model="shopInfo.startTime" style="width: 100%;"  value-format="HH:mm:ss"></el-time-picker>
          </el-col>
          <el-col class="line" :span="2" style="text-align: center">---</el-col>
          <el-col :span="8">
            <el-time-picker type="fixed-time" placeholder="选择时间" v-model="shopInfo.endTime" style="width: 100%;"  value-format="HH:mm:ss"></el-time-picker>
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
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="modifyShop">确 定</el-button>
      </div>
    </el-dialog>
    <div class="shopMain">
      <div class="menuWrapper">
        <div class="menuTitle">
          <svg class="icon" aria-hidden="true" >
            <use xlink:href="#icon-fenlei"></use>
          </svg>
          &nbsp;&nbsp;菜单
        </div>
        <div class="menuItem" v-for="(menu, index) in menuList" :key="index" @click="chooseMenu(index)">
          {{menu.name}}
          <p style="float: right" @click="deleteMenuConfirmn">×</p>
        </div>
        <div class="menuItem newMenuItem" @click="openAddNewMenu">+&nbsp;添加新菜单</div>
        <el-dialog
          title="添加新菜单"
          :visible.sync="dialogNewMenuVisible"
          width="30%">
          <el-form :model="newMenu" style="height: 30px">
            <el-form-item label="新菜单名称：" :label-width="formLabelWidth">
              <el-input v-model="newMenu.name" autocomplete="off" style="width: 80%"></el-input>
            </el-form-item>
          </el-form>
          <span slot="footer" class="dialog-footer">
          <el-button @click="dialogNewMenuVisible = false">取 消</el-button>
          <el-button type="primary" @click="doAddMenu">确 定</el-button>
          </span>
        </el-dialog>
      </div>
      <div class="foodList">
        <div class="foodMenu">{{menuList[menuIndex].name}}</div>
        <div class="foodCard" v-for="(food, index) in menuList[menuIndex].foodList" :key="index" v-show="menuList[menuIndex].foodList.length > 0">
          <img :src="food.url" class="foodImg"/>
          <div class="foodNameTitle">{{food.name}}</div>
          <div class="foodBottom">
            <span class="foodPrice">￥{{food.price}}</span>
            <div style="float: right"><el-input-number size="mini" v-model="food.num"></el-input-number></div>
          </div>
          <div class="foodCardMask">
            <p class="foodMaskText" @click="modifyFood(index)">修改</p>
            <p class="foodMaskText" @click="deleteFoodConfirmn(index)">删除</p>
          </div>
          <el-dialog
            title="修改食品信息"
            :visible.sync="dialogFoodVisible"
            width="30%">
            <el-form :model="menuList[menuIndex].foodList[foodIndex]" style="height: 240px">
              <el-form-item label="食品名称：" :label-width="formLabelWidth">
                <el-input v-model="menuList[menuIndex].foodList[foodIndex].name" autocomplete="off" style="width: 80%"></el-input>
              </el-form-item>
              <el-form-item label="食品单价：" :label-width="formLabelWidth">
                <el-input v-model="menuList[menuIndex].foodList[foodIndex].price" autocomplete="off" style="width: 80%"></el-input>
              </el-form-item>
              <el-form-item label="食品数量：" :label-width="formLabelWidth">
                <el-input v-model="menuList[menuIndex].foodList[foodIndex].num" autocomplete="off" style="width: 80%"></el-input>
              </el-form-item>
              <el-form-item label="食品图片：" :label-width="formLabelWidth">
                <div class="uploadFoodCover">+
                  <input id="uploadFoodCoverInput" type="file" accept=".png,.jpg,.jpeg,image/png,image/jpg,image/jpeg" @change="coverLoadOld">
                </div>
                <img id="foodCoverOld" :src="menuList[menuIndex].foodList[foodIndex].url" class="foodCoverImg oldFood"/>
              </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
          <el-button @click="dialogFoodVisible = false">取 消</el-button>
          <el-button type="primary" @click="doModifyFood">确 定</el-button>
          </span>
          </el-dialog>
        </div>
        <div class="foodCard addNewFood" @click="openAddNewFood">+&nbsp;添加新食品</div>
        <el-dialog
          title="添加新食品"
          :visible.sync="dialogNewFoodVisible"
          width="30%">
          <el-form :model="newFood" style="height: 240px">
            <el-form-item label="新食品名称：" :label-width="formLabelWidth">
              <el-input v-model="newFood.name" autocomplete="off" style="width: 80%"></el-input>
            </el-form-item>
            <el-form-item label="新食品单价：" :label-width="formLabelWidth">
              <el-input v-model="newFood.price" autocomplete="off" style="width: 80%"></el-input>
            </el-form-item>
            <el-form-item label="新食品数量：" :label-width="formLabelWidth">
              <el-input v-model="newFood.num" autocomplete="off" style="width: 80%"></el-input>
            </el-form-item>
            <el-form-item label="新食品图片：" :label-width="formLabelWidth">
              <div class="uploadFoodCover">+
                <input id="fileInput" type="file" accept=".png,.jpg,.jpeg,image/png,image/jpg,image/jpeg" @change="coverLoad">
              </div>
              <img id="foodCover" src="" class="foodCoverImg"/>
            </el-form-item>
          </el-form>
          <span slot="footer" class="dialog-footer">
          <el-button @click="dialogNewFoodVisible = false">取 消</el-button>
          <el-button type="primary" @click="doAddFood">确 定</el-button>
          </span>
        </el-dialog>
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
          <div class="shopInfoText" v-for="(offITem, index) in offList" :key="index">满{{offITem.price}}减{{offITem.off}}
            <span style="margin-left: 10px;cursor: pointer" @click="deleteOffConfirm(index)">×</span>
          </div>
          <div class="shopInfoText addOffBtn" @click="openAddNewOff">+添加满减</div>
          <el-dialog
            title="添加新满减"
            :visible.sync="dialogNewOffVisible"
            width="30%">
            <el-form :inline="true" :model="newOff" class="demo-form-inline">
              <el-form-item label="满额">
                <el-input v-model="newOff.price" placeholder="满额"></el-input>
              </el-form-item>
              <el-form-item label="优惠">
                <el-input v-model="newOff.off" placeholder="优惠"></el-input>
              </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
          <el-button @click="dialogNewOffVisible = false">取 消</el-button>
          <el-button type="primary" @click="doAddOff">确 定</el-button>
          </span>
          </el-dialog>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'mer-shop',
  data () {
    return {
      shop: {
        shopname: '肯德基（广州路店）',
        rate: 3.8,
        miniPrice: 30,
        deliveryPrice: 9,
        packingPrice: 2.5,
        startTime: '00:08:00',
        endTime: '20:00:00'
      },
      menuList: [
        {
          name: '热销',
          foodList: []
        //     {
        //       name: '原味吮指鸡',
        //       price: 11,
        //       num: 0
        //     },
        //     {
        //       name: '黄金鸡块',
        //       price: 11.5,
        //       num: 0
        //     },
        //     {
        //       name: '香辣鸡翅',
        //       price: 10,
        //       num: 0
        //     },
        //     {
        //       name: '新奥尔良鸡腿堡',
        //       price: 17,
        //       num: 0
        //     },
        //     {
        //       name: '原味吮指鸡',
        //       price: 11,
        //       num: 0
        //     }
        //   ]
        }
        // {
        //   name: '汉堡',
        //   foodList: [
        //     {
        //       name: '原味吮指鸡',
        //       price: 11,
        //       num: 0
        //     }
        //   ]
        // },
        // {
        //   name: '小吃',
        //   foodList: [
        //     {
        //       name: '原味吮指鸡',
        //       price: 11,
        //       num: 0
        //     }
        //   ]
        // },
        // {
        //   name: '优惠套餐',
        //   foodList: [
        //     {
        //       name: '原味吮指鸡',
        //       price: 11,
        //       num: 0
        //     }
        //   ]
        // }
      ],
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
      shopInfo: {
        shopname: '肯德基（广州路店）',
        rate: 3.8,
        miniPrice: 30,
        deliveryPrice: 9,
        packingPrice: 2.5,
        startTime: '00:08:00',
        endTime: '20:00:00'
      },
      menuIndex: 0,
      foodIndex: 0,
      dialogFormVisible: false,
      dialogNewMenuVisible: false,
      dialogNewFoodVisible: false,
      dialogFoodVisible: false,
      dialogNewOffVisible: false,
      newMenu: {
        name: '',
        shopid: 0
      },
      newFood: {
        name: '',
        price: 0,
        num: 10
      },
      newOff: {
        off: '',
        price: '',
        shopid: ''
      },
      form: {
        name: '肯德基（广州路店）',
        phone: '12423461',
        region: '',
        date1: '',
        date2: '',
        delivery: false,
        type: [],
        resource: '',
        desc: '',
        address: '',
        startMoney: 30,
        dispatching: 9
      },
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
    let name = this.$route.query.name
    this.$axios.post('/shop/getShopInfo', {
      username: name
    })
      .then(function (response) {
        if (response.data.state === '待审核') {
          loading.close()
          thisVue.$router.push({path: '/merchant/myShopChecking', query: {name: name, shop: response.data}})
        } else {
          let menuList = response.data.menuList
          let offList = response.data.offList
          let result = response.data
          thisVue.shop = result
          thisVue.menuList = menuList
          thisVue.offList = offList
          thisVue.newMenu.shopid = result.id
          thisVue.newOff.shopid = result.id
          thisVue.shopInfo = result
          thisVue.shopInfo.menuList = null
          loading.close()
        }
      })
      .catch(function (error) {
        console.log(error)
      })
  },
  methods: {
    chooseMenu: function (index) {
      this.menuIndex = index
    },
    openEditWrapper () {
      this.dialogFormVisible = true
    },
    modifyShop () {
      this.dialogFormVisible = false
      let thisVue = this
      let loading = this.$loading({
        lock: true,
        text: 'Loading',
        spinner: 'el-icon-loading',
        background: 'rgba(0, 0, 0, 0.7)'
      })
      this.$axios.post('/shop/modifyShop', thisVue.shopInfo)
        .then(function (response) {
          console.log(response.data)
          thisVue.shop.shopname = thisVue.shopInfo.shopname
          thisVue.shop.type = thisVue.shopInfo.type
          thisVue.shop.address = thisVue.shopInfo.address
          thisVue.shop.description = thisVue.shopInfo.description
          thisVue.shop.startTime = thisVue.shopInfo.startTime
          thisVue.shop.endTime = thisVue.shopInfo.endTime
          thisVue.shop.miniPrice = thisVue.shopInfo.miniPrice
          thisVue.shop.deliveryPrice = thisVue.shopInfo.deliveryPrice
          thisVue.shop.telephone = thisVue.shopInfo.telephone
          loading.close()
        })
        .catch(function (error) {
          console.log(error)
        })
    },
    openAddNewMenu () {
      this.dialogNewMenuVisible = true
      this.newMenu.name = ''
    },
    openAddNewFood () {
      this.dialogNewFoodVisible = true
      this.newFood.name = ''
      this.newFood.num = '10'
      this.newFood.price = '0.0'
      // document.getElementById('fileInput').files = []
      document.getElementById('foodCover').style.display = 'none'
    },
    openAddNewOff () {
      this.dialogNewOffVisible = true
      this.newOff.price = ''
      this.newOff.off = ''
    },
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
        thisVue.newFood.url = this.result
      }
    },
    doAddFood () {
      this.dialogNewFoodVisible = false
      let thisVue = this
      let param = new FormData()
      param.append('file', this.getBlobBydataURI(thisVue.newFood.url, 'image/jpg'))
      param.append('name', thisVue.newFood.name)
      param.append('num', thisVue.newFood.num)
      param.append('price', thisVue.newFood.price)
      param.append('menuid', thisVue.menuList[thisVue.menuIndex].id)
      let config = {
        headers: {'Content-Type': 'multipart/form-data'}
      }
      let loading = this.$loading({
        lock: true,
        text: 'Loading',
        spinner: 'el-icon-loading',
        background: 'rgba(0, 0, 0, 0.7)'
      })
      thisVue.$axios.post('/shop/addFood', param, config)
        .then(function (response) {
          console.log(response.data)
          let result = response.data
          thisVue.menuList[thisVue.menuIndex].foodList.push(result)
          loading.close()
        })
        .catch(function (error) {
          console.log(error)
        })
    },
    modifyFood (index) {
      this.foodIndex = index
      this.dialogFoodVisible = true
    },
    coverLoadOld () {
      let thisVue = this
      let file = document.getElementById('uploadFoodCoverInput').files[0]
      let reader = new FileReader()
      reader.readAsDataURL(file)
      reader.fileName = file.name
      reader.onload = function () {
        let foodCover = document.getElementById('foodCoverOld')
        foodCover.src = this.result
        thisVue.menuList[thisVue.menuIndex].foodList[thisVue.foodIndex].url = this.result
      }
    },
    doModifyFood () {
      this.dialogFoodVisible = false
      let thisVue = this
      let param = new FormData()
      param.append('file', this.getBlobBydataURI(thisVue.menuList[thisVue.menuIndex].foodList[thisVue.foodIndex].url, 'image/jpg'))
      param.append('name', thisVue.menuList[thisVue.menuIndex].foodList[thisVue.foodIndex].name)
      param.append('num', thisVue.menuList[thisVue.menuIndex].foodList[thisVue.foodIndex].num)
      param.append('price', thisVue.menuList[thisVue.menuIndex].foodList[thisVue.foodIndex].price)
      param.append('id', thisVue.menuList[thisVue.menuIndex].foodList[thisVue.foodIndex].id)
      let config = {
        headers: {'Content-Type': 'multipart/form-data'}
      }
      let loading = this.$loading({
        lock: true,
        text: 'Loading',
        spinner: 'el-icon-loading',
        background: 'rgba(0, 0, 0, 0.7)'
      })
      thisVue.$axios.post('/shop/modifyFood', param, config)
        .then(function (response) {
          console.log(response.data)
          loading.close()
        })
        .catch(function (error) {
          console.log(error)
        })
    },
    deleteMenuConfirmn () {
      this.$confirm('此操作将永久删除该菜单以及菜单下的食物, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        let thisVue = this
        this.$axios.post('/shop/deleteMenu', {
          id: thisVue.menuList[thisVue.menuIndex].id
        })
          .then(function (response) {
            console.log(response.data)
            thisVue.$message({
              type: 'success',
              message: '删除成功!'
            })
          })
          .catch(function (error) {
            console.log(error)
          })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
    },
    deleteFoodConfirmn (index) {
      this.$confirm('此操作将永久删除该食物, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        let thisVue = this
        this.$axios.post('/shop/deleteFood', {
          id: thisVue.menuList[thisVue.menuIndex].foodList[index].id
        })
          .then(function (response) {
            console.log(response.data)
            thisVue.$message({
              type: 'success',
              message: '删除成功!'
            })
          })
          .catch(function (error) {
            console.log(error)
          })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
    },
    deleteOffConfirm (index) {
      this.$confirm('此操作将永久删除该满减, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        let thisVue = this
        this.$axios.post('/shop/deleteOff', {
          shopid: thisVue.shop.id,
          price: thisVue.offList[index].price
        })
          .then(function (response) {
            console.log(response.data)
            thisVue.$message({
              type: 'success',
              message: '删除成功!'
            })
          })
          .catch(function (error) {
            console.log(error)
          })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
    },
    doAddMenu () {
      let thisVue = this
      let loading = this.$loading({
        lock: true,
        text: 'Loading',
        spinner: 'el-icon-loading',
        background: 'rgba(0, 0, 0, 0.7)'
      })
      this.$axios.post('/shop/addMenu', thisVue.newMenu)
        .then(function (response) {
          console.log(response.data)
          thisVue.menuList.push(response.data)
          thisVue.dialogNewMenuVisible = false
          loading.close()
        })
        .catch(function (error) {
          console.log(error)
        })
    },
    doAddOff () {
      let thisVue = this
      this.$axios.post('/shop/addOff', thisVue.newOff)
        .then(function (response) {
          console.log(response.data)
          thisVue.offList.push(response.data)
          thisVue.dialogNewOffVisible = false
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
  .editShop{
    height: 60px;
    line-height: 60px;
    font-size: 14px;
    text-align: center;
    border-left: 1px dashed #78787a;
    margin: 20px 0;
    cursor: pointer;
  }
  .editShop:hover {
    color: #d64b29;
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
  .newMenuItem{
    border: 1px dashed #bbb;
  }
  .newMenuItem:hover {
    border-color: #d64b29;
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
    position: relative;
  }
  .foodCard:hover{
    border-color: #d64b29;
  }
  .foodCardMask{
    position: absolute;
    height: 200px;
    width: 100%;
    top: 0;
    left: 0;
    background-color: rgba(0, 0, 0, 0.5);
    z-index: 3;
    opacity: 0;
  }
  .foodMaskText{
    width: 50%;
    height: 100%;
    line-height: 200px;
    float: left;
    font-size: 16px;
    color: white;
    text-align: center;
    cursor: pointer;
  }
  .foodMaskText:hover {
    color: #008de8;
  }
  .foodCard:hover .foodCardMask{
    opacity: 1;
  }
  .addNewFood{
    line-height: 200px;
    text-align: center;
    background-color: #fafafa;
    font-size: 14px;
    cursor: pointer;
  }
  .addNewFood:hover {
    color: #d64b29;
  }
  .uploadFoodCover{
    width: 100px;
    height: 80px;
    border: 1px dashed #c0c4cc;
    line-height: 80px;
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
    width: 100px;
    height: 100%;
    position: absolute;
    top: 0;
    left: 0;
    opacity: 0;
    -ms-filter: 'alpha(opacity=0)';
  }
  .foodCoverImg{
    width: 100px;
    height: 80px;
    float: left;
    margin-left: 20px;
    display: none;
  }
  .oldFood{
    display: block;
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
  .addOffBtn{
    width: 70%;
    margin: 10px auto;
    height: 20px;
    line-height: 20px;
    border: 1px dashed #bbb;
    cursor: pointer;
  }
  .addOffBtn:hover {
    border-color: #d64b29;
    color: #d64b29;
  }
</style>
