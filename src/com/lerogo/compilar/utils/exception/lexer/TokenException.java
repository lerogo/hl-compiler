package com.lerogo.compilar.utils.exception.lexer;

import com.lerogo.compilar.lexer.Token;

/**
 * @author lerogo
 * @date 2021/3/26 10:20
 */
public class TokenException extends Exception {
    private static final String REMIND_EXCEPTION = "词法分析 TOKEN出错";

    public TokenException() {
        super(REMIND_EXCEPTION);
    }

    public TokenException(Token t) {
        super(
                "TokenException{" +
                        "row=" + t.getRow() +
                        ", col=" + t.getCol() +
                        ", val=" + t.getVal() +
                        ", error=" + REMIND_EXCEPTION +
                        "}"
        );
    }

    public TokenException(Token t, String error) {
        super(
                "TokenException{" +
                        "row=" + t.getRow() +
                        ", col=" + t.getCol() +
                        ", val=" + t.getVal() +
                        ", error=" + REMIND_EXCEPTION + ": " + error +
                        "}"
        );
    }
}
