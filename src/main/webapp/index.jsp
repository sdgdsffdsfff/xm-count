<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>通用更新管理后台</title>
    <link href="${pageContext.request.contextPath}/resources/css/style.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery.min.js"></script>
</head>
<body class="loginbg">
<div class="login_box">
    <div class="title"><img src="${pageContext.request.contextPath}/resources/images/title.png"/></div>
    <form method="post" action="/usr/resources/login">
        <div class="login_div">
            <div class="login_name"><input type="text" name="username"/></div>
            <div class="login_passw"><input type="password" name="password"/></div>
        </div>
        <div class="login_btn"><input type="submit" name="button" id="button" value="登录"/></div>
    </form>
</div>
</body>
</html>
