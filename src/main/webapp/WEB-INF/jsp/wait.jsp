<%--
  Created by IntelliJ IDEA.
  User: lijie
  Date: 2015-07-28
  Time: 下午 5:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>数据查询后台</title>
    <link href="${pageContext.request.contextPath}/resources/ui/themes/icon.css" rel="stylesheet" type="text/css"/>
    <link href="${pageContext.request.contextPath}/resources/ui/themes/gray/easyui.css" rel="stylesheet"
          type="text/css"/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery.min.js"></script>
</head>
<body>
<div style="display: none;">
    <form action="/task/testView" method="post" id="test">
        <input type="hidden" name="name" value="${name}">
    </form>
</div>
<script type="text/javascript">
    var name = "${name}";

    var waitState = function () {
        $.ajax({
            type: 'post',
            url: "/task/check",
            data: {name: name},
            contentType: "application/x-www-form-urlencoded; charset=UTF-8",
            success: function (data) {

                if (data == "success") {

                    $("#test").submit();

                } else {
                    $('<div class="datagrid-mask-msg" style="display:block;left:40%">正在创建测试表,请稍后......</div>').appendTo("body");
                    setTimeout('waitState()', 10000);
                }

            }
        });
    };

    $(function () {
        waitState();
    });

</script>
</body>
</html>
