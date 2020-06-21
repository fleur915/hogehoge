package jp.co.persol.crm.hitolink.main.model.dto;

public class AccountRole {

	//アカウントロールID
	private String accountRoleId;

	//アカウントロール名
	private String roleName;

	public AccountRole() {
	}

	public AccountRole(String accountRoleId, String roleName) {
		this.setAccountRoleId(accountRoleId);
		this.setRoleName(roleName);
	}

	public String getAccountRoleId() {
		return accountRoleId;
	}

	public void setAccountRoleId(String accountRoleId) {
		this.accountRoleId = accountRoleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

}
