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
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap-clockpicker.min.css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/ui/locale/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/ui/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery.reveal.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/page/jquery.pager.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/form/task.js"></script>
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
                    <li><a href="/task/list?bid=${bz.id}" <c:if test="${bid==bz.id}">class="on" </c:if> > <span>${bz.name}</span></a></li>
                </c:forEach>
            </ul>
        </div>
    </div>
    <div class="con_box" id="scroll">
        <jsp:include page="top.jsp">
            <jsp:param name="index" value="2"/>
        </jsp:include>
        <div class="add_div">
            <c:if test="${fn:contains(code, ',C,')}">
                <a href="javascript:void (0)" data-reveal-id="addWindow" data-animation="fade" class="btn">添加任务</a>
            </c:if>
        </div>
        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="list">
            <thead>
            <tr>
                <td width="5%">序号</td>
                <td width="10%">任务名称</td>
                <td width="10%">任务类型</td>
                <td width="10%">执行模式</td>
                <td width="10%">执行结果</td>
                <td width="10%">任务状态</td>
                <td width="10%">操作</td>
            </tr>
            </thead>
            <tbody>

            <c:forEach items="${taskList}" var="task" varStatus="state">
                <c:if test="${state.index%2==0}"><tr class="tr1"></c:if>
                <c:if test="${state.index%2==1}"><tr class="tr2"></c:if>
                <td>${state.index+1}</td>
                <td>${task.name}</td>
                <td>
                    <c:choose>
                        <c:when test="${task.type==1}">普通任务</c:when>
                        <c:otherwise>脚本任务</c:otherwise>
                    </c:choose>
                </td>
                <td>
                    <c:choose>
                        <c:when test="${task.execute==1}">立即执行</c:when>
                        <c:otherwise>定时执行</c:otherwise>
                    </c:choose>
                </td>
                <td>
                    <c:choose>
                        <c:when test="${task.model==1}">覆盖写入</c:when>
                        <c:otherwise>增量写入</c:otherwise>
                    </c:choose>
                </td>
                <td>
                    <c:choose>
                        <c:when test="${task.state==1}"><span style="color: #46c5e4;">启动</span></c:when>
                        <c:otherwise><span style="color: #d2d3d8;">停止</span></c:otherwise>
                    </c:choose>
                </td>
                <td class="operate">
                    <c:if test="${fn:contains(code, ',U,')}">
                    <a href="javascript:void (0)" data-reveal-id="addWindow" data-animation="fade" class="edit" data="${task.id}">编辑</a>
                    </c:if>
                    <c:if test="${fn:contains(code, ',D,')}">
                    <a href="javascript:void (0)" data-reveal-id="delWindow" data-animation="fade" class="delete" key="${task.id}">删除</a>
                    </c:if>
                </td>
                </tr>
            </c:forEach>

            </tbody>
            <tfoot>
            </tfoot>
        </table>
        <div class="page_box">
            <div id="pager" pagenumber="${pagenumber}" pagecount="${pagecount}" url="/task/list?bid=${bid}"></div>
        </div>
    </div>
</div>

<!--新增弹出层开始-->
<div class="reveal-modal3" id="addWindow" >
    <div class="modal_title"><span>新增查询任务</span></div>
    <%--<a class="close-reveal-modal" >×</a>--%>
    <!--<div class="form_tip">表单错误提示</div>-->
    <div class="formbox" >
        <form method="post" action="/task/add" id="add" enctype="multipart/form-data">
            <div class="r_form clearfix"><label class="labels_w">任务名称：</label>

                <div class="input_w">
                    <input type="text" name="name" id="name" class="inputs"/>
                </div>
            </div>

            <div class="r_form clearfix"><label class="labels_w">任务类型：</label>

                <div class="input_w">
                    <span><input type="radio" name="type" value="1"  checked="checked"/> sql任务</span>
                    <span><input type="radio" name="type" value="2" />python任务</span>
                </div>
            </div>

            <div class="r_form clearfix" id="sqlinput"><label class="labels_w">执行sql：</label>

                <div class="input_w">
                    <textarea name="sql" id="sql" cols="50" rows="10" style="width:550px;"></textarea>
                </div>
            </div>

            <div class="r_form clearfix" id="pythoninput" ><label class="labels_w">执行python：</label>

                <div class="input_w">
                    <input type="text" class="inputs easyui-filebox" name="file" data-options="prompt:'选择python文件...',buttonText:'浏览'" style="height: 30px;" />
                    <input type="hidden" name="url" id="url"/>
                </div>

            </div>

            <div class="r_form clearfix"><label class="labels_w">终端收益：</label>

                <div class="input_w">
                    <input type="text" name="plus" id="plus" class="inputs"/>
                </div>
            </div>
            <div class="r_form clearfix"><label class="labels_w">排序：</label>

                <div class="input_w">
                    <input type="text" name="orderNo" id="orderNo" class="inputs"/>
                </div>
            </div>
            <div class="r_form clearfix">
                <label class="labels_w">执行模式：</label>

                <div class="input_w">
                    <span><input type="radio" name="execute" value="1" onclick="bb.style.display='none'"  checked="checked"/> 立即执行</span><br/>
                    <span><input type="radio" name="execute" value="2" onclick="bb.style.display='block'"/> 定时执行(每天)</span>
                  <span id="bb">
                    <div class="w_add">
                        <input type="text" id="timer" class="inputs time clockpicker" data-placement="left" data-align="top" data-autoclose="true" />
                        <a href="javascript:void (0)" onclick="addTimer()">+添加</a>
                        <a href="javascript:void (0)" class="all_del" onclick="$('tbody').empty()">全部清除</a>
                    </div>
                    <div class="w_scoll">
                        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="w_table">
                            <thead>
                            <th width="50%">时间（时分）</th>
                            <th>操作</th>
                            </thead>
                            <tbody id="tbody">
                            </tbody>
                        </table>
                    </div>
                  </span>
                </div>

            </div>

            <div class="r_form clearfix"><label class="labels_w">执行结果：</label>

                <div class="input_w">
                    <span><input type="radio" name="model" value="1" checked="checked"/> 覆盖保存</span>
                    <span><input type="radio" name="model" value="2"/>增量保存</span>
                </div>
            </div>

            <div class="r_form clearfix"><label class="labels_w">任务状态：</label>

                <div class="input_w">
                    <span><input type="radio" name="state" value="1" checked="checked"/> 启动</span>
                    <span><input type="radio" name="state" value="2"/>停止</span>
                </div>
            </div>
            <input type="hidden" name="businessId" value="${bid}"/>
            <input type="hidden" name="id" id="id" />
            <input type="hidden" name="test" id="test" value="1" /><%--判断当前提交是测试提交 还是报错提交 0=测试提交 --%>
            <%--<div class="r_form clearfix"><label class="labels_w">每终端收益：</label><div class="input_w"><input type="text" name="income" id="income" class="inputs w_155"/> 元/天</div></div>--%>
            <div class="r_btn">
                <a href="javascript:void (0)" onclick="saveAndTest()"  class="btn">保存并测试</a>
                <a href="javascript:void (0)" onclick="$('#add').submit()" class="btn">保存</a>
                <a href="#" class="btn close-reveal">关闭</a>
            </div>
        </form>
    </div>
</div>
<!--新增弹出层结束-->
<!--删除弹出层开始-->
<div class="reveal-modal2" id="delWindow">
    <div class="del_text clearfix">
        <span>数据删除后将无法恢复，您是否确定要继续执行删除操作？</span>
        <form method="post" action="/task/delete" id="delete">
            <input type="hidden" name="key" id="keys"/>
            <input type="hidden" name="bid" value="${bid}"/>
        </form>
    </div>
    <div class="operation">
        <a href="javascript:void (0)" class="btn add_btn" onclick="document.getElementById('delete').submit()">确认</a>
        <a href="javascript:void (0)" class="btn del_btn close-reveal">取消</a>
    </div>
</div>

<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/bootstrap-clockpicker.min.js"></script>
<script type="text/javascript">
    $('.clockpicker').clockpicker()
            .find('input').change(function () {
                console.log(this.value);
            });
    var input = $('#single-input').clockpicker({
        placement: 'bottom',
        align: 'left',
        autoclose: true,
        'default': 'now'
    });

    // Manually toggle to the minutes view
    $('#check-minutes').click(function (e) {
        // Have to stop propagation here
        e.stopPropagation();
        input.clockpicker('show')
                .clockpicker('toggleView', 'minutes');
    });
    if (/mobile/i.test(navigator.userAgent)) {
        $('input').prop('readOnly', true);
    }
</script>
</body>
</html>
