import{J as F,K as se,_ as ie,L as ae,r as t,c as R,e as ce,q as ye,M as de,o as T,l as O,N as b,k as s,z as ne,O as re,i as me,w as be,P as $,t as G,m as U,T as Se,Q as ue,S as he,F as xe,B as we,D as ze,j as ke,h as Be,C as Ce}from"./index.dcc91eec.js";function ee(r,a="XwKsGlMcdPMEhR1B"){var i=F.enc.Utf8.parse(a),e=F.enc.Utf8.parse(r),g=F.AES.encrypt(e,i,{mode:F.mode.ECB,padding:F.pad.Pkcs7});return g.toString()}function fe(r){var a,i,e,g,N=r.$el.parentNode.offsetWidth||window.offsetWidth,d=r.$el.parentNode.offsetHeight||window.offsetHeight;return r.imgSize.width.indexOf("%")!=-1?a=parseInt(r.imgSize.width)/100*N+"px":a=r.imgSize.width,r.imgSize.height.indexOf("%")!=-1?i=parseInt(r.imgSize.height)/100*d+"px":i=r.imgSize.height,r.barSize.width.indexOf("%")!=-1?e=parseInt(r.barSize.width)/100*N+"px":e=r.barSize.width,r.barSize.height.indexOf("%")!=-1?g=parseInt(r.barSize.height)/100*d+"px":g=r.barSize.height,{imgWidth:a,imgHeight:i,barWidth:e,barHeight:g}}function ve(r){return se({url:"/api/v1/captcha/get",method:"post",data:r})}function ge(r){return se({url:"/api/v1/captcha/check",method:"post",data:r})}const Te={name:"VerifySlide",props:{captchaType:{type:String},type:{type:String,default:"1"},mode:{type:String,default:"fixed"},vSpace:{type:Number,default:5},explain:{type:String,default:"\u5411\u53F3\u6ED1\u52A8\u5B8C\u6210\u9A8C\u8BC1"},imgSize:{type:Object,default(){return{width:"310px",height:"155px"}}},blockSize:{type:Object,default(){return{width:"50px",height:"50px"}}},barSize:{type:Object,default(){return{width:"310px",height:"40px"}}}},setup(r,a){const{mode:i,captchaType:e,vSpace:g,imgSize:N,barSize:d,type:v,blockSize:S,explain:M}=ae(r),{proxy:c}=ue();let o=t(""),h=t(""),z=t(""),P=t(""),p=t(""),y=t(""),k=t(""),V=t(""),x=t(""),W=t(""),K=t(""),B=R({imgHeight:0,imgWidth:0,barHeight:0,barWidth:0}),Q=t(0),L=t(0),A=t(void 0),E=t(void 0),I=t(void 0),H=t("#ddd"),_=t(void 0),j=t("icon-right"),u=t(!1),l=t(!1),w=t(!0),m=t(""),J=t(""),X=t(0);const D=ce(()=>c.$el.querySelector(".verify-bar-area"));function oe(){W.value=M.value,le(),he(()=>{let{imgHeight:n,imgWidth:f,barHeight:q,barWidth:C}=fe(c);B.imgHeight=n,B.imgWidth=f,B.barHeight=q,B.barWidth=C,c.$parent.$emit("ready",c)}),window.removeEventListener("touchmove",function(n){Y(n)}),window.removeEventListener("mousemove",function(n){Y(n)}),window.removeEventListener("touchend",function(){Z()}),window.removeEventListener("mouseup",function(){Z()}),window.addEventListener("touchmove",function(n){Y(n)}),window.addEventListener("mousemove",function(n){Y(n)}),window.addEventListener("touchend",function(){Z()}),window.addEventListener("mouseup",function(){Z()})}ye(v,()=>{oe()}),de(()=>{oe(),c.$el.onselectstart=function(){return!1}});function pe(n){if(n=n||window.event,n.touches)var f=n.touches[0].pageX;else var f=n.clientX;console.log(D),X.value=Math.floor(f-D.value.getBoundingClientRect().left),y.value=+new Date,l.value==!1&&(W.value="",I.value="#337ab7",H.value="#337AB7",_.value="#fff",n.stopPropagation(),u.value=!0)}function Y(n){if(n=n||window.event,u.value&&l.value==!1){if(n.touches)var f=n.touches[0].pageX;else var f=n.clientX;var q=D.value.getBoundingClientRect().left,C=f-q;C>=D.value.offsetWidth-parseInt(parseInt(S.value.width)/2)-2&&(C=D.value.offsetWidth-parseInt(parseInt(S.value.width)/2)-2),C<=0&&(C=parseInt(parseInt(S.value.width)/2)),A.value=C-X.value+"px",E.value=C-X.value+"px"}}function Z(){if(k.value=+new Date,u.value&&l.value==!1){var n=parseInt((A.value||"").replace("px",""));n=n*310/parseInt(B.imgWidth);let f={captchaType:e.value,pointJson:o.value?ee(JSON.stringify({x:n,y:5}),o.value):JSON.stringify({x:n,y:5}),token:p.value};ge(f).then(q=>{if(q.repCode=="0000"){I.value="#5cb85c",H.value="#5cb85c",_.value="#fff",j.value="icon-check",w.value=!1,l.value=!0,i.value=="pop"&&setTimeout(()=>{c.$parent.clickShow=!1,te()},1500),h.value=!0,x.value=`${((k.value-y.value)/1e3).toFixed(2)}s\u9A8C\u8BC1\u6210\u529F`;var C=o.value?ee(p.value+"---"+JSON.stringify({x:n,y:5}),o.value):p.value+"---"+JSON.stringify({x:n,y:5});setTimeout(()=>{x.value="",c.$parent.closeBox(),c.$parent.$emit("success",{captchaVerification:C})},1e3)}else I.value="#d9534f",H.value="#d9534f",_.value="#fff",j.value="icon-close",h.value=!1,setTimeout(function(){te()},1e3),c.$parent.$emit("error",c),x.value="\u9A8C\u8BC1\u5931\u8D25",setTimeout(()=>{x.value=""},1e3)}),u.value=!1}}const te=()=>{w.value=!0,K.value="",m.value="left .3s",A.value=0,E.value=void 0,J.value="width .3s",H.value="#ddd",I.value="#fff",_.value="#000",j.value="icon-right",l.value=!1,le(),setTimeout(()=>{J.value="",m.value="",W.value=M.value},300)};function le(){let n={captchaType:e.value};ve(n).then(f=>{f.repCode=="0000"?(z.value=f.repData.originalImageBase64,P.value=f.repData.jigsawImageBase64,p.value=f.repData.token,o.value=f.repData.secretKey):x.value=f.repMsg})}return{secretKey:o,passFlag:h,backImgBase:z,blockBackImgBase:P,backToken:p,startMoveTime:y,endMovetime:k,tipsBackColor:V,tipWords:x,text:W,finishText:K,setSize:B,top:Q,left:L,moveBlockLeft:A,leftBarWidth:E,moveBlockBackgroundColor:I,leftBarBorderColor:H,iconColor:_,iconClass:j,status:u,isEnd:l,showRefresh:w,transitionLeft:m,transitionWidth:J,barArea:D,refresh:te,start:pe}}},We={style:{position:"relative"}},Ie=["src"],He=["textContent"],_e=["textContent"],Oe=["src"];function Ne(r,a,i,e,g,N){return T(),O("div",We,[i.type==="2"?(T(),O("div",{key:0,class:"verify-img-out",style:b({height:parseInt(e.setSize.imgHeight)+i.vSpace+"px"})},[s("div",{class:"verify-img-panel",style:b({width:e.setSize.imgWidth,height:e.setSize.imgHeight})},[s("img",{src:"data:image/png;base64,"+e.backImgBase,alt:"",style:{width:"100%",height:"100%",display:"block"}},null,8,Ie),ne(s("div",{class:"verify-refresh",onClick:a[0]||(a[0]=(...d)=>e.refresh&&e.refresh(...d))},a[3]||(a[3]=[s("i",{class:"iconfont icon-refresh"},null,-1)]),512),[[re,e.showRefresh]]),me(Se,{name:"tips"},{default:be(()=>[e.tipWords?(T(),O("span",{key:0,class:$(["verify-tips",e.passFlag?"suc-bg":"err-bg"])},G(e.tipWords),3)):U("",!0)]),_:1})],4)],4)):U("",!0),s("div",{class:"verify-bar-area",style:b({width:e.setSize.imgWidth,height:i.barSize.height,"line-height":i.barSize.height})},[s("span",{class:"verify-msg",textContent:G(e.text)},null,8,He),s("div",{class:"verify-left-bar",style:b({width:e.leftBarWidth!==void 0?e.leftBarWidth:i.barSize.height,height:i.barSize.height,"border-color":e.leftBarBorderColor,transaction:e.transitionWidth})},[s("span",{class:"verify-msg",textContent:G(e.finishText)},null,8,_e),s("div",{class:"verify-move-block",onTouchstart:a[1]||(a[1]=(...d)=>e.start&&e.start(...d)),onMousedown:a[2]||(a[2]=(...d)=>e.start&&e.start(...d)),style:b({width:i.barSize.height,height:i.barSize.height,"background-color":e.moveBlockBackgroundColor,left:e.moveBlockLeft,transition:e.transitionLeft})},[s("i",{class:$(["verify-icon iconfont",e.iconClass]),style:b({color:e.iconColor})},null,6),i.type==="2"?(T(),O("div",{key:0,class:"verify-sub-block",style:b({width:Math.floor(parseInt(e.setSize.imgWidth)*47/310)+"px",height:e.setSize.imgHeight,top:"-"+(parseInt(e.setSize.imgHeight)+i.vSpace)+"px","background-size":e.setSize.imgWidth+" "+e.setSize.imgHeight})},[s("img",{src:"data:image/png;base64,"+e.blockBackImgBase,alt:"",style:{width:"100%",height:"100%",display:"block","-webkit-user-drag":"none"}},null,8,Oe)],4)):U("",!0)],36)],4)],4)])}var Pe=ie(Te,[["render",Ne]]);const Ve={name:"VerifyPoints",props:{mode:{type:String,default:"fixed"},captchaType:{type:String},vSpace:{type:Number,default:5},imgSize:{type:Object,default(){return{width:"310px",height:"155px"}}},barSize:{type:Object,default(){return{width:"310px",height:"40px"}}}},setup(r,a){const{mode:i,captchaType:e,vSpace:g,imgSize:N,barSize:d}=ae(r),{proxy:v}=ue();let S=t(""),M=t(3),c=R([]),o=R([]),h=t(1),z=t(""),P=R([]),p=t(""),y=R({imgHeight:0,imgWidth:0,barHeight:0,barWidth:0}),k=R([]),V=t(""),x=t(void 0),W=t(void 0),K=t(!0),B=t(!0);const Q=()=>{c.splice(0,c.length),o.splice(0,o.length),h.value=1,_(),he(()=>{let{imgHeight:u,imgWidth:l,barHeight:w,barWidth:m}=fe(v);y.imgHeight=u,y.imgWidth=l,y.barHeight=w,y.barWidth=m,v.$parent.$emit("ready",v)})};de(()=>{Q(),v.$el.onselectstart=function(){return!1}});const L=t(null),A=u=>{if(o.push(E(L,u)),h.value==M.value){h.value=I(E(L,u));let l=j(o,y);o.length=0,o.push(...l),setTimeout(()=>{var w=S.value?ee(p.value+"---"+JSON.stringify(o),S.value):p.value+"---"+JSON.stringify(o);let m={captchaType:e.value,pointJson:S.value?ee(JSON.stringify(o),S.value):JSON.stringify(o),token:p.value};ge(m).then(J=>{J.repCode=="0000"?(x.value="#4cae4c",W.value="#5cb85c",V.value="\u9A8C\u8BC1\u6210\u529F",B.value=!1,i.value=="pop"&&setTimeout(()=>{v.$parent.clickShow=!1,H()},1500),v.$parent.$emit("success",{captchaVerification:w})):(v.$parent.$emit("error",v),x.value="#d9534f",W.value="#d9534f",V.value="\u9A8C\u8BC1\u5931\u8D25",setTimeout(()=>{H()},700))})},400)}h.value<M.value&&(h.value=I(E(L,u)))},E=function(u,l){var w=l.offsetX,m=l.offsetY;return{x:w,y:m}},I=function(u){return k.push(Object.assign({},u)),h.value+1},H=function(){k.splice(0,k.length),x.value="#000",W.value="#ddd",B.value=!0,c.splice(0,c.length),o.splice(0,o.length),h.value=1,_(),V.value="\u9A8C\u8BC1\u5931\u8D25",K.value=!0};function _(){let u={captchaType:e.value};ve(u).then(l=>{l.repCode=="0000"?(z.value=l.repData.originalImageBase64,p.value=l.repData.token,S.value=l.repData.secretKey,P.value=l.repData.wordList,V.value="\u8BF7\u4F9D\u6B21\u70B9\u51FB\u3010"+P.value.join(",")+"\u3011"):V.value=l.repMsg})}const j=function(u,l){var w=u.map(m=>{let J=Math.round(310*m.x/parseInt(l.imgWidth)),X=Math.round(155*m.y/parseInt(l.imgHeight));return{x:J,y:X}});return w};return{secretKey:S,checkNum:M,fontPos:c,checkPosArr:o,num:h,pointBackImgBase:z,poinTextList:P,backToken:p,setSize:y,tempPoints:k,text:V,barAreaColor:x,barAreaBorderColor:W,showRefresh:K,bindingClick:B,init:Q,canvas:L,canvasClick:A,getMousePos:E,createPoint:I,refresh:H,getPictrue:_,pointTransfrom:j}}},Ee={style:{position:"relative"}},Me={class:"verify-img-out"},je=["src"],Je={class:"verify-msg"};function Le(r,a,i,e,g,N){return T(),O("div",Ee,[s("div",Me,[s("div",{class:"verify-img-panel",style:b({width:e.setSize.imgWidth,height:e.setSize.imgHeight,"background-size":e.setSize.imgWidth+" "+e.setSize.imgHeight,"margin-bottom":i.vSpace+"px"})},[ne(s("div",{class:"verify-refresh",style:{"z-index":"3"},onClick:a[0]||(a[0]=(...d)=>e.refresh&&e.refresh(...d))},a[2]||(a[2]=[s("i",{class:"iconfont icon-refresh"},null,-1)]),512),[[re,e.showRefresh]]),s("img",{src:"data:image/png;base64,"+e.pointBackImgBase,ref:"canvas",alt:"",style:{width:"100%",height:"100%",display:"block"},onClick:a[1]||(a[1]=d=>e.bindingClick?e.canvasClick(d):void 0)},null,8,je),(T(!0),O(xe,null,we(e.tempPoints,(d,v)=>(T(),O("div",{key:v,class:"point-area",style:b({"background-color":"#1abd6c",color:"#fff","z-index":9999,width:"20px",height:"20px","text-align":"center","line-height":"20px","border-radius":"50%",position:"absolute",top:parseInt(d.y-10)+"px",left:parseInt(d.x-10)+"px"})},G(v+1),5))),128))],4)]),s("div",{class:"verify-bar-area",style:b({width:e.setSize.imgWidth,color:this.barAreaColor,"border-color":this.barAreaBorderColor,"line-height":this.barSize.height})},[s("span",Je,G(e.text),1)],4)])}var Ae=ie(Ve,[["render",Le]]);const De={name:"Vue2Verify",components:{VerifySlide:Pe,VerifyPoints:Ae},props:{captchaType:{type:String,required:!0},figure:{type:Number},arith:{type:Number},mode:{type:String,default:"pop"},vSpace:{type:Number},explain:{type:String},imgSize:{type:Object,default(){return{width:"310px",height:"155px"}}},blockSize:{type:Object},barSize:{type:Object}},setup(r){const{captchaType:a,figure:i,arith:e,mode:g,vSpace:N,explain:d,imgSize:v,blockSize:S,barSize:M}=ae(r),c=t(!1),o=t(void 0),h=t(void 0),z=t({}),P=ce(()=>g.value=="pop"?c.value:!0),p=()=>{console.log(z.value),z.value.refresh&&z.value.refresh()},y=()=>{c.value=!1,p()},k=()=>{g.value=="pop"&&(c.value=!0)};return ze(()=>{switch(a.value){case"blockPuzzle":o.value="2",h.value="VerifySlide";break;case"clickWord":o.value="",h.value="VerifyPoints";break}}),{clickShow:c,verifyType:o,componentType:h,instance:z,showBox:P,closeBox:y,show:k}}},Re={key:0,class:"verifybox-top"};function Ke(r,a,i,e,g,N){return ne((T(),O("div",{class:$(i.mode=="pop"?"mask":"")},[s("div",{class:$(i.mode=="pop"?"verifybox":""),style:b({"max-width":parseInt(i.imgSize.width)+30+"px"})},[i.mode=="pop"?(T(),O("div",Re,[a[2]||(a[2]=ke(" \u8BF7\u5B8C\u6210\u5B89\u5168\u9A8C\u8BC1 ")),s("span",{class:"verifybox-close",onClick:a[0]||(a[0]=(...d)=>e.closeBox&&e.closeBox(...d))},a[1]||(a[1]=[s("i",{class:"iconfont icon-close"},null,-1)]))])):U("",!0),s("div",{class:"verifybox-bottom",style:b({padding:i.mode=="pop"?"15px":"0"})},[e.componentType?(T(),Be(Ce(e.componentType),{key:0,captchaType:i.captchaType,type:e.verifyType,figure:i.figure,arith:i.arith,mode:i.mode,vSpace:i.vSpace,explain:i.explain,imgSize:i.imgSize,blockSize:i.blockSize,barSize:i.barSize,ref:"instance"},null,8,["captchaType","type","figure","arith","mode","vSpace","explain","imgSize","blockSize","barSize"])):U("",!0)],4)],6)],2)),[[re,e.showBox]])}var qe=ie(De,[["render",Ke]]);export{qe as V};
//# sourceMappingURL=Verify.fd31e088.js.map