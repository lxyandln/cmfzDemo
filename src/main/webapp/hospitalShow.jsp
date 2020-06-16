<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set value="${pageContext.request.contextPath}" var="path"></c:set>
<script>

    $(function () {

        $("#table").jqGrid({
            styleUI: "Bootstrap",
            url: "${path}/hospital/selectHospitalsAllController",
            datatype: "json",
            mtype: "post",
            colNames: ["编号", "姓名", "出生日期", "部门", "等级", "电话", "工资"],
            autowidth: true,
            pager: "#page",
            rowNum: 2,
            viewrecords: true,
            editurl: "${path}/hospital/edit",
            rowList: [2, 4, 6, 8],
            colModel: [
                {name: "id"},
                {name: "name", editable: true},
                {name: "birth", editable: true},
                {
                    name: "department.id",
                    editable: true,
                    edittype: "select",
                    editoptions: {
                        dataUrl: "${path}/department/selectDepartmentsAllController",
                    },
                    formatter: function (cellvalue, options, rowObject) {
                        return rowObject.department.depart;
                    }
                },
                {
                    name: "grade.id",
                    editable: true,
                    edittype: "select",
                    editoptions: {
                        dataUrl: "${path}/grade/selectGradesAllController",
                    },
                    formatter: function (cellvalue, options, rowObject) {
                        return rowObject.grade.grade;
                    }
                },
                {name: "phone", editable: true},
                {name: "salary", editable: true}
            ],
            multiselect: true,
            toolbar: ['true', 'top'],
            caption: "医护人员管理",
        }).jqGrid("navGrid", "#page", {});

    });

</script>

<table id="table"></table>

<div id="page"></div>