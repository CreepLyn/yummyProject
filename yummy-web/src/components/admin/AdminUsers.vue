<template>
  <div>
    <div class="userManaWrapper">
      <div class="userChartsWrapper" id="userBar"></div>
      <div class="userChartsWrapper" id="userPie"></div>
    </div>
    <div class="userManaWrapper" id="userMap"></div>
  </div>
</template>

<script>
import 'echarts/map/js/china.js'

export default {
  name: 'admin-users',
  data () {
    return {
      user: {
        cosNum: 2, // 用户总数
        merNum: 3, // 商家总数
        outNum: 1, // 注销人数
        fastNum: 1,
        foodNum: 1,
        drinkNum: 2,
        fruitNum: 2
      },
      userMap: []
    }
  },
  created () {
    this.userMap = [
      {name: '北京', value: Math.round(Math.random() * 100)},
      {name: '天津', value: Math.round(Math.random() * 100)},
      {name: '上海', value: Math.round(Math.random() * 100)},
      {name: '重庆', value: Math.round(Math.random() * 100)},
      {name: '河北', value: 0},
      {name: '河南', value: Math.round(Math.random() * 100)},
      {name: '云南', value: 5},
      {name: '辽宁', value: 305},
      {name: '黑龙江', value: Math.round(Math.random() * 100)},
      {name: '湖南', value: 200},
      {name: '安徽', value: Math.round(Math.random() * 100)},
      {name: '山东', value: Math.round(Math.random() * 100)},
      {name: '新疆', value: Math.round(Math.random() * 100)},
      {name: '江苏', value: Math.round(Math.random() * 100)},
      {name: '浙江', value: Math.round(Math.random() * 100)},
      {name: '江西', value: Math.round(Math.random() * 100)},
      {name: '湖北', value: Math.round(Math.random() * 100)},
      {name: '广西', value: Math.round(Math.random() * 100)},
      {name: '甘肃', value: Math.round(Math.random() * 100)},
      {name: '山西', value: Math.round(Math.random() * 100)},
      {name: '内蒙古', value: Math.round(Math.random() * 100)},
      {name: '陕西', value: Math.round(Math.random() * 100)},
      {name: '吉林', value: Math.round(Math.random() * 100)},
      {name: '福建', value: Math.round(Math.random() * 100)},
      {name: '贵州', value: Math.round(Math.random() * 100)},
      {name: '广东', value: Math.round(Math.random() * 100)},
      {name: '青海', value: Math.round(Math.random() * 100)},
      {name: '西藏', value: Math.round(Math.random() * 100)},
      {name: '四川', value: Math.round(Math.random() * 100)},
      {name: '宁夏', value: Math.round(Math.random() * 100)},
      {name: '海南', value: Math.round(Math.random() * 100)},
      {name: '台湾', value: Math.round(Math.random() * 100)},
      {name: '香港', value: Math.round(Math.random() * 100)},
      {name: '澳门', value: Math.round(Math.random() * 100)}
    ]
  },
  mounted () {
    this.$nextTick(function () {
      let thisVue = this
      let loading = this.$loading({
        lock: true,
        text: 'Loading',
        spinner: 'el-icon-loading',
        background: 'rgba(0, 0, 0, 0.7)'
      })
      this.$axios.get('/admin/getInfo')
        .then(function (response) {
          console.log(response.data)
          thisVue.user = response.data
          thisVue.drawUserBar()
          thisVue.drawUserPie()
          thisVue.drawUserMap()
          loading.close()
        })
        .catch(function (error) {
          console.log(error)
        })
    })
  },
  methods: {
    drawUserBar: function () {
      let div = document.getElementById('userBar')
      let myChart = this.$echarts.init(div)
      let option = {
        title: {
          text: '用户人数柱状图'
        },
        color: ['#008de8'],
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'shadow'
          }
        },
        grid: {
          left: '3%',
          right: '4%',
          bottom: '3%',
          containLabel: true
        },
        xAxis: [
          {
            type: 'category',
            data: ['普通用户', '商家用户', '注销用户', '全部用户'],
            axisTick: {
              alignWithLabel: true
            }
          }
        ],
        yAxis: [
          {
            type: 'value'
          }
        ],
        series: [
          {
            name: '用户数量',
            type: 'bar',
            barWidth: '60%',
            data: [this.user.cosNum, this.user.merNum, this.user.outNum, this.user.cosNum + this.user.merNum + this.user.outNum]
          }
        ]
      }
      myChart.setOption(option)
    },
    drawUserPie: function () {
      let option = {
        title: {
          text: '商店类型分布饼图',
          subtext: '包括未审核',
          x: 'center'
        },
        tooltip: {
          trigger: 'item',
          formatter: '{a} <br/>{b} : {c} ({d}%)'
        },
        legend: {
          orient: 'vertical',
          left: 'left',
          data: ['快餐便当', '特色菜系', '甜品饮品', '果蔬生鲜']
        },
        series: [
          {
            name: '商店类型',
            type: 'pie',
            radius: '55%',
            center: ['50%', '60%'],
            data: [
              {value: this.user.fastNum, name: '快餐便当'},
              {value: this.user.foodNum, name: '特色菜系'},
              {value: this.user.drinkNum, name: '甜品饮品'},
              {value: this.user.fruitNum, name: '果蔬生鲜'}
            ],
            itemStyle: {
              emphasis: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: 'rgba(0, 0, 0, 0.5)'
              }
            }
          }
        ]
      }
      let div = document.getElementById('userPie')
      let myChart = this.$echarts.init(div)
      myChart.setOption(option)
    },
    drawUserMap: function () {
      let div = document.getElementById('userMap')
      let myChart = this.$echarts.init(div)
      let option = {
        title: {
          text: '用户分布图',
          x: 'center'
        },
        tooltip: {// 提示框组件。
          trigger: 'item'// 数据项图形触发，主要在散点图，饼图等无类目轴的图表中使用。
        },
        legend: {
          orient: 'horizontal', // 图例的排列方向
          x: 'left', // 图例的位置
          data: ['用户数']
        },

        visualMap: {// 颜色的设置  dataRange
          x: 'left',
          y: 'center',
          splitList: [
            {start: 70},
            {start: 50, end: 70},
            {start: 30, end: 50},
            {start: 20, end: 30},
            {start: 10, end: 20, label: '10 到 20（自定义label）'},
            {start: 5, end: 5, label: '5（自定义特殊颜色）', color: 'black'},
            {end: 10}
          ],
          //            min: 0,
          //            max: 2500,
          //            calculable : true,//颜色呈条状
          text: ['高', '低'], // 文本，默认为数值文本
          color: ['#E0022B', '#E09107', '#A3E00B']
        },
        toolbox: {// 工具栏
          show: true,
          orient: 'vertical', // 工具栏 icon 的布局朝向
          x: 'right',
          y: 'center',
          feature: {// 各工具配置项。
            mark: {show: true},
            dataView: {show: true, readOnly: true}, // 数据视图工具，可以展现当前图表所用的数据，编辑后可以动态更新。
            saveAsImage: {show: true}// 保存为图片。
          }
        },
        roamController: {// 控制地图的上下左右放大缩小 图上没有显示
          show: true,
          x: 'right',
          mapTypeControl: {
            'china': true
          }
        },
        series: [
          {
            name: '用户数',
            type: 'map',
            mapType: 'china',
            roam: false, // 是否开启鼠标缩放和平移漫游
            itemStyle: {// 地图区域的多边形 图形样式
              normal: {// 是图形在默认状态下的样式
                label: {
                  show: true, // 是否显示标签
                  textStyle: {
                    color: 'rgb(249, 249, 249)'
                  }
                }
              },
              emphasis: {// 是图形在高亮状态下的样式,比如在鼠标悬浮或者图例联动高亮时
                label: {show: true}
              }
            },
            top: '3%', // 组件距离容器的距离
            data: [
              {name: this.userMap[0].name, value: this.userMap[0].value},
              {name: this.userMap[1].name, value: this.userMap[1].value},
              {name: this.userMap[2].name, value: this.userMap[2].value},
              {name: this.userMap[3].name, value: this.userMap[3].value},
              {name: this.userMap[4].name, value: this.userMap[4].value},
              {name: this.userMap[5].name, value: this.userMap[5].value},
              {name: this.userMap[6].name, value: this.userMap[6].value},
              {name: this.userMap[7].name, value: this.userMap[7].value},
              {name: this.userMap[8].name, value: this.userMap[8].value},
              {name: this.userMap[9].name, value: this.userMap[9].value},
              {name: this.userMap[10].name, value: this.userMap[10].value},
              {name: this.userMap[11].name, value: this.userMap[11].value},
              {name: this.userMap[12].name, value: this.userMap[12].value},
              {name: this.userMap[13].name, value: this.userMap[13].value},
              {name: this.userMap[14].name, value: this.userMap[14].value},
              {name: this.userMap[15].name, value: this.userMap[15].value},
              {name: this.userMap[16].name, value: this.userMap[16].value},
              {name: this.userMap[17].name, value: this.userMap[17].value},
              {name: this.userMap[18].name, value: this.userMap[18].value},
              {name: this.userMap[19].name, value: this.userMap[19].value},
              {name: this.userMap[20].name, value: this.userMap[20].value},
              {name: this.userMap[21].name, value: this.userMap[21].value},
              {name: this.userMap[22].name, value: this.userMap[22].value},
              {name: this.userMap[23].name, value: this.userMap[23].value},
              {name: this.userMap[24].name, value: this.userMap[24].value},
              {name: this.userMap[25].name, value: this.userMap[25].value},
              {name: this.userMap[26].name, value: this.userMap[26].value},
              {name: this.userMap[27].name, value: this.userMap[27].value},
              {name: this.userMap[28].name, value: this.userMap[28].value},
              {name: this.userMap[29].name, value: this.userMap[29].value},
              {name: this.userMap[30].name, value: this.userMap[30].value},
              {name: this.userMap[31].name, value: this.userMap[31].value},
              {name: this.userMap[32].name, value: this.userMap[32].value},
              {name: this.userMap[33].name, value: this.userMap[33].value}
            ]
          }
        ]
      }
      myChart.setOption(option)
    }
  }
}
</script>

<style scoped>
  .userManaWrapper{
    width: 800px;
    height: 300px;
    margin: 15px;
    /*border: 1px solid #55a532;*/
  }
  .userChartsWrapper{
    float: left;
    width: 350px;
    height: 270px;
    margin-right: 20px;
    /*background-color: #e9e9e9;*/
  }
</style>
