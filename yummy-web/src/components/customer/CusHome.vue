<template>
  <div>
    <div class="nav">
      <div style="float: left">
      <img src="../../assets/img/logo.png" class="logoImg"/>
      <img src="../../assets/img/title.png" class="titleImg"/>
      </div>
      <div class="navTab">
        <el-menu :default-active="activeIndex" class="el-menu-demo" mode="horizontal" @select="handleNavSelect"
                 background-color="#d64b29"
                 text-color="#fff"
                 active-text-color="#fff"
                 height="70px">
          <el-menu-item index="1">首页</el-menu-item>
          <el-menu-item index="2" >我的订单</el-menu-item>
          <el-menu-item index="3">个人中心</el-menu-item>
        </el-menu>
      </div>
      <div class="logout" @click="logout">
        <svg class="icon logoutIcon" aria-hidden="true" >
          <use xlink:href="#icon-logout"></use>
        </svg>
        退出
      </div>
    </div>
    <router-view></router-view>
    <bottom-nav></bottom-nav>
  </div>
</template>

<script>
import bottomNav from '../MyBottom'
export default {
  name: 'cus-home',
  components: {
    bottomNav
  },
  data () {
    return {
      activeIndex: '1',
      searchText: '',
      rate: 3.0
    }
  },
  mounted () {
    if (this.$route.query.name !== null && this.$route.query.initial !== 'no') {
      this.$router.push({path: '/customer/home', query: {name: this.$route.query.name}})
    }
  },
  methods: {
    handleNavSelect: function (key, keyPath) {
      switch (key) {
        case '1':
          this.$router.push({path: '/customer/home', query: {name: this.$route.query.name}})
          break
        case '2':
          this.$router.push({path: '/customer/myOrder', query: {name: this.$route.query.name}})
          break
        case '3':
          this.$router.push({path: '/customer/myInfo', query: {name: this.$route.query.name}})
          break
      }
    },
    logout: function () {
      this.$router.replace({path: '/', query: {name: this.$route.query.name}})
    }
  }
}
</script>

<style scoped>
  .logoutIcon{
    color: white;
  }
  .moneyIcon{
    width: 13px;
    height: 13px;
  }
  .nav{
    height: 70px;
    width: 100%;
    background-color: #d64b29;
    box-sizing: border-box;
  }
  .logoImg{
    margin-left: 35px;
    margin-right: 20px;
    vertical-align: middle;
  }
  .titleImg{
    height: 35px;
    width: auto;
    margin-top: 6px;
    vertical-align: middle;
  }
  .navTab{
    width: 320px;
    height: 70px;
    float: left;
    /*padding-top: 5px;*/
    margin-left: 80px;
    text-align: center;
  }
  .el-menu--horizontal>.el-menu-item{
    width: 100px;
    height: 70px;
    line-height: 70px;
  }
  .logout{
    float: right;
    color: white;
    height: 70px;
    line-height: 70px;
    margin-right: 30px;
    cursor: pointer;
  }
</style>
