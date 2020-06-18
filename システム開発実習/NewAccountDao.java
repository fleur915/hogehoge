package jp.co.persol.crm.hitolink.main.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.persol.crm.hitolink.main.model.dto.RegisterAccountRequestDto;

public class NewAccountDao extends CommonDao{
    public String newAccount(RegisterAccountRequestDto register) {
        // DBへアクセスしてユーザ情報を取得する。
		Connection conn = getConnection();
        PreparedStatement pstmt = null;
        String accountId;
        try {
            // SQL文の設定
            String sql = "INSERT INTO orders "
                    + "(order_id,"
                    + "order_date,"
                    + "order_mode,"
                    + "customer_id,"
                    + "order_status,"
                    + "order_total) "
                    + "VALUES (?,?,?,?,?,?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, register.getName()); // アカウントIDの設定
            pstmt.setString(2, register.getPassword()); // の設定
            pstmt.setString(3, register.getAccountRoleId()); // 注文方法の設定


            pstmt.getString("account_id"); // 顧客IDの設定

            // 登録実行
            System.out.println("登録成功");

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
        	closeConnection(conn);
        }
        return accountId;
}