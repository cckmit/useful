/**
 * Created by yuanjia on 17/2/24.
 */
$(document).ready(function() {

    $('#downloadApp').click(function () {
        window.location.href = "http://dbell.me";
    });

    $("#account_bind_btn").click(function(){

        var userName = $("#userName").val();
        var password = $("#password").val();
        if(userName.length == 0 || password.length == 0){
            return;
        }
        var params = {};
        //注意params.名称  名称与实体bean中名称一致
        params.userName = $("#userName").val();
        params.openId = $("#openId").val();
        params.appId = $("#appId").val();
        params.password = $("#password").val();

        $.ajax({
            type: "GET",
            url: "binding",
            data:params,
            // dataType:"json",
            //contentType: "application/json; charset=utf-8",//此处不能设置，否则后台无法接值
            success:function(data){
                window.location = "success";
            },
            error:function(jqXHR, textStatus, errorThrown){
                var error = eval('(' + jqXHR.responseText + ')');
                $('#bind_password_wrong').html(error.localizeMessage);
                $('#bind_password_wrong').css('display', 'block');
            }
        });
    });

});