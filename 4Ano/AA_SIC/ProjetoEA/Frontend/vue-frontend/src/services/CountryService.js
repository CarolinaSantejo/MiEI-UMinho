import axios from 'axios'

const API_BASE_URL = 'http://localhost:8080/api/allCountries'

class CountryService{
    getAllCountries(){
        return axios.get(API_BASE_URL);
    }

    async getCities(country) {
        try {
            const response = await axios.post('http://localhost:8080/api/cities',country);
            return response.data
        }
        catch(err) {
            console.error("failed to access response", err);
        }
        return null;
    }

}


export default new CountryService()