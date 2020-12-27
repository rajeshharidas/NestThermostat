(this.webpackJsonpmynestreport=this.webpackJsonpmynestreport||[]).push([[0],{23:function(t,e,a){},46:function(t,e,a){},74:function(t,e,a){"use strict";a.r(e);var n=a(1),i=a(0),r=a.n(i),s=a(37),c=a.n(s),o=(a(46),a(9)),l=a(10),d=a(16),h=a(15),u=(a(23),a(21)),g=a(2),p=a(13),m=a(14),b=a(11),j=a.n(b),v=a(18),O=a.n(v),y="".concat("http://localhost:8085","/mynest/thermostats"),f=new(function(){function t(){Object(o.a)(this,t)}return Object(l.a)(t,[{key:"retrieveAllThermostatData",value:function(t){return O.a.get("".concat(y,"?page=")+t)}}]),t}()),D=a(38),x=a.n(D),C=a(19),T=a(20),S=a.n(T),P=function(t){Object(d.a)(a,t);var e=Object(h.a)(a);function a(t){var n;return Object(o.a)(this,a),(n=e.call(this,t)).state={heatMapData:[],chartEvents:[],paging:{pageSize:0,currentPage:1,itemCount:0,pageCount:0}},n.loadThermostatData=n.loadThermostatData.bind(Object(m.a)(n)),n.loadThermostatData(0),n}return Object(l.a)(a,[{key:"loadThermostatData",value:function(t){var e=this;f.retrieveAllThermostatData(t).then((function(t){var a=Array.from(t.data._embedded.thermostats),n=[];a.forEach((function(t){var e={timeRange:{}};e.timeRange.Caption=j()(t.startTs).format("YYYY-MM-DD")+" to "+j()(t.endTs).format("YYYY-MM-DD");var a=Array.from(t.cycles);a.length>0?(e.chartData=[[{type:"string",id:"Caption"},{type:"string",id:"Duration"},{type:"date",id:"StartTime"},{type:"date",id:"EndTime"}]],a.forEach((function(t){var a=[];a.push(t.captionText),a.push(t.duration),a.push(j()(t.startTime).toDate()),a.push(j()(t.endTime).toDate()),e.chartData.push(a)}))):e.chartData=null,n.push(e)}));var i={pageSize:t.data.page.size,pageCount:t.data.page.totalPages,currentPage:t.data.page.number+1,itemCount:t.data.page.totalElements};e.setState({heatMapData:n,chartEvents:[{eventName:"select",callback:function(t){var e=t.chartWrapper;console.log("Selected ",e.getChart().getSelection())}}],paging:i}),console.log("Service data",a),console.log("Heat map data",n),console.log("Paging data",i)}))}},{key:"handlePageChange",value:function(t){console.log("active page is",t),this.setState({currentPageNumber:t}),this.loadThermostatData(t-1)}},{key:"render",value:function(){var t=this.state.heatMapData.map((function(t){return Object(n.jsx)(x.a,{trigger:t.timeRange.Caption,children:t.chartData?Object(n.jsx)(C.a,{width:"100%",height:"100%",chartType:"Timeline",loader:Object(n.jsx)("div",{children:"Loading Chart"}),data:t.chartData,rootProps:{"data-testid":"10"}}):Object(n.jsx)("div",{children:"No data available!"})})}));return Object(n.jsxs)("div",{children:[Object(n.jsx)("div",{children:t}),Object(n.jsx)("div",{children:Object(n.jsx)(S.a,{itemClass:"page-item",linkClass:"page-link",activePage:this.state.paging.currentPage,itemsCountPerPage:50,totalItemsCount:this.state.paging.itemCount,pageRangeDisplayed:this.state.paging.pageSize,onChange:this.handlePageChange.bind(this)})})]})}}]),a}(r.a.Component),k="".concat("http://localhost:8085","/mynest/sensorDatas"),M=new(function(){function t(){Object(o.a)(this,t)}return Object(l.a)(t,[{key:"retrieveAllSensorData",value:function(t){return O.a.get("".concat(k,"?page=")+t+",sort=asc")}}]),t}()),A=function(t){Object(d.a)(a,t);var e=Object(h.a)(a);function a(t){var n;return Object(o.a)(this,a),(n=e.call(this,t)).state={sensorMapData:[[{type:"string",id:"Date"},{type:"number",id:"Temperature"},{type:"number",id:"Humidity"}]],paging:{pageSize:0,currentPage:1,itemCount:0,pageCount:0}},n.loadSensorData=n.loadSensorData.bind(Object(m.a)(n)),n.loadSensorData(0),n}return Object(l.a)(a,[{key:"loadSensorData",value:function(t){var e=this;M.retrieveAllSensorData(t).then((function(t){var a=Array.from(t.data._embedded.sensorDatas),n=[[{type:"date",id:"Date",label:"Date"},{type:"number",id:"Temperature",label:"Temperature"},{type:"number",id:"Humidity",label:"Humidity"}]];a.forEach((function(t){var e=[],a=j()(t.timestamp).toDate();e.push(a),e.push(t.avgTemp),e.push(t.avgHumidity),""!==t.avgTemp&&""!==t.avgHumidity&&n.push(e)}));var i={pageSize:t.data.page.size,pageCount:t.data.page.totalPages,currentPage:t.data.page.number+1,itemCount:t.data.page.totalElements};e.setState({sensorMapData:n,paging:i}),console.log("Service data",a),console.log("Heat map data",n),console.log("Paging data",i)}))}},{key:"handlePageChange",value:function(t){console.log("active page is",t),this.setState({currentPageNumber:t}),this.loadSensorData(t-1)}},{key:"render",value:function(){return Object(n.jsxs)("div",{children:[Object(n.jsx)("div",{children:Object(n.jsx)(C.a,{width:"100%",height:"100%",chartType:"LineChart",loader:Object(n.jsx)("div",{children:"Loading Chart"}),data:this.state.sensorMapData,options:{chart:{title:"Average Temperatures and Humidity Throughout the Year"},width:1e3,height:800,hAxis:{format:"MM/dd/yyyy hh:mm a",gridlines:{count:15}},vAxis:{title:"Sensor"},series:{0:{axis:"Temps",curveType:"function"},1:{axis:"Humidity",curveType:"function"}},axes:{y:{Temps:{label:"Temps (Celsius)"},Humidity:{label:"Humidity"}}}},rootProps:{"data-testid":"2"}})}),Object(n.jsx)("br",{}),Object(n.jsx)("div",{children:Object(n.jsx)(S.a,{itemClass:"page-item",linkClass:"page-link",activePage:this.state.paging.currentPage,itemsCountPerPage:45,totalItemsCount:this.state.paging.itemCount,pageRangeDisplayed:this.state.paging.pageSize,onChange:this.handlePageChange.bind(this)})})]})}}]),a}(r.a.Component);a(70);function H(){return Object(n.jsx)("div",{children:Object(n.jsx)(P,{})})}function E(){return Object(n.jsx)("div",{children:Object(n.jsx)(A,{})})}var Y=function(t){Object(d.a)(a,t);var e=Object(h.a)(a);function a(){return Object(o.a)(this,a),e.apply(this,arguments)}return Object(l.a)(a,[{key:"render",value:function(){return Object(n.jsx)("div",{className:"App",children:Object(n.jsx)("div",{class:"container",children:Object(n.jsx)(u.a,{basename:"/mynestreport",children:Object(n.jsxs)(p.d,{children:[Object(n.jsxs)(p.b,{class:"react-tabs__tab-list",children:[Object(n.jsx)(p.a,{class:"react-tabs__tab",children:Object(n.jsx)(u.b,{to:"/",children:"Time Series"})}),Object(n.jsx)(p.a,{class:"react-tabs__tab",children:Object(n.jsx)(u.b,{to:"/sensor",children:"Sensor Chart"})})]}),Object(n.jsx)(p.c,{children:Object(n.jsx)(g.a,{path:"/",component:H})}),Object(n.jsx)(p.c,{children:Object(n.jsx)(g.a,{path:"/sensor",component:E})})]})})})})}}]),a}(i.Component),z=function(t){t&&t instanceof Function&&a.e(3).then(a.bind(null,75)).then((function(e){var a=e.getCLS,n=e.getFID,i=e.getFCP,r=e.getLCP,s=e.getTTFB;a(t),n(t),i(t),r(t),s(t)}))};c.a.render(Object(n.jsx)(r.a.StrictMode,{children:Object(n.jsx)(Y,{})}),document.getElementById("root")),z()}},[[74,1,2]]]);
//# sourceMappingURL=main.947870d8.chunk.js.map