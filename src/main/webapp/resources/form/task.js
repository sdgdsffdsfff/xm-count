/**
 * Created by lijie on 2015-07-10.
 */
function addTimer() {
    var val = $("#timer").val();
    if (val) {
        $("#tbody").append($('<tr><td><input type="hidden" name="timer" value="' + val + '"/>' + val + '</td><td><a href="javascript:void(0)" onclick="$(this).parent().parent().remove()">删除</a></td></tr>'));
    }
}

$.fn.echo = function ($this) {
    /**
     * 删除
     */
    var key = $this.attr("key");
    if (key) {
        $("#keys").val(key);
    }

    /*
     * 保存提交
     * */
    $("#test").val("1");

    /**
     * data="${task.id}"
     * 编辑
     */
    var id = $this.attr("data");
    if (id) {
        $.ajax({
            type: 'post',
            url: "/task/ajax",
            data: {id: id},
            dataType: 'json',
            success: function (data) {
                $("#id").val(data.id);
                $("#name").val(data.name);
                $("#orderNo").val(data.orderNo);
                $("#plus").val(data.plus);
                $("#sql").val(data.sql);
                $("#url").val(data.url);

                if (data.execute == 2) {
                    $("input[name='execute'][value='2']").attr("checked", true).trigger("click");
                    $("#bb").css("display", "block");
                } else {
                    $("input[name='execute'][value='1']").attr("checked", true);
                    $("#bb").css("display", "none");
                }

                if (data.model == 2) {
                    $("input[name='model'][value='2']").attr("checked", true);
                } else {
                    $("input[name='model'][value='1']").attr("checked", true);
                }

                if (data.state == 2) {
                    $("input[name='state'][value='2']").attr("checked", true);
                } else {
                    $("input[name='state'][value='1']").attr("checked", true);
                }

                if (data.type == 2) {
                    $("input[name='type'][value='2']").attr("checked", true).trigger("click");
                } else {
                    $("input[name='type'][value='1']").attr("checked", true);
                }

                var timerList = data.timerList;
                if (timerList.length > 0) {
                    var $tbody = $("#tbody").empty();
                    for (var i = 0; i < timerList.length; i++) {
                        var timer = timerList[i];
                        $tbody.append($('<tr><td><input type="hidden" name="timer" value="' + timer.time + '"/>' + timer.time + '</td><td><a href="javascript:void(0)" onclick="$(this).parent().parent().remove()">删除</a></td></tr>'));
                    }
                }
            }
        });
    } else {
        $("#id").val("");
        $("#name").val("");
        $("#orderNo").val("");
        $("#plus").val("");
        $("#sql").val("");
        $("#url").val("");

        $("input[name='execute'][value='1']").attr("checked", true);
        $("input[name='model'][value='1']").attr("checked", true);
        $("input[name='state'][value='1']").attr("checked", true);
        $("input[name='type'][value='1']").attr("checked", true);

        $('#tbody').empty();
    }
};

$(function () {
    //检测sql语句语法正确性
    $("#sql").change(function () {
        var wait = $('<div class="datagrid-mask-msg" style="display:block;left:40%">sql语句检测中,请稍候......</div>');
        wait.appendTo("#addWindow");
        var sql = $(this).val();
        $.ajax({
            type: 'post',
            url: "/test/explain",
            data: {sql: sql},
            success: function (data) {
                wait.remove();
                if (data) {
                    alert(data);
                }
            }
        });

    });

    //sql 任务 python任务切换
    $("#pythoninput").css("display", "none");

    $("input[name='type'][value='1']").click(function () {
        $("#sqlinput").css("display", "block");
        $("#pythoninput").css("display", "none");
    });

    $("input[name='type'][value='2']").click(function () {
        $("#sqlinput").css("display", "none");
        $("#pythoninput").css("display", "block");
    });

});

function saveAndTest() {

    var name = $("#name").val();
    $("#test").val("0");//测试提交
    $('#add').submit();

    window.open("/task/wait?name=" + encodeURI(encodeURI(name)))
}