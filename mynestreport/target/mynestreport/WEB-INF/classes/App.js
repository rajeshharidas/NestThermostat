
import './App.css';
import React, { Component } from 'react';
import {
	BrowserRouter,
	Link,
	Route
} from 'react-router-dom';
import {Tabs, TabList, TabPanel, Tab } from 'react-tabs';
import NestDataTimeline from './component/NestDataTimeline';
import SensorDataChart from './component/SensorDataChart';
import LiveFeedChart from './component/LiveFeedChart';
import 'bootstrap/dist/css/bootstrap.min.css';

class App extends Component {
	render() {
		return (
			<div className="App">
				<div class="container">

					<BrowserRouter basename={process.env.REACT_APP_ROUTER_BASE || ''}>
						<Tabs>
							<TabList class='react-tabs__tab-list'>
								<Tab class='react-tabs__tab'><Link to="/">Time line Data</Link></Tab>
								<Tab class='react-tabs__tab'><Link to="/sensor">Sensor Chart</Link></Tab>
								<Tab class='react-tabs__tab'><Link to="/live">Live Chart</Link></Tab>
							</TabList>

							<TabPanel>
								<Route path="/" component={NestDataTimelineMap} />
							</TabPanel>
							<TabPanel>
								<Route path="/sensor" component={SensorDataMap} />
							</TabPanel>
							<TabPanel>
								<Route path="/live" component={LiveDataMap} />
							</TabPanel>
						</Tabs>					
					</BrowserRouter>
				</div>
			</div>
		);
	}
}

function NestDataTimelineMap() {
	return (
		<div>
			<NestDataTimeline />
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
function LiveDataMap() {
	return (
		<div>
			<LiveFeedChart/>
		</div>
	);
}

export default App;
