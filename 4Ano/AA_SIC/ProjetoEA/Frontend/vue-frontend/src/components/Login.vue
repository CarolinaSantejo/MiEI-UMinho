<template>
  <head>
    <link href="http://maxcdn.bootstrapcdn.com/font-awesome/4.1.0/css/font-awesome.min.css" rel="stylesheet">
  </head>
  
  <body>
    <div class="imgcontainer">
      <img src="../images/logo_petit.jpg" alt="Logo PetIt" class="logo">
    </div>
    <div class="container"  style="background-color:#f1f1f1">
    <form v-on:submit.prevent="login">
      <div class="input-boxes">
        <div class="input-box">
            <!--i class="fas fa-envelope"></i-->
            <label class="aux" >   Enter your Email</label>
            <input id="emailCliente" type="text" v-model="email" placeholder="example@petit.com" required>
        </div> 
        <div class="input-box">
            <!--i class="fas fa-lock"></i-->
            <label class="aux" >   Enter your password</label>
            <input id="passwordCliente" type="password" v-model="password" placeholder="*******" required>
        </div>
      </div>
      <!--label for="uname"><b>Username</b></label>
      <input type="text" placeholder="Enter Username" name="uname" required>

      <label for="psw"><b>Password</b></label>
      <input type="password" placeholder="Enter Password" name="psw" required>
          
      <button type="submit">Login</button>
      <label>
        <input type="checkbox" checked="checked" name="remember"> Remember me 
      </label-->
      
      
      <div class="loader"  v-if="waiting"></div>
      <button type="submit" v-else>Login </button>
      
    </form>
    
    <div v-if="!valid" style="color:red; padding-top:2%">Invalid email or password</div>
  </div>
  
  <div class="container" style="background-color:#f1f1f1">
    <!--button type="button" class="cancelbtn">Cancel</button-->
    <span class="psw">Don't have an account? Sign up <a href="/signupClient">here</a></span>
  </div>
  </body>
</template>

<script>
export default {
  name: 'MyLogin',
  data() {
    return {
      email: '',
      password: '',
      valid: true,
      waiting: false
    }
  },
  methods: {
    login(){
      console.log('entrar funcao login');
      const { email, password } = this
      this.waiting = true;
      this.$store.dispatch('login', {email, password}).then(response => {this.valid = response; this.waiting = false})
      
    }
  }
}



</script>

<style scoped>
body {font-family: Arial, Helvetica, sans-serif;}
form {border: 3px solid #f1f1f1;}

.loader {
  position: relative;
  left: 48%;
  border: 5px solid white;
  border-radius: 50%;
  border-top: 5px solid #74C9E3;
  width: 17px;
  height: 17px;
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

.aux{
  text-align:left;
  text-indent: 7px;
  float:left;
}

input[type=text], input[type=password] {
  width: 100%;
  padding: 12px 20px;
  margin: 20px 0;
  display: inline-block;
  border: 1px solid #ccc;
  box-sizing: border-box;
}

button {
  background-color: #74C9E3;
  color: white;
  padding: 14px 20px;
  margin: 8px 0;
  border: none;
  cursor: pointer;
  width: 40%;
  margin: auto;
}

button:hover {
  opacity: 0.8;
}

.cancelbtn {
  width: auto;
  padding: 10px 18px;
  width: 35%;
  margin: auto;
  background-color: #f44336;
}

.container {
  display: grid;
  padding: 20px;
  width: 35%;
  margin: auto;
  background-color: lightgrey;
}

span.psw {
  float: right;
  padding-top: 16px;
}

.imgcontainer {
  text-align: center;
  margin: 5px 0 12px 0;
}

img.logo {
  width: 30%;
  border-radius: 0%;
}
input-boxes{
  margin-top: 50px;
}
input-box{
  display: flex;
  align-items: center;
  height: 50px;
  width: 100%;
  margin: 10px 0;
  position: relative;
}
input-box input{
  height: 100%;
  width: 100%;
  outline: none;
  border: none;
  padding: 0 30px;
  font-size: 16px;
  font-weight: 500;
  border-bottom: 2px solid rgba(0,0,0,0.2);
  transition: all 0.3s ease;
}
input-box input:focus,
input-box input:valid{
  border-color: #7d2ae8;
}
input-box i{
  position: absolute;
  color: #7d2ae8;
  font-size: 17px;
}
</style>
