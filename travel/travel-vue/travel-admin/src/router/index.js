import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

/* Layout */
import Layout from '@/layout'

export const constantRoutes = [
  {
    path: '/login',
    component: () => import('@/views/login/index'),
    hidden: true
  },
  {
    path: '/addUser',
    component: () => import('@/views/system/addUser'),
    hidden: true
  },
  
  {
    path: '/404',
    component: () => import('@/views/404'),
    hidden: true
  },

  {
    path: '/',
    component: Layout,
    redirect: '/index',
    children: [{
      path: 'index',
      name: '系统首页',
      component: () => import('@/views/index/index'),
      meta: {title: '系统首页', icon: 'el-icon-s-home'}
    }]
  },

  {
    path: '/baseInfo',
    component: Layout,
    redirect: '/baseInfo/goods',
    name: 'BaseInfo',
    meta: {title: '基础信息', icon: 'el-icon-s-help'},
    children: [
      {
        path: 'goods',
        name: 'Goods',
        component: () => import('@/views/base_info/goods'),
        meta: {title: '商品信息', icon: 'table'}
      },
      {
        path: 'customer',
        name: 'Customer',
        component: () => import('@/views/base_info/customer'),
        meta: {title: '客户信息', icon: 'el-icon-s-custom'}
      },
      {
        path: 'supplier',
        name: 'Supplier',
        component: () => import('@/views/base_info/supplier'),
        meta: {title: '供应商信息', icon: 'el-icon-s-check'}
      }
    ]
  },
  {
    path: '/stock',
    component: Layout,
    redirect: '/stock/table',
    name: 'Stock',
    meta: {title: '进货管理', icon: 'el-icon-folder-add'},
    children: [
      {
        path: 'index',
        name: 'Add',
        component: () => import('@/views/stock/index'),
        meta: {title: '进货记录', icon: 'el-icon-notebook-2'}
      },
      {
        path: 'add',
        name: 'Add',
        component: () => import('@/views/stock/add'),
        meta: {title: '商品入库', icon: 'el-icon-shopping-cart-full'}
      }
    ]
  },

  {
    path: '/sell',
    component: Layout,
    redirect: '/sell/record',
    name: 'Sell',
    meta: {title: '销售管理', icon: 'el-icon-folder-remove'},
    children: [
      {
        path: 'record',
        name: 'Record',
        component: () => import('@/views/sell/record'),
        meta: {title: '销售记录', icon: 'el-icon-notebook-2'}
      },
      {
        path: 'returnGoods',
        name: 'ReturnGoods',
        component: () => import('@/views/sell/return_goods_record.vue'),
        meta: {title: '退货记录', icon: 'el-icon-shopping-cart-2'}
      },
      {
        path: 'out',
        name: 'Out',
        component: () => import('@/views/sell/out.vue'),
        meta: {title: '商品退货', icon: 'el-icon-shopping-cart-2'}
      }
    ]
  },

  {
    path: '/system',
    component: Layout,
    redirect: '/system/user',
    name: 'System',
    meta: {title: '系统管理', icon: 'el-icon-setting'},
    children: [
      {
        path: 'user',
        name: 'User',
        component: () => import('@/views/system/user'),
        meta: {title: '用户管理', icon: 'el-icon-s-check'}
      },
      {
        path: 'role',
        name: 'Role',
        component: () => import('@/views/system/role'),
        meta: {title: '权限管理', icon: 'el-icon-s-custom'}
      },
      {
        path: 'addGoods',
        name: 'AddGoods',
        component: () => import('@/views/system/add_goods'),
        meta: {title: '添加商品', icon: 'el-icon-s-custom'}
      }
    ]
  },


  // 404 page must be placed at the end !!!
  {path: '*', redirect: '/404', hidden: true}
]

const createRouter = () => new Router({
  // mode: 'history', // require service support
  scrollBehavior: () => ({y: 0}),
  routes: constantRoutes
})

const router = createRouter()

// Detail see: https://github.com/vuejs/vue-router/issues/1234#issuecomment-357941465
export function resetRouter() {
  const newRouter = createRouter()
  router.matcher = newRouter.matcher // reset router
}

export default router
