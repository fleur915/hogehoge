package jp.co.persol.crm.hitolink.main.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class DeleteAccountDao extends CommonDao{

	public DeleteAccountDao() {
	}

	public void deleteAccount(String accountId) {
        // DBへアクセスしてユーザ情報を取得する。
		Connection conn = getConnection();
        PreparedStatement pstmt = null;

        try {
        	String sql = "UPDATE account SET delete_flg = '1' WHERE account_id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, accountId); // アカウントIDの設定

            // 削除実行
            System.out.println("削除成功");

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
        	closeConnection(conn);
        }
    }
}
