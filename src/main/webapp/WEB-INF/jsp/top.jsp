<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="top_div clearfix">
    <div class="exit">您好！<span>${sessionScope.currentLoginUser.username}</span>
        <a href="javascript:void (0)" data-reveal-id="passwordWindow" data-animation="fade" >修改密码</a>
        <a href="/usr/logout">退出登录</a>
    </div>
    <ul class="menu clearfix">
        <c:forEach items="${rsList}" var="rs">
            <li><a href="${rs.url}" <c:if test="${param.index==rs.orderNo}" >class="on"</c:if> ><i class="${rs.cls}"></i>${rs.name}</a></li>
        </c:forEach>
    </ul>
</div>

<div class="reveal-modal" id="passwordWindow" >
    <div class="modal_title">用户管理 - <span>密码修改</span></div>
    <div class="formbox">
        <form method="post" id="sf">
            <div class="r_form clearfix"><label class="labels_w">原密码：</label>
                <input type="password" name="oldpassword" id="oldpassword" class="inputs"/>
            </div>
            <div class="r_form clearfix"><label class="labels_w">新密码：</label>
                <input type="password" name="newpassword" id="newpassword" class="inputs"/>
            </div>
            <div class="r_form clearfix"><label class="labels_w">确认密码：</label>
                <input type="password" name="confirmpassword" id="confirmpassword" class="inputs">
                <input type="hidden" name="id" id="key" value="${sessionScope.currentLoginUser.id}"/>
            </div>
            <div class="r_btn clearfix">
                <a href="javascript:void (0)" class="btn add_btn" onclick="sf()">保存</a>
                <a href="javascript:void (0)" class="btn del_btn close-reveal">关闭</a>
            </div>
        </form>
    </div>
</div>
<script type="text/javascript">
    /*
     * 密码修改
     * */
    function sf() {
        var oldpassword = $("#oldpassword").val();
        var newpassword = $("#newpassword").val();
        var confirmpassword = $("#confirmpassword").val();
        if (oldpassword == "" || newpassword == "" || confirmpassword == "") {
            return;
        }
        if (newpassword != confirmpassword) {
            alert("两次输入密码不一致!");
        } else {
            var $sf = $('#sf');
            $.ajax({
                cache: true,
                type: "POST",
                url: "/usr/update?" + $sf.serialize(),
                async: false,
                error: function (request) {
                    alert("Connection error");
                },
                success: function (data) {
                    if (data == "error") {
                        alert("原密码不正确!")
                    } else {
                        $(".close-reveal").trigger("click");
                        alert("密码修改成功!");
                        $sf.get(0).reset();
                    }
                }
            });
        }
    }
</script>