/*
 * Copyright ©　Intelligence Business Solutions. All rights reserved.
 */
package training.jdbc.practice.p01;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import training.jdbc.practice.dao.OrderItemsDao;
import training.jdbc.practice.dao.OrdersDao;

/**
 * 登録された注文を削除する
 * 
 * @author go.yokoyama
 * @since 2013/05/09
 */
public final class DeleteOrders {
    /**
     * インスタンスの生成を抑止。
     */
    private DeleteOrders() {
    }

    /**
     * @param args 引数なし
     */
    public static void main(String[] args) {
        // 課題1-5 で実装
        Connection conn = null;
        try {
            // DBとの接続
            DriverManager.setLoginTimeout(20);
            conn = DriverManager.getConnection("jdbc:postgresql://vm-fy20-postgresql.japaneast.cloudapp.azure.com:5432/[接続先DB名]", "[DB接続ユーザ名]", "[DB接続パスワード]");
            System.out.println("接続成功");

            conn.setAutoCommit(false); // 自動コミットの無効化
            // 引数
            int orderId = 9001;

            // 注文詳細の削除
            OrderItemsDao.deleteByOrderId(conn, orderId);
            // 注文の削除
            OrdersDao.deleteById(conn, orderId);
            // コミット
            conn.commit();

            // CHECKSTYLE:OFF 何かしらの例外が発生した場合にRollbackしたいためExceptionでキャッチする
        } catch (Exception e) {
            // CHECKSTYLE:ON
            // 例外処理
            e.printStackTrace();
            if (conn != null) {
                try {
                    // ロールバック
                    conn.rollback();
                    System.out.println("ロールバック");
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
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
