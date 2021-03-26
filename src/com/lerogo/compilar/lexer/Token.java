package com.lerogo.compilar.lexer;

/**
 * @author lerogo
 * @date 2021/3/7 17:38
 * Token类 每一个Token应该有的
 */
public class Token {
    /**
     * 行
     */
    private int row;
    /**
     * 列
     */
    private int col;
    /**
     * 种类
     */
    private TokenType kind;
    /**
     * 值
     */
    private String val;

    public Token() {
    }

    public Token(int row, int col, TokenType kind, String val) {
        this.row = row;
        this.col = col;
        this.kind = kind;
        this.val = val;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
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
                ", col=" + col +
                ", kind=" + kind +
                ", val='" + val + '\'' +
                '}';
    }
}
