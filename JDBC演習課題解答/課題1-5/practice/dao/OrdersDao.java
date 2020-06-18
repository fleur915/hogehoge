/*
 * Copyright ©　Intelligence Business Solutions. All rights reserved.
 */
package training.jdbc.practice.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import training.jdbc.practice.entity.OrdersEntity;

/**
 * 注文テーブルにアクセスするクラス
 *
 * @author go.yokoyama
 * @since 2013/05/21
 */
public final class OrdersDao {

    /**
     * インスタンスの生成を抑止。
     */
    private OrdersDao() {
    }

    /**
     * 注文を登録する
     *
     * @param conn コネクション
     * @param orders 注文情報
     * @throws SQLException 登録処理に失敗した場合に発生する。
     */
    public static void insertOrders(Connection conn, OrdersEntity orders) throws SQLException {
        // 課題1-2 で実装
        PreparedStatement pstmt = null;
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
            pstmt.setInt(1, orders.getOrderId()); // 注文IDの設定
            pstmt.setTimestamp(2, orders.getOrderDate()); // 注文日の設定
            pstmt.setString(3, orders.getOrderMode()); // 注文方法の設定
            pstmt.setInt(4, orders.getCustomerId()); // 顧客IDの設定
            pstmt.setInt(5, orders.getOrderStatus()); // 注文状況の設定
            pstmt.setBigDecimal(6, orders.getOrderTotal()); // 注文合計の設定

            // 登録実行
            int resultCount = pstmt.executeUpdate();
            System.out.println("登録成功：" + resultCount + "件登録");

        } finally {
            if (pstmt != null) {
                pstmt.close();
            }
        }
    }

    /**
     * 注文更新する。
     *
     * @param conn コネクション
     * @param orders 注文情報
     * @return 更新件数
     * @throws SQLException 更新処理に失敗した場合に発生する。
     */
    public static int updateOrders(Connection conn, OrdersEntity orders) throws SQLException {
        // 課題1-3 で実装
        PreparedStatement pstmt = null;
        int resultCount = 0;
        try {
            String sql = "UPDATE orders SET order_date = ?, order_mode = ?, customer_id = ? ,order_status = ?, order_total = ? WHERE order_id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setTimestamp(1, orders.getOrderDate()); // 注文日の設定
            pstmt.setString(2, orders.getOrderMode()); // 注文方法の設定
            pstmt.setInt(3, orders.getCustomerId()); // 顧客IDの設定
            pstmt.setInt(4, orders.getOrderStatus()); // 注文状況の設定
            pstmt.setBigDecimal(5, orders.getOrderTotal()); // 注文合計の設定
            pstmt.setInt(6, orders.getOrderId()); // 注文IDの設定

            // 登録実行
            resultCount = pstmt.executeUpdate();
            System.out.println("更新成功：" + resultCount + "件更新");

        } finally {
            if (pstmt != null) {
                pstmt.close();
            }
        }
        return resultCount;
    }

    /**
     * 注文を検索する
     *
     * @param conn コネクション
     * @param orderId 注文ID
     * @return 注文情報
     * @throws SQLException 検索処理に失敗した場合に発生する。
     */
    public static OrdersEntity selectById(Connection conn, Integer orderId) throws SQLException {
        // 課題1-3 で実装
        PreparedStatement pstmt = null;
        OrdersEntity orders = null;
        try {
            String sql = "SELECT order_id, order_date, order_mode, customer_id, order_status, order_total, sales_rep_id, promotion_id FROM orders WHERE order_id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, orderId);

            // SQLの実行
            ResultSet rs = pstmt
                    .executeQuery();
            while (rs.next()) {
                // 結果を取得する。
                orders = new OrdersEntity(orderId,
                        rs.getTimestamp("order_date"),
                        rs.getString("order_mode"),
                        rs.getInt("customer_id"),
                        rs.getInt("order_status"),
                        rs.getBigDecimal("order_total"),
                        rs.getInt("sales_rep_id"),
                        rs.getInt("promotion_id"));
            }
            // ResultSetにもcloseメソッドがあるが、Statementのクローズの中で閉じられる。
        } finally {
            if (pstmt != null) {
                pstmt.close();
            }
        }
        return orders;

    }

    /**
     * 注文を削除する。
     *
     * @param conn コネクション
     * @param orderId 注文ID
     * @return 削除件数
     * @throws SQLException 削除処理に失敗した場合に発生する。
     */
    public static int deleteById(Connection conn, int orderId) throws SQLException {
        // 課題1-5 で実装
        PreparedStatement pstmt = null;
        int resultCount = 0;
        try {
            String sql = " DELETE FROM orders WHERE order_id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, orderId); // 注文IDの設定

            // 削除実行
            resultCount = pstmt.executeUpdate();
            System.out.println("削除成功：" + resultCount + "件削除");

        } finally {
            if (pstmt != null) {
                pstmt.close();
            }
        }
        return resultCount;
    }

    /**
     * 注文IDの発行
     *
     * @param conn コネクション
     * @return 注文ID
     * @throws SQLException 検索処理に失敗した場合に発生する。
     */
    public static Integer selectNextOrderId(Connection conn) throws SQLException {
        // 課題2-1 で実装
        PreparedStatement pstmt = null;
        Integer orderId = null;
        return orderId;

    }

}
