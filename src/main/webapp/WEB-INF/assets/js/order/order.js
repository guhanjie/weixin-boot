$(function() {

	order = {};
	order["open_id"] = $('input[name="open_id"]').val();
	order["vehicle"] = 1;
	
	//车型
	$('.car-type').on('click', '.car-type-label', function(e) {
		$(this).siblings('.car-type-label').removeClass('text-primary');
		$(this).siblings('.car-type-label').find('i').removeClass().addClass('icon-circle-blank');
		$(this).addClass('text-primary');
		$(this).find('i').removeClass().addClass('icon-ok-sign');
		var carType = $(this).data('type');
		order["vehicle"] = carType;
		var arrowLeft = ['16%', '50%', '83%'];
		$('.car-type-tips .arrow').css('left', arrowLeft[carType-1]);
		$('.car-type-tips').find('p').hide();
		$('.car-type-tips').find('p[id=carType'+carType+']').show();
	});
	
	//服务时间
    var now = new Date();
    var minDate = new Date(now.getTime()+30*60*1000);	//起始时间为30分钟
    var maxDate = new Date(now.getTime()+7*24*60*60*1000);	//终止时间为7天后
    $.mobiscroll.themes.wap2 = {
        timeWheels  : 'HHii',
        rows        : 5,
        display     : 'modal',
        mode        : 'scroller',
        lang        : 'zh',
        stepMinute  : 30,
    };
    var options = {
        theme     : 'wap2',
        dateOrder : 'S',
        minDate   : minDate,
        maxDate   : maxDate,
        setText   : '预约服务',
        cancelText: '马上服务',
        onSelect  : function(val){
            order["start_time"] = new Date(val);
            var str  = val.replace(/-/g, "/");
            var date = new Date(str);
            var weektime = ($('.dw-sel').text()).substring(0,3);
            var hourtime = str.substring(11,16);
            if(weektime.trim()=="今天"){
                var week = weektime;
            }else if(weektime.trim()=="明天"){
                var week = weektime;
            }else if(weektime.trim()=="后天"){
                var week = weektime;
            }else{
                var week = (date.getMonth()+1) + 
                            '-' + 
                            date.getDate()+
                            ' '+ 
                            weektime;
            }
            var showtime = week + hourtime;
            $('#start_time').val(showtime);
        },
        onCancel : function(){
            order["start_time"] = new Date();
            $('#start_time').val('马上用车');
        }
    };//options
    $('#start_time').mobiscroll().datetime(options);
	
    //计算价格
    $('#submit').on('click', function() {
    	
    });
	
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
	
	//提交订单
	$('#submit').on('click', function(e) {
//		var pairs = $('form').serialize().split(/&/gi);
//		var data = {};
//		pairs.forEach(function(pair) {
//			pair = pair.split('=');
//			data[pair[0]] = decodeURIComponent(pair[1] || '');
//		});
		$('.weui_cell_warn').removeClass('weui_cell_warn');
		//收集数据
		order["from_address"] = $('input[name="from_address"]').val();
		order["from_detail"] = $('input[name="from_detail"]').val();
		order["from_floor"] = $('select[name="from_floor"]').val();
		order["to_address"] = $('input[name="to_address"]').val();
		order["to_detail"] = $('input[name="to_detail"]').val();
		order["to_floor"] = $('select[name="to_floor"]').val();
		order["start_time"] = !order["start_time"] || new Date();
		order["contactor"] = $('input[name="contactor"]').val();
		order["phone"] = $('input[name="phone"]').val();
		order["remark"] = $('textarea[name="remark"]').val();
		//验证订单
		if(!order["vehicle"] || (order["vehicle"]!=1 && order["vehicle"]!=2 && order["vehicle"]!=3)) {
			$.weui.topTips('未选择正确车型');
			return;
		}
		if(!order["from_address"] || !order["from_detail"] || !order["from_floor"]) {
			if(!order["from_address"]) {
				$.weui.topTips('请输入起始地');
				$('input[name="from_address"]').addClass('weui_cell_warn');
			}
			else if(!order["from_detail"]) {
				$.weui.topTips('请输入起始地的详细信息');
				$('input[name="from_detail"]').addClass('weui_cell_warn');
			}
			else if(!order["from_floor"]) {
				$.weui.topTips('请输入起始地的楼层信息');
				$('select[name="from_floor"]').addClass('weui_cell_warn');
			}
			return;
		}
		if(!order["to_address"] || !order["to_detail"] || !order["to_floor"]) {
			if(!order["to_address"]) {
				$.weui.topTips('请输入目的地');
				$('input[name="to_address"]').closest('.weui_cell').addClass('weui_cell_warn');
			}
			else if(!order["to_detail"]) {
				$.weui.topTips('请输入目的地的详细信息');
				$('input[name="to_detail"]').addClass('weui_cell_warn');
			}
			else if(!order["to_floor"]) {
				$.weui.topTips('请输入起始地的楼层信息');
				$('select[name="to_floor"]').addClass('weui_cell_warn');
			}
			return;
		}
		if (!order["contactor"]) {
			$.weui.topTips('请输入联系人姓名');
			$('input[name="contactor"]').closest('.weui_cell').addClass('weui_cell_warn');
			return;
		}
		if (!order["phone"] || !/^((86-)?\d{11})|(\d{3}-\d{8})|(\d{4}-\d{7,8})$/.test(order["phone"])) {
			$.weui.topTips('请输入正确的手机号码');
			$('input[name="phone"]').closest('.weui_cell').addClass('weui_cell_warn');
			return;
		}
		$.weui.loading('数据加载中...');
		console.log(order);
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