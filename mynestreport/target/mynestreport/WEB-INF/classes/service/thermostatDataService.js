/**
 * Thermostat data service - Query the backend Mongo DB for 
 * thermostat data
 */

import axios from 'axios'

const BASE_API_URL = 'http://rhwin8srv:8190'
const NESTDATASERVICE_API_URL = `${BASE_API_URL}/mynest/api/thermostats`

class ThermostatDataService {

    retrieveAllThermostatData(page) {
        return axios.get(`${NESTDATASERVICE_API_URL}?page=` + page);		
    }
}

export default new ThermostatDataService()