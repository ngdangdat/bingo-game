import Vue from 'vue';
import VueRouter from 'vue-router';
import Home from '../views/Home.vue';
import LotoCall from '../views/LotoCall.vue';
import LotoSheets from '../views/LotoSheets.vue';
import LotoSheet from '../views/LotoSheet.vue';

Vue.use(VueRouter);

const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home,
  },
  {
    path: '/about',
    name: 'About',
    component: () => import('../views/About.vue'),
  },
  {
    path: '/bingo/:id',
    component: LotoCall,
    props: true,
  },
  {
    path: '/bingo/:id/sheet',
    component: LotoSheets,
    props: true,
  },
  {
    path: '/bingo/:id/sheet/:sheetId',
    component: LotoSheet,
    props: true,
  },
  /**
   * Paths:
   * - Sheet
   * - Call
   * - Home
   */
];

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes,
});

export default router;
