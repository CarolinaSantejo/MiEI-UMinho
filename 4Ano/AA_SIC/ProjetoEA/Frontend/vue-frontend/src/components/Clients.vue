<template>
    <div class = "container">
        <h1 class="text-center"> Clients List</h1>
        <table class="table table-striped">
            <thead>
                <th> Client email </th>
                <th> Client username </th>
            </thead>
            <tbody>
                <tr v-for = "client in clients" v-bind:key = "client.email">
                    <td> {{client.email}} </td>
                    <td> {{client.username}} </td>
                </tr>
            </tbody>
        </table>
    </div>
</template>



<script>
import ClientService from '../services/ClientService'

    export default {
        name: 'MyClients',
        data(){
            return {
            clients : []
            }
        },
        methods: {
            getClients(){
                ClientService.getClients().then((response) =>{
                    this.clients = response.data;
                })
            }
        },
        
        created() {
            this.getClients()
        }
    }
</script>




