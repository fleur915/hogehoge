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

import training.jdbc.practice.entity.InventriesEntity;

/**
 * 
 * @author go.yokoyama
 * @since 2013/05/21
 */
public final class InventriesDao {

    /**
     * インスタンスの生成を抑止。
     */
    private InventriesDao() {
    }

    /**
     * 在庫を更新する。
     * 
     * @param conn コネクション
     * @param inventriesList 在庫リスト
     * @return 更新件数
     * @throws SQLException SQL例外
     */
    public static int update(Connection conn, List<InventriesEntity> inventriesList) throws SQLException {
        // 課題2-1 で実装
        PreparedStatement pstmt = null;
        int resultCount = 0;
        try {
            // SQL文の設定
            String sql = "UPDATE inventories SET quantity_on_hand = ? WHERE product_id = ? AND warehouse_id = ?";
            pstmt = conn.prepareStatement(sql);
            for (InventriesEntity inventries : inventriesList) {
                pstmt.setInt(1, inventries.getQuantityOnHand()); // 在庫数の設定
                pstmt.setInt(2, inventries.getProductId()); // 製品IDの設定
                pstmt.setInt(3, inventries.getWarehouseId()); // 倉庫IDの設定
                // 更新実行
                resultCount += pstmt.executeUpdate();

            }

            System.out.println("更新成功：" + resultCount + "件更新");

        } finally {
            if (pstmt != null) {
                pstmt.close();
            }
        }
        return resultCount;
    }

    /**
     * 在庫を確保する。
     * 製品IDをキーに在庫情報を取得する。
     * 倉庫IDの昇順に取得し、悲観的排他ロックをかける。
     * 
     * @param conn コネクション
     * @param productId 製品ID
     * @return 在庫リスト
     * @throws SQLException SQL例外
     */
    public static List<InventriesEntity> selectByProductIdForUpdate(Connection conn, int productId)
            throws SQLException {
        // TODO 課題2-1 で実装
        PreparedStatement pstmt = null;
        List<InventriesEntity> inventriesList = new ArrayList<InventriesEntity>();
        try {
            String sql = "SELECT product_id, warehouse_id, quantity_on_hand FROM inventories WHERE product_id = ? ORDER BY warehouse_id FOR UPDATE";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, productId);
            // SQLの実行
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                InventriesEntity inventries = new InventriesEntity(rs.getInt("product_id"), rs.getInt("warehouse_id"),
                        rs.getInt("quantity_on_hand"));
                inventriesList.add(inventries);
            }
        } finally {
            if (pstmt != null) {
                pstmt.close();
            }
        }
        return inventriesList;

    }

}
