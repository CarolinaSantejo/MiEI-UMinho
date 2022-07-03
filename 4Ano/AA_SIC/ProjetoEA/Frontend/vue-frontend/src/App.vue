<template>
<body>
  <div id ="app">
    <!--Nav/-->
    <router-view/>
    <footer class="row">
        <div class="col-md-12 text-center">
            PetIt 2022 &copy;
          </div><!-- /.col -->
    </footer><!-- /.row -->
    <!--div class="footer">
      <p>PetIt 2022 &copy;</p>
    </div-->
  </div>
</body>  
</template>


<script>

export default {
  name: 'App',
  components: {

  }
}
</script>


<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
body {
  background-image: url('./images/patinhas.jpg');
  background-repeat: no-repeat;
  background-attachment: fixed;
  background-size: 100% 100%;
  color: rgb(148, 143, 143);
}
.footer {
  position: fixed;
  left: 0;
  bottom: 0;
  width: 100%;
  color: black;
  text-align: center;
}
</style>
