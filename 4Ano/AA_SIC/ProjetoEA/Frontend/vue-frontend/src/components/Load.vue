<template>
    <div class="ringContainer">
        <br><br><br>
        <!--h3>Loading...</--h3-->
        <div class="ring"> </div>
    </div>
    
</template>

<script>
export default {
  name: 'MyLoad'
}
</script>

<style scoped>
.ringContainer {
    min-height: 300px;
    width: 100%;
    position: relative;

    /*align-items: center;*/
}
.ring {
    border: 10px solid rgb(211, 211, 211);
    position: relative;
    left: 48%;
    border-radius: 50%;
    border-top: 10px solid #74C9E3;
    width: 50px;
    height: 50px;
    animation: spin 1s linear infinite;
}
@keyframes spin {
    0% {
    transform: rotate(0deg);
    }
    100% {
    transform: rotate(360deg);
    }
}
</style>