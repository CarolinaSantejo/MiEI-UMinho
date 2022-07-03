<template>
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <link href="https://unpkg.com/ionicons@5.4.0/dist/ionicons.js">
    </head>
    <body>
        <div class="container" id="petit">
            <Nav/>
            <div class="loading" v-if="waiting">
                <Load/>
            </div>
            <form v-on:submit.prevent="makeRequest" v-else>
            <div class="profileContainer">
                <div class="requestsContainer">
                    <h1>Make your request:</h1>
                    <h2 class="text-center"> 1. Select the date</h2>
                    <div class="select-date">
                        
                        <div class="calendar">
                            <table class="tablereq table-striped" style="width:75%">
                                <thead>
                                    <th></th>
                                    <th> Date </th>
                                    <th> Time </th>
                                </thead>
                                <tbody>
                                    <tr>
                                        <td> <b>Start</b> </td>
                                        <td>
                                            <div v-if="!startDateTime.validDate" class="required"><input type="date" v-model="startDateTime.date" @change="validDate()" v-bind:min="getTodayDate()" placeholder="Enter your birthdate" required> </div>
                                            <input v-else type="date" v-model="startDateTime.date" @change="validDate()" v-bind:min="getTodayDate()" placeholder="Enter your birthdate" required>
                                        </td>
                                        <td>
                                            <div v-if="!startDateTime.validTime" class="required"><input type="time" id="start" @change="validTime()" v-model="startDateTime.time" name="start" v-bind:min="minTime" v-bind:max="maxTime" required></div>
                                            <input v-else type="time" id="start" @change="validTime()" v-model="startDateTime.time" name="start" v-bind:min="minTime" v-bind:max="maxTime" required>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td> <b>End</b> </td>
                                        <td>
                                            <div v-if="!endDateTime.validDate" class="required"><input type="date" v-model="endDateTime.date" @change="validDate()" v-bind:min="startDateTime.date" placeholder="Enter your birthdate" required></div>
                                            <input v-else type="date" v-model="endDateTime.date" @change="validDate()" v-bind:min="startDateTime.date" placeholder="Enter your birthdate" required>
                                        </td>
                                        <td>
                                            <div v-if="!endDateTime.validTime" class="required"><input type="time" id="start" @change="validTime()" v-model="endDateTime.time" name="start" v-bind:min="startDateTime.time" v-bind:max="maxTime" required></div>
                                            <input v-else type="time" id="start" @change="validTime()" v-model="endDateTime.time" name="start" v-bind:min="minTimeEnd" v-bind:max="maxTimeEnd" required>
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                            <div v-if="duration < 30" style="color:red; padding-top:3%; font-size: 75%;">* Invalid time (Duration less than 30 minutes)</div>
                            <div v-else>
                                <div v-if="!startDateTime.validDate || !endDateTime.validDate" style="color:red; padding-top:3%; font-size: 75%;">* Invalid date (Petsitter not available)</div>
                                <div v-if="!startDateTime.validTime || !endDateTime.validTime" style="color:red; padding-top:3%; font-size: 75%;">* Invalid time (Petsitter not available)</div>
                            </div>
                            
                            
                        </div>
                        <div v-if="(startDateTime.validDate || endDateTime.validDate) && startDateTime.date !== ''">
                            <table class="tablereq table-striped" style="width:95% ">
                                <thead>
                                    <th> Day </th>
                                    <th> Available Hours </th>
                                </thead>
                                <tbody>
                                    <tr v-for="obj in availableDay" v-bind:key = "obj">
                                        <td> {{obj.date.getDate()}}/{{obj.date.getMonth()+1}} </td>
                                        <td> {{obj.start}} - {{obj.end}} </td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                    <h2 class="text-center"> 2. Select your pet(s)</h2>
                    <table class="tablepet table-striped" style="width:85%">
                        <tbody>
                            <tr style="line-height: 10px;" v-for = "pet in pets" v-bind:key = "pet.name">
                                <td style="width:28%"> 
                                    <img v-bind:src="require('../images/'+ imgs[pet.species])" alt="Avatar" style="border-radius: 50%; height:120px; width:120px;box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);"> 
                                </td>
                                <td > 
                                    <label class="radio-option">{{pet.name}}
                                        <input type="radio" name="radiopet" v-model="petSelected" v-bind:value="pet.id + ':' + pet.weight" required>
                                        <span class="radiomark"></span>
                                    </label>  
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </div>
                <div class="serviceContainer" style="width:85%">
                    <div class="selectTitle">
                        <h2 class="text-left"> 3. Select your service</h2>
                    </div>
                    <div v-for="service in services" v-bind:key = "service.id">
                        <h3 class="text-left"><input type="radio" name="radio" v-model="option" v-bind:value="service.serviceType" required> {{service.serviceType}} </h3>
                        
                        <table class="tablereq table-striped" style="width:95%">
                            <thead>
                                <th style="width:50%;" v-if="service.serviceType === 'Bath_Shear'"> Weight (kg) </th>
                                <th style="width:50%;" v-if="service.serviceType === 'Companion'"> No. of Hours </th>
                                <th style="width:50%;" v-if="service.serviceType === 'Training'"> Hours </th>
                                <th style="width:50%;"> Price </th>
                            </thead>
                            <tbody>
                                <tr v-for="obj in service.price" v-bind:key = "obj.id">
                                    <td> {{obj.opt}} </td>
                                    <td> {{obj.price}} €</td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                    <div class="tax">
                        <h3>+ tax = {{tax}} €</h3>
                    </div>
                    <div class="final-price">
                        <h2>Total = {{setTotal()}} €</h2>
                    </div>
                    <div class="cancel-save">
                        <div class="cancel">
                            <button class="button" @click="cancel()"><i class="fa fa-close"></i> Cancel</button>
                        </div>
                        <div class="save">
                            <div class="loader" v-if="processing" ></div>
                            <button class="button button2" v-else type="submit"><i class="fa fa-pencil"></i> Save</button>
                        </div>
                    </div>
                </div> 
            </div>
            </form>

        </div>
    </body>
</template>

<script>
import Nav from './Nav.vue';
import Load from './Load.vue';
import ClientProfileService from '@/services/ClientProfileService';
import router from '../router';

export default {
    name: 'MakeRequest',
    components: { Nav, Load },
    data() {
        return {
            petsitterId: '',
            imgs: {
                Cat: 'cat.jpg',
                Dog: 'dog2.png',
                Reptile: 'reptil.png',
                Horse: 'cavalo.png'
            },
            pets: [],
            services: [],
            tax: '',
            option: '',
            startDateTime: {
                date: '',
                time: '',
                validDate: true,
                validTime: true
            },
            endDateTime: {
                date: '',
                time: '',
                validDate: true,
                validTime: true
            },
            petSelected: '',
            total: 0,
            servOpt: '',
            availability: {},
            minTime: '',
            maxTime: '',
            minTimeEnd: '',
            maxTimeEnd: '',
            waiting: true,
            available: {},
            availableDay: [],
            processing: false,
            duration: 30
        }
    },
    methods: {
        makeRequest() {
            if (this.startDateTime.validDate && this.endDateTime.validDate && this.startDateTime.validTime && this.endDateTime.validTime) {
                this.processing = true;
                ClientProfileService.makeRequest(this.petsitterId, this.startDateTime.date, this.endDateTime.date , this.startDateTime.time, this.endDateTime.time, this.petSelected.split(':')[0], this.option, this.servOpt, this.total)
                .then((response) => {
                    this.processing = false;
                    if (response.res == true) {
                        alert("Request successfully submitted!");
                        router.push('/advertisement/' + this.petsitterId);
                    }
                    else {
                        
                        alert("Request not submitted! (Unavailable dates)");
                    }
                })
            }
            
            
        },
        validDate() {
            var date = this.startDateTime.date;
            for (var i = 0; i < 2; i++) {
                if (date !== '') {
                    var d = new Date(date);
                    const dayOfWeekName = d.toLocaleString(
                        'en-US', {weekday: 'long'}
                    );
                    if (this.availability[dayOfWeekName].startTime !== null && this.availability[dayOfWeekName].endTime !== null) {
                        if (i === 0) {
                            this.startDateTime.validDate = true;
                            this.minTime = this.availability[dayOfWeekName].startTime.split(':')[0] + ':' + this.availability[dayOfWeekName].startTime.split(':')[1];
                            this.maxTime = this.availability[dayOfWeekName].endTime.split(':')[0] + ':' + this.availability[dayOfWeekName].endTime.split(':')[1];
                        }
                        else {
                            this.endDateTime.validDate = true;
                            this.minTimeEnd = this.availability[dayOfWeekName].startTime.split(':')[0] + ':' + this.availability[dayOfWeekName].startTime.split(':')[1];
                            this.maxTimeEnd = this.availability[dayOfWeekName].endTime.split(':')[0] + ':' + this.availability[dayOfWeekName].endTime.split(':')[1];
                        }
                        
                    }
                    else {
                        if (i === 0) {
                            this.startDateTime.validDate = false;
                        }
                        else {
                            this.endDateTime.validDate = false;
                        }
                    }
                }
                date = this.endDateTime.date;
            }
            this.getAvailableHours();
        },
        validTime() {
            if (!this.startDateTime.validDate || !this.endDateTime.validDate) {
                this.startDateTime.validTime = false;
                this.endDateTime.validTime = false;
                this.duration = 30;
            }
            else {
                var startT = new Date(this.startDateTime.date + " " + this.startDateTime.time);
                var endT = new Date(this.endDateTime.date + " " + this.endDateTime.time);
                var diff = endT - startT;
                this.duration = Math.floor(((diff)/1000)/60);
                if (isNaN(this.duration)) {
                    this.duration = 30;
                }
                this.startDateTime.validTime = false;
                this.endDateTime.validTime = false;
                console.log(this.duration);
                if(this.duration >= 30) {
                    for (var elem in this.availableDay) {
                        var obj = this.availableDay[elem];

                        var sI = new Date(obj.date.getTime());
                        sI.setHours(obj.start.split(':')[0], obj.start.split(':')[1]);
                        var eI = new Date(obj.date.getTime());
                        eI.setHours(obj.end.split(':')[0], obj.end.split(':')[1]);
                        if (startT >= sI && startT <= eI && this.endDateTime.time === '') {
                            console.log("entrou start");
                            this.startDateTime.validTime = true;
                            this.endDateTime.validTime = true;
                            break;
                        }
                        else if (this.startDateTime.time === ''  && endT >= sI && endT <= eI) {
                            console.log("entrou end");
                            this.startDateTime.validTime = true;
                            this.endDateTime.validTime = true;
                            break;
                        }
                        else if (startT >= sI && endT <= eI) {
                            this.startDateTime.validTime = true;
                            this.endDateTime.validTime = true;
                            break;
                        }
                        else if(startT >= sI && eI.getHours() + ':' + eI.getMinutes() === '23:59') {
                            this.startDateTime.validTime = true;
                        }
                        else if(this.startDateTime.validTime === true) {
                            if (endT <= eI) {
                                this.startDateTime.validTime = true;
                                this.endDateTime.validTime = true;
                                break;
                            }
                            else {
                                this.endDateTime.validTime = false;
                                break;
                            }
                        }
                        
                    }
                }
                
            }
            console.log("ola");
        },
        setTotal() {
            if (this.option === 'Bath_Shear') {
                const weight = this.petSelected.split(':')[1];
                console.log(weight);
                var price = 0;
                this.services.forEach(element => {
                    
                    if (element.serviceType === this.option) {
                        //console.log(elem.);
                        const prices = element.price;
                        for (const obj in prices) {
                            var key = prices[obj].opt;
                            if (key.split('-').length > 1) {
                                const max = parseInt(key.split('-')[1]);
                                if (weight < max) {
                                    this.servOpt = key;
                                    price = prices[obj].price;
                                    break;
                                }
                            }
                            else {
                                this.servOpt = key;
                                price = prices[obj].price;
                            }
                        }
                    }
                    
                });
            }
            else if (this.option === 'Companion') {
                var start = new Date("1970-01-01 " + this.startDateTime.time);
                
                var end = new Date("1970-01-01 " + this.endDateTime.time);
                var hours = Math.floor((end - start) / 36e5);
                this.services.forEach(element => {
                    if (element.serviceType === this.option) {
                        //console.log(elem.);
                        const prices = element.price;
                        for (const obj in prices) {
                            var key = prices[obj].opt;
                            if (key.split('-').length > 1) {
                                const max = parseInt(key.split('-')[1]);
                                if (hours <= max) {
                                    this.servOpt = key;
                                    price = prices[obj].price;
                                    break;
                                }
                            }
                        }
                    }
                    
                });
            }
            else if (this.option === 'Training') {
                var startT = new Date("1970-01-01 " + this.startDateTime.time);
                var endT = new Date("1970-01-01 " + this.endDateTime.time);
                var hoursT = Math.floor((endT - startT) / 36e5);
                this.services.forEach(element => {
                    if (element.serviceType === this.option) {
                        //console.log(elem.);
                        const prices = element.price;
                        for (const obj in prices) {
                            price = prices[obj].price * hoursT;
                        }
                    }
                    
                });
            }
            this.total = this.tax + price;
            return this.tax + price;
        },
        getTodayDate() {
            var today = new Date();
            var dd = today.getDate();
            var mm = today.getMonth() + 1; //January is 0!
            var yyyy = today.getFullYear();

            if (dd < 10) {
                dd = '0' + dd;
            }

            if (mm < 10) {
                mm = '0' + mm;
            } 
                
            today = yyyy + '-' + mm + '-' + dd;
            return today;
        },
        cancel() {
            router.push('/advertisement/' + this.petsitterId);
        },
        getAvailableHours() {
            if (this.startDateTime.validDate || this.endDateTime.validDate) {
                var days = 1;
                if (this.startDateTime.date !== this.endDateTime.date) {
                    days = 2;
                }
                var d = new Date(this.startDateTime.date);
                d.setHours(0,0,0,0);
                this.availableDay = [{start: this.minTime, date: d}];
                var i = 0;
                for (var n = 0; n < days; n++) {
                    console.log(n);
                    if (n === 1) {
                        d = new Date(this.endDateTime.date);
                        this.availableDay[i] = {start: this.minTimeEnd, date: d};
                    }
                    
                    for (var key in this.available[d]) {
                        if (this.availableDay[i].start === this.available[d][key].start) {
                            this.availableDay[i].start = this.available[d][key].end;
                        }
                        else {
                            this.availableDay[i].end = this.available[d][key].start;
                            i++;
                            this.availableDay[i] = {date: d}
                            this.availableDay[i].start = this.available[d][key].end;
                        }
                        
                    }
                    if (n === 0) {
                        if (this.maxTime === this.availableDay[i].start) {
                            this.availableDay.splice(i, 1);
                        }
                        else {
                            this.availableDay[i].end = this.maxTime;
                            i++;
                        }
                    }
                    else {
                        if (this.maxTimeEnd === this.availableDay[i].start) {
                            this.availableDay.splice(i, 1);
                            i++;
                        }
                        else {
                            this.availableDay[i].end = this.maxTimeEnd;
                        }
                    }
                }
                if(this.availableDay.length === 0) {
                    this.startDateTime.validDate = false;
                    this.endDateTime.validDate = false;
                }
                console.log(this.availableDay);
            }
            
        }
    },
    created() {
        this.petsitterId = this.$route.params.id;

        if (Object.keys(this.$store.state.services).length === 0) {
            this.$store.dispatch('setServices');
        }

        ClientProfileService.getMakeRequestData(this.petsitterId).then((response) => {
            this.services = response.services;

            this.pets = response.animals;
            this.tax = response.tax;
            this.availability = response.availability;
            const apps = response.appointments;
            for (var key in apps) {
                var dateI = new Date(apps[key].start_date);
                var dateF = new Date(apps[key].end_date);
                if (dateI.getDate() === dateF.getDate()) {
                    var date = new Date(apps[key].start_date);
                    date.setHours(0,0,0,0);
                    if (!(date in this.available)) {
                        this.available[date] = []
                        
                    }
                    const obj = {
                        start: new Date(apps[key].start_date),
                        end: new Date(apps[key].end_date)
                    }
                    this.available[date].push(obj);
                }
                else {
                    dateI.setHours(0,0,0,0);
                    dateF.setHours(0,0,0,0);
                    if (!(dateI in this.available)) {
                        this.available[dateI] = []
                    }
                    if (!(dateF in this.available)) {
                        this.available[dateF] = []
                    }
                    const dateEnd = new Date(apps[key].start_date);
                    dateEnd.setHours(23,59,0,0);
                    const objI = {
                        start: new Date(apps[key].start_date),
                        end: new Date(dateEnd)
                    }
                    const dateStart = new Date(apps[key].end_date);
                    dateStart.setHours(0,0,0,0);
                    const objF = {
                        start: dateStart,
                        end: new Date(apps[key].end_date)
                    }

                    this.available[dateI].push(objI);
                    this.available[dateF].push(objF);
                }
            }
            console.log("Available")
            console.log(apps)
            console.log(this.available)
            for (var l in this.available) {
                this.available[l].sort((a,b) => a.start - b.start);
            }
            for (var i in this.available) {
                const elems = this.available[i];
                for (var j in elems) {
                    elems[j].start = String(elems[j].start.getHours()).padStart(2, '0') + ':' + String(elems[j].start.getMinutes()).padStart(2, '0');
                    elems[j].end = String(elems[j].end.getHours()).padStart(2, '0') + ':' + String(elems[j].end.getMinutes()).padStart(2, '0');
                }
            }
            this.waiting = false;
        });
    }
}
</script>

<style scoped>
.required:after {
    content:" *";
    color: red;
  }


.loader {
  position: relative;
  left: 42%;
  top:27%;
  border: 5px solid rgb(211, 211, 211);
  border-radius: 50%;
  border-top: 5px solid #74C9E3;
  width: 25px;
  height: 25px;
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

.profileContainer {
    color: rgb(92, 89, 89);
    display: grid;
    grid-template-columns: 50% 50%;
}
.requestsContainer {
    text-align: left;
    grid-column-start: 1;
    padding-left: 3%;
}
.serviceContainer {
    text-align: left;
    grid-column-start: 2;
    grid-column-end: 3;
    display: grid;
    padding-left: 3%;
    grid-template-columns: 55% 55%;
    grid-template-rows: 8% auto auto 12% 12%;
}
.selectTitle {
    grid-column-start: 1;
    grid-column-end: 3;
}

.tablepet {
    text-align: center;
    align-items: left;
    white-space:pre;
}


.tablepet td {
    height: 8em;
    text-align: center;
    padding-bottom: 2%;
    width:40%;
}
/*
.bath-shear {
    text-align: left;
    grid-column-start: 1;
    padding-left: 3%;

}

.training {
    text-align: left;
    grid-column-start: 1;
    padding-left: 3%;
}
*/
.final-price {
    text-align: left;
    grid-column-start: 1;
    padding-left: 3%;

}
.tax {
    text-align: left;
    grid-column-start: 1;
    padding-left: 3%;
}

.cancel-save {
    text-align: left;
    grid-column-start: 2;
    display:grid;
    grid-template-columns: 50% 50%;
}

.cancel{
    text-align: left;
    grid-column-start: 1;
    grid-column-end: 2;
    
}

.save{
    text-align: left;
    grid-column-start: 2;

}

.select-date {
    text-align: left;
    grid-column-start: 2;
    padding-left: 3%;
    display:grid;
    grid-template-columns: 60% 40%;
}

.calendar{
    text-align: left;
    grid-column-start: 1;
    padding-left: 3%;
    /*width:250px;*/
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
  width:100px;
  
}

/* Hide the browser's default checkbox */
.box-option input {
  position: absolute;
  opacity: 0;
  cursor: pointer;
  height: 0;
  width: 0;
  top:10px;
  
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

/* The container */
.radio-option {
  display: block;
  position: relative;
  padding-left: 35px;
  margin-bottom: 12px;
  cursor: pointer;
  font-size: 22px;
  -webkit-user-select: none;
  -moz-user-select: none;
  -ms-user-select: none;
  user-select: none;
  width:100px;
}

/* Hide the browser's default radio button */
.radio-option input {
  position: absolute;
  opacity: 0;
  cursor: pointer;
  top:10px;
}

/* Create a custom radio button */
.radiomark {
  position: absolute;
  top: 0;
  left: 0;
  height: 25px;
  width: 25px;
  background-color: #eee;
  border-radius: 50%;
}

/* On mouse-over, add a grey background color */
.radio-option:hover input ~ .radiomark {
  background-color: #ccc;
}

/* When the radio button is checked, add a blue background */
.radio-option input:checked ~ .radiomark {
  background-color: #74C9E3;
}

/* Create the indicator (the dot/circle - hidden when not checked) */
.radiomark:after {
  content: "";
  position: absolute;
  display: none;
}

/* Show the indicator (dot/circle) when checked */
.radio-option input:checked ~ .radiomark:after {
  display: block;
}

/* Style the indicator (dot/circle) */
.radio-option .radiomark:after {
	top: 9px;
	left: 9px;
	width: 8px;
	height: 8px;
	border-radius: 50%;
	background: white;
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


.button {
	border: none;
	color: white; 
	background-color: rgba(180, 177, 177, 0.953);
	text-align: center;
	text-decoration: none;
	display: inline-block;
	font-size: 16px;
	transition-duration: 0.4s;
	cursor: pointer;
    position: relative;
    top: 20%;
	width: 90%;
	height: 50%;
    border-radius: 12px;
}

.button:hover {
	color: white; 
	background-color: rgba(203, 202, 202, 0.953);
}

.button2 {
	position: relative;
	background-color: #74C9E3; /*6CC4C7*/
	color: white; 
	border-radius: 12px;
}

.button2:hover {
	color: white; 
	background-color: rgba(203, 202, 202, 0.953);
}

.month {
  padding: 5px 25px;
  width: 100%;
  background: #74C9E3;
  text-align: center;
}

.month ul {
  margin: 0;
  padding: 0;
}

.month ul li {
  color: white;
  font-size: 20px;
  text-transform: uppercase;
  letter-spacing: 3px;
}

.month .prev {
  float: left;
  padding-top: 10px;
}

.month .next {
  float: right;
  padding-top: 10px;
}

.weekdays {
  margin: 0;
  padding: 5px 0;
  width:100%;
  background-color: #ddd;
}

.weekdays li {
  display: inline-block;
  width: 13.6%;
  color: #666;
  text-align: center;
}

.days {
  padding: 5px 0;
  width: 100%;
  background: #eee;
  margin: 0;
}

.days li {
  list-style-type: none;
  display: inline-block;
  width: 13.6%;
  text-align: center;
  margin-bottom: 5px;
  font-size:12px;
  color: #777;
}

.days li .active {
  padding: 5px;
  background: #74C9E3;
  color: white !important
}

* {box-sizing: border-box;}
ul {list-style-type: none;}
body {font-family: Verdana, sans-serif;}

.container {
    background-color: rgba(255, 255, 255, 0.761);
    color: rgb(92, 89, 89);
}

</style>


