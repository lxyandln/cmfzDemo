<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set value="${pageContext.request.contextPath}" var="path"></c:set>
<!-- 为ECharts准备一个具备大小（宽高）的Dom -->
<div id="china" style="width: 600px;height: 600px;margin-top: 30px;margin-left: 30px"></div>
<div style="height: 50px"></div>
<script type="text/javascript">
    // 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('china'));

    function randomData() {
        return Math.round(Math.random() * 10000);
    }

    var option = {
        title: {
            text: '持明法洲用户分布',
            subtext: '2019年11月26日 最新数据',
            left: 'center'
        },
        tooltip: {},
        // 说明
        legend: {
            orient: 'vertical',
            left: 'left',
            data: ['男', '女']
        },
        visualMap: {
            min: 0,
            max: 300,
            left: 'left',
            top: 'bottom',
            text: ['高', '低'],           // 文本，默认为数值文本
            calculable: true
        },
        // 工具箱
        toolbox: {
            show: true,
            orient: 'vertical',
            left: 'right',
            top: 'center',
            feature: {
                dataView: {readOnly: false},
                restore: {},
                saveAsImage: {}
            }
        },
        series: [
            {
                name: '男',
                type: 'map',
                mapType: 'china',
                roam: false,
                label: {
                    normal: {
                        show: true
                    },
                    emphasis: {
                        show: true
                    }
                }
            },
            {
                name: '女',
                type: 'map',
                mapType: 'china',
                label: {
                    normal: {
                        show: true
                    },
                    emphasis: {
                        show: true
                    }
                }
            }
        ]
    };

    $.ajax({
        url: "${path}/user/selectUsersByProvinceAndCount",
        type: "POST",
        datatype: "json",
        success: function (result) {
            var arr1 = new Array();
            var arr2 = new Array();
            var count1 = 0;
            var count2 = 0;
            $.each(result, function (index, user) {
                if (user.sex == 1) {
                    arr1[count1] = {name: user.province, value: user.num};
                    count1 = count1 + 1;
                } else {
                    arr2[count2] = {name: user.province, value: user.num};
                    count2 = count2 + 1;
                }
            })
            myChart.setOption({
                series: [{
                    data: arr1
                }, {
                    data: arr2
                }]
            });
        }
    });

    myChart.setOption(option);

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
                url: "${path}/user/selectUsersByProvinceAndCount",
                type: "POST",
                datatype: "json",
                success: function (result) {
                    var arr1 = new Array();
                    var arr2 = new Array();
                    var count1 = 0;
                    var count2 = 0;
                    $.each(result, function (index, user) {
                        if (user.sex == 1) {
                            arr1[count1] = {name: user.province, value: user.num};
                            count1 = count1 + 1;
                        } else {
                            arr2[count2] = {name: user.province, value: user.num};
                            count2 = count2 + 1;
                        }
                    })
                    myChart.setOption({
                        series: [{
                            data: arr1
                        }, {
                            data: arr2
                        }]
                    });
                }
            });
        }
    });

</script>












