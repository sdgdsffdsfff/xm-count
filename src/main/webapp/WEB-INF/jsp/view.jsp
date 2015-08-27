<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
      var tid='${tid}';
        window.onload = function () {
            var h = document.documentElement.clientHeight;//可见区域高度
            document.getElementById('scroll').style.height =h + "px";
        };
    </script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/form/view.js"></script>
</head>

<body>
<div class="clearfix">
    <div class="left_box">
        <div class="logo"><img src="${pageContext.request.contextPath}/resources/images/logo.png"/></div>
        <div class="vmenu">
            <ul>
                <c:forEach items="${businessList}" var="bz" varStatus="state">
                    <li><a href="/task/view?bid=${bz.id}"  <c:if test="${bid==bz.id}">class="on" </c:if> > <span>${bz.name}</span></a></li>
                </c:forEach>
            </ul>
        </div>
    </div>
    <div class="con_box" id="scroll">
        <jsp:include page="top.jsp">
            <jsp:param name="index" value="3"/>
        </jsp:include>
        <%--<div class="add_div"><a href="#" data-reveal-id="addWindow" data-animation="fade" class="btn">添加查询项</a><a href="#" class="btn">查询项管理</a></div>--%>
        <div class="nav">
            <c:forEach items="${taskList}" var="task">
                <a href="/task/view?bid=${bid}&tid=${task.id}" <c:if test="${tid==task.id}"> class="on" </c:if>  >${task.name}</a>
            </c:forEach>
            <c:if test="${plus>0}">
                <div class="count">【共收益金额：${plus} 元】</div>
            </c:if>
        </div>
        <c:if test="${toolbar==1}">
        <div class="search_box">
            <form action="/task/view?bid=${bid}&tid=${tid}" method="post" id="s">
            <c:if test="${ipbar==1}">
                <span>区域:<input class="easyui-combotree" data-options="url:'/task/getRegion',method:'get'" style="width:200px;height: 25px;" name="ip" value="${ip}"/> </span>
            </c:if>
            <c:if test="${agentbar==1}">
                <span>代理:<input class="easyui-combotree" data-options="url:'/task/getAgent',method:'get'" style="width:200px;height: 25px;" name="agentid" value="${agentid}" /> </span>
            </c:if>
            <c:if test="${verbar==1}">
                 <span>版本:<input class="easyui-combotree" data-options="url:'/task/getVer?tid=${tid}',method:'get'" style="width:200px;height: 25px;" name="ver" value="${ver}" /> </span>
            </c:if>
            <c:if test="${userIdbar==1}">
                <span>网吧:<input class="easyui-numberbox" style="width:200px;height: 25px;" name="userId" value="${userId}" /> </span>
            </c:if>
            <c:if test="${timebar==1}">
                  <span>时间:<input class="easyui-datebox" style="width:150px;height: 25px;" name="start" value="${start}">~<input class="easyui-datebox" style="width:150px;height: 25px;" name="end" value="${end}"></span>
            </c:if>
            <span>
                <a href="javascript:void (0)" class="search" onclick="s.submit()">查询</a>
                <a href="/task/excel?tid=${tid}" class="search" >导出</a>
            </span>
            </form>
        </div>
        </c:if>
        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="list" id="table" >
            <thead>
            <tr>
                <td width="3%">序号</td>
                <c:forEach items="${hlist}" var="column">
                    <td width="8%" class="${column}">${column}</td>
                </c:forEach>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${list}" var="map" varStatus="state">
                <c:if test="${state.index%2==0}"><tr class="tr1"></c:if>
                <c:if test="${state.index%2==1}"><tr class="tr2"></c:if>
                <td>${state.index+1}</td>
                <c:forEach items="${map}" var="entry">
                    <td class="${entry.key}">${entry.value}</td>
                </c:forEach>
                </tr>
            </c:forEach>
            </tbody>
            <tfoot>
            </tfoot>
        </table>
        <div class="page_box">
            <div id="pager" pagenumber="${pagenumber}" pagecount="${pagecount}"  url="/task/view?bid=${bid}&tid=${tid}&ip=${ip}&agentid=${agentid}&ver=${ver}&start=${start}&end=${end}"></div>
        </div>
    </div>
</div>
<div style="display: none;"><form action="/task/view" id="rf"><input name="bid" value="${bid}"/><input name="tid" value="${tid}"/></form></div>
</body>
</html>
