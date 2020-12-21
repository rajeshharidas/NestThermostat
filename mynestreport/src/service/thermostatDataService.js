/**
 * Thermostat data service - Query the backend Mongo DB for 
 * thermostat data
 */

import axios from 'axios'

const BASE_API_URL = 'http://localhost:8085'
const NESTDATASERVICE_API_URL = `${BASE_API_URL}/mynest/thermostats`

class ThermostatDataService {

    retrieveAllThermostatData(name) {
        return axios.get(`${NESTDATASERVICE_API_URL}`);		
    }
}

export default new ThermostatDataService()