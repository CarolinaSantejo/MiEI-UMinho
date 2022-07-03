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
			<div class="profileContainer" v-else>
				<div class="requestsContainer">
					<h1 class="text-center"> Scheduled Requests</h1>
					<table id="table-requests" v-if="requests.length > 0" class="tablereq table-striped" style="width:95%">
						<thead>
							<th @click="sort('start_date')"> Start Date </th>
							<th @click="sort('start_time')"> Start time </th>
							<th @click="sort('end_date')"> End Date </th>
							<th @click="sort('end_time')"> End Time </th>
							<th @click="sort('serviceType')"> Service </th>
							<th @click="sort('pet')"> Pet </th>
							<th @click="sort('petsitter')"> Petsitter </th>
						</thead>
						<tbody >
							<tr v-for = "request in renderTable" v-bind:key = "request">
								<td> {{request.start_date.split("T")[0]}} </td>
								<td> {{request.start_date.split("T")[1].split(".")[0]}} </td>
								<td> {{request.end_date.split("T")[0]}} </td>
								<td> {{request.end_date.split("T")[1].split(".")[0]}} </td>
								<td> {{request.serviceOptions.serviceType}} </td>
								<td> {{request.animal.name}} </td>
								<td> {{request.petsitter}} </td>
							</tr>
						</tbody>
					</table>

					<h4 v-else class="text-center" style="text-align:center"> No requests found</h4>

					<div class="pagination" id="pagination" v-if="requests.length>pageSize">
						<a @click="prevPage">&laquo;</a>
						<a class="active" @click="selectPage(1)">1</a>
						<a v-for="i in (Math.ceil(parseInt((requests.length/6)))-1)" v-bind:key = "i" @click="selectPage(i+1)">{{i+1}}</a>
						<a @click="nextPage">&raquo;</a>
					</div>

				</div>

				<div class="petsContainer">
					<div class="addPet" style="width:85%">
						<h1 class="text-center" style="padding-left: 3%; grid-column-start: 1;"> My Pets</h1>
						<a href="/AddPet"> 
						<button class="button button2"  style="grid-column-start: 2;"><i class="fa fa-plus"></i> New Pet</button>
						</a>						
					</div>
					
					<table class="tablepet table-striped" style="width:85%">
						<tbody>
							<tr style="line-height: 10px;" v-for = "pet in pets" v-bind:key = "pet.id">
								<td style="width:28%"> <img v-bind:src="require('../images/'+ imgs[pet.species])" alt="Avatar" style="border-radius: 50%; height:120px; width:120px;box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);"> </td>
								<td > <h2>{{pet.name}}</h2> </td>
							</tr>
						</tbody>
						
					</table>
				</div>
			</div>
		</div>
	</body>
</template>

<script>
import Nav from './Nav.vue';
import Load from './Load.vue';
import ClientProfileService from '@/services/ClientProfileService';


export default {
  name: 'ClientProfile',
  components: { Nav , Load},
  data() {
	return {
		requests: [],
		pets: [],
		imgs: {
			Cat: 'cat.jpg',
			Dog: 'dog2.png',
			Reptile: 'reptil.png',
			Horse: 'cavalo.png'
		},
		pageSize : 6,
		currentPage : 1,
		waiting: true,
		currentSort:'name',
		currentSortDir:'desc'
	}
  },
  methods: {
	getProfile(){
		ClientProfileService.getProfileData().then((response) => {
			this.requests = response.requests;
			this.sort('start_date');
			this.pets = response.animals;
			console.log(response);
			this.waiting = false;
		})
		
	},
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
	nextPage:function() {
		
        var pag = document.getElementById("pagination");
        var n = pag.getElementsByTagName("a");

		if((this.currentPage*this.pageSize) < this.requests.length){ 
			n[this.currentPage].classList.remove("active");
			this.currentPage++;
			console.log(n[1]);
			n[this.currentPage].classList.add("active");
	
		}
	},
	prevPage:function() {
		var pag = document.getElementById("pagination");
        var n = pag.getElementsByTagName("a");

		if(this.currentPage > 1){
			n[this.currentPage].classList.remove("active");
			this.currentPage--;
			n[this.currentPage].classList.add("active");
		}
	},
	selectPage:function(nPage){
		var pag = document.getElementById("pagination");
        var n = pag.getElementsByTagName("a");
		
		n[this.currentPage].classList.remove("active");
		this.currentPage = nPage;
		n[this.currentPage].classList.add("active");
	}
  },
  created() {
	this.$store.dispatch('setNewPage', 'profile');
	this.getProfile();
  },
  computed:{
	renderTable:function() {
		return this.requests.filter((row, index) => {
			console.log('aqui');
			console.log(row);
			let start = (this.currentPage-1)*this.pageSize;
			let end = this.currentPage*this.pageSize;
			if(index >= start && index < end) return true;
		});
	}
  }
}
</script>

<style scoped>
.addPet {
    display: grid;
    grid-template-columns: 70% 50%;
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

.tablereq th:hover {
	cursor:pointer;
	background-color: rgba(154, 154, 154, 0.953);
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
}

.button2 {
	position: relative;
	top: 20%;
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

.container {
    background-color: rgba(255, 255, 255, 0.761);
    color: rgb(92, 89, 89);
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


</style>


