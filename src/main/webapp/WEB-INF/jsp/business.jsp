<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>数据查询后台</title>
    <link href="${pageContext.request.contextPath}/resources/css/style.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery.reveal.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/page/jquery.pager.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/form/busniss.js"></script>
    <script type="text/javascript">
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
                <c:forEach items="${list}" var="bz" varStatus="state">
                    <li><a href="javascript:void (0)"><span>${bz.name}</span></a></li>
                </c:forEach>
            </ul>
        </div>
    </div>
    <div class="con_box" id="scroll">
        <jsp:include page="top.jsp">
            <jsp:param name="index" value="1"/>
        </jsp:include>
        <div class="add_div"><a href="javascript:void (0)" data-reveal-id="addWindow" data-animation="fade" class="btn">添加业务</a>
        </div>
        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="list">
            <thead>
            <tr>
                <td width="10%">序号</td>
                <td width="30%">名称</td>
                <td width="10%">排序</td>
                <td width="20%">操作</td>
            </tr>
            </thead>
            <tbody>

            <c:forEach items="${list}" var="bz" varStatus="state">
                <c:if test="${state.index%2==0}"><tr class="tr1"></c:if>
                <c:if test="${state.index%2==1}"><tr class="tr2"></c:if>
                    <td>${state.index+1}</td>
                    <td>${bz.name}</td>
                    <td>${bz.orderNo}</td>
                    <td class="operate">
                        <a href="javascript:void (0)" data-reveal-id="addWindow" data-animation="fade" class="edit" data="${bz.id}#${bz.name}#${bz.orderNo}">编辑</a>
                        <a href="javascript:void (0)" data-reveal-id="delWindow" data-animation="fade" class="delete" key="${bz.id}">删除</a>
                    </td>
                </tr>
            </c:forEach>

            </tbody>
            <tfoot>
            </tfoot>
        </table>
        <div class="page_box">
            <div id="pager" pagenumber="${pagenumber}" pagecount="${pagecount}" url="/bz/list"></div>
        </div>
    </div>
</div>

<!--新增弹出层开始-->
<div class="reveal-modal" id="addWindow">
    <div class="modal_title">业务分类管理 - <span>新增业务分类</span></div>
    <%--<a class="close-reveal-modal" >×</a>--%>
    <!--<div class="form_tip">表单错误提示</div>-->
    <div class="formbox">
        <form method="post" action="/bz/add" id="add">
            <div class="r_form clearfix"><label class="labels_w">名称：</label>
                <div class="input_w">
                    <input type="hidden" name="id" id="id" class="inputs"/>
                    <input type="text" name="name" id="name" class="inputs"/>
                </div>
            </div>
            <div class="r_form clearfix"><label class="labels_w">排序：</label>
                <div class="input_w">
                    <input type="text" name="orderNo" id="orderNo" class="inputs"/>
                </div>
            </div>
            <div class="r_btn">
                <a href="javascript:void (0)" class="btn" onclick="document.getElementById('add').submit()">保存</a>
                <a href="javascript:void (0)" class="btn close-reveal">关闭</a>
            </div>
        </form>
    </div>
</div>
<!--新增弹出层结束-->

<!--删除弹出层开始-->
<div class="reveal-modal2" id="delWindow">
    <div class="del_text clearfix">
        <span>数据删除后将无法恢复，您是否确定要继续执行删除操作？</span>
       <form method="post" action="/bz/delete" id="delete"> <input type="hidden" name="key" id="keys"/></form>
    </div>
    <div class="operation">
        <a href="javascript:void (0)" class="btn add_btn" onclick="document.getElementById('delete').submit()">确认</a>
        <a href="javascript:void (0)" class="btn del_btn close-reveal">取消</a>
    </div>
</div>
<!--删除弹出层开始-->
</body>
</html>
