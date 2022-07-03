import axios from 'axios'
import store from '../store';


class ClientProfileService{
    async getProfileData() {
        var request = {email: store.getters.email};
        try {
            const response = await axios.post('/clientProfile',request);
            return response.data
        }
        catch(err) {
            console.error("failed to access response", err);
        }
        return null;
    }
    async getMakeRequestData(petsitter) {
        var request = {email: store.getters.email, emailPetsitter: petsitter};
        try {
            const response = await axios.post('/getMakeRequest',request);
            return response.data
        }
        catch(err) {
            console.error("failed to access response", err);
        }
        return null;
    }
    async makeRequest(petsitterId, date, endDate, startTime, endTime, petSelected, serviceType, opt, total) {
        var request = {
            email: store.getters.email, 
            petsitterId: petsitterId,
            date: date,
            endDate: endDate,
            startTime: startTime,
            endTime: endTime,
            petSelected: petSelected,
            serviceType: serviceType,
            opt: opt,
            total: total
        };
        try {
            const response = await axios.post('/makeRequest',request);
            return response.data
        }
        catch(err) {
            console.error("failed to access response", err);
            return false;
        }
    }

}

export default new ClientProfileService()