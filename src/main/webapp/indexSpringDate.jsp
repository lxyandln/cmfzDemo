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
    <title>Document</title>
    <script src="${path}/boot/js/jquery-2.2.1.min.js"></script>
    <script>
        $(function () {
            $("#btn").click(function () {
                var inp = $("#inp").val();
                $("#inp").attr("value", inp);
                $.ajax({
                    url: "${path}/article/queryHighLight",//指定ajax的请求路径
                    data: {"search": inp},
                    type: "post",//指定请求方式
                    dataType: "json",
                    success: function (data) {
                        $("#div3").empty();
                        $.each(data, function (index, article) {
                            var hr = $("<hr>");

                            var id = $("<p>").html("编号：" + article.id);
                            var title = $("<p>").html("题目：" + article.title);
                            var author = $("<p>").html("作者：" + article.author);
                            var content = $("<p>").html("内容：" + article.content);
                            var state = $("<p>").html("状态：" + article.state);
                            var time = $("<p>").html("发布时间：" + article.time);

                            $("#div3").append(id).append(title).append(author).append(content).append(state).append(time).append(hr);
                            $("#div2").empty();
                        })

                    }
                });
            });

        });
    </script>
</head>
<body>
<form>
    <input id="inp" type="text" placeholder="请输入信息"/>
    <input id="btn" type="button" value="点击搜索"/>
</form>
<div id="div1">
    <div id="div2"></div>
    <div id="div3"></div>
</div>

</body>
</html>