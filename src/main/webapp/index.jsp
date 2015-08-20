<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>通用更新管理后台</title>
    <link href="${pageContext.request.contextPath}/resources/css/style.css" rel="stylesheet" type="text/css"/>
    <link href="${pageContext.request.contextPath}/resources/ui/themes/gray/easyui.css" rel="stylesheet" type="text/css"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/ui/themes/icon.css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/ui/jquery.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/ui/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/ui/locale/easyui-lang-zh_CN.js"></script>
</head>
<body class="easyui-layout" >

<div data-options="region:'center',border:false" style="background:url(/resources/images/login_bg.jpg) top center no-repeat;};">
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
</div>

</body>
</html>
