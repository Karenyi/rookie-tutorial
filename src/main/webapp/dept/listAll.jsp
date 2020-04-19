<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="deptSvc" scope="page" class="org.tutorial.service.impl.DeptServiceImpl"/>
<html>
<head>
    <title>�Ҧ�����</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/table.css">
</head>
<body>
    <h3>�Ҧ�����</h3>
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
            <th>�����s��</th>
            <th>�����W��</th>
            <th>������a</th>
            <th>�ק�</th>
            <th>�R��<font color=red>(���p����)</font></th>
            <th>�d�߳������u</th>
        </tr>

        <c:forEach var="deptVO" items="${deptSvc.all}">
            <tr align='center' valign='middle'>
                <td>${deptVO.deptno}</td>
                <td>${deptVO.dname}</td>
                <td>${deptVO.loc}</td>
                <td>
                    <form method="POST" action="${pageContext.request.contextPath}/dept/dept.do">
                        <input type="submit" value="�ק�">
                        <input type="hidden" name="deptno" value="${deptVO.deptno}">
                        <input type="hidden" name="action" value="getOne_For_Update_Dept">
                    </form>
                </td>
                <td>
                    <form method="POST" action="${pageContext.request.contextPath}/dept/dept.do">
                        <input type="submit" value="�R��">
                        <input type="hidden" name="deptno" value="${deptVO.deptno}">
                        <input type="hidden" name="action" value="delete_Dept">
                    </form>
                </td>
                <td>
                    <form method="POST" action="${pageContext.request.contextPath}/dept/dept.do">
                        <input type="submit" value="�e�X�d��">
                        <input type="hidden" name="deptno" value="${deptVO.deptno}">
                        <input type="hidden" name="action" value="listEmps_ByDeptno_B">
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>
    <br />
    <c:choose>
        <c:when test="${listEmps_ByDeptno != null}">
            <jsp:include page="listEmpsByDeptno.jsp"/>
        </c:when>
        <c:otherwise>
            <br />
            <a href="${pageContext.request.contextPath}/index.jsp">�^����</a>
        </c:otherwise>
    </c:choose>
</body>
</html>
