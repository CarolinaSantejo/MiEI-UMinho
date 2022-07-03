<template>
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <link href="css/bootstrap.css" rel="stylesheet">
        <link href="style/starter-template.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <link href="https://unpkg.com/ionicons@5.4.0/dist/ionicons.js">
        
    </head>
    <body>
        <div class="container" id="petit">
            <Nav/>
            <div class="loading" v-if="waiting">
                <Load/>
            </div>
            <div v-else>
                <div class="infoContainer">
                    <div class="avatar"> </div>
                    <div class="username">
                        <h2>{{username}}</h2>
                        <div class="infos">
                            <p><i class="fas fa-map-marker-alt"></i>&nbsp; {{location}}</p>
                            <p><i class="fa fa-phone fa-fw"></i>&nbsp;{{phone}}</p>
                            <p><i class="fa fa-envelope fa-fw"> </i> Email: {{email}}</p>
                        </div>
                    </div>
                    <div class = "about-me">
                        <h4 style="font-size:22px"> &#128075; <strong>About me </strong> &#128075;</h4>
                        <div class="aboutme">
                            <h4 style="color: grey;white-space: pre-wrap">{{text}}</h4>
                        </div>
                    </div>
                    <div class="back-button">
                        <button class="button-back" @click="back()">
                        <i class="fas fa-arrow-circle-left"></i> Back </button>
                    </div>
                </div>

                <div class="profileContainer">
                    <div class="requestsContainer">
                        <table v-if="Object.keys(requests).length > 0" class="tablereq table-striped" style="width:95%">
                                <thead>
                                    <th style="width:20%;"> Week day </th>
                                    <th style="width:15%;"> Availability </th>
                                    
                                </thead>
                                <tbody>
                                    <tr v-for = "(value, key) in requests" v-bind:key = "key">
                                        <td> {{key}} </td>
                                        <td v-if="value.startTime === null || value.endTime === null"> Closed </td>
                                        <td v-else> {{value.startTime.split(':')[0] + ':' + value.startTime.split(':')[1]}} - {{value.endTime.split(':')[0] + ':' + value.endTime.split(':')[1]}}</td>
                                    </tr>
                                </tbody> 
                            </table>
                            
                    </div>

                    <div class="serviceContainer">
                        
        
                        <div class="animals" >
                            <h2 class="text-center">Animals</h2>
                            <table v-if="animals.length>0 && services.length>0">
                                <tr v-for = "animal in animals" v-bind:key = "animal">
                                    <td class="text_ad"><i class="fa fa-paw"></i> {{' ' + animal}}</td>
                                </tr>
                            </table> 
                            
                        </div>

                        <div class="services">
                            <h2 class="text-center">Services</h2>
                            <table v-if="animals.length>0 && services.length>0">
                                <tr v-for = "service in services" v-bind:key = "service.id">
                                    <td class="text_ad"><i class="fa fa-paw"></i> {{' ' + service.serviceType}}</td>
                                </tr>
                            </table>
                            
                        </div>

                        <div class="prices">
                            <h2 class="text-center">Prices</h2>
                            <table v-if="animals.length>0 && services.length>0">
                                <tr>
                                    <td class="text_ad"> <i class="fa fa-plus"></i> My tax: {{tax}} €</td>
                                </tr>
                                <tr>
                                    <td class="text_ad"> <i class="fa fa-plus"></i> Service price</td>
                                </tr>
                            </table>
                        </div>

                        <div class="makeButton"> 
                            <div v-if="valid === 0" style="color:red; grid-column-start: 2; grid-column-end: 4; padding-top: 10%"> <b> Petsitter does not provide services for your animals </b></div>
                            <div v-else-if="valid === 2" style="color:red; grid-column-start: 2; grid-column-end: 4; padding-top: 10%"> <b> <a href="/addPet">Add</a> a new animal to request petsitter services </b> </div>
                            <button v-else class="button button2" @click="makeRequest()" style="grid-column-start: 3;top: 100%"><i class="fa fa-plus"></i> Make request</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>  
    </body>
</template>

<script>
import Nav from './Nav.vue';
import router from '../router';
import Load from './Load.vue';

export default {
  name: 'ClientProfile',
  components: { Nav, Load },
  data() {
      return {
        username: '',
        requests: [],
        animals: [],
        services: [],
        tax: '',
        text:'',
        location: '',
        phone: '',
        email: '',
        waiting: true,
        valid: 0
      }
  },
  methods:{
      getProfileData(){
          
        var request = {email: this.$route.params.id}
        console.log('ADVERTISEMENT');
        console.log(request.email);
        this.$store.dispatch('getProfileAd', request).then(response => {

            const pet = response.petsitter;
            this.username = pet.username;
            this.email = pet.email;
            this.tax = pet.ad.tax;
            this.animals = pet.ad.species;
            this.services = pet.ad.services;
            this.text = pet.about_me;
            this.location = pet.location;
            this.phone = pet.phone;

            const sorter = {
                // "sunday": 0, // << if sunday is first day of week
                "monday": 1,
                "tuesday": 2,
                "wednesday": 3,
                "thursday": 4,
                "friday": 5,
                "saturday": 6,
                "sunday": 7
            }
            // Ordenar por dia da semana
            const ordered = Object.keys(pet.ad.availability).sort(
                function sortByDay(a, b) {
                    let day1 = a.toLowerCase();
                    let day2 = b.toLowerCase();
                    return sorter[day1] - sorter[day2];
                })
                .reduce(
                (obj, key) => { 
                    obj[key] = pet.ad.availability[key]; 
                    return obj;
                }, 
                {});
            this.requests = ordered;
            this.waiting = false;
            console.log(response.cliAnimals);
            if (response.cliAnimals.length === 0) {
                this.valid = 2;
            }

            for (var a in response.cliAnimals) {
                if (this.animals.includes(response.cliAnimals[a])) {
                    this.valid = 1;
                    break;
                }
            }
            });
            
      },
    makeRequest() {
        router.push('/makeRequest/' + this.$route.params.id);
    },
      
    back() {
        router.push('/home');
    }
  },
    created() {
        this.getProfileData();
    }
}
</script>

<style scoped>

.makeButton {
    grid-column-start: 1;
    grid-column-end: 4;
    display: grid;
    grid-template-columns: 30% 30% auto;
    margin-top: 15%;
}


.button-back:hover {
    color: white; 
    background-color: rgba(154, 154, 154, 0.953);
}

.button-back{
    grid-column-start: 4;
    /*grid-row-start: 1;*/
    width: 45%;
    position: relative;
    height: 25%;
    background-color: #74C9E3; /*6CC4C7*/
    color: white; 
    border-radius: 20px;
    border: none;
    font-size: 16px;
    margin: 4px 2px;
    transition-duration: 0.4s;
    cursor: pointer;
    top:13%;
    left: 14%;
}



.infoContainer{
    display: grid;
    grid-template-columns: 15% 20% 50% 15%;
}

.avatar{
    grid-column-start: 1;
    grid-row-start: 1;
    position: relative;
    top:14%;
    left:43%;
    width: 100px;
    height: 100px;
    border-radius: 50%;
    box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);
    background: url('../images/avatar.jpg');
    background-repeat: no-repeat;
    background-size: 100px 100px;

}

.username{
    margin-top:7%;
    grid-column-start: 2;
    grid-row-start: 1;
    text-align: left;
    top:10%;
    left:6%;
}

.infos{
     padding-top:0.4%;
}

.about-me{
    text-align: left;
}
.aboutme{
    grid-column-start: 3;
    grid-column-end: 3;
    grid-row-start: 1;
    text-align: left;
    height: 50%;
    width: 88%;
    padding-top: 1px;
    padding-left:4%;
    box-shadow: 0 1px 8px 0 rgba(0, 0, 0, 0.2);
}

.animals tr, .services tr {
    color: rgba(50, 50, 50, 0.889);
    text-align: left;
}
.animals table, .services table {

    border-collapse: separate;
    border-spacing: 0 10px;
    padding-left: 1%;
}

.tablereq thead{
    box-shadow: 0px 0px 9px 0px rgba(0,0,0,0.3);
}
.tablereq th {
    
    background-color: rgba(50, 50, 50, 0.889);
    text-align: center;
    padding: 1%;
    color: rgb(255, 255, 255);
    
}
.tablereq td {
    padding: 1%;
}
.tablereq tr {
    border: 1px solid rgb(17, 20, 20);
    box-shadow: 0px 0px 9px 0px rgba(0,0,0,0.2);
    
}

.tablereq {
    text-align: center;
    border-collapse: separate;
    color: black;
    border-spacing: 0 15px;
}

.profileContainer {
    color: rgb(92, 89, 89);
    display: grid;
    grid-template-columns: 45% 55%;
}

.requestsContainer {
    text-align: left;
    grid-column-start: 1;
    padding-left: 15%;
    padding-top: 6%;
    
    margin-top:2%;
}

.serviceContainer {
    text-align: left;
    grid-column-start: 2;
    display: grid;
    margin-top:6%;
    padding-left: 10%;
    padding-bottom: 35%;
}


.animals {
    grid-column-start: 1;
}

.services {
    grid-column-start: 2;
}
.prices {
    grid-column-start: 3;
    grid-column-end: 4;
}

.button {
    border: none;
    color: white;
    padding: 2% 2%;
    text-align: center;
    text-decoration: none;
    display: inline-block;
    font-size: 16px;
    margin: 4px 2px;
    transition-duration: 0.4s;
    cursor: pointer;
}

.button2 {
    position: relative;
    width: 55%;
    height: 100%;
    background-color: #74C9E3;
    color: white; 
    border-radius: 12px;
}

.button2:hover {
    color: white; 
    background-color: rgba(154, 154, 154, 0.953);
}

.container {
    background-color: rgba(255, 255, 255, 0.761);
    color: rgb(92, 89, 89);
}

</style>


