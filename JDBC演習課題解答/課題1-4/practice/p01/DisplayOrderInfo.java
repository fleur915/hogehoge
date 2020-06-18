/*
 * Copyright ©　Intelligence Business Solutions. All rights reserved.
 */
package training.jdbc.practice.p01;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import training.jdbc.practice.dao.OrderItemInfoDao;
import training.jdbc.practice.info.OrderItemInfo;

/**
 * 注文登録した情報を表示する。
 * 
 * @author go.yokoyama
 * @since 2013/05/08
 */
public final class DisplayOrderInfo {
    /**
     * インスタンスの生成を抑止。
     */
    private DisplayOrderInfo() {
    }
    /**
     * @param args 引数なし
     */
    public static void main(String[] args) {
        // 課題1-4 で実装
        Connection conn = null;
        try {
            // DBとの接続
            DriverManager.setLoginTimeout(20);
            conn = DriverManager.getConnection("jdbc:postgresql://vm-fy20-postgresql.japaneast.cloudapp.azure.com:5432/[接続先DB名]", "[DB接続ユーザ名]", "[DB接続パスワード]");
            System.out.println("接続成功");
            // 入力情報
            int orderId = 9001;
            // 製品検索
            List<OrderItemInfo> orderItemInfoList = OrderItemInfoDao.selectByOrderId(conn, orderId);

            for (OrderItemInfo itemInfo : orderItemInfoList) {
                System.out.println(itemInfo.toDisplayInfo());
            }

        } catch (SQLException e) {
            // 例外処理
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    // 接続の終了処理
                    conn.close();
                    System.out.println("接続終了");
                } catch (SQLException e) {
                    // 切断失敗
                    e.printStackTrace();
                }
            }
        }
    }

}
