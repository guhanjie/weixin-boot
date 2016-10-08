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
<meta name="description" content="尊涵搬家服务，拉货、搬家按公里数计费，快速安全将货物送达。让用户以最经济的方式获得高质量、高性价比的同城货物运输体验。咨询热线：18916840930">
<link rel="stylesheet" href="resources/css/fontawesome/font-awesome.css">
<link rel="stylesheet" href="resources/css/weui/weui.css">
<link rel="stylesheet" href="resources/css/order/order.css">
<link rel="stylesheet" type="text/css" href="resources/css/3rds/mobiscroll.scroller.css">
<link rel="stylesheet" type="text/css" href="resources/css/3rds/lxn.mobiscroll.scroller.wap2.css">
<title>尊涵搬家服务</title>
</head>
<body ontouchstart>
  <div id="baiduMap"></div>
  <form>
    <input type="hidden" name="open_id" value="123"/>
    <div class="weui_cells_title">
      <div class="car-type">
        <div class="car-type-title">
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
            <span>计价：起步150元(10公里)，超出5元/公里</span><br> 
            <span>容量：可容纳10个24寸行李箱，载重600kg</span>
          </p>
          <p id="carType2">
            <span>计价：起步200元(10公里)，超出6元/公里</span><br> 
            <span>容量：可容纳15个24寸行李箱，载重1500kg</span>
          </p>
          <p id="carType3">
            <span>计价：起步300元(10公里)，超出8元/公里</span><br> 
            <span>容量：可容纳25个24寸行李箱，载重1500kg</span>
          </p>
          <a href="javascript:void(0)">车型说明</a>
        </div>
      </div>
    </div>
    <div class="weui_cells weui_cells_form">
      <div class="weui_cell">
        <div class="weui_cell_hd">
          <label class="weui_label text-primary">
            <i class="icon-map-marker"> </i>起始地
          </label>
        </div>
        <div class="weui_cell_bd weui_cell_primary">
          <input class="weui_input" id="from_address" name="from_address" type="text" placeholder="请输入起始地" />
          <input class="weui_input address-detail" name="from_detail" type="text" placeholder="几号几室" />
          <div class="floor_select">
            <select class="weui_select address-floor" name="from_floor">
              <option value="0">电梯-免费</option>
              <option value="1">1楼-免费</option>
              <option value="2">2楼-加收20元</option>
              <option value="3">3楼-加收30元</option>
              <option value="4">4楼-加收40元</option>
              <option value="5">5楼-加收50元</option>
              <option value="6">6楼-加收60元</option>
              <option value="7">7楼-加收70元</option>
              <option value="8">8楼-加收80元</option>
            </select>
          </div>
        </div>
      </div>
      <div class="weui_cell">
        <div class="weui_cell_hd">
          <label class="weui_label text-primary">
            <i class="icon-map-marker"> </i>目的地
          </label>
        </div>
        <div class="weui_cell_bd weui_cell_primary">
          <input class="weui_input" id="to_address" name="to_address" type="text" placeholder="请输入目的地" />
          <input class="weui_input address-detail" name="to_detail" type="text" placeholder="几号几室" />
          <div class="floor_select">
            <select class="weui_select address-floor" name="to_floor">
              <option value="0">电梯-免费</option>
              <option value="1">1楼-免费</option>
              <option value="2">2楼-加收20元</option>
              <option value="3">3楼-加收30元</option>
              <option value="4">4楼-加收40元</option>
              <option value="5">5楼-加收50元</option>
              <option value="6">6楼-加收60元</option>
              <option value="7">7楼-加收70元</option>
              <option value="8">8楼-加收80元</option>
            </select>
          </div>
        </div>
      </div>
      <div class="addway">
        <i class="icon-plus-sign-alt"> 还有途经点~</i>
      </div>
      <div class="weui_cell">
        <div class="weui_cell_hd">
          <label class="weui_label text-primary">
            <i class="icon-time"> </i>搬家时间
          </label>
        </div>
        <div class="weui_cell_bd weui_cell_primary">
          <input class="weui_input" id="start_time" name="start_time" type="text" placeholder="服务时间" />
        </div>
      </div>
      <div class="weui_cell">
        <div class="weui_cell_hd">
          <label class="weui_label text-primary">
            <i class="icon-user"> </i> 联系人
          </label>
        </div>
        <div class="weui_cell_bd weui_cell_primary">
          <input class="weui_input" name="contactor" type="text" placeholder="请输入联系人姓名" value="${user.name}"/>
        </div>
      </div>
      <div class="weui_cell">
        <div class="weui_cell_hd">
          <label class="weui_label text-primary">
            <i class="icon-phone"> </i>联系电话
          </label>
        </div>
        <div class="weui_cell_bd weui_cell_primary">
          <input class="weui_input" name="phone" type="tel" placeholder="请输入联系电话" value="${user.phone}" />
        </div>
      </div>
      <div class="weui_cell">
        <div class="weui_cell_hd">
          <label class="weui_label text-primary">
            <i class="icon-group"> </i>搬家师傅
          </label>
        </div>
        <div class="weui_cell_bd weui_cell_primary">
          <input class="weui_input" name="workers" type="number" min="1" max="10" placeholder="请输入搬家师傅人数，默认1人" />
        </div>
      </div>
      <div class="weui_cell remark">
        <div class="weui_cell_hd">
          <label class="weui_label text-primary">
            <i class=" icon-edit"> </i>订单备注
          </label>
        </div>
        <div class="weui_cell_bd weui_cell_primary">
          <textarea class="weui_textarea" name="remark" placeholder="请输入备注信息" rows="1" maxlength="200"></textarea>
          <div class="weui_textarea_counter">
            <span id="remark_count">0</span>/200
          </div>
        </div>
      </div>
    </div>
    <p class="weui_cells_tips">提示：计费距离以百度推荐路径为准，价格根据实际情况可调</p>
    <p class="weui_cells_tips order_sumary">
          <span class="distance">全程<em>0</em>公里</span>
          <span class="price"><em>0</em>元</span>
    </p>
    <div class="weui_btn_area">
      <a class="weui_btn weui_btn_primary" id="submit">确认下单</a>
    </div>
  </form>
  
  <div class="weui_msg" style="display: none;">
    <div class="weui_icon_area"><i class="weui_icon_success weui_icon_msg"></i></div>
    <div class="weui_text_area">
        <h2 class="weui_msg_title">操作成功</h2>
        <p class="weui_msg_desc">订单已提交，我们会尽快为您安排服务</p>
        <div class="weui_cells_title">订单详情如下：</div>
        <div class="weui_cells">
            <div class="weui_cell">
                <div class="weui_cell_hd">
                    <p><i class="weui_icon_success_circle"></i>预估路程：</p>
                </div>
                <div class="weui_cell_bd weui_cell_primary">
                    <span id="res_distance"></span>
                </div>
            </div>
            <div class="weui_cell">
                <div class="weui_cell_hd">
                    <p><i class="weui_icon_success_circle"></i>预计费用：</p>
                </div>
                <div class="weui_cell_bd weui_cell_primary">
                    <span id="res_amount"></span>
                </div>
            </div>
            <div class="weui_cell">
                <div class="weui_cell_hd">
                    <p><i class="weui_icon_success_circle"></i>预订车型：</p>
                </div>
                <div class="weui_cell_bd weui_cell_primary">
                    <span id="res_vehicle"></span>
                </div>
            </div>
            <div class="weui_cell">
                <div class="weui_cell_hd">
                    <p><i class="weui_icon_success_circle"></i>起始地：</p>
                </div>
                <div class="weui_cell_bd weui_cell_primary">
                    <span id="res_from_address"></span>
                </div>
            </div>
            <div class="weui_cell">
                <div class="weui_cell_hd">
                    <p><i class="weui_icon_success_circle"></i>目的地：</p>
                </div>
                <div class="weui_cell_bd weui_cell_primary">
                    <span id="res_to_address"></span>
                </div>
            </div>
            <div class="weui_cell">
                <div class="weui_cell_hd">
                    <p><i class="weui_icon_success_circle"></i>联系人：</p>
                </div>
                <div class="weui_cell_bd weui_cell_primary">
                    <span id="res_contactor"></span>
                </div>
            </div>
            <div class="weui_cell">
                <div class="weui_cell_hd">
                    <p><i class="weui_icon_success_circle"></i>搬家师傅：</p>
                </div>
                <div class="weui_cell_bd weui_cell_primary">
                    <span id="res_workers"></span> 人
                </div>
            </div>
            <div class="weui_cell">
                <div class="weui_cell_hd">
                    <p><i class="weui_icon_success_circle"></i>服务时间：</p>
                </div>
                <div class="weui_cell_bd weui_cell_primary">
                    <span id="res_start_time"></span>
                </div>
            </div>
        </div>
    </div>
    <div class="weui_opr_area">
        <p class="weui_btn_area">
            <!-- <a href="javascript:;" class="weui_btn weui_btn_primary">确定</a> -->
        </p>
    </div>
    <div class="weui_extra_area">
        <!-- <a href="">查看详情</a> -->
    </div>
  </div>
  
  <script src="resources/js/zepto/zepto-1.1.6.js"></script>
  <script src="resources/js/weui/weui.js"></script>
  <script src="resources/js/3rds/mobiscroll.zepto.js"></script>
  <script src="resources/js/3rds/mobiscroll.core.js"></script>
  <script src="resources/js/3rds/lxn.mobiscroll.scroller.js"></script>
  <script src="resources/js/3rds/lxn.mobiscroll.datetime.js"></script>
  <script src="resources/js/3rds/lxn.mobiscroll.i18n.zh.js"></script>
  <script src="http://api.map.baidu.com/api?v=2.0&ak=jZG6MRj1WWYy5tlPLpZf6h8q5Q5ZGcTT"></script>
  <script src="resources/js/order/distance.js"></script>
  <script src="resources/js/order/order.js"></script>
</body>
</html>