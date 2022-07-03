<template>
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <link href="https://unpkg.com/ionicons@5.4.0/dist/ionicons.js">
    </head>
    <body>
        <div class="container">
            <Nav/>
            <div class="loading" v-if="waiting">
                <Load/>
            </div>
            <form v-on:submit.prevent="editAd" v-else>
            <div class="profileContainer">
                <div class="requestsContainer">
                    <h1 class="text-center" > Ad Configuration</h1>
                    <h2 class="text-center" v-if="avValid"> Your availability:</h2>
                    <h2 class="text-center required" v-else> Your availability:</h2>
                    <div class="calendar">
                        <div class="daysleft">
                            <table style="width:95%;">
                                <tbody v-for="day in daysleft" v-bind:key = "day">
                                    <tr >
                                        <td rowspan="3" style="width:20%">
                                            <img src="../images/yellowpaw.png" alt="Paw" style="width:60%"/>
                                        </td>
                                        <td rowspan="3" >
                                            <h2>{{day}}</h2>
                                        </td>
                                        <!-- Horas-->
                                        <td >
                                            <tr>
                                                <td><input type="time" id="start" v-model="ad[day].start" name="start"  min="00:00" max="23:59" ></td>    
                                            </tr>
                                            <tr>
                                                <td><input type="time" id="end" v-model="ad[day].end" name="end" min="00:00" max="23:59" ></td>
                                            </tr>
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>
                        <div class="daysright">
                            <table style="width:95%;">
                                <tbody v-for="day in daysright" v-bind:key = "day">
                                    <tr >
                                        <td rowspan="3" style="width:20%">
                                            <img src="../images/yellowpaw.png" alt="Paw" style="width:60%"/>
                                        </td>
                                        <td rowspan="3" >
                                            <h2>{{day}}</h2>
                                        </td>
                                        <!-- Horas-->
                                        <td>
                                            <tr>
                                                <td><input type="time" id="start" v-model="ad[day].start" name="start" min="00:00" max="23:59" ></td>    
                                            </tr>
                                            <tr>
                                                <td><input type="time" id="end" v-model="ad[day].end" name="end" min="00:00" max="23:59" ></td>
                                            </tr>
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                    
                </div>
                
                <div class="petsContainer">
                    <!--div class="back-button">
                        <button class="button-back" @click="back()">
                        <i class="fas fa-arrow-circle-left"></i> Back </button>
                    </div-->
                    <div class="addPet" style="width:85%">
                        <div class="filter-animals">
                            <div class="title-a">
                                <h2 v-if="anValid">Animals</h2>
                                <h2 class="required" v-else>Animals</h2>
                            </div>
                            <label class="box-option">Dog
                                <input type="checkbox" v-model="ad.animals.dog" checked="checked">
                                <span class="checkmark"></span>
                            </label>
                            <label class="box-option">Cat
                                <input type="checkbox" v-model="ad.animals.cat" checked="checked">
                                <span class="checkmark"></span>
                            </label>
                            <label class="box-option">Reptile
                                <input type="checkbox" v-model="ad.animals.reptile" checked="checked">
                                <span class="checkmark"></span>
                            </label>
                            <label class="box-option">Horse
                                <input type="checkbox" v-model="ad.animals.horse" checked="checked">
                                <span class="checkmark"></span>
                            </label>
                        </div>
                        <div class="filter-service">
                            <div class="title-s">
                                <h2 v-if="servValid">Services</h2>
                                <h2 class="required" v-else>Services</h2>
                            </div>
                            <label class="box-option">Companion
                            <input type="checkbox" v-model="ad.services.companion" checked="checked">
                            <span class="checkmark"></span>
                            </label>
                            <label class="box-option">Bath/Shear
                                <input type="checkbox" v-model="ad.services.bath_shear" checked="checked">
                                <span class="checkmark"></span>
                            </label>
                            <label class="box-option">Training
                                <input type="checkbox" v-model="ad.services.training" checked="checked">
                                <span class="checkmark"></span>
                            </label>
                        </div>
                        <div class="price">
                            <h2 style="grid-column-start: 1;">Price</h2>
                            <div class="input-box" style="grid-column-start: 1;">
                                <!--i class="fas fa-user"></i-->
                                <label>   Your tax: </label>
                                <input style="height:90%" v-model="ad.price" type="text" placeholder="5 €" required>
                            </div>
                            <div class="save-button" style="grid-column-start: 2;grid-row-start: 2;">
                                <div class="loader" v-if="processing"></div>
                                <button class="button button2" type="submit" v-else><i class="fa fa-pencil"></i> Save </button>
                            </div>
                            <div class="save-button" style="grid-column-start: 2;grid-row-start: 3;">
                                <button class="button button2" @click="back()" style="background-color: rgba(154, 154, 154, 0.953);" ><i class="fa fa-close"></i> Cancel </button>
                            </div>
                        </div>
                        
                        
                    </div>
                    <div v-if="!avValid" style="color:red; padding-top:2%"> <b>* You must define at least one avalability day!</b> </div>
                    <div v-if="!anValid" style="color:red; padding-top:2%"> <b>* You must choose at least one animal option!</b> </div>
                    <div v-if="!servValid" style="color:red; padding-top:2%"> <b>* You must choose at least one service option!</b> </div>
                </div>
            </div>
            </form>
        </div>
    </body>
</template>

<script>
import Nav from './Nav.vue';
import Load from './Load.vue';
import router from '../router';
import PetsittersService from '@/services/PetsittersService';

export default {
    name: 'ClientProfile',
    components: { Nav, Load },
    data() {
        this.$store.state.page = 'petsitterProfile'
        return {
            daysleft: ['Monday','Tuesday','Wednesday','Thursday'],
            daysright: ['Friday','Saturday', 'Sunday'],
            ad: { 
                Monday : {start: '', end: ''},
                Tuesday : {start: '', end: ''},
                Wednesday : {start: '', end: ''},
                Thursday : {start: '', end: ''},
                Friday : {start: '', end: ''},
                Saturday : {start: '', end: ''},
                Sunday : {start: '', end: ''},
                animals : {
                    dog : false,
                    cat : false,
                    reptile: false,
                    horse: false
                },
                services : {
                    companion : false,
                    bath_shear : false,
                    training : false
                },
                price: '',
                
            },
            avValid: true,
            anValid: true,
            servValid: true,
            waiting: true,
            processing: false
        }
    },
    methods: {
        validOptions() {
            var avCounter = 0;
            for(var d in this.daysleft) {
                console.log(d);
                if (this.ad[this.daysleft[d]].start !== '' && this.ad[this.daysleft[d]].end !== '') {
                    avCounter += 1;
                    break;
                }
            }
            if (avCounter === 0) {
                for(var dr in this.daysright) {
                    if (this.ad[this.daysright[dr]].start !== '' && this.ad[this.daysright[dr]].end !== '') {
                        avCounter += 1;
                        break;
                    }
                }
            }
            if(avCounter > 0) {
                this.avValid = true;
            }
            else {
                this.avValid = false;
            }
            var anCounter = 0;
            for (var an in this.ad.animals) {
                if(this.ad.animals[an]) {
                    anCounter += 1;
                    break;
                }
            }
            if(anCounter > 0) {
                this.anValid = true;
            }
            else {
                this.anValid = false;
            }
            var servCounter = 0;
            for (var sv in this.ad.services) {
                if(this.ad.services[sv]) {
                    servCounter += 1;
                    break;
                }
            }
            if(servCounter > 0) {
                this.servValid = true;
            }
            else {
                this.servValid = false;
            }
            return this.servValid && this.anValid && this.avValid;
            
        },
        editAd() {
            if (this.validOptions()) {
                this.processing = true;
                console.log(this.ad.price);
                var req = {
                    Monday : [this.ad.Monday.start, this.ad.Monday.end],
                    Tuesday : [this.ad.Tuesday.start, this.ad.Tuesday.end],
                    Wednesday : [this.ad.Wednesday.start, this.ad.Wednesday.end],
                    Thursday : [this.ad.Thursday.start, this.ad.Thursday.end],
                    Friday : [this.ad.Friday.start, this.ad.Friday.end],
                    Saturday : [this.ad.Saturday.start, this.ad.Saturday.end],
                    Sunday : [this.ad.Sunday.start, this.ad.Sunday.end],
                    animals: [this.ad.animals.dog, this.ad.animals.cat, this.ad.animals.reptile, this.ad.animals.horse],
                    services: [this.ad.services.companion, this.ad.services.bath_shear, this.ad.services.training],
                    price: this.ad.price
                }
                this.$store.dispatch('editAd',req);
            }
        },
        back() {
            router.push('/petsitterProfile');
        }

    },
    created() {
        PetsittersService.getAd().then((response) => {
                console.log(response);
                const av = response.availability;
                for(var a in av) {
                    if (av[a].startTime !== null && av[a].endTime !== null) {
                        this.ad[a].start = av[a].startTime.split(":")[0] + ":" + av[a].startTime.split(":")[1];
                        this.ad[a].end = av[a].endTime.split(":")[0] + ":" + av[a].endTime.split(":")[1];
                    }
                }
                const an = response.species;
                for(var s in an) {
                    this.ad.animals[an[s].toLowerCase()] = true;
                    
                }
                const ss = response.services;
                for(var sv in ss) {
                    this.ad.services[ss[sv].serviceType.toLowerCase()] = true;
                    
                }
                this.ad.price = response.tax;
                this.waiting = false;
            })
    }
}
</script>

<style scoped>
.loader {
  position: relative;
  top: 15%;
  left: 45%;
  border: 5px solid rgb(211, 211, 211);
  border-radius: 50%;
  border-top: 5px solid #74C9E3;
  width: 20px;
  height: 20px;
  -webkit-animation: spin 2s linear infinite; /* Safari */
  animation: spin 2s linear infinite;
}

/* Safari */
@-webkit-keyframes spin {
  0% { -webkit-transform: rotate(0deg); }
  100% { -webkit-transform: rotate(360deg); }
}

@keyframes spin {
  0% { transform: rotate(0deg); }
  100% { transform: rotate(360deg); }
}
.back-button{
    display: grid;
    grid-template-columns: 50% 50%;
}

.button-back:hover {
    color: white; 
    background-color: rgba(154, 154, 154, 0.953);
}

.button-back{
    grid-column-start: 2;
    grid-row-start: 1;
    width: 30%;
    position: relative;
    height: 200%;
    background-color: #74C9E3; /*6CC4C7*/
    color: white; 
    border-radius: 20px;
    border: none;
    font-size: 16px;
    margin: 4px 2px;
    transition-duration: 0.4s;
    cursor: pointer;
    bottom:250%;
    left:30%;
}

.calendar {
    display: grid;
        
}
.calendar table {
    border-collapse: separate;
    border-spacing: 0 15px;
    
}
.daysleft{
    grid-column-start: 1;
}
.daysright{
    grid-column-start: 2;
}

.addPet {
    display: grid;
    grid-template-columns: 50% 50%;
    grid-template-rows: 45% 55%;
    height:100%;
}

.filter-animals {
    text-align: left;
    position: relative;
    vertical-align: middle; 
    top: 0;
    width: 180%;
    grid-column-start: 1;
    padding-left: 3%;
}

.filter-service {
    text-align: left;
    position: relative;
    vertical-align: middle; 
    top: 0;
    width: 180%;
    grid-column-start: 2;
    padding-left: 3%;
}

.price {
    display:grid;
    grid-template-columns: 50% 50%;
    grid-template-rows: 35% 15% 15% 35%;
    row-gap: 5%;
    text-align: left;
    position: relative;
    vertical-align: middle;
    top: 0;
    width: 180%;
    height: 100%;
    grid-column-start: 1;
    padding-left: 3%;
}



.box-option {
  display: block;
  position: relative;
  padding-left: 35px;
  margin-bottom: 12px;
  cursor: pointer;
  font-size: 18px;
  -webkit-user-select: none;
  -moz-user-select: none;
  -ms-user-select: none;
  user-select: none;
}

/* Hide the browser's default checkbox */
.box-option input {
  position: absolute;
  opacity: 0;
  cursor: pointer;
  height: 0;
  width: 0;
}

/* Create a custom checkbox */
.checkmark {
  position: absolute;
  top: 0;
  left: 0;
  height: 25px;
  width: 25px;
  background-color: #ccc;
}

/* On mouse-over, add a grey background color */
.box-option:hover input ~ .checkmark {
  background-color: #2195f377;
}

/* When the checkbox is checked, add a blue background */
.box-option input:checked ~ .checkmark {
  background-color: #74C9E3;
}

/* Create the checkmark/indicator (hidden when not checked) */
.checkmark:after {
  content: "";
  position: absolute;
  display: none;
}

/* Show the checkmark when checked */
.box-option input:checked ~ .checkmark:after {
  display: block;
}

/* Style the checkmark/indicator */
.box-option .checkmark:after {
  left: 9px;
  top: 5px;
  width: 5px;
  height: 10px;
  border: solid white;
  border-width: 0 3px 3px 0;
  -webkit-transform: rotate(45deg);
  -ms-transform: rotate(45deg);
  transform: rotate(45deg);
}

.profileContainer {
    color: rgb(92, 89, 89);
    display: grid;
    grid-template-columns: 65% 40%;
}
.requestsContainer {
    text-align: left;
    grid-column-start: 1;
    padding-left: 3%;
}
.petsContainer {
    text-align: left;
    grid-column-start: 2;
    padding-left: 3%;
    padding-top: 17%;
}

.button {
    border: none;
    color: white;
    padding: 5px 5px;
    text-align: center;
    text-decoration: none;
    display: inline-block;
    font-size: 16px;
    transition-duration: 0.4s;
    cursor: pointer;
}

.button2 {
    position: relative;
    background-color: #74C9E3; /*6CC4C7*/
    color: white; 
    border-radius: 12px;
    width:60%;
    left: 20%;
    height:100%;
}

.button2:hover {
    color: white; 
    background-color: rgba(154, 154, 154, 0.953);

}


.container {
    background-color: rgba(255, 255, 255, 0.761);
    color: rgb(92, 89, 89);
}

.required:after {
    content:" *";
    color: red;
  }

</style>


