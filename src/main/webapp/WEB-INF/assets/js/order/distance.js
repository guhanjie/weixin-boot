$(function() {
  
  DistancePlugin = {
    createNew: function(order, from, to, waypoints){
      var obj = {};
      // 创建百度地图实例
      var map = new BMap.Map("baiduMap", {
        enableMapClick : false
      });
      map.setCurrentCity("上海"); // 设置地图显示的城市 此项是必须设置的
      map.centerAndZoom("上海", 13); // 初始化地图,设置城市和地图级别。
      //三种驾车策略：最少时间，最短距离，避开高速
      var routePolicy = [BMAP_DRIVING_POLICY_LEAST_TIME,BMAP_DRIVING_POLICY_LEAST_DISTANCE,BMAP_DRIVING_POLICY_AVOID_HIGHWAYS];
      //驾车路线查询实例
      var driving = new BMap.DrivingRoute(map, {
        renderOptions : {map : map, autoViewport: false},
        policy : routePolicy[0],
        onSearchComplete : function(results) {
          //console.log("policy:"+results.policy+", city:"+results.city+", more:"+results.moreResultsUrl);
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
      
      //初始化自动提示地址输入框
      obj.initialInput = function(point) {
        map.setCurrentCity("上海"); // 设置地图显示的城市 此项是必须设置的
        map.centerAndZoom("上海", 13); // 初始化地图,设置城市和地图级别。
        var addressAC = new BMap.Autocomplete( // 建立一个自动完成的对象
          {
            "location" : map,
            "input" : point.target,
            "onSearchComplete" : function(r){
              if(r.getNumPois() > 0) {
                var _value = r.getPoi(0);
                var location = _value.province + _value.city + _value.district + _value.street + _value.streetNumber + _value.business;
                //console.log('关键字为：'+r.keyword+'地址为：'+location);
                //obj.locate(location, point);
              }
            } 
          });
        addressAC.addEventListener("onconfirm", function(e) { // 确定地址
          var _value = e.item.value;
          //addressAC.setInputValue(_value.district + _value.street + _value.business);
          var location = _value.province + _value.city +  _value.district + _value.street + _value.streetNumber + _value.business;
          //console.log("地址为：" + location + "<br/>");
          obj.locate(location, point);
        });
      };
      
      //定位输入关键字
      obj.locate = function(place, point) {
        map.clearOverlays(); // 清除地图上所有覆盖物
        point.point = null;//清楚先前位置信息
        var local = new BMap.LocalSearch(map, { // 本地智能搜索
          renderOptions: {map: map, autoViewport: false},
          onSearchComplete : function() {
            var poi = local.getResults().getPoi(0); // 获取第一个智能搜索的结果
            if(poi) {
              map.centerAndZoom(poi.point, 18);
              //map.addOverlay(new BMap.Marker(pp.point)); // 添加标注
              point.point = poi.point;
              point["address"] = $('#'+point.target).val();
              point["geoLat"] = poi.point.lat;
              point["geoLng"] = poi.point.lng;
              console.log("place located: " + poi.title + '-' +poi.address + '-' +poi.url);
            }
            obj.confirmPlace();
            //local.clearResults();
          }
        });
        local.disableAutoViewport();
        local.setLocation("上海市");
        local.search(place, {forceLocal:true});
      }

      //确认位置，进行路径规划，计算距离
      obj.confirmPlace = function() {
        //console.log("confirm place..."+from.point + to.point);
        var _from = from.point || from["address"];
        var _to = to.point || to["address"];

        if(_from && _to) {
          var _ways = [];
          if(waypoints && waypoints.length>0) {
            waypoints.forEach(function(e, i) {
              var _poi = e.point || e.address;
              if(_poi) {
                _ways.push(_poi);
              }
            });
          }
          if(_ways.length > 0) {
            driving.search(_from, _to, {waypoints:_ways});
          }
          else {
            driving.search(_from, _to);
          }
        }
      }
      
      return obj;
    }
  };
})