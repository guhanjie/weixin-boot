$(function() {
	
	$('textarea[name="remark"]').on('input', function() {
		var max = 200;
		var text = $(this).val();
		var len = text.length;
		$('#remark_count').text(len);
		if (len > max) {
			$(this).closest('.weui_cell').addClass('weui_cell_warn');
		} else {
			$(this).closest('.weui_cell').removeClass('weui_cell_warn');
		}
	});
	
	$('#button').on(
			'click',
			function(e) {
				var pairs = $('form').serialize().split(/&/gi);
				var data = {};
				pairs.forEach(function(pair) {
					pair = pair.split('=');
					data[pair[0]] = decodeURIComponent(pair[1] || '');
				});
				if (!data.contactor) {
					$.weui.topTips('请输入联系人姓名');
					return;
				}
				if (!data.phone
						|| !/^((86-)?\d{11})|(\d{3}-\d{8})|(\d{4}-\d{7,8})$/
								.test(data.phone)) {
					$.weui.topTips('请输入正确的手机号码');
					return;
				}
				$.weui.loading('数据加载中...');
				setTimeout($.weui.hideLoading, 3000);
			})

	$('#ddd').on('click', '#btnDialog', function(e) {
		$.weui.dialog({
			title : '自定义标题',
			content : '自定义内容',
			buttons : [ {
				label : '知道了',
				type : 'default',
				onClick : function() {
					console.log('知道了......');
				}
			}, {
				label : '好的',
				type : 'primary',
				onClick : function() {
					console.log('好的......');
				}
			} ]
		});
	})
})