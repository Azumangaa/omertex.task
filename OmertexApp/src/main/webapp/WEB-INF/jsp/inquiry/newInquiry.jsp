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
<script src="//code.jquery.com/jquery-1.11.3.min.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$( "#addNewAttribute" ).click( function() {
			var attributesCount = $("#Attributes tr").length;
			$.get("<%=request.getContextPath()%>/inquiryAttributeRow", {
				fieldId : attributesCount
			}, function(data) {
				$('#Attributes:last-child').append(data);
			});
		});
	})
</script>
</head>
<body>
	<div class="container">
		<c:import url="/WEB-INF/jsp/jsp/menu.jspx"></c:import>
		<div class="row marketing">
			<div class="row center">
				<form:form
					action="${pageContext.request.contextPath}/customer/${inquiry.customer}/inquiry"
					commandName="inquiry" method="POST" enctype="utf8" role="form">
					<div id="form-group-description" class="form-group">
						<label class="control-label" for="inputSuccess2"></label> <label
							class="control-label" for="inquiry-description">Inquiry
							description:</label>
						<form:input id="inquiry-description" path="description"
							cssClass="form-control" />
						<form:errors id="error-description" path="description"
							cssClass="help-block" />
					</div>
					<div id="form-group-customer" class="form-group">
						<label class="control-label" for="inputSuccess2"></label> <label
							class="control-label" for="inquiry-customer">Customer
							name:</label>
						<form:input id="inquiry-customer" path="customer"
							cssClass="form-control" disabled="true" />
					</div>
					<div id="form-group-attributes" class="form-group">
						<label class="control-label" for="inputSuccess2"></label> <label
							class="control-label" for="">Attributes:</label>
						<table class="table">
							<tbody id="Attributes">
							</tbody>
						</table>
						<a href="#" id="addNewAttribute">Add new attribute</a>
					</div>
					<div id="form-group-topic" class="form-group">
						<label class="control-label" for="inputSuccess2"></label> <label
							class="control-label" for="">Topic:</label>
							<form:select path="topic">
								<c:forEach items="${topics}" var="topic">
									<form:option value="${topic.id}">
										<c:out value="${topic.name}"></c:out>
									</form:option>
								</c:forEach>
							</form:select>
					</div>
					<button type="submit" class="btn btn-primary btn-lg btn-block">
						Add inquiry</button>
				</form:form>
			</div>
		</div>
		<script src="//code.jquery.com/jquery-1.11.3.min.js"></script>
		<script
			src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
		<!--	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/scripts/addNewAttribute.js"></script>-->

	</div>
</body>
</html>
