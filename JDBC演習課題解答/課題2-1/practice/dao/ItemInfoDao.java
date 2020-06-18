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

import training.jdbc.practice.info.ItemInfo;

/**
 * 製品情報を取得するためのクラス。
 * 製品説明テーブル、製品情報テーブル、在庫テーブルにアクセスする。
 *
 * @author go.yokoyama
 * @since 2013/05/21
 */
public final class ItemInfoDao {

    /** 製品情報詳細検索用のベースSQL */
    private static final String BASE_SQL = "SELECT pi.product_id, pi.product_name , "
            +
            "pi.category_id, pd.translated_name, "
            +
            "pd.translated_description, "
            +
            "pi.list_price, pi.min_price, "
            +
            "(SELECT COALESCE(SUM(iv.quantity_on_hand),0) FROM inventories iv WHERE pi.product_id = iv.product_id) AS sum_quantiy_on_hand "
            +
            "FROM product_descriptions pd "
            +
            "INNER JOIN product_information pi ON pi.product_id = pd.product_id ";
    /** 製品情報のソート条件 */
    private static final String ORDER_BY_PRODUCT_ID = " ORDER BY pi.product_id";

    /**
     * インスタンスの生成を抑止。
     */
    private ItemInfoDao() {
    }

    /**
     * 製品情報リストの検索
     *
     * @param conn コネクション
     * @return 製品情報リスト
     * @throws SQLException SQL例外
     */
    public static List<ItemInfo> selectAll(Connection conn) throws SQLException {
        List<ItemInfo> itemInfos = new ArrayList<ItemInfo>();
        PreparedStatement pstmt = null;
        // 課題1-0 で実装
        try {
            String sql = "SELECT pi.product_id, pi.product_name , "
                    +
                    "pi.product_description, pi.list_price "
                    +
                    "FROM product_information pi "
                    + ORDER_BY_PRODUCT_ID;
            pstmt = conn.prepareStatement(sql);
            // SQLの実行
            ResultSet rs = pstmt
                    .executeQuery();
            while (rs.next()) {
                // 検索結果を設定する
                ItemInfo itemInfo = new ItemInfo(rs.getInt("product_id"), rs.getString("product_name"),
                        rs.getString("product_description"), rs.getBigDecimal("list_price"));
                itemInfos.add(itemInfo);
            }
        } finally {
            if (pstmt != null) {
                pstmt.close();
            }
        }
        return itemInfos;

    }

    /**
     * 製品情報リストの検索
     *
     * @param conn コネクション
     * @param categoryId カテゴリID
     * @param productName 製品名
     * @param productStatus 製品状態
     * @return 製品情報リスト
     * @throws SQLException SQL例外
     */
    public static List<ItemInfo> selectByCategoryAndProductName(Connection conn, Integer categoryId,
            String productName, String productStatus) throws SQLException {
        List<ItemInfo> itemInfos = new ArrayList<ItemInfo>();
        // 課題1-1 で実装
        PreparedStatement pstmt = null;
        try {
            String sql = BASE_SQL
                    +
                    "WHERE pi.category_id = ? AND pi.product_name LIKE '%' || ? || '%' AND pi.product_status = ? AND  pd.language_id = 'JA' "
                    +
                    ORDER_BY_PRODUCT_ID;
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, categoryId);
            pstmt.setString(2, productName);
            pstmt.setString(3, productStatus);

            // SQLの実行
            ResultSet rs = pstmt
                    .executeQuery();
            while (rs.next()) {
                // 検索結果を設定する
                ItemInfo itemInfo = new ItemInfo(rs.getInt("product_id"), rs.getString("translated_name"),
                        rs.getString("translated_description"), rs.getBigDecimal("list_price"),
                        rs.getInt("sum_quantiy_on_hand"));
                itemInfos.add(itemInfo);
            }
        } finally {
            if (pstmt != null) {
                pstmt.close();
            }
        }
        return itemInfos;
    }

    /**
     * 製品情報リストの検索
     *
     * @param conn コネクション
     * @param categoryId カテゴリID
     * @param productStatus 製品状態
     * @return 製品情報リスト
     * @throws SQLException SQL例外
     */
    public static List<ItemInfo> selectByCategory(Connection conn, Integer categoryId,
            String productStatus) throws SQLException {
        List<ItemInfo> itemInfos = new ArrayList<ItemInfo>();

        PreparedStatement pstmt = null;
        try {
            String sql = BASE_SQL
                    +
                    "WHERE pi.category_id = ? AND pi.product_status = ? AND  pd.language_id = 'JA' "
                    +
                    ORDER_BY_PRODUCT_ID;
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, categoryId);
            pstmt.setString(2, productStatus);

            // SQLの実行
            ResultSet rs = pstmt
                    .executeQuery();
            while (rs.next()) {
                // 検索結果を表示する
                ItemInfo itemInfo = new ItemInfo(rs.getInt("product_id"), rs.getString("translated_name"),
                        rs.getString("translated_description"), rs.getBigDecimal("list_price"),
                        rs.getInt("sum_quantiy_on_hand"));
                itemInfos.add(itemInfo);
            }
        } finally {
            if (pstmt != null) {
                pstmt.close();
            }
        }
        return itemInfos;
    }

    /**
     * 製品情報の検索
     *
     * @param conn コネクション
     * @param productId 製品ID
     * @return 製品情報
     * @throws SQLException SQL例外
     */
    public static ItemInfo selectById(Connection conn,
            Integer productId) throws SQLException {
        ItemInfo itemInfo = null;
        // 課題2-1 で実装
        PreparedStatement pstmt = null;
        try {
            String sql = BASE_SQL
                    +
                    "WHERE pi.product_id = ? AND  pd.language_id = 'JA' "
                    +
                    ORDER_BY_PRODUCT_ID;
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, productId);

            // SQLの実行
            ResultSet rs = pstmt
                    .executeQuery();
            while (rs.next()) {
                // 検索結果を表示する
                itemInfo = new ItemInfo(rs.getInt("product_id"), rs.getString("translated_name"),
                        rs.getString("translated_description"), rs.getBigDecimal("list_price"),
                        rs.getInt("sum_quantiy_on_hand"));

            }
        } finally {
            if (pstmt != null) {
                pstmt.close();
            }
        }
        return itemInfo;
    }

}
