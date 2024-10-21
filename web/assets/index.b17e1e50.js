var E={exports:{}},k={exports:{}},h=1e3,v=h*60,y=v*60,w=y*24,Z=w*365.25,$=function(n,e){e=e||{};var o=typeof n;if(o==="string"&&n.length>0)return R(n);if(o==="number"&&isNaN(n)===!1)return e.long?D(n):S(n);throw new Error("val is not a non-empty string or a valid number. val="+JSON.stringify(n))};function R(n){if(n=String(n),!(n.length>100)){var e=/^((?:\d+)?\.?\d+) *(milliseconds?|msecs?|ms|seconds?|secs?|s|minutes?|mins?|m|hours?|hrs?|h|days?|d|years?|yrs?|y)?$/i.exec(n);if(!!e){var o=parseFloat(e[1]),s=(e[2]||"ms").toLowerCase();switch(s){case"years":case"year":case"yrs":case"yr":case"y":return o*Z;case"days":case"day":case"d":return o*w;case"hours":case"hour":case"hrs":case"hr":case"h":return o*y;case"minutes":case"minute":case"mins":case"min":case"m":return o*v;case"seconds":case"second":case"secs":case"sec":case"s":return o*h;case"milliseconds":case"millisecond":case"msecs":case"msec":case"ms":return o;default:return}}}}function S(n){return n>=w?Math.round(n/w)+"d":n>=y?Math.round(n/y)+"h":n>=v?Math.round(n/v)+"m":n>=h?Math.round(n/h)+"s":n+"ms"}function D(n){return C(n,w,"day")||C(n,y,"hour")||C(n,v,"minute")||C(n,h,"second")||n+" ms"}function C(n,e,o){if(!(n<e))return n<e*1.5?Math.floor(n/e)+" "+o:Math.ceil(n/e)+" "+o+"s"}(function(n,e){e=n.exports=c.debug=c.default=c,e.coerce=i,e.disable=d,e.enable=l,e.enabled=m,e.humanize=$,e.names=[],e.skips=[],e.formatters={};var o;function s(r){var t=0,a;for(a in r)t=(t<<5)-t+r.charCodeAt(a),t|=0;return e.colors[Math.abs(t)%e.colors.length]}function c(r){function t(){if(!!t.enabled){var a=t,u=+new Date,g=u-(o||u);a.diff=g,a.prev=o,a.curr=u,o=u;for(var f=new Array(arguments.length),p=0;p<f.length;p++)f[p]=arguments[p];f[0]=e.coerce(f[0]),typeof f[0]!="string"&&f.unshift("%O");var b=0;f[0]=f[0].replace(/%([a-zA-Z%])/g,function(A,M){if(A==="%%")return A;b++;var z=e.formatters[M];if(typeof z=="function"){var T=f[b];A=z.call(a,T),f.splice(b,1),b--}return A}),e.formatArgs.call(a,f);var N=t.log||e.log||console.log.bind(console);N.apply(a,f)}}return t.namespace=r,t.enabled=e.enabled(r),t.useColors=e.useColors(),t.color=s(r),typeof e.init=="function"&&e.init(t),t}function l(r){e.save(r),e.names=[],e.skips=[];for(var t=(typeof r=="string"?r:"").split(/[\s,]+/),a=t.length,u=0;u<a;u++)!t[u]||(r=t[u].replace(/\*/g,".*?"),r[0]==="-"?e.skips.push(new RegExp("^"+r.substr(1)+"$")):e.names.push(new RegExp("^"+r+"$")))}function d(){e.enable("")}function m(r){var t,a;for(t=0,a=e.skips.length;t<a;t++)if(e.skips[t].test(r))return!1;for(t=0,a=e.names.length;t<a;t++)if(e.names[t].test(r))return!0;return!1}function i(r){return r instanceof Error?r.stack||r.message:r}})(k,k.exports);(function(n,e){e=n.exports=k.exports,e.log=c,e.formatArgs=s,e.save=l,e.load=d,e.useColors=o,e.storage=typeof chrome!="undefined"&&typeof chrome.storage!="undefined"?chrome.storage.local:m(),e.colors=["lightseagreen","forestgreen","goldenrod","dodgerblue","darkorchid","crimson"];function o(){return typeof window!="undefined"&&window.process&&window.process.type==="renderer"?!0:typeof document!="undefined"&&document.documentElement&&document.documentElement.style&&document.documentElement.style.WebkitAppearance||typeof window!="undefined"&&window.console&&(window.console.firebug||window.console.exception&&window.console.table)||typeof navigator!="undefined"&&navigator.userAgent&&navigator.userAgent.toLowerCase().match(/firefox\/(\d+)/)&&parseInt(RegExp.$1,10)>=31||typeof navigator!="undefined"&&navigator.userAgent&&navigator.userAgent.toLowerCase().match(/applewebkit\/(\d+)/)}e.formatters.j=function(i){try{return JSON.stringify(i)}catch(r){return"[UnexpectedJSONParseError]: "+r.message}};function s(i){var r=this.useColors;if(i[0]=(r?"%c":"")+this.namespace+(r?" %c":" ")+i[0]+(r?"%c ":" ")+"+"+e.humanize(this.diff),!!r){var t="color: "+this.color;i.splice(1,0,t,"color: inherit");var a=0,u=0;i[0].replace(/%[a-zA-Z%]/g,function(g){g!=="%%"&&(a++,g==="%c"&&(u=a))}),i.splice(u,0,t)}}function c(){return typeof console=="object"&&console.log&&Function.prototype.apply.call(console.log,console,arguments)}function l(i){try{i==null?e.storage.removeItem("debug"):e.storage.debug=i}catch(r){}}function d(){var i;try{i=e.storage.debug}catch(r){}return!i&&typeof process!="undefined"&&"env"in process&&(i={}.DEBUG),i}e.enable(d());function m(){try{return window.localStorage}catch(i){}}})(E,E.exports);var j=E.exports("jsonp"),O=F,U=0;function _(){}function F(n,e,o){typeof e=="function"&&(o=e,e={}),e||(e={});var s=e.prefix||"__jp",c=e.name||s+U++,l=e.param||"callback",d=e.timeout!=null?e.timeout:6e4,m=encodeURIComponent,i=document.getElementsByTagName("script")[0]||document.head,r,t;d&&(t=setTimeout(function(){a(),o&&o(new Error("Timeout"))},d));function a(){r.parentNode&&r.parentNode.removeChild(r),window[c]=_,t&&clearTimeout(t)}function u(){window[c]&&a()}return window[c]=function(g){j("jsonp got",g),a(),o&&o(null,g)},n+=(~n.indexOf("?")?"&":"?")+l+"="+m(c),n=n.replace("?&","?"),j('jsonp req "%s"',n),r=document.createElement("script"),r.src=n,i.parentNode.insertBefore(r,i),u}function L(n){const e=n.split(","),o=e[0].match(/:(.*?);/)[1],s=atob(e[1]);let c=s.length;const l=new Uint8Array(c);for(;c--;)l[c]=s.charCodeAt(c);return new Blob([l],{type:o})}function B(n,e){return n.lastModifiedDate=new Date,n.name=e,n}function I(n){return/^(http|https|ftp)\:\/\/([a-zA-Z0-9\.\-]+(\:[a-zA-Z0-9\.&%\$\-]+)*@)?((25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9])\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])|([a-zA-Z0-9\-]+\.)*[a-zA-Z0-9\-]+\.[a-zA-Z]{2,4})(\:[0-9]+)?(\/[^/][a-zA-Z0-9\.\,\?\'\\/\+&%\$#\=~_\-@]*)*$/.test(n)}function J(n){return new Promise(function(e,o){O("https://www.baidu.com/sugrec?pre=1&p=3&ie=utf-8&json=1&prod=pc&from=pc_web&wd="+n,null,(s,c)=>{if(s)o(s);else{let l=[];c.g&&c.g.length>0&&(l=c.g.map(function(d){return{value:d.q}})),e(l)}})})}export{B as a,L as b,I as c,J as s};
//# sourceMappingURL=index.b17e1e50.js.map