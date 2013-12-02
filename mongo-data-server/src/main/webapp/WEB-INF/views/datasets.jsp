<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<title>Home</title>
</head>
<body>
<h1>Datasets</h1>

<ul id="dataset-list">
	<c:forEach items="${datasets}" var="dataset">
		<c:url var="datasetUrl" value="/data/${dataset.name}"></c:url>
	    
		<li>
			<a href="<c:out value="${datasetUrl}"/>"><c:out value="${dataset.name}"/></a>
			<small><c:out value="${dataset.info.description}"/></small>
		</li>
	</c:forEach>
</ul>

</body>
</html>
