/**
 * Render the thermostat data for every day included in the
 * Nest data report
 */
import '../App.css';
import React from 'react';
import SensorDataService from '../service/sensorDataService'
import Chart from "react-google-charts";
import Pagination from "react-js-pagination";

class SensorDataChart extends React.Component {

	constructor(props) {
		super(props)
		this.state = { sensorMapData: [], paging: { pageSize: 0, currentPage: 1, itemCount: 0, pageCount: 0 } };
		this.loadSensorData = this.loadSensorData.bind(this);
		this.loadSensorData(0);
	}

	loadSensorData(pageNumber) {
		SensorDataService.retrieveAllSensorData(pageNumber)
			.then(response => {

				const sensordatas = Array.from(response.data._embedded.sensorDatas);

				var sensorDataArray = [
					['x', 'Temperature', 'Humidity']
				];

				sensordatas.forEach(element => {

					var chartDataRow = [];
					var datetime = element.dateCaptured + ' ' + element.timeCaptured;
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
		this.loadThermostatData(pageNumber - 1);
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
							hAxis: {
								title: 'Time',
							},
							vAxis: {
								title: 'Sensor',
							},
							series: {
								1: { curveType: 'function' },
							},
						}}
						rootProps={{ 'data-testid': '2' }}
					/>
				</div>
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
