<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>

<div class="container">
	<t:insertAttribute name="header" />
	<t:insertAttribute name="nav" />
	<t:insertAttribute name="content" ignore="true"/>
	<t:insertAttribute name="footer" />
</div>