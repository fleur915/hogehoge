package jp.co.persol.crm.hitolink.main.model.dto;

public class RegisterAccountRequestDto{
    //フィールド

    //テナントID
    private String tenantId;

    //アカウントID
    private String name;

    //パスワード
    private String password;

    //アカウントロールID
    private String accountRoleId;

    //コンストラクタ
    public RegisterAccountRequestDto(String tenantId, String name, String password, String accountRoleId){
    	this.tenantId = tenantId;
    	this.name = name;
    	this.password = password;
    	this.accountRoleId = accountRoleId;
    }

    //getter
	public String getTenantId() {
		return tenantId;
	}

	public String getName() {
		return name;
	}

	public String getPassword() {
		return password;
	}

	public String getAccountRoleId() {
		return accountRoleId;
	}

	//setter
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setAccountRoleId(String accountRoleId) {
		this.accountRoleId = accountRoleId;
	}

}