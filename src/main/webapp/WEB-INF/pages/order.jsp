<%@ page  contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="zh-CN">
    <head>
    	<meta charset="utf-8"/>
    	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
    	<meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1, user-scalable=no">
	    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    	<meta name="renderer" content="webkit|ie-comp|ie-stand">
    	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    	<meta name="keywords" content="搬家,货运,上门,服务,上海,市内搬家,同城搬家"/>
    	<meta name="description" content="尊涵搬家服务，拉货、搬家按公里数计费，快速安全将货物送达。让用户以最经济的方式获得高质量、高性价比的同城货物运输体验。咨询热线：">
    	<meta name="description" content=""/>
        <link rel="stylesheet" href="resources/css/bootstrap/bootstrap.css">
        <link rel="stylesheet" href="resources/css/fontawesome/font-awesome.css">
        <link rel="stylesheet" href="resources/css/weui/weui.css">
        <link rel="stylesheet" type="text/css" href="resources/css/3rds/mobiscroll.scroller.css">
        <link rel="stylesheet" type="text/css" href="resources/css/3rds/lxn.mobiscroll.scroller.wap2.css">
    	<title>尊涵搬家服务</title>
    </head>
    <body>
		<div class="container-fluid">
			<div class="row">
				<form>
					<input type="hidden" name="open_id">
					<input type="hidden" name="distance">
					<input type="hidden" name="amount">
					  <div class="weui_cells_title">创建订单</div>
					    <div class="weui_cells weui_cells_form">
					        <div class="weui_cell weui_cell_select weui_select_after">
					            <div class="weui_cell_hd">
					                <label class="weui_label">车型</label>
					            </div>
					            <div class="weui_cell_bd weui_cell_primary">
					                <select class="weui_select" name="vehicle">
					                    <option value="1">小面车型</option>
					                    <option value="2">金杯车型</option>
					                    <option value="3">全顺/依维柯</option>
					                </select>
					            </div>
					        </div>
					        <div class="weui_cell">
					            <div class="weui_cell_hd">
					                <label class="weui_label"><i class="icon-map-marker"></i></label>
					            </div>
					            <div class="weui_cell_bd weui_cell_primary">
					                <input class="weui_input" name="from_address" type="text" placeholder="请输入起始地" />
					                <input class="weui_input" name="from_detail" type="text" placeholder="请输入起始地" />   
					                <input type="hidden" name="from_lat">   
					                <input type="hidden" name="from_lng">  
					                <select class="weui_select" name="from_floor">
					                    <option value="0">电梯-楼层费0元</option>
					                    <option value="1">无电梯1楼-楼层费0元</option>
					                    <option value="2">无电梯2楼-楼层费10元</option>
					                    <option value="3">无电梯3楼-楼层费20元</option>
					                    <option value="4">无电梯4楼-楼层费30元</option>
					                    <option value="5">无电梯2楼-楼层费40元</option>
					                    <option value="6">无电梯3楼-楼层费50元</option>
					                    <option value="7">无电梯4楼-楼层费60元</option>
					                    <option value="8">无电梯4楼-楼层费70元</option>
					                </select>
					            </div>
					        </div>
					        <div class="weui_cell">
					            <div class="weui_cell_hd">
					                <label class="weui_label text-primary"><i class="icon-time"></i>  搬家时间</label>
					            </div>
					            <div class="weui_cell_bd weui_cell_primary">
					                <input class="weui_input" id="start_time" name="start_time" type="text" placeholder="服务时间" />
					            </div>
					        </div>
					        <div class="weui_cell">
					            <div class="weui_cell_hd">
					                <label class="weui_label text-primary"><i class="icon-user"></i> 联系人</label>
					            </div>
					            <div class="weui_cell_bd weui_cell_primary">
					                <input class="weui_input" name="contactor" type="text" placeholder="请输入联系人姓名" />
					            </div>
					        </div>
					        <div class="weui_cell">
					            <div class="weui_cell_hd">
					                <label class="weui_label text-primary"><i class="icon-phone"></i> 联系电话</label>
					            </div>
					            <div class="weui_cell_bd weui_cell_primary">
					                <input class="weui_input" name="phone" type="tel" placeholder="请输入联系电话" />
					            </div>
					        </div>
					        <div class="weui_cell">
					            <div class="weui_cell_hd">
					                <label class="weui_label">订单备注</label>
					            </div>
					            <div class="weui_cell_bd weui_cell_primary">
					                <textarea class="weui_textarea" name="remark" placeholder="请输入备注信息" rows="3" maxlength="200"></textarea>
					                <div class="weui_textarea_counter"><span id="remark_count">0</span>/200</div>
					            </div>
					        </div>
					    </div>
					    <p class="weui_cells_tips">提示:目前只能查询金融社保卡制卡进度</p>
					    <div class="weui_cell">
					        <div class="weui_cell_bd weui_cell_primary">
					            <p style="text-align:center;color:red;font-family:sans-serif;font-weight:bold;font-size:2.5em;"><span>150</span>元</p>
					        </div>
					    </div>
					    <div class="weui_btn_area">
					        <a class="weui_btn weui_btn_primary" id="button" href="javascript:">查询</a>
					    </div>
					</form>
			</div>
		</div>
        <script src="resources/js/jquery/jquery-1.11.3.js"></script>
        <script src="resources/js/bootstrap/bootstrap.js"></script>
        <script src="resources/js/weui/weui.js"></script>
        <script src="resources/js/3rds/mobiscroll.core.js"></script>
        <script src="resources/js/3rds/lxn.mobiscroll.scroller.js"></script>
        <script src="resources/js/3rds/lxn.mobiscroll.datetime.js"></script>
        <script src="resources/js/3rds/lxn.mobiscroll.scroller.wap2.js"></script>
        <script src="resources/js/3rds/lxn.mobiscroll.i18n.zh.js"></script>
        <script src="resources/js/order/order.js"></script>
        <script type="text/javascript">
        $(function () {
            var datetime = new Date(1473164193000 + 3600000 * 8);
            var year     = datetime.getUTCFullYear();
            var month    = datetime.getUTCMonth();
            var day      = datetime.getUTCDate();
            var hour     = datetime.getUTCHours() + 2;
            var minute   = datetime.getUTCMinutes();
            minute       = ((minute < 30) ? 30 : 60);
            var minDate  = new Date(year, month, day, hour, minute);
            var maxDate  = new Date(year, month, day+7, hour, minute);

            var options  = {
                theme     : 'wap2',
                dateOrder : 'S',
                minDate   : minDate,
                maxDate   : maxDate,
                setText   : '预约用车',
                cancelText: '马上用车',
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

                    var ts = date.getTime() / 1000 - date.getTimezoneOffset() * 60;
                    order_data['service_type'] = 100;
                    order_data['service_label'] = '预约服务';
                    order_data['service_time_desc'] = str;
                    order_data['service_timestamp'] = (ts - 8 * 60 * 60);
                    $('#service_time').text(showtime).attr('serviceTime',showtime);

                    lxn_order.setItem('order_data', order_data);
                    lxn_order.save();
                    calculate_order_price();
                },
                onCancel : function(){
                    init_time();
                    $('#service_time').text('马上用车').attr('serviceTime','马上用车');
                }
            };//options
            $('#start_time').mobiscroll().datetime(options);
            var service_type = order_data['service_type'];
            if(service_type){
                $('#service_time').attr('servicetime',service_type);
            }
            function init_time(){
                var date = new Date(1473164193000);
                order_data['service_type'] = 200;
                order_data['service_label'] = '马上用车';
                order_data['service_time_desc'] = year+'/'+(month+1)+'/'+day+' '+(datetime.getUTCHours()>9?datetime.getUTCHours():'0'+datetime.getUTCHours())+':'+(datetime.getUTCMinutes()>9?datetime.getUTCMinutes():'0'+datetime.getUTCMinutes());
                order_data['service_timestamp'] = date.getTime() / 1000;

                lxn_order.setItem('order_data', order_data);
                lxn_order.save();
                calculate_order_price();
            }
            if(!service_type){
               init_time();
               $('#service_time').text('马上用车').attr('serviceTime','马上用车');
            }
            //计算价格
        });
    </script>
    </body>
</html>