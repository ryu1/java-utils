package org.ryu1.utils;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * 国際化
 * 
 * @since 2014
 * @version 1.0
 * @author 石塚 隆一
 */
public class I18nImpl implements I18n {
    
    private static final String BUNDLE_NAME = "i18n.messages"; //$NON-NLS-1$
    
    private ResourceBundle bundle;
    
    private final Locale locale = Locale.getDefault();
    
    
    /**
     * インスタンスを生成する。
     * 
     */
    public I18nImpl() {
        bundle = ResourceBundle.getBundle(BUNDLE_NAME, locale);
    }
    
    /**
     * 文字列を取得する
     * 
     * @param key プロパティーキー
     * @return 文字列
     * @since 2014
     */
    @Override
    public String getString(final String key) {
        if (key == null) {
            throw new IllegalArgumentException("key is null");
        }
        return bundle.getString(key);
    }
    
    @Override
    public String getString(String key, Object... params) {
        String value = getString(key);
        return String.format(value, params);
    }
    
}
