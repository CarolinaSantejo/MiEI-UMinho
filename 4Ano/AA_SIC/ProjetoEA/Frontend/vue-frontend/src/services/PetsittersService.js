import axios from 'axios'
import store from '../store';

const API_BASE_URL = 'http://localhost:8080/api/allPetsitters'

class PetsittersService{
    getAllPetsitters(){
        return axios.get(API_BASE_URL);
    }
    async getProfile() {
        var request = {email: store.getters.email};
        try {
            const response = await axios.post('/petsitterProfile',request);
            
            return response.data
        }
        catch(err) {
            console.error("failed to access response", err);
        }
        return null;
    }
    async getAppointments() {
        var request = {email: store.getters.email};
        try {
            const response = await axios.post('/appointments',request);
            console.log(response);
            return response.data
        }
        catch(err) {
            console.error("failed to access response", err);
        }
        return null;
    }

    async sendAboutText(email, about){
        var request = {email: email, about_text: about};
        try {
            const response = await axios.post('http://localhost:8080/api/aboutMe',request);
            return response.data
        }
        catch(err) {
            console.error("failed to access response", err);
        }
        return null;
    }

    async getAd() {
        var request = {email: store.getters.email};
        try {
            const response = await axios.post('/getAd',request);
            
            return response.data
        }
        catch(err) {
            console.error("failed to access response", err);
        }
        return null;
    }
}

export default new PetsittersService()