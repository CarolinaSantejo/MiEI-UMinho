<template>
  <head>
    <link href="http://maxcdn.bootstrapcdn.com/font-awesome/4.1.0/css/font-awesome.min.css" rel="stylesheet">
  </head>
  <body>
    <div class="imgcontainer">
      <img src="../images/logo_petit.jpg" alt="Logo PetIt" class="logo">
    </div>
    <div class="buttons_container">
      <a href="/signupPetsitter"><button type="button" id="PetsitterButton" >Petsitter</button></a>
      <a ><button type="button" id="ClientButton" disabled>Client</button></a>
    </div>
    <br>
    <br>
    <div class="container"  style="background-color:#f1f1f1">
    <form v-on:submit.prevent="signUpClient">
      <div class="input-boxes">
        <div class="input-box">
            <!--i class="fas fa-user"></i-->
            <label class="aux" >   Enter your name</label>
            <input id="usernameCliente" type="text" v-model="username" placeholder="ex: PetIt" required>
        </div>
        <div class="input-box">
            <!--i class="fas fa-envelope"></i-->
            <label class="aux" >   Enter your email</label>
            <input id="emailCliente" type="text" v-model="email" placeholder="example@petit.com" required>
        </div> 
        <div class="input-box">
            <!--i class="fas fa-lock"></i-->
            <label class="aux" >   Enter your password</label>
            <input id="passwordCliente" type="password" v-model="password" placeholder="*******"  required>
        </div>
        <div class="input-box">
            <!--i class="fa fa-mobile" aria-hidden="true"></i-->
            <label class="aux" >   Enter your phone number</label>
            <input id="phoneNumberCliente" type="tel" v-model="phone" placeholder="ex: 000000000" pattern="[0-9]{9}" required>
        </div>

        <div class="country-box">
          <label class="aux" >   Enter your country</label>
          <select class="form-select" id="countriesId" @change="selectCountry()" required>
              <option value="" selected>Country</option>
              <option v-for="i in countries.length" v-bind:key="i">{{countries[i]}}</option>
          </select>
        </div>

        <div class="city-box">
            <!--i class="fa fa-globe" aria-hidden="true">></i-->
            <label class="aux" >   Enter your city</label>
            <select class="form-select" id="citiesId" @change="selectCity()" required>
                <option value="" selected>City</option>
                <option v-for="i in cities.length" v-bind:key="i">{{cities[i]}}</option>
            </select>
            <!--input id="locationClient" type="text" v-model="location" placeholder="Country, City" required-->
        </div>


      </div>
      <div class="loader"  v-if="waiting"></div>
      <button type="submit" v-else>Sign up</button>
      <div v-if="!valid" style="color:red; padding-top:2%; text-align:center">Email already exists</div>
    </form>
      
    </div>
    
    <div class="container" style="background-color:#f1f1f1">
      <!--button type="button" class="cancelbtn">Cancel</button-->
      <span class="psw">Already have an account? Sign in <a href="/">here</a></span>
    </div>

    <br>
    <br>
    <br>
    </body>
</template>

<script>

import CountryService from '../services/CountryService'

export default {
  name: 'MySignupClient',
  data() {
    return {
      username:'',
      email: '',
      password: '',
      phone: '',
      location: '',
      valid: true,
      waiting: false,
      countries: [],
      cities: [],
      country: '',
      city: ''
    }
  },
  methods: {
    
    getAllCountries(){
      console.log("countries:")
      CountryService.getAllCountries().then((response) =>{
      this.countries = response.data
      console.log(this.countries);
      })
    },

    signUpClient(){
      console.log('entrar funcao signUpClient');
      this.location = this.country + ', ' + this.city;
      console.log(this.location)
      const { username, email, password , phone, location} = this
      this.waiting = true;
      this.$store.dispatch('signUpClient', {username, email, password, phone, location}).then(resp => {this.valid = resp; this.waiting = false;});
    },

    selectCountry(){
        var select;
        select = document.getElementById("countriesId");
        this.country = select.options[select.selectedIndex].text;
        console.log(this.country);
        CountryService.getCities(this.country).then((response) => {
          this.cities = response;
        })
    },
    selectCity(){
      var select;
      select = document.getElementById("citiesId");
      this.city = select.options[select.selectedIndex].text;
      console.log(this.city);
    }

  },
  created() {
    this.getAllCountries()
  }
}
</script>

<style scoped>
body {font-family: Arial, Helvetica, sans-serif;}
form {border: 3px solid #f1f1f1;}

.loader {
  position: relative;
  left: 48%;
  border: 5px solid white;
  border-radius: 50%;
  border-top: 5px solid #74C9E3;
  width: 17px;
  height: 17px;
  -webkit-animation: spin 2s linear infinite; /* Safari */
  animation: spin 2s linear infinite;
}

.aux{
  text-align:left;
  text-indent: 7px;
  float:left;
}

input[type=text], input[type=password], input[type=tel] {
  width: 100%;
  padding: 12px 20px;
  margin: 20px 0;
  display: inline-block;
  border: 1px solid #ccc;
  box-sizing: border-box;
}

.container button {
  background-color: #74C9E3;
  color: white;
  padding: 14px 20px;
  margin: 8px 0;
  border: none;
  cursor: pointer;
  width: 40%;
  margin: auto;
}

button {
  background-color: #74C9E3;
  color: white;
  margin: 8px 0;
  border: none;
  cursor: pointer;
  width: 40%;
  margin: auto;
}


.cancelbtn {
  width: auto;
  padding: 10px 18px;
  width: 35%;
  margin: auto;
  background-color: #f44336;
}

.container {
  display: grid;
  padding: 10px;
  width: 35%;
  margin: auto;
  background-color: lightgrey;

}

span.psw {
  float: right;
  padding-top: 16px;
}

.imgcontainer {
  text-align: center;
  margin: 0px 0 5px 0;
}

.form-select {
  
  margin: 10px 0;
  height: 40px;
  width: 100%;
  border: 1px solid rgba(0,0,0,0.2);
}

img.logo {
  width: 30%;
  border-radius: 0%;
}
input-boxes{
  margin-top: 50px;
}
input-box{
  display: flex;
  align-items: center;
  height: 50px;
  width: 100%;
  margin: 10px 0;
  position: relative;
}
input-box input{
  height: 100%;
  width: 100%;
  outline: none;
  border: none;
  padding: 0 30px;
  font-size: 16px;
  font-weight: 500;
  border-bottom: 2px solid rgba(0,0,0,0.2);
  transition: all 0.3s ease;
}
input-box input:focus,
input-box input:valid{
  border-color: #7d2ae8;
}
input-box i{
  position: absolute;
  color: #7d2ae8;
  font-size: 17px;
}


buttons_container{
    margin: 0 auto;
    display: block;
    float: left;
    width: 100px;
    text-align: center;
    position: relative;
    background-color:rgba(255, 255, 255, 0);
}

#PetsitterButton, #ClientButton{
width: 150px;
height: 40px;
display:inline-block;
text-align:center;
}
#PetsitterButton{
  background-color: white;
  border-radius: 3px;
  border: 3px solid #74C9E3;
  color:#74C9E3;
}
#ClientButton{
  background-color: #74C9E3;
  border-radius: 3px;
  border: 3px solid #74C9E3;
  color:white;
}
#PetsitterButton:hover{
  background-color: #74c9e36a;
}
</style>
