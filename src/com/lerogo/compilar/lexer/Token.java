package com.lerogo.compilar.lexer;

/**
 * @author lerogo
 * @date 2021/3/7 17:38
 * Token类 每一个Token应该有的 仅lexer包可以用
 */
public class Token {
    private int row;
    private TokenType kind;
    private String val;

    public Token() {
    }

    public Token(int row, TokenType kind, String val) {
        this.row = row;
        this.kind = kind;
        this.val = val;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public TokenType getKind() {
        return kind;
    }

    public void setKind(TokenType kind) {
        this.kind = kind;
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }

    @Override
    public String toString() {
        return "Token{" +
                "row=" + row +
                ", kind=" + kind +
                ", val='" + val + '\'' +
                '}';
    }
}
