$(function() {
	// 百度地图API功能
	var map = new BMap.Map("baiduMap", {
		enableMapClick : false
	});
	map.centerAndZoom("上海", 12); // 初始化地图,设置城市和地图级别。
	map.setCurrentCity("上海"); // 设置地图显示的城市 此项是必须设置的
	map.enableScrollWheelZoom(true); // 开启鼠标滚轮缩放
	// 创建地址解析器实例
	var geoCoder = new BMap.Geocoder();
	// 定义起末点
	var fromPoint = null;
	var toPoint = null;

	var fromAC = new BMap.Autocomplete( // 建立一个自动完成的对象
	{
		"input" : "from_address",
		"location" : map
	});

	fromAC.addEventListener("onhighlight", function(e) { // 鼠标放在下拉列表上的事件
		//console.log("from onhighlight...");
		var _value = e.toitem.value;
		var location = _value.province + _value.city + _value.district
				+ _value.street + _value.business;
		locate(location, 0);
	});

	fromAC.addEventListener("onconfirm", function(e) { // 确定起始地址
		//console.log("from onconfirm...");
		var _value = e.item.value;
		var location = _value.province + _value.city +  _value.district
				+ _value.street + _value.business;
		console.log("起始地为：" + location + "<br/>");
		locate(location, 0);
	});

	var toAC = new BMap.Autocomplete( // 建立一个自动完成的对象
	{
		"input" : "to_address",
		"location" : map
	});

	toAC.addEventListener("onhighlight", function(e) { // 鼠标放在下拉列表上的事件
		//console.log("to onhighlight...");
		var _value = e.toitem.value;
		var location = _value.province + _value.city + _value.district
				+ _value.street + _value.business;
		locate(location, 1);
	});

	toAC.addEventListener("onconfirm", function(e) { // 确定目的地址
		//console.log("to onconfirm...");
		var _value = e.item.value;
		var location = _value.province + _value.city + _value.district
				+ _value.street + _value.business;
		locate(location, 1);
		console.log("目的地为：" + location + "<br/>");
	});

	function locate(place, index) {
		map.clearOverlays(); // 清除地图上所有覆盖物
		var local = new BMap.LocalSearch(map, { // 智能搜索
			onSearchComplete : function() {
				var pp = local.getResults().getPoi(0); // 获取第一个智能搜索的结果
				map.centerAndZoom(pp.point, 18);
				map.addOverlay(new BMap.Marker(pp.point)); // 添加标注
				if (index == 0) {
					fromPoint = pp;
				} else if (index == 1) {
					toPoint = pp;
				}
				//console.log("located place..." + place + index);
				confirmPlace();
				// local.clearResults();
			}
		});
		local.search(place);
	}

	function confirmPlace() {
		//console.log("confirm place..."+fromPoint + toPoint);
		if (fromPoint && toPoint) {
			order["from_lng"] = fromPoint.point.lng;
			order["from_lat"] = fromPoint.point.lat;
			order["to_lng"] = toPoint.point.lng;
			order["to_lat"] = toPoint.point.lat;
			driving.search(fromPoint, toPoint);
		}
	}

	var driving = new BMap.DrivingRoute(map, {
		renderOptions : {
			map : map
		},
		policy : BMAP_DRIVING_POLICY_LEAST_TIME, // |
													// BMAP_DRIVING_POLICY_LEAST_DISTANCE
													// |
													// BMAP_DRIVING_POLICY_AVOID_HIGHWAYS,
		onSearchComplete : function(results) {
			if (driving.getStatus() != BMAP_STATUS_SUCCESS) {
				return;
			}
			console.log(results);
			console.log("起点：" + results.getStart().point.lng + ","
					+ results.getStart().point.lat + "，终点："
					+ results.getEnd().point.lng + ","
					+ results.getEnd().point.lat);
			for (var i = 0; i < results.getNumPlans(); i++) {
				var val = "";
				var plan = results.getPlan(i);
				val += "--" + i + "预计时间为：";
				val += plan.getDuration(true) + "\n"; // 获取时间
				val += "总路程为：";
				val += plan.getDistance(true) + "\n"; // 获取距离
				console.log(val);
				order["distance"] = plan.getDistance(false) / 1000;
				console.log("最终距离结果：" + order["distance"]);
			}
		}
	});

})