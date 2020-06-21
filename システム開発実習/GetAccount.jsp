<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import = "
    jp.co.persol.crm.hitolink.main.presentation.servlet.GetAccountServlet ,
    jp.co.persol.crm.hitolink.main.model.dto.GetAccountDto ,
    jp.co.persol.crm.hitolink.main.model.dto.AccountRole ,
    java.util.*, java.text.SimpleDateFormat" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8">
<title>アカウント詳細</title>
<link rel="stylesheet" href="css/accountDetail.css">
</head>
<body class=account-detail>

<header>
  <div class=header-content>
  	<div class=header-flex>
  		<div class=header-icon-area>
  			<div class=header-icon >
  				<img src="css/assets/logo.svg">
  			</div>
  		</div>
  		<div class=header-menu-flex>
  			<div class=header-menu-talent>タレント管理</div>
			<div class=header-menu-setting>設定</div>
		</div>
		<div class=header-menu-right>
			<div class=header-menu-name>こんにちは～～さん</div>
			<div class=header-menu-logout>ログアウト</div>
		</div>
	</div>
  </div>
</header>

<div class=back-to>一覧に戻る</div>
<div class=account-detail-main>

<%
	List<GetAccountDto> accountInfo = (List<GetAccountDto>)request.getAttribute("accountData");
	GetAccountDto account = accountInfo.get(0);

			%>

<div class=main-title>アカウント情報</div>
<div class=form-label-area>
<div class=form-label>アカウントID<br>　<%= account.getAccountId() %></div>

<div class=form-label>アカウント名</div>
<input class=form-input-text name="accountName" value=<%= account.getAccountName() %>>
<div class=form-label>パスワード</div>
<input class=form-input-text type="password" name="accountPass"  value=<%= account.getPassword() %>>
<div class=form-label>ロール</div>
<select class=form-input-select name="accountRole">
<%
	List<AccountRole> roleList = (List<AccountRole>)request.getAttribute("accountRole");
	for(int i=0; i < roleList.size(); i++){
		AccountRole role = roleList.get(i);
		if(account.getAccountRole().equals(role.getRoleName())){
%>
	<option value=<%=role.getAccountRoleId()%> selected><%=role.getRoleName()%></option>
<%
		}else{
%>
	<option value=<%=role.getAccountRoleId()%>><%=role.getRoleName()%></option>
<%
		}
	}
%>
</select>
<div class=form-label>登録日<br>　<%= account.getInsertDateTime() %></div>
</div>

<div class=button-area>
<button class=form-delete-button type=button onclick="location.href='http://abehiroshi.la.coocan.jp/'">削除する</button>
<button class=form-save-button type=button onclick="location.href='http://abehiroshi.la.coocan.jp/'">保存する</button>

</div>

</div>
</body>
</html>