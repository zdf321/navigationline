var m=(h,a,l)=>new Promise((i,v)=>{var w=o=>{try{_(l.next(o))}catch(r){v(r)}},g=o=>{try{_(l.throw(o))}catch(r){v(r)}},_=o=>o.done?i(o.value):Promise.resolve(o.value).then(w,g);_((l=l.apply(h,a)).next())});import{d as R,g as d,o as c,l as f,k as e,h as F,w as n,a3 as I,i as t,t as p,F as y,B as M,j as V,f as u,_ as $}from"./index.dcc91eec.js";import{u as j}from"./usePage.57f3776c.js";import{_ as x,b as E}from"./useEmitter.63415644.js";import{u as H,_ as L}from"./useCloneMove.8e454083.js";import{r as T,d as U}from"./nav.66145f38.js";import{g as A}from"./form.8bf32b71.js";const G={class:"basic-card-header flex justify-between items-center"},P={class:"ml-1"},q={class:"view flex items-center cursor-pointer"},J={class:"ml-1"},K={class:"basic-card-content flex items-center"},O={class:"logo"},Q={class:"label relative"},W={class:"overflow-hidden"},X={class:"label-name"},Y=["title"],Z=R({__name:"BasicCard",props:{item:{}},setup(h){const a=h,l=j(),{cloneMoveData:i,handleCloneMoveVisible:v,handleCloneMoveRes:w}=H("\u6536\u85CF\u5230");function g(){return m(this,null,function*(){v()})}function _(){return m(this,null,function*(){yield r(),l("/resource-navigation/website-detail/"+a.item.id)})}function o(){return m(this,null,function*(){a.item.dataType==2&&window.open(a.item.websiteInfo.url),yield r()})}function r(){return m(this,null,function*(){const s=A({id:a.item.id});yield U(s),E("handleUpdateList")})}return(s,k)=>{const C=d("SvgIcon"),B=d("el-tooltip"),N=d("el-image"),z=d("el-tag"),D=d("el-icon"),S=d("el-card");return c(),f(y,null,[e("div",{class:"basic-card-container",onClick:o},[(c(),F(S,{class:"basic-card",shadow:"hover",key:s.item.id},{header:n(()=>[e("div",G,[e("span",{class:"collect flex items-center cursor-pointer",onClick:I(g,["stop"])},[t(B,{content:"\u6536\u85CF\u91CF",placement:"top"},{default:n(()=>[t(C,{name:"handle-collect",size:13})]),_:1}),e("span",P,p(s.item.collectNum||0),1)]),e("span",q,[t(B,{content:"\u6D4F\u89C8\u91CF",placement:"top"},{default:n(()=>[t(C,{name:"handle-view",size:16})]),_:1}),e("span",J,p(s.item.hitsNum||0),1)])])]),default:n(()=>[e("div",K,[e("div",O,[t(N,{src:s.item.websiteInfo.logo},{error:n(()=>[t(C,{name:"list-website",size:40})]),_:1},8,["src"])]),e("div",Q,[e("h4",W,[e("span",X,p(s.item.name),1),(c(!0),f(y,null,M(s.item.tags,b=>(c(),F(z,{class:"label-tag",type:"info",key:b},{default:n(()=>[V(p(b),1)]),_:2},1024))),128))]),e("p",{class:"overflow-hidden",title:s.item.desc},p(s.item.desc),9,Y),t(D,{color:"#999",size:20,class:"access",onClick:I(_,["stop"])},{default:n(()=>[t(u(T))]),_:1})])])]),_:1}))]),t(x,{visible:u(i).visible,"onUpdate:visible":k[0]||(k[0]=b=>u(i).visible=b),title:u(i).title,footer:!1},{default:n(()=>[t(L,{isCollect:"",itemId:s.item.id,onFormResponse:u(w)},null,8,["itemId","onFormResponse"])]),_:1},8,["visible","title"])],64)}}});const ee={class:"basic-row"},se=R({__name:"BaiscRow",props:{list:{}},setup(h){return(a,l)=>(c(),f("div",ee,[(c(!0),f(y,null,M(a.list,i=>(c(),f("div",{class:"basic-col",key:i.id},[t(Z,{item:i},null,8,["item"])]))),128))]))}});var re=$(se,[["__scopeId","data-v-e2b76032"]]);export{re as B};
//# sourceMappingURL=BaiscRow.03a105fb.js.map