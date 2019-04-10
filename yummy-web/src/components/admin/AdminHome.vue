<template>
  <div>
    <div class="nav">
      <div style="float: left">
        <img src="../../assets/img/logo.png" class="logoImg"/>
        <img src="../../assets/img/title.png" class="titleImg"/>
      </div>
      <div class="logout" @click="logout">
        <svg class="icon logoutIcon" aria-hidden="true" >
          <use xlink:href="#icon-logout"></use>
        </svg>
        退出
      </div>
    </div>
    <div class="adminMain">
      <div class="adminNavWrapper">
        <div class="adminNavTitle">
          <svg class="icon adminNavIcon" aria-hidden="true" >
            <use xlink:href="#icon-french-fries"></use>
          </svg>
          网站管理
        </div>
        <div class="adminNav" @click="handleNavSelect(1)">商店审批</div>
        <div class="adminNav" @click="handleNavSelect(2)">网站结算</div>
        <div class="adminNavTitle">
          <svg class="icon adminNavIcon" aria-hidden="true" >
            <use xlink:href="#icon-coffee"></use>
          </svg>
          网站运营
        </div>
        <div class="adminNav" @click="handleNavSelect(3)">用户数据</div>
        <div class="adminNav" @click="handleNavSelect(4)">财务状况</div>
      </div>
      <div class="adminContentWrapper">
        <div class="adminTitle" id="adminTitle">商店审批</div>
        <router-view></router-view>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'admin-home',
  data () {
    return {
      activeIndex: '1',
      searchText: '',
      rate: 3.0
    }
  },
  mounted () {
    if (this.$route.query.name !== null) {
      this.$router.push({path: '/admin/examination', query: {name: this.$route.query.name}})
    }
  },
  methods: {
    handleNavSelect: function (key) {
      let adminTitle = document.getElementById('adminTitle')
      switch (key) {
        case 1:
          adminTitle.innerText = '商店审批'
          this.$router.push({path: '/admin/examination', query: {name: this.$route.query.name}})
          break
        case 2:
          adminTitle.innerText = '网站结算'
          this.$router.push({path: '/admin/settleAccount', query: {name: this.$route.query.name}})
          break
        case 3:
          adminTitle.innerText = '用户数据'
          this.$router.push({path: '/admin/userManagement', query: {name: this.$route.query.name}})
          break
        case 4:
          adminTitle.innerText = '财务状况'
          this.$router.push({path: '/admin/finance', query: {name: this.$route.query.name}})
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
  .adminNavIcon{
    color: #008de8;
    width: 18px;
    height: 18px;
    vertical-align: middle;
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
  .adminMain{
  }
  .adminNavWrapper{
    float: left;
    width: 180px;
    height: 637px;
    background-color: white;
    margin-top: 20px;
    margin-left: 10%;
    padding-left: 10px;
    box-sizing: border-box;
  }
  .adminNavTitle{
    width: 100%;
    height: 40px;
    margin: 10px 0 0 0;
    box-sizing: border-box;
    border: 1px solid white;
    line-height: 40px;
    font-size: 16px;
    padding-left: 20px;
    font-weight: 600;
  }
  .adminNav{
    width: 100%;
    height: 30px;
    box-sizing: border-box;
    line-height: 30px;
    font-size: 14px;
    padding-left: 45px;
    cursor: pointer;
  }
  .adminNav:hover {
    color: #d64b29;
  }
  .adminContentWrapper{
    float: right;
    width: 65%;
    height: 637px;
    box-sizing: border-box;
    background-color: white;
    margin-top: 20px;
    margin-right: 10%;
  }
  .adminTitle{
    height: 20px;
    width: 60%;
    margin: 10px 20px;
    line-height: 20px;
    font-size: 16px;
    font-weight: 600;
    padding-bottom: 10px;
    padding-left: 10px;
    padding-top: 10px;
    border-bottom: 1px solid #bbb;
  }
</style>
