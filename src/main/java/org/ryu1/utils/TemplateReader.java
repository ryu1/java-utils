package org.ryu1.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.validator.GenericValidator;

/**
 * メールテンプレートなどを読み込んで文字列置換まで行います。
 * 
 * <p>
 * テンプレート内では置き換えたい文字列を特殊な表記で表します。このクラスを利用してテンプレートをメモリ上に読み込み、置換を行いたい文字列の
 * {@link Map} を作成して与えます。戻り値は置換後の文字列になります。
 * </p>
 * 
 * <pre>
 * e.g.
 * テンプレート: testTemplate.txt
 * hoge {hoge}
 * foo {foo}
 * bar {foo}{bar}
 * 
 * 上のようなテンプレートが存在した時に次のような置換が可能です。
 * 
 * TemplateReader reader = new TemplateReader(&quot;testTemplate.txt&quot;);
 * Map&lt;String, String&gt; replaceMap = new HashMap&lt;String, String&gt;();
 * replaceMap.put(&quot;hoge&quot;, &quot;だいこん&quot;);
 * replaceMap.put(&quot;foo&quot;, &quot;きゅうり&quot;);
 * replaceMap.put(&quot;bar&quot;, &quot;にんじん&quot;;
 * System.out.println(reader.read(replaceMap));
 * 
 * 置換結果
 * hoge だいこん
 * foo きゅうり
 * bar きゅうりにんじん
 * </pre>
 *
 * @author R.Ishitsuka
 */
public class TemplateReader {

    private static final String charaSet = "UTF-8";

    private static final String lineSeparator = "\r\n";

    private static final String identifierLeft = "{";

    private static final String identifierRight = "}";

    private static final Map<String, String> templateMap = new HashMap<String, String>();

    private static TemplateReader templateReader = null;

    private TemplateReader() {
    }

    public static TemplateReader getInstance() {
        if (templateReader == null) {
            templateReader = new TemplateReader();
        }
        return templateReader;
    }

    public void initTemplate(String templateDir) {
        File dir = new File(templateDir);
        File[] files = dir.listFiles();
        for (File file : files) {
            if (file.isFile()) {
                templateMap.put(file.getName(), getTemplateContent(file
                        .getAbsolutePath()));
            }
        }
    }

    /**
     * テンプレートファイルのオリジナルを取得します。このメソッドで得られる結果では置換されていません。
     * 
     * @author R.Ishitsuka
     * @return 読み込んだテンプレートファイルの内容
     */
    private String getTemplateContent(String path) {
        StringBuilder builder = new StringBuilder();
        InputStreamReader streamReader = null;
        BufferedReader bufferedReader = null;

        try {
            streamReader = new InputStreamReader(new FileInputStream(path),
                    charaSet);
            bufferedReader = new BufferedReader(streamReader);

            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                builder.append(line).append(lineSeparator);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bufferedReader.close();
                streamReader.close();
            } catch (IOException e) {
            }
        }

        return builder.toString();
    }

    /**
     * 読み込んだテンプレートと置換指定文字列とを比較してすべて置き換えます。
     *
     * @author R.Ishitsuka
     * @param replaceMap
     *            置換するためのルールを設定した {@link Map} オブジェクトです。詳しくはこのクラスの説明を参照してください。
     * @return 置換が行われた結果の文字列
     */
    public String read(String templateKey, Map<String, String> replaceMap) {
        if (replaceMap == null || replaceMap.size() <= 0) {
            return null;
        }
        String template = templateMap.get(templateKey);
        Set<String> keySet = replaceMap.keySet();
        for (String key : keySet) {
            String replacement = replaceMap.get(key);
            String regex = identifierLeft + key + identifierRight;
            // template = template.replaceAll(regex, replacement);
            template = StringUtils.replace(template, regex, replacement);
        }
        return template;
    }

    /**
     * 指定したテンプレートファイルの内容を取得します。
     *
     * @author R.Ishitsuka
     * @param templateKey
     * @return
     */
    public String readTemplate(String templateKey) {
        return templateMap.get(templateKey);
    }

    /**
     * テンプレートと置換指定文字列とを比較してすべて置き換えます。
     * @author R.Ishitsuka
     * @param template テンプレート
     * @param replaceMap 置換するためのルールを設定した {@link Map} オブジェクトです。詳しくはこのクラスの説明を参照してください。
     * @return 置換が行われた結果の文字列
     */
    public String replaceTemplate(String template, Map<String, String> replaceMap) {
        if (GenericValidator.isBlankOrNull(template) || replaceMap == null || replaceMap.size() <= 0) {
            return null;
        }
        Set<String> keySet = replaceMap.keySet();
        for (String key : keySet) {
            String replacement = replaceMap.get(key);
            String regex = identifierLeft + key + identifierRight;
            template = StringUtils.replace(template, regex, replacement);
        }
        return template;
    }
}
