import Vue from 'vue'
import Router from 'vue-router'
import Login from '@/components/Login'
import Person from '@/components/Person'

import CusHome from '@/components/customer/CusHome'
import CusList from '@/components/customer/CusList'
import CusOrder from '@/components/customer/CusOrder'
import CusInfo from '@/components/customer/CusInfo'
import CusAddress from '@/components/customer/CusAddress'
import CusAccount from '@/components/customer/CusAccount'
import CusBonus from '@/components/customer/CusBonus'
import ShopDetail from '@/components/customer/ShopDetail'
import CartOrder from '@/components/customer/CartOrder'

import MerHome from '@/components/merchant/MerHome'
import MerShop from '@/components/merchant/MerShop'
import MerShopInitial from '@/components/merchant/MerShopInitial'
import MerShopChecking from '@/components/merchant/MerShopChecking'
import MerOrder from '@/components/merchant/MerOrder'
import MerInfo from '@/components/merchant/MerInfo'
import MerAccount from '@/components/merchant/MerAccount'

import AdminHome from '@/components/admin/AdminHome'
import AdminExami from '@/components/admin/AdminExami'
import AdminSettle from '@/components/admin/AdminSettle'
import AdminUsers from '@/components/admin/AdminUsers'
import AdminFinance from '@/components/admin/AdminFinance'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'login',
      component: Login
    },
    {
      path: '/customer',
      name: 'cus-home',
      component: CusHome,
      children: [
        {
          path: 'home',
          name: 'cus-list',
          component: CusList
        },
        {
          path: 'shopDetail',
          name: 'shop-detail',
          component: ShopDetail
        },
        {
          path: 'orderCheck',
          name: 'cart-order',
          component: CartOrder
        },
        {
          path: 'myOrder',
          name: 'cus-order',
          component: CusOrder
        },
        {
          path: 'myInfo',
          name: 'cus-info',
          component: CusInfo,
          children: [
            {
              path: 'personal',
              name: 'cus-person',
              component: Person
            },
            {
              path: 'address',
              name: 'cus-addr',
              component: CusAddress
            },
            {
              path: 'account',
              name: 'cus-account',
              component: CusAccount
            },
            {
              path: 'bonus',
              name: 'cus-bonus',
              component: CusBonus
            }
          ]
        }
      ]
    },
    {
      path: '/merchant',
      name: 'mer-home',
      component: MerHome,
      children: [
        {
          path: 'myShop',
          name: 'mer-shop',
          component: MerShop
        },
        {
          path: 'myShopInitial',
          name: 'mer-shop-initial',
          component: MerShopInitial
        },
        {
          path: 'myShopChecking',
          name: 'mer-shop-checking',
          component: MerShopChecking
        },
        {
          path: 'myOrder',
          name: 'mer-order',
          component: MerOrder
        },
        {
          path: 'myInfo',
          name: 'mer-info',
          component: MerInfo,
          children: [
            {
              path: 'personal',
              name: 'mer-person',
              component: Person
            },
            {
              path: 'account',
              name: 'mer-account',
              component: MerAccount
            }
          ]
        }
      ]
    },
    {
      path: '/admin',
      name: 'admin-home',
      component: AdminHome,
      children: [
        {
          path: 'examination',
          name: 'admin-exami',
          component: AdminExami
        },
        {
          path: 'settleAccount',
          name: 'admin-settle',
          component: AdminSettle
        },
        {
          path: 'userManagement',
          name: 'admin-users',
          component: AdminUsers
        },
        {
          path: 'finance',
          name: 'admin-finance',
          component: AdminFinance
        }
      ]
    }
  ]
})
