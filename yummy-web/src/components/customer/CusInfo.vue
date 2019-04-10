<template>
    <div class="infoDiv">
      <div class="infoNav">
        <div class="navTitle">
          <svg class="icon navIcon" aria-hidden="true" >
            <use xlink:href="#icon-wode1"></use>
          </svg>
          我的资料
        </div>
        <div class="navText" @click="changeInfoNav(1)">个人资料</div>
        <div class="navText" @click="changeInfoNav(2)">地址管理</div>
        <div class="navTitle">
          <svg class="icon navIcon" aria-hidden="true" >
            <use xlink:href="#icon-xunjia"></use>
          </svg>
          我的资产
        </div>
        <div class="navText" @click="changeInfoNav(3)">我的红包</div>
        <div class="navText" @click="changeInfoNav(4)">账户管理</div>
      </div>
      <div class="infoWrapper">
        <div class="infoName" id="infoName">个人资料</div>
        <router-view></router-view>
      </div>
    </div>
</template>

<script>
export default {
  name: 'cus-info',
  mounted () {
    if (this.$route.query.name !== null) {
      this.$router.push({path: '/customer/myInfo/personal', query: {name: this.$route.query.name}})
    }
  },
  methods: {
    changeInfoNav: function (index) {
      let infoName = document.getElementById('infoName')
      switch (index) {
        case 1:
          infoName.innerText = '个人资料'
          this.$router.push({path: '/customer/myInfo/personal', query: {name: this.$route.query.name}})
          break
        case 2:
          infoName.innerText = '地址管理'
          this.$router.push({path: '/customer/myInfo/address', query: {name: this.$route.query.name}})
          break
        case 3:
          infoName.innerText = '我的红包'
          this.$router.push({path: '/customer/myInfo/bonus', query: {name: this.$route.query.name}})
          break
        case 4:
          infoName.innerText = '账户管理'
          this.$router.push({path: '/customer/myInfo/account', query: {name: this.$route.query.name}})
          break
      }
    }
  }
}
</script>

<style scoped>
  .navIcon{
    width: 22px;
    height: 22px;
    color: #008de8;
    margin-right: 15px;
  }
  .infoDiv{
    height: 600px;
    width: 70%;
    margin: 30px 15%;
    padding: 17px;
    overflow: hidden;
  }
  .infoNav{
    width: 20%;
    height: 100%;
    float: left;
    background-color: white;
  }
  .navTitle{
    width: 100%;
    height: 30px;
    line-height: 30px;
    /*border: 1px solid #55a532;*/
    font-size: 18px;
    font-weight: 600;
    padding-left: 20px;
    margin: 10px 0;
  }
  .navText{
    height: 30px;
    width: 100%;
    line-height: 30px;
    font-size: 14px;
    padding-left: 61px;
    margin: 5px 0;
    cursor: pointer;
  }
  .navText:hover {
    color: #d64b29;
  }
  .infoWrapper{
    width: 78%;
    background-color: white;
    /*border: 1px solid #e5e5e5;*/
    height: 100%;
    float: right;
  }
  .infoName{
    font-size: 18px;
    font-weight: 600;
    color: black;
    margin: 15px 20px 0 20px;
    padding-left: 10px;
    padding-bottom: 15px;
    border-bottom: 1px solid #ccc;
  }
</style>
