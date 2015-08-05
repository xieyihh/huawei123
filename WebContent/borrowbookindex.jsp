<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="http://code.jquery.com/mobile/1.3.2/jquery.mobile-1.3.2.min.css">
<script src="http://code.jquery.com/jquery-1.8.3.min.js"></script>
<script src="http://code.jquery.com/mobile/1.3.2/jquery.mobile-1.3.2.min.js"></script>
<meta name="viewport" content="width=device-width, initial-scale=1" />
</head>
<body>

<div data-role="page">
  <div data-role="header">
  <h1>搜索</h1>
  </div>
  <div data-role="content">
    <form method="post" action="showbookinfoindex.action">
      <div data-role="fieldcontain">
        <label for="fullname">书名：</label>
        <input type="text" name="fullname" id="fullname">       
      </div>
        <div data-role="content">
 		 <input type="submit" value="查询">
  		</div>     
    </form>
  </div>
</div>

</body>
</html>
