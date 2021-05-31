<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Bye Page!</title>
</head>
<body>
<div>Martial Arts - Bye Page</div>
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
            <td>Login</td>
            <td>Gender</td>
            <td>Weight</td>
            <td>Is Deleted</td>
            <td>Created</td>
            <td>Changed</td>
            <td>Birth date</td>
            <td>Edit</td>
            <td>Delete</td>
        </tr>
        <%--        Show all users from collection of elements by jstl--%>
        <c:forEach var="user" items="${users}">
            <tr>
                <td>${user.id}</td>
                <td>${user.name}</td>
                <td>${user.surname}</td>
                <td>${user.login}</td>
                <td>${user.gender}</td>
                <td>${user.weight}</td>
                <td>${user.isDeleted}</td>
                <td>${user.created}</td>
                <td>${user.changed}</td>
                <td>${user.birthDate}</td>
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
