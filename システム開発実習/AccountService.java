package jp.co.persol.crm.hitolink.main.model.service;

import java.util.List;

import jp.co.persol.crm.hitolink.main.model.dao.DeleteAccountDao;
import jp.co.persol.crm.hitolink.main.model.dao.GetAccountDao;
import jp.co.persol.crm.hitolink.main.model.dao.UpdateAccountDao;
import jp.co.persol.crm.hitolink.main.model.dto.GetAccountDto;
import jp.co.persol.crm.hitolink.main.model.dto.UpdateAccountRequestDto;

public class AccountService {


	//アカウント情報取得
	public List<GetAccountDto> getAccountById(String accountId) {
		GetAccountDao accountInfo = new GetAccountDao();
		List<GetAccountDto> account = accountInfo.AccountInfo(accountId);
		return account;
	}

	//アカウント情報登録
//	public String registerAccount(String tenantId, String name, String password, String accountRoleId) {
//		NewAccountDao newAccount = new NewAccountDao();
//
//
//		return accountId;
//	}


	//アカウント情報更新
	public void updateAccount(String accountId, String name, String password ,String accountRoleId) {
		UpdateAccountDao update = new UpdateAccountDao();
		UpdateAccountRequestDto updateData = new UpdateAccountRequestDto(accountId, name, password, accountRoleId);
		update.updateAccount(updateData);
	}

	//アカウント情報削除
	public void deleteAccount(String accountId) {
		DeleteAccountDao delete = new DeleteAccountDao();
		delete.deleteAccount(accountId);
	}

	//アカウントロール取得

}
