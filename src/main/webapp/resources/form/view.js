/**
 * Created by lijie on 2015-07-20.
 */

var setIn;

var ajaxRequestWaitState = function () {
    $.ajax({
        type: 'post',
        url: "/task/getWaitState",
        data: {taskId: tid},
        dataType: 'json',
        success: function (data) {

            if (data == "0") {
                if (setIn) {
                    clearTimeout(setIn);
                    $("#rf").submit();
                }
                return;
            }

            if (data == "2") {
                alert("定时任务还未执行");
                return;
            }

            if (data == "1") {
                $('<div class="datagrid-mask-msg" style="display:block;left:50%">数据正在计算,请稍候......</div>').appendTo("table");
                setIn = setTimeout('ajaxRequestWaitState()', 10000);

            }
        }
    });
};

$(document).ready(function () {
    ajaxRequestWaitState();
});



