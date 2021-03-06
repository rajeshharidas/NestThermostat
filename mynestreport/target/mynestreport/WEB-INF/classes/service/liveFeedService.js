/**
 * Thermostat data service - Query the backend Mongo DB for 
 * thermostat data
 */

import axios from 'axios'

const BASE_API_URL = 'http://rhwin8srv:8190'
const SENSORDATASERVICE_API_URL = `${BASE_API_URL}/feedstore/dataapi/temperaturedata`
const HVACDATASERVICE_API_URL = `${BASE_API_URL}/feedstore/dataapi/hvacdata`

class LiveFeedService {

    retrieveAllSensorData() {
        return axios.get(`${SENSORDATASERVICE_API_URL}`);		
    }
    
     retrieveAllHvacData() {
        return axios.get(`${HVACDATASERVICE_API_URL}`);		
    }
}

export default new LiveFeedService()