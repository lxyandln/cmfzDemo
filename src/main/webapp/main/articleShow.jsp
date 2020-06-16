<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set value="${pageContext.request.contextPath}" var="path"></c:set>
<script>

    $(function () {

        $("#articletable").jqGrid({
            styleUI: "Bootstrap",//集成bootstrap
            url: "${path}/article/selectArticlesPage",//请求路径
            datatype: "json",//数据格式
            mtype: "post",//请求方式
            colNames: ["编号", "标题", "作者", "内容", "状态", "上传时间", "操作"],//表格头部提示信息（与列名对应）
            autowidth: true,//是否自动适应页面宽度
            pager: "#articlepage",//显示分页栏（需要自己添加一个div）。
            page: 1,//默认进入页面时展示第1页
            rowNum: 2,//每页默认显示的条数
            viewrecords: true,//显示：当前页码-总页数 总条数
            editurl: "${path}/article/deletesArticle",
            rowList: [2, 4, 8],//选择每页显示多少条数
            search: {
                caption: "Search...",
                Find: "Find",
                Reset: "Reset",
                odata: ['equal', 'not equal', 'less', 'less or equal', 'greater', 'greater or equal', 'begins with', 'does not begin with', 'is in', 'is not in', 'ends with', 'does not end with', 'contains', 'does not contain'],
                groupOps: [{op: "AND", text: "all"}, {op: "OR", text: "any"}],
                matchText: " match",
                rulesText: " rules"
            },
            colModel: [//表格展示的内容
                {name: "id"},
                {name: "title", editable: true},//editable：开启添加编辑
                {name: "author", editable: true},
                {name: "content", hidden: true},
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
                {name: "time"},
                {
                    name: "",
                    formatter: function (cellvalue, option, rowObject) {
                        return "<a><span onclick=\"lookModal('" + rowObject.id + "')\" class=\"glyphicon glyphicon-th-list\"></span></a>";
                    }
                }
            ],
            height: "70%",
            multiselect: true,//开启批量操作
        }).jqGrid("navGrid", "#articlepage", {edit: false, add: false, del: true, search: true},
            {/*参数4：控制编辑（修改）按钮，在编辑之前/后进行额外操作，*/},
            {/*参数5：控制添加按钮，在添加之前/后进行额外操作，*/},
            {// 参数6：控制删除按钮，在删除之前/后进行额外操作
                afterSubmit: function (response) {
                    $("#albumtable").trigger("reloadGrid");// 刷新父列表（专辑）
                    return "ln";
                },
            }
        );
    });

    function showModal() {//单击事件：展示模态框
        $("#myModal").modal("show");
        $("#modal_footer").html(//设置提交按钮
            "<button type=\"button\" onclick='addArticle()' class=\"btn btn-primary\">添加</button>\n" +
            "<button type=\"button\" class=\"btn btn-danger\" data-dismiss=\"modal\">取消</button>"
        );
        //editor
        KindEditor.create('#editor', {//editor为文本域的id
            uploadJson: "${path}/kindeditor/upload",//文件上传的路径
            filePostName: "img",//指定上传文件的名称
            fileManagerJson: "${path}/kindeditor/getAllImg",//图片空间的请求路径
            allowFileManager: true,//开启远程文件访问按钮
            afterBlur: function () {//失去焦点时执行此函数内容（文本域失去焦点）
                this.sync();//失去焦点时将文本域里的内容同步到form表单里
            }
        });
        $("#addArticleFrom")[0].reset();//打开模态框之前先清空模态框中的form表单里的内容，但是不包括文本域中的内容，因为其不受js控制，受kindeditor控制（如果取消添加，则清空已经填写的信息）
        KindEditor.html("#editor", "");//打开模态框之前先清空模态框中的form表单里的文本域的内容（如果取消添加，则清空已经填写的信息）
    }

    function addArticle() {
        $.ajax({
            url: "${path}/article/insertArticle",
            datatype: "json",
            type: "post",
            data: $("#addArticleFrom").serialize(),
            success: function (data) {
                $("#myModal").modal("toggle");//添加之后自动隐藏模态框
                $("#articletable").trigger("reloadGrid");// 刷新父列表（专辑）
            }
        })
    }


    function lookModal(id) {
        $("#addArticleFrom")[0].reset();
        $("#myModal").modal("show");//显示模态框
        var value = $("#articletable").getRowData(id);//返回此id这一行的所有信息
        $("#title").val(value.title);//对标题进行赋值（回显）
        $("#author").val(value.author);//对作者进行赋值（回显）
        if (value.state == "展示") {//对状态进行赋值（回显）
            $("#state").val('1');
        } else {
            $("#state").val('2');
        }
        KindEditor.create('#editor', {
            uploadJson: "${path}/kindeditor/upload",
            filePostName: "img",
            fileManagerJson: "${path}/kindeditor/getAllImg",
            allowFileManager: true,
            afterBlur: function () {
                this.sync();
            }
        });
        KindEditor.html("#editor", "");
        KindEditor.appendHtml("#editor", value.content);


        $("#modal_footer").html(//设置提交按钮
            "<button type=\"button\" onclick=\"updateArticle('" + id + "')\" class=\"btn btn-primary\">修改</button>\n" +
            "<button type=\"button\" class=\"btn btn-danger\" data-dismiss=\"modal\">取消</button>"
        );

    }

    function updateArticle(id) {//单击事件：修改文章
        $.ajax({
            url: "${path}/article/updateArticle?id=" + id,
            datatype: "json",
            type: "post",
            data: $("#addArticleFrom").serialize(),
            success: function (data) {
                $("#myModal").modal("toggle");//添加之后自动隐藏模态框
                $("#articletable").trigger("reloadGrid");// 刷新父列表（专辑）
            }
        })
    }

</script>

<div>
    <!-- Nav tabs -->
    <ul class="nav nav-tabs" role="tablist">
        <li role="presentation" class="active"><a href="#home" aria-controls="home" role="tab"
                                                  data-toggle="tab">文章列表</a></li>
        <li role="presentation"><a href="#profile" onclick="showModal()" aria-controls="profile" role="tab"
                                   data-toggle="tab">添加文章</a></li>
    </ul>
</div>

<table id="articletable"></table>
<div id="articlepage"></div>

<div class="modal fade" id="myModal" tabindex="-1">
    <div class="modal-dialog">
        <div class="modal-content" style="width:750px">
            <!--模态框标题-->
            <div class="modal-header">
                <!--
                    用来关闭模态框的属性:data-dismiss="modal"
                -->
                <button type="button" class="close" data-dismiss="modal"><span>&times;</span></button>
                <h4 class="modal-title">编辑用户信息</h4>
            </div>

            <!--模态框内容体-->
            <div class="modal-body">
                <form action="${path}/article/editArticle" class="form-horizontal" id="addArticleFrom">
                    <div class="form-group">
                        <label class="col-sm-1 control-label">标题</label>
                        <div class="col-sm-5">
                            <input type="text" name="title" id="title" placeholder="请输入标题" class="form-control">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-1 control-label">作者</label>
                        <div class="col-sm-5">
                            <input type="text" name="author" id="author" placeholder="请输入作者" class="form-control">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-1 control-label">状态</label>
                        <div class="col-sm-5">
                            <select class="form-control" name="state" id="state">
                                <option value="1">展示</option>
                                <option value="2">隐藏</option>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-12">
                            <textarea id="editor" name="content" style="width:700px;height:300px;"></textarea>
                        </div>
                    </div>
                    <input id="addInsertImg" name="insertImg" hidden>
                </form>
            </div>
            <!--模态页脚-->
            <div class="modal-footer" id="modal_footer">
                <%--<button type="button" class="btn btn-primary">保存</button>
                    <button type="button" class="btn btn-danger" data-dismiss="modal">取消</button>--%>
            </div>
        </div>
    </div>
</div>
