package com.lerogo.compilar.utils.exception.lexer;

import com.lerogo.compilar.lexer.Token;

/**
 * @author lerogo
 * @date 2021/3/26 10:20
 */
public class TokenError extends Exception {
    private static final String remindError = "词法分析 TOKEN出错";

    public TokenError() {
        super(remindError);
    }

    public TokenError(Token t) {
        super(
                "TokenError{" +
                        "row=" + t.getRow() +
                        ", col=" + t.getCol() +
                        ", val=" + t.getVal() +
                        ", error=" + remindError +
                        "}"
        );
    }

    public TokenError(Token t, String error) {
        super(
                "TokenError{" +
                        "row=" + t.getRow() +
                        ", col=" + t.getCol() +
                        ", val=" + t.getVal() +
                        ", error=" + remindError + ": " + error +
                        "}"
        );
    }
}
