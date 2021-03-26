package com.lerogo.compilar.utils.exception.parser;

import com.lerogo.compilar.lexer.Token;

import java.util.Set;

/**
 * @author lerogo
 * @date 2021/3/26 14:11
 */
public class GrammarError extends Exception {
    private static final String remindError = "语法分析出错";

    public GrammarError() {
        super(remindError);
    }

    public GrammarError(Set<String> keys, Token t) {
        super(
                "GrammarError{" +
                        "row=" + t.getRow() +
                        ", col=" + t.getCol() +
                        ", error=" + remindError +
                        ", expect=" + keys +
                        ", get=" + t.getVal() +
                        "}"
        );
    }

    public GrammarError(Set<String> keys, int row) {
        super(
                "GrammarError{" +
                        "row=" + row +
                        ", error=" + remindError +
                        ", expect=" + keys +
                        ", get=null" +
                        "}"
        );
    }
}
