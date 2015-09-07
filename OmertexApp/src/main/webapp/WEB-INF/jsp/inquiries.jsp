<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<html>
<head>
<title>Inquiries</title>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
<link rel="stylesheet" href="<c:url value="/resources/styles/app.css" />" />
</head>
<body>
<div class="container">
	<c:import url="/WEB-INF/jsp/jsp/menu.jspx"></c:import>
	<div class="row marketing">
	<h1>This should be the table with all inquiries</h1>
	<table class="table table-stripped">
		<thead>
			<tr>
				<td>Id</td>
				<td>Description</td>
				<td>Creation date</td>
				<td>Customer name</td>
				<td>Topic</td>
				<td>Attributes</td>
			</tr>
		</thead>
		<tbody>
		</tbody>
	</table>
	</div>
	<script src="//code.jquery.com/jquery-1.11.3.min.js"></script>
	<script
		src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
		</div>
</body>
</html>
