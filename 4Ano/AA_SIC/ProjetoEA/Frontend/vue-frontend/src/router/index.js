//import Vue from 'vue'
import Login from '../components/Login.vue'
import SignUpClient from '../components/SignupCliente.vue'
import SignUpPetsitter from '../components/SignupPetsitter.vue'
import Home from '../components/Home.vue'
import Appointments from '../components/Appointments'
import Prices from '../components/Prices'
import ClientProfileVue from '@/components/ClientProfile.vue'
import PetsitterProfileVue from '@/components/PetsitterProfile.vue'
import Advertisement from '@/components/Advertisement.vue'
import EditAd from '@/components/EditAd.vue'
import MakeRequest from '@/components/MakeRequest.vue'
import AddPet from '@/components/AddPet.vue'
import {createRouter, createWebHistory} from "vue-router"
import store from '../store';

const routes = [
    {path: '/', meta: {isAuthenticated : true}, component: Login},
    {path: '/home', meta: {guestC : true}, component: Home},
    {path: '/clientProfile', meta: {guestC : true}, component: ClientProfileVue},
    {path: '/signupClient', meta: {isAuthenticated : true}, component: SignUpClient},
    {path: '/signupPetsitter', meta: {isAuthenticated : true}, component: SignUpPetsitter},
    {path: '/petsitterProfile', meta: {guestP : true}, component: PetsitterProfileVue},
    {path: '/petsitterProfile/appointments', meta: {guestP : true}, component: Appointments},
    {path: '/petsitterProfile/prices', meta: {guestP : true}, component: Prices},
    {path: '/advertisement/:id', meta: {guestC : true}, component: Advertisement},
    {path: '/editAd', component: EditAd},
    {path: '/makeRequest/:id', component: MakeRequest},
    {path: '/addPet', component: AddPet}
]

const router = createRouter({
    history: createWebHistory(),
    routes
  })
  

router.beforeEach((to, from, next) => {
  console.log(store.getters.isAuthenticated);
  if (to.matched.some((record) => record.meta.isAuthenticated)) {
    if (store.getters.isAuthenticated) {
      if(store.getters.userType == 'Client')
        next("/home");
      else if(store.getters.userType == 'Petsitter')
        next("/petsitterProfile");
      return;
    }
    next();
  } else {
    next();
  }
});


router.beforeEach((to, from, next) => {
  console.log(store.getters.isAuthenticated);
  if (to.matched.some((record) => record.meta.guest)) {
    if (store.getters.isAuthenticated) {
      next();
      return;
    }
    next("/");
  } else {
    next();
  }
});


router.beforeEach((to, from, next) => {
  if (to.matched.some((record) => record.meta.guestC)) {
    if (store.getters.isAuthenticated && store.getters.userType == 'Client') {
      next();
      return;
    }
    next("/");
  } else {
    next();
  }
});

router.beforeEach((to, from, next) => {
  if (to.matched.some((record) => record.meta.guestP)) {
    if (store.getters.isAuthenticated && store.getters.userType == 'Petsitter') {
      next();
      return;
    }
    next("/");
  } else {
    next();
  }
});

export default router