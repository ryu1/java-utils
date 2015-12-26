package org.ryu1.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * CSVファイル作成用共通処理.
 * 
 * @author R.Ishitsuka
 */
public final class CsvUtil {

    /**
     * デフォルトコンストラクタ禁止.
     */
    private CsvUtil() {
    }

    /**
     * ヘッダーデータを取得.
     * 
     * @param columns
     *        カラム
     * @param dqFlag
     *        ダブルクォーテーションフラグ(true:有、false:無)
     * @return String ヘッダー
     */
    public static String getHeader(final List<String> columns,
            final boolean dqFlag) {
        StringBuilder record = new StringBuilder();

        for (String column : columns) {
            if (record.length() > 0) {
                record.append(M2mConstants.COMMA);
            }

            // 書き込み
            record.append((dqFlag) ? "\"" : null);
            record.append(CheckUtil.isEmpty(column) ? "" : column);
            record.append((dqFlag) ? "\"" : null);
        }

        return record.toString();
    }

    /**
     * レコードデータを取得.
     * 
     * @param strList
     *        文字列リスト
     * @param dqFlag
     *        ダブルクォーテーションフラグ(true:有、false:無)
     * @return String レコード情報
     * @throws Exception
     *         例外発生時
     */
    public static String getRecordToString(
            final List<String> strList, final boolean dqFlag) throws Exception {

        // -------------------------------------
        // プロパティ情報からCSVレコードを生成
        // -------------------------------------
        StringBuilder record = new StringBuilder();

        // 引数のデータ
        for (String str : strList) {

            // 文字列の最初の場合
            if (record.length() > 0) {
                record.append(M2mConstants.COMMA);
            }

            // 書き込み
            record.append((dqFlag) ? "\"" : null);
            record.append(CheckUtil.isEmpty(str) ? "" : str);
            record.append((dqFlag) ? "\"" : null);
        }

        return record.toString();
    }

    /**
     * レコードデータを取得.
     * 
     * @param beanObjectList
     *        エンティティリスト
     * @param dqFlag
     *        ダブルクォーテーションフラグ(true:有、false:無)
     * @return List&lt;String&gt; レコード情報
     * @throws Exception
     *         例外発生時
     */
    public static List<String> getRecordToStringList(
            final List<? extends BaseEntity> beanObjectList,
            final boolean dqFlag) throws Exception {

        // レコードリスト
        List<String> resultList = new ArrayList<String>();

        if (beanObjectList == null || beanObjectList.isEmpty()) {
            return resultList;
        }

        //
        for (Object beanObject : beanObjectList) {
            // --------------------------------------
            // オブジェクトからフィールド情報を取得
            // --------------------------------------
            Class<?> clazz = beanObject.getClass();
            Field[] fields = clazz.getDeclaredFields();

            // -------------------------------------
            // プロパティ情報からCSVレコードを生成
            // -------------------------------------
            StringBuilder record = new StringBuilder();

            for (Field field : fields) {
                //
                if ("serialVersionUID".equals(field.getName())) {
                    continue;
                }

                // Getメソッド実行
                field.setAccessible(true);
                Object dataObj = field.get(beanObject);

                // 文字列の最初の場合
                if (record.length() > 0) {
                    record.append(M2mConstants.COMMA);
                }

                // 書き込み
                record.append((dqFlag) ? "\"" : null);
                record.append(CheckUtil.isEmpty(dataObj) ? "" : dataObj);
                record.append((dqFlag) ? "\"" : null);
            }

            // 1レコード追加
            resultList.add(record.toString());
        }

        return resultList;
    }
}
