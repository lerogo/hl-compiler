package com.lerogo.compilar.lexer;

import com.lerogo.compilar.utils.exception.file.ReadFileError;

import java.io.IOException;
import java.util.List;

/**
 * @author lerogo
 * @date 2021/3/7 17:21
 * 对外提供接口的Lexer
 */
public class Lexer {
    private Tokenizer t;

    public Lexer(String configPath, String fileName) {
        try {
           this.t = new Tokenizer(configPath, fileName);
        } catch (ReadFileError readFileError) {
            readFileError.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public List<Token> getTokens(){
        return t.getTokens();
    }
}
