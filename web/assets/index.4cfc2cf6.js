var k=(w,i,s)=>new Promise((o,h)=>{var C=l=>{try{c(s.next(l))}catch(r){h(r)}},n=l=>{try{c(s.throw(l))}catch(r){h(r)}},c=l=>l.done?o(l.value):Promise.resolve(l.value).then(C,n);c((s=s.apply(w,i)).next())});import{S as W}from"./SingleMenu.86606ae6.js";import{B as X}from"./BaiscRow.03a105fb.js";import{d as z,r as u,q as Y,g as N,o as v,h as B,w as U,l as y,B as R,F as q,e as V,c as Z,y as I,k as _,P as ee,t as ae,i as g,z as te,m as le,N as ne,f as L}from"./index.dcc91eec.js";import{B as se}from"./BasicRound.88a26b9f.js";import{f as oe,c as ce,a as E,b as ie}from"./nav.66145f38.js";import{a as ue}from"./useEmitter.63415644.js";import{s as re}from"./index.b17e1e50.js";import"./menuValueEnum.ea7dad60.js";import"./team.611cb9ab.js";import"./form.8bf32b71.js";import"./search.f48a30ec.js";import"./plugin-vue_export-helper.41ffa612.js";import"./menu.11f2eb8e.js";import"./usePage.57f3776c.js";import"./useCloneMove.8e454083.js";import"./files.d698779e.js";const de=z({__name:"BasicTabs",props:{list:{}},emits:["handleTabChange"],setup(w,{emit:i}){const s=w,o=u(s.list[0].id),h=i;h("handleTabChange",s.list[0]),Y(()=>s.list,n=>{n.length>0&&(o.value=n[0].id,h("handleTabChange",n[0]))},{deep:!0,immediate:!0});function C(n){const{index:c}=n,l=s.list[c];h("handleTabChange",l)}return(n,c)=>{const l=N("el-tab-pane"),r=N("el-tabs");return v(),B(r,{modelValue:o.value,"onUpdate:modelValue":c[0]||(c[0]=m=>o.value=m),class:"demo-tabs",onTabClick:C},{default:U(()=>[(v(!0),y(q,null,R(n.list,m=>(v(),B(l,{label:m.name,name:m.id,key:m.id},null,8,["label","name"]))),128))]),_:1},8,["modelValue"])}}});const he={class:"resource-navigation-container h-full"},ve={class:"resource-header flex flex-col justify-center items-center"},me={class:"search-navs flex items-center"},fe=["onClick"],pe={class:"resource-search"},_e={class:"mt-8 mb-5 flex justify-center"},ge={class:"resource-content flex"},ye={class:"left-menu"},Ce={class:"right-content flex-1"},be={key:0,class:"resource-tabs flex items-center"},xe={key:1,class:"h-full flex justify-center items-center"},He=z({__name:"index",setup(w){const i=u(""),s=u(!1),o=u([]),h=u(""),C=u(!0),n=u([]),c=u(""),l=u(""),r=u(""),m=V(()=>{var e;return n.value.length===0?[]:((e=n.value.find(a=>a.categoryName===c.value))==null?void 0:e.childs).map(a=>({name:a.name,id:a.name,searchUrl:a.searchUrl,placeholder:a.placeholder}))}),j=V(()=>{var t;return(t=L(m).find(e=>e.name===l.value))==null?void 0:t.placeholder}),f=u([]),b=u([]),p=Z({id:"root",tabId:""});function F(t){var a;s.value=!1,i.value="",b.value=[],p.id=t.value;const e=o.value.find(d=>d.value===p.id);e&&(e==null?void 0:e.childs)?(f.value=(a=e==null?void 0:e.childs)==null?void 0:a.map(d=>({id:d.value,name:d.text})),(e==null?void 0:e.childs.length)===0&&(p.tabId=p.id,S())):f.value=[]}function M(t){p.tabId=t.id,S()}function $(t){c.value=t.categoryName;const e=L(n).find(a=>a.categoryName===c.value);e&&e.childs&&(l.value=e.childs[0].name,r.value=e.childs[0].searchUrl)}function H(t){l.value=t;const e=V(()=>{var a;return(a=L(m).find(d=>d.name===l.value))==null?void 0:a.searchUrl});r.value=e.value}function P(){s.value=!1,D()}function D(){h.value=o.value.length>0?o.value[0].value:"root"}function A(t,e){re(t).then(a=>{a&&a.length>0&&e(a)})}ue("handleUpdateList",S);function K(){return k(this,null,function*(){const{data:t}=yield oe();if(n.value=t,t&&t.length>0){let e=t.find(a=>a.default);e||(e=t[0]),c.value=e.categoryName,l.value=e.childs&&e.childs.length>0?e.childs[0].name:"",r.value=e.childs&&e.childs.length>0?e.childs[0].searchUrl:""}})}function O(){return k(this,null,function*(){var e;const{data:t}=yield ce();o.value=t.map(a=>({icon:a.icon,text:a.name,value:a.id,childs:a.childs.map(d=>({value:d.id,text:d.name}))})),o.value.length>0&&(D(),p.id=o.value[0].value,f.value=(e=o.value[0].childs)==null?void 0:e.map(a=>({id:a.value,name:a.text}))),C.value=!1})}K(),O();function S(){return k(this,null,function*(){if(s.value){const{data:t}=yield E({categoryName:l.value,search:i.value});b.value=t}else{const{data:t}=yield ie({pid:p.tabId});b.value=t}})}function T(){return k(this,null,function*(){if(console.log(i.value),!!i.value)if(r.value)window.open(r.value+i.value);else{const{data:t}=yield E({categoryName:l.value,search:i.value});s.value=!0,b.value=t;const e=[{id:"\u641C\u7D22\u7ED3\u679C",name:"\u641C\u7D22\u7ED3\u679C"}];f.value=e,h.value=""}})}return(t,e)=>{const a=N("SvgIcon"),d=N("el-button"),Q=N("el-autocomplete"),G=N("el-empty"),J=I("loading");return v(),y("div",he,[_("div",ve,[_("div",me,[(v(!0),y(q,null,R(n.value,x=>(v(),y("div",{class:ee(["navs-item",{"navs-item--active":x.categoryName===c.value}]),key:x.id,onClick:Ne=>$(x)},ae(x.categoryName),11,fe))),128))]),_("div",pe,[g(Q,{size:"large",modelValue:i.value,"onUpdate:modelValue":e[0]||(e[0]=x=>i.value=x),clearable:"","trigger-on-focus":!1,placeholder:j.value,"fetch-suggestions":A,"select-when-unmatched":!0,onClear:P,onSelect:T},{append:U(()=>[g(d,{onClick:T},{default:U(()=>[g(a,{name:"handle-query-search",size:20,class:"handle-query-search"})]),_:1})]),_:1},8,["modelValue","placeholder"])]),_("div",_e,[g(se,{list:m.value,defaultValue:l.value,onChange:H},null,8,["list","defaultValue"])])]),_("div",ge,[_("div",ye,[g(W,{elIcon:!0,"default-key":h.value,menus:o.value,class:"h-full",onHandleChange:F},null,8,["default-key","menus"])]),te((v(),y("div",Ce,[f.value.length>0?(v(),y("div",be,[g(de,{list:f.value,onHandleTabChange:M},null,8,["list"])])):le("",!0),_("div",{style:ne(`height:calc(100% - ${f.value.length>0?"60px":"0px"});overflow-y:auto`)},[b.value.length>0?(v(),B(X,{key:0,list:b.value},null,8,["list"])):(v(),y("div",xe,[g(G,{description:"\u6682\u65E0\u6570\u636E"})]))],4)])),[[J,C.value]])])])}}});export{He as default};
//# sourceMappingURL=index.4cfc2cf6.js.map
