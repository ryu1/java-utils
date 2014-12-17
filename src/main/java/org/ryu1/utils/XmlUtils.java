package org.ryu1.utils;

/**
 * Created with IntelliJ IDEA.
 * User: ryu
 * Date: 2014/03/22
 * Time: 16:18
 * To change this template use File | Settings | File Templates.
 */
import com.thoughtworks.xstream.XStream;

/**
 * XMLUtilsクラス.
 */
public final class XmlUtils {

    private XmlUtils() {
    }

    /**
     * XML文字列をオブジェクトに変換します
     * @param clazz クラス
     * @param xml XML文字列
     * @return object of clazz
     */
    @SuppressWarnings("unchecked")
    public static <T> T fromXML(Class<T> clazz, String xml) {
        XStream xstream = new XStream();
        xstream.processAnnotations(clazz);
        return (T) xstream.fromXML(xml);
    }

    /**
     * オブジェクトをXML文字列に変換します
     * @param obj オブジェクト
     * @return String of XML
     */
    public static <T> String toXML(T obj) {
        XStream xstream = new XStream();
        xstream.processAnnotations(obj.getClass());
        return xstream.toXML(obj);
    }

}
