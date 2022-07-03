<template>
	<head>
		<link href="http://maxcdn.bootstrapcdn.com/font-awesome/4.1.0/css/font-awesome.min.css" rel="stylesheet">
	</head>
	<body>
		<div class="imgcontainer">
			<img src="../images/logo_petit.jpg" alt="Logo PetIt" class="logo">
		</div>
		<div class="buttons_container">
			<a><button type="button" id="PetsitterButton" disabled>Petsitter</button></a>
			<a href="/signupClient"><button type="button" id="ClientButton">Client</button></a>
		</div>
		<br>
		<br>
		<div class="container"  style="background-color:#f1f1f1">
			<form v-on:submit.prevent="signUpPetsitter">
				<div class="input-boxes">
				<div class="input-box">
					<!--i class="fas fa-user"></i-->
					<label class="aux" >   Enter your name</label>
					<input id="usernameCliente" type="text" v-model="username" placeholder="ex: PetIt" required>
				</div>
				<div class="input-box">
					<!--i class="fas fa-envelope"></i-->
					<label class="aux" >   Enter your Email</label>
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
          <select class="form-select" id="countriesId" @change="selectCountry()">
              <option value="" selected>Country</option>
              <option v-for="i in countries.length" v-bind:key="i">{{countries[i]}}</option>
          </select>
        </div>

        <div class="city-box">
            <!--i class="fa fa-globe" aria-hidden="true">></i-->
            <label class="aux" >   Enter your city</label>
            <select class="form-select" id="citiesId" @change="selectCity()">
                <option value="" selected>City</option>
                <option v-for="i in cities.length" v-bind:key="i">{{cities[i]}}</option>
            </select>
            <!--input id="locationClient" type="text" v-model="location" placeholder="Country, City" required-->
        </div>


				<div class="input-box">
					<!--i class="fa fa-calendar" aria-hidden="true">></i-->
					<label class="aux" >   Enter your Birthdate</label>
					<input id="birthdatePetsitter" type="date" v-model="birthdate" placeholder="Enter your birthdate" required>
				</div> 

        <div class="alert" id="alert"> 
          <span style='font-size:40px;'>&#9888;&#65039;</span> You must be older than <strong>18 years old</strong> to be a <strong>Petsitter</strong>. Thank you!!
        </div>

			</div>
      <div class="loader"  v-if="waiting"></div>
			<button type="submit" v-else>Sign up</button>
			</form>
			<div v-if="!valid" style="color:red; padding-top:2%">Email already exists</div>
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
  name: 'MySignupPetsitter',
  data() {
    return {
		username:'',
		email: '',
		password: '',
		phone: '',
		location: '',
		birthdate: '',
		valid: true,
    waiting: false,
    countries: [],
    cities: [],
    country: '',
    city: ''
    }
  },
  methods: {
    signUpPetsitter() {
		console.log('entrar funcao signUpPetsitter');
    this.location = this.country + ', ' + this.city;
    if (this.adult_or_not()){
      const { username, email, password , phone, location, birthdate} = this;
      this.waiting = true;
      this.$store.dispatch('signUpPetsitter', {username, email, password, phone, location, birthdate}).then(resp => {this.valid = resp; this.waiting = false;});
    }
    else{
      var alerta = document.getElementById("alert")
      console.log(alerta)
      alerta.style.display = "block";
      //alert("You must be older than 18 years old to be a Petsitter.")
    }
      
    },
    getAllCountries(){
      console.log("countries:")
      CountryService.getAllCountries().then((response) =>{
      this.countries = response.data
      console.log(this.countries);
      })
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
    },
    adult_or_not(){
      
      var ageInMilliseconds = new Date() - new Date(this.birthdate);
      var age = Math.floor(ageInMilliseconds/1000/60/60/24/365); 
      console.log(age)
      if(18<=age){
        
        return true;
      }
      else {
        return false;
      }
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


.alert {
  padding: 20px;
  background-color: #f44336;
  color: white;
  opacity: 1;
  transition: opacity 0.6s;
  margin-bottom: 15px;
  box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2);
  display:none;
}



.alert:hover {
  box-shadow: none;
}

.aux{
  text-align:left;
  text-indent: 7px;
  float:left;
}

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

input[type=text], input[type=password], input[type=tel], input[type=date] {
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

.form-select {
  margin: 10px 0;
  height: 40px;
  width: 100%;
  border: 1px solid rgba(0,0,0,0.2);
}

span.psw {
  float: right;
  padding-top: 16px;
}

.imgcontainer {
  text-align: center;
  margin: 0px 0 5px 0;
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
#ClientButton{
  background-color: white;
  border-radius: 3px;
  border: 3px solid #74C9E3;
  color:#74C9E3;
}
#PetsitterButton{
  background-color: #74C9E3;
  border-radius: 3px;
  border: 3px solid #74C9E3;
  color:white;
}

#ClientButton:hover{
  background-color: #74c9e36a;
}
</style>
