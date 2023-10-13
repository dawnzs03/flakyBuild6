import*as e from"../../recorder/components/components.js";import*as t from"../../../ui/lit-html/lit-html.js";const r=new CSSStyleSheet;r.replaceSync("*{box-sizing:border-box;padding:0;margin:0;font-size:inherit}:host{display:block}.row{display:flex;flex-direction:row;color:var(--color-syntax-1);font-family:var(--monospace-font-family);font-size:var(--monospace-font-size);align-items:center;line-height:18px;margin-top:3px}.separator{margin-right:0.5em;color:var(--color-text-primary)}.padded{margin-left:2em}.padded.double{margin-left:4em}\n/*# sourceURL=JSONEditor.css */\n");var o=self&&self.__decorate||function(e,t,r,o){var a,s=arguments.length,n=s<3?t:null===o?o=Object.getOwnPropertyDescriptor(t,r):o;if("object"==typeof Reflect&&"function"==typeof Reflect.decorate)n=Reflect.decorate(e,t,r,o);else for(var d=e.length-1;d>=0;d--)(a=e[d])&&(n=(s<3?a(n):s>3?a(t,r,n):a(t,r))||n);return s>3&&n&&Object.defineProperty(t,r,n),n};const{html:a,Decorators:s,LitElement:n,nothing:d}=t,{customElement:i,property:p,state:l}=s;let c=class extends n{static styles=[r];command="";getCommand(){return this.command}getParameters(){return this.parameters}#e=async t=>{t.target instanceof e.RecorderInput.RecorderInput&&(this.command=t.target.value)};#t(){return a`<div class="row attribute padded" data-attribute="type"> <div>command<span class="separator">:</span></div> <devtools-recorder-input .disabled="${!1}" .options="${this.protocolMethods}" .value="${this.command}" .placeholder="${"Enter your command..."}" @blur="${this.#e}"></devtools-recorder-input> </div>`}#r(){return a`<div class="row attribute padded" data-attribute="type"> <div>parameters<span class="separator">:</span></div> </div>`}#o(e){return a` <ul> ${Object.keys(e).map((t=>{const r=JSON.stringify(e[t]);return a` <div class="row attribute padded double" data-attribute="type"> <div>${t}<span class="separator">:</span></div> <devtools-recorder-input .disabled="${!1}" .value="${r}" .placeholder="${"Enter your parameter..."}"></devtools-recorder-input> </div> `}))} </ul>`}render(){return a` <div class="wrapper"> ${this.#t()} ${this.parameters&&0!==Object.keys(this.parameters).length?a` ${this.#r()} ${this.#o(this.parameters)} `:d} </div>`}};o([p()],c.prototype,"jsonPromptEditors",void 0),o([p()],c.prototype,"parameters",void 0),o([p()],c.prototype,"protocolMethods",void 0),o([l()],c.prototype,"command",void 0),c=o([i("devtools-json-editor")],c);var m=Object.freeze({__proto__:null,get JSONEditor(){return c}});export{m as JSONEditor};
