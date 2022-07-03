<template>
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link href="css/bootstrap.css" rel="stylesheet">
    <link href="style/starter-template.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
  </head>
  <body>
      <div class="container" id="petit">
      <Nav/>
      <div class="loading" v-if="waiting">
        <Load/>
      </div>

      <div class="homepage" v-else>
      <div class="filters">
        <form>
          <fieldset class="form-group">
            <legend><h2>Filter by</h2></legend>
            <div class="filter-service">
              <div class="title-s">
                <h2>Services</h2>
              </div>
              <label class="box-option">Companion
              <input type="checkbox" value="Companion" v-model="categories">
              <span class="checkmark"></span>
              </label>
              <label class="box-option">Bath/Shear
                <input type="checkbox" value="Bath_Shear" v-model="categories">
                <span class="checkmark"></span>
              </label>
              <label class="box-option">Training
                <input type="checkbox" value="Training" v-model="categories" >
                <span class="checkmark"></span>
              </label>
            </div>
            <div>&nbsp;</div>
            <div class="filter-animals">
              <div class="title-a">
                <h2>Animals</h2>
              </div>
              <label class="box-option">Dog
              <input type="checkbox" value="Dog" v-model="categories">
              <span class="checkmark"></span>
              </label>
              <label class="box-option">Cat
                <input type="checkbox" value="Cat" v-model="categories">
                <span class="checkmark"></span>
              </label>
              <label class="box-option">Reptile
                <input type="checkbox" value="Reptile" v-model="categories">
                <span class="checkmark"></span>
              </label>
              <label class="box-option">Horse
                <input type="checkbox" value="Horse" v-model="categories">
                <span class="checkmark"></span>
              </label>
            </div>
          </fieldset>
        </form>

        <div class="sortBy">
        <form>
          <fieldset class="form-group">
            <legend><h2>Sort by</h2></legend>
            <div class="sort-name">
              <div class="title-s">
                <h2>Name</h2>
              </div>
              <select class="form-select" id="sortByName">
                <option value="" selected>Name</option>
                <option value="asc">Ascending</option>
                <option value="desc">Descending</option>
              </select>
            </div>
            <div class="sort-price">
              <div class="title-a">
                <h2>Price</h2>
              </div>
              <select class="form-select" id="sortByPrice">
                <option value="" selected>Price</option>
                <option value="asc">Ascending</option>
                <option value="desc">Descending</option>
              </select>
            </div>
          </fieldset>
        </form>
        </div>
      </div>

        <div class="feed">
          <div class="row" v-for="i in parseInt((newAds.length/3).toFixed(0))" :key="i">
            <div class="column" v-for="j in 3" :key="j">
              <div class="card" v-if="(i-1)*3+(j-1) < newAds.length" >
                <img src="../images/avatar.jpg" alt="avatar" style="width:100%" class="userPic">

                <div class="text-card">
                  <h2 class="name">{{newAds[(i-1)*3+(j-1)].username}}</h2>
                  <h3 class="title">Tax: {{newAds[(i-1)*3+(j-1)].ad.tax}}€</h3>
                  <div class="services">
                    <l v-for="s in newAds[(i-1)*3+(j-1)].ad.services" :key="s">
                    <li v-if="s.serviceType==='Bath_Shear'">Bath and Shear</li>
                    <li v-else>{{s.serviceType}}</li>
                    </l>
                  </div>
                  <div v-if="newAds[(i-1)*3+(j-1)].ad.services.length === 2 "><ul style="list-style-type:none;"><li></li></ul></div>
                  <div v-if="newAds[(i-1)*3+(j-1)].ad.services.length === 1 "><ul style="list-style-type:none;"><li></li><li></li></ul></div>
                  <div class="icons">
                    <i id="icon" v-if="compare(newAds[(i-1)*3+(j-1)].ad.species,'Cat')" class="fas fa-cat" style='font-size:26px'>&nbsp;</i>
                    <i id="icon" v-if="compare(newAds[(i-1)*3+(j-1)].ad.species,'Dog')" class="fas fa-dog" style='font-size:26px'>  </i>
                    <i id="icon" v-if="compare(newAds[(i-1)*3+(j-1)].ad.species,'Horse')" class="fas fa-horse-head" style='font-size:23px'>  </i>
                    <i id="icon" v-if="compare(newAds[(i-1)*3+(j-1)].ad.species,'Reptile')" class="fas fa-frog" style='font-size:23px'></i>
                  </div>
                </div>
                <p class="bu"><button @click="select(newAds[(i-1)*3+(j-1)].email)">Select</button></p> 
              </div>
            </div>
          </div>
        </div>

        <!--div class="feed" >
          <div class="row" v-for="i in parseInt((ads.length/3).toFixed(0))" :key="i">
            <div class="column" v-for="j in 3" :key="j">
              <div class="card" v-if="(i-1)*3+(j-1) < ads.length" >
                <img src="../images/avatar.jpg" alt="John" style="width:100%" class="userPic">
                <div class="text-card"> 
                  <h2 class="name">{{ads[(i-1)*3+(j-1)].username}}</h2>
                  <h3 class="title">Tax: {{ads[(i-1)*3+(j-1)].ad.tax}}€</h3>
                  <div class="services">
                    <l v-for="s in ads[(i-1)*3+(j-1)].ad.services" :key="s">
                    <li v-if="s.serviceType==='Bath_Shear'">Bath and Shear</li>
                    <li v-else>{{s.serviceType}}</li>
                    </l>
                  </div>
                  <div v-if="ads[(i-1)*3+(j-1)].ad.services.length === 2 "><ul style="list-style-type:none;"><li></li></ul></div>
                  <div v-if="ads[(i-1)*3+(j-1)].ad.services.length === 1 "><ul style="list-style-type:none;"><li></li><li></li></ul></div>
                  <div class="icons">
                    <i id="icon" v-if="compare(ads[(i-1)*3+(j-1)].ad.species,'Cat')" class="fas fa-cat" style='font-size:26px'>&nbsp;</i>
                    <i id="icon" v-if="compare(ads[(i-1)*3+(j-1)].ad.species,'Dog')" class="fas fa-dog" style='font-size:26px'>  </i>
                    <i id="icon" v-if="compare(ads[(i-1)*3+(j-1)].ad.species,'Horse')" class="fas fa-horse-head" style='font-size:23px'>  </i>
                    <i id="icon" v-if="compare(ads[(i-1)*3+(j-1)].ad.species,'Reptile')" class="fas fa-frog" style='font-size:23px'></i>
                  </div>
                </div>
                <p class="bu"><button @click="select(ads[(i-1)*3+(j-1)].email)">Select</button></p> 
              </div>
            </div>
          </div>
        </div-->
      </div>
    </div>
  </body>
  
</template>

<script>
import Nav from './Nav.vue';
import Load from './Load.vue';
import PetsittersService from '../services/PetsittersService'
import router from '../router';
 
export default {
  name: "MyHome",
  components: {Nav , Load},
  data() {
    this.$store.dispatch('setNewPage', 'home');
    return{
      categories: [],
      ads : [],
      newAds : [],
      aux : [],
      waiting: true
    }
  },
   methods: {
    getAllAds(){
      PetsittersService.getAllPetsitters().then((response) =>{
        this.aux = response.data
        for(let i = 0; i < this.aux.length; i++){
          if(this.aux[i].ad.services.length!=0) this.ads.push(this.aux[i])
        }
        this.waiting = false;
        console.log(this.ads);
        console.log((this.ads.length/3).toFixed(0));
      })
    },
    compare(sps, object){
      for(var s in sps){
        if(sps[s] === object) return true;
      }
      return false;
    },
    select(petsitterId) {
      router.push('/advertisement/' + petsitterId);
    }
  },
  created() {
    this.getAllAds()
  },
  computed: {
    selectedItems: function () {
      console.log("Entrouuuu")
      return this.ads.filter(function (item) {
        var counter = 0;

        for(var s in item.ad.species){
          if(this.categories.includes(item.ad.species[s])) counter++;
          
        }
        console.log(item.ad.services)
        for(var i in item.ad.services){
          console.log(item.ad.services[i].serviceType)
          console.log(this.categories)
          if(this.categories.includes(item.ad.services[i].serviceType)) counter++;
        }
        console.log(counter===this.categories.length);
        return(counter===this.categories.length);
      }, this);
    }
  },
  watch: {
    selectedItems(newAds) {
      this.newAds = newAds;
    }
  }
}


</script>

<style scoped>




.container {
  background-color: rgba(255, 255, 255, 0.561);
  color: rgb(92, 89, 89);
  
}

.card {
  box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2);
  max-width: 300px;
  margin: auto;
  text-align: center;
  margin-top:50px;
  
}

.name{
  color: #05151a;
  text-align: center; 
}


.services{
  text-align: left;
  vertical-align: middle; 
  padding-left: 30%;
  font-size: 17px;
}

.icons{
  padding-top: 10%;
  padding-bottom: 6%;
}

#icon{
  padding-left: 0.5em;
}

.userPic {
  border-radius: 50%;
  max-width: 50%;
  position: relative;
  left: 0;
  right: 0;
  box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2);
  margin-top:-50px;
}

.title {
  color: #1a1905;
  font-size: 18px;
  text-align: center;
  vertical-align: middle; 
}


button {
  border: none;
  outline: 0;
  display: inline-block;
  padding: 8px;
  color: white;
  background-color: rgba(50, 50, 50, 0.889);
  text-align: center;
  cursor: pointer;
  width: 100%;
  font-size: 18px;
}

a {
  text-decoration: none;
  font-size: 22px;
  color: black;
}

button:hover, a:hover {
  opacity: 0.7;
}

/* Float four columns side by side */
.column {
  float: right;
  width: 25%;
  padding: 10px 20px;
}

/* Remove extra left and right margins, due to padding */
.row {
  margin: 0 -5px;
  grid-column-start: 1;
}

/* Clear floats after the columns */
.row:after {
  content: "";
  display: table;
  clear: both;
}

/* Responsive columns */
@media screen and (max-width: 600px) {
  .column {
    width: 100%;
    display: block;
    margin-bottom: 20px;
  }
}

.homepage{
  display: grid;
  grid-template-columns: 10% 90%;
  
}

.feed{
  padding-top: 3%;
  grid-column-start: 2;
  
  grid-row-start: 1;
  grid-row-end: 6;
}

.filters {
  grid-row-start: 1;
  padding:40px;
  text-align: left;
  position: relative;
  vertical-align: middle; 
  position: -webkit-sticky;
  position: sticky;
  top:0;
  width: 200%;
  align-self: start;
  
}

.form-select{
    width: 95%;
    font-size: 16px;
    padding: 12px 0px 12px 4%;
    border: 1px solid #ddd;
    color: rgb(103, 101, 101);
}

.form-group{
  background: #eef0f28c;
  box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2);
  border: 0;
}

.title-a{
  font-size: 15px;
  color: #74C9E3;
}

.title-s{
  font-size: 15px;
  color: palevioletred;
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



</style>
