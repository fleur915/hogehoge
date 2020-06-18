/*
 * Copyright ©　Intelligence Business Solutions. All rights reserved.
 */
package training.jdbc.practice.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import training.jdbc.practice.entity.OrderItemsEntity;

/**
 * 注文詳細テーブルにアクセスするクラス
 *
 * @author go.yokoyama
 * @since 2013/05/21
 */
public final class OrderItemsDao {

    /**
     * インスタンスの生成を抑止。
     */
    private OrderItemsDao() {
    }

    /**
     * 注文詳細リストを検索する。
     *
     * @param conn コネクション
     * @param orderId 注文ID
     * @return 注文詳細リスト
     * @throws SQLException SQL例外
     */
    public static List<OrderItemsEntity> selectByOrderId(Connection conn, Integer orderId)
            throws SQLException {
        // 課題2-2 で実装
        PreparedStatement pstmt = null;
        List<OrderItemsEntity> orderItemsList = new ArrayList<OrderItemsEntity>();
        try {
            String sql = "SELECT order_id, line_item_id, product_id, unit_price, quantity FROM order_items WHERE order_id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, orderId);

            // SQLの実行
            ResultSet rs = pstmt
                    .executeQuery();
            while (rs.next()) {
                // 結果を取得する。
                OrderItemsEntity orderItems = new OrderItemsEntity(orderId,
                        rs.getInt("line_item_id"),
                        rs.getInt("product_id"),
                        rs.getBigDecimal("unit_price"),
                        rs.getInt("quantity"));
                orderItemsList.add(orderItems);
            }
            // ResultSetにもcloseメソッドがあるが、Statementのクローズの中で閉じられる。
        } finally {
            if (pstmt != null) {
                pstmt.close();
            }
        }
        return orderItemsList;

    }

    /**
     * 注文詳細を登録する
     *
     * @param conn コネクション
     * @param orderItemsList 注文詳細リスト
     * @return 登録件数
     * @throws SQLException SQL例外
     */
    public static int insert(Connection conn, List<OrderItemsEntity> orderItemsList) throws SQLException {
        // 課題1-2 で実装
        PreparedStatement pstmt = null;
        int resultCount = 0;
        try {
            // SQL文の設定
            String sql = "INSERT INTO order_items ("
                    + "order_id,"
                    + "product_id,"
                    + "unit_price,"
                    + "quantity) "
                    + "VALUES(?,?,?,?)";
            pstmt = conn.prepareStatement(sql);
            for (OrderItemsEntity orderItems : orderItemsList) {
                pstmt.setInt(1, orderItems.getOrderId()); // 注文IDの設定
                pstmt.setInt(2, orderItems.getProductId()); // 製品IDの設定
                pstmt.setBigDecimal(3, orderItems.getUnitPrice()); // 単価の設定
                pstmt.setInt(4, orderItems.getQuantity()); // 個数の設定
                resultCount += pstmt.executeUpdate();

            }

            // 登録実行
            System.out.println("登録成功：" + resultCount + "件登録");

        } finally {
            if (pstmt != null) {
                pstmt.close();
            }
        }
        return resultCount;
    }

    /**
     * 注文詳細を削除する。
     *
     * @param conn コネクション
     * @param orderId 注文ID
     * @return 削除件数
     * @throws SQLException SQL例外
     */
    public static int deleteByOrderId(Connection conn, int orderId) throws SQLException {
        // 課題1-3 で実装
        PreparedStatement pstmt = null;
        int resultCount = 0;
        try {
            // SQL文の設定
            String sql = "DELETE FROM order_items WHERE order_id = ?";
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

}
