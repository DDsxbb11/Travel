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
    }]
  },

  {
    path: '/attraction',
    component: Layout,
    redirect: '/attraction/index',
    children: [{
      path: 'index',
      name: '景点信息',
      component: () => import('@/views/attraction/index.vue'),
    }]
  },
  {
    path: '/hotel',
    component: Layout,
    redirect: '/hotel/index',
    children: [{
      path: 'index',
      name: '酒店信息',
      component: () => import('@/views/hotel/index.vue'),
    }]
  },
  {
    path: '/record',
    component: Layout,
    redirect: '/record/index',
    children: [{
      path: 'index',
      name: '出行记录',
      component: () => import('@/views/record/index.vue'),
    }]
  },
  {
    path: '/strategy',
    component: Layout,
    redirect: '/strategy/index',
    children: [{
      path: 'index',
      name: '攻略信息',
      component: () => import('@/views/strategy/index.vue'),
    }]
  },

  {
    path: '/attraction/detail/:id',
    component: Layout,
    children: [{
      path: '', // 空路径，直接作为默认子路由
      name: '景点信息详情',
      component: () => import('@/views/attraction/detail/index.vue')
    }]
  },

  {
    path: '/hotel/detail/:id',
    component: Layout,
    children: [{
      path: '', // 空路径，直接作为默认子路由
      name: '酒店信息详情',
      component: () => import('@/views/hotel/detail/index.vue')
    }]
  },
  {
    path: '/strategy/detail/:id',
    component: Layout,
    children: [{
      path: '', // 空路径，直接作为默认子路由
      name: '攻略信息详情',
      component: () => import('@/views/strategy/detail/index.vue')
    }]
  },
  {
    path: '/record/detail/:id',
    component: Layout,
    children: [{
      path: '', // 空路径，直接作为默认子路由
      name: '出行记录详情',
      component: () => import('@/views/record/detail/index.vue')
    }]
  },
  {
    path: '/myInfo',
    component: Layout,
    children: [{
      path: '', // 空路径，直接作为默认子路由
      name: '个人信息',
      component: () => import('@/views/myCenter/myInfo/index.vue')
    }]
  },
  {
    path: '/collection',
    component: Layout,
    children: [{
      path: '', // 空路径，直接作为默认子路由
      name: '我的收藏',
      component: () => import('@/views/myCenter/collection/index.vue')
    }]
  },
  {
    path: '/history',
    component: Layout,
    children: [{
      path: '', // 空路径，直接作为默认子路由
      name: '浏览历史',
      component: () => import('@/views/myCenter/history/index.vue')
    }]
  },
  {
    path: '/order',
    component: Layout,
    children: [{
      path: '', // 空路径，直接作为默认子路由
      name: '我的订单',
      component: () => import('@/views/myCenter/order/index.vue')
    }]
  },
  

  // {
  //   path: '/center',
  //   component: Layout,
  //   redirect: '/center/index',
  //   children: [
  //     {
  //       path: '/center/info',
  //       name: 'Goods',
  //       component: () => import('@/views/center/info.vue'),
  //       meta: {title: '商品信息', icon: 'table'}
  //     },
  //     {
  //       path: 'index',
  //       name: 'Customer',
  //       component: () => import('@/views/center/index.vue'),
  //     },
  //     {
  //       path: 'supplier',
  //       name: 'Supplier',
  //       component: () => import('@/views/base_info/supplier'),
  //       meta: {title: '供应商信息', icon: 'el-icon-s-check'}
  //     }
  //   ]
  // },
  // 404 exam_recordpage must be placed at the end !!!
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
