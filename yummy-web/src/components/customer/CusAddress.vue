<template>
  <div class="addressMain">
    <div class="addrCard" v-for="(address, index) in addressList" :key="index">
      <div class="topAddr">
        <div class="nameText">{{address.name}}&nbsp;&nbsp;{{address.sex}}</div>
        <div class="btnText">
          <span class="btnSpan" @click="modifyAddr(index)">修改</span>
          <span class="btnSpan" @click="deleteAddr(index)">取消</span>
        </div>
      </div>
      <div class="addrText">{{address.description}}</div>
      <div class="addrText">{{address.telephone}}</div>
    </div>
    <el-dialog
      title="修改地址"
      :visible.sync="dialogAddrVisible"
      width="50%">
      <el-form :model="addressList[addrIndex]" style="height: 220px" :label-position="labelPosition" label-width="130px">
        <el-form-item label="收货人姓名：" >
          <el-input v-model="addressList[addrIndex].name" autocomplete="off" style="width: 80%"></el-input>
        </el-form-item>
        <el-form-item label="收货人性别：" >
          <el-radio-group v-model="addressList[addrIndex].sex">
            <el-radio label="女士" value="女士"></el-radio>
            <el-radio label="男士" value="男士"></el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="收货人联系方式：" >
          <el-input v-model="addressList[addrIndex].telephone" autocomplete="off" style="width: 80%"></el-input>
        </el-form-item>
        <el-form-item label="详细收货地址：" >
          <el-input v-model="addressList[addrIndex].description" autocomplete="off" style="width: 80%"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
          <el-button @click="dialogAddrVisible = false">取 消</el-button>
          <el-button type="primary" @click="modify">确 定</el-button>
          </span>
    </el-dialog>
    <div class="addrCard newCard" @click="dialogNewAddrVisible = true">+&nbsp;添加新地址</div>
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
  </div>
</template>

<script>
export default {
  name: 'cus-addr',
  data () {
    return {
      labelPosition: 'right',
      addrIndex: 0,
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
          telephone: 18901862354,
          description: '123bobhbjlwdbflwae123bobhbjlwdbflwaedfdf',
          valid: false
        }
      ],
      newAddr: {
        name: '',
        sex: '',
        telephone: '',
        description: '',
        valid: true
      },
      dialogAddrVisible: false,
      dialogNewAddrVisible: false
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
    this.$axios.post('/address/getAllAddressByUsername',
      {
        username: thisVue.$route.query.name
      })
      .then(function (response) {
        console.log(response.data)
        thisVue.addressList = response.data
        loading.close()
      })
      .catch(function (error) {
        console.log(error)
      })
  },
  methods: {
    modifyAddr: function (index) {
      this.addrIndex = index
      this.dialogAddrVisible = true
    },
    modify: function () {
      let thisVue = this
      let loading = this.$loading({
        lock: true,
        text: 'Loading',
        spinner: 'el-icon-loading',
        background: 'rgba(0, 0, 0, 0.7)'
      })
      this.$axios.post('/address/modifyAddress',
        thisVue.addressList[thisVue.addrIndex])
        .then(function (response) {
          console.log(response.data)
          loading.close()
          if (response.data === 'SUCCESS') {
            thisVue.$message({
              message: '恭喜你，修改成功！',
              type: 'success'
            })
            thisVue.dialogAddrVisible = false
          }
        })
        .catch(function (error) {
          console.log(error)
        })
    },
    deleteAddr: function (index) {
      this.$confirm('此操作将永久删除该地址, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        let thisVue = this
        this.$axios.post('/address/deleteAddress',
          thisVue.addressList[index])
          .then(function (response) {
            console.log(response.data)
            if (response.data === 'SUCCESS') {
              thisVue.$message({
                message: '恭喜你，删除成功！',
                type: 'success'
              })
            }
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
          loading.close()
          thisVue.cancelAddNewAddr()
        })
        .catch(function (error) {
          console.log(error)
        })
    }
  }
}
</script>

<style scoped>
  .addressMain{
    padding: 20px;
  }
  .addrCard{
    width: 30%;
    height: 110px;
    margin-left: 10px;
    margin-right: 10px;
    margin-bottom: 10px;
    border: 1px solid #ccc;
    padding: 10px 15px;
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
  .topAddr{
    height: 20px;
    margin-bottom: 15px;
  }
  .nameText{
    float: left;
  }
  .btnText{
    float: right;
  }
  .btnSpan{
    font-size: 12px;
    color: #ccc;
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
  .newCard{
    background-color: #fafafa;
    line-height: 90px;
    text-align: center;
    cursor: pointer;
  }
  .newCard:hover {
    color: #d64b29;
  }
</style>
