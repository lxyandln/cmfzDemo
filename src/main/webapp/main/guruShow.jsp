<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set value="${pageContext.request.contextPath}" var="path"></c:set>
<script>

    $(function () {

        $("#gurutable").jqGrid({
            styleUI: "Bootstrap",
            url: "${path}/guru/selectGurusPage",
            datatype: "json",
            mtype: "post",
            colNames: ["编号", "姓名", "法号", "状态", "创建时间"],
            autowidth: true,
            pager: "#gurupage",
            rowNum: 2,
            viewrecords: true,
            editurl: "${path}/guru/edit",
            rowList: [2, 4, 6, 8],
            colModel: [
                {name: "id"},
                {name: "name", editable: true},
                {name: "sign", editable: true},
                {name: "state", editable: true},
                {name: "time", editable: true}
            ],
            multiselect: true,
            toolbar: ['true', 'top'],
            caption: "上师列表",
        }).jqGrid("navGrid", "#gurupage", {});

    });

</script>

<table id="gurutable"></table>

<div id="gurupage"></div>