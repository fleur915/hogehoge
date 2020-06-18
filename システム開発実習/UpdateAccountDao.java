package jp.co.persol.crm.hitolink.main.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jp.co.persol.crm.hitolink.main.model.dto.UpdateAccountRequestDto;

public class UpdateAccountDao extends CommonDao{

	public UpdateAccountDao() {
	}

    public void updateAccount(UpdateAccountRequestDto updateData) {
        // DBへアクセスしてユーザ情報を取得する。
		Connection conn = getConnection();
        PreparedStatement pstmt = null;

        try {
            String sql = "UPDATE account SET order_date = ?, order_mode = ?, customer_id = ? ,order_status = ?, order_total = ? WHERE order_id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, updateData.getAccountId()); // アカウントIDの設定
            pstmt.setString(2, updateData.getName()); // 名前の設定
            pstmt.setString(3, updateData.getPassword()); // パスワードの設定
            pstmt.setString(4, updateData.getAccountRoleId()); // ロールIDの設定
            pstmt.setTimestamp(5, updateData.getNowTime());//更新時間の設定

            // 登録実行
            System.out.println("更新成功");

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
        	closeConnection(conn);
        }
    }
}
