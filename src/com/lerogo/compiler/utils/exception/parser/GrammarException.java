package com.lerogo.compiler.utils.exception.parser;

import com.lerogo.compiler.lexer.Token;

import java.util.Set;

/**
 * @author lerogo
 * @date 2021/3/26 14:11
 */
public class GrammarException extends Exception {
    private static final String REMIND_EXCEPTION = "语法分析出错";

    public GrammarException() {
        super(REMIND_EXCEPTION);
    }

    public GrammarException(Set<String> keys, Token t) {
        super(
                "GrammarException{" +
                        "row=" + t.getRow() +
                        ", col=" + t.getCol() +
                        ", error=" + REMIND_EXCEPTION +
                        ", expect=" + keys +
                        ", get=" + t.getVal() +
                        "}"
        );
    }

    public GrammarException(Set<String> keys, int row) {
        super(
                "GrammarException{" +
                        "row=" + row +
                        ", error=" + REMIND_EXCEPTION +
                        ", expect=" + keys +
                        ", get=null" +
                        "}"
        );
    }
}
