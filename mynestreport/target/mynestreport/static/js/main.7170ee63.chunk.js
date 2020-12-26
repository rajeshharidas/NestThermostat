(this.webpackJsonpmynestreport=this.webpackJsonpmynestreport||[]).push([[0],{21:function(t,e,a){},46:function(t,e,a){},74:function(t,e,a){"use strict";a.r(e);var n=a(1),r=a(0),i=a.n(r),c=a(35),s=a.n(c),o=(a(46),a(9)),h=a(10),l=a(14),d=a(13),u=(a(21),a(19)),j=a(2),p=a(12),g=a(17),b=a(15),m=a.n(b),O=a(36),v=a.n(O),x="".concat("http://localhost:8085","/mynest/thermostats"),f=new(function(){function t(){Object(o.a)(this,t)}return Object(h.a)(t,[{key:"retrieveAllThermostatData",value:function(t){return v.a.get("".concat(x,"?page=")+t)}}]),t}()),C=a(37),y=a.n(C),D=a(18),T=a(39),P=a.n(T),S=function(t){Object(l.a)(a,t);var e=Object(d.a)(a);function a(t){var n;return Object(o.a)(this,a),(n=e.call(this,t)).state={heatMapData:[],chartEvents:[],paging:{pageSize:0,currentPage:1,itemCount:0,pageCount:0}},n.loadThermostatData=n.loadThermostatData.bind(Object(g.a)(n)),n.loadThermostatData(0),n}return Object(h.a)(a,[{key:"loadThermostatData",value:function(t){var e=this;f.retrieveAllThermostatData(t).then((function(t){var a=Array.from(t.data._embedded.thermostats),n=[];a.forEach((function(t){var e={timeRange:{}};e.timeRange.Caption=m()(t.startTs).format("YYYY-MM-DD")+" to "+m()(t.endTs).format("YYYY-MM-DD");var a=Array.from(t.cycles);a.length>0?(e.chartData=[[{type:"string",id:"Caption"},{type:"string",id:"Duration"},{type:"date",id:"StartTime"},{type:"date",id:"EndTime"}]],a.forEach((function(t){var a=[];a.push(t.captionText),a.push(t.duration),a.push(m()(t.startTime).toDate()),a.push(m()(t.endTime).toDate()),e.chartData.push(a)}))):e.chartData=null,n.push(e)}));var r={pageSize:t.data.page.size,pageCount:t.data.page.totalPages,currentPage:t.data.page.number+1,itemCount:t.data.page.totalElements};e.setState({heatMapData:n,chartEvents:[{eventName:"select",callback:function(t){var e=t.chartWrapper;console.log("Selected ",e.getChart().getSelection())}}],paging:r}),console.log("Service data",a),console.log("Heat map data",n),console.log("Paging data",r)}))}},{key:"handlePageChange",value:function(t){console.log("active page is",t),this.setState({currentPageNumber:t}),this.loadThermostatData(t-1)}},{key:"render",value:function(){var t=this.state.heatMapData.map((function(t){return Object(n.jsx)(y.a,{trigger:t.timeRange.Caption,triggertyle:"CustomTriggerCSS",children:t.chartData?Object(n.jsx)(D.a,{width:"100%",height:"100%",chartType:"Timeline",loader:Object(n.jsx)("div",{children:"Loading Chart"}),data:t.chartData,rootProps:{"data-testid":"10"}}):Object(n.jsx)("div",{children:"No data available!"})})}));return Object(n.jsxs)("div",{children:[Object(n.jsx)("div",{children:t}),Object(n.jsx)("div",{children:Object(n.jsx)(P.a,{itemClass:"page-item",linkClass:"page-link",activePage:this.state.paging.currentPage,itemsCountPerPage:this.state.paging.pageCount,totalItemsCount:this.state.paging.itemCount,pageRangeDisplayed:this.state.paging.pageSize,onChange:this.handlePageChange.bind(this)})})]})}}]),a}(i.a.Component),k=function(t){Object(l.a)(a,t);var e=Object(d.a)(a);function a(){return Object(o.a)(this,a),e.apply(this,arguments)}return Object(h.a)(a,[{key:"render",value:function(){return Object(n.jsx)("div",{children:Object(n.jsx)(D.a,{width:"600px",height:"400px",chartType:"LineChart",loader:Object(n.jsx)("div",{children:"Loading Chart"}),data:[["x","dogs","cats"],[0,0,0],[1,10,5],[2,23,15],[3,17,9],[4,18,10],[5,9,5],[6,11,3],[7,27,19]],options:{hAxis:{title:"Time"},vAxis:{title:"Popularity"},series:{1:{curveType:"function"}}},rootProps:{"data-testid":"2"}})})}}]),a}(i.a.Component);a(70);function M(){return Object(n.jsx)("div",{children:Object(n.jsx)(S,{})})}function Y(){return Object(n.jsx)("div",{children:Object(n.jsx)(k,{})})}var A=function(t){Object(l.a)(a,t);var e=Object(d.a)(a);function a(){return Object(o.a)(this,a),e.apply(this,arguments)}return Object(h.a)(a,[{key:"render",value:function(){return Object(n.jsx)("div",{className:"App",children:Object(n.jsx)("div",{class:"container",children:Object(n.jsx)(u.a,{basename:"/mynestreport",children:Object(n.jsxs)(p.d,{children:[Object(n.jsxs)(p.b,{class:"react-tabs__tab-list",children:[Object(n.jsx)(p.a,{class:"react-tabs__tab",children:Object(n.jsx)(u.b,{to:"/",children:"Time Series"})}),Object(n.jsx)(p.a,{class:"react-tabs__tab",children:Object(n.jsx)(u.b,{to:"/sensor",children:"Sensor Chart"})})]}),Object(n.jsx)(p.c,{children:Object(n.jsx)(j.a,{path:"/",component:M})}),Object(n.jsx)(p.c,{children:Object(n.jsx)(j.a,{path:"/sensor",component:Y})})]})})})})}}]),a}(r.Component),E=function(t){t&&t instanceof Function&&a.e(3).then(a.bind(null,75)).then((function(e){var a=e.getCLS,n=e.getFID,r=e.getFCP,i=e.getLCP,c=e.getTTFB;a(t),n(t),r(t),i(t),c(t)}))};s.a.render(Object(n.jsx)(i.a.StrictMode,{children:Object(n.jsx)(A,{})}),document.getElementById("root")),E()}},[[74,1,2]]]);
//# sourceMappingURL=main.7170ee63.chunk.js.map