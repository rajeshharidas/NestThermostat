/**
 * Thermostat data service - Query the backend Mongo DB for 
 * thermostat data
 */

import axios from 'axios'

const BASE_API_URL = 'http://rhwin8srv:8085'
const SENSORDATASERVICE_API_URL = `${BASE_API_URL}/feedstore/dataapi/temperaturedata`

class LiveFeedService {

    retrieveAllSensorData() {
        return axios.get(`${SENSORDATASERVICE_API_URL}`);		
    }
}

export default new LiveFeedService()