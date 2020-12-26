
import './App.css';
import React, { Component } from 'react';
import {
	BrowserRouter,
	Link,
	Route
} from 'react-router-dom';
import {Tabs, TabList, TabPanel, Tab } from 'react-tabs';
import NestDataHeatmap from './component/NestDataHeatmap';
import SensorDataChart from './component/SensorDataChart';
import 'bootstrap/dist/css/bootstrap.min.css';

class App extends Component {
	render() {
		return (
			<div className="App">
				<div class="container">

					<BrowserRouter basename={process.env.REACT_APP_ROUTER_BASE || ''}>
						<Tabs>
							<TabList class='react-tabs__tab-list'>
								<Tab class='react-tabs__tab'><Link to="/">Time Series</Link></Tab>
								<Tab class='react-tabs__tab'><Link to="/sensor">Sensor Chart</Link></Tab>
							</TabList>

							<TabPanel>
								<Route path="/" component={TimeLineDataMap} />
							</TabPanel>
							<TabPanel>
								<Route path="/sensor" component={SensorDataMap} />
							</TabPanel>
						</Tabs>					
					</BrowserRouter>
				</div>
			</div>
		);
	}
}

function TimeLineDataMap() {
	return (
		<div>
			<NestDataHeatmap />
		</div>
	);
}
function SensorDataMap() {
	return (
		<div>
			<SensorDataChart/>
		</div>
	);
}

export default App;
