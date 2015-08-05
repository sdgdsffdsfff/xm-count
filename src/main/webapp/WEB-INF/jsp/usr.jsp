<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>数据查询后台</title>
    <link href="${pageContext.request.contextPath}/resources/css/style.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery.reveal.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/page/jquery.pager.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/form/usr.js"></script>
    <script language="javascript">
        var ss;
        window.onload = function () {
//var w=document.documentElement.clientWidth ;//可见区域宽度
            var h = document.documentElement.clientHeight;//可见区域高度
            ss = document.getElementById('scroll');
//alert(w);
//ss.style.width=w+"px";
            ss.style.height = h + "px";
        }
    </script>
</head>

<body>
<div class="clearfix">
    <div class="left_box">
        <div class="logo"><img src="${pageContext.request.contextPath}/resources/images/logo.png"/></div>
        <div class="vmenu">
            <ul>
                <c:forEach items="${businessList}" var="bz" varStatus="state">
                    <li><a href="/bz/list?bid=${bz.id}"> <span>${bz.name}</span></a></li>
                </c:forEach>
            </ul>
        </div>
    </div>
    <div class="con_box" id="scroll">
        <jsp:include page="top.jsp">
            <jsp:param name="index" value="5"/>
        </jsp:include>
        <div class="add_div"><a href="javascript:void (0)" data-reveal-id="addWindow" data-animation="fade" class="btn">添加用户</a>
        </div>
        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="list">
            <thead>
            <tr>
                <td width="10%">序号</td>
                <td width="">用户名</td>
                <td width="30%">登录名</td>
                <td width="20%">操作</td>
            </tr>
            </thead>
            <tbody>


            <c:forEach items="${userList}" var="usr" varStatus="state">
                <c:if test="${state.index%2==0}"> <tr class="tr1"></c:if>
                <c:if test="${state.index%2==1}"> <tr class="tr2"></c:if>
                <td>${state.index+1}</td>
                <td>${usr.nickname}</td>
                <td>${usr.username}</td>
                <td class="operate" style="text-align: left;">
                    <c:if test="${usr.username!='admin'}">
                        <a href="javascript:void (0)" data-reveal-id="authorityWindow" data-animation="fade" class="authority" auth-key="${usr.id}">权限设置</a>
                    </c:if>
                    <a href="javascript:void (0)" data-reveal-id="passwordWindow" data-animation="fade" class="password"
                       key="${usr.id}">密码修改</a>
                    <a href="javascript:void (0)" data-reveal-id="updateWindow" data-animation="fade" class="edit"
                       update-key="${usr.id}">编辑</a>
                    <a href="/usr/delete?ids=${usr.id}" class="delete">删除</a>
                </td>
                </tr>
            </c:forEach>


            </tbody>
            <tfoot>
            </tfoot>
        </table>
        <div class="page_box">
            <div id="pager" pagenumber="${pagenumber}" pagecount="${pagecount}" url="/usr/list"></div>
        </div>
    </div>
</div>

<!--新增弹出层开始-->
<div class="reveal-modal" id="addWindow">
    <div class="modal_title">用户管理 - <span>新增</span></div>
    <div class="formbox">
        <form method="post" action="/usr/add" id="add">
            <div class="r_form clearfix"><label class="labels_w">用户名：</label>

                <div class="input_w"><input type="text" name="nickname" class="inputs"/></div>
            </div>
            <div class="r_form clearfix"><label class="labels_w">登录名：</label>

                <div class="input_w"><input type="text" name="username" class="inputs"/></div>
            </div>
            <div class="r_form clearfix"><label class="labels_w">密码设置：</label>

                <div class="input_w"><input type="password" name="password" id="password" class="inputs"></div>
            </div>
            <div class="r_form clearfix"><label class="labels_w">密码确认：</label>

                <div class="input_w"><input type="password" name="confirmpsw" id="confirmpsw" class="inputs"></div>
            </div>
            <div class="r_btn clearfix">
                <a href="javascript:void(0)" class="btn add_btn" onclick="add()">保存</a>
                <a href="javascript:void(0)" class="btn del_btn close-reveal">关闭</a>
            </div>
        </form>
    </div>
</div>
<div class="reveal-modal" id="updateWindow">
    <div class="modal_title">用户管理 - <span>编辑</span></div>
    <div class="formbox">
        <form method="post" action="/usr/add" id="update">
            <div class="r_form clearfix"><label class="labels_w">用户名：</label>

                <div class="input_w">
                    <input type="text" name="nickname" id="nickname" class="inputs"/>
                    <input type="hidden" name="id" id="id"/></div>
            </div>
            <div class="r_form clearfix"><label class="labels_w">登录名：</label>

                <div class="input_w">
                    <input type="text" name="username" id="username" class="inputs"/>
                </div>
            </div>
            <div class="r_btn clearfix">
                <a href="javascript:void(0)" class="btn add_btn"
                   onclick="document.getElementById('update').submit()">保存</a>
                <a href="javascript:void(0)" class="btn del_btn close-reveal">关闭</a>
            </div>
        </form>
    </div>
</div>
<!--新增弹出层结束-->

<!--权限设置弹出层开始-->
<div class="reveal-modal" id="authorityWindow">
    <div class="modal_title">权限管理 - <span>权限设置</span></div>
    <a class="close-reveal-modal">×</a>
    <!--<div class="form_tip">表单错误提示</div>-->
    <div class="formbox authority_style">
        <form method="post" action="/usr/auths" id="auths">
            <div class="r_form clearfix"><label class="labels_w">栏目配置：</label>
                <div class="input_w">
                    <input type="hidden" id="auth_key" name="id"/>
                    <c:forEach items="${rsList}" var="rs">
                            <span><input type="checkbox" name="rs" value="${rs.id}"/> ${rs.name}</span>
                    </c:forEach>
                </div>
            </div>
            <div class="r_form clearfix"><label class="labels_w">操作配置：</label>
                <div class="input_w">
                    <c:forEach items="${businessList}" var="bz">
                        <span style="width: 80px">${bz.name}</span>
                        <span>增:<input type="checkbox" name="bz${bz.id}" value="C"/></span>
                        <span>删:<input type="checkbox" name="bz${bz.id}" value="U"/> </span>
                        <span>改:<input type="checkbox" name="bz${bz.id}" value="D"/></span>
                        <span>查:<input type="checkbox" name="bz${bz.id}" value="R"/></span><br>
                    </c:forEach>
                </div>
            </div>
            <div class="r_btn clearfix">
                <a href="javascript:void (0)" class="btn add_btn" onclick="document.getElementById('auths').submit()">保存</a>
                <a href="javascript:void (0)" class="btn del_btn close-reveal" >关闭</a>
            </div>
        </form>
    </div>
</div>
<!--权限设置弹出层开始-->


</body>
</html>
