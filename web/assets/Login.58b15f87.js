var O=(L,c,u)=>new Promise((_,n)=>{var r=o=>{try{d(u.next(o))}catch(f){n(f)}},s=o=>{try{d(u.throw(o))}catch(f){n(f)}},d=o=>o.done?_(o.value):Promise.resolve(o.value).then(r,s);d((u=u.apply(L,c)).next())});import{u as U,a as S,L as i,b as $}from"./useUserLogin.a921a5f0.js";import{d as V,u as j,a as q,b as P,r as h,c as M,e as C,f as F,g as p,o as v,h as x,w as a,i as t,j as R,t as z,k as y,_ as G,R as A,l as D,m as k,n as Q,p as J,E as K,q as W,s as X,v as Y,x as Z}from"./index.96d3aca4.js";import{u as ee}from"./usePage.d2f65d4f.js";import{u as te,a as oe,b as se}from"./useEmitter.ba8e6a16.js";import{H as ne}from"./index.3670952a.js";import{V as ue}from"./Verify.fe047753.js";import"./useFiles.8ce8d7f0.js";import"./files.a226cb89.js";import"./menuValueEnum.ea7dad60.js";import"./avatar.9103c0b9.js";import"./consts.0b1c5abe.js";import"./team.4e4308de.js";import"./form.8bf32b71.js";import"./menu.11f2eb8e.js";import"./plugin-vue_export-helper.41ffa612.js";const le={class:"ops-button flex justify-between items-center"},ae=V({__name:"LoginForm",setup(L){const c=j(),u=q(),_=P(),n=h(),{validForm:r}=$(n),{getFormRules:s}=U(),{setLoginState:d}=S(),o=h(!1),f=ee(),g=M({account:"",password:"",loginType:0}),m=C(()=>F(o)?"\u767B\u5F55\u4E2D...":"\u7ACB\u5373\u767B\u5F55"),e=_.currentRoute.value.href;function w(){d(i.REGISTER)}function E(){return O(this,null,function*(){if(!!(yield r()))try{o.value=!0,yield c.login(g),setTimeout(()=>{if(e.indexOf("callback")>0){const l=e.substring(e.indexOf("callback")+9);window.open(l,"_self")}else f(u.getActiveTeamType==="TEAM"?A.TEAM:A.SELF_FILE);o.value=!1},500)}catch(l){console.log(l),o.value=!1}})}return(b,l)=>{const B=p("el-input"),T=p("ElFormItem"),N=p("el-button"),H=p("ElForm");return v(),x(H,{ref_key:"ruleFormRef",ref:n,model:g,rules:F(s),"label-position":"left","label-width":"0",size:"large",class:"login-basic-form"},{default:a(()=>[t(T,{label:"",prop:"account"},{default:a(()=>[t(B,{modelValue:g.account,"onUpdate:modelValue":l[0]||(l[0]=I=>g.account=I),placeholder:"\u8BF7\u8F93\u5165\u7528\u6237\u540D"},null,8,["modelValue"])]),_:1}),t(T,{label:"",prop:"password"},{default:a(()=>[t(B,{modelValue:g.password,"onUpdate:modelValue":l[1]||(l[1]=I=>g.password=I),type:"password",placeholder:"\u8BF7\u8F93\u5165\u5BC6\u7801","show-password":""},null,8,["modelValue"])]),_:1}),t(T,null,{default:a(()=>[t(N,{loading:o.value,class:"w-full",type:"primary",round:"",onClick:E},{default:a(()=>[R(z(m.value),1)]),_:1},8,["loading"])]),_:1}),y("div",le,[l[3]||(l[3]=y("span",{class:"forget cursor-pointer"},null,-1)),t(N,{type:"text",onClick:w},{default:a(()=>l[2]||(l[2]=[R("\u53BB\u6CE8\u518C")])),_:1})])]),_:1},8,["model","rules"])}}});var re=G(ae,[["__scopeId","data-v-0b927d5c"]]);const ie={key:0,class:"agree-terms flex justify-center items-center"},ce=V({__name:"AgreeTerms",setup(L){const{getLoginState:c}=S(),u=C(()=>[i.LOGIN,i.MOBILE,i.QR_CODE,i.REGISTER].includes(F(c))),_=C(()=>[i.LOGIN,i.MOBILE].includes(F(c))?"\u767B\u5F55":"\u6CE8\u518C");function n(){const r=Q.resolve(A.TOS).href;window.open(r,"_blank")}return(r,s)=>{const d=p("el-button");return u.value?(v(),D("div",ie,[y("span",null,z(_.value)+"\u5373\u8868\u793A\u60A8\u5DF2\u9605\u8BFB\u5E76\u540C\u610F",1),t(d,{type:"text",onClick:n},{default:a(()=>s[0]||(s[0]=[R("\u670D\u52A1\u6761\u6B3E")])),_:1})])):k("",!0)}}});var de=G(ce,[["__scopeId","data-v-01102ff6"]]);const me={class:"ops-button flex justify-end items-center"},pe=V({__name:"RegisterForm",emits:["reset"],setup(L,{emit:c}){const u=h(),{setLoginState:_}=S(),n=h(!1),{validForm:r}=$(u),s=M({account:"",password:"",code:""}),{getFormRules:d}=U(),o=c;function f(){_(i.LOGIN),o("reset")}function g(){return O(this,null,function*(){if(!!(yield r()))try{n.value=!0,yield J(s),setTimeout(()=>{K.success("\u6CE8\u518C\u6210\u529F\uFF0C\u8BF7\u767B\u5F55"),f(),n.value=!1},500)}catch(e){console.log(e),n.value=!1}})}return(m,e)=>{const w=p("el-input"),E=p("ElFormItem"),b=p("el-button"),l=p("ElForm");return v(),x(l,{ref_key:"ruleFormRef",ref:u,model:s,rules:F(d),"label-position":"left","label-width":"0",size:"large",class:"register-form"},{default:a(()=>[t(E,{label:"",prop:"account"},{default:a(()=>[t(w,{modelValue:s.account,"onUpdate:modelValue":e[0]||(e[0]=B=>s.account=B),placeholder:"\u8BF7\u8F93\u5165\u7528\u6237\u540D"},null,8,["modelValue"])]),_:1}),t(E,{label:"",prop:"password"},{default:a(()=>[t(w,{modelValue:s.password,"onUpdate:modelValue":e[1]||(e[1]=B=>s.password=B),type:"password",placeholder:"\u8BF7\u8F93\u5165\u5BC6\u7801","show-password":""},null,8,["modelValue"])]),_:1}),t(E,null,{default:a(()=>[t(b,{loading:n.value,class:"w-full",type:"primary",round:"",onClick:g},{default:a(()=>e[2]||(e[2]=[R(" \u7ACB\u5373\u6CE8\u518C ")])),_:1},8,["loading"])]),_:1}),y("div",me,[e[4]||(e[4]=y("span",{class:"hased"},"\u5DF2\u6709\u5E10\u53F7,",-1)),t(b,{type:"text",onClick:f},{default:a(()=>e[3]||(e[3]=[R("\u53BB\u767B\u5F55")])),_:1})])]),_:1},8,["model","rules"])}}});var _e=G(pe,[["__scopeId","data-v-657db850"]]);const fe={class:"login-container h-full"},ge={class:"flex justify-center items-center h-full login-content"},ve={class:"login-form-container p-8 pl-12 pr-12 xl:w-6/12"},Fe={class:"login-header mb-4"},ye={key:1,class:"register-state"},De=V({__name:"Login",setup(L){const{getLoginState:c,setLoginState:u}=S(),_=C(()=>F(c)===i.LOGIN),n=C(()=>F(c)===i.REGISTER),r=h("login"),s=h(null);W(()=>F(r),m=>{u(m==="login"?i.LOGIN:i.MOBILE)}),X(()=>{te("showVerify")}),oe("showVerify",d);function d(){s.value.show()}function o(m){se("verfiySuccess",m)}function f(){r.value="login"}g();function g(){let m=Y("$cookies"),e=Z(window.location.hash);if(!e)return;m.config("7d"),m.set("x-access-token",e),j().oauthLogin()}return(m,e)=>{const w=p("el-tab-pane"),E=p("el-tabs");return v(),D("div",fe,[t(ne),y("div",ge,[y("div",ve,[y("div",Fe,[_.value?(v(),x(E,{key:0,modelValue:r.value,"onUpdate:modelValue":e[0]||(e[0]=b=>r.value=b),class:"demo-tabs"},{default:a(()=>[t(w,{label:"\u5E10\u53F7\u5BC6\u7801\u767B\u5F55",name:"login"})]),_:1},8,["modelValue"])):k("",!0),n.value?(v(),D("div",ye,"\u6CE8\u518C")):k("",!0)]),_.value?(v(),x(re,{key:0})):k("",!0),n.value?(v(),x(_e,{key:1,onReset:f})):k("",!0),t(de)])]),t(ue,{mode:"pop",captchaType:"blockPuzzle",imgSize:{width:"400px",height:"200px"},ref_key:"verify",ref:s,onSuccess:o},null,512)])}}});export{De as default};
//# sourceMappingURL=Login.58b15f87.js.map