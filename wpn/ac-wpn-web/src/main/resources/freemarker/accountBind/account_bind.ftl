<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no" />
    <style>
        *{ margin:0; padding:0}
        input:focus {
            outline:none;
        }
    </style>
    <link href="../css/bootstrap.min.css" type="text/css" rel="stylesheet" />
    <link href="<@md5>../css/account_bind.css</@md5>" type="text/css" rel="stylesheet" />
    <script type="text/javascript" src="../js/jquery.min.js"></script>
    <script type="text/javascript" src="../js/bootstrap.min.js"></script>
    <script type="text/javascript" src="<@md5>../js/account_bind.js</@md5>"></script>
    <title>账号绑定</title>

</head>

<body>

<div class="container">

    <div class="row">
        <div class="bind_img_div">
            <img src="../img/andpaylogo_img.png" class="bind_img_style"/>
        </div>
    </div>

    <div class="row bind_input_user_margin" >
        <div class="common_all_width">
            <input type="text"  class="bind_common_input"  id="userName" name="userName" placeholder="请输入您的手机号" >
        </div>
    </div>

    <div class="row bind_input_password_margin">
        <div class="common_all_width">
            <input type="password" class="bind_common_input" id="password" name="password" placeholder="请输入您的密码" >
        </div>
    </div>
    <input type="hidden" id="appId" value="${appId!""}"/>
    <input type="hidden" id="openId" value="${openId!""}"/>

    <div class="row bind_password_wrong_div" ">
    <label class="bind_password_wrong" id="bind_password_wrong"></label>
    </div>

    <div class="row bind_btn_div">
        <button type="button" class="btn btn-block bind_btn" id="account_bind_btn">绑定</button>
    </div>

    <div class="row bind_reminder_div">
        <p class="bind_reminder_p">还未注册，点击此处<label class="bind_reminder_label" id="downloadApp">下载APP</label></p>
    </div>

</div>

</body>
</html>
