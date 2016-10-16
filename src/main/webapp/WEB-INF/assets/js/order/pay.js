function callWeixinPay(param, success, fail) {
	function onBridgeReady(){
		   WeixinJSBridge.invoke(
		       'getBrandWCPayRequest', param,
//		       {
//		           "appId" : "wx425b5e47d51971d5",     //公众号名称，由商户传入     
//		           "timeStamp" : Number.parseInt(now.getTime()/1000),         //时间戳，自1970年以来的秒数     
//		           "nonceStr" : "e61463f8efa94090b1f366cccfbbb444", //随机串     
//		           "package" : "prepay_id=u802345jgfjsdfgsdg888",     
//		           "signType" : "MD5",         //微信签名方式：     
//		           "paySign" : "70EA570631E4BB79628FBCA90534C63FF7FADD89" //微信签名 
//		       },
		       function(res){     
		    	   // 使用以上方式判断前端返回,微信团队郑重提示：res.err_msg将在用户支付成功后返回    ok，但并不保证它绝对可靠。 
		           if(res.err_msg == "get_brand_wcpay_request:ok" ) {     // 支付成功
		        	   if(success && typeof success == "function") {
		        		   success.apply(this);
		        	   }
		           }
		           else if(res.err_msg == "get_brand_wcpay_request:cancel" ) {     // 支付过程中用户取消
			    		$.weui.toast('支付已取消');
		           }
		           else if(res.err_msg == "get_brand_wcpay_request:fail" ) {     // 支付失败
		        	   if(fail && typeof fail == "function") {
		        		   fail.apply(this);
		        	   }
				   }
		       }
		   ); 
		}
		if (typeof WeixinJSBridge == "undefined"){
		   if( document.addEventListener ){
		       document.addEventListener('WeixinJSBridgeReady', onBridgeReady, false);
		   }else if (document.attachEvent){
		       document.attachEvent('WeixinJSBridgeReady', onBridgeReady); 
		       document.attachEvent('onWeixinJSBridgeReady', onBridgeReady);
		   }
		}else{
		   onBridgeReady();
		}
}