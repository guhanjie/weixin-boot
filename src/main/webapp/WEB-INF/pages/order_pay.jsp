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
<title>尊涵搬家服务</title>
</head>
<body ontouchstart>
  <div id="baiduMap"></div>
  <form>
    <input type="hidden" name="open_id" value="${user.openId}"/>
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
                    <p><i class="weui_icon_success_circle"></i>预估路程</p>
                </div>
                <div class="weui_cell_bd weui_cell_primary">
                    <span id="res_distance"></span>
                </div>
            </div>
            <div class="weui_cell">
                <div class="weui_cell_hd">
                    <p><i class="weui_icon_success_circle"></i>预计费用</p>
                </div>
                <div class="weui_cell_bd weui_cell_primary">
                    <span id="res_amount"></span>
                </div>
            </div>
            <div class="weui_cell">
                <div class="weui_cell_hd">
                    <p><i class="weui_icon_success_circle"></i>预订车型</p>
                </div>
                <div class="weui_cell_bd weui_cell_primary">
                    <span id="res_vehicle"></span>
                </div>
            </div>
            <div class="weui_cell">
                <div class="weui_cell_hd">
                    <p><i class="weui_icon_success_circle"></i>起始地</p>
                </div>
                <div class="weui_cell_bd weui_cell_primary">
                    <span id="res_from_address"></span>
                </div>
            </div>
            <div class="weui_cell">
                <div class="weui_cell_hd">
                    <p><i class="weui_icon_success_circle"></i>目的地</p>
                </div>
                <div class="weui_cell_bd weui_cell_primary">
                    <span id="res_to_address"></span>
                </div>
            </div>
            <div class="weui_cell">
                <div class="weui_cell_hd">
                    <p><i class="weui_icon_success_circle"></i>联系人</p>
                </div>
                <div class="weui_cell_bd weui_cell_primary">
                    <span id="res_contactor"></span>
                </div>
            </div>
            <div class="weui_cell">
                <div class="weui_cell_hd">
                    <p><i class="weui_icon_success_circle"></i>服务时间</p>
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
  <script src="resources/js/order/pay.js"></script>
</body>
</html>