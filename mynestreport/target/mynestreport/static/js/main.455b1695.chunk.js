(this.webpackJsonpmynestreport=this.webpackJsonpmynestreport||[]).push([[0],{20:function(t,e,a){},44:function(t,e,a){},72:function(t,e,a){"use strict";a.r(e);var n=a(1),i=a(0),r=a.n(i),c=a(32),s=a.n(c),o=(a(44),a(10)),l=a(11),h=a(15),g=a(14),u=(a(20),a(17)),p=a(2),d=a(12),j=a.n(d),m=a(33),b=a.n(m),v="".concat("http://localhost:8085","/mynest/thermostats"),O=new(function(){function t(){Object(o.a)(this,t)}return Object(l.a)(t,[{key:"retrieveAllThermostatData",value:function(t){return b.a.get("".concat(v))}}]),t}()),f=a(34),C=a.n(f),x=a(35),D=a(37),y=a.n(D),P=function(t){Object(h.a)(a,t);var e=Object(g.a)(a);function a(t){var n;return Object(o.a)(this,a),(n=e.call(this,t)).state={heatMapData:[],chartEvents:[],paging:{pageSize:0,currentPage:0,itemCount:0,pageCount:0}},O.retrieveAllThermostatData().then((function(t){var e=Array.from(t.data._embedded.thermostats),a=[];e.forEach((function(t){var e={timeRange:{}};e.timeRange.Caption=j()(t.startTs).format("YYYY-MM-DD")+" to "+j()(t.endTs).format("YYYY-MM-DD");var n=Array.from(t.cycles);n.length>0?(e.chartData=[[{type:"string",id:"Caption"},{type:"string",id:"Duration"},{type:"date",id:"StartTime"},{type:"date",id:"EndTime"}]],n.forEach((function(t){var a=[];a.push(t.captionText),a.push(t.duration),a.push(j()(t.startTime).toDate()),a.push(j()(t.endTime).toDate()),e.chartData.push(a)}))):e.chartData=null,a.push(e)}));var i={pageSize:t.data.page.size,pageCount:t.data.page.totalPages,currentPage:t.data.page.number,itemCount:t.data.page.totalElements};n.setState({heatMapData:a,chartEvents:[{eventName:"select",callback:function(t){var e=t.chartWrapper;console.log("Selected ",e.getChart().getSelection())}}],paging:i}),console.log("Service data",e),console.log("Heat map data",a),console.log("Paging data",i)})),n}return Object(l.a)(a,[{key:"handlePageChange",value:function(t){console.log("active page is",t),this.setState({currentPageNumber:t})}},{key:"render",value:function(){var t=this.state.heatMapData.map((function(t){return Object(n.jsx)(C.a,{trigger:t.timeRange.Caption,children:t.chartData?Object(n.jsx)(x.a,{width:"100%",height:"100%",chartType:"Timeline",loader:Object(n.jsx)("div",{children:"Loading Chart"}),data:t.chartData,rootProps:{"data-testid":"10"}}):Object(n.jsx)("div",{children:"No data available!"})})}));return Object(n.jsxs)("div",{children:[Object(n.jsx)("div",{children:t}),Object(n.jsx)("div",{children:Object(n.jsx)(y.a,{itemClass:"page-item",linkClass:"page-link",activePage:this.state.paging.currentPage,itemsCountPerPage:this.state.paging.pageCount,totalItemsCount:this.state.paging.itemCount,pageRangeDisplayed:this.state.paging.pageSize,onChange:this.handlePageChange.bind(this)})})]})}}]),a}(r.a.Component);a(68);function T(){return Object(n.jsx)("div",{children:Object(n.jsx)(P,{})})}var S=function(t){Object(h.a)(a,t);var e=Object(g.a)(a);function a(){return Object(o.a)(this,a),e.apply(this,arguments)}return Object(l.a)(a,[{key:"render",value:function(){return Object(n.jsx)("div",{className:"App",children:Object(n.jsx)("div",{class:"container",children:Object(n.jsxs)(u.a,{basename:"/mynestreport",children:[Object(n.jsx)("ul",{class:"list-group",children:Object(n.jsx)("li",{class:"list-group-item",children:Object(n.jsx)(u.b,{to:"/",children:"Nest Data Map for 2119"})})}),Object(n.jsx)(p.c,{children:Object(n.jsx)(p.a,{path:"/",component:T})})]})})})}}]),a}(i.Component),k=function(t){t&&t instanceof Function&&a.e(3).then(a.bind(null,73)).then((function(e){var a=e.getCLS,n=e.getFID,i=e.getFCP,r=e.getLCP,c=e.getTTFB;a(t),n(t),i(t),r(t),c(t)}))};s.a.render(Object(n.jsx)(r.a.StrictMode,{children:Object(n.jsx)(S,{})}),document.getElementById("root")),k()}},[[72,1,2]]]);
//# sourceMappingURL=main.455b1695.chunk.js.map