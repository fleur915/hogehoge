/*
 * Copyright ©　Intelligence Business Solutions. All rights reserved.
 */
package training.jdbc.practice.p01;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import training.jdbc.practice.dao.ItemInfoDao;
import training.jdbc.practice.info.ItemInfo;

/**
 * 全製品の検索
 *
 * @author go.yokoyama
 * @since 2013/05/08
 */
public final class SelectAllItemInfo {

    /**
     * インスタンスの生成を抑止。
     */
    private SelectAllItemInfo() {
    }

    /**
     * 製品検索のSQL実行
     * Ex020201SelectAllLocationsのselectAllLocationsを参考に実装する。
     * 検索処理はItemInfoDaoのselectAllメソッドに実装すること。
     *
     *
     * @param args 引数なし
     */
    public static void main(String[] args) {
        // 課題1-0 で実装
        Connection conn = null;
        try {
            // DBとの接続
            conn = DriverManager
                    .getConnection("jdbc:postgresql://vm-fy20-postgresql.japaneast.cloudapp.azure.com:5432/[接続先DB名]", "[DB接続ユーザ名]", "[DB接続パスワード]");
            DriverManager.setLoginTimeout(20);
            System.out.println("接続成功");
            // 製品検索
            // 入力情報

            List<ItemInfo> itemInfos = ItemInfoDao.selectAll(conn);
            for (ItemInfo itemInfo : itemInfos) {
                System.out.println(itemInfo.toDisplayInfo());
                System.out.println();

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
