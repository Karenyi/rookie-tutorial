<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>部門資料</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/table.css">
</head>
<body>
    <h3>部門資料</h3>
    <table>
        <tr>
            <th>部門編號</th>
            <th>部門名稱</th>
            <th>部門基地</th>
        </tr>
        <tr align='center' valign='middle'>
            <td>${deptVO.deptno}</td>
            <td>${deptVO.dname}</td>
            <td>${deptVO.loc}</td>
        </tr>
    </table>
    <br />
    <a href="${pageContext.request.contextPath}/index.jsp">回首頁</a>
</body>
</html>
