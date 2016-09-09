<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1, user-scalable=no">
<!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="keywords" content="搬家,货运,上门,服务,上海,市内搬家,同城搬家" />
<meta name="description" content="尊涵搬家服务，拉货、搬家按公里数计费，快速安全将货物送达。让用户以最经济的方式获得高质量、高性价比的同城货物运输体验。咨询热线：">
<meta name="description" content="" />
<link rel="stylesheet" href="resources/css/bootstrap/bootstrap.css">
<link rel="stylesheet" href="resources/css/fontawesome/font-awesome.css">
<link rel="stylesheet" href="resources/css/weui/weui.css">
<link rel="stylesheet" href="resources/css/order/order.css">
<link rel="stylesheet" type="text/css" href="resources/css/3rds/mobiscroll.scroller.css">
<link rel="stylesheet" type="text/css" href="resources/css/3rds/lxn.mobiscroll.scroller.wap2.css">
<title>尊涵搬家服务</title>
</head>
<body>
  <div class="container-fluid">
    <div class="row" id="order-index">
      <form>
        <input type="hidden" name="open_id" /> 
        <input type="hidden" name="distance" /> 
        <input type="hidden" name="amount" />
        <input type="hidden" name="vehicle" value="1"/>
        <div class="weui_cells_title">
          <div class="car-type">
            <div class="car-type-title" >
              <label class="car-type-label text-primary" data-type="1">
                <span class="car-type-radio"><i class="icon-ok-sign"> </i>小面</span>
              </label>
              <label class="car-type-label" data-type="2">
                <span class="car-type-radio"><i class="icon-circle-blank"> </i>金杯</span>
              </label>
              <label class="car-type-label" data-type="3">
                <span class="car-type-radio"><i class="icon-circle-blank"> </i>全顺</span>
              </label>
            </div>
            <div class="car-type-tips">
              <div class="arrow">
                <div></div>
              </div>
              <p id="carType1" style="display: block;">
                <span>尺寸：1.7m*1.4m*1.0m</span><br> 
                <span>范围：可容纳12个24寸行李箱</span>
              </p>
              <p id="carType2">
                <span>尺寸：2.7m*1.7m*1.2m</span><br> 
                <span>范围：可容纳28个24寸行李箱</span>
              </p>
              <p id="carType3">
                <span>尺寸：3.6m*2.2m*1.5m</span><br> 
                <span>范围：可容纳28个24寸行李箱</span>
              </p>
              <a href="javascript:void(0)">车型说明</a>
            </div>
          </div>
        </div>
        <div class="weui_cells weui_cells_form">
          <div class="weui_cell">
            <div class="weui_cell_hd">
              <label class="weui_label text-primary"><i class="icon-map-marker"> </i> 起始地</label>
            </div>
            <div class="weui_cell_bd weui_cell_primary">
              <input type="hidden" name="from_lat" />
              <input type="hidden" name="from_lng" />
              <input class="weui_input" name="from_address" type="text" placeholder="请输入起始地" />
              <input class="weui_input address-detail" name="from_detail" type="text" placeholder="几号几单元几室" />
              <select class="weui_select address-floor" name="from_floor">
                <option value="0">电梯-免费</option>
                <option value="1">1楼-免费</option>
                <option value="2">2楼-加收10元</option>
                <option value="3">3楼-加收20元</option>
                <option value="4">4楼-加收30元</option>
                <option value="5">5楼-加收40元</option>
                <option value="6">6楼-加收50元</option>
                <option value="7">7楼-加收60元</option>
                <option value="8">8楼-加收70元</option>
              </select>
            </div>
          </div>
          <div class="weui_cell">
            <div class="weui_cell_hd">
              <label class="weui_label text-primary"><i class="icon-map-marker"> </i> 目的地</label>
            </div>
            <div class="weui_cell_bd weui_cell_primary">
              <input type="hidden" name="to_lat" />
              <input type="hidden" name="to_lng" />
              <input class="weui_input" name="to_address" type="text" placeholder="请输入目的地" />
              <input class="weui_input address-detail" name="to_detail" type="text" placeholder="几号几单元几室" />
              <select class="weui_select address-floor" name="to_floor">
                <option value="0">电梯-免费</option>
                <option value="1">1楼-免费</option>
                <option value="2">2楼-加收10元</option>
                <option value="3">3楼-加收20元</option>
                <option value="4">4楼-加收30元</option>
                <option value="5">5楼-加收40元</option>
                <option value="6">6楼-加收50元</option>
                <option value="7">7楼-加收60元</option>
                <option value="8">8楼-加收70元</option>
              </select>
            </div>
          </div>
          <div class="weui_cell">
            <div class="weui_cell_hd">
              <label class="weui_label text-primary"><i class="icon-time"></i> 搬家时间</label>
            </div>
            <div class="weui_cell_bd weui_cell_primary">
              <input class="weui_input" id="start_time" name="start_time" type="text" placeholder="服务时间" />
            </div>
          </div>
          <div class="weui_cell">
            <div class="weui_cell_hd">
              <label class="weui_label text-primary"><i class="icon-user"> </i> 联系人</label>
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
              <label class="weui_label text-primary"><i class=" icon-edit"></i>订单备注</label>
            </div>
            <div class="weui_cell_bd weui_cell_primary">
              <textarea class="weui_textarea" name="remark" placeholder="请输入备注信息" rows="3" maxlength="200"></textarea>
              <div class="weui_textarea_counter">
                <span id="remark_count">0</span>/200
              </div>
            </div>
          </div>
        </div>
        <p class="weui_cells_tips">提示:目前只能查询金融社保卡制卡进度</p>
        <div class="weui_cell">
          <div class="weui_cell_bd weui_cell_primary">
            <p style="text-align: center; color: red; font-family: sans-serif; font-weight: bold; font-size: 2.5em;">
              <span>150</span>元
            </p>
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
</body>
</html>