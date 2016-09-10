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
		$('input[name="vehicle"]').val(order["vehicle"]);
		order["vehicle"] = carType;
		var price = [150, 200, 300];
		$('.order_sumary .price  em').text(price[carType-1]);
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
            order["start_time"] = new Date(val).getTime();
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
            order["start_time"] = new Date().getTime();
            $('#start_time').val('马上服务');
        }
    };//options
    $('#start_time').mobiscroll().datetime(options);

    //计算价格
	$('body').on('click', function() {
    	order["amount"] = undefined;
    	var distance = order["distance"] || 0;
    	distance = distance.toFixed(1);
    	$('.order_sumary .distance em').text(distance);
    	var fromFloor = $('select[name="from_floor"]').val() || 0;
    	var toFloor = $('select[name="to_floor"]').val() || 0;
    	if(!distance) {
    		return;
    	}
    	var price = 0;
    	if(order["vehicle"] == 1) {	//小面车型
    		price = 150.0; //起步价150（10公里内）
            price += (distance<10) ? 0.0 : (distance-10)*5.0;  //超出后每公里5元
            price += (fromFloor<2) ? 0.0 : (fromFloor-1)*10.0; //电梯和1楼搬运免费，每多1层加收10元
            price += (toFloor<2) ? 0.0 : (toFloor-1)*10.0;
    	}
    	else if(order["vehicle"] == 2) {	//金杯车型
		    price = 200.0; //起步价200（10公里内）
            price += (distance<10) ? 0.0 : (distance-10)*6.0;  //超出后每公里6元
            price += (fromFloor<2) ? 0.0 : (fromFloor-1)*10.0; //电梯和1楼搬运免费，每多1层加收10元
            price += (toFloor<2) ? 0.0 : (toFloor-1)*10.0;
    	}
    	else if(order["vehicle"] == 3) {   //全顺/依维轲
		    price = 300.0; //起步价300（10公里内）
            price += (distance<10) ? 0.0 : (distance-10)*8.0;  //超出后每公里8元
            price += 50.0; //电梯和1楼搬运按50元收取，每多1层加收20元
            price += (fromFloor<2) ? 0.0 : (fromFloor-1)*20.0;
            price += (toFloor<2) ? 0.0 : (toFloor-1)*20.0;
    	}
    	order["amount"] = price.toFixed(1);
    	$('.order_sumary .price em').text(price);
		$('input[name="vehicle"]').val(order["vehicle"]);
		$('input[name="from_lng"]').val(order["from_lng"]);
		$('input[name="from_lat"]').val(order["from_lat"]);
		$('input[name="to_lng"]').val(order["to_lng"]);
		$('input[name="to_lat"]').val(order["to_lat"]);
		$('input[name="distance"]').val(order["distance"]);
		$('input[name="amount"]').val(order["amount"]);
    	return;
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
		order["start_time"] = order["start_time"] || new Date().getTime();
		$('input[name="start_time"]').val(order["start_time"]);
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
		if (!order["phone"] || !/(^(86-)?\d{11}$)|(^\d{3}-\d{8}$)|(^\d{4}-\d{7,8}$)/.test(order["phone"])) {
			$.weui.topTips('请输入正确的手机号码');
			$('input[name="phone"]').closest('.weui_cell').addClass('weui_cell_warn');
			return;
		}
		if(!order["distance"]) {
			$.weui.topTips('距离计算失败，请输入正确地址');
			return;
		}
		if(!order["amount"]) {
			$.weui.topTips('请输入确切信息以确定价格');
			return;
		}
		$.weui.loading('订单提交中...');
		console.log(order);
		$.ajax({
		    type: 'POST',
		    url: 'order',
		    data: $('form').serialize(),
		    success: function(data){
	    		$.weui.hideLoading();
		    	if(data.success) {
					$.weui.loading('订单提交成功');
					setTimeout($.weui.hideLoading, 1000);
					var vehicleName = ['小面', '金杯', '全顺/依维柯'];
					$('#res_vehicle').text(vehicleName[order["vehicle"]]);
					$('#res_from_address').text(order["from_address"]);
					$('#res_to_address').text(order["to_address"]);
					$('#res_contactor').text(order["contactor"]);
					$('#res_start_time').text(new Date(order["start_time"]).toLocaleString());
					$('form').remove();
					$('.weui_msg').show();
		    	}
		    	else {
					$.weui.topTips('订单信息有误，提交失败');
		    	}
		      },
		      error: function(xhr, type){
					$.weui.topTips('订单信息有误，提交失败');
		      }
		  })
	});
	
})