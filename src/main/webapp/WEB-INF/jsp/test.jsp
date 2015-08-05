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
    <div class="con_box" id="scroll" style="width: 100%;">
            <c:if test="${plus>0}">
                <div class="count">【共收益金额：${plus} 元】</div>
            </c:if>
        <c:if test="${toolbar==1}">
        <div class="search_box">
            <form action="/task/testView?tid=${tid}" method="post" id="s">
            <c:if test="${ipbar==1}">
                <span>区域:<input class="easyui-combotree" data-options="url:'/task/getRegion',method:'get'" style="width:200px;height: 25px;" name="ip" value="${ip}"/> </span>
            </c:if>
            <c:if test="${agentbar==1}">
                <span>代理:<input class="easyui-combotree" data-options="url:'/task/getAgent',method:'get'" style="width:200px;height: 25px;" name="agentid" value="${agentid}" /> </span>
            </c:if>
            <c:if test="${verbar==1}">
                 <span>版本:<input class="easyui-textbox" style="width:200px;height: 25px;" name="ver" value="${ver}" /> </span>
            </c:if>
            <c:if test="${timebar==1}">
                  <span>时间:<input class="easyui-datebox" style="width:150px;height: 25px;" name="start" value="${start}">~<input class="easyui-datebox" style="width:150px;height: 25px;" name="end" value="${end}"></span>
            </c:if>
            <span><a href="javascript:void (0)" class="search" onclick="s.submit()">查询</a></span>
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
            <div id="pager" pagenumber="${pagenumber}" pagecount="${pagecount}"  url="/task/testView?tid=${tid}&ip=${ip}&agentid=${agentid}&ver=${ver}&start=${start}&end=${end}"></div>
        </div>
    </div>
</div>
<div style="display: none;"><form action="/task/testView" id="rf"><input name="name" value="${name}"/><input name="tid" value="${tid}"/></form></div>
</body>
</html>
