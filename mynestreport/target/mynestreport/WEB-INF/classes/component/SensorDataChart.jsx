/**
 * Render the thermostat data for every day included in the
 * Nest data report
 */
import '../App.css';
import React from 'react';
//import Moment from 'moment';
//import ThermostatDataService from '../service/thermostatDataService'
import Chart from "react-google-charts";

class SensorDataChart extends React.Component {

	render() {
		return (
			<div>
				<Chart
					width={'600px'}
					height={'400px'}
					chartType="LineChart"
					loader={<div>Loading Chart</div>}
					data={[
						['x', 'dogs', 'cats'],
						[0, 0, 0],
						[1, 10, 5],
						[2, 23, 15],
						[3, 17, 9],
						[4, 18, 10],
						[5, 9, 5],
						[6, 11, 3],
						[7, 27, 19],
					]}
					options={{
						hAxis: {
							title: 'Time',
						},
						vAxis: {
							title: 'Popularity',
						},
						series: {
							1: { curveType: 'function' },
						},
					}}
					rootProps={{ 'data-testid': '2' }}
				/>
			</div>
		);
	}
}

export default SensorDataChart;
