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
      meta: {title: '系统首页', icon: 'el-icon-s-home'}
    }]
  },

  {
    path: '/attraction',
    component: Layout,
    redirect: '/attraction/index',
    name: 'Attraction',
    meta: {title: '景点管理', icon: 'el-icon-folder-add'},
    children: [
      {
        path: 'index',
        name: 'Index',
        component: () => import('@/views/attraction/index'),
        meta: {title: '景点管理', icon: 'el-icon-notebook-2'}
      },
      {
        path: 'theme',
        name: 'Theme',
        component: () => import('@/views/attraction/theme'),
        meta: {title: '主题管理', icon: 'el-icon-shopping-cart-full'}
      },
      {path: 'addOrUpdateAttraction/:id',
      name: 'AddOrUpdateAttraction',
      component: () => import('@/views/attraction/component/addOrUpdateAttraction'),
      hidden: true 
    },
    ]
  },

  {
  path: '/hotel',
  component: Layout,  // 一级布局
  redirect: '/hotel/index',
  name: 'Hotel',
  meta: { title: '酒店管理', icon: 'el-icon-house' },
  children: [
    {
      path: 'index',
      name: 'HotelIndex',
      component: () => import('@/views/hotel/index'),
      meta: { title: '酒店列表', icon: 'el-icon-notebook-2' }
    },
    {
      path: 'room',
      name: 'HotelRoom',
      component: () => import('@/views/hotel/room'),
      meta: { title: '客房管理', icon: 'el-icon-shopping-cart-2' }
    },
    {
      path: 'facility',
      name: 'Facility',
      component: () => import('@/views/hotel/facility'), // 专门用于嵌套路由的容器组件
      meta: { title: '属性管理', icon: 'el-icon-setting' },
      
    },
    {path: 'addOrUpdateFacility/:id',
      name: 'AddOrUpdateFacility',
      component: () => import('@/views/hotel/component/addOrUpdateFacility'),
      hidden: true 
    },
    {path: 'addOrUpdateHotel/:id',
      name: 'AddOrUpdateHotel',
      component: () => import('@/views/hotel/component/addOrUpdateHotel'),
      hidden: true 
    },
    {path: 'addOrUpdateRoom/:id',
      name: 'AddOrUpdateRoom',
      component: () => import('@/views/hotel/component/addOrUpdateRoom'),
      hidden: true 
    },
]
},

  {
    path: '/strategy',
    component: Layout,
    redirect: '/strategy/index',
    name: 'Strategy',
    meta: {title: '攻略管理', icon: 'el-icon-setting'},
    children: [
      {
        path: 'index',
        name: 'Index',
        component: () => import('@/views/strategy/index'),
        meta: {title: '攻略管理', icon: 'el-icon-s-check'}
      },
      {
        path: 'attribute',
        name: 'Attribute',
        component: () => import('@/views/strategy/attribute'),
        meta: {title: '标签管理', icon: 'el-icon-s-custom'}
      },
      {path: 'addOrUpdateStrategy/:id',
        name: 'AddOrUpdateStrategy',
        component: () => import('@/views/strategy/component/addOrUpdateStrategy'),
        hidden: true 
      },
    ]
  },

  {
    path: '/record',
    component: Layout,
    redirect: '/record/index',
    name: 'Record',
    meta: {title: '日记管理', icon: 'el-icon-setting'},
    children: [
      {
        path: 'index',
        name: 'Index',
        component: () => import('@/views/record/index'),
        meta: {title: '日记管理', icon: 'el-icon-s-check'}
      }
    ]
  },

  {
    path: '/bill',
    component: Layout,
    redirect: '/bill/index',
    name: 'Bill',
    meta: {title: '账单管理', icon: 'el-icon-setting'},
    children: [
      {
        path: 'attraction',
        name: 'Atttraction',
        component: () => import('@/views/bill/attraction'),
        meta: {title: '景点账单', icon: 'el-icon-s-check'}
      },
      {
        path: 'hotel',
        name: 'Hotel',
        component: () => import('@/views/bill/hotel'),
        meta: {title: '酒店账单', icon: 'el-icon-s-custom'}
      },
      {
        path: 'strategy',
        name: 'Strategy',
        component: () => import('@/views/bill/strategy'),
        meta: {title: '攻略账单', icon: 'el-icon-s-custom'}
      }
    ]
  },


  {
    path: '/system',
    component: Layout,
    redirect: '/system/user',
    name: 'System',
    meta: {title: '系统管理', icon: 'el-icon-s-help'},
    children: [
      {
        path: 'user',
        name: 'User',
        component: () => import('@/views/system/user'),
        meta: {title: '用户管理', icon: 'table'}
      },
      {
        path: 'tourist',
        name: 'Tourist',
        component: () => import('@/views/system/tourist'),
        meta: {title: '游客管理', icon: 'el-icon-s-custom'}
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
