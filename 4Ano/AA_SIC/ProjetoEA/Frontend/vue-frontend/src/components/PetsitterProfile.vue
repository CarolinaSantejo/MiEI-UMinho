<template>
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <link href="css/bootstrap.css" rel="stylesheet">
        <link href="style/starter-template.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <link href="https://unpkg.com/ionicons@5.4.0/dist/ionicons.js">
        
        <!--link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css"-->
        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Montserrat">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    </head>
    <body>
        <div class="container" id="petit">
            <Nav/>
            <div class="loading" v-if="waiting">
                <Load/>
            </div>
            <div v-else>
                <div class="profile">
                    <div class="info_left">
                        <div class="ContactMe">
                            <h2 class="text-center"> Contacts </h2>
                            <hr class="w3-opacity"> 
                            <div class="infos">
                                <p><i class="fas fa-map-marker-alt"></i>&nbsp; {{location}}</p>
                                <p><i class="fa fa-phone fa-fw"></i>&nbsp;{{phone}}</p>
                                <p><i class="fa fa-envelope fa-fw"> </i><strong> Email: </strong>{{email}}</p>
                            </div>
                        </div>
                        <div class="aboutMe">
                            <h2> About &nbsp;  <button id="edit" class="button_about" @click="onclick()"><i class="fa fa-pencil"></i>&nbsp; Edit</button></h2>
                            <hr class="w3-opacity" id="about_line"> 
                            <h4 id="text_about" style="color: grey;white-space: pre-wrap">{{text}}</h4>
                        </div>
                    </div>

                    <div class="availableContainer">
                        <div class="edit_button">
                            <a href="/editAd"> 
                            <button class="button button2" v-if="services.length>0"><i class="fa fa-pencil"></i> Edit Ad </button>
                            <button class="button button2" v-else><i class="fa fa-pencil"></i> Make Ad </button>
                            </a>
                        </div>
                        <div class="requestsContainer">
                            <h2 class="text-center">Your Availability</h2>
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
                            <h4 v-else class="text-center" style="text-align:center"> No ad yet! </h4>
                        </div>
                    </div>
                </div>


                <div class="serviceContainer">
                    <div class="title">
                        <h2 class="text-center"> Your Advertisement</h2>
                        <hr class="w3-opacity"> 
                    </div>

                    <div class="animals" >
                        <h2 class="text-center">Animals</h2>
                        <table v-if="animals.length>0 && services.length>0">
                            <tr v-for = "animal in animals" v-bind:key = "animal">
                                <td class="text_ad"><i class="fa fa-paw"></i> {{' ' + animal}}</td>
                            </tr>
                        </table> 
                        <div v-else>
                            <h4 class="text-center" style="text-align:center"> No ad yet! </h4>
                        </div>
                    </div>

                    <div class="services">
                        <h2 class="text-center">Services</h2>
                        <table v-if="animals.length>0 && services.length>0">
                            <tr v-for = "service in services" v-bind:key = "service.id">
                                <td class="text_ad"><i class="fa fa-paw"></i> {{' ' + service.serviceType}}</td>
                            </tr>
                        </table>
                        <div v-else>
                            <h4 class="text-center" style="text-align:center"> No ad yet! </h4>
                        </div>
                    </div>

                    <div class="prices">
                        <h2 class="text-center">Total Price</h2>
                        <table v-if="animals.length>0 && services.length>0">
                            <tr>
                                <td class="text_ad"> <i class="fa fa-plus"></i> My tax: {{tax}} €</td>
                            </tr>
                            <tr>
                                <td class="text_ad"> <i class="fa fa-plus"></i> Service price</td>
                            </tr>
                        </table>
                        <div v-else>
                            <h4 class="text-center" style="text-align:center"> No ad yet! </h4>
                        </div>
                    </div>
                </div>
            </div>
            
        </div>
    </body>
</template>

<script>
import Nav from './Nav.vue';
import Load from './Load.vue';
import PetsittersService from '@/services/PetsittersService';

export default {
  name: 'PetsitterProfile',
  components: { Nav , Load },
  data() {
    this.$store.state.page = 'petsitterProfile'
    return {
        email: this.$store.state.email,
        requests: {},
        animals: [],
        services: ['Bath', 'Shear'],
        tax: '5€',
        location: '',
        phone: '',
        text:'',
        waiting: true
    }
  },
  methods: {
    getProfileData(){
        PetsittersService.getProfile().then((response) => {
            console.log(response);
            this.tax = response.ad.tax;
            this.animals = response.ad.species;
            this.services = response.ad.services;
            this.text = response.about_me;
            this.location = response.location;
            this.phone = response.phone;

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
            const ordered = Object.keys(response.ad.availability).sort(
                function sortByDay(a, b) {
                    let day1 = a.toLowerCase();
                    let day2 = b.toLowerCase();
                    return sorter[day1] - sorter[day2];
                })
                .reduce(
                (obj, key) => { 
                    obj[key] = response.ad.availability[key]; 
                    return obj;
                }, 
                {});
            this.requests = ordered;
            this.waiting = false;
        });
      },
      onclick(){
        var data = document.getElementById('text_about'),
        btn  = document.getElementById('edit');

        
        data.toggleAttribute('contenteditable');
        
        if( data.hasAttribute('contenteditable') ){
            // Currently editing, change the button
            btn.innerText = 'Save';

            /*var j = btn.querySelectorAll('i');
            j[0].className="fas fa-save";
            j[0].innerText='&nbsp;';*/
        } else {
            // We just "saved". run "save functions" here
            btn.innerText = 'Edit';

           /* var i = btn.querySelectorAll('i');
            i[0].className="fa fa-pencil";
            i[0].innerText='&nbsp; ';*/

            this.text = data.innerText;
            console.log(this.text)
            PetsittersService.sendAboutText(this.email ,this.text).then((response) => {
                console.log(response)
            })
        }
      }
  },
    created() {
        this.getProfileData();
    }
}
</script>

<style scoped>
.container {
  background-color: rgba(255, 255, 255, 0.561);
}

.profile {
    display: grid;
    grid-template-columns: 50% 50%;
}

.info_left {
    padding-top: 2%;
    margin: 6% 7%;
    grid-column-start: 1;
    text-align: left;
}


.aboutMe {
    padding-top: 5%;
    text-align: left;
}

[contenteditable] { outline: 1px solid #ccc; }

.availableContainer {
    color: rgb(92, 89, 89);
    grid-row-start: 1;
    grid-column-start: 2;
    
}

.requestsContainer {
    padding-top: 2%;
    text-align: left;
    padding-left: 3%;
    padding: 0px 5px;
}

.edit_button{
    width: 30%;
    position: relative;
    text-align: right;
    border-spacing: 0 15px;
    margin: 3% 64%;

}



.button_about{
    width:15%;
    font-size: 16px;
    margin: 0px 2px;
    position: relative;
    text-align: center;
    border: none;
    background-color: #74C9E3; /*6CC4C7*/
    color: white; 
    border-radius: 12px;
    transition-duration: 0.4s;
    cursor: pointer;

    text-decoration: none;
    display: inline-block;
}

.button_about:hover {
    color: white; 
    background-color: rgba(154, 154, 154, 0.953);

}


.button {
    border: none;
    color: white;
    padding: 5px 5px;
    text-align: center;
    text-decoration: none;
    display: inline-block;
    font-size: 16px;
    margin: 4px 2px;
    transition-duration: 0.4s;
    cursor: pointer;
    top: 20%;
}

.button2 {
    position: relative;
    width: 55%;
    height: 45%;
    background-color: #74C9E3; /*6CC4C7*/
    color: white; 
    border-radius: 12px;
}

.button2:hover {
    color: white; 
    background-color: rgba(154, 154, 154, 0.953);

}




.serviceContainer {
    display: grid;
    grid-template-columns: 33% 33% 33%;
    
}

.title{
    text-align: left;
    margin: 0% 4%;
    grid-column-start: 1;
    grid-column-end: 7;
}

.animals {
    grid-row-start: 2;
    grid-column-start: 1;
    text-align: left;
    margin: 0% 30%;
}

.services {
    grid-row-start: 2;
    grid-column-start: 2;
    text-align: left;
    margin: 0% 30%;
}

.prices {
    grid-row-start: 2;
    grid-column-start: 3;
    grid-column-end: 4;
    text-align: left;
    margin: 0% 30%;
}


#text_about{
    height: 70%;
}


textarea {
    resize: none;
    box-shadow: 0px 0px 9px 0px rgba(0,0,0,0.3);
    border: transparent;
    background-color: rgba(230, 230, 230, 0.5);
    font-family: Arial, Helvetica, sans-serif;
    font-size: 20px;
    padding: 1%;
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


.container {
    background-color: rgba(255, 255, 255, 0.761);
    color: rgb(92, 89, 89);
}


</style>


