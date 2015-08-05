<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>数据查询后台</title>
    <link href="${pageContext.request.contextPath}/resources/css/style.css" rel="stylesheet" type="text/css"/>
    <link href="${pageContext.request.contextPath}/resources/ui/themes/icon.css" rel="stylesheet" type="text/css"/>
    <link href="${pageContext.request.contextPath}/resources/ui/themes/gray/easyui.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery.reveal.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/page/jquery.pager.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/ui/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/ui/locale/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript">
        var ss;
        window.onload = function () {
            var h = document.documentElement.clientHeight;//可见区域高度
            ss = document.getElementById('scroll');
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
                    <li><a href="/task/view?bid=${bz.id}"  > <span>${bz.name}</span></a></li>
                </c:forEach>
            </ul>
        </div>
    </div>
    <div class="con_box" id="scroll">
        <jsp:include page="top.jsp">
            <jsp:param name="index" value="4"/>
        </jsp:include>
        <div class="add_div">
        </div>
        <div class="search_box">
            <form action="/his/list" method="post" id="s">
                <span>任务名称:<input class="easyui-textbox" style="width:150px;height: 25px;" name="name" value="${name}"/> </span>
                <span>开始时间:<input class="easyui-datebox" style="width:150px;height: 25px;" name="start" value="${start}">
                    ~<input class="easyui-datebox" style="width:150px;height: 25px;" name="end" value="${end}">
                </span>
                <span><a href="javascript:void (0)" class="search" onclick="s.submit()">查询</a></span>
            </form>
        </div>
        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="list">
            <thead>
            <tr>
                <td width="3%">序号</td>
               <td width="5%">任务名称</td>
               <td width="5%">任务类型</td>
               <td width="20%">执行语句/脚本地址</td>
               <td width="5%">执行模式</td>
               <td width="5%">执行结果</td>
               <td width="10%">开始时间</td>
               <td width="5%">任务用时</td>
               <td width="5%">执行状态</td>
               <td width="5%">重试次数</td>
               <td width="5%">操作</td>
            </tr>
            </thead>
            <tbody>


            <c:forEach items="${historyList}" var="history" varStatus="state">
                <c:if test="${state.index%2==0}"><tr class="tr1"></c:if>
                <c:if test="${state.index%2==1}"><tr class="tr2"></c:if>
                <td>${state.index+1}</td>
                <td>${history.task.name}</td>

                <c:choose>
                    <c:when test="${history.task.type==1}">
                        <td>普通任务</td>
                        <td><input value="${history.task.sql}" style="width: 400px;height: 25px;"/></td>
                    </c:when>
                    <c:otherwise>
                        <td>脚本任务</td>
                        <td><input value="${history.task.url}" style="width: 400px;height: 25px;"/></td>
                    </c:otherwise>
                </c:choose>

                <td>
                    <c:choose>
                        <c:when test="${history.task.execute==2}">定时执行</c:when>
                        <c:otherwise>立即执行</c:otherwise>
                    </c:choose>
                </td>
                <td>
                    <c:choose>
                        <c:when test="${history.task.model==1}">覆盖写入</c:when>
                        <c:otherwise>增量写入</c:otherwise>
                    </c:choose>
                </td>
                <td>${fn:substring(history.start, 0, 19)}</td>
                <td>${history.time}(s)</td>
                <td>
                    <c:choose>
                        <c:when test="${history.state==2}"><span style="color:#2fbee1;" >正在执行</span></c:when>
                        <c:when test="${history.state==1}">执行成功</c:when>
                        <c:otherwise><span style="color:red;" >执行失败</span></c:otherwise>
                    </c:choose>
                </td>
                <td>${history.times}</td>
                <td>
                    <c:if test="${history.state==0}">
                        <a href="javascript:void (0)" class="delete" title="${history.error}" onclick="javascript:alert(this.title)">查看原因</a>
                    </c:if>
                </td>
                </tr>
            </c:forEach>


            </tbody>
            <tfoot>
            </tfoot>
        </table>
        <div class="page_box">
            <div id="pager" pagenumber="${pagenumber}" pagecount="${pagecount}"  url="/his/list?name=${name}&start=${start}&end=${end}"></div>
        </div>
    </div>
</div>
</body>
</html>
