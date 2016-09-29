$(function () {
  $('.order-item').on('click', function () {
    $(this).find('.order-detail').fadeToggle(600);
  });
  
  $('.weui_cell').on('click', '.btn_cancel', function() {
	  var orderid = $(this).parents('.weui_cell').data('id');
	  var $that = $(this);
	  $.weui.dialog({
          title: '取消订单',
          content: '确定取消该订单',
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
            		    		$parent.append('<span class="text-bold">已取消</span>');
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
});