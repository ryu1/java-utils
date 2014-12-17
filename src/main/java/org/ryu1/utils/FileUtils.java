package org.ryu1.utils;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;

/**
 * Created by ryu on 14/12/17.
 */
public class FileUtils extends org.apache.commons.io.FileUtils {

    private FileUtils() {
    }

    /**
     * ディレクトリにファイルを退避する.
     * @param File ファイル
     * @param backupDirPath ディレクトリ
     * @throws java.io.IOException
     */
    public static void backupFile(final File file, final String backupDirPath)
            throws IOException {
        File destDir = new File(backupDirPath);
        if (!destDir.exists()) {
            FileUtils.forceMkdir(destDir);
        }
        FileUtils.copyFileToDirectory(file, destDir);
        FileUtils.forceDelete(file);
    }

    /**
     * 正規表現でファイルネームをフィルタリングするクラスを生成する.
     * @param regex ファイル名の正規表現
     * @param isDir ディレクトリか
     * @return FilenameFilter
     */
    public static FileFilter createRegexFileFilter(
            final String regex, final boolean isDir) {
        final String regexTmp = regex;
        final Boolean isDirTmp = isDir;
        return new FileFilter() {
            public boolean accept(final File file) {
                if (isDirTmp) {
                    if (!file.isDirectory()) {
                        return false;
                    }
                } else {
                    if (!file.isFile()) {
                        return false;
                    }
                }
                boolean ret = file.getName().matches(regexTmp);
                return ret;
            }
        };
    }

    /**
     * 指定されたディレクトリが存在するか.
     * @param dir ディレクトリ
     * @return
     *   true: 存在する
     *   false: 存在しない
     */
    public static boolean isExist(final File dir) {
        if (!(dir.exists() && dir.isDirectory())) {
            return false;
        }
        return true;
    }
}
