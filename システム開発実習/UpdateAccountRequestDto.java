package jp.co.persol.crm.hitolink.main.model.dto;

import java.sql.Timestamp;

public class UpdateAccountRequestDto{
	//フィールド

	//アカウントID
	private String accountId;

	//アカウント名

	private String name;

	//パスワード
	private String password;

	//アカウントロールID
	private String accountRoleId;

	//タイムスタンプ
	private Timestamp nowTime;

	//コンストラクタ
	public UpdateAccountRequestDto() {
	}

    public UpdateAccountRequestDto(String accountId, String name, String password, String accountRoleId){
            this.accountId = accountId;
            this.name = name;
            this.password = password;
            this.accountRoleId = accountRoleId;
            this.nowTime = new Timestamp(System.currentTimeMillis());
    }

    //getter
	public String getAccountId() {
		return accountId;
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

	public Timestamp getNowTime() {
		return nowTime;
	}

}