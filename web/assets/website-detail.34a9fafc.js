var y=(b,C,t)=>new Promise((m,v)=>{var w=r=>{try{F(t.next(r))}catch(l){v(l)}},B=r=>{try{F(t.throw(r))}catch(l){v(l)}},F=r=>r.done?m(r.value):Promise.resolve(r.value).then(w,B);F((t=t.apply(b,C)).next())});import{d as k,o as c,l as _,k as e,_ as T,b as I,r as M,g as d,i as s,w as o,t as i,f as a,j as n,m as E,F as x,B as j,h as D}from"./index.96d3aca4.js";import{r as L,e as z}from"./nav.93b2b935.js";import{B as S}from"./BaiscRow.6cad5bd9.js";import{F as H}from"./index.ee0c65c0.js";import{i as q,p as G,v as J,s as K,t as O}from"./vue-qrcode.esm.cba43f53.js";import{_ as P}from"./useEmitter.ba8e6a16.js";import{u as Q,_ as U}from"./useCloneMove.9f8ce0cb.js";import{_ as X}from"./plugin-vue_export-helper.41ffa612.js";import"./usePage.d2f65d4f.js";import"./form.8bf32b71.js";import"./files.a226cb89.js";const Y=k({name:"CollectionTag"}),Z={viewBox:"0 0 1024 1024",xmlns:"http://www.w3.org/2000/svg"},ee=e("path",{fill:"currentColor",d:"M256 128v698.88l196.032-156.864a96 96 0 0 1 119.936 0L768 826.816V128H256zm-32-64h576a32 32 0 0 1 32 32v797.44a32 32 0 0 1-51.968 24.96L531.968 720a32 32 0 0 0-39.936 0L243.968 918.4A32 32 0 0 1 192 893.44V96a32 32 0 0 1 32-32z"},null,-1),te=[ee];function se(b,C,t,m,v,w){return c(),_("svg",Z,te)}var ue=X(Y,[["render",se]]),le="/assets/error.ff3d6e6c.jpg";const oe={class:"website-detail-container"},ie={class:"detail-website"},ae={class:"detail-website-top"},ne={class:"detail-website-top-img"},re={class:"detail-website-top-title"},de={class:"detail-website-top-title-url"},ce={class:"detail-website-top-more"},_e={class:"detail-website-data"},me={style:{flex:"1"}},pe={style:{flex:"1"}},ve={style:{flex:"1"}},Fe={style:{flex:"1"}},fe={style:{flex:"1"}},Ce={key:0,class:"detail-website-keywords"},we={key:1,class:"detail-website-desc"},be={key:2,class:"detail-website-carousel"},Be={key:4,class:"h-full flex justify-center items-center"},ge={class:"detail-website-hint"},he={class:"detail-website-hint-content"},ye=k({__name:"website-detail",setup(b){const C=I(),t=M({}),{cloneMoveData:m,handleCloneMoveVisible:v,handleCloneMoveRes:w}=Q("\u6536\u85CF\u5230");function B(){return y(this,null,function*(){v()})}function F(l){window.open(l)}function r(){return y(this,null,function*(){const{data:l}=yield z({id:C.currentRoute.value.params.id||""});t.value={id:l.id,name:l.name,url:l.url,categoryName:l.categoryName,hitsNum:l.hitsNum,collectNum:l.collectNum,createTime:l.createTime,keywords:l.keywords,desc:l.desc,logo:l.logo,tags:l.tags,screenshots:l.screenshots,relationWebsites:l.relationWebsites}})}return r(),(l,u)=>{const g=d("SvgIcon"),A=d("el-image"),h=d("el-button"),N=d("el-popover"),$=d("el-tooltip"),p=d("el-icon"),V=d("el-carousel-item"),W=d("el-carousel"),R=d("el-empty");return c(),_(x,null,[e("div",oe,[e("div",ie,[e("div",ae,[e("div",ne,[s(A,{src:t.value.logo},{error:o(()=>[s(g,{name:"list-website",size:48})]),_:1},8,["src"])]),e("div",re,[e("h2",null,i(t.value.name),1),e("p",de,i(t.value.url),1)]),e("div",ce,[s(N,{placement:"bottom",width:"150",trigger:"hover"},{reference:o(()=>[s(h,null,{default:o(()=>[s(g,{name:"handle-collect"})]),_:1})]),default:o(()=>[s(a(q),{options:{width:150},value:t.value.url},null,8,["value"])]),_:1}),s($,{content:"\u70B9\u51FB\u6536\u85CF",placement:"bottom"},{default:o(()=>[s(h,{onClick:B},{default:o(()=>[s(g,{name:"handle-collect"})]),_:1})]),_:1}),s(h,{type:"primary",onClick:u[0]||(u[0]=f=>F(t.value.url))},{default:o(()=>[u[2]||(u[2]=n(" \u7ACB\u5373\u8BBF\u95EE ")),s(p,{class:"el-icon--right"},{default:o(()=>[s(a(L))]),_:1})]),_:1})])]),e("div",_e,[e("div",me,[e("span",null,[s(p,null,{default:o(()=>[s(a(G))]),_:1}),n("\xA0\u5206\u7C7B\uFF1A"+i(t.value.categoryName),1)])]),e("div",pe,[e("span",null,[s(p,null,{default:o(()=>[s(a(J))]),_:1}),n("\xA0\u6D4F\u89C8\u91CF\uFF1A"+i(t.value.hitsNum||0),1)])]),e("div",ve,[e("span",null,[s(p,null,{default:o(()=>[s(a(K))]),_:1}),n("\xA0\u6536\u85CF\u91CF\uFF1A"+i(t.value.collectNum||0),1)])]),e("div",Fe,[e("span",null,[s(p,null,{default:o(()=>[s(a(ue))]),_:1}),n("\xA0\u6807\u7B7E\uFF1A"+i((t.value.tags||[]).join("\uFF0C")),1)])]),e("div",fe,[e("span",null,[s(p,null,{default:o(()=>[s(a(O))]),_:1}),n("\xA0\u6536\u5F55\u65F6\u95F4\uFF1A"+i(t.value.createTime),1)])])]),t.value.keywords?(c(),_("div",Ce,[e("span",null,"\u5173\u952E\u8BCD\uFF1A"+i(t.value.keywords),1)])):E("",!0),t.value.desc?(c(),_("div",we,[e("span",null,"\u7F51\u7AD9\u63CF\u8FF0\uFF1A"+i(t.value.desc),1)])):E("",!0),t.value.screenshots?(c(),_("div",be,[s(W,{height:"600px"},{default:o(()=>[(c(!0),_(x,null,j(t.value.screenshots,f=>(c(),D(V,{key:f},{default:o(()=>[s(A,{src:f,style:{width:"100%"}},{default:o(()=>u[3]||(u[3]=[e("div",{slot:"error",class:"image-slot"},[e("img",{src:le,width:"100%"})],-1)])),_:2},1032,["src"])]),_:2},1024))),128))]),_:1})])):E("",!0),u[10]||(u[10]=e("div",{class:"detail-website-recommend"},[e("span",null,"\u540C\u7C7B\u7F51\u5740")],-1)),t.value.relationWebsites?(c(),D(S,{key:3,list:t.value.relationWebsites},null,8,["list"])):(c(),_("div",Be,[s(R,{description:"\u6682\u65E0\u6570\u636E"})])),e("div",ge,[u[9]||(u[9]=e("p",{class:"detail-website-hint-title"},"\u7279\u522B\u63D0\u793A",-1)),e("p",he,[u[4]||(u[4]=n(" \u672C\u7F51\u9875\u5E76\u975E\u201C")),e("i",null,i(t.value.name),1),u[5]||(u[5]=n("\u201D\u5B98\u7F51\uFF0C\u9875\u9762\u5185\u5BB9\u662F\u7531\u3010navigationline\u3011\u7F16\u5F55\u4E8E\u4E92\u8054\u7F51\uFF0C\u53EA\u4F5C\u5C55\u793A\u4E4B\u7528\uFF1B\u5982\u679C\u6709\u4E0E\u201C")),e("i",null,i(t.value.name),1),u[6]||(u[6]=n("\u201D\u76F8\u5173\u4E1A\u52A1\u4E8B\u5B9C\uFF0C\u8BF7\u8BBF\u95EE\u5176\u7F51\u7AD9\u5E76\u83B7\u53D6\u8054\u7CFB\u65B9\u5F0F\uFF1B\u3010navigationline\u3011\u4E0E\u201C")),e("i",null,i(t.value.name),1),u[7]||(u[7]=n("\u201D\u65E0\u4EFB\u4F55\u5173\u7CFB\uFF0C\u5BF9\u4E8E\u201C")),e("i",null,i(t.value.name),1),u[8]||(u[8]=n("\u201D\u7F51\u7AD9\u4E2D\u7684\u4FE1\u606F\uFF0C\u8BF7\u7528\u6237\u8C28\u614E\u8FA8\u8BC6\u5176\u771F\u4F2A\u3002 "))])])]),s(H)]),s(P,{visible:a(m).visible,"onUpdate:visible":u[1]||(u[1]=f=>a(m).visible=f),title:a(m).title,footer:!1},{default:o(()=>[s(U,{isCollect:"",itemId:t.value.id,onFormResponse:a(w)},null,8,["itemId","onFormResponse"])]),_:1},8,["visible","title"])],64)}}});var Me=T(ye,[["__scopeId","data-v-12efc242"]]);export{Me as default};
//# sourceMappingURL=website-detail.34a9fafc.js.map