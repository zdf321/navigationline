import{a as o,a6 as c,a7 as t,a8 as a,a9 as r}from"./index.dcc91eec.js";const n=o(),l=c({id:"store-files",state:()=>({viewType:t.get(a)||"menu",folderBreadcrumb:t.get(r)||[],events:{isClick:!1,isDrag:!1},startItem:{},enterId:"",pointOffset:{x:-999,y:-999},enterType:"file",isClick:!0}),getters:{getFileListViewType(){return this.viewType},getFolderBreadcrumb(){return this.folderBreadcrumb},getStartItem(){return this.startItem},getEnterId(){return this.enterId},getPointOffset(){return this.pointOffset},getEnterType(){return this.enterType},getEvents(){return this.events},getIsClick(){return this.isClick}},actions:{setFilesListViewType(e){this.viewType=e,t.set(a,e)},setFolderBreadcrumb(e){if(e.pid||(this.folderBreadcrumb=[e]),this.folderBreadcrumb.some(s=>s.name===e.name)){const s=this.folderBreadcrumb.findIndex(d=>d.name===e.name);this.folderBreadcrumb.splice(s+1,1)}else this.folderBreadcrumb.push(e);console.log("this.folderBreadcrumb:",this.folderBreadcrumb),t.set(r,this.folderBreadcrumb)},resetFolderBreadcrumb(){let e=n.getActiveMenu.spaceId,i=e?"root:"+e:"";this.folderBreadcrumb=[{name:n.getActiveMenu.text,id:i}],t.set(r,this.folderBreadcrumb)},setStartItem(e){this.startItem=e},setEnterId(e){this.enterId=e},setPointOffset(e){this.pointOffset=e},setEnterType(e){this.enterType=e},setEvents(e){this.events=e},setIsClick(e){this.isClick=e}}});export{l as u};
//# sourceMappingURL=files.d698779e.js.map
