package com.lerogo.compiler.utils.exception.file;


/**
 * @author lerogo
 * @date 2021/3/7 18:25
 */
public class ReadFileException extends Exception {
    private static final String REMIND_EXCEPTION = "读取文件过程中失败\n\t文件可能占用\n\t";

    public ReadFileException() {
        super(REMIND_EXCEPTION);
    }

    public ReadFileException(String str) {
        super(REMIND_EXCEPTION + str);
    }
}
