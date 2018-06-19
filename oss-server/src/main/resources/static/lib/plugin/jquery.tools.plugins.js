(function ($) {
    $.extend({
        log:function (msg) {
            if (window.console){
                console.log(msg)
            }
        },
        handle:function (data, success) {
            if(data.code===8200){
                success(data.data);
            }else{
                layer.msg(data.message);
            }
        },
        currentPage:function () {
            return parseInt($(".layui-laypage-skip input:eq(0)").val());
        },
        getNodeById:function (id, treeObj) {
            //ztree
        },
        /***
         * 表单赋值
         * @param data 表单数据
         * @param obj form对象 #form
         * @param sp 分隔字符
         */
        setForm:function(data, obj, sp){
            $.each(data, function(name, value) {
                var t = $(obj + ' *[name=' + name + ']');
                if (t.is('span')) {
                    t.html(value)
                } else if (t.is(":radio")) {
                    t.each(function() {
                        if ($(this).val() == value) {
                            $(this).prop("checked", "checked");
                        }
                    })
                } else if (t.is(":text") || t.is(":password") || t.is(":file")
                    || t.is(":hidden")) {
                    t.val(value);
                } else if (t.is("input:checkbox")) {
                    if (!sp)
                        sp = ",";
                    if (typeof (value) == "string") {
                        var cks = value.split(sp)
                        if (cks.length == 1) {
                            if (t.val() == value) {
                                t.prop("checked", true);
                            } else
                                t.prop("checked", false);
                        } else {
                            t.each(function() {
                                if ($.inArray($(this).val(), cks))
                                    $(this).prop("checked", true);
                                else
                                    $(this).prop("checked", false);
                            });
                        }
                    } else if (typeof (value) == "object") {
                        $.each(t, function(i, n) {
                            if($.inArray($(this).val(),value)!=-1){
                                $(this).attr("checked", true);
                            }

                        });

                    }

                } else {
                    t.val(value);
                }
            })
        }

    })


    $.fn.extend({
        json:function () {
            var o = {};
            var a = this.serializeArray();
            $.each(a, function() {
                if (o[this.name]) {
                    if (!o[this.name].push) {
                        o[this.name] = [o[this.name]];
                    }
                    o[this.name].push(this.value || '');
                } else {
                    o[this.name] = this.value || '';
                }
            });
            return o;
        }
    })

})(jQuery)