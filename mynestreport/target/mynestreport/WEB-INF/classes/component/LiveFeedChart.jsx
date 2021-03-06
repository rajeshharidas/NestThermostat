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
		this.loadHvacData = this.loadHvacData.bind(this);
		this.loadHvacData();
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

				//console.log("Service data", sensordatas);
				//console.log("Temperature data map", sensorTempArray);
				//console.log("Humidity data map", sensorHumidityArray);
			}
			);
	}

	loadHvacData() {
		LiveFeedService.retrieveAllHvacData()
			.then(response => {

				const hvacdatas = Array.from(response.data.values);

				//console.log("Event Service data", hvacdatas);

				var flagData = [];
				var bandData = [];
				var band = {};
				band.color = "rgba(255, 197, 0, 0.2)";

				hvacdatas.forEach(element => {
					var flag = {};
					var datetime = new Date(Moment(element.timeofevent, "YYYY-MM-DDTHH:mm:ss.SSS").format("YYYY-MM-DD HH:mm:ss"));

					if (element.traitkey === "temperature") {

						var temp = parseFloat(element.traitvalue);
						temp = (temp * (9 / 5)) + 32;

						flag = {
							x: datetime,
							y: temp,
							title: "T",
							text: temp
						}
						flagData.push(flag);
					}
					else if (element.traitkey === "humidity") {
						flag = {
							x: datetime,
							y: parseFloat(element.traitvalue),
							title: "H",
							text: element.traitvalue
						}
						flagData.push(flag);
					}
					else if (element.traitkey === "hvacStatus") {

						if (element.traitvalue === "ON") {
							band.from = datetime;
						}
						else if (element.traitvalue === "OFF") {

							band.to = datetime;
							bandData.push(band);
							band = {}
							band.color = "rgba(255, 197, 0, 0.2)";
						}
						else if (element.traitvalue === "HEATING") {
							band.color = "rgba(255, 80, 0, 0.42)";
						}
						else if (element.traitvalue === "COOLING") {
							band.color = "rgba(0, 0, 255, 0.27)";
						}

					}

				});

				this.setState({
					flagData: flagData,
					bandData: bandData
				});

				console.log("Event Flag data", flagData);
				console.log("Event Band data", bandData);
			}
			);
	}

	handlePageChange() {
		this.loadSensorData();
		this.loadHvacData();
	}

	render() {

		var options = {
			chart: {
				height: (9 / 16 * 100) + '%',
				type: 'line',
				zoomType: 'x',
				panning: {
					enabled: true,
					type: 'xy'
				},
				panKey: 'shift'
			},
			rangeSelector: {
				selected: 1,
				inputDateFormat: '%b %e, %Y %H:%M',
				inputBoxWidth: 120,
				inputBoxHeight: 18
			},
			title: {
				text: "Temperature and Humity around the year"
			},
			xAxis: {
				title: { text: "Time" },
				type: "datetime",
				plotBands: this.state.bandData,
				crosshair: true
			},
			yAxis: {
				title: { text: "Temperature/Humidity" },
				type: "linear",
				crosshair: true
			},
			series: [
				{
					id: "tempseries",
					name: "Temperature",
					data: this.state.sensorTempData,
					marker: {
						enabled: true
					},
					showInLegend: true
				},
				{
					id: "humidityseries",
					name: "Humidity",
					data: this.state.sensorHumidityData,
					marker: {
						enabled: true
					},
					showInLegend: true
				},
				{
					id: "events",
					name: "Events",
					type: "flags",
					showInLegend: true,
					turboThreshold: 40000,
					states: {
						hover: {
							fillColor: "#395C84",
							color: "white"
						}
					},
					style: {
						// text style
						color: "white"
					},
					data: this.state.flagData,
					color: Highcharts.getOptions().colors[0], // same as onSeries
					fillColor: Highcharts.getOptions().colors[0],
					shape: "flag",
					width: 16
				}
			]
		};


		return (
			<div>
				<div>
					<HighchartsReact
						highcharts={Highcharts}
						constructorType={'stockChart'}
						ref={this.chartComponent}
						options={options}
					/>
				</div>

			</div>
		);
	}
}

export default LiveFeedChart;
