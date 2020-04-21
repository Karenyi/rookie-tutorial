<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>�������u</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/table.css">
</head>
<body>

    <h3>�������u</h3>
    <c:if test="${not empty errorMsgs}">
        <font color='red'>�Эץ��H�U���~:
            <ul>
                <c:forEach var="message" items="${errorMsgs}">
                    <li>${message}</li>
                </c:forEach>
            </ul>
        </font>
    </c:if>

    <table>
        <tr>
            <th>���u�s��</th>
            <th>���u�m�W</th>
            <th>¾��</th>
            <th>���Τ��</th>
            <th>�~��</th>
            <th>����</th>
            <th>����</th>
            <th>�ק�</th>
            <th>�R��</th>
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
                    ${empVO.deptVO.deptno}�i<font color=red>${empVO.deptVO.dname}</font> - ${empVO.deptVO.loc}�j
                </td>
                <td>
                    <form method="POST" action="${pageContext.request.contextPath}/emp/emp.do">
                        <input type="submit" value="�ק�">
                        <input type="hidden" name="empno" value="${empVO.empno}">
                        <input type="hidden" name="action" value="getOne_For_Update">
                    </form>
                </td>
                <td>
                    <form method="POST" action="${pageContext.request.contextPath}/emp/emp.do">
                        <input type="submit" value="�R��">
                        <input type="hidden" name="empno" value="${empVO.empno}">
                        <input type="hidden" name="action" value="delete">
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>
    <br />
    <a href="${pageContext.request.contextPath}/index.jsp">�^����</a>

</body>
</html>
