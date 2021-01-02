/**
 * Render the thermostat data for every day included in the
 * Nest data report
 */
import '../App.css';
import Moment from 'moment';
import React from 'react';
import LiveFeedService from '../service/liveFeedService'
import Chart from "react-google-charts";

import 'react-loader-spinner/dist/loader/css/react-spinner-loader.css'
import Loader from 'react-loader-spinner'

class LiveFeedChart extends React.Component {

	constructor(props) {
		super(props)
		this.state = {
			sensorMapData: [
				[
					{ type: 'string', id: 'Date' },
					{ type: 'number', id: 'Temperature' },
					{ type: 'number', id: 'Humidity' }
				]
			]
		};
		this.loadSensorData = this.loadSensorData.bind(this);
		this.loadSensorData();
	}

	loadSensorData() {
		LiveFeedService.retrieveAllSensorData()
			.then(response => {

				const sensordatas = Array.from(response.data.values);

				var sensorDataArray = [
					[
						{ type: 'date', id: 'Date', label: 'Date' },
						{ type: 'number', id: 'Temperature', label: 'Temperature' },
						{ type: 'number', id: 'Humidity', label: 'Humidity' }
					]
				];

				sensordatas.forEach(element => {

					var chartDataRow = [];
					var datetime = Moment(element.timeofcapture).toDate();
					chartDataRow.push(datetime);
					chartDataRow.push(parseFloat(element.temperature));
					chartDataRow.push(parseFloat(element.humidity));
					if (element.temperature !== "" && element.humidity !== "")
						sensorDataArray.push(chartDataRow);
				});

				this.setState({
					sensorMapData: sensorDataArray
				});

				console.log("Service data", sensordatas);
				console.log("Heat map data", sensorDataArray);
			}
			);
	}

	handlePageChange(data) {
		this.loadSensorData();
	}

	render() {

		return (
			<div>
				<div>
					<Chart
						width={'100%'}
						height={'100%'}
						chartType="LineChart"
						loader={<div>Loading Chart</div>}
						data={this.state.sensorMapData}
						options={{
							chart: {
								title:
									'Average Temperatures and Humidity Throughout the Year',
							},
							width: 1280,
							height: 800,
							explorer: {
								actions: ['dragToZoom', 'rightClickToReset'],
								axis: 'horizontal',
								keepInBounds: true,
								maxZoomIn: 8.0
							},
							hAxis: {
								format: 'MM/dd/yyyy hh:mm a',
								gridlines: { count: 15 },
							},
							vAxis: {
								title: 'Sensor',
							},
							series: {
								0: { axis: 'Temps', curveType: 'function' },
								1: { axis: 'Humidity', curveType: 'function' },
							},
							crosshair: {
								color: '#000',
								trigger: 'selection'
							},
							axes: {
								// Adds labels to each axis; they don't have to match the axis names.
								y: {
									Temps: { label: 'Temps (Celsius)' },
									Humidity: { label: 'Humidity' },
								},
							},

						}}
						rootProps={{ 'data-testid': '2' }}
					/>
				</div>
				<div class="overlay-box">
					<Loader
						type="Puff"
						color="#00BFFF"
						height={100}
						width={100}
						timeout={5000} //3 secs
					/>

				</div>
			</div>
		);
	}
}

export default LiveFeedChart;
