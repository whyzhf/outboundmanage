(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-4e9f78bc"],{"0203":function(e,t,r){},"5a9e":function(e,t,r){"use strict";var a=function(){var e=this,t=e.$createElement,r=e._self._c||t;return r("div",[r("el-table",{directives:[{name:"loading",rawName:"v-loading",value:e.isLoading,expression:"isLoading"}],ref:"multipleTable",attrs:{"element-loading-text":"加载中...","element-loading-spinner":"el-icon-loading","element-loading-background":"rgba(220, 237, 255,.8)",data:e.tableData,"tooltip-effect":"dark",border:"","cell-style":e.getCellStyle,"header-cell-style":e.getHeaderStyle},on:{"selection-change":e.handleSelectionChange}},[e.$route.path.match("/manage/taskcenter")?r("el-table-column",{attrs:{width:"55"},scopedSlots:e._u([{key:"default",fn:function(t){return[r("el-radio",{staticClass:"radio",attrs:{label:t.row.id},nativeOn:{change:function(r){return e.getRadioRow(t.row)}},model:{value:e.radio,callback:function(t){e.radio=t},expression:"radio"}},[e._v(" ")])]}}],null,!1,2432602288)}):e._e(),e.isSelectShow?r("el-table-column",{staticClass:"mulit-select",attrs:{type:"selection",width:"55"}}):e._e(),e._l(e.thList,function(e,t){return r("el-table-column",{key:t,staticStyle:{"font-size":"16px"},attrs:{label:e.ch,prop:e.en}})}),e.isShow?r("el-table-column",{attrs:{label:"状态"},scopedSlots:e._u([{key:"default",fn:function(t){return[r("span",{class:0==t.row.type?"normal":"destroy",domProps:{textContent:e._s(0==t.row.type?"正常":"故障")}}),0==t.row.type?r("span",{class:1===t.row.status?"normal":"useing",domProps:{textContent:e._s(1==t.row.status?"(未使用)":"(使用中)")}}):e._e()]}}],null,!1,1142099066)}):e._e(),e.isVisible?r("el-table-column",{attrs:{label:"任务状态"},scopedSlots:e._u([{key:"default",fn:function(t){return[r("span",{class:0==t.row.status?"checking":1==t.row.status?"normal":2==t.row.status?"destroy":3==t.row.status?"working":"useing",domProps:{textContent:e._s(0==t.row.status?"审核中":1==t.row.status?"审核通过":2==t.row.status?"审核未通过":3==t.row.status?"执行中":"已完成")}})]}}],null,!1,1272741679)}):e._e(),e.isOperateShow?r("el-table-column",{attrs:{label:"/manage/taskverify"===e.$route.path?"审核":"操作"},scopedSlots:e._u([{key:"default",fn:function(t){return[r("div",{staticClass:"row-operate"},[e._t("operate",null,null,t)],2)]}}],null,!0)}):e._e()],2)],1)},s=[],o=(r("8e6e"),r("456d"),r("ac6a"),r("bd86")),n=r("2f62");function i(e,t){var r=Object.keys(e);if(Object.getOwnPropertySymbols){var a=Object.getOwnPropertySymbols(e);t&&(a=a.filter(function(t){return Object.getOwnPropertyDescriptor(e,t).enumerable})),r.push.apply(r,a)}return r}function l(e){for(var t=1;t<arguments.length;t++){var r=null!=arguments[t]?arguments[t]:{};t%2?i(r,!0).forEach(function(t){Object(o["a"])(e,t,r[t])}):Object.getOwnPropertyDescriptors?Object.defineProperties(e,Object.getOwnPropertyDescriptors(r)):i(r).forEach(function(t){Object.defineProperty(e,t,Object.getOwnPropertyDescriptor(r,t))})}return e}var u={name:"dataTable",props:{tableData:{type:Array,required:!0},thList:{type:Array,required:!0},isLoading:{type:Boolean}},data:function(){return{isShow:!1,isVisible:!1,isSelectShow:!1,isOperateShow:!1}},mounted:function(){switch(this.$route.path){case"/manage/equipmentmanage":case"/manage/carmanage":this.isShow=!0;break;default:this.isShow=!1;break}switch(this.$route.path){case"/manage/taskmanage":case"/manage/taskcenter":case"/manage/taskverify":case"/manage/currenttask":this.isVisible=!0;break;default:this.isVisible=!1;break}switch(this.$route.path){case"/manage/historytask":case"/manage/taskverify":case"/manage/taskcenter/tasklist":case"/manage/currenttask":case"/manage/taskcenter/pro/tasklist":this.isSelectShow=!1;break;default:this.isSelectShow=!0;break}switch(this.$route.path){case"/manage/taskcenter":case"/manage/currenttask":case"/manage/historytask":case"/manage/taskcenter/tasklist":case"/manage/taskcenter/pro/tasklist":this.isOperateShow=!1;break;default:this.isOperateShow=!0;break}},methods:l({},Object(n["c"])(["updateMultipleSelection"]),{toggleSelection:function(e){var t=this;e?e.forEach(function(e){t.$refs.multipleTable.toggleRowSelection(e)}):this.$refs.multipleTable.clearSelection()},handleSelectionChange:function(e){this.updateMultipleSelection(e)},getRadioRow:function(e){this.updateMultipleSelection(e)},getCellStyle:function(){return"height:50px;textAlign:center;fontSize:18px;color:rgba(102,102,102,1);fontWeight:400"},getHeaderStyle:function(){return"background:rgba(220,237,255,1);fontWeight:bold;height:50px;textAlign:center;fontSize:18px;color:rgba(102,102,102,1)"}}),computed:l({},Object(n["d"])({multipleSelection:function(e){return e.multipleSelection}}),{radio:{get:function(){return Array.isArray(this.multipleSelection)?0:this.multipleSelection.id},set:function(){return this.multipleSelection.id}}})},c=u,d=(r("7c61"),r("2877")),m=Object(d["a"])(c,a,s,!1,null,null,null);t["a"]=m.exports},6559:function(e,t,r){"use strict";r.r(t);var a=function(){var e=this,t=e.$createElement,r=e._self._c||t;return r("div",{staticClass:"manage-table-wrap"},[r("div",{staticClass:"user-btn-list"},[r("el-button",{attrs:{type:"primary",icon:"el-icon-plus"},on:{click:e.showAddForm}},[e._v("新增")]),r("el-button",{attrs:{type:"primary",icon:"el-icon-delete"},on:{click:e.deleteSelected}},[e._v("删除")]),r("el-dialog",{attrs:{title:"新增用户",width:"25%",visible:e.dialogFormVisible,center:""},on:{"update:visible":function(t){e.dialogFormVisible=t},close:function(t){return e.closeForm("dialogForm")}}},[r("el-form",{ref:"dialogForm",attrs:{model:e.userForm,rules:e.rules,labelPosition:"left"}},[r("el-form-item",{attrs:{label:"姓名",prop:"trueName","label-width":"100px"}},[r("el-input",{attrs:{autocomplete:"off"},model:{value:e.userForm.trueName,callback:function(t){e.$set(e.userForm,"trueName",t)},expression:"userForm.trueName"}})],1),r("el-form-item",{attrs:{label:"编号",prop:"card","label-width":"100px"}},[r("el-input",{attrs:{autocomplete:"off"},model:{value:e.userForm.card,callback:function(t){e.$set(e.userForm,"card",t)},expression:"userForm.card"}})],1),r("el-form-item",{attrs:{label:"密码",prop:"password","label-width":"100px"}},[r("el-input",{attrs:{type:"password",autocomplete:"off"},model:{value:e.userForm.password,callback:function(t){e.$set(e.userForm,"password",t)},expression:"userForm.password"}})],1),r("el-form-item",{attrs:{label:"确认密码",prop:"repassword","label-width":"100px"}},[r("el-input",{attrs:{type:"password",autocomplete:"off"},model:{value:e.userForm.repassword,callback:function(t){e.$set(e.userForm,"repassword",t)},expression:"userForm.repassword"}})],1),r("el-form-item",{attrs:{label:"角色",prop:"roleName","label-width":"100px"}},[r("el-select",{attrs:{placeholder:"请选择角色"},model:{value:e.userForm.roleName,callback:function(t){e.$set(e.userForm,"roleName",t)},expression:"userForm.roleName"}},[r("el-option",{attrs:{value:"监所管理员"}}),r("el-option",{attrs:{value:"干警"}})],1)],1)],1),r("div",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[r("el-button",{attrs:{type:"primary"},on:{click:function(t){e.dialogFormVisible=!1}}},[e._v("取 消")]),r("el-button",{attrs:{type:"primary"},on:{click:function(t){return e.submitAddUser("dialogForm")}}},[e._v("确 定")])],1)],1)],1),r("dataTable",{attrs:{tableData:e.tableData,thList:e.thList,isLoading:e.isLoading},scopedSlots:e._u([{key:"operate",fn:function(t){return[r("div",{on:{click:function(r){return e.handleEditForm(t.$index,t.row)}}},[e._v("编辑")]),r("div",{on:{click:function(r){return e.handleDelete(t.$index,t.row)}}},[e._v("删除")])]}}])}),r("el-dialog",{attrs:{title:"编辑用户",width:"25%",visible:e.isEditShow,center:""},on:{"update:visible":function(t){e.isEditShow=t},close:function(t){return e.closeEditForm("editUser")}}},[r("el-form",{ref:"editUser",attrs:{model:e.editUserForm,rules:e.editUserRule,labelPosition:"left"}},[r("el-form-item",{attrs:{label:"用户名",prop:"userName","label-width":"100px"}},[r("el-input",{attrs:{autocomplete:"off"},model:{value:e.editUserForm.userName,callback:function(t){e.$set(e.editUserForm,"userName",t)},expression:"editUserForm.userName"}})],1),r("el-form-item",{attrs:{label:"编号",prop:"card","label-width":"100px"}},[r("el-input",{attrs:{autocomplete:"off"},model:{value:e.editUserForm.card,callback:function(t){e.$set(e.editUserForm,"card",t)},expression:"editUserForm.card"}})],1),r("el-form-item",{attrs:{label:"旧密码",prop:"oldPassword","label-width":"100px"}},[r("el-input",{attrs:{type:"password",autocomplete:"off"},model:{value:e.editUserForm.oldPassword,callback:function(t){e.$set(e.editUserForm,"oldPassword",t)},expression:"editUserForm.oldPassword"}})],1),r("el-form-item",{attrs:{label:"新密码",prop:"password","label-width":"100px"}},[r("el-input",{attrs:{type:"password",autocomplete:"off"},model:{value:e.editUserForm.password,callback:function(t){e.$set(e.editUserForm,"password",t)},expression:"editUserForm.password"}})],1),r("el-form-item",{attrs:{label:"确认密码",prop:"repassword","label-width":"100px"}},[r("el-input",{attrs:{type:"password",autocomplete:"off"},model:{value:e.editUserForm.repassword,callback:function(t){e.$set(e.editUserForm,"repassword",t)},expression:"editUserForm.repassword"}})],1),r("el-form-item",{attrs:{label:"角色",prop:"roleName","label-width":"100px"}},[r("el-select",{model:{value:e.editUserForm.roleName,callback:function(t){e.$set(e.editUserForm,"roleName",t)},expression:"editUserForm.roleName"}},[r("el-option",{attrs:{value:"监所管理员"}}),r("el-option",{attrs:{value:"干警"}})],1)],1)],1),r("div",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[r("el-button",{attrs:{type:"primary"},on:{click:function(t){e.isEditShow=!1}}},[e._v("取 消")]),r("el-button",{attrs:{type:"primary"},on:{click:function(t){return e.submitEditUser("editUser")}}},[e._v("确 定")])],1)],1),r("div",{staticClass:"pagination-wrap"},[r("el-pagination",{attrs:{background:"",layout:"prev, pager, next",total:e.userTotal},on:{"current-change":e.numPage,"prev-click":e.prevpage,"next-click":e.nextpage}})],1)],1)},s=[],o=(r("8e6e"),r("456d"),r("ac6a"),r("bd86")),n=(r("96cf"),r("3b8d")),i=r("2f62"),l=r("5a9e"),u=r("365c"),c=r("6821f"),d=r.n(c);function m(e,t){var r=Object.keys(e);if(Object.getOwnPropertySymbols){var a=Object.getOwnPropertySymbols(e);t&&(a=a.filter(function(t){return Object.getOwnPropertyDescriptor(e,t).enumerable})),r.push.apply(r,a)}return r}function p(e){for(var t=1;t<arguments.length;t++){var r=null!=arguments[t]?arguments[t]:{};t%2?m(r,!0).forEach(function(t){Object(o["a"])(e,t,r[t])}):Object.getOwnPropertyDescriptors?Object.defineProperties(e,Object.getOwnPropertyDescriptors(r)):m(r).forEach(function(t){Object.defineProperty(e,t,Object.getOwnPropertyDescriptor(r,t))})}return e}var f={name:"UserManage",components:{dataTable:l["a"]},data:function(){var e=this,t=function(e,t,r){var a=/^[\u4E00-\u9FA5]+$/;t||r(new Error("此项不能为空")),a.test(t)?r():r(new Error("只能是中文"))},r=function(){var e=Object(n["a"])(regeneratorRuntime.mark(function e(t,r,a){var s,o;return regeneratorRuntime.wrap(function(e){while(1)switch(e.prev=e.next){case 0:if(s=/^[a-zA-Z0-9]+$/,r||a(new Error("此项不能为空")),s.test(r)){e.next=6;break}a(new Error("只能是数字、字母")),e.next=10;break;case 6:return e.next=8,Object(u["q"])(r);case 8:o=e.sent,"Success"===o.msg?a():a(new Error("该编号已存在"));case 10:case"end":return e.stop()}},e)}));return function(t,r,a){return e.apply(this,arguments)}}(),a=function(t,r,a){r||a(new Error("此项不能为空")),r!==e.userForm.password?a(new Error("两次密码输入不一致")):a()},s=function(){var t=Object(n["a"])(regeneratorRuntime.mark(function t(r,a,s){var o,n;return regeneratorRuntime.wrap(function(t){while(1)switch(t.prev=t.next){case 0:if(o=/^[a-zA-Z0-9]+$/,a||s(new Error("此项不能为空")),o.test(a)){t.next=6;break}s(new Error("只能是数字、字母")),t.next=10;break;case 6:return t.next=8,Object(u["r"])(e.vaildateUserName,d()(d()(a).toUpperCase()).toUpperCase());case 8:n=t.sent,"Success"===n.msg?s():s(new Error("密码错误"));case 10:case"end":return t.stop()}},t)}));return function(e,r,a){return t.apply(this,arguments)}}(),o=function(t,r,a){r||a(new Error("此项不能为空")),r!==e.editUserForm.password?a(new Error("两次密码输入不一致")):a()},i=function(){var t=Object(n["a"])(regeneratorRuntime.mark(function t(r,a,s){var o,n;return regeneratorRuntime.wrap(function(t){while(1)switch(t.prev=t.next){case 0:if(o=/^[a-zA-Z0-9]+$/,a||s(new Error("此项不能为空")),o.test(a)){t.next=6;break}s(new Error("只能是数字、字母")),t.next=10;break;case 6:return t.next=8,Object(u["s"])(a,e.editUserForm.id);case 8:n=t.sent,"Success"===n.msg?s():s(new Error("该用户名已存在"));case 10:case"end":return t.stop()}},t)}));return function(e,r,a){return t.apply(this,arguments)}}(),l=function(){var t=Object(n["a"])(regeneratorRuntime.mark(function t(r,a,s){var o,n;return regeneratorRuntime.wrap(function(t){while(1)switch(t.prev=t.next){case 0:if(o=/^[a-zA-Z0-9]+$/,a||s(new Error("此项不能为空")),o.test(a)){t.next=6;break}s(new Error("只能是数字、字母")),t.next=10;break;case 6:return t.next=8,Object(u["q"])(a,e.editUserForm.id);case 8:n=t.sent,"Success"===n.msg?s():s(new Error("该编号已存在"));case 10:case"end":return t.stop()}},t)}));return function(e,r,a){return t.apply(this,arguments)}}();return{pageNum:1,thList:[{ch:"姓名",en:"trueName"},{ch:"编号",en:"card"},{ch:"角色",en:"roleName"},{ch:"用户名",en:"userName"}],dialogFormVisible:!1,isEditShow:!1,userForm:{trueName:"",card:"",roleName:"",password:"",repassword:""},rules:{trueName:{required:!0,trigger:"blur",validator:t},password:{required:!0,trigger:"blur",validator:a},repassword:{required:!0,trigger:"blur",validator:a},card:{required:!0,trigger:"blur",validator:r},roleName:{required:!0,trigger:"blur"}},editUserForm:{userName:"",card:"",roleName:"",password:"",repassword:"",oldPassword:""},editUserRule:{userName:{required:!0,trigger:"blur",validator:i},oldPassword:{required:!0,trigger:"blur",validator:s},password:{required:!0,trigger:"blur",validator:o},repassword:{required:!0,trigger:"blur",validator:o},card:{required:!0,trigger:"blur",validator:l},roleName:{required:!0,trigger:"blur"}},vaildateUserName:""}},computed:p({},Object(i["d"])({areaId:function(e){return e.areaId},tableData:function(e){return e.userManage.tableData},userTotal:function(e){return e.userManage.userTotal},isLoading:function(e){return e.userManage.isLoading}})),mounted:function(){var e=this;0===this.areaId?setTimeout(function(){var t=e.areaId;e.getUserData({areaId:t,pageNum:e.pageNum,pageSize:10})},0):this.getUserData({areaId:this.areaId,pageNum:this.pageNum,pageSize:10})},methods:p({},Object(i["b"])(["getUserData","getAddUser"]),{prevpage:function(e){this.getUserData({areaId:this.areaId,pageNum:e-1,pageSize:10})},nextpage:function(e){this.getUserData({areaId:this.areaId,pageNum:e+1,pageSize:10})},numPage:function(e){this.pageNum=e,this.getUserData({areaId:this.areaId,pageNum:e,pageSize:10})},showAddForm:function(){this.dialogFormVisible=!0},submitAddUser:function(e){var t=this;this.$refs[e].validate(function(e){if(!e)return setTimeout(function(){t.$message.error("新增用户失败")},300),!1;var r=t.userForm,a=r.password,s=r.roleName,o=r.card;t.userForm.areaId=t.areaId,t.userForm.userName=o,t.userForm.password=d()(d()(a).toUpperCase()).toUpperCase(),"监所管理员"===s?t.userForm.roleId=4:"干警"===s&&(t.userForm.roleId=5),t.getAddUser(t.userForm),t.dialogFormVisible=!1,setTimeout(function(){t.$message.success("新增用户成功")},300)})},closeForm:function(e){this.dialogFormVisible=!1,this.$refs[e].resetFields()},closeEditForm:function(e){this.isEditShow=!1,this.$refs[e].resetFields()},handleEditForm:function(e,t){this.isEditShow=!0;var r=t.userid,a=t.userName,s=t.card,o=t.areaId,n=t.trueName,i=t.roleName,l=t.roleId;this.vaildateUserName=a,this.editUserForm.id=r,this.editUserForm.trueName=n,this.editUserForm.card=s,this.editUserForm.areaId=o,this.editUserForm.userName=a,this.editUserForm.oldRoleName=i,this.editUserForm.oldRoleId=l,this.editUserForm.roleName=i,"监所管理员"===i?this.editUserForm.roleId=4:"干警"===i&&(this.editUserForm.roleId=5)},submitEditUser:function(e){var t=this;this.$refs[e].validate(function(){var e=Object(n["a"])(regeneratorRuntime.mark(function e(r){var a,s,o,n,i;return regeneratorRuntime.wrap(function(e){while(1)switch(e.prev=e.next){case 0:if(!r){e.next=11;break}return a=t.editUserForm,s=a.roleName,o=a.oldRoleName,n=a.oldRoleId,i=a.password,s===o?t.editUserForm.roleId=n:"监所管理员"===s?t.editUserForm.roleId=4:"干警"===s&&(t.editUserForm.roleId=5),t.editUserForm.password=d()(d()(i).toUpperCase()).toUpperCase(),t.isEditShow=!1,e.next=7,Object(u["n"])(t.editUserForm);case 7:t.getUserData({areaId:t.areaId,pageNum:t.pageNum,pageSize:10}),t.$message.success("编辑用户成功"),e.next=13;break;case 11:return setTimeout(function(){t.$message.error("编辑用户失败")},300),e.abrupt("return",!1);case 13:case"end":return e.stop()}},e)}));return function(t){return e.apply(this,arguments)}}())},deleteSelected:function(){var e=this,t=[];this.$store.state.multipleSelection.forEach(function(e){t.push(e.userid)});var r=t.join();0!==t.length&&this.$messageBox.confirm("确定要删除么?","提示",{confirmButtonText:"确定",cancelButtonText:"取消",showCancelButton:!0,type:"warning"}).then(Object(n["a"])(regeneratorRuntime.mark(function t(){var a;return regeneratorRuntime.wrap(function(t){while(1)switch(t.prev=t.next){case 0:return t.next=2,Object(u["D"])(r);case 2:a=t.sent,a&&(e.$message.success("删除成功"),e.getUserData({areaId:e.areaId,pageNum:e.pageNum,pageSize:10}));case 4:case"end":return t.stop()}},t)}))).catch(function(){e.$message({type:"info",message:"已取消删除"})})},handleDelete:function(e,t){var r=this,a=t.userid;this.$messageBox.confirm("确定要删除么?","提示",{confirmButtonText:"确定",cancelButtonText:"取消",showCancelButton:!0,type:"warning"}).then(Object(n["a"])(regeneratorRuntime.mark(function e(){var t;return regeneratorRuntime.wrap(function(e){while(1)switch(e.prev=e.next){case 0:return e.next=2,Object(u["D"])(a);case 2:t=e.sent,t&&(r.$message.success("删除成功"),r.getUserData({areaId:r.areaId,pageNum:r.pageNum,pageSize:10}));case 4:case"end":return e.stop()}},e)}))).catch(function(){r.$message({type:"info",message:"已取消删除"})})}})},g=f,b=(r("8eb1"),r("2877")),h=Object(b["a"])(g,a,s,!1,null,"60443d2b",null);t["default"]=h.exports},"7c61":function(e,t,r){"use strict";var a=r("dca4"),s=r.n(a);s.a},"8eb1":function(e,t,r){"use strict";var a=r("0203"),s=r.n(a);s.a},dca4:function(e,t,r){}}]);