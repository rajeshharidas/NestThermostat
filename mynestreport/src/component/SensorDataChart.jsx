/**
 * Render the thermostat data for every day included in the
 * Nest data report
 */
import '../App.css';
import Moment from 'moment';
import React from 'react';
import SensorDataService from '../service/sensorDataService'
import Chart from "react-google-charts";
import Pagination from "react-js-pagination";

class SensorDataChart extends React.Component {

	constructor(props) {
		super(props)
		this.state = {
			sensorMapData: [
				[
					{ type: 'string', id: 'Date' },
					{ type: 'number', id: 'Temperature' },
					{ type: 'number', id: 'Humidity' }
				]
			],
			paging: { pageSize: 0, currentPage: 1, itemCount: 0, pageCount: 0 }
		};
		this.loadSensorData = this.loadSensorData.bind(this);
		this.loadSensorData(0);
	}

	loadSensorData(pageNumber) {
		SensorDataService.retrieveAllSensorData(pageNumber)
			.then(response => {

				const sensordatas = Array.from(response.data._embedded.sensorDatas);

				var sensorDataArray = [
					[
						{ type: 'date', id: 'Date', label: 'Date' },
						{ type: 'number', id: 'Temperature', label: 'Temperature' },
						{ type: 'number', id: 'Humidity', label: 'Humidity' }
					]
				];

				sensordatas.forEach(element => {

					var chartDataRow = [];
					var datetime = Moment(element.timestamp).toDate();
					chartDataRow.push(datetime);
					chartDataRow.push(element.avgTemp);
					chartDataRow.push(element.avgHumidity);
					sensorDataArray.push(chartDataRow);
				});

				var pagingInfo = {
					pageSize: response.data.page.size,
					pageCount: response.data.page.totalPages,
					currentPage: response.data.page.number + 1,
					itemCount: response.data.page.totalElements
				}

				this.setState({
					sensorMapData: sensorDataArray,
					paging: pagingInfo
				});

				console.log("Service data", sensordatas);
				console.log("Heat map data", sensorDataArray);
				console.log("Paging data", pagingInfo);
			}
			);
	}

	handlePageChange(pageNumber) {
		console.log('active page is', pageNumber);
		this.setState({ currentPageNumber: pageNumber });
		this.loadSensorData(pageNumber - 1);
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
							width: 900,
							height: 500,
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
				<br></br>
				<div>
					<Pagination itemClass="page-item" linkClass="page-link"
						activePage={this.state.paging.currentPage}
						itemsCountPerPage={this.state.paging.pageCount}
						totalItemsCount={this.state.paging.itemCount}
						pageRangeDisplayed={this.state.paging.pageSize}
						onChange={this.handlePageChange.bind(this)}
					/>
				</div>
			</div>
		);
	}
}

export default SensorDataChart;
