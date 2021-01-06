/**
 * Render the thermostat data for every day included in the
 * Nest data report
 */
import '../App.css';
import Moment from 'moment';
import React from 'react';
import LiveFeedService from '../service/liveFeedService'
import Highcharts from "highcharts/highstock";
import HighchartsReact from 'highcharts-react-official'

require("highcharts/indicators/indicators")(Highcharts);
require("highcharts/indicators/pivot-points")(Highcharts);
require("highcharts/indicators/macd")(Highcharts);
require("highcharts/modules/exporting")(Highcharts);


class LiveFeedChart extends React.Component {


	constructor(props) {
		super(props)
		this.state = {
			sensorTempData: [
			],
			sensorHumidityData: [
			]

		};
		this.loadSensorData = this.loadSensorData.bind(this);
		this.loadSensorData();
	}

	loadSensorData() {
		LiveFeedService.retrieveAllSensorData()
			.then(response => {

				const sensordatas = Array.from(response.data.values);

				var sensorTempArray = [
				];

				var sensorHumidityArray = [
				];

				sensordatas.forEach(element => {

					var chartTempDataRow = [];
					var datetime = Moment(element.timeofcapture).toDate();
					chartTempDataRow.push(datetime);

					var temp = parseFloat(element.temperature);
					temp = (temp * (9 / 5)) + 32;
					console.log(temp);

					chartTempDataRow.push(temp);
					if (element.temperature !== "")
						sensorTempArray.push(chartTempDataRow);

					var chartHumidityDataRow = [];
					chartHumidityDataRow.push(datetime);

					chartHumidityDataRow.push(parseFloat(element.humidity));

					if (element.humidity !== "")
						sensorHumidityArray.push(chartHumidityDataRow);

				});

				this.setState({
					sensorTempData: sensorTempArray,
					sensorHumidityData: sensorHumidityArray
				});
				
				console.log("Service data", sensordatas);
				console.log("Temperature data map", sensorTempArray);
				console.log("Humidity data map", sensorHumidityArray);
			}
			);
	}

	handlePageChange() {
		this.loadSensorData();
	}

	render() {
		
		 var options = {
				chart: {
					type: 'line',
					zoomType: 'x',
					panning: true,
        			panKey: 'shift'
				},
				title: {
					text: "Temperature and Humity around the year"
				},
				xAxis: {
					title: { text: "Time" },
					type: "datetime"
				},
				yAxis: {
					title: { text: "Temperature/Humidity" },
					type: "linear"
				},
				series: [
					{
						id: "tempseries",
						name: "Temperature",
						data: this.state.sensorTempData
					},
					{
						id: "humidityseries",
						name: "Humidity",
						data: this.state.sensorHumidityData
					}
				]
			};


		return (
			<div>
				<div>
					<HighchartsReact
						highcharts={Highcharts}
						ref={this.chartComponent}
						options={options}
					/>
				</div>

			</div>
		);
	}
}

export default LiveFeedChart;
