package com.lerogo.compilar.lexer;

import com.lerogo.compilar.utils.exception.file.ReadFileError;
import com.lerogo.compilar.utils.exception.lexer.TokenError;
import com.lerogo.compilar_bak.exception.LexerError;

import java.io.IOException;
import java.util.List;

/**
 * @author lerogo
 * @date 2021/3/7 17:21
 * 对外提供接口的Lexer
 */
public class Lexer {
    /**
     * 生成的Tokenizer
     */
    private final Tokenizer t;
    /**
     * 配置文件地址
     */
    private final String configPath;
    /**
     * 识别的文件地址
     */
    private final String fileName;

    public Lexer(String configPath, String fileName) throws TokenError, IOException, ReadFileError {
        this.configPath = configPath;
        this.fileName = fileName;
        this.t = new Tokenizer(configPath, fileName);
    }

    public List<Token> getTokens() {
        return t.getTokens();
    }

    public String getConfigPath() {
        return configPath;
    }

    public String getFileName() {
        return fileName;
    }

    /**
     * 打印输出token列表
     */
    public void printToken() {
        for (Token t : this.t.getTokens()) {
            System.out.println(t);
        }
    }
}
