package com.lerogo.compilar.utils.exception.file;


/**
 * @author lerogo
 * @date 2021/3/7 18:25
 */
public class ReadFileError extends Exception {
    private static final String remindError = "读取文件过程中失败\n\t文件可能占用\n\t";

    public ReadFileError() {
        super(remindError);
    }

    public ReadFileError(String str) {
        super(remindError + str);
    }
}
