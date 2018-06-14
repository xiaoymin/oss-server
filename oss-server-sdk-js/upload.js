function Upload(opt){
	this.el = document.querySelector(opt.el) || opt.el
	this.width = this.el.offsetWidth //元素内容+内边距+边框
	this.height = this.el.offsetHeight
	
	try{
		if(!this.el){
			throw '容器传值错误！请传入选择器(#ID)或者直接传入dom！'
		}
		if((isNaN(this.width)&&(this.width>0)) || (isNaN(this.height)&&(this.height>0))){
			throw '请确保容器已正确设置宽高，上传组件将会以容器宽高作为大小！'
		}
		if(!opt.action){
			throw '表单提交地址，必填！'
		}
	}catch(err){
		console.error(err)
	}
	
	for(var attr in opt){
		if(!/el|width|height/.test(attr)){
			this[attr] = opt[attr]
		}
	}

	this.init()
}

Upload.prototype = {
	init: function(){
		this.el.style.position = 'relative'
		var styleText = 'position: absolute;top: 0;bottom: 0;left: 0;width: 100%;'
		
		this.myForm = document.createElement('form')
		this.myForm.style.cssText = styleText + 'overflow:hidden;opacity:0'
		this.setFormAttr(this.myForm)
		
		var inputFile = document.createElement('input')
		inputFile.style.cssText = styleText
		this.setFileAttr(inputFile)
		
		this.addOtherParams()
		
		var iframe = document.createElement('iframe')
		iframe.setAttribute('name','uploadIFrame')
		iframe.style.display = 'none'
		
		this.myForm.appendChild(inputFile)
		this.el.appendChild(this.myForm)
		this.el.appendChild(iframe)
	},
	setFormAttr:function(myForm){
		myForm.setAttribute('action',this.action)
		myForm.setAttribute('method','post')
		myForm.setAttribute('enctype','multipart/form-data')
		myForm.setAttribute('target','uploadIFrame')
	},
	setFileAttr:function(inputFile){
		inputFile.setAttribute('type','file')
		inputFile.setAttribute('name','file')
		
		if(this.accept){
			inputFile.setAttribute('accept',this.accept)
		}
		if(this.disabled){
			inputFile.setAttribute('disabled',this.disabled)
		}
		if(this.multiple){
			inputFile.setAttribute('multiple',this.multiple)
		}
		
		var _this = this
		inputFile.onchange = function(ev){			
			_this.change(ev)
		}
	},
	addOtherParams:function(){
		if(!this.data){
			return
		}
		for(var attr in this.data){
			var newInput = document.createElement('input')
			newInput.setAttribute('name', attr)
			newInput.setAttribute('type', 'hidden')
			newInput.value = this.data[attr]
			this.myForm.appendChild(newInput)
		}
	},
	change:function(ev){
		var files = ev.target.files
      	if (this.disabled || !files.length) {
        	return;
      	}
      	files = [].slice.call(files, 0)
      	
      	var isFormat = true
      	var formatList = this.accept?this.accept.split(','):[]
      	var oversize = false
      	
      	var _this = this
      	files.map(function(t){
      		if(!isNaN(parseInt(_this.maxSize)) && _this.maxSize>0 && t.size>_this.maxSize){
      			_this.oversize && _this.oversize(t.size,t)
      			oversize = true
      		}
      		if(formatList.length){
      			if(formatList.indexOf(t.type) === -1){
      				_this.formatError && _this.formatError(t.type,t)
      				isFormat = false
      			}
      		}
      	})
      	if(oversize || !isFormat){
      		return false;
      	}else{
      		this.beforeUpload && this.beforeUpload(files)
      		
      		var xhr = new XMLHttpRequest();
      		var action = this.action;
			xhr.open('post', action);
			xhr.onload = function(event){
				if(xhr.status < 200 || xhr.status >= 300){					
					_this.error && _this.error(_this.getBody(xhr))
				}else{
					_this.success && _this.success(_this.getBody(xhr))
				}
			}; 
			xhr.onerror = function(event){
				_this.error && _this.error(event)
			}
			var formData = new FormData(this.myForm); 
			xhr.send(formData);
      	}
	},
	getBody(xhr){
		const text = xhr.responseText || xhr.response;
	    if (!text) {
	        return text;
	    }
	    try {
	        return JSON.parse(text);
	    } catch (e) {
	        return text;
	    }
	}
}