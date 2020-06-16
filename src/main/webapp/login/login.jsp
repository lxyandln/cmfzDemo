<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set value="${pageContext.request.contextPath}" var="path"></c:set>
<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Bootstrap Login Form Template</title>
    <!-- CSS -->
    <link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Roboto:400,100,300,500">
    <link rel="stylesheet" href="${path}/login/assets/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="${path}/login/assets/font-awesome/css/font-awesome.min.css">
    <link rel="stylesheet" href="${path}/login/assets/css/form-elements.css">
    <link rel="stylesheet" href="${path}/login/assets/css/style.css">
    <link rel="shortcut icon" href="${path}/login/assets/ico/favicon.png">
    <link rel="apple-touch-icon-precomposed" sizes="144x144"
          href="${path}/login/assets/ico/apple-touch-icon-144-precomposed.png">
    <link rel="apple-touch-icon-precomposed" sizes="114x114"
          href="${path}/login/assets/ico/apple-touch-icon-114-precomposed.png">
    <link rel="apple-touch-icon-precomposed" sizes="72x72"
          href="${path}/login/assets/ico/apple-touch-icon-72-precomposed.png">
    <link rel="apple-touch-icon-precomposed" href="${path}/login/assets/ico/apple-touch-icon-57-precomposed.png">
    <script src="${path}/boot/js/jquery-2.2.1.min.js"></script>
    <script src="${path}/login/assets/bootstrap/js/bootstrap.min.js"></script>
    <script src="${path}/login/assets/js/jquery.backstretch.min.js"></script>
    <script src="${path}/login/assets/js/scripts.js"></script>
    <script src="${path}/boot/js/jquery.validate.min.js"></script>
    <script>

        $(function () {

            $("#loginForm").validate({
                rules: {
                    username: {required: true, maxlength: 10, minlength: 2},
                    password: {required: true, maxlength: 10, minlength: 2},
                    enCode: {required: true, maxlength: 4, minlength: 4}
                },
                messages: {
                    username: {
                        required: "<span class=\"text-danger\">用户名不能为空，请重新输入！</span>",
                        maxlength: "<span class=\"text-danger\">用户名最大长度为10个字符</span>",
                        minlength: "<span class=\"text-danger\">用户名最短长度为2个字符</span>"
                    },
                    password: {
                        required: "<span class=\"text-danger\">密码不能为空，请重新输入！</span>",
                        maxlength: "<span class=\"text-danger\">密码最大长度为10个字符</span>",
                        minlength: "<span class=\"text-danger\">密码最短长度为2个字符</span>"
                    },
                    enCode: {
                        required: "<span class=\"text-danger\">验证码不能为空，请重新输入！</span>",
                        maxlength: "<span class=\"text-danger\">验证码只能为4个字符</span>",
                        minlength: "<span class=\"text-danger\">验证码只能为4个字符</span>"
                    }
                }
            });

            $("#loginButtonId").click(function () {
                var valid = $("#loginForm").valid();
                if (valid) {
                    $.ajax({
                        url: "${path}/admin/login",//指定ajax的请求路径
                        data: $("#loginForm").serialize(),
                        type: "post",//指定请求方式
                        dataType: "json",
                        error: function () {
                            $("#msgDiv").html("登陆失败，请重新输入！");
                        },
                        success: function (data) {
                            if (data.admin != null) {
                                window.location.href = "${path}/main/main.jsp";
                            } else {
                                $("#msgDiv").html(data.message);
                            }

                        }
                    });
                } else {

                }
            });
            $("#captchaImage").click(function () {
                $("#captchaImage").prop("src", "${path}/code/imageCodeAction?time=" + new Date().getTime());
            });
        });
    </script>
</head>

<body>

<!-- Top content -->
<div class="top-content">

    <div class="inner-bg">
        <div class="container">
            <div class="row">
                <div class="col-sm-8 col-sm-offset-2 text">
                    <h1><strong>CMFZ</strong> Login Form</h1>
                    <div class="description">
                        <p>
                            <a href="#"><strong>CMFZ</strong></a>
                        </p>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-6 col-sm-offset-3 form-box">
                    <div class="form-top" style="width: 450px">
                        <div class="form-top-left">
                            <h3>登录</h3>
                            <p>请输入您的用户名和密码：</p>
                            <div id="error">
                                <span id="msgDiv" class="text-danger"></span>
                            </div>
                        </div>
                        <div class="form-top-right">
                            <i class="fa fa-key"></i>
                        </div>
                    </div>
                    <div class="form-bottom" style="width: 450px">
                        <form role="form" action="" method="post" class="login-form" id="loginForm">
                            <div class="form-group">
                                <label class="sr-only" for="form-username">Username</label>
                                <input type="text" name="username" placeholder="请输入用户名..."
                                       class="form-username form-control" id="form-username">
                            </div>
                            <div class="form-group">
                                <label class="sr-only" for="form-password">Password</label>
                                <input type="password" name="password" placeholder="请输入密码..."
                                       class="form-password form-control" id="form-password">
                            </div>
                            <div class="form-group">
                                <a class="code_pic" id="vcodeImgWrap" name="change_code_img" href="javascript:void(0);">
                                    <img id="captchaImage" style="height: 48px" class="captchaImage"
                                         src="${path}/code/imageCodeAction">
                                </a>
                                <input style="width: 289px;height: 50px;border:3px solid #ddd;border-radius: 4px;"
                                       type="test" name="enCode" id="form-code">
                            </div>
                            <input type="button" style="width: 400px;border:1px solid #9d9d9d;border-radius: 4px;"
                                   id="loginButtonId" value="登录">
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>

</div>


</body>

</html>