(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-1e05ab98"],{"5a9e":function(e,t,a){"use strict";var n=function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("div",[a("el-table",{directives:[{name:"loading",rawName:"v-loading",value:e.isLoading,expression:"isLoading"}],ref:"multipleTable",attrs:{"element-loading-text":"加载中...","element-loading-spinner":"el-icon-loading","element-loading-background":"rgba(220, 237, 255,.8)",data:e.tableData,"tooltip-effect":"dark",border:"","cell-style":e.getCellStyle,"header-cell-style":e.getHeaderStyle},on:{"selection-change":e.handleSelectionChange}},["/manage/taskcenter"===e.$route.path?a("el-table-column",{attrs:{width:"55"},scopedSlots:e._u([{key:"default",fn:function(t){return[a("el-radio",{staticClass:"radio",attrs:{label:t.$index},nativeOn:{change:function(a){return e.getRadioRow(t.row)}},model:{value:e.radio,callback:function(t){e.radio=t},expression:"radio"}},[e._v(" ")])]}}],null,!1,3510363555)}):e._e(),e.isSelectShow?a("el-table-column",{staticClass:"mulit-select",attrs:{type:"selection",width:"55"}}):e._e(),e._l(e.thList,function(e,t){return a("el-table-column",{key:t,staticStyle:{"font-size":"16px"},attrs:{label:e.ch,prop:e.en}})}),e.isShow?a("el-table-column",{attrs:{label:"状态"},scopedSlots:e._u([{key:"default",fn:function(t){return[a("span",{class:0==t.row.type?"normal":"destroy",domProps:{textContent:e._s(0==t.row.type?"正常":"故障")}}),0==t.row.type?a("span",{class:1===t.row.status?"normal":"useing",domProps:{textContent:e._s(1==t.row.status?"(未使用)":"(使用中)")}}):e._e()]}}],null,!1,1142099066)}):e._e(),e.isVisible?a("el-table-column",{attrs:{label:"任务状态"},scopedSlots:e._u([{key:"default",fn:function(t){return[a("span",{class:0==t.row.status?"checking":1==t.row.status?"normal":2==t.row.status?"destroy":3==t.row.status?"working":"useing",domProps:{textContent:e._s(0==t.row.status?"审核中":1==t.row.status?"审核通过":2==t.row.status?"审核未通过":3==t.row.status?"执行中":"已完成")}})]}}],null,!1,1272741679)}):e._e(),e.isOperateShow?a("el-table-column",{attrs:{label:"/manage/taskverify"===e.$route.path?"审核":"操作"},scopedSlots:e._u([{key:"default",fn:function(t){return[a("div",{staticClass:"row-operate"},[e._t("operate",null,null,t)],2)]}}],null,!0)}):e._e()],2)],1)},r=[],s=(a("8e6e"),a("456d"),a("ac6a"),a("bd86")),i=a("2f62");function o(e,t){var a=Object.keys(e);if(Object.getOwnPropertySymbols){var n=Object.getOwnPropertySymbols(e);t&&(n=n.filter(function(t){return Object.getOwnPropertyDescriptor(e,t).enumerable})),a.push.apply(a,n)}return a}function c(e){for(var t=1;t<arguments.length;t++){var a=null!=arguments[t]?arguments[t]:{};t%2?o(a,!0).forEach(function(t){Object(s["a"])(e,t,a[t])}):Object.getOwnPropertyDescriptors?Object.defineProperties(e,Object.getOwnPropertyDescriptors(a)):o(a).forEach(function(t){Object.defineProperty(e,t,Object.getOwnPropertyDescriptor(a,t))})}return e}var u={name:"dataTable",props:{tableData:{type:Array,required:!0},thList:{type:Array,required:!0},isLoading:{type:Boolean}},data:function(){return{isShow:!1,isVisible:!1,isSelectShow:!1,isOperateShow:!1,radio:[]}},mounted:function(){switch(this.$route.path){case"/manage/equipmentmanage":case"/manage/carmanage":this.isShow=!0;break;default:this.isShow=!1;break}switch(this.$route.path){case"/manage/taskmanage":case"/manage/taskcenter":case"/manage/taskverify":case"/manage/currenttask":this.isVisible=!0;break;default:this.isVisible=!1;break}switch(this.$route.path){case"/manage/historytask":case"/manage/taskverify":case"/manage/taskcenter":case"/manage/currenttask":this.isSelectShow=!1;break;default:this.isSelectShow=!0;break}switch(this.$route.path){case"/manage/taskcenter":case"/manage/currenttask":case"/manage/historytask":this.isOperateShow=!1;break;default:this.isOperateShow=!0;break}},methods:c({},Object(i["c"])(["updateMultipleSelection"]),{toggleSelection:function(e){var t=this;e?e.forEach(function(e){t.$refs.multipleTable.toggleRowSelection(e)}):this.$refs.multipleTable.clearSelection()},handleSelectionChange:function(e){this.updateMultipleSelection(e)},getRadioRow:function(e){this.updateMultipleSelection(e)},getCellStyle:function(){return"height:50px;textAlign:center;fontSize:18px;color:rgba(102,102,102,1);fontWeight:400"},getHeaderStyle:function(){return"background:rgba(220,237,255,1);fontWeight:bold;height:50px;textAlign:center;fontSize:18px;color:rgba(102,102,102,1)"}})},l=u,p=(a("7c61"),a("2877")),g=Object(p["a"])(l,n,r,!1,null,null,null);t["a"]=g.exports},"7c61":function(e,t,a){"use strict";var n=a("dca4"),r=a.n(n);r.a},"984d":function(e,t,a){"use strict";var n=a("fbcc"),r=a.n(n);r.a},a2dd:function(e,t,a){"use strict";a.r(t);var n=function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("div",[a("dataTable",{attrs:{tableData:e.tableData,thList:e.thList,isLoading:e.isLoading},scopedSlots:e._u([{key:"operate",fn:function(t){return[a("div",{class:{"grey-class":0!==t.row.status},on:{click:function(a){return e.handleEdit(t.$index,t.row)}}},[e._v("通过")]),a("div",{class:{"grey-class":0!==t.row.status},on:{click:function(a){return e.handleDelete(t.$index,t.row)}}},[e._v("拒绝")])]}}])}),a("div",{staticClass:"pagination-wrap"},[a("el-pagination",{attrs:{background:"",layout:"prev, pager, next",total:e.tasksTotal},on:{"current-change":e.numPage,"prev-click":e.prevpage,"next-click":e.nextpage}})],1)],1)},r=[],s=(a("8e6e"),a("ac6a"),a("456d"),a("96cf"),a("3b8d")),i=a("bd86"),o=a("5a9e"),c=a("2f62"),u=a("365c");function l(e,t){var a=Object.keys(e);if(Object.getOwnPropertySymbols){var n=Object.getOwnPropertySymbols(e);t&&(n=n.filter(function(t){return Object.getOwnPropertyDescriptor(e,t).enumerable})),a.push.apply(a,n)}return a}function p(e){for(var t=1;t<arguments.length;t++){var a=null!=arguments[t]?arguments[t]:{};t%2?l(a,!0).forEach(function(t){Object(i["a"])(e,t,a[t])}):Object.getOwnPropertyDescriptors?Object.defineProperties(e,Object.getOwnPropertyDescriptors(a)):l(a).forEach(function(t){Object.defineProperty(e,t,Object.getOwnPropertyDescriptor(a,t))})}return e}var g={name:"TaskVerify",data:function(){return{pageNum:1,thList:[{ch:"任务名称",en:"name"},{ch:"开始时间",en:"startTime"},{ch:"结束时间",en:"endTime"},{ch:"出发地",en:"origin"},{ch:"目的地",en:"destination"},{ch:"路线名称",en:"routeName"}]}},components:{dataTable:o["a"]},computed:p({},Object(c["d"])({areaId:function(e){return e.areaId},tableData:function(e){return e.taskVerify.tableData},tasksTotal:function(e){return e.taskVerify.tasksTotal},isLoading:function(e){return e.taskVerify.isLoading}})),mounted:function(){var e=this;0===this.areaId?setTimeout(function(){var t=e.areaId;e.getTasksData({areaId:t,pageNum:e.pageNum,pageSize:10})},0):(this.getTasksData({areaId:this.areaId,pageNum:this.pageNum,pageSize:10}),console.log(this.tasksTotal))},methods:p({},Object(c["b"])(["getTasksData"]),{prevpage:function(e){this.getTasksData({areaId:this.areaId,pageNum:e-1,pageSize:10})},nextpage:function(e){this.getTasksData({areaId:this.areaId,pageNum:e+1,pageSize:10})},numPage:function(e){this.pageNum=e,this.getTasksData({areaId:this.areaId,pageNum:e,pageSize:10})},handleEdit:function(){var e=Object(s["a"])(regeneratorRuntime.mark(function e(t,a){var n,r,i=this;return regeneratorRuntime.wrap(function(e){while(1)switch(e.prev=e.next){case 0:if(n=a.status,r=a.id,0!==n){e.next=5;break}this.$messageBox.confirm("确定要同意该任务么?","提示",{confirmButtonText:"确定",cancelButtonText:"取消",showCancelButton:!0,type:"warning"}).then(Object(s["a"])(regeneratorRuntime.mark(function e(){return regeneratorRuntime.wrap(function(e){while(1)switch(e.prev=e.next){case 0:return e.next=2,Object(u["hb"])(r,1,"同意");case 2:i.getTasksData({areaId:i.areaId,pageNum:i.pageNum,pageSize:10}),setTimeout(function(){i.$message.success("已审核成功")},300);case 4:case"end":return e.stop()}},e)}))).catch(function(){i.$message({type:"info",message:"已取消审核"})}),e.next=6;break;case 5:return e.abrupt("return");case 6:case"end":return e.stop()}},e,this)}));function t(t,a){return e.apply(this,arguments)}return t}(),handleDelete:function(){var e=Object(s["a"])(regeneratorRuntime.mark(function e(t,a){var n,r,i=this;return regeneratorRuntime.wrap(function(e){while(1)switch(e.prev=e.next){case 0:if(n=a.status,r=a.id,0!==n){e.next=5;break}this.$messageBox.confirm("确定要拒绝该任务么?","提示",{confirmButtonText:"确定",cancelButtonText:"取消",showCancelButton:!0,type:"warning"}).then(Object(s["a"])(regeneratorRuntime.mark(function e(){return regeneratorRuntime.wrap(function(e){while(1)switch(e.prev=e.next){case 0:return e.next=2,Object(u["hb"])(r,2,"不通过");case 2:i.getTasksData({areaId:i.areaId,pageNum:i.pageNum,pageSize:10}),setTimeout(function(){i.$message.success("已审核成功")},300);case 4:case"end":return e.stop()}},e)}))).catch(function(){i.$message({type:"info",message:"已取消审核"})}),e.next=6;break;case 5:return e.abrupt("return");case 6:case"end":return e.stop()}},e,this)}));function t(t,a){return e.apply(this,arguments)}return t}()})},d=g,h=(a("984d"),a("2877")),f=Object(h["a"])(d,n,r,!1,null,null,null);t["default"]=f.exports},dca4:function(e,t,a){},fbcc:function(e,t,a){}}]);