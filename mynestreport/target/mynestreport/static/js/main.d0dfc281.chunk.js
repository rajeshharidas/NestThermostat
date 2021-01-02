(this.webpackJsonpmynestreport=this.webpackJsonpmynestreport||[]).push([[0],{21:function(e,t,a){},47:function(e,t,a){},95:function(e,t,a){"use strict";a.r(t);var n=a(1),i=a(0),r=a.n(i),s=a(37),o=a.n(s),c=(a(47),a(5)),l=a(6),d=a(15),u=a(14),h=(a(21),a(19)),p=a(3),g=a(12),m=a(13),b=a(11),j=a.n(b),v=a(17),y=a.n(v),O="".concat("http://localhost:8085","/mynest/api/thermostats"),f=new(function(){function e(){Object(c.a)(this,e)}return Object(l.a)(e,[{key:"retrieveAllThermostatData",value:function(e){return y.a.get("".concat(O,"?page=")+e)}}]),e}()),x=a(38),D=a.n(x),C=a(18),T=a(22),S=a.n(T),P=function(e){Object(d.a)(a,e);var t=Object(u.a)(a);function a(e){var n;return Object(c.a)(this,a),(n=t.call(this,e)).state={heatMapData:[],chartEvents:[],paging:{pageSize:0,currentPage:1,itemCount:0,pageCount:0}},n.loadThermostatData=n.loadThermostatData.bind(Object(m.a)(n)),n.loadThermostatData(0),n}return Object(l.a)(a,[{key:"loadThermostatData",value:function(e){var t=this;f.retrieveAllThermostatData(e).then((function(e){var a=Array.from(e.data.values),n=[];a.forEach((function(e){var t={timeRange:{}};t.timeRange.Caption=j()(e.startTs).format("YYYY-MM-DD")+" to "+j()(e.endTs).format("YYYY-MM-DD");var a=Array.from(e.cycles);a.length>0?(t.chartData=[[{type:"string",id:"Caption"},{type:"string",id:"Duration"},{type:"date",id:"StartTime"},{type:"date",id:"EndTime"}]],a.forEach((function(e){var a=[];a.push(e.captionText),a.push(e.duration),a.push(j()(e.startTime).toDate()),a.push(j()(e.endTime).toDate()),t.chartData.push(a)}))):t.chartData=null,n.push(t)}));var i={pageSize:e.data.size,pageCount:e.data.totalPages,currentPage:e.data.number+1,itemCount:e.data.totalElements};t.setState({heatMapData:n,chartEvents:[{eventName:"select",callback:function(e){var t=e.chartWrapper;console.log("Selected ",t.getChart().getSelection())}}],paging:i}),console.log("Service data",a),console.log("Heat map data",n),console.log("Paging data",i)}))}},{key:"handlePageChange",value:function(e){console.log("active page is",e),this.setState({currentPageNumber:e}),this.loadThermostatData(e-1)}},{key:"render",value:function(){var e=this.state.heatMapData.map((function(e){return Object(n.jsx)(D.a,{trigger:e.timeRange.Caption,children:e.chartData?Object(n.jsx)(C.a,{width:"100%",height:"100%",chartType:"Timeline",loader:Object(n.jsx)("div",{children:"Loading Chart"}),data:e.chartData,rootProps:{"data-testid":"10"}}):Object(n.jsx)("div",{children:"No data available!"})})}));return Object(n.jsxs)("div",{children:[Object(n.jsx)("div",{children:e}),Object(n.jsx)("div",{children:Object(n.jsx)(S.a,{itemClass:"page-item",linkClass:"page-link",activePage:this.state.paging.currentPage,itemsCountPerPage:20,totalItemsCount:this.state.paging.itemCount,pageRangeDisplayed:this.state.paging.pageSize,onChange:this.handlePageChange.bind(this)})})]})}}]),a}(r.a.Component),k="".concat("http://localhost:8085","/mynest/api/sensordata"),H=new(function(){function e(){Object(c.a)(this,e)}return Object(l.a)(e,[{key:"retrieveAllSensorData",value:function(e,t){return y.a.get("".concat(k,"?sort=desc&page=")+e+"&size="+t)}}]),e}()),M=function(e){Object(d.a)(a,e);var t=Object(u.a)(a);function a(e){var n;return Object(c.a)(this,a),(n=t.call(this,e)).state={sensorMapData:[[{type:"string",id:"Date"},{type:"number",id:"Temperature"},{type:"number",id:"Humidity"}]],paging:{pageSize:0,currentPage:1,itemCount:0,pageCount:0}},n.loadSensorData=n.loadSensorData.bind(Object(m.a)(n)),n.loadSensorData(0),n}return Object(l.a)(a,[{key:"loadSensorData",value:function(e){var t=this;H.retrieveAllSensorData(e,100).then((function(e){var a=Array.from(e.data.values),n=[[{type:"date",id:"Date",label:"Date"},{type:"number",id:"Temperature",label:"Temperature"},{type:"number",id:"Humidity",label:"Humidity"}]];a.forEach((function(e){var t=[],a=j()(e.timestamp).toDate();t.push(a),t.push(e.avgTemp),t.push(e.avgHumidity),""!==e.avgTemp&&""!==e.avgHumidity&&n.push(t)}));var i={pageSize:e.data.size,pageCount:e.data.totalPages,currentPage:e.data.number+1,itemCount:e.data.totalElements};t.setState({sensorMapData:n,paging:i}),console.log("Service data",a),console.log("Heat map data",n),console.log("Paging data",i)}))}},{key:"handlePageChange",value:function(e){console.log("active page is",e),this.setState({currentPageNumber:e}),this.loadSensorData(e-1)}},{key:"render",value:function(){return Object(n.jsxs)("div",{children:[Object(n.jsx)("div",{children:Object(n.jsx)(C.a,{width:"100%",height:"100%",chartType:"LineChart",loader:Object(n.jsx)("div",{children:"Loading Chart"}),data:this.state.sensorMapData,options:{chart:{title:"Average Temperatures and Humidity Throughout the Year"},width:1e3,height:800,hAxis:{format:"MM/dd/yyyy hh:mm a",gridlines:{count:15}},vAxis:{title:"Sensor"},explorer:{actions:["dragToZoom","rightClickToReset"],axis:"horizontal",keepInBounds:!0,maxZoomIn:8},crosshair:{color:"#000",trigger:"selection"},series:{0:{axis:"Temps",curveType:"function"},1:{axis:"Humidity",curveType:"function"}},axes:{y:{Temps:{label:"Temps (Celsius)"},Humidity:{label:"Humidity"}}}},rootProps:{"data-testid":"2"}})}),Object(n.jsx)("div",{children:Object(n.jsx)(S.a,{itemClass:"page-item",linkClass:"page-link",activePage:this.state.paging.currentPage,itemsCountPerPage:100,totalItemsCount:this.state.paging.itemCount,pageRangeDisplayed:this.state.paging.pageSize,onChange:this.handlePageChange.bind(this)})})]})}}]),a}(r.a.Component),A="".concat("http://localhost:8085","/feedstore/dataapi/temperaturedata"),w=new(function(){function e(){Object(c.a)(this,e)}return Object(l.a)(e,[{key:"retrieveAllSensorData",value:function(){return y.a.get("".concat(A))}}]),e}()),z=(a(71),a(40)),E=a.n(z),Y=function(e){Object(d.a)(a,e);var t=Object(u.a)(a);function a(e){var n;return Object(c.a)(this,a),(n=t.call(this,e)).state={sensorMapData:[[{type:"string",id:"Date"},{type:"number",id:"Temperature"},{type:"number",id:"Humidity"}]]},n.loadSensorData=n.loadSensorData.bind(Object(m.a)(n)),n.loadSensorData(),n}return Object(l.a)(a,[{key:"loadSensorData",value:function(){var e=this;w.retrieveAllSensorData().then((function(t){var a=Array.from(t.data.values),n=[[{type:"date",id:"Date",label:"Date"},{type:"number",id:"Temperature",label:"Temperature"},{type:"number",id:"Humidity",label:"Humidity"}]];a.forEach((function(e){var t=[],a=j()(e.timeofcapture).toDate();t.push(a),t.push(parseFloat(e.temperature)),t.push(parseFloat(e.humidity)),""!==e.temperature&&""!==e.humidity&&n.push(t)})),e.setState({sensorMapData:n}),console.log("Service data",a),console.log("Heat map data",n)}))}},{key:"handlePageChange",value:function(e){this.loadSensorData()}},{key:"render",value:function(){return Object(n.jsxs)("div",{children:[Object(n.jsx)("div",{children:Object(n.jsx)(C.a,{width:"100%",height:"100%",chartType:"LineChart",loader:Object(n.jsx)("div",{children:"Loading Chart"}),data:this.state.sensorMapData,options:{chart:{title:"Average Temperatures and Humidity Throughout the Year"},width:1280,height:800,explorer:{actions:["dragToZoom","rightClickToReset"],axis:"horizontal",keepInBounds:!0,maxZoomIn:8},hAxis:{format:"MM/dd/yyyy hh:mm a",gridlines:{count:15}},vAxis:{title:"Sensor"},series:{0:{axis:"Temps",curveType:"function"},1:{axis:"Humidity",curveType:"function"}},crosshair:{color:"#000",trigger:"selection"},axes:{y:{Temps:{label:"Temps (Celsius)"},Humidity:{label:"Humidity"}}}},rootProps:{"data-testid":"2"}})}),Object(n.jsx)("div",{class:"overlay-box",children:Object(n.jsx)(E.a,{type:"Puff",color:"#00BFFF",height:100,width:100,timeout:3e3})})]})}}]),a}(r.a.Component);a(91);function F(){return Object(n.jsx)("div",{children:Object(n.jsx)(P,{})})}function I(){return Object(n.jsx)("div",{children:Object(n.jsx)(M,{})})}function L(){return Object(n.jsx)("div",{children:Object(n.jsx)(Y,{})})}var _=function(e){Object(d.a)(a,e);var t=Object(u.a)(a);function a(){return Object(c.a)(this,a),t.apply(this,arguments)}return Object(l.a)(a,[{key:"render",value:function(){return Object(n.jsx)("div",{className:"App",children:Object(n.jsx)("div",{class:"container",children:Object(n.jsx)(h.a,{basename:"/mynestreport",children:Object(n.jsxs)(g.d,{children:[Object(n.jsxs)(g.b,{class:"react-tabs__tab-list",children:[Object(n.jsx)(g.a,{class:"react-tabs__tab",children:Object(n.jsx)(h.b,{to:"/",children:"Time Series"})}),Object(n.jsx)(g.a,{class:"react-tabs__tab",children:Object(n.jsx)(h.b,{to:"/sensor",children:"Sensor Chart"})}),Object(n.jsx)(g.a,{class:"react-tabs__tab",children:Object(n.jsx)(h.b,{to:"/live",children:"Live Chart"})})]}),Object(n.jsx)(g.c,{children:Object(n.jsx)(p.a,{path:"/",component:F})}),Object(n.jsx)(g.c,{children:Object(n.jsx)(p.a,{path:"/sensor",component:I})}),Object(n.jsx)(g.c,{children:Object(n.jsx)(p.a,{path:"/live",component:L})})]})})})})}}]),a}(i.Component),R=function(e){e&&e instanceof Function&&a.e(3).then(a.bind(null,96)).then((function(t){var a=t.getCLS,n=t.getFID,i=t.getFCP,r=t.getLCP,s=t.getTTFB;a(e),n(e),i(e),r(e),s(e)}))};o.a.render(Object(n.jsx)(r.a.StrictMode,{children:Object(n.jsx)(_,{})}),document.getElementById("root")),R()}},[[95,1,2]]]);
//# sourceMappingURL=main.d0dfc281.chunk.js.map