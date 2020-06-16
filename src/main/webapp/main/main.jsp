<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set value="${pageContext.request.contextPath}" var="path"></c:set>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>cmfz</title>
    <link rel="icon" href="${path}/img/favicon.ico">
    <link rel="stylesheet" href="${path}/boot/css/bootstrap.min.css">
    <link rel="stylesheet" href="${path}/jqgrid/css/jquery-ui.css">
    <%--引入bootstrap的核心css--%>
    <link rel="stylesheet" href="${path}/boot/css/bootstrap.css">
    <link rel="stylesheet" href="${path}/jqgrid/css/trirand/ui.jqgrid-bootstrap.css">
    <%--引入jquery的js--%>
    <script src="${path}/boot/js/jquery-2.2.1.min.js"></script>
    <%--引入bootstrap的js--%>
    <script src="${path}/boot/js/bootstrap.min.js"></script>
    <%--jqgird的国际化js--%>
    <script src="${path}/jqgrid/js/trirand/i18n/grid.locale-cn.js"></script>
    <%--jqgird的js--%>
    <script src="${path}/jqgrid/js/trirand/jquery.jqGrid.min.js"></script>

    <script src="${path}/boot/js/ajaxfileupload.js"></script>
    <script charset="UTF-8" src="${path}/kindeditor/kindeditor-all-min.js"></script>
    <script charset="UTF-8" src="${path}/kindeditor/lang/zh-CN.js"></script>

    <script src="${path}/boot/js/echarts.min.js"></script>
    <script src="${path}/boot/js/china.js"></script>

    <script src="${path}/boot/js/goeasy-1.0.3.js" type="text/javascript"></script>
</head>
<body>
<nav class="navbar navbar-default ">
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand" href="#">
                持明法洲后台管理系统
            </a>
        </div>
        <ul class="nav navbar-nav navbar-right">
            <li>
                <a href="${path}/admin/exitController" class="dropdown-toggle" data-toggle="dropdown" role="button"
                   aria-haspopup="true" aria-expanded="false"><span>退出登录</span> <span
                        class="glyphicon glyphicon-log-out"></span> </a>
            </li>
        </ul>
        <ul class="nav navbar-nav navbar-right">
            <li>
                <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true"
                   aria-expanded="false">欢迎您：<span class="text-primary">${sessionScope.admin.username}</span></a>
            </li>
        </ul>
    </div>
</nav>

<div class="row">
    <div class="col-md-2">

        <div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">
            <div class="panel panel-default">
                <div class="panel-heading" role="tab" id="headingOne">
                    <h4 class="panel-title">
                        <a role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseOne"
                           aria-expanded="true" aria-controls="collapseOne">
                            人员管理
                        </a>
                    </h4>
                </div>
                <div id="collapseOne" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingOne">
                    <div class="panel-body">
                        <a href="javascript:$('#divtable').load('${path}/main/userShow.jsp')">
                            用户列表
                        </a>
                    </div>
                    <div class="panel-body">
                        <a href="javascript:$('#divtable').load('${path}/main/barShow.jsp')">
                            用户活跃图
                        </a>
                    </div>
                    <div class="panel-body">
                        <a href="javascript:$('#divtable').load('${path}/main/china.jsp')">
                            用户分布图
                        </a>
                    </div>
                    <div class="panel-body">
                        <a href="javascript:$('#divtable').load('${path}/main/guruShow.jsp')">
                            上师列表
                        </a>
                    </div>
                </div>
            </div>
            <div class="panel panel-default">
                <div class="panel-heading" role="tab" id="headingTwo">
                    <h4 class="panel-title">
                        <a class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion"
                           href="#collapseTwo" aria-expanded="false" aria-controls="collapseTwo">
                            作品管理
                        </a>
                    </h4>
                </div>
                <div id="collapseTwo" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingTwo">
                    <div class="panel-body">
                        <a href="javascript:$('#divtable').load('${path}/main/articleShow.jsp')">
                            文章列表
                        </a>
                    </div>
                    <div class="panel-body">
                        <a href="javascript:$('#divtable').load('${path}/main/albumShow.jsp')">
                            专辑列表
                        </a>
                    </div>
                </div>
            </div>
            <div class="panel panel-default">
                <div class="panel-heading" role="tab" id="headingThree">
                    <h4 class="panel-title">
                        <a class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion"
                           href="#collapseThree" aria-expanded="false" aria-controls="collapseThree">
                            图片管理
                        </a>
                    </h4>
                </div>
                <div id="collapseThree" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingThree">
                    <div class="panel-body">
                        <a href="javascript:$('#divtable').load('${path}/main/bannerShow.jsp')">
                            轮播图列表
                        </a>
                    </div>
                    <div class="panel-body">
                        <a href="">
                            背景图列表
                        </a>
                    </div>
                </div>
            </div>
        </div>

    </div>
    <div class="col-md-10" id="divtable">
        <div class="panel panel-default">
            <div class="panel-heading" style="height:90px"><h2>欢迎来到持明法洲后台管理系统</h2></div>
        </div>
        <div id="carousel-example-generic" class="carousel slide" data-ride="carousel">
            <!-- Indicators -->
            <ol class="carousel-indicators">
                <li data-target="#carousel-example-generic" data-slide-to="0" class="active"></li>
                <li data-target="#carousel-example-generic" data-slide-to="1"></li>
                <li data-target="#carousel-example-generic" data-slide-to="2"></li>
            </ol>

            <!-- Wrapper for slides -->
            <div class="carousel-inner" role="listbox">
                <div class="item active">
                    <img src="${path}/img/shouye.jpg" alt="...">
                    <div class="carousel-caption">
                    </div>
                </div>
                <div class="item">
                    <img src="${path}/img/xiaochou.jpg" alt="..." style="height: 335.08px;width: 100%">
                    <div class="carousel-caption">
                    </div>
                </div>
                <div class="item">
                    <img src="${path}/img/diqiu.jpg" alt="..." style="height: 335.08px;width: 100%">
                    <div class="carousel-caption">
                    </div>
                </div>
            </div>
            <!-- Controls -->
            <a class="left carousel-control" href="#carousel-example-generic" role="button" data-slide="prev">
                <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
                <span class="sr-only">Previous</span>
            </a>
            <a class="right carousel-control" href="#carousel-example-generic" role="button" data-slide="next">
                <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
                <span class="sr-only">Next</span>
            </a>
        </div>

    </div>
    <nav class="navbar navbar-default navbar-fixed-bottom">
        <div class="container-fluid text-center">
            @百知教育@zparkhr.com.cn
        </div>
    </nav>
</div>
</body>
</html>