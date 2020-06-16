<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set value="${pageContext.request.contextPath}" var="path"></c:set>
<script>

    $(function () {

        $("#albumtable").jqGrid({
            styleUI: "Bootstrap",
            url: "${path}/album/selectAlbumsPage",
            datatype: "json",
            mtype: "post",
            colNames: ["编号", "标题", "作者（上师）", "播音员", "集数", "简介", "大小（M）", "封面", "评分", "发布时间", "状态"],
            autowidth: true,
            pager: "#albumpage",
            rowNum: 2,
            viewrecords: true,
            editurl: "${path}/album/edit",
            rowList: [2, 4, 6, 8],
            colModel: [
                {name: "id"},
                {name: "title", editable: true},
                {name: "author", editable: true},
                {name: "announcer", editable: true},
                {name: "series"},
                {name: "brief", editable: true},
                {name: "size"},
                {
                    name: "cover", editable: true, edittype: "file",
                    //参数1：当前name的值，参数2：response请求中的任意值都可以拿到，参数3：当前行的数据都可以得到
                    formatter: function (cellvalue, option, rowObject) {
                        return "<img style='height:37px;width:107px' src='${path}/upload/" + cellvalue + "'/>"
                    }
                },
                {name: "score"},
                {name: "newstime"},
                {
                    name: "state", editable: true, edittype: "select",
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
                }
            ],
            height: "70%",
            subGrid: true,
            subGridRowExpanded: function (subGridId, albumId) {
                var tableId = subGridId + "table";
                var pageId = subGridId + "page";
                $("#" + subGridId).html(
                    "<table id=" + tableId + "></table>\n" +
                    "<div id=" + pageId + "></div>"
                );
                $("#" + tableId).jqGrid({
                    styleUI: "Bootstrap",
                    url: "${path}/chapter/selectChaptersPage?albumid=" + albumId,
                    datatype: "json",
                    mtype: "post",
                    colNames: ["编号", "标题", "内容", "大小（单位：M）", "时长", "操作"],
                    autowidth: true,
                    pager: "#" + pageId,
                    rowNum: 2,
                    viewrecords: true,
                    editurl: "${path}/chapter/edit?albumId=" + albumId,
                    rowList: [2, 4, 6, 8],
                    colModel: [
                        {name: "id"},
                        {name: "name", editable: true},
                        {
                            name: "content", editable: true, edittype: "file",
                            formatter: function (cellvalue, options, rowObject) {
                                return "<audio src=\"${path}/download/" + cellvalue + "\" controls=\"controls\"></audio>";
                            }
                        },
                        {name: "size"},
                        {
                            name: "duration",
                            formatter: function (cellvalue, options, rowObject) {
                                var fen = (cellvalue - cellvalue % 60) / 60;
                                var miao = cellvalue % 60;
                                return fen + "′" + miao + "″";
                            }
                        },
                        {
                            name: "",
                            formatter: function (cellvalue, options, rowObject) {
                                return "<a><span onclick=\"playAudio('" + rowObject.content + "')\" class=\"glyphicon glyphicon-play-circle text-primary\"></span></a>" +
                                    "&nbsp;" + "&nbsp;" + "&nbsp;" + "&nbsp;" + "&nbsp;" +
                                    "<a href=\"${path}/chapter/download?name=" + rowObject.content + "\">" +
                                    "<span class=\"glyphicon glyphicon-download text-primary\"></span>" +
                                    "</a>"
                            }
                        }
                    ],
                    height: "50%",
                    multiselect: true,
                    toolbar: ['true', 'top'],
                    caption: "所属章节列表",
                }).jqGrid("navGrid", "#" + pageId, {},
                    {// 参数4：控制编辑（修改）按钮，在编辑之前/后进行额外操作，
                        closeAfterEdit: true,
                        afterSubmit: function (response) {
                            var chapterId = response.responseText;
                            $.ajaxFileUpload({
                                url: "${path}/chapter/upload",
                                fileElementId: "content",
                                data: {cid: chapterId},
                                success: function (data) {
                                    $("#" + tableId).trigger("reloadGrid");// 刷新子列表（章节）
                                    $("#albumtable").trigger("reloadGrid");// 刷新父列表（专辑）
                                }
                            });
                            return "lxy";
                        },
                    },
                    {// 参数5：控制添加按钮，在添加之前/后进行额外操作，
                        closeAfterAdd: true,
                        afterSubmit: function (response) {
                            var chapterId = response.responseText;
                            $.ajaxFileUpload({
                                url: "${path}/chapter/upload",
                                fileElementId: "content",
                                data: {cid: chapterId},
                                success: function (data) {
                                    $("#" + tableId).trigger("reloadGrid");// 刷新子列表（章节）
                                    $("#albumtable").trigger("reloadGrid");// 刷新父列表（专辑）
                                }
                            });
                            return "ln";
                        },
                    },
                    {// 参数6：控制删除按钮，在删除之前/后进行额外操作
                        afterSubmit: function (response) {
                            $("#" + tableId).trigger("reloadGrid");// 刷新子列表（章节）
                            $("#albumtable").trigger("reloadGrid");// 刷新父列表（专辑）
                            return "lnn";
                        },
                    }
                );
            },
            multiselect: true,
            toolbar: ['true', 'top'],
            caption: "专辑列表",
        }).jqGrid("navGrid", "#albumpage", {},
            {// 参数4：控制编辑（修改）按钮，在编辑之前/后进行额外操作，
                closeAfterEdit: true,
                afterSubmit: function (response) {
                    var albumId = response.responseText;
                    $.ajaxFileUpload({
                        url: "${path}/album/upload",
                        fileElementId: "cover",
                        data: {aid: albumId},
                        success: function (data) {
                            $("#albumtable").trigger("reloadGrid");// 刷新整个列表
                        }
                    });
                    return "lxy";
                },
            },
            {// 参数5：控制添加按钮，在添加之前/后进行额外操作，
                closeAfterAdd: true,
                afterSubmit: function (response) {
                    var albumId = response.responseText;
                    $.ajaxFileUpload({
                        url: "${path}/album/upload",
                        fileElementId: "cover",
                        data: {aid: albumId},
                        success: function (data) {
                            $("#albumtable").trigger("reloadGrid");// 刷新整个列表
                        }
                    });
                    return "ln";
                },
            },
            {// 参数6：控制删除按钮，在删除之前/后进行额外操作
                afterSubmit: function (response) {
                    $("#albumtable").trigger("reloadGrid");// 刷新父列表（专辑）
                    return "ln";
                },
            }
        );

    });

    function playAudio(a) {
        $("#audioModal").modal("show");
        $("#audioId").attr("src", "${path}/download/" + a);

    }

</script>
<div id="audioModal" class="modal fade" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <audio id="audioId" src="" controls></audio>
    </div><!-- /.modal-dialog -->
</div>
<!-- /.modal -->

<table id="albumtable"></table>
<div id="albumpage"></div>