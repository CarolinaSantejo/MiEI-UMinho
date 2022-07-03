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
            <div class="profileContainer">
                <form v-on:submit.prevent="registerAnimal">
                <div class="fillIn">
                    <div class="general">
                        <!--div class="back-button">
                            <button class="button-back" @click="back()">
                            <i class="fas fa-arrow-circle-left"></i> Back </button>
                        </div-->
                        <div class="photoContainer">
                            <!--div>
                                <img src="../images/peticon.png" alt="Avatar"  style="border-radius: 50%; height:120px; width:120px" >
                                <a class="caption" href="#" padding="20%">Add image</a>
                            </div-->
                            <div class="container-avatar" id="pet">
                                <input id='fileidPet' type='file' @change="loadFile($event)" hidden/>
                                <input class="btn" id='buttonid' type='button' value='Edit' @click="openDialog()"/>
                            </div>
                            <div style="position:relative; left:6%; top:1%;">
                                Pet photo
                            </div>
                        </div>
                        <div class="descriptions">
                            <table class="descriptionsNames" style="width:85%">
                                <tbody>
                                    <td><label class="required">Name</label></td> 
                                    <td><label class="required">Species</label></td>
                                    <td>Breed</td>
                                    <td><label class="required">Sex</label></td>
                                    <td><label class="required">Weight</label></td>
                                </tbody>
                            </table>
                        </div>
                        <div class="inputs">
                            <table class="inputsSpaces" style="width:85%">
                                <tbody>
                                    <td>
                                        <input id="namePet" type="text" v-model="name" placeholder="ex.: Bobby" required>
                                    </td> <div class="space2"></div>
                                    <td>
                                        <select name="species" v-model="species" id="species" required>
                                            <option value="Dog">Dog</option>
                                            <option value="Cat">Cat</option>
                                            <option value="Horse">Horse</option>
                                            <option value="Reptile">Reptile</option>
                                        </select>
                                    </td> <div class="space2"></div>
                                    <td>
                                        <input id="breed" type="text" v-model="breed" placeholder="ex.: Husky">
                                    </td> <div class="space2"></div>
                                    <td>
                                        <select name="sexPet" v-model="sex" id="sexPet" required>
                                            <option value="0">Male</option>
                                            <option value="1">Female</option>
                                            <option value="2">Undefined</option>
                                        </select>
                                    </td> <div class="space2"></div>
                                    <td>
                                        <input id="weightPet" type="number" v-model="weight" min="0" step="0.001" placeholder="ex: 5.0" required>
                                    </td>
                                </tbody>
                            </table>
                        </div>
                        <div class="descriptions2">
                            <table class="descriptionsNames2" style="width:85%">
                                <tbody>
                                    <td><label class="required">Age</label></td>
                                    <td>Diseases</td>
                                    <td>Note</td>
                                </tbody>
                            </table>
                        </div>
                        <div class="inputs2">
                            <table class="inputsSpaces" style="width:85%">
                                <tbody>
                                    <tr style="line-height: 10px;">
                                        <td>
                                            <input id="agePetYears" v-model="ageYears" type="number" min="0" max="100" placeholder="Years">
                                            <input id="agePetMonths" v-model="ageMonths" type="number" min="0" max="11" placeholder="Months">
                                        </td> <div class="space"></div>
                                        <td>
                                            <textarea id="diseasesPet" v-model="diseases" maxlength="300" placeholder="Please describe any disease that your pet might have"></textarea>
                                        </td> <div class="space"></div>
                                        <td>
                                            <textarea id="notePet" v-model="notes" maxlength="300" placeholder="Do you want to add anything else?"></textarea>
                                        </td>
                                        <td>
                                            <div class="saveButton">   
                                                <div class="save-button" style="grid-column-start: 1;">
                                                    <button class="button button2" @click="back()" style="background-color: rgba(154, 154, 154, 0.953);"><i class="fa fa-close"></i> Cancel </button>
                                                </div>                 
                                                <div class="save-button" style="grid-column-start: 2;">
                                                    <div class="loader" v-if="processing" ></div>
                                                    <button class="button button2" type="submit"  v-else><i class="fa fa-plus"></i> Add Pet </button>
                                                </div>
                                            </div> 
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>   
                </div>
                </form>
            </div>
        </div>
    </body>
</template>

<script>
import Nav from './Nav.vue';
import router from '../router';

export default {
  name: 'ClientProfile',
  components: { Nav },
  data() {
      return {
        name: '',
        species: '',
        breed: '',
        sex: '',
        weight: '',
        ageYears: '',
        ageMonths: '',
        diseases: '',
        notes: '',
        valid: true,
        processing: false,
        imgPet: new Image()
      }
  }, methods: {
    registerAnimal(){
      this.processing = true;
      const { name, species, breed, sex, weight, ageYears, ageMonths, diseases, notes } = this
      this.$store.dispatch('registerAnimal', { name, species, breed, sex, weight, ageYears, ageMonths, diseases, notes}).then(response => {
            this.valid = response;
            this.processing = false;
        })
        
    },
    back() {
        router.push('/clientProfile');
    },
    openDialog(){
      document.getElementById('fileidPet').click();
    },
    loadFile(event) {
      this.imgPet = document.getElementById('fileidPet');
      this.imgPet.src = URL.createObjectURL(event.target.files[0]);
      //this.img.width = 10;
      //this.img.height = 10;
      console.log(this.imgPet)
      document.getElementById('pet').style.background = 'url('+this.imgPet.src+')';
      document.getElementById('pet').style.backgroundSize = '110px 110px';

    }
  }
}
</script>

<style scoped>

.container-avatar {
  position: relative;
  vertical-align: middle;
  width: 100px;
  height: 100px;
  border-radius: 50%;
  box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);
  background: url('../images/dog2.png');
  background-repeat: no-repeat;
  background-size: 100px 100px;
}

.container-avatar:hover{
  background-image: linear-gradient(rgba(255,255,255,0.5), rgba(255,255,255,0.5)), url('../images/dog2.png');
  cursor: pointer;
}

.container-avatar:hover > .btn {
  display: block;
  font-size: larger;
  cursor: pointer;
}

.btn {
  display: none;
  position: absolute;
  top: 40%;
  right: 28%;
}
.loader {
  position: relative;
  left: 39%;
  top:10%;
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

.button-back:hover {
    color: white; 
    background-color: rgba(154, 154, 154, 0.953);
}

.button-back{
    grid-column-start: 3;
    width: 35%;
    position: relative;
    height: 170%;
    background-color: #74C9E3;  /*6CC4C7*/
    color: white; 
    border-radius: 20px;
    border: none;
    font-size: 16px;
    margin: 4px 2px;
    transition-duration: 0.4s;
    cursor: pointer;
    right:10%;
    top: 55%;
}


.general {
    position: relative;
    padding-top:2%;
    display: grid;
    grid-template-columns: 15% 10% 27.5% 10% 27.5% 5%;
    
}

.photoContainer {
    text-align: left;
    position: relative;
    position: sticky;
    top: 10%;
    grid-column-start: 1;
}

.descriptions {
    padding:60px;
    text-align: left;
    position: relative;
    vertical-align: middle; 
    position: -webkit-sticky;
    position: sticky;
    top: 0;
    width: 180%;
    grid-column-start: 2;
    padding-left: 3%;
    font-size: 18px;
}

.descriptions2 {
    padding:60px;
    text-align: left;
    position: relative;
    vertical-align: middle; 
    position: -webkit-sticky;
    position: sticky;
    top: 0;
    width: 180%;
    grid-column-start: 4;
    padding-left: 3%;
    font-size: 18px;
}

.tablepet {
    text-align: center;
    align-items: left;
}


.tablepet td {
    height: 8em;
    text-align: center;
    padding-bottom: 2%;
    color: black;
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

.inputs {
    padding:30px;
    text-align: left;
    position: relative;
    vertical-align: middle; 
    position: -webkit-sticky;
    position: sticky;
    top: 0;
    width: 180%;
    grid-column-start: 3;
    padding-left: 3%;
    padding-top: 8%;
}

.inputs2 {
    padding:30px;
    text-align: left;
    position: relative;
    vertical-align: middle; 
    width: 180%;
    grid-column-start: 5;
}

.inputsSpaces {
    text-align: center;
    align-items: left;
    border-spacing: 30px 30px;
}

.inputsSpaces td {
    height: 5em;
    text-align: left;
    padding-bottom: 2%;
    display:block;
    white-space:pre;
}

.descriptionsNames {
    text-align: center;
    align-items: left;
    height: 3em;
    padding-bottom: 30%;
    display:block;
    white-space:pre;
    border-spacing: 0 30px;
}

.descriptionsNames td {
    height: 5em;
    text-align: left;
    padding-bottom: 35%;
    display:block;
    white-space:pre;
}

.descriptionsNames2 {
    text-align: center;
    align-items: left;
    height: 3em;
    padding-bottom: 30%;
    display:block;
    white-space:pre;
    border-spacing: 0 30px;
}

.descriptionsNames2 td {
    height: 5em;
    text-align: left;
    padding-bottom: 35%;
    display:block;
    white-space:pre;
}


.fillIn {
    text-align: left;
    grid-column-start: 1;
    padding-left: 3%;
}

.profileContainer {
    color: rgb(92, 89, 89);
    display: grid;
}
.saveButton {
    display: grid;
    grid-template-columns: 50% 50%;
    text-align: left;
    position:relative;
    margin-top:20%;
    left: 20%;
    width:50%;
}
.save-button{
    width: 100%;
    height: 100%;
    text-align: bottom;
}

.button {
    border: none;
    color: white;
    padding: 5% 15%;
    text-align: center;
    text-decoration: none;
    display: inline-block;
    font-size: 20px;
    transition-duration: 0.4s;
    cursor: pointer;
}

.button2 {
    background-color: #74C9E3; /*6CC4C7*/
    color: white; 
    border-radius: 12px;
}

.button2:hover {
    color: white; 
    background-color: rgba(154, 154, 154, 0.953);

}

textarea {
    resize: none;
    border: transparent;
    font-family: Arial, Helvetica, sans-serif;
    font-size: 16px;
    top: 10;
}


.container {
    background-color: rgba(255, 255, 255, 0.761);
    color: rgb(92, 89, 89);
}


input[type=text], input[type=number], #species, #sexPet, #notePet, #diseasesPet {
  width: 200px;
  padding: 12px 20px;
  margin: 14px 0px;
  display: inline-block;
  border: 1px solid #ccc;
  box-sizing: border-box;
}

#notePet, #diseasesPet {
    height: 100px;
    align-items: left;
    display:inline-block;
    overflow-wrap: break-word;
    text-align: left;
    width: 400px;
}

.aux {
    padding: 20px;
    align-items: center;
    justify-content: space-between;
    display: inline-block;
    text-align: center;
}

.caption {
    display: block;
    padding: 20px;
    
}

.space { margin-bottom: 20px; }

.space2 { margin-bottom: 20px; }


.required:after {
    content:" *";
    color: red;
  }


/* Add responsiveness - will automatically display the navbar vertically instead of horizontally on screens less than 500 pixels */
@media screen and (max-width: 500px) {
    .navbar a {
        float: none;
        display: block;
    }
}
</style>


