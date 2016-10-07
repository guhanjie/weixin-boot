$(function() {
	
	DistancePlugin = {
		createNew: function(order, from, to, waypoints){
			var obj = {};
			// 百度地图API功能
			var map = new BMap.Map("baiduMap", {
				enableMapClick : false
			});
			map.centerAndZoom("上海", 12); // 初始化地图,设置城市和地图级别。
			map.setCurrentCity("上海"); // 设置地图显示的城市 此项是必须设置的
			map.enableScrollWheelZoom(true); // 开启鼠标滚轮缩放
			// 创建地址解析器实例
			var geoCoder = new BMap.Geocoder();
			//三种驾车策略：最少时间，最短距离，避开高速
			var routePolicy = [BMAP_DRIVING_POLICY_LEAST_TIME,BMAP_DRIVING_POLICY_LEAST_DISTANCE,BMAP_DRIVING_POLICY_AVOID_HIGHWAYS];
			var driving = new BMap.DrivingRoute(map, {
				renderOptions : {
					map : map/*, autoViewport: true*/
				},
				policy : routePolicy[0],
				onSearchComplete : function(results) {
					if (driving.getStatus() != BMAP_STATUS_SUCCESS) {
						return;
					}
					console.log(results);
					console.log("起点：" + results.getStart().point.lng + "," + results.getStart().point.lat
							 			+ "，终点：" + results.getEnd().point.lng + "," + results.getEnd().point.lat);
					for (var i = 0; i < results.getNumPlans(); i++) {
						var val = "";
						var plan = results.getPlan(i);
						val += "--" + i + " 预计时间为：";
						val += plan.getDuration(true) + " "; // 获取时间
						val += "总路程为：";
						val += plan.getDistance(true) + " "; // 获取距离
						console.log(val);
						order["distance"] = Math.ceil(plan.getDistance(false) / 1000);
						document.body.click();
						console.log("最终距离结果：" + order["distance"]);
					}
				}
			});
			
			obj.initialInput = function(input_id, point) {
				var addressAC = new BMap.Autocomplete( // 建立一个自动完成的对象
					{
						"location" : map,
						"input" : input_id,
						"onSearchComplete" : function(r){
							if(r.getNumPois() > 0) {
								var _value = r.getPoi(0);
								var location = _value.province + _value.city + _value.district + _value.street + _value.streetNumber + _value.business;
								//console.log('地址为：'+location);
							}
						} 
					});
//				addressAC.addEventListener("onhighlight", function(e) { // 鼠标放在下拉列表上的事件
//					var _value = e.toitem.value;		//当前记录的信息
//					var location = _value.province + _value.city + _value.district + _value.street + _value.business;
//					console.log("地址为：" + location + "<br/>");
//				});
				addressAC.addEventListener("onconfirm", function(e) { // 确定地址
					var _value = e.item.value;
					var location = _value.province + _value.city +  _value.district + _value.street + _value.business;
					console.log("地址为：" + location + "<br/>");
					locate(location, point);
				});
			};
			
			function locate(place, point) {
				map.clearOverlays(); // 清除地图上所有覆盖物
				var local = new BMap.LocalSearch(map, { // 智能搜索
					onSearchComplete : function() {
						var pp = local.getResults().getPoi(0); // 获取第一个智能搜索的结果
						map.centerAndZoom(pp.point, 18);
						//map.addOverlay(new BMap.Marker(pp.point)); // 添加标注
						point.address = pp.title+pp.address;
						point.point = pp.point;
						point.lat = pp.point.lat;
						point.lng = pp.point.lng;
						//console.log("located place: " + pp.title + '-' +pp.address + '-' +pp.url);
						confirmPlace();
						// local.clearResults();
					}
				});
				local.search(place);
			}

			function confirmPlace() {
				//console.log("confirm place..."+from.point + to.point);
				if (from.point && to.point) {
					order["from_lng"] = from.point.lng;
					order["from_lat"] = from.point.lat;
					order["to_lng"] = to.point.lng;
					order["to_lat"] = to.point.lat;
					var ways = [];
					if(waypoints && waypoints.length>0) {
						waypoints.forEach(function(e, i) {
							if(e.point) {
								ways.push(e.point);
							}
						});
					}
					if(ways.length > 0) {
						driving.search(from.point, to.point, {waypoints:ways});
					}
					else {
						driving.search(from.point, to.point);
					}
				}
			}
			
			return obj;
		}
	};
	
//	// 百度地图API功能
//	var map = new BMap.Map("baiduMap", {
//		enableMapClick : false
//	});
//	map.centerAndZoom("上海", 12); // 初始化地图,设置城市和地图级别。
//	map.setCurrentCity("上海"); // 设置地图显示的城市 此项是必须设置的
//	map.enableScrollWheelZoom(true); // 开启鼠标滚轮缩放
//	// 创建地址解析器实例
//	var geoCoder = new BMap.Geocoder();
//	var from = {};
//	var to = {}
//	
//	function initialInput(input_id, point) {
//		var addressAC = new BMap.Autocomplete( // 建立一个自动完成的对象
//		{
//			"location" : map,
//			"input" : input_id,
//			"onSearchComplete" : function(r){
//				if(r.getNumPois() > 0) {
//					var _value = r.getPoi(0);
//					var location = _value.province + _value.city + _value.district + _value.street + _value.streetNumber + _value.business;
//					console.log('地址为：'+location);
//				}
//			} 
//		});
//
////		addressAC.addEventListener("onhighlight", function(e) { // 鼠标放在下拉列表上的事件
////			var _value = e.toitem.value;		//当前记录的信息
////			var location = _value.province + _value.city + _value.district + _value.street + _value.business;
////			console.log("地址为：" + location + "<br/>");
////		});
//
//		addressAC.addEventListener("onconfirm", function(e) { // 确定地址
//			var _value = e.item.value;
//			var location = _value.province + _value.city +  _value.district + _value.street + _value.business;
//			console.log("地址为：" + location + "<br/>");
//			locate(location, point);
//		});
//	}
//	
//	function locate(place, point) {
//		map.clearOverlays(); // 清除地图上所有覆盖物
//		var local = new BMap.LocalSearch(map, { // 智能搜索
//			onSearchComplete : function() {
//				var pp = local.getResults().getPoi(0); // 获取第一个智能搜索的结果
//				map.centerAndZoom(pp.point, 18);
//				//map.addOverlay(new BMap.Marker(pp.point)); // 添加标注
//				point.point = pp.point;
//				console.log("located place: " + pp.title + '-' +pp.address + '-' +pp.url);
//				confirmPlace();
//				// local.clearResults();
//			}
//		});
//		local.search(place);
//	}
//
//	function confirmPlace(order) {
//		//console.log("confirm place..."+from.point + to.point);
//		if (from.point && to.point) {
//			order["from_lng"] = from.point.lng;
//			order["from_lat"] = from.point.lat;
//			order["to_lng"] = to.point.lng;
//			order["to_lat"] = to.point.lat;
//			driving.search(from.point, to.point);
//		}
//	}
//
//	var driving = new BMap.DrivingRoute(map, {
//		renderOptions : {
//			map : map
//		},
//		policy : BMAP_DRIVING_POLICY_LEAST_TIME, // |
//													// BMAP_DRIVING_POLICY_LEAST_DISTANCE
//													// |
//													// BMAP_DRIVING_POLICY_AVOID_HIGHWAYS,
//		onSearchComplete : function(results) {
//			if (driving.getStatus() != BMAP_STATUS_SUCCESS) {
//				return;
//			}
//			console.log(results);
//			console.log("起点：" + results.getStart().point.lng + "," + results.getStart().point.lat + 
//								"，终点：" + results.getEnd().point.lng + "," + results.getEnd().point.lat);
//			for (var i = 0; i < results.getNumPlans(); i++) {
//				var val = "";
//				var plan = results.getPlan(i);
//				val += "--" + i + " 预计时间为：";
//				val += plan.getDuration(true) + " "; // 获取时间
//				val += "总路程为：";
//				val += plan.getDistance(true) + " "; // 获取距离
//				console.log(val);
//				order["distance"] = Math.ceil(plan.getDistance(false) / 1000);
//				document.body.click();
//				console.log("最终距离结果：" + order["distance"]);
//			}
//		}
//	});
//	
//	initialInput("from_address", from);
//	initialInput("to_address", to);
//	
//	
//	distancePlugin = {
//		initialInput : initialInput,
//	};
})