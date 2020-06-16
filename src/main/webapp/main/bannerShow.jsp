<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set value="${pageContext.request.contextPath}" var="path"></c:set>
<script>

    $(function () {

        $("#bannertable").jqGrid({
            styleUI: "Bootstrap",//集成bootstrap
            url: "${path}/banner/selectBannersPage",//请求路径
            datatype: "json",//数据格式
            mtype: "post",//请求方式
            colNames: ["编号", "标题", "描述", "状态", "图片", "上传时间"],//表格头部提示信息（与列名对应）
            autowidth: true,//是否自动适应页面宽度
            pager: "#bannerpage",//显示分页栏（需要自己添加一个div）。
            page: 1,//默认进入页面时展示第1页
            rowNum: 2,//每页默认显示的条数
            viewrecords: true,//显示：当前页码-总页数 总条数
            editurl: "${path}/banner/edit",//增删改的请求路径
            rowList: [2, 4, 8],//选择每页显示多少条数
            colModel: [//表格展示的内容
                {name: "id"},
                {name: "title", editable: true},//editable：开启添加编辑
                {name: "describes", editable: true},
                {
                    name: "state", editable: true,
                    edittype: "select",
                    editoptions: {
                        value: "1:展示;2:隐藏"
                    },
                    formatter: function (cellvalue, options, rowObject) {
                        if (cellvalue == 1) {
                            return "展示";
                        } else {
                            return "隐藏";
                        }
                    }
                },
                {
                    name: "picture", editable: true, edittype: "file",
                    //参数1：当前name的值，参数2：response请求中的任意值都可以拿到，参数3：当前行的数据都可以得到
                    formatter: function (cellvalue, option, rowObject) {
                        return "<img style='height:72px;width:178px' src='${path}/upload/" + cellvalue + "'/>"
                    }
                },
                {name: "time"}
            ],
            height: "70%",
            multiselect: true,//开启批量操作
            toolbar: ['true', 'top'],
            caption: "轮播图列表",
        }).jqGrid("navGrid", "#bannerpage",//参数1：固定写，参数2：分页栏的id，
            {// :参数3：是否展示前台页面的按钮组（每个均可选择），

            },
            {// 参数4：控制编辑（修改）按钮，在编辑之前/后进行额外操作，
                beforeShowForm: function (request) {
                    console.log(request.request);
                },
                closeAfterEdit: true,
                afterSubmit: function (response) {
                    var bannerId = response.responseText;
                    $.ajaxFileUpload({
                        url: "${path}/banner/upload",
                        fileElementId: "picture",
                        data: {bid: bannerId},
                        success: function (data) {
                            $("#bannertable").trigger("reloadGrid");// 刷新整个列表
                        }
                    });
                    return "lxy";
                },
            },
            {// 参数5：控制添加按钮，在添加之前/后进行额外操作，
                closeAfterEdit: true,
                afterSubmit: function (response) {
                    var bannerId = response.responseText;
                    $.ajaxFileUpload({
                        url: "${path}/banner/upload",
                        fileElementId: "picture",
                        data: {bid: bannerId},
                        success: function (data) {
                            $("#bannertable").trigger("reloadGrid");// 刷新整个列表
                        }
                    });
                    return "ln";
                }
            },
            {// 参数6：控制删除按钮，在删除之前/后进行额外操作
                /*afterSubmit:function (response) {
                    $("#bannertable").trigger("reloadGrid");// 刷新整个列表
                    return "nn";
                }*/
            }).jqGrid("navButtonAdd", "#bannerpage",
            {
                caption: "", buttonicon: "glyphicon glyphicon-download-alt",
                onClickButton: function () {
                    location.href = "${path}/banner/create";
                },
                position: "last", title: "", cursor: "pointer", id: "dowmloadxls"
            });
    });

</script>
<table id="bannertable"></table>
<div id="bannerpage"></div>

<%--
<div id="xlsdiv">
    <a href="${path}/banner/create">下载</a>
</div>--%>
