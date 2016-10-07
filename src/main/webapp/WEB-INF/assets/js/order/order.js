$(function() {

	order = {
			"vehicle": 1,			//默认车型：小面
			"workers": 1,		//默认搬家师傅：1人
			"from": {},
			"to": {},					
			"waypoints": []	//途径点
	};
	order["open_id"] = $('input[name="open_id"]').val();

	// 定义起末点
//	var from = {};
//	var to = {};
//	var waypoints = [];
	var waypointsIdx = 1;
	var plugin = DistancePlugin.createNew(order, order.from, order.to, order.waypoints);
	plugin.initialInput("from_address", order.from);
	plugin.initialInput("to_address", order.to);
	//车型
	$('.car-type').on('click', '.car-type-label', function(e) {
		$(this).siblings('.car-type-label').removeClass('text-primary');
		$(this).siblings('.car-type-label').find('i').removeClass().addClass('icon-circle-blank');
		$(this).addClass('text-primary');
		$(this).find('i').removeClass().addClass('icon-ok-sign');
		var carType = $(this).data('type');
		order["vehicle"] = carType;
		$('input[name="vehicle"]').val(order["vehicle"]);
//		var price = [150, 200, 300];
//		$('.order_sumary .price  em').text(price[carType-1]);
		var arrowLeft = ['16%', '50%', '83%'];
		$('.car-type-tips .arrow').css('left', arrowLeft[carType-1]);
		$('.car-type-tips').find('p').hide();
		$('.car-type-tips').find('p[id=carType'+carType+']').show();
		if(carType == 3) {	//全顺/依维柯
			$('.weui_select.address-floor').find('option[value="0"]').html('无需搬运-0元');
			$('.weui_select.address-floor').find('option[value="1"]').html('电梯或1楼-50元');
			$('.weui_select.address-floor').find('option[value="2"]').html('2楼-加收20元');
			$('.weui_select.address-floor').find('option[value="3"]').html('3楼-加收40元');
			$('.weui_select.address-floor').find('option[value="4"]').html('4楼-加收60元');
			$('.weui_select.address-floor').find('option[value="5"]').html('5楼-加收80元');
			$('.weui_select.address-floor').find('option[value="6"]').html('6楼-加收100元');
			$('.weui_select.address-floor').find('option[value="7"]').html('7楼-加收120元');
			$('.weui_select.address-floor').find('option[value="8"]').html('8楼-加收140元');
		}
		else {						//小面或金杯
			$('.weui_select.address-floor').find('option[value="0"]').html('电梯-免费');
			$('.weui_select.address-floor').find('option[value="1"]').html('1楼-免费');
			$('.weui_select.address-floor').find('option[value="2"]').html('2楼-加收20元');
			$('.weui_select.address-floor').find('option[value="3"]').html('3楼-加收30元');
			$('.weui_select.address-floor').find('option[value="4"]').html('4楼-加收40元');
			$('.weui_select.address-floor').find('option[value="5"]').html('5楼-加收50元');
			$('.weui_select.address-floor').find('option[value="6"]').html('6楼-加收60元');
			$('.weui_select.address-floor').find('option[value="7"]').html('7楼-加收70元');
			$('.weui_select.address-floor').find('option[value="8"]').html('8楼-加收80元');
		}
	});
	
	//添加途经点
	$('.addway i').on('click', function() {
		var input_id = "way_point_"+waypointsIdx;
		$(this).parent().before(
		      '<div class="weui_cell waypoint">'+
		        '<div class="weui_cell_hd">'+
		          '<label class="weui_label text-primary">'+
		            '<i class="icon-map-marker"> </i>途经点'+
		          '</label>'+
		        '</div>'+
		        '<div class="weui_cell_bd weui_cell_primary">'+
		          '<input class="weui_input" id='+input_id+' data-idx='+waypointsIdx+' name="way_address" type="text" placeholder="请输入途经点" />'+
		          '<input class="weui_input address-detail" name="way_detail" type="text" placeholder="几号几室" />'+
		          '<div class="floor_select">'+
		            '<select class="weui_select address-floor" name="way_floor">'+
		              '<option value="0">电梯-免费</option>'+
		              '<option value="1">1楼-免费</option>'+
		              '<option value="2">2楼-加收20元</option>'+
		              '<option value="3">3楼-加收30元</option>'+
		              '<option value="4">4楼-加收40元</option>'+
		              '<option value="5">5楼-加收50元</option>'+
		              '<option value="6">6楼-加收60元</option>'+
		              '<option value="7">7楼-加收70元</option>'+
		              '<option value="8">8楼-加收80元</option>'+
		            '</select>'+
		          '</div>'+
		        '</div>'+
		        '<div class="weui_cell_ft remove">'+
		          '<i class="icon-remove"></i>'+
		        '</div>'+
		      '</div>');
		var point = {'index': waypointsIdx++};
		order.waypoints.push(point);
		plugin.initialInput(input_id, point);
	});
	//删除途经点
	$('.weui_cells_form').on('click', '.waypoint .remove', function() {
		var $cell = $(this).parents('.weui_cell.waypoint');
		var idx = $cell.find('.weui_input').data('idx');
		var del = undefined;
		order.waypoints.forEach(function(e, i) {
			if(e.index == idx) {
				del = i;
				return;
			}
		});
		if(del != undefined) {
			order.waypoints.splice(del, 1);
		}
		$cell.remove();
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
	$('body').on('click touchend tap blur focus change select keyup mouseup', function(e) {
		//console.log('==========event type: '+e.type);
    	order["amount"] = undefined;
    	var distance = order["distance"] || 0;
    	if(!$('input[name="from_address"]').val() || !$('input[name="to_address"]').val()) {
    		distance = 0;
    	}
    	$('.order_sumary .distance em').text(distance);
    	if(!distance) {
        	$('.order_sumary .price em').text('0');
    		return;
    	}
    	var fromFloor = $('select[name="from_floor"]').val() || 0;
    	var toFloor = $('select[name="to_floor"]').val() || 0;
    	var workers = $('input[name="workers"]').val() || 1;
    	var price = 0;
    	if(order["vehicle"] == 1) {	//小面车型
            price += (distance<10) ? 150.0 : (150.0+(distance-10)*5.0);  //起步价150（10公里内），超出后每公里5元
            price += (fromFloor<2) ? 0.0 : 10.0+(fromFloor-1)*10.0; //电梯和1楼搬运免费，每多1层加收10元
            price += (toFloor<2) ? 0.0 : 10.0+(toFloor-1)*10.0;
        	price += workers>1 ? (workers-1)*150 : 0;
    	}
    	else if(order["vehicle"] == 2) {	//金杯车型
            price += (distance<10) ? 200.0 : (200.0+(distance-10)*6.0);  //起步价200（10公里内），超出后每公里6元
            price += (fromFloor<2) ? 0.0 : 10.0+(fromFloor-1)*10.0; //电梯和1楼搬运免费，每多1层加收10元
            price += (toFloor<2) ? 0.0 : 10.0+(toFloor-1)*10.0;
        	price += workers>1 ? (workers-1)*150 : 0;
    	}
    	else if(order["vehicle"] == 3) {   //全顺/依维轲
            price += (distance<10) ? 300.0 : (300.0+(distance-10)*8.0);  //起步价300（10公里内），超出后每公里8元
            price += (fromFloor==0?0.0:50.0) + (toFloor==0?0.0:50.0); //电梯和1楼搬运按50元收取，每多1层加收20元
            price += (fromFloor<2) ? 0.0 : (fromFloor-1)*20.0;
            price += (toFloor<2) ? 0.0 : (toFloor-1)*20.0;
        	price += workers>1 ? (workers-1)*150 : 0;
    	}
    	order["amount"] = price.toFixed(0);
    	$('.order_sumary .price em').text(order["amount"]);
//		$('input[name="vehicle"]').val(order["vehicle"]);
//		$('input[name="from_lng"]').val(order["from_lng"]);
//		$('input[name="from_lat"]').val(order["from_lat"]);
//		$('input[name="to_lng"]').val(order["to_lng"]);
//		$('input[name="to_lat"]').val(order["to_lat"]);
//		$('input[name="distance"]').val(order["distance"]);
//		$('input[name="amount"]').val(order["amount"]);
		e.stopPropagation();
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
		//$('input[name="start_time"]').val(order["start_time"]);
		order["contactor"] = $('input[name="contactor"]').val();
		order["phone"] = $('input[name="phone"]').val();
		order["workers"] = $('input[name="workers"]').val() || 1;
		order["remark"] = $('textarea[name="remark"]').val();
		//验证订单
		if(!order["vehicle"] || (order["vehicle"]!=1 && order["vehicle"]!=2 && order["vehicle"]!=3)) {
			$.weui.topTips('未选择正确车型');
			return;
		}
		if(!order["from_address"]) {
			$.weui.topTips('请输入起始地');
			$('input[name="from_address"]').addClass('weui_cell_warn');
			return;
		}
//		if(!order["from_detail"]) {
//			$.weui.topTips('请输入起始地的详细信息');
//			$('input[name="from_detail"]').addClass('weui_cell_warn');
//			return;
//		}
		if(!order["from_floor"]) {
			$.weui.topTips('请输入起始地的楼层信息');
			$('select[name="from_floor"]').addClass('weui_cell_warn');
			return;
		}
		if(!order["to_address"]) {
			$.weui.topTips('请输入目的地');
			$('input[name="to_address"]').closest('.weui_cell').addClass('weui_cell_warn');
			return;
		}
//		if(!order["to_detail"]) {
//			$.weui.topTips('请输入目的地的详细信息');
//			$('input[name="to_detail"]').addClass('weui_cell_warn');
//			return;
//		}
		if(!order["to_floor"]) {
			$.weui.topTips('请输入起始地的楼层信息');
			$('select[name="to_floor"]').addClass('weui_cell_warn');
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
		if (!order["workers"] || !/^\d+$/.test(order["workers"])) {
			$.weui.topTips('请输入正确的搬家师傅人数');
			$('input[name="workers"]').closest('.weui_cell').addClass('weui_cell_warn');
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
		//console.log(order);
		//console.log($('form').serialize());
		//var formData = $('form').serialize();
//		var formData = '';
//		for(i in order) {
//			formData += '&'+i+'='+order[i];
//		}
//		formData = formData.substring(1);
		//console.log(formData);
		$.ajax({
		    type: 'POST',
		    url: 'order',
		    data: order,
		    success: function(data){
	    		$.weui.hideLoading();
		    	if(data.success) {
					$.weui.loading('订单提交成功');
					setTimeout($.weui.hideLoading, 1000);
					var vehicleName = ['小面车型', '金杯车型', '全顺/依维柯'];
					$('#res_vehicle').text(vehicleName[order["vehicle"]-1]);
					$('#res_distance').text(order["distance"]+' 公里');
					$('#res_amount').text(order["amount"]+' 元');
					$('#res_from_address').text(order["from_address"]);
					$('#res_to_address').text(order["to_address"]);
					$('#res_contactor').text(order["contactor"]);
					$('#res_workers').text(order["workers"]);
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