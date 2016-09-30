$(function () {
  $('.order-item').on('click', '.weui_cell_bd', function () {
    $(this).find('.order-detail').fadeToggle(600);
    return false;
  });
  
  $('.order-list').on('click', '.btn_cancel', function() {
	  var orderid = $(this).parents('.order-item').data('id');
	  var $that = $(this);
	  $.weui.dialog({
          title: '取消订单',
          content: '是否确定取消该订单？',
          buttons: [{
              label: '放弃',
              type: 'default',
              onClick: function (){
                  console.log('知道了......');
              }
          }, {
              label: '确定',
              type: 'primary',
              onClick: function (){
            	  $.ajax({
            		    type: 'POST',
            		    url: 'cancel',
            		    data: {'orderid': orderid},
            		    dataType:  'json',
            		    success: function(data){
            		    	if(data.success) {
            		    		var $parent = $that.parents('.weui_cell_ft');
            		    		$that.remove();
            		    		$parent.append('<span class="btn_status text-bold">已取消</span>');
            		    	}
            		    	else {
            					$.weui.topTips(data.content);
            		    	}
            		    },
            		  	error: function(xhr, type){
            				$.weui.topTips('订单取消失败');
            		    }
            		  });
              }
          }]
      });
  });
  
  $('.order-list').on('click', '.btn_success', function() {
		var $item = $(this).parents('.order-item');
		$('.order-list').hide();
		$('.weui_msg .order-item').data('id', $item.data('id'));
		$('#res_vehicle').text($item.find('#vehicle').text());
		$('#res_distance').text($item.find('#distance').text());
		$('#res_amount').text($item.find('#amount').text());
		$('#res_from_address').text($item.find('#from-address').text());
		$('#res_to_address').text($item.find('#to-address').text());
//		var now = new Date(startTime);
//		var timestr = now.getFullYear()+'-'+(now.getMonth()+1)+'-'+now.getDate()+' '+now.getHours()+':'+now.getMinutes()+':'+now.getSeconds();
		$('#res_start_time').text($item.find('#start_time').text());
		$('.weui_msg').fadeIn();
  });
  
  $('.weui_msg').on('click', '.btn_back', function() {
	  $('.weui_msg').hide();
	  $('.order-list').fadeIn();
  });
  
  $('.weui_msg').on('click', '.order-pay', function() {
	  var orderid = $(this).parents('.weui_msg').find('.order-item').data('id');
	  $.ajax({
		    type: 'GET',
		    url: 'pay',
		    data: {'orderid': orderid},
		    dataType:  'json',
		    success: function(data){
		    	if(data.success) {
		    		onBridgeReady(data.content, 
		    		function() {
		         	   $('body').html('<div class="weui_msg">'
  			   				 +'   <div class="weui_icon_area">'
  			   				 +'  		<i class="weui_icon_success weui_icon_msg"></i>'
  			   				 +'	</div>'
  			   				 +'	<div class="weui_text_area">'
  			   				 +'		<h2 class="weui_msg_title">支付成功</h2>'
  			   				 +'		<p class="weui_msg_desc">尊涵搬家，竭诚为您服务！</p>'
  			   				 +'	</div>'
  			   				 +'</div>')
		    			
		    		}, function() {
		         	   $('body').html('<div class="weui_msg">'
				   				 +'   <div class="weui_icon_area">'
				   				 +'  		<i class="weui_icon_warn weui_icon_msg"></i>'
				   				 +'	</div>'
				   				 +'	<div class="weui_text_area">'
				   				 +'		<h2 class="weui_msg_title">支付失败</h2>'
				   				 +'		<p class="weui_msg_desc">给您带来不便，敬请谅解。<br/>请返回重新尝试，或直接面付。</p>'
				   				 +'	</div>'
				   				 +'	<div class="weui_opr_area">'
				   				 +'		<p class="weui_btn_area">'
				   				 +'			<a href="search" class="weui_btn weui_btn_primary">重新支付</a>'
				   				 +'		</p>'
				   				 +'	</div>'
				   				 +'</div>')
		    		});
		    	}
		    	else {
					$.weui.topTips(data.content);
		    	}
		    },
		  	error: function(xhr, type){
				$.weui.topTips('订单支付失败');
		    }
		  });
  });
});