$(function() {

	//车型
	$('.car-type').on('click', '.car-type-radio', function(e) {
		var checked = $(this).closest('.car-type-label');
		$(checked).siblings('.car-type-label').removeClass('text-primary');
		$(checked).siblings('.car-type-label').find('i').removeClass().addClass('icon-circle-blank');
		$(checked).addClass('text-primary');
		$(this).find('i').removeClass().addClass('icon-ok-sign');
		var carType = $(checked).data('type');
		$('input[name="vehicle"]').val(carType);
		$('.car-type-tips').find('p').hide();
		$('.car-type-tips').find('p[id=carType'+carType+']').show();
	});
	
	//服务时间
    var now = new Date();
    var minDate = new Date(now.getTime()+30*60*1000);	//起始时间为30分钟
    var maxDate = new Date(now.getTime()+7*24*60*60*1000);	//终止时间为7天后
    var options = {
        theme     : 'wap2',
        dateOrder : 'S',
        minDate   : minDate,
        maxDate   : maxDate,
        setText   : '预约服务',
        cancelText: '马上服务',
        onSelect  : function(val){
            var str  = val.replace(/-/g, "/");
            var date = new Date(str);
            var weektime = ($('.dw-sel').text()).substring(0,3);
            var hourtime = str.substring(11,16);
            if(weektime.trim()=="今天"){
                var week = weektime;
            }else if(weektime.trim()=="明天"){
                var week = weektime;
            }else{
                var week = (date.getMonth()+1) + 
                            '-' + 
                            date.getDate()+
                            ' '+ 
                            weektime;
            }
            var showtime = week + hourtime;
        },
        onCancel : function(){
            init_time();
            $('#start_time').val('马上用车').attr('serviceTime','马上用车');
        }
    };//options
    $('#start_time').mobiscroll().datetime(options);
    function init_time(){
    }
	
    //计算价格
	
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
	
	$('#button').on('click', function(e) {
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
	});

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
	});
})