<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Welcome Page</title>
</head>
<body>
<h2>Select a Page</h2>
<form action="${pageContext.request.contextPath}/ControllerServlet" method="get">
    <label for="choice">Choose a page:</label>
    <select name="choice" id="choice">
        <option value="1">Page 1</option>
        <option value="2">Page 2</option>
    </select>
    <br><br>
    <input type="submit" value="Go">
</form>
</body>
</html>
