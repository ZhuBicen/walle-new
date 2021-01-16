import Vue from 'vue'
import Router from 'vue-router'
import Dashboard from './views/Dashboard.vue'
import User from './views/User.vue'
import Grid from './views/Grid.vue'
import Search from './views/Search.vue'
import Admin from './views/Admin.vue'

Vue.use(Router)
// eslint-disable-next-line no-console
console.log("BASE_URL", process.env.BASE_URL);
export default new Router({
  mode: 'history',
  base: process.env.BASE_URL,
  routes: [
    {
      path: '/',
      name: 'dashboard',
      component: Dashboard
    },
    {
      path: '/user',
      name: 'user',
      component: User
    },
    {
      path: '/grid',
      name: 'grid',
      component: Grid
    },
    {
      path: '/search',
      name: 'search',
      component: Search
    },
    {
      path: '/admin',
      name: 'admin',
      component: Admin
    }
  ]
})
