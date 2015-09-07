<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<html>
<head>
<title>Topics</title>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
<link rel="stylesheet" href="<c:url value="/resources/styles/app.css" />" />
</head>
<body>
<div class="container">
	<c:import url="/WEB-INF/jsp/jsp/menu.jspx"></c:import>
	<div class="row marketing">
	<h1>This should be the tables with all topics</h1>
	<table class="table table-stripped">
		<thead>
			<tr>
				<td>Name</td>
				<td>Creation time</td>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${topicList}" var="topic" >
				<tr>
					<td><c:out value="${topic.name}"/></td>
					<td><c:out value="${topic.creationTime}"/></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	</div>
</div>
	<script src="//code.jquery.com/jquery-1.11.3.min.js"></script>
	<script
		src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
</body>
</html>
