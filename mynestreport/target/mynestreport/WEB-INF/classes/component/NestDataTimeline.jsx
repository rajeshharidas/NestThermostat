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
import Pagination from "react-js-pagination";

class NestDataTimeline extends React.Component {

	constructor(props) {
		super(props)
		this.state = { heatMapData: [], chartEvents: [], paging:{pageSize:0, currentPage:1, itemCount:0, pageCount:0 } };
		this.loadThermostatData = this.loadThermostatData.bind(this);
		this.loadThermostatData(0);
	}
	
	loadThermostatData(pageNumber) {
		ThermostatDataService.retrieveAllThermostatData(pageNumber)
			.then(response => {

				const thermostats = Array.from(response.data.values);

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
				
				var pagingInfo = {
						pageSize: response.data.size,
						pageCount: response.data.totalPages,
						currentPage: response.data.number+1,
						itemCount: response.data.totalElements					
					}

				this.setState({
					heatMapData: heatMapData,
					chartEvents: [
						{
							eventName: "select",
							callback({ chartWrapper }) {
								console.log("Selected ", chartWrapper.getChart().getSelection());
							}
						}
					],
					paging: pagingInfo
				});

				console.log("Service data", thermostats);
				console.log("Heat map data", heatMapData);
				console.log("Paging data", pagingInfo);
			}
			);
	}
	
	handlePageChange(pageNumber) {
    	console.log('active page is', pageNumber);
    	this.setState({currentPageNumber: pageNumber});
		this.loadThermostatData(pageNumber-1);
  	}

	render() {

		//Create a list of components to render from the heatmap data
		const items = this.state.heatMapData.map(function(element) {
			//Group heat map data by dates			
			return <Collapsible trigger={element.timeRange.Caption} >

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
		return (<div>
			<div>{items}</div>
			<div>
				<Pagination itemClass="page-item" linkClass="page-link"
					activePage={this.state.paging.currentPage}
					itemsCountPerPage={20}
					totalItemsCount={this.state.paging.itemCount}
					pageRangeDisplayed={25}
					onChange={this.handlePageChange.bind(this)}
				/>
			</div>
		</div>)
	}
}

export default NestDataTimeline;
