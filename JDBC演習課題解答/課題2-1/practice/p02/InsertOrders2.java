/*
 * Copyright ©　Intelligence Business Solutions. All rights reserved.
 */
package training.jdbc.practice.p02;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import training.jdbc.practice.dao.InventriesDao;
import training.jdbc.practice.dao.ItemInfoDao;
import training.jdbc.practice.dao.OrderItemInfoDao;
import training.jdbc.practice.dao.OrderItemsDao;
import training.jdbc.practice.dao.OrdersDao;
import training.jdbc.practice.entity.InventriesEntity;
import training.jdbc.practice.entity.OrderItemsEntity;
import training.jdbc.practice.entity.OrdersEntity;
import training.jdbc.practice.info.ItemInfo;
import training.jdbc.practice.info.OrderItemInfo;

/**
 *
 * @author go.yokoyama
 * @since 2013/05/09
 */
public final class InsertOrders2 {

    /** 注文方法：オンライン */
    private static final String ORDER_MODE_ONLINE = "online";

    /** 注文状況：注文 */
    private static final int ORDER_STATUS_ENTRY = 1;

    /**
     * インスタンスの生成を抑止。
     */
    private InsertOrders2() {
    }

    /**
     * @param args 引数なし
     */
    public static void main(String[] args) {
        // 課題2-1 で実装
        Connection conn = null;
        try {
            // DBとの接続
            DriverManager.setLoginTimeout(20);
            conn = DriverManager.getConnection("vm-fy20-postgresql.japaneast.cloudapp.azure.com:5432/[接続先DB名]", "[DB接続ユーザ名]", "[DB接続パスワード]");
            System.out.println("接続成功");

            conn.setAutoCommit(false); // 自動コミットの無効化
            // 入力情報
            int customerId = 101; // 顧客ID
            // 注文する製品ID
            int item01ProductId = 1750;
            int item02ProductId = 2266;
            int item03ProductId = 3209;
            // 注文する製品の数量
            int item01count = 2;
            int item02count = 20;
            int item03count = 30;
            // 注文する製品の単価
            BigDecimal item01Price = BigDecimal.valueOf(699);
            BigDecimal item02Price = BigDecimal.valueOf(333);
            BigDecimal item03Price = BigDecimal.valueOf(13);

            // 注文詳細の仮設定
            List<OrderItemsEntity> orderItemsList = new ArrayList<OrderItemsEntity>();
            OrderItemsEntity orderItems01 = new OrderItemsEntity(null, item01ProductId,
                    item01Price, item01count);
            OrderItemsEntity orderItems02 = new OrderItemsEntity(null, item02ProductId,
                    item02Price, item02count);
            OrderItemsEntity orderItems03 = new OrderItemsEntity(null, item03ProductId,
                    item03Price, item03count);
            orderItemsList.add(orderItems01);
            orderItemsList.add(orderItems02);
            orderItemsList.add(orderItems03);

            // 在庫のロック
            for (OrderItemsEntity orderItems : orderItemsList) {
                InventriesDao.selectByProductIdForUpdate(conn, orderItems.getProductId());
            }
            // 在庫のチェック
            for (OrderItemsEntity orderItems : orderItemsList) {
                ItemInfo itemInfo = ItemInfoDao.selectById(conn, orderItems.getProductId());
                if (itemInfo.getQuantityOnHand() == null || itemInfo.getQuantityOnHand() < orderItems.getQuantity()) {
                    System.out.println("在庫が足りません：製品ID[" + orderItems.getProductId() + "]在庫数："
                            + itemInfo.getQuantityOnHand() + "希望数：" + orderItems.getQuantity());
                    conn.rollback();
                    return;
                }
            }

            // 在庫引き当て
            reserveInventries(conn, orderItemsList);

            // 注文IDの発行
            Integer orderId = OrdersDao.selectNextOrderId(conn);

            // 注文IDの追加設定
            addOrderId(orderItemsList, orderId);

            // 注文の合計金額の計算
            BigDecimal allListPrice = OrderItemsEntity.sumTotaPrice(orderItemsList);

            // 注文作成
            OrdersEntity orders = new OrdersEntity(orderId,
                    new Timestamp(System.currentTimeMillis()),
                    ORDER_MODE_ONLINE,
                    customerId,
                    ORDER_STATUS_ENTRY,
                    allListPrice,
                    null,
                    null);

            OrdersDao.insertOrders(conn, orders);
            OrderItemsDao.insert(conn, orderItemsList);

            // 注文結果を表示
            System.out.println("注文ID：" + orderId);
            List<OrderItemInfo> orderItemInfoList = OrderItemInfoDao.selectByOrderId(conn, orderId);

            for (OrderItemInfo itemInfo : orderItemInfoList) {
                System.out.println(itemInfo.toDisplayInfo());
            }
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

    /**
     * 在庫を取得し、在庫の引き当てを行う。
     *
     * @param conn コネクション
     * @param orderItemsList 注文詳細リスト
     * @throws SQLException 検索、更新に失敗した場合に発生する。
     */
    public static void reserveInventries(Connection conn, List<OrderItemsEntity> orderItemsList) throws SQLException {
        for (OrderItemsEntity orderItems : orderItemsList) {
            // 在庫取得
            List<InventriesEntity> inventriesList = InventriesDao.selectByProductIdForUpdate(conn,
                    orderItems.getProductId());

            List<InventriesEntity> newInventriesList = InventriesEntity.reserveInvation(inventriesList,
                    orderItems.getQuantity());
            // 在庫引き当て
            InventriesDao.update(conn, newInventriesList);
        }
    }

    /**
     * 注文詳細に注文IDを追加する。
     * 入力となった注文詳細情報に対して直接注文IDの追加を行う。
     *
     * @param orderItemsList 注文詳細
     * @param orderId 注文ID
     */
    private static void addOrderId(List<OrderItemsEntity> orderItemsList, int orderId) {
        for (OrderItemsEntity orderItems : orderItemsList) {
            orderItems.setOrderId(orderId);
        }
    }

}
