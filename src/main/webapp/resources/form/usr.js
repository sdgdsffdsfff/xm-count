/**
 * Created by lijie on 2015-07-10.
 */

function add() {
    var password = $("#password").val();
    var confirmpsw = $("#confirmpsw").val();
    if (password != confirmpsw) {
        alert("两次输入密码不一致");
        return 0;
    }
    $("#add").submit();
}

$.fn.echo = function ($this) {
    /**
     * 编辑
     */
    var update_key = $this.attr("update-key");
    if (update_key) {
        $.ajax({
            type: 'post',
            url: "/usr/get",
            data: {id: update_key},
            dataType: 'json',
            success: function (data) {
                $("#id").val(data.id);
                $("#nickname").val(data.nickname);
                $("#username").val(data.username);
            }
        })
    }

    /**
     * 权限
     */
    var auth_key = $this.attr("auth-key");
    if (auth_key) {
        $("#auth_key").val(auth_key);

        $.ajax({
            type: 'post',
            url: "/usr/get",
            data: {id: auth_key},
            dataType: 'json',
            success: function (data) {
                $("input[name='rs']").attr("checked", false);
                var rsList = data.rsSet;
                var bzList=data.userBzSet;
                for (var i = 0; i < rsList.length; i++) {
                    var id = rsList[i].id;
                    $("input[name='rs'][value='" + id + "']").attr("checked", true);
                }
                for(var j=0;j<bzList.length;j++){
                    var bzId =bzList[j].id;
                    var bzCode =bzList[j].code;
                    if(bzCode.indexOf(",C,")!=-1){
                        $("input[name='bz"+bzId+"'][value='C']").attr("checked", true);
                    }
                    if(bzCode.indexOf(",U,")!=-1){
                        $("input[name='bz"+bzId+"'][value='U']").attr("checked", true);
                    }
                    if(bzCode.indexOf(",D,")!=-1){
                        $("input[name='bz"+bzId+"'][value='D']").attr("checked", true);
                    }
                    if(bzCode.indexOf(",R,")!=-1){
                        $("input[name='bz"+bzId+"'][value='R']").attr("checked", true);
                    }

                }
            }
        })

    }

};