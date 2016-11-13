$(function() {

	order = {
			"vehicle": 1,			//默认车型：小面
			"workers": 1,		//默认搬家师傅：1人
			"from": {'target': 'from_address'},	//起点
			"to": {'target': 'to_address'},			//终点		
			"waypoints": []	//途径点
	};
	var waypointsIdx = 1;
	
	//百度地图插件
	var plugin = DistancePlugin.createNew(order, order["from"], order["to"], order["waypoints"]);
	plugin.initialInput(order["from"]);
	plugin.initialInput(order["to"]);
	
	//车型
	$('.car-type').on('click tap', '.car-type-label', function(e) {
		$(this).siblings('.car-type-label').removeClass('text-primary');
		$(this).siblings('.car-type-label').find('i').removeClass().addClass('icon-circle-blank');
		$(this).addClass('text-primary');
		$(this).find('i').removeClass().addClass('icon-ok-sign');
		var carType = $(this).data('type');
		order["vehicle"] = carType;
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
		var point = {
				'index': waypointsIdx++,
				'target': input_id
		};
		order["waypoints"].push(point);
		plugin.initialInput(point);
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
			order["waypoints"].splice(del, 1);
		}
		$cell.remove();
		plugin.confirmPlace();
	});
	
	//服务时间
    var now = new Date();
    var minDate = new Date(now.getTime()+1*60*60*1000);	//起始时间为60分钟
    var maxDate = new Date(now.getTime()+7*24*60*60*1000);	//终止时间为7天后
    $.mobiscroll.themes.wap2 = {
        timeWheels  : 'HHii',
        rows        : 5,
        display     : 'modal',
        mode        : 'scroller',
        lang        : 'zh',
        stepMinute  : 30,
    };
    var setTime = function(val){
        var str  = val.replace(/-/g, "/");
        var date = new Date(str);
        order["startTime"] = date.getTime();
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
    };
    var options = {
        theme     : 'wap2',
        dateOrder : 'S',
        minDate   : minDate,
        maxDate   : maxDate,
        setText   : '预约服务',
        cancelText: '马上服务',
        onSelect  : setTime,
        onCancel : setTime/*function(){
            order["startTime"] = new Date().getTime();
            $('#start_time').val('马上服务');
        }*/
    };//options
    $('#start_time').mobiscroll().datetime(options);

    //计算价格
    var calculate = function(e) {
		//console.log('==========event type: '+e.type);
		order["from"]["address"] = $('input[name="from_address"]').val();
		order["from"]["detail"] = $('input[name="from_detail"]').val();
		order["from"]["floor"] = $('select[name="from_floor"]').val() || 0;
		order["to"]["address"] = $('input[name="to_address"]').val();
		order["to"]["detail"] = $('input[name="to_detail"]').val();
		order["to"]["floor"] = $('select[name="to_floor"]').val() || 0;
		order["workers"] = $('input[name="workers"]').val() || 1;
		order["waypoints"].forEach(function(e, i) {
			var $wayInput = $('#'+e.target);
			e["address"] = $wayInput.val();
			e["detail"] = $wayInput.next('input[name="way_detail"]').val();
			e["floor"] = $wayInput.parents('.weui_cell.waypoint').find('select[name="way_floor"]').val();
		});
		if(e && e.data && e.data.forceConfirmPlace) {
			plugin.confirmPlace();
		}
		//console.log('==========calculating order amount...');
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
    	var price = 0;
    	if(order["vehicle"] == 1) {	//小面车型
            price += (distance<10) ? 150.0 : (150.0+(distance-10)*5.0);  //起步价150（10公里内），超出后每公里5元
            price += (order["from"]["floor"]<2) ? 0.0 : order["from"]["floor"]*10.0; //电梯和1楼搬运免费，2楼20元，每多1层加收10元
            price += (order["to"]["floor"]<2) ? 0.0 : order["to"]["floor"]*10.0;
            price += (order["workers"]<2) ? 0.0 : (order["workers"]-1)*150;  //每增加一名搬家师傅，加收150元
            if(order["waypoints"].length > 0) {					//途经点
            	order["waypoints"].forEach(function(e, i) {
                    price += 50.0; //每增加一个点位装卸货，增加50元
                    price += (e.floor<2) ? 0.0 : e.floor*10.0;
            	})
            }
    	}
    	else if(order["vehicle"] == 2) {	//金杯车型
            price += (distance<10) ? 200.0 : (200.0+(distance-10)*6.0);  //起步价200（10公里内），超出后每公里6元
            price += (order["from"]["floor"]<2) ? 0.0 : order["from"]["floor"]*10.0; //电梯和1楼搬运免费，2楼20元，每多1层加收10元
            price += (order["to"]["floor"]<2) ? 0.0 : order["to"]["floor"]*10.0;
            price += order["workers"]>1 ? (order["workers"]-1)*150 : 0;     //每增加一名搬家师傅，加收150元
            if(order["waypoints"].length > 0) {				 //途经点
            	order["waypoints"].forEach(function(e, i) {
                    price += 50.0; //每增加一个点位装卸货，增加50元
                    price += (e.floor<2) ? 0.0 : e.floor*10.0;
            	})
            }
    	}
    	else if(order["vehicle"] == 3) {   //全顺/依维轲
            price += (distance<10) ? 300.0 : (300.0+(distance-10)*8.0);  //起步价300（10公里内），超出后每公里8元
            price += (order["from"]["floor"]==0?0.0:50.0) + (order["to"]["floor"]==0?0.0:50.0); //电梯和1楼搬运按50元收取，每多1层加收20元
            price += (order["from"]["floor"]<2) ? 0.0 : (order["from"]["floor"]-1)*20.0;
            price += (order["to"]["floor"]<2) ? 0.0 : (order["to"]["floor"]-1)*20.0;
        	price += order["workers"]>1 ? (order["workers"]-1)*150 : 0;     //每增加一名搬家师傅，加收150元
            if(order["waypoints"].length > 0) {				 //途经点
            	order["waypoints"].forEach(function(e, i) {
                    price += 50.0; //每增加一个点位装卸货，增加50元
                    price += ((e.floor==0) ? 0.0 : ((e.floor==1)? 50.0: 50.0+(e.floor-1)*20.0));
            	})
            }
    	}
    	order["amount"] = price.toFixed(0);
    	$('.order_sumary .price em').text(order["amount"]);
    	return;
	};
	$('body').on('click tap touchend mouseup keyup', calculate);
	$('body').on('blur', 'input', {'forceConfirmPlace': true}, calculate);
			
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
		if($(this).is('.weui_btn_disabled')) {
			return;
		}
		$(this).addClass('weui_btn_disabled');
		$('.weui_cell_warn').removeClass('weui_cell_warn');
//		var pairs = $('form').serialize().split(/&/gi);
//		var data = {};
//		pairs.forEach(function(pair) {
//			pair = pair.split('=');
//			data[pair[0]] = decodeURIComponent(pair[1] || '');
//		});
		//收集数据
		order["contactor"] = $('input[name="contactor"]').val();
		order["phone"] = $('input[name="phone"]').val();
		order["remark"] = $('textarea[name="remark"]').val();
		//order["startTime"] = order["startTime"] || new Date().getTime();
		//验证订单
		if(!order["startTime"]) {
			$.weui.topTips('请输入搬家时间');
			$('input[name="start_time"]').addClass('weui_cell_warn');
		    $('#submit').removeClass('weui_btn_disabled');
			return;
		}
		if(!order["vehicle"] || (order["vehicle"]!=1 && order["vehicle"]!=2 && order["vehicle"]!=3)) {
			$.weui.topTips('未选择正确车型');
		    $('#submit').removeClass('weui_btn_disabled');
			return;
		}
		if(!order["from"]["address"]) {
			$.weui.topTips('请输入起始地');
			$('input[name="from_address"]').addClass('weui_cell_warn');
		    $('#submit').removeClass('weui_btn_disabled');
			return;
		}
//		if(!order["from_detail"]) {
//			$.weui.topTips('请输入起始地的详细信息');
//			$('input[name="from_detail"]').addClass('weui_cell_warn');
//		    $('#submit').removeClass('weui_btn_disabled');
//			return;
//		}
//		if(!order["from"]["floor"]) {
//			$.weui.topTips('请输入起始地的楼层信息');
//			$('select[name="from_floor"]').addClass('weui_cell_warn');
//		    $('#submit').removeClass('weui_btn_disabled');
//			return;
//		}
		if(!order["to"]["address"]) {
			$.weui.topTips('请输入目的地');
			$('input[name="to_address"]').addClass('weui_cell_warn');
		    $('#submit').removeClass('weui_btn_disabled');
			return;
		}
//		if(!order["to_detail"]) {
//			$.weui.topTips('请输入目的地的详细信息');
//			$('input[name="to_detail"]').addClass('weui_cell_warn');
//		    $('#submit').removeClass('weui_btn_disabled');
//			return;
//		}
//		if(!order["to"]["floor"]) {
//			$.weui.topTips('请输入起始地的楼层信息');
//			$('select[name="to_floor"]').addClass('weui_cell_warn');
//		    $('#submit').removeClass('weui_btn_disabled');
//			return;
//		}
		if (!order["contactor"]) {
			$.weui.topTips('请输入联系人姓名');
			$('input[name="contactor"]').addClass('weui_cell_warn');
		    $('#submit').removeClass('weui_btn_disabled');
			return;
		}
		if (!order["phone"] || !/(^(86-)?\d{11}$)|(^\d{3}-\d{8}$)|(^\d{4}-\d{7,8}$)/.test(order["phone"])) {
			$.weui.topTips('请输入正确的手机号码');
			$('input[name="phone"]').addClass('weui_cell_warn');
		    $('#submit').removeClass('weui_btn_disabled');
			return;
		}
		if (!order["workers"] || !/^\d+$/.test(order["workers"])) {
			$.weui.topTips('请输入正确的搬家师傅人数');
			$('input[name="workers"]').addClass('weui_cell_warn');
		    $('#submit').removeClass('weui_btn_disabled');
			return;
		}
		if(!order["distance"]) {
			$.weui.topTips('距离计算失败，请输入正确地址');
		    $('#submit').removeClass('weui_btn_disabled');
			return;
		}
		if(!order["amount"]) {
			$.weui.topTips('请输入确切信息以确定价格');
		    $('#submit').removeClass('weui_btn_disabled');
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
		    url: '/weixin-boot/order/put?open_id='+$('input[name="open_id"]').val()+'&source='+$('input[name="source"]').val(),
		    data: JSON.stringify(order),
		    contentType: 'application/json',
		    success: function(data){
	    		$.weui.hideLoading();
		    	if(data.success) {
		    		$.weui.hideLoading();
		    		$.weui.toast('订单提交成功');
					//$.weui.loading('订单提交成功');
					//setTimeout($.weui.hideLoading, 1000);
					var vehicleName = ['小面车型', '金杯车型', '全顺/依维柯'];
					$('#res_vehicle').text(vehicleName[order["vehicle"]-1]);
					$('#res_distance').text(order["distance"]+' 公里');
					$('#res_amount').text(order["amount"]+' 元');
					//$('#res_from_address').text(order["from"]["address"]);
					//$('#res_to_address').text(order["to"]["address"]);
					$('#res_contactor').text(order["contactor"]);
					$('#res_workers').text(order["workers"]);
					var date = new Date(order["startTime"]);
					$('#res_start_time').text(date.getFullYear()+'-'+(date.getMonth()+1)+'-'+date.getDate()+' '+date.getHours()+':'+date.getMinutes());
					if(order["waypoints"].length > 0) {
						order["waypoints"].forEach(function(e, i) {
							var $toEl = $('#res_to_address').parents('.weui_cell');
							$toEl.before('<div class="weui_cell">'+
					                			 '	<div class="weui_cell_hd">'+
					                			 '		<p><i class="weui_icon_success_circle"></i>途径点：</p>'+
					                			 '	</div>'+
					                			 '	<div class="weui_cell_bd weui_cell_primary">'+
					                			 '		<span id="res_way_address">'+e.address+'</span>'+
					                			 '	</div>'+
					                			 '</div>');
						});
					}
					$('form').hide();
					$('.weui_msg').show();
		    	}
		    	else {
					$.weui.topTips('订单信息有误，提交失败');
		    	}
		    	$('#submit').removeClass('weui_btn_disabled');
		      },
		      error: function(xhr, type){
					$.weui.topTips('订单信息有误，提交失败');
			    	$('#submit').removeClass('weui_btn_disabled');
		      }
		  })
	});
	
})