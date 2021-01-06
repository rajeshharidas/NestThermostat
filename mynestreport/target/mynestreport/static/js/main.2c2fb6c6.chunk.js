(this.webpackJsonpmynestreport=this.webpackJsonpmynestreport||[]).push([[0],{22:function(t,e,a){},50:function(t,e,a){},82:function(t,e,a){"use strict";a.r(e);var n=a(1),r=a(0),i=a.n(r),s=a(40),o=a.n(s),c=(a(50),a(5)),l=a(6),u=a(15),d=a(14),h=(a(22),a(20)),p=a(2),m=a(12),g=a(13),j=a(11),b=a.n(j),v=a(18),y=a.n(v),O="".concat("http://rhwin8srv:8190","/mynest/api/thermostats"),f=new(function(){function t(){Object(c.a)(this,t)}return Object(l.a)(t,[{key:"retrieveAllThermostatData",value:function(t){return y.a.get("".concat(O,"?page=")+t)}}]),t}()),x=a(41),D=a.n(x),C=a(23),T=a(24),S=a.n(T),P=function(t){Object(u.a)(a,t);var e=Object(d.a)(a);function a(t){var n;return Object(c.a)(this,a),(n=e.call(this,t)).state={heatMapData:[],chartEvents:[],paging:{pageSize:0,currentPage:1,itemCount:0,pageCount:0}},n.loadThermostatData=n.loadThermostatData.bind(Object(g.a)(n)),n.loadThermostatData(0),n}return Object(l.a)(a,[{key:"loadThermostatData",value:function(t){var e=this;f.retrieveAllThermostatData(t).then((function(t){var a=Array.from(t.data.values),n=[];a.forEach((function(t){var e={timeRange:{}};e.timeRange.Caption=b()(t.startTs).format("YYYY-MM-DD")+" to "+b()(t.endTs).format("YYYY-MM-DD");var a=Array.from(t.cycles);a.length>0?(e.chartData=[[{type:"string",id:"Caption"},{type:"string",id:"Duration"},{type:"date",id:"StartTime"},{type:"date",id:"EndTime"}]],a.forEach((function(t){var a=[];a.push(t.captionText),a.push(t.duration),a.push(b()(t.startTime).toDate()),a.push(b()(t.endTime).toDate()),e.chartData.push(a)}))):e.chartData=null,n.push(e)}));var r={pageSize:t.data.size,pageCount:t.data.totalPages,currentPage:t.data.number+1,itemCount:t.data.totalElements};e.setState({heatMapData:n,chartEvents:[{eventName:"select",callback:function(t){var e=t.chartWrapper;console.log("Selected ",e.getChart().getSelection())}}],paging:r}),console.log("Service data",a),console.log("Heat map data",n),console.log("Paging data",r)}))}},{key:"handlePageChange",value:function(t){console.log("active page is",t),this.setState({currentPageNumber:t}),this.loadThermostatData(t-1)}},{key:"render",value:function(){var t=this.state.heatMapData.map((function(t){return Object(n.jsx)(D.a,{trigger:t.timeRange.Caption,children:t.chartData?Object(n.jsx)(C.a,{width:"100%",height:"100%",chartType:"Timeline",loader:Object(n.jsx)("div",{children:"Loading Chart"}),data:t.chartData,rootProps:{"data-testid":"10"}}):Object(n.jsx)("div",{children:"No data available!"})})}));return Object(n.jsxs)("div",{children:[Object(n.jsx)("div",{children:t}),Object(n.jsx)("div",{children:Object(n.jsx)(S.a,{itemClass:"page-item",linkClass:"page-link",activePage:this.state.paging.currentPage,itemsCountPerPage:20,totalItemsCount:this.state.paging.itemCount,pageRangeDisplayed:25,onChange:this.handlePageChange.bind(this)})})]})}}]),a}(i.a.Component),k="".concat("http://rhwin8srv:8190","/mynest/api/sensordata"),H=new(function(){function t(){Object(c.a)(this,t)}return Object(l.a)(t,[{key:"retrieveAllSensorData",value:function(t,e){return y.a.get("".concat(k,"?sort=desc&page=")+t+"&size="+e)}}]),t}()),A=function(t){Object(u.a)(a,t);var e=Object(d.a)(a);function a(t){var n;return Object(c.a)(this,a),(n=e.call(this,t)).state={sensorMapData:[[{type:"string",id:"Date"},{type:"number",id:"Temperature"},{type:"number",id:"Humidity"}]],paging:{pageSize:0,currentPage:1,itemCount:0,pageCount:0}},n.loadSensorData=n.loadSensorData.bind(Object(g.a)(n)),n.loadSensorData(0),n}return Object(l.a)(a,[{key:"loadSensorData",value:function(t){var e=this;H.retrieveAllSensorData(t,100).then((function(t){var a=Array.from(t.data.values),n=[[{type:"date",id:"Date",label:"Date"},{type:"number",id:"Temperature",label:"Temperature"},{type:"number",id:"Humidity",label:"Humidity"}]];a.forEach((function(t){var e=[],a=b()(t.timestamp).toDate();e.push(a);var r=parseFloat(t.avgTemp);r=1.8*r+32,console.log(r),e.push(r),e.push(t.avgHumidity),""!==t.avgTemp&&""!==t.avgHumidity&&n.push(e)}));var r={pageSize:t.data.size,pageCount:t.data.totalPages,currentPage:t.data.number+1,itemCount:t.data.totalElements};e.setState({sensorMapData:n,paging:r}),console.log("Service data",a),console.log("Heat map data",n),console.log("Paging data",r)}))}},{key:"handlePageChange",value:function(t){console.log("active page is",t),this.setState({currentPageNumber:t}),this.loadSensorData(t-1)}},{key:"render",value:function(){return Object(n.jsxs)("div",{children:[Object(n.jsx)("div",{children:Object(n.jsx)(C.a,{width:"100%",height:"100%",chartType:"LineChart",loader:Object(n.jsx)("div",{children:"Loading Chart"}),data:this.state.sensorMapData,options:{chart:{title:"Average Temperatures and Humidity Throughout the Year"},width:1e3,height:800,hAxis:{format:"MM/dd/yyyy hh:mm a",gridlines:{count:15}},vAxis:{title:"Sensor"},explorer:{actions:["dragToZoom","rightClickToReset"],axis:"horizontal",keepInBounds:!0,maxZoomIn:8},crosshair:{color:"#000",trigger:"selection"},series:{0:{axis:"Temps",curveType:"function"},1:{axis:"Humidity",curveType:"function"}},axes:{y:{Temps:{label:"Temps (Celsius)"},Humidity:{label:"Humidity"}}}},rootProps:{"data-testid":"2"}})}),Object(n.jsx)("div",{children:Object(n.jsx)(S.a,{itemClass:"page-item",linkClass:"page-link",activePage:this.state.paging.currentPage,itemsCountPerPage:100,totalItemsCount:this.state.paging.itemCount,pageRangeDisplayed:25,onChange:this.handlePageChange.bind(this)})})]})}}]),a}(i.a.Component),M="".concat("http://rhwin8srv:8190","/feedstore/dataapi/temperaturedata"),w=new(function(){function t(){Object(c.a)(this,t)}return Object(l.a)(t,[{key:"retrieveAllSensorData",value:function(){return y.a.get("".concat(M))}}]),t}()),E=a(19),z=a.n(E),Y=a(43),_=a.n(Y);a(74)(z.a),a(75)(z.a),a(76)(z.a),a(77)(z.a);var F=function(t){Object(u.a)(a,t);var e=Object(d.a)(a);function a(t){var n;return Object(c.a)(this,a),(n=e.call(this,t)).state={sensorTempData:[],sensorHumidityData:[]},n.loadSensorData=n.loadSensorData.bind(Object(g.a)(n)),n.loadSensorData(),n}return Object(l.a)(a,[{key:"loadSensorData",value:function(){var t=this;w.retrieveAllSensorData().then((function(e){var a=Array.from(e.data.values),n=[],r=[];a.forEach((function(t){var e=[],a=b()(t.timeofcapture).toDate();e.push(a);var i=parseFloat(t.temperature);i=1.8*i+32,console.log(i),e.push(i),""!==t.temperature&&n.push(e);var s=[];s.push(a),s.push(parseFloat(t.humidity)),""!==t.humidity&&r.push(s)})),t.setState({sensorTempData:n,sensorHumidityData:r}),console.log("Service data",a),console.log("Temperature data map",n),console.log("Humidity data map",r)}))}},{key:"handlePageChange",value:function(){this.loadSensorData()}},{key:"render",value:function(){var t={chart:{type:"line",zoomType:"x",panning:!0,panKey:"shift"},title:{text:"Temperature and Humity around the year"},xAxis:{title:{text:"Time"},type:"datetime"},yAxis:{title:{text:"Temperature/Humidity"},type:"linear"},series:[{id:"tempseries",name:"Temperature",data:this.state.sensorTempData},{id:"humidityseries",name:"Humidity",data:this.state.sensorHumidityData}]};return Object(n.jsx)("div",{children:Object(n.jsx)("div",{children:Object(n.jsx)(_.a,{highcharts:z.a,ref:this.chartComponent,options:t})})})}}]),a}(i.a.Component);a(78);function I(){return Object(n.jsx)("div",{children:Object(n.jsx)(P,{})})}function L(){return Object(n.jsx)("div",{children:Object(n.jsx)(A,{})})}function R(){return Object(n.jsx)("div",{children:Object(n.jsx)(F,{})})}var N=function(t){Object(u.a)(a,t);var e=Object(d.a)(a);function a(){return Object(c.a)(this,a),e.apply(this,arguments)}return Object(l.a)(a,[{key:"render",value:function(){return Object(n.jsx)("div",{className:"App",children:Object(n.jsx)("div",{class:"container",children:Object(n.jsx)(h.a,{basename:"/mynestreport",children:Object(n.jsxs)(m.d,{children:[Object(n.jsxs)(m.b,{class:"react-tabs__tab-list",children:[Object(n.jsx)(m.a,{class:"react-tabs__tab",children:Object(n.jsx)(h.b,{to:"/",children:"Time Series"})}),Object(n.jsx)(m.a,{class:"react-tabs__tab",children:Object(n.jsx)(h.b,{to:"/sensor",children:"Sensor Chart"})}),Object(n.jsx)(m.a,{class:"react-tabs__tab",children:Object(n.jsx)(h.b,{to:"/live",children:"Live Chart"})})]}),Object(n.jsx)(m.c,{children:Object(n.jsx)(p.a,{path:"/",component:I})}),Object(n.jsx)(m.c,{children:Object(n.jsx)(p.a,{path:"/sensor",component:L})}),Object(n.jsx)(m.c,{children:Object(n.jsx)(p.a,{path:"/live",component:R})})]})})})})}}]),a}(r.Component),B=function(t){t&&t instanceof Function&&a.e(3).then(a.bind(null,83)).then((function(e){var a=e.getCLS,n=e.getFID,r=e.getFCP,i=e.getLCP,s=e.getTTFB;a(t),n(t),r(t),i(t),s(t)}))};o.a.render(Object(n.jsx)(i.a.StrictMode,{children:Object(n.jsx)(N,{})}),document.getElementById("root")),B()}},[[82,1,2]]]);
//# sourceMappingURL=main.2c2fb6c6.chunk.js.map