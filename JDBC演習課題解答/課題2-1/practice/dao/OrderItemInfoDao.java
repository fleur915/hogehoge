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

import training.jdbc.practice.info.OrderItemInfo;

/**
 * 注文した製品情報を取得するためのクラス。
 * 注文テーブルと注文詳細テーブルにアクセスする。
 * 
 * @author go.yokoyama
 * @since 2013/05/21
 */
public final class OrderItemInfoDao {

    /**
     * インスタンスの生成を抑止。
     */
    private OrderItemInfoDao() {
    }

    /**
     * 注文製品情報リストの検索
     * 
     * @param conn コネクション
     * @param orderId 注文ID
     * @return 製品情報リスト
     * @throws SQLException SQL例外
     */
    public static List<OrderItemInfo> selectByOrderId(Connection conn,
            Integer orderId) throws SQLException {
        // 課題2-1 or 課題1-4 で実装
        List<OrderItemInfo> itemInfoList = new ArrayList<OrderItemInfo>();
        PreparedStatement pstmt = null;
        try {
            String sql =
                    "SELECT oi.order_id, oi.product_id, pd.translated_name, pd.translated_description, oi.unit_price, oi.quantity, oi.unit_price * oi.quantity AS sub_total "
                            + "FROM order_items oi "
                            + "INNER JOIN product_descriptions pd ON oi.product_id = pd.product_id "
                            + "WHERE oi.order_id = ? AND pd.language_id = 'JA' "
                            + "ORDER BY pd.product_id";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, orderId);

            // SQLの実行
            ResultSet rs = pstmt
                    .executeQuery();
            while (rs.next()) {
                // 検索結果を表示する
                OrderItemInfo itemInfo = new OrderItemInfo(rs.getInt("order_id"), rs.getInt("product_id"),
                        rs.getString("translated_name"),
                        rs.getString("translated_description"), rs.getBigDecimal("unit_price"), rs.getInt("quantity"),
                        rs.getBigDecimal("sub_total"));
                itemInfoList.add(itemInfo);

            }
        } finally {
            if (pstmt != null) {
                pstmt.close();
            }
        }
        return itemInfoList;
    }

}
