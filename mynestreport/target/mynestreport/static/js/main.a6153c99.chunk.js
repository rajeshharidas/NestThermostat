(this.webpackJsonpmynestreport=this.webpackJsonpmynestreport||[]).push([[0],{22:function(t,e,a){},50:function(t,e,a){},82:function(t,e,a){"use strict";a.r(e);var n=a(1),r=a(0),i=a.n(r),s=a(40),o=a.n(s),c=(a(50),a(5)),l=a(6),u=a(15),h=a(14),d=(a(22),a(20)),p=a(2),g=a(13),m=a(12),v=a(11),b=a.n(v),j=a(17),y=a.n(j),f="".concat("http://rhwin8srv:8190","/mynest/api/thermostats"),O=new(function(){function t(){Object(c.a)(this,t)}return Object(l.a)(t,[{key:"retrieveAllThermostatData",value:function(t){return y.a.get("".concat(f,"?page=")+t)}}]),t}()),D=a(41),x=a.n(D),C=a(23),T=a(24),S=a.n(T),H=function(t){Object(u.a)(a,t);var e=Object(h.a)(a);function a(t){var n;return Object(c.a)(this,a),(n=e.call(this,t)).state={heatMapData:[],chartEvents:[],paging:{pageSize:0,currentPage:1,itemCount:0,pageCount:0}},n.loadThermostatData=n.loadThermostatData.bind(Object(m.a)(n)),n.loadThermostatData(0),n}return Object(l.a)(a,[{key:"loadThermostatData",value:function(t){var e=this;O.retrieveAllThermostatData(t).then((function(t){var a=Array.from(t.data.values),n=[];a.forEach((function(t){var e={timeRange:{}};e.timeRange.Caption=b()(t.startTs).format("YYYY-MM-DD")+" to "+b()(t.endTs).format("YYYY-MM-DD");var a=Array.from(t.cycles);a.length>0?(e.chartData=[[{type:"string",id:"Caption"},{type:"string",id:"Duration"},{type:"date",id:"StartTime"},{type:"date",id:"EndTime"}]],a.forEach((function(t){var a=[];a.push(t.captionText),a.push(t.duration),a.push(b()(t.startTime).toDate()),a.push(b()(t.endTime).toDate()),e.chartData.push(a)}))):e.chartData=null,n.push(e)}));var r={pageSize:t.data.size,pageCount:t.data.totalPages,currentPage:t.data.number+1,itemCount:t.data.totalElements};e.setState({heatMapData:n,chartEvents:[{eventName:"select",callback:function(t){var e=t.chartWrapper;console.log("Selected ",e.getChart().getSelection())}}],paging:r}),console.log("Service data",a),console.log("Heat map data",n),console.log("Paging data",r)}))}},{key:"handlePageChange",value:function(t){console.log("active page is",t),this.setState({currentPageNumber:t}),this.loadThermostatData(t-1)}},{key:"render",value:function(){var t=this.state.heatMapData.map((function(t){return Object(n.jsx)(x.a,{trigger:t.timeRange.Caption,children:t.chartData?Object(n.jsx)(C.a,{width:"100%",height:"100%",chartType:"Timeline",loader:Object(n.jsx)("div",{children:"Loading Chart"}),data:t.chartData,rootProps:{"data-testid":"10"}}):Object(n.jsx)("div",{children:"No data available!"})})}));return Object(n.jsxs)("div",{children:[Object(n.jsx)("div",{children:t}),Object(n.jsx)("div",{children:Object(n.jsx)(S.a,{itemClass:"page-item",linkClass:"page-link",activePage:this.state.paging.currentPage,itemsCountPerPage:20,totalItemsCount:this.state.paging.itemCount,pageRangeDisplayed:25,onChange:this.handlePageChange.bind(this)})})]})}}]),a}(i.a.Component),k="".concat("http://rhwin8srv:8190","/mynest/api/sensordata"),P=new(function(){function t(){Object(c.a)(this,t)}return Object(l.a)(t,[{key:"retrieveAllSensorData",value:function(t,e){return y.a.get("".concat(k,"?sort=desc&page=")+t+"&size="+e)}}]),t}()),A=function(t){Object(u.a)(a,t);var e=Object(h.a)(a);function a(t){var n;return Object(c.a)(this,a),(n=e.call(this,t)).state={sensorMapData:[[{type:"string",id:"Date"},{type:"number",id:"Temperature"},{type:"number",id:"Humidity"}]],paging:{pageSize:0,currentPage:1,itemCount:0,pageCount:0}},n.loadSensorData=n.loadSensorData.bind(Object(m.a)(n)),n.loadSensorData(0),n}return Object(l.a)(a,[{key:"loadSensorData",value:function(t){var e=this;P.retrieveAllSensorData(t,100).then((function(t){var a=Array.from(t.data.values),n=[[{type:"date",id:"Date",label:"Date"},{type:"number",id:"Temperature",label:"Temperature"},{type:"number",id:"Humidity",label:"Humidity"}]];a.forEach((function(t){var e=[],a=b()(t.timestamp).toDate();e.push(a);var r=parseFloat(t.avgTemp);r=1.8*r+32,console.log(r),e.push(r),e.push(t.avgHumidity),""!==t.avgTemp&&""!==t.avgHumidity&&n.push(e)}));var r={pageSize:t.data.size,pageCount:t.data.totalPages,currentPage:t.data.number+1,itemCount:t.data.totalElements};e.setState({sensorMapData:n,paging:r}),console.log("Service data",a),console.log("Heat map data",n),console.log("Paging data",r)}))}},{key:"handlePageChange",value:function(t){console.log("active page is",t),this.setState({currentPageNumber:t}),this.loadSensorData(t-1)}},{key:"render",value:function(){return Object(n.jsxs)("div",{children:[Object(n.jsx)("div",{children:Object(n.jsx)(C.a,{width:"100%",height:"100%",chartType:"LineChart",loader:Object(n.jsx)("div",{children:"Loading Chart"}),data:this.state.sensorMapData,options:{chart:{title:"Average Temperatures and Humidity Throughout the Year"},width:1e3,height:800,hAxis:{format:"MM/dd/yyyy hh:mm a",gridlines:{count:15}},vAxis:{title:"Sensor"},explorer:{actions:["dragToZoom","rightClickToReset"],axis:"horizontal",keepInBounds:!0,maxZoomIn:8},crosshair:{color:"#000",trigger:"selection"},series:{0:{axis:"Temps",curveType:"function"},1:{axis:"Humidity",curveType:"function"}},axes:{y:{Temps:{label:"Temps (Celsius)"},Humidity:{label:"Humidity"}}}},rootProps:{"data-testid":"2"}})}),Object(n.jsx)("div",{children:Object(n.jsx)(S.a,{itemClass:"page-item",linkClass:"page-link",activePage:this.state.paging.currentPage,itemsCountPerPage:100,totalItemsCount:this.state.paging.itemCount,pageRangeDisplayed:25,onChange:this.handlePageChange.bind(this)})})]})}}]),a}(i.a.Component),M="http://rhwin8srv:8190",Y="".concat(M,"/feedstore/dataapi/temperaturedata"),w="".concat(M,"/feedstore/dataapi/hvacdata"),E=new(function(){function t(){Object(c.a)(this,t)}return Object(l.a)(t,[{key:"retrieveAllSensorData",value:function(){return y.a.get("".concat(Y))}},{key:"retrieveAllHvacData",value:function(){return y.a.get("".concat(w))}}]),t}()),F=a(16),z=a.n(F),I=a(43),N=a.n(I);a(74)(z.a),a(75)(z.a),a(76)(z.a),a(77)(z.a);var _=function(t){Object(u.a)(a,t);var e=Object(h.a)(a);function a(t){var n;return Object(c.a)(this,a),(n=e.call(this,t)).state={sensorTempData:[],sensorHumidityData:[]},n.loadSensorData=n.loadSensorData.bind(Object(m.a)(n)),n.loadSensorData(),n.loadHvacData=n.loadHvacData.bind(Object(m.a)(n)),n.loadHvacData(),n}return Object(l.a)(a,[{key:"loadSensorData",value:function(){var t=this;E.retrieveAllSensorData().then((function(e){var a=Array.from(e.data.values),n=[],r=[];a.forEach((function(t){var e=[],a=b()(t.timeofcapture).toDate();e.push(a);var i=parseFloat(t.temperature);i=1.8*i+32,e.push(i),""!==t.temperature&&n.push(e);var s=[];s.push(a),s.push(parseFloat(t.humidity)),""!==t.humidity&&r.push(s)})),t.setState({sensorTempData:n,sensorHumidityData:r})}))}},{key:"loadHvacData",value:function(){var t=this;E.retrieveAllHvacData().then((function(e){var a=Array.from(e.data.values),n=[],r=[],i={color:"rgba(255, 197, 0, 0.2)"};a.forEach((function(t){var e={},a=new Date(b()(t.timeofevent,"YYYY-MM-DDTHH:mm:ss.SSS").format("YYYY-MM-DD HH:mm:ss"));if("temperature"===t.traitkey){var s=parseFloat(t.traitvalue);e={x:a,y:s=1.8*s+32,title:"T",text:s},n.push(e)}else"humidity"===t.traitkey?(e={x:a,y:parseFloat(t.traitvalue),title:"H",text:t.traitvalue},n.push(e)):"hvacStatus"===t.traitkey&&("ON"===t.traitvalue?i.from=a:"OFF"===t.traitvalue?(i.to=a,r.push(i),(i={}).color="rgba(255, 197, 0, 0.2)"):"HEATING"===t.traitvalue?i.color="rgba(255, 80, 0, 0.42)":"COOLING"===t.traitvalue&&(i.color="rgba(0, 0, 255, 0.27)"))})),t.setState({flagData:n,bandData:r}),console.log("Event Flag data",n),console.log("Event Band data",r)}))}},{key:"handlePageChange",value:function(){this.loadSensorData(),this.loadHvacData()}},{key:"render",value:function(){var t={chart:{height:"56.25%",type:"line",zoomType:"x",panning:!0,panKey:"shift"},rangeSelector:{selected:1,inputDateFormat:"%b %e, %Y %H:%M",inputBoxWidth:120,inputBoxHeight:18},title:{text:"Temperature and Humity around the year"},xAxis:{title:{text:"Time"},type:"datetime",plotBands:this.state.bandData,crosshair:!0},yAxis:{title:{text:"Temperature/Humidity"},type:"linear",crosshair:!0},series:[{id:"tempseries",name:"Temperature",data:this.state.sensorTempData},{id:"humidityseries",name:"Humidity",data:this.state.sensorHumidityData},{id:"events",name:"Events",type:"flags",turboThreshold:4e4,states:{hover:{fillColor:"#395C84",color:"white"}},style:{color:"white"},data:this.state.flagData,color:z.a.getOptions().colors[0],fillColor:z.a.getOptions().colors[0],shape:"flag",width:16}]};return Object(n.jsx)("div",{children:Object(n.jsx)("div",{children:Object(n.jsx)(N.a,{highcharts:z.a,constructorType:"stockChart",ref:this.chartComponent,options:t})})})}}]),a}(i.a.Component);a(78);function B(){return Object(n.jsx)("div",{children:Object(n.jsx)(H,{})})}function L(){return Object(n.jsx)("div",{children:Object(n.jsx)(A,{})})}function R(){return Object(n.jsx)("div",{children:Object(n.jsx)(_,{})})}var G=function(t){Object(u.a)(a,t);var e=Object(h.a)(a);function a(){return Object(c.a)(this,a),e.apply(this,arguments)}return Object(l.a)(a,[{key:"render",value:function(){return Object(n.jsx)("div",{className:"App",children:Object(n.jsx)("div",{class:"container",children:Object(n.jsx)(d.a,{basename:"/mynestreport",children:Object(n.jsxs)(g.d,{children:[Object(n.jsxs)(g.b,{class:"react-tabs__tab-list",children:[Object(n.jsx)(g.a,{class:"react-tabs__tab",children:Object(n.jsx)(d.b,{to:"/",children:"Time Series"})}),Object(n.jsx)(g.a,{class:"react-tabs__tab",children:Object(n.jsx)(d.b,{to:"/sensor",children:"Sensor Chart"})}),Object(n.jsx)(g.a,{class:"react-tabs__tab",children:Object(n.jsx)(d.b,{to:"/live",children:"Live Chart"})})]}),Object(n.jsx)(g.c,{children:Object(n.jsx)(p.a,{path:"/",component:B})}),Object(n.jsx)(g.c,{children:Object(n.jsx)(p.a,{path:"/sensor",component:L})}),Object(n.jsx)(g.c,{children:Object(n.jsx)(p.a,{path:"/live",component:R})})]})})})})}}]),a}(r.Component),J=function(t){t&&t instanceof Function&&a.e(3).then(a.bind(null,83)).then((function(e){var a=e.getCLS,n=e.getFID,r=e.getFCP,i=e.getLCP,s=e.getTTFB;a(t),n(t),r(t),i(t),s(t)}))};o.a.render(Object(n.jsx)(i.a.StrictMode,{children:Object(n.jsx)(G,{})}),document.getElementById("root")),J()}},[[82,1,2]]]);
//# sourceMappingURL=main.a6153c99.chunk.js.map