
import './App.css';
import React, { Component } from 'react';
import {
	BrowserRouter,
	Link,
	Route,
	Switch
} from 'react-router-dom';
import NestDataHeatmap from './component/NestDataHeatmap';
import 'bootstrap/dist/css/bootstrap.min.css';

class App extends Component {
	render() {
		return (
			<div className="App">
				<div class="container">
					<BrowserRouter basename={process.env.REACT_APP_ROUTER_BASE || ''}>
						<ul class="list-group">
							<li class="list-group-item">
								<Link to="/">Nest Data Map for 2119</Link>
							</li>
						</ul>
						<Switch>
							<Route path="/" component={TimeLineDataMap} />
						</Switch>
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

export default App;
