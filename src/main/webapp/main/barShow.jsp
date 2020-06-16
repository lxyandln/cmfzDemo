<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set value="${pageContext.request.contextPath}" var="path"></c:set>
<!-- 为ECharts准备一个具备大小（宽高）的Dom -->
<div><p id="mess"></p></div>
<div id="main" style="width: 600px;height:400px;"></div>
<script type="text/javascript">
    // 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('main'));

    // 指定图表的配置项和数据
    var option = {
        title: {
            text: "持明法洲App活跃用户统计"
        },
        tooltip: {},
        legend: {
            data: ["男", "女"]
        },
        xAxis: {
            data: ["一星期", "半个月", "一个月", "三个月", "半年", "一年"]
        },
        yAxis: {},
        series: [{
            name: '男',
            type: 'bar',
            //data: [5, 20, 36, 10, 10, 20]
        },
            {
                name: '女',
                type: 'bar',
                //data: [5, 20, 36, 10, 10, 20]
            }
        ]
    };

    $.ajax({
        url: "${path}/user/selectUsersBySexAndCount",
        type: "POST",
        datatype: "json",
        success: function (result) {
            myChart.setOption({
                series: [{
                    data: [result.m0, result.m1, result.m2, result.m3, result.m4, result.m5]
                }, {
                    data: [result.w0, result.w1, result.w2, result.w3, result.w4, result.w5]
                }]
            });
        }
    });

    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);
    //===============================================================================================

    //初始化
    var goEasy = new GoEasy({
        host: 'hangzhou.goeasy.io', //应用所在的区域地址: 【hangzhou.goeasy.io |singapore.goeasy.io】
        appkey: "BC-6df2985b1ccc46d2a3666e0103c2cfcf", //替换为您的应用appkey
    });

    //接收消息
    goEasy.subscribe({
        channel: "myChannel", //替换为您自己的channel
        onMessage: function (message) {
            $("#mess").text(message.content);
            $.ajax({
                url: "${path}/user/selectUsersBySexAndCount",
                type: "POST",
                datatype: "json",
                success: function (result) {
                    myChart.setOption({
                        series: [{
                            data: [result.m0, result.m1, result.m2, result.m3, result.m4, result.m5]
                        }, {
                            data: [result.w0, result.w1, result.w2, result.w3, result.w4, result.w5]
                        }]
                    });
                }
            });
        }
    });
</script>
