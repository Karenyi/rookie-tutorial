<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>部門員工</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/table.css">
</head>
<body>

    <h3>部門員工</h3>
    <table>
        <tr>
            <th>員工編號</th>
            <th>員工姓名</th>
            <th>職位</th>
            <th>雇用日期</th>
            <th>薪水</th>
            <th>獎金</th>
            <th>部門</th>
            <th>修改</th>
            <th>刪除</th>
        </tr>

        <c:forEach var="empVO" items="${listEmps_ByDeptno}">
            <tr align='center' valign='middle'>
                <td>${empVO.empno}</td>
                <td>${empVO.ename}</td>
                <td>${empVO.job}</td>
                <td>${empVO.hiredate}</td>
                <td>${empVO.sal}</td>
                <td>${empVO.comm}</td>
                <td>
                    ${empVO.deptVO.deptno}【<font color=red>${empVO.deptVO.dname}</font> - ${empVO.deptVO.loc}】
                </td>
                <td>
                    <form method="POST" action="${pageContext.request.contextPath}/emp/getOne_For_Update">
                        <input type="submit" value="修改">
                        <input type="hidden" name="empno" value="${empVO.empno}">
                    </form>
                </td>
                <td>
                    <form method="POST" action="${pageContext.request.contextPath}/emp/delete">
                        <input type="submit" value="刪除">
                        <input type="hidden" name="empno" value="${empVO.empno}">
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>
    <br />
    <a href="${pageContext.request.contextPath}/index">回首頁</a>

</body>
</html>
