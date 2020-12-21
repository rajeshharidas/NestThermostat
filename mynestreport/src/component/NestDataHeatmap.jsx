/**
 * Render the thermostat data for every day included in the
 * Nest data report
 */
import '../App.css';
import React from 'react';
import Moment from 'moment';
import ThermostatDataService from '../service/thermostatDataService'
import Collapsible from 'react-collapsible';
import Chart from "react-google-charts";

class NestDataHeatmap extends React.Component {

	constructor(props) {
		super(props)
		this.state = { heatMapData: [], chartEvents: [] };

		ThermostatDataService.retrieveAllThermostatData()
			.then(response => {

				const thermostats = Array.from(response.data._embedded.thermostats);

				var heatMapData = [];

				thermostats.forEach(element => {
					var heatMapDataRow = {};
					heatMapDataRow.timeRange = {};
					heatMapDataRow.timeRange.Caption = Moment(element.startTs).format('YYYY-MM-DD') + ' to ' + Moment(element.endTs).format('YYYY-MM-DD');

					const cycles = Array.from(element.cycles);

					if (cycles.length > 0) {
						heatMapDataRow.chartData = [
							[
								{ type: 'string', id: 'Caption' },
								{ type: 'string', id: 'Duration' },
								{ type: 'date', id: 'StartTime' },
								{ type: 'date', id: 'EndTime' }
							]
						];

						cycles.forEach(cycle => {
							var chartDataRow = [];
							chartDataRow.push(cycle.captionText);
							chartDataRow.push(cycle.duration);
							chartDataRow.push(Moment(cycle.startTime).toDate());
							chartDataRow.push(Moment(cycle.endTime).toDate());
							heatMapDataRow.chartData.push(chartDataRow);
						});
					}
					else {
						heatMapDataRow.chartData = null;
					}

					heatMapData.push(heatMapDataRow);
				});

				this.setState({
					heatMapData: heatMapData,
					chartEvents: [
						{
							eventName: "select",
							callback({ chartWrapper }) {
								console.log("Selected ", chartWrapper.getChart().getSelection());
							}
						}
					]
				});

				console.log("Service data", thermostats);
				console.log("Heat map data", heatMapData);
			}
			);
	}

	render() {

		//Create a list of components to render from the heatmap data
		const items = this.state.heatMapData.map(function(element) {
			//Group heat map data by dates			
			return <Collapsible trigger={element.timeRange.Caption}>

				{
					element.chartData ?
						<Chart
							width={'100%'}
							height={'100%'}
							chartType="Timeline"
							loader={<div>Loading Chart</div>}
							data={element.chartData}
							rootProps={{ 'data-testid': '10' }}
						/>
						: <div>No data available!</div>
				}

			</Collapsible>;
		});
		//return the components to be rendered
		return (<div>{items}</div>)
	}
}

export default NestDataHeatmap;
