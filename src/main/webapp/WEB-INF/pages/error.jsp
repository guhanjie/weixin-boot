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
<link rel="shortcut icon" type="image/x-icon" href="${pageContext.request.contextPath}/resources/img/favicon.ico">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap/bootstrap.css">
<title>Index</title>
</head>
<body>
  <div class="container" id="page-body">
    <div class="row">
      <div class="col-sm-offset-3 col-sm-6">
        <img src="${pageContext.request.contextPath}/resources/img/error.jpg" class="img-responsive img-rounded" />
      </div>
    </div>
    <div class="row">
      <div style="color: #666;" ondblclick='$("#detailMsg").slideToggle(1000);'>
        <c:if test="${not empty exception.screenMessage}">
                  ${exception.screenMessage}
                </c:if>
        <c:if test="${empty exception.screenMessage}">
                  ${exception.code}:${exception.message}
                </c:if>
      </div>
    </div>
    <div class="row">
      <div id="detailMsg" style="display: none;">
        <br>错误代码：${exception.code} <br>错误简述：${exception.message}
        <c:if test="${not empty exception.causeMessage}">
          <br>错误详情：${exception.causeMessage}
                </c:if>
        <c:if test="${empty exception.causeMessage}">
          <br>错误详情：${exception.cause}
                </c:if>
      </div>
    </div>
  </div>
  <script src="${pageContext.request.contextPath}/resources/js/jquery/jquery-1.11.3.js"></script>
</body>
</html>