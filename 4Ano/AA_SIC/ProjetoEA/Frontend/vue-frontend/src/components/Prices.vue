<template>
<head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <link href="https://unpkg.com/ionicons@5.4.0/dist/ionicons.js">
        
        <!--link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css"-->
        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Montserrat">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
</head>
<body>
<div class="container" id="petit">
    <Nav/>
    <div class="loading" v-if="Object.keys(this.$store.state.services).length === 0">
        <Load/>
    </div>
    <div v-else>
        <h1 class="text-title"> Services </h1>
        <hr class="w3-opacity" style="width:20%;">
        <div class="tables-price">
            <div class="table-bath">
                <h2 class="text-center">Bath/Shear</h2>
                <table v-if="baths !== null" class="tablereq table-striped">
                    <thead>
                        <th style="width:20%;"> Weight </th>
                        <th style="width:15%;"> Price </th>
                    </thead>
                    <tbody>
                        <tr v-for="obj in this.$store.state.services.baths" v-bind:key = "obj.id">
                            <td> {{obj.opt}} kg</td>
                            <td> {{obj.price}} €</td>
                        </tr>
                    </tbody> 
                </table>

            </div>
            <div class="table-companion">
                <h2 class="text-center">Companion</h2>
                <table v-if="companions !== null" class="tablereq table-striped">
                    <thead>
                        <th style="width:20%;"> No. of Hours </th>
                        <th style="width:15%;"> Price </th>
                    </thead>
                    <tbody>
                        <tr v-for="obj in this.$store.state.services.companions" v-bind:key = "obj.id">
                            <td> {{obj.opt}} h</td>
                            <td> {{obj.price}} €</td>
                        </tr>
                    </tbody> 
                </table>
            </div>
            <div class="table-training">
                <h2 class="text-center">Training</h2>
                <table v-if="training !== null" class="tablereq table-striped">
                    <thead>
                        <th style="width:20%;"> Hours </th>
                        <th style="width:15%;"> Price </th>
                    </thead>
                    <tbody>
                        <tr v-for="obj in this.$store.state.services.training" v-bind:key = "obj.id">
                            <td> {{obj.opt}} </td>
                            <td> {{obj.price}} €</td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
</body>
</template>

<script>
import Nav from './Nav.vue';
import Load from './Load.vue';

export default {
  name: 'ClientProfile',
  components: { Nav, Load },
  data() {
        this.$store.state.page = 'prices'
        
        return {
            baths: [],
            companions: [],
            training: [],
        }
        
    },
    created() {
        if (Object.keys(this.$store.state.services).length === 0) {
            this.$store.dispatch('setServices');
        }
        
    }
}
</script>

<style scoped>
.container {
  background-color: rgba(255, 255, 255, 0.561);
  color: rgb(92, 89, 89);
}

.text-title{
    color:rgb(75, 70, 70);
}

.tables-price {
    margin: 2% 0%;
    display: grid;
    grid-template-columns: 33% 33% 33%;
}

.table-bath {
    grid-column-start: 1;
    text-align: center;
    margin: 0% 30%;
}

.table-companion {
    grid-column-start: 2;
    text-align: center;
    margin: 0% 10%;
}

.table-training {
    grid-column-start: 3;
    text-align: center;
    margin: 0% 20%;

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

</style>
