<%@ page  contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String path = request.getContextPath();
	if("/".equals(path)){
		path = "";
	}
	request.setAttribute("rootPath", path);
    /* 等价于JSP中使用如下EL表达式：${pageContext.request.contextPath} */
%>
<!DOCTYPE html>
<html lang="zh-CN">
    <head>
    	<meta charset="utf-8"/>
    	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
    	<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
    	<meta name="renderer" content="webkit|ie-comp|ie-stand">
    	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    	<meta name="keywords" content="${meta.keywords}"/>
    	<meta name="description" content="${meta.description}"/>
    	<link rel="shortcut icon" type="image/x-icon" href="${rootPath}/img/favicon.ico">
    	<t:importAttribute name="cssPath" />
        <link rel="stylesheet" href="${rootPath}/${cssPath}/common/bootstrap.css">
        <link rel="stylesheet" href="${rootPath}/${cssPath}/common/header.css">
        <link rel="stylesheet" href="${rootPath}/${cssPath}/common/footer.css">
    	<t:importAttribute name="css" />
        <c:if test='${not empty css}'>
            <link rel="stylesheet" href="${rootPath}/${cssPath}/${css}.css">
        </c:if>
    	<title><t:getAsString name="title"/>-${rootPath}</title>
    </head>
    <body>
		<t:insertAttribute name="body" />
        <t:importAttribute name="jsPath" />
        <script src="${rootPath}/${jsPath}/common/jquery-1.11.3.js"></script>
        <script src="${rootPath}/${jsPath}/common/bootstrap.js"></script>
        <t:importAttribute name="js" />
        <c:if test='${not empty js}'>
            <script src="${rootPath}/${jsPath}/${js}.js"></script>
        </c:if>
    </body>
</html>