<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML >
<html  >
	<head>
		<base href="<%=basePath%>">
		<title>Mini~班车信息</title>
		<!-- 验证用户是否登录，如果未登录，调用缓存中的账号密码登录 -->
		<jsp:include page="verifyLogin.jsp" flush="true"></jsp:include>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="description" content="Mini班车信息">
		<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no">
		<meta name="author" content="雷晓冰">
		<meta name="format-detection" content="telephone=no">
		<meta http-equiv="x-rim-auto-match" content="none">
		<jsp:include page="base.jsp" flush="true"></jsp:include>
		<script type="text/javascript" src="js/lib/ajaxfileupload.js"></script>
		<script type="text/javascript" src="js/bus.js"></script>
		<style type="text/css">
			/****班车列表***/
			#bus_panel .panel-footer button {
				display: none;
			}
			#bus_panel .panel-body .input-group input,
			#bus_panel .panel-body .input-group button,
			#bus_panel .panel-body .input-group ul li {
				font-size: 16px;
			}
			#bus_panel .panel-body .input-group input,
			#bus_panel .panel-body .input-group button {
				height: 36px;
			}
			#bus_panel .panel-body #bus_list {
				margin-top: 10px;
				margin-bottom: 5px;
			}
			#bus_panel .panel-body #bus_list .list-group-item {
				font-size: 16px;
				background-color: #59C129;
				color: white;
			}
			#bus_panel .panel-body #bus_list .list-group-item h4 {
				color: white;
			}
			#bus_panel .panel-body #bus_list .list-group-item p {
				font-size: 14px;
				margin-bottom: 0;
			}
			#bus_panel .panel-body #bus_list .list-group-item:hover {
				background-color: #39A337;
			}
			#bus_panel .panel-body #bus_list .list-group-item .badge {
				background-color: #d2322d;
			}
			#bus_panel .panel-body #load_more_button {
				width: 100%;
				display: none;
			}
			/****导入班车信息的对话框****/
			#import_bus_dialog .modal-body #bus_file_upload {
				font-size: 16px;
				margin-bottom: 10px;
			}
			/******显示班车详细信息******/
			#bus_detail_dialog .modal-body .input-group, 
				#add_bus_dialog .modal-body .input-group {
				margin-bottom: 10px;
			}
			#bus_detail_dialog .modal-footer #saveBus,
			#bus_detail_dialog .modal-footer #deleteBus {
				display: none;
			}
		</style>
	</head>
	
	<body>
		<div class="title_div">
			<img alt="logo" src="img/logo2.png">
			<div class="right_title_div">
				<span class="right_title_span">
					<span class="user_name">${sessionScope.user.name }</span>
					<a href="userAction!logout.action">[退出]</a>
				</span>
			</div>
		</div>
		<div class="main_content">
			<!-- 班车信息查询 -->
			<div id="bus_panel" class="panel panel-default">
				<div class="panel-heading">
					<div class="panel-title">
						班车信息查询
					</div>
				</div>
				<div class="panel-body">
					<!-- 班车信息搜索框 -->
					<div class="input-group">
						<div class="input-group-btn">
							<button type="button" class="btn btn-default dropdown-toggle"
								data-toggle="dropdown" aria-expanded="false">
								<span class="bus_type">全部</span> <span class="caret"></span>
							</button>
							<ul class="dropdown-menu" role="menu">
								<li><a href="javascript:void(0)">全部</a></li>
								<li class="divider"></li>
								<li><a href="javascript:void(0)">上班车</a></li>
								<li class="divider"></li>
								<li><a href="javascript:void(0)">下班车</a></li>
								<li class="divider"></li>
								<li><a href="javascript:void(0)">晚班车</a></li>
								<li class="divider"></li>
								<li><a href="javascript:void(0)">加班车</a></li>
								<li class="divider"></li>
								<li><a href="javascript:void(0)">穿梭车</a></li>
							</ul>
						</div>
						<input type="text" class="form-control" name="keyword" placeholder="请输入班车编号/线路">
						<div class="input-group-btn">
							<button class="btn btn-default" onclick="bus_pageNum=1;loadBus()">
								<span class="glyphicon glyphicon-search"></span>
							</button>
						</div>
					</div>
					<!-- 搜索到的班车列表 -->
					<div id="bus_list" class="list-group">
					</div>
					<button id="load_more_button" class="btn btn-default" onclick="++bus_pageNum;loadBus()">
						<span class="glyphicon glyphicon-search"></span> 加载更多
					</button>
					<!-- 提示信息 -->
					<div class="msg_div">
						没有找到对应的班车信息
					</div>
				</div>
				<div class="panel-footer">
					<button id="addBus" class="btn btn-success" data-toggle="modal" data-target="#add_bus_dialog">
						<span class="glyphicon glyphicon-plus"></span> 添加
					</button>
					<button id="importBus" class="btn btn-primary" data-toggle="modal" data-target="#import_bus_dialog">
						<span class="glyphicon glyphicon-download"></span> 导入
					</button>
				</div>
			</div>
		</div>
		<!-- 导入班车信息的对话框 -->
		<div id="import_bus_dialog" class="modal fade" role="dialog" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span></button>
						<h4 class="modal-title">
							请选择导入的班车信息Excel文件
						</h4>
					</div>
					<div class="modal-body">
						<input type="file" name="upload" id="bus_file_upload" class="file_upload" accept=".xls,.xlsx" />
						<!-- 格式说明 -->
						<div class="alert alert-danger fade in">
							<strong>要求：</strong><br>
							1、数据必须为文本格式（如果有纯数字的单元格，必须将格式转化为文本格式），格式与模板一致；<br>
							2、第一行是标题，后面几行是班车信息；<br>
							3、班别和线路两列不能为空，其他属性自选；<br>
							4、对于穿梭车，可以不写”班车编号“和”发车时间“，把每一班的时间写在”线路“里面；<br>
							<a href="file/bus.xlsx">点击下载模板</a>
						</div>
					</div>
					<div class="modal-footer">
						<div class="btn-group">
							<button type="button" class="btn btn-success" onclick="importBus()">
								<span class="glyphicon glyphicon-ok"></span> 确定
							</button>
							<button type="button" class="btn btn-default" onclick="$('#import_bus_dialog').modal('hide')">
								<span class="glyphicon glyphicon-remove"></span> 取消
							</button>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!-- 显示班车详细信息的对话框 -->
		<div id="bus_detail_dialog" class="modal fade" role="dialog" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span></button>
						<h4 class="modal-title">
							班车详细信息
						</h4>
					</div>
					<div class="modal-body">
						<form>
							<div class="input-group" style="display: none;">
								<span class="input-group-addon">ID</span>
								<input type="text" class="form-control" name="id" readonly="readonly">
							</div>
							<div class="input-group">
								<span class="input-group-addon">班别</span>
								<input type="text" class="form-control" name="type" readonly="readonly">
							</div>
							<div class="input-group">
								<span class="input-group-addon">编号</span>
								<input type="text" class="form-control" name="number" readonly="readonly">
							</div>
							<div class="input-group">
								<span class="input-group-addon">时间</span>
								<input type="text" class="form-control" name="time" readonly="readonly">
							</div>
							<div class="input-group">
								<span class="input-group-addon">起点</span>
								<input rows="5" class="form-control" name="start" readonly="readonly">
							</div>
							<div class="input-group">
								<span class="input-group-addon">终点</span>
								<input rows="5" class="form-control" name="end" readonly="readonly">
							</div>
							<div class="input-group">
								<span class="input-group-addon">线路</span>
								<textarea rows="5" class="form-control" name="line" readonly="readonly"></textarea>
							</div>
							<div class="input-group">
								<span class="input-group-addon">票价</span>
								<input type="text" class="form-control" name="price" readonly="readonly">
							</div>
							<div class="input-group">
								<span class="input-group-addon">车型</span>
								<input type="text" class="form-control" name="vehicleType" readonly="readonly">
							</div>
							<div class="input-group">
								<span class="input-group-addon">车牌</span>
								<input type="text" class="form-control" name="plateNumber" readonly="readonly">
							</div>
							<div class="input-group">
								<span class="input-group-addon">司机</span>
								<input type="text" class="form-control" name="driver" readonly="readonly">
							</div>
							<div class="input-group">
								<span class="input-group-addon">备注</span>
								<input type="text" class="form-control" name="remark" readonly="readonly">
							</div>
						</form>
					</div>
					<div class="modal-footer">
						<button id="saveBus" class="btn btn-success" onclick="updateBus()">
							<span class="glyphicon glyphicon-upload"></span> 保存
						</button>
						<button id="deleteBus" class="btn btn-primary" onclick="deleteBus()">
							<span class="glyphicon glyphicon-trash"></span> 删除
						</button>
						<button type="button" class="btn btn-danger" onclick="$('#bus_detail_dialog').modal('hide')">
							<span class="glyphicon glyphicon-remove"></span> 关闭
						</button>
					</div>
				</div>
			</div>
		</div>
		<!-- 添加班车信息的对话框 -->
		<div id="add_bus_dialog" class="modal fade" role="dialog" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span></button>
						<h4 class="modal-title">
							添加新的班车信息
						</h4>
					</div>
					<div class="modal-body">
						<form>
							<div class="input-group">
								<span class="input-group-addon">班别</span>
								<input type="text" class="form-control" name="type" placeholder="请输入班车类别">
							</div>
							<div class="input-group">
								<span class="input-group-addon">编号</span>
								<input type="text" class="form-control" name="number" placeholder="请输入班车编号">
							</div>
							<div class="input-group">
								<span class="input-group-addon">时间</span>
								<input type="text" class="form-control" name="time" placeholder="请输入发车时间">
							</div>
							<div class="input-group">
								<span class="input-group-addon">起点</span>
								<input rows="5" class="form-control" name="start" placeholder="请输入起点站">
							</div>
							<div class="input-group">
								<span class="input-group-addon">终点</span>
								<input rows="5" class="form-control" name="end" placeholder="请输入终点站">
							</div>
							<div class="input-group">
								<span class="input-group-addon">线路</span>
								<textarea rows="5" class="form-control" name="line" placeholder="请输入班车线路"></textarea>
							</div>
							<div class="input-group">
								<span class="input-group-addon">票价</span>
								<input type="text" class="form-control" name="price" placeholder="请输入班车票价">
							</div>
							<div class="input-group">
								<span class="input-group-addon">车型</span>
								<input type="text" class="form-control" name="vehicleType" placeholder="请输入车型">
							</div>
							<div class="input-group">
								<span class="input-group-addon">车牌</span>
								<input type="text" class="form-control" name="plateNumber" placeholder="请输入车牌号">
							</div>
							<div class="input-group">
								<span class="input-group-addon">司机</span>
								<input type="text" class="form-control" name="driver" placeholder="请输入司机个人信息">
							</div>
							<div class="input-group">
								<span class="input-group-addon">备注</span>
								<input type="text" class="form-control" name="remark" placeholder="请输入备注信息">
							</div>
						</form>
					</div>
					<div class="modal-footer">
						<div class="btn-group">
							<button type="button" class="btn btn-success" onclick="addBus()">
								<span class="glyphicon glyphicon-ok"></span> 确定
							</button>
							<button type="button" class="btn btn-default" onclick="$('#add_bus_dialog').modal('hide')">
								<span class="glyphicon glyphicon-remove"></span> 取消
							</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</body>
</html>
