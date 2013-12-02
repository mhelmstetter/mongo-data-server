<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<title><c:out value="${dataset.name}"/></title>
</head>
<body>
<h1><c:out value="${dataset.name}"/></h1>

<h2>Slices</h2>
<ul id="slice-list">
	<c:forEach items="${dataset.slices}" var="slice">
		<c:url var="sliceUrl" value="/data/${dataset.name}/${slice.name}"></c:url>
	    
		<li>
			<a href="<c:out value="${sliceUrl}"/>"><c:out value="${slice.name}"/></a>
			<small><c:out value="${slice.info.description}"/></small>
		</li>
	</c:forEach>
</ul>

</body>
</html>
