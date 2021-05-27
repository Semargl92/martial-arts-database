<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Bye Page!</title>
</head>
<body>
<div>Students Demo - Bye Page</div>
</body>

<div>
    <h1>System Users</h1>
</div>
<div>
    <table>
        <tr>
            <td>User Id</td>
            <td>User Name</td>
            <td>User Surname</td>
            <td>Birth date</td>
            <td>Login</td>
            <td>Weight</td>
            <td>Edit</td>
            <td>Delete</td>
        </tr>
        <%--        Show all users from collection of elements by jstl--%>
        <c:forEach var="user" items="${users}">
            <tr>
                <td>${user.id}</td>
                <td>${user.name}</td>
                <td>${user.surname}</td>
                <td>${user.birthDate}</td>
                <td>${user.login}</td>
                <td>${user.weight}</td>
                <td>
                    <button>Edit</button>
                </td>
                <td>
                    <button>Delete</button>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>
<div>
    ${singleUser}
</div>
</html>
