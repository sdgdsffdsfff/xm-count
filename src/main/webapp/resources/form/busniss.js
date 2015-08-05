/**
 * Created by lijie on 2015-07-09.
 */
$.fn.echo = function ($this) {
    /**
     * 删除
     */
    var key = $this.attr("key");
    if (key) {
        $("#keys").val(key);
    }

    /**
     * data="${bz.id}#${bz.name}#${bz.orderNo}"
     * 编辑
     */
    var data = $this.attr("data");
    if (data) {
        var vals = data.split("#");
        var id = vals[0];
        var name = vals[1];
        var orderNo = vals[2];
        $("#id").val(id);
        $("#name").val(name);
        $("#orderNo").val(orderNo);
    } else {
        $("#id").val("");
        $("#name").val("");
        $("#orderNo").val("");
    }
};