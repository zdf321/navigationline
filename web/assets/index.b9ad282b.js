var h=(s,u,l)=>new Promise((t,i)=>{var c=o=>{try{a(l.next(o))}catch(p){i(p)}},r=o=>{try{a(l.throw(o))}catch(p){i(p)}},a=o=>o.done?t(o.value):Promise.resolve(o.value).then(c,r);a((l=l.apply(s,u)).next())});import{S as z}from"./SingleMenu.86606ae6.js";import{B as M}from"./BaiscRow.03a105fb.js";import{K as x,$ as B,d as N,r as d,c as j,e as q,g as _,y as I,o as D,l as F,k as f,i as v,w as C,z as K}from"./index.dcc91eec.js";import"./menuValueEnum.ea7dad60.js";import"./useEmitter.63415644.js";import"./team.611cb9ab.js";import"./form.8bf32b71.js";import"./search.f48a30ec.js";import"./plugin-vue_export-helper.41ffa612.js";import"./menu.11f2eb8e.js";import"./usePage.57f3776c.js";import"./useCloneMove.8e454083.js";import"./files.d698779e.js";import"./nav.66145f38.js";function A(){return x({url:"/api/v1/tool/loadCategory",method:"get",headers:B})}function H(s){return x({url:"/api/v1/tool/loadTool",method:"get",params:s,headers:B})}function Q(s){return x({url:"/api/v1/tool/searchTool",method:"get",params:s,headers:B})}const R={class:"tools-online-container h-full"},U={class:"tools-header flex flex-col justify-center items-center"},$={class:"tools-search"},G={class:"tools-content flex"},J={class:"left-menu"},O={class:"right-content flex-1"},re=N({__name:"index",setup(s){const u=d(""),l=d(!1),t=d([]),i=d(""),c=d(!0),r=d([]),a=j({pid:"root"});q(()=>t.value.length>0?t.value.find(e=>e.value===a.pid):{text:""});function o(e){u.value="",l.value=!1,a.pid=e.value,u.value?y():m()}function p(){l.value=!1,g(),m()}function w(e){e?y():(l.value=!1,g(),m())}function g(){i.value=t.value.length>0?t.value[0].value:"root"}function S(){return h(this,null,function*(){const{data:e}=yield A();t.value=e.map(n=>({icon:n.icon||"tool-default",value:n.id,text:n.name})),t.value.length>0&&(a.pid=t.value[0].value),g(),m()})}S();function m(){return h(this,null,function*(){const{data:e}=yield H(a);r.value=e,c.value=!1})}function y(){return h(this,null,function*(){c.value=!0;const{data:e}=yield Q({pid:a.pid,search:u.value});l.value=!0,i.value="",r.value=e.length?e[0].childs:[],console.log("listlistlist:",r.value),c.value=!1})}return(e,n)=>{const k=_("SvgIcon"),E=_("el-button"),V=_("el-input"),b=_("el-scrollbar"),L=I("loading");return D(),F("div",R,[f("div",U,[n[1]||(n[1]=f("p",{class:"search-title text-center"},"\u6253\u9020\u5B89\u5168\u3001\u5FEB\u6377\u3001\u514D\u8D39\u7684\u5728\u7EBF\u5DE5\u5177\u7BB1",-1)),f("div",$,[v(V,{size:"large",modelValue:u.value,"onUpdate:modelValue":n[0]||(n[0]=T=>u.value=T),clearable:"",placeholder:"\u641C\u7D22\u9700\u8981\u7684\u5DE5\u5177(\u5982\uFF1A\u56FE\u7247\u4F20\u8F93\u3001png\u8F6Cjpg)",onClear:p,onChange:w},{append:C(()=>[v(E,{onClick:y},{default:C(()=>[v(k,{name:"handle-query-search",size:20,class:"handle-query-search"})]),_:1})]),_:1},8,["modelValue"])])]),f("div",G,[f("div",J,[v(z,{"default-key":i.value,menus:t.value,class:"h-full",onHandleChange:o},null,8,["default-key","menus"])]),K((D(),F("div",O,[v(b,null,{default:C(()=>[v(M,{list:r.value},null,8,["list"])]),_:1})])),[[L,c.value]])])])}}});export{re as default};
//# sourceMappingURL=index.b9ad282b.js.map
