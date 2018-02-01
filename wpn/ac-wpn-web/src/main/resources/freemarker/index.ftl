<!doctype html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <title>Index</title>
  <link rel="stylesheet" href="<@md5>../bower_components/bootstrap/dist/css/bootstrap.css</@md5>">
  <link rel="stylesheet" href="<@md5>../bower_components/pnotify/dist/pnotify.css</@md5>">
  <link rel="stylesheet" href="<@md5>../css/app.css</@md5>">
  <@md5Map>js/../../bower_components</@md5Map>
  <@md5Map>js/../../js</@md5Map>
  <@md5Map>js</@md5Map>
  <@md5Map>html</@md5Map>
  <@webEnvs />
</head>
<body>
	<div id="top" class="container">
		<nav class="navbar navbar-default">
		  <div class="container-fluid">
		      <ul class="nav navbar-nav navbar-right">
        		<li><a>您好：</a></li>
				<li><a href="../logout">退出系统</a></li>
		      </ul>
		  </div>
		</nav>
	</div>
	<div ui-view ></div>
  
	<script src="<@md5>../bower_components/jquery/dist/jquery.js</@md5>"></script>  
	<script src="<@md5>../bower_components/bootstrap/dist/js/bootstrap.js</@md5>"></script>
	<script data-main="<@md5>js/main.js</@md5>" src="<@md5>../bower_components/requirejs/require.js</@md5>"></script>

</body>
</html>