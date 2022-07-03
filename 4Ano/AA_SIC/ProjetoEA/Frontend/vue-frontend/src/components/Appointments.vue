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
    <div class="loading" v-if="waiting">
        <Load/>
    </div>
    <div class="page" v-else>
        <div class="requestsContainer">
            <h1 class="text-center"> Scheduled Appointments</h1>
            <table v-if="requests.length > 0" id="tableA" class="tablereq table-striped" style="width:95%">
                <thead>
                    <th @click="sort('start_date')"> Start Date </th>
                    <th @click="sort('start_time')"> Start time </th>
                    <th @click="sort('end_date')"> End Date </th>
                    <th @click="sort('end_time')"> End Time </th>
                    <th @click="sort('serviceType')"> Service </th>
                    <th @click="sort('pet')"> Pet </th>
                    <th @click="sort('clientId')"> Client </th>
                </thead>
                <tbody >
                    <tr v-for = "request in requests" v-bind:key = "request">
                        <td> {{request.start_date.split("T")[0]}} </td>
                        <td> {{request.start_date.split("T")[1].split(".")[0]}} </td>
                        <td> {{request.end_date.split("T")[0]}} </td>
                        <td> {{request.end_date.split("T")[1].split(".")[0]}} </td>
                        <td> {{request.serviceOptions.serviceType}} </td>
                        <td> {{request.animal.name}} </td>
                        <td> {{request.clientId}} </td>
                    </tr>
                </tbody>
            </table>

            <h4 v-else class="text-center" style="text-align:center"> No Appointments found</h4>
            
        </div>

        <div class="filters">
            <fieldset class="form-group">
            <legend><h2>Filter by</h2></legend>

            <input type="text" id="myInput" @keyup="myFunction" placeholder="Search for clients.." title="Type in a name">

            <!--div class="filter-service"-->
                <select class="form-select" id="filterByService" @change="serviceFilter()">
                    <option value="" selected>Service</option>
                    <option value="bath">Bath_Shear</option>
                    <option value="companion">Companion</option>
                    <option value="training">Training</option>
                </select>
            <!--/div-->

            <!--div class="filter-date"-->
				<input id="date" type="date" @change="dateFilter()" placeholder="Filter by date">
            <!--/div-->
            
          </fieldset>
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
  name: 'MyAppointments',
  components: { Nav, Load },
  data() {
      this.$store.state.page = 'appointments'
      return {
        requests: [],
        waiting: true,
        currentSort:'name',
		currentSortDir:'desc'
      }
  },
  methods:{
      sort(s) {
          if(s === this.currentSort) {
              this.currentSortDir = this.currentSortDir==='asc'?'desc':'asc';
          }
          this.currentSort = s;
          this.sortRequests();
      },
      sortRequests() {
          this.requests.sort((a,b) => {
              var x = '';
              var y = '';
              if (this.currentSort === 'start_date') {
                  x = a.start_date.split("T")[0];
                  y = b.start_date.split("T")[0];
              }
              else if (this.currentSort === 'end_date') {
                  x = a.end_date.split("T")[0];
                  y = b.end_date.split("T")[0];
              }
              else if (this.currentSort === 'start_time') {
                  x = a.start_date.split("T")[1].split(".")[0];
                  y = b.start_date.split("T")[1].split(".")[0];
              }
              else if (this.currentSort === 'end_time') {
                  x = a.end_date.split("T")[1].split(".")[0];
                  y = b.end_date.split("T")[1].split(".")[0];
              }
              else if (this.currentSort === 'serviceType') {
                  x = a.serviceOptions.serviceType;
                  y = b.serviceOptions.serviceType;
              }
              else if (this.currentSort === 'pet') {
                  x = a.animal.name;
                  y = b.animal.name;
              }
              else {
                  x = a[this.currentSort];
                  y = b[this.currentSort];
              }
              let modifier = 1;
              if(this.currentSortDir === 'desc') modifier = -1;
              if(x < y) return -1 * modifier;
              if(x > y) return 1 * modifier;
              return 0;
          });
      },
      myFunction(){
        var input, filter, table, tr, td, i, txtValue;
        input = document.getElementById("myInput");
        filter = input.value.toUpperCase();
        table = document.getElementById("tableA");
        tr = table.getElementsByTagName("tr");
        for (i = 0; i < tr.length; i++) {
            td = tr[i].getElementsByTagName("td")[5];
            if (td) {
                txtValue = td.textContent || td.innerText;
                console.log(txtValue)
                console.log(filter)
                if (txtValue.toUpperCase().indexOf(filter) > -1) {
                    console.log("true")
                    tr[i].style.display = "";
                } else {
                    tr[i].style.display = "none";
                }
            }       
        }
      },
      serviceFilter(){
        var filter, table, tr, td, i, txtValue;
        filter = document.querySelector('select');
        table = document.getElementById("tableA");
        tr = table.getElementsByTagName("tr");
        for (i = 0; i < tr.length; i++) {
            td = tr[i].getElementsByTagName("td")[3];
            if (td) {
                txtValue = td.textContent || td.innerText;
                console.log(txtValue);
                console.log(filter.options[filter.selectedIndex].text.toUpperCase());
                if (txtValue.toUpperCase()==filter.options[filter.selectedIndex].text.toUpperCase()) {
                    tr[i].style.display = "";
                } else if(filter.options[filter.selectedIndex].text.toUpperCase()=='SERVICE'){
                    tr[i].style.display = "";
                }
                else {
                    tr[i].style.display = "none";
                }
            }       
        }
      },
      dateFilter(){
        var input, filter, table, tr, td, i, txtValue;
        input = document.getElementById("date");
        filter = input.value.toUpperCase();
        table = document.getElementById("tableA");
        tr = table.getElementsByTagName("tr");
        for (i = 0; i < tr.length; i++) {
            td = tr[i].getElementsByTagName("td")[0];
            if (td) {
            txtValue = td.textContent || td.innerText;
            console.log(td)
            console.log(txtValue)
            var tabelDate = txtValue.split('-').map(Number);
            var dateOld = new Date(filter);
            var date = dateOld.getFullYear() +'-' + (dateOld.getMonth() +1) + '-' + dateOld.getDate()  ;
            var inputDate = date.split('-').map(Number);

            console.log(tabelDate);
            console.log(inputDate);
            console.log(filter);
            const equals = (a, b) => JSON.stringify(a) === JSON.stringify(b);
            if (equals(tabelDate,inputDate)) {
                tr[i].style.display = "";
            } else if(isNaN(inputDate[0])){
                tr[i].style.display = "";
            }
            else {
                tr[i].style.display = "none";
            }
            }       
        }
      },
      getAppointments(){
          PetsittersService.getAppointments().then((response) => {
              console.log(response);
              this.requests = response;
              this.sort('start_date');
              this.waiting = false;
          });
      }
    },
    created() {
        this.getAppointments();
    }
    
}
</script>

<style scoped>
.container {
  background-color: rgba(255, 255, 255, 0.561);
  color: rgb(92, 89, 89);
}

.page{
    display: grid;
    grid-template-columns: 70% 30%;
}

.requestsContainer {
    padding-top: 3%;
    text-align: left;
    grid-column-start: 1;
    padding-left: 3%;
}

.tablepet {
    text-align: center;
    align-items: left;
}



.filters{
    grid-column-start: 2;
    padding:40px;
    text-align: left;
    position: relative;
    vertical-align: middle; 
    position: -webkit-sticky;
    position: sticky;
    padding-top: 17%;
    padding-left:5%;
    width: 90%;
}

.form-group{
  background: #eef0f28c;
  box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2);
  border: 0;
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

.tablereq th:hover { 
    
    cursor:pointer;
    background-color: rgba(130, 130, 130, 0.889);
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

.requestsContainer {
    padding-top: 3%;
    text-align: left;
    grid-column-start: 1;
    padding-left: 3%;
}

.pagination a {
  color: black;
  float: left;
  padding: 8px 16px;
  text-decoration: none;
  transition: background-color .3s;
}

.pagination a.active {
  background-color: dodgerblue;
  color: white;
}

.pagination a:hover:not(.active) {background-color: #ddd;}



#myInput {
  background-image: url('https://www.w3schools.com/css/searchicon.png');
  background-position: 10px 10px;
  background-repeat: no-repeat;
  width: 86%;
  font-size: 16px;
  padding: 12px 0px 12px 10%;
  border: 1px solid #ddd;
  margin-bottom: 12px;
}

.form-select{
    width: 96.5%;
    font-size: 16px;
    padding: 12px 0px 12px 4%;
    margin-bottom: 12px;
    border: 1px solid #ddd;
    color: rgb(103, 101, 101);
}

#date{
    width: 92%;
    font-size: 16px;
    padding: 12px 0px 12px 4%;
    margin-bottom: 12px;
    border: 1px solid #ddd;
    color: rgb(103, 101, 101);
    font-family: Arial, Helvetica, sans-serif;
}


</style>
