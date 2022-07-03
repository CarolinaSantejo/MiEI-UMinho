import axios from 'axios';
import Vuex from 'vuex';
import router from '../router';
import createPersistedState from "vuex-persistedstate";


//Vue.use(Vuex);

axios.defaults.baseURL = 'http://localhost:8080/api'

// Create a new store instance.
export const store = new Vuex.Store({
  state: {
    email: '',
    password: '',
    username: '',
    isLoggedIn: false,
    userType : '',
    page: '',
    services: {}
  },
  getters: {
    isAuthenticated: state => state.isLoggedIn,
    userType: state => state.userType,
    email: state => state.email,
    services: state => state.services
  },
  mutations: {
    loginClient(state, payload){
      state.isLoggedIn = true;
      state.email = payload.email;
      state.password = payload.password;
      state.username = payload.username;
      state.userType = 'Client';
      router.push('/home');
    },
    loginPetsitter(state, payload){
      state.isLoggedIn = true;
      state.email = payload.email;
      state.password = payload.password;
      state.username = payload.username;
      state.userType = 'Petsitter';
      router.push('/petsitterProfile');
    },
    logout(state){
      state.isLoggedIn = false;
      state.email = '';
      state.password = '';
      state.username = '';
      state.userType = '';
      router.push('/');
    },
    signUpClient(state, payload){
      state.isLoggedIn = true;
      state.email = payload.email;
      state.password = payload.password;
      state.username = payload.username;
      state.userType= "Client"
      router.push('/home');
    },
    signUpPetsitter(state, payload){
      state.isLoggedIn = true;
      state.email = payload.email;
      state.password = payload.password;
      state.userType= "Petsitter";
      state.username = payload.username;
      router.push('/petsitterProfile');
    },
    setNewPage(state, payload) {
      state.page = payload;
    },
    async setServices(state) {
      console.log("Servicos");
      try {
        const response = await axios.get('/servicePrices');
        console.log(response.data);
        await response.data.forEach(element => {
          if (element.serviceType === 'Companion') {
            //const sorted = element.price.sort((a,b) => a.price - b.price)
            state.services.companions = element.price;
          }
          else if (element.serviceType === 'Bath_Shear') {
            //const sortedB = element.price.sort((a,b) => a.price - b.price);
            state.services.baths = element.price;
          }
          else if (element.serviceType === 'Training') {
            //const sortedT = element.price.sort((a,b) => a.price - b.price);
            state.services.training = element.price;
          }
        });
      }
      catch(err) {
        console.error("failed to access response", err);
      }
      
    }
  },
  actions: {
    async login(context, payload) {
      console.log('request_login', payload.email, payload.password);
      const request = {
        email: payload.email,
        password: payload.password
      };

      try {
        const response = await axios.post('/login',request);
        console.log(response.data.res);
        console.log(response.data.userType);
        if(response.data.res==true && response.data.userType=="Client") {
          const aux = {
            email: payload.email,
            password: payload.password,
            username: response.data.username
          };
          this.commit('loginClient', aux);
          return true;
        }
        else if(response.data.res==true && response.data.userType=="Petsitter") {
          const aux = {
            email: payload.email,
            password: payload.password,
            username: response.data.username
          };
          this.commit('loginPetsitter', aux);
          return true;
        }
        else {
          return false;
        }
      }
      catch (e) {
        console.error("failed access response", e);
        return false;
      }

    },
    async signUpClient(context, payload){
      console.log('request_sigupClient', payload.username, payload.email, payload.password, payload.phone, payload.location);
      const request = {
        email: payload.email,
        password: payload.password,
        username: payload.username,
        phone: payload.phone,
        location: payload.location
      };
      var valid = true;
      try {
        const response = await axios.post('/registerClient',request);
        console.log("Registo de client bem sucedido")
        console.log(response.data);
        //console.log(response.data.email);
        //console.log(response.data.password);
        if(response.data.res==true) {
          const aux = {
            email: payload.email,
            password: payload.password,
            username: payload.username
          }
          this.commit('signUpClient', aux);
          valid = true;
        }
        else {
          valid = false;
        }
      }
      catch (err) {
        console.error("failed to access response", err);
        valid = false;
      }

      return valid;
    },
    async signUpPetsitter(context, payload) {
      console.log('request_sigupPetsitter', payload.username, payload.email, payload.password, payload.phone, payload.location, payload.birthdate);
      const request = {
        email: payload.email,
        password: payload.password,
        username: payload.username,
        phone: payload.phone,
        location: payload.location,
        birthdate: payload.birthdate
      };
      
      try {
        var valid = true;
        const response = await axios.post('/registerPetsitter',request);
        console.log("Registo de petsitter bem sucedido")
        console.log(response.data);
        if(response.data.res==true) {
          const aux = {
            email: payload.email,
            password: payload.password,
            username: payload.username,
          }
          this.commit('signUpPetsitter', aux);
          valid = true;
        }
        else {
          valid = false;
        }
      }
      catch(err) {
        console.error("failed to access response", err);
        valid = false;
      }
      
      return valid;
    },
    setNewPage(context, payload) {
      this.commit('setNewPage', payload);
    },
    async registerAnimal(context, payload) {
      console.log('request_registerAnimal', payload.name, payload.species, payload.breed, payload.sex, payload.weight, payload.ageYears, payload.ageMonths, payload.diseases, payload.notes);
      const request = {
        email: this.state.email,
        name: payload.name,
        species: payload.species,
        breed: payload.breed,
        sex: payload.sex,
        weight: payload.weight,
        age: payload.ageYears,  //VER SE POMOS OS MESES
        ageMonths: payload.ageMonths,
        diseases: payload.diseases,
        notes: payload.notes,
      };
      var valid = true;
      try {
        const response = await axios.post('/registerAnimal',request);
        console.log(response.data);
        if(response.data.res==true) {
          alert("New pet successfully registered");
          valid = true;
          router.push('/clientProfile');
        }
        else {
          valid = false;
        }
      }
      catch (err) {
        console.error("failed to access response", err);
        valid = false;
      }
        
      return valid;
    },
    async editAd(context, payload) {
      console.log(payload)
      const request = {
        email : this.state.email,
        monday : payload.Monday,
        tuesday : payload.Tuesday,
        wednesday : payload.Wednesday,
        thursday : payload.Thursday,
        friday : payload.Friday,
        saturday : payload.Saturday,
        sunday : payload.Sunday,
        animals : payload.animals,
        services : payload.services,
        price: payload.price
      }
      try {
        const response = await axios.post('/editAd', request);
        console.log(response.data);
        if(response.data.res==true) {
          alert("Advertisement successfully updated!")
          router.push('/petsitterProfile');
        }
        
      }
      catch (err) {
        console.error("failed to access response", err);
      }
    },
    setServices() {
      this.commit('setServices');
    },
    async getProfileAd(context, payload) {
      var request = {
          emailPetsitter: payload.email,
          email: this.state.email
      };
      console.log('vou enviar email');
      console.log(request.email);
      try {
          const response = await axios.post('/petsitterProfileReq',request);
          
          return response.data
      }
      catch(err) {
          console.error("failed to access response", err);
      }
      return null;
    }
  },
  plugins: [createPersistedState()]
})

export default store