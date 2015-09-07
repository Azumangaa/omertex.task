<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page session="false"%>
<html>
<head>
<title>Home</title>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
<link rel="stylesheet"
	href="<c:url value="/resources/styles/app.css" />" />
</head>
<body>
	<div class="container">
		<c:import url="/WEB-INF/jsp/jsp/menu.jspx"></c:import>
		<div class="row marketing">
			<div class="row center">
				<form:form action="${pageContext.request.contextPath}/customer/add"
					commandName="customer" method="POST" enctype="utf8" role="form">
					<div id="form-group-name" class="form-group">
						<label class="control-label" for="inputSuccess2"></label> <label
							class="control-label" for="customer-name">Customer name:</label>
						<form:input id="customer-name" path="name"
							cssClass="form-control" />
						<form:errors id="error-customerName" path="name"
							cssClass="help-block" />
					</div>
					<button type="submit" class="btn btn-primary btn-lg btn-block">
						Add customer</button>
				</form:form>
			</div>
		</div>
		<script src="//code.jquery.com/jquery-1.11.3.min.js"></script>
		<script
			src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
	</div>
</body>
</html>
