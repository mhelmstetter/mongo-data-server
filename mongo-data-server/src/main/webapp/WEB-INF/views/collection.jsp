<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<title><c:out value="${collection.name}"/></title>
</head>
<body>
<h1><c:out value="${collection.name}"/></h1>

<h2>Fields</h2>
<ul id="field-list">
	<c:forEach items="${collection.fields}" var="field">
		
	    
		<li>
			<c:out value="${field}"/>
		</li>
	</c:forEach>
</ul>

</body>
</html>
