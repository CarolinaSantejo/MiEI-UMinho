<template>
 <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css"> 
<div>
  <div class="row-top">
      <div class="container-avatar" id="avatar">
        <input id='fileid' type='file' @change="loadFile($event)" hidden/>
        <input class="btn" id='buttonid' type='button' value='Edit' @click="openDialog()"/>
      </div>
      <div class="username">
          <h1>{{username}}</h1>
      </div>
      <div class="logo">
        <img src="../images/logo_petit.jpg" alt="Avatar" style="width:55%">
      </div>
  </div>
    <div class="navbar" v-if="page=='petsitterProfile' || page=='appointments' || page=='prices'">
      <a href="/petsitterProfile" v-if="page === 'petsitterProfile'" class="active"><i class="fa fa-fw fa-user"></i>Profile</a>
      <a href="/petsitterProfile" v-else><i class="fa fa-fw fa-user"></i> Profile</a>

      <a href="/petsitterProfile/appointments" v-if="page === 'appointments'" class="active">  <i class="fas fa-calendar-alt" ></i> Appointments </a>
      <a href="/petsitterProfile/appointments" v-else>  <i class="fas fa-calendar-alt"></i> Appointments </a>

      <a href="/petsitterProfile/prices" v-if="page === 'prices'" class="active">  <i class="fa fa-dollar"></i> Prices </a>
      <a href="/petsitterProfile/prices" v-else>  <i class='fa fa-dollar'></i> Prices </a>

      <a @click="logout" class="right"><i class="fa fa-sign-out"></i> Logout</a>
    </div>


    <div class="navbar" v-else>
      <a href="/home" v-if="page === 'home'" class="active">  <i class="fa fa-fw fa-home"></i> Home </a>
      <a href="/home" v-else>  <i class="fa fa-fw fa-home"></i> Home </a>

      <a href="/clientProfile" v-if="page === 'profile'" class="active"><i class="fa fa-fw fa-user"></i>Profile</a>
      <a href="/clientProfile" v-else><i class="fa fa-fw fa-user"></i>Profile</a>

      <a @click="logout" class="right"><i class="fa fa-sign-out"></i> Logout</a>
    </div>

</div> 
</template>

<script>

export default {
  name: 'MyNav',
  data() {
    return {
      username: this.$store.state.username,
      page: this.$store.state.page,
      img: new Image()
    }
  },
  methods: {
    logout() {
      console.log('entrar funcao logout');
      this.$store.commit('logout')
    },
    openDialog(){
      document.getElementById('fileid').click();
    },
    loadFile(event) {
      this.img = document.getElementById('fileid');
      this.img.src = URL.createObjectURL(event.target.files[0]);
      //this.img.width = 10;
      //this.img.height = 10;
      console.log(this.img)
      document.getElementById('avatar').style.background = 'url('+this.img.src+')';
      document.getElementById('avatar').style.backgroundSize = '110px 110px';

    }
  },
  created() {
    //if utilizador não tem imagem
    // não faz nada
    //else 
    // this.img.src = url da BD ; avatar = imagem da BD
    // document.getElementById('avatar').style.background = url('../images/avatar.jpg');
    // 

  },

}

</script>


<style scoped>

.row-top {
  display: grid;
  grid-template-columns: 8% 70% 30%;

}

.username {
  padding: 5px;
  grid-column-start: 2;
  text-align: left;
  position: relative;
  vertical-align: middle;
  
  color: rgb(92, 89, 89);
}



.container-avatar {
  position: relative;
  left:4%;
  bottom:5%;
  vertical-align: middle;
  width: 100px;
  height: 100px;
  border-radius: 50%;
  box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);
  background: url('../images/avatar.jpg');
  background-repeat: no-repeat;
  background-size: 100px 100px;
}

.container-avatar:hover{
  background-image: linear-gradient(rgba(255,255,255,0.5), rgba(255,255,255,0.5)), url('../images/avatar.jpg');
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

.logo {
  grid-column-start: 3;
}

.navbar {
  width: 100%;
  background-color: rgba(50, 50, 50, 0.889);
  overflow: auto;
}


/* Navbar links */
.navbar a {
  float: left;
  text-align: center;
  padding: 14px;
  color: white;
  text-decoration: none;
  font-size: 17px;
}

/* Navbar links on mouse-over */
.navbar a:hover {
  background-color: rgba(154, 154, 154, 0.953);
}

/* Current/active navbar link */
.active {
  background-color: #15889d;
}

.navbar a.right {
  float: right;
  padding: 14px;
}

/* Add responsiveness - will automatically display the navbar vertically instead of horizontally on screens less than 500 pixels */
@media screen and (max-width: 500px) {
  .navbar a {
    float: none;
    display: block;
  }
}
</style>