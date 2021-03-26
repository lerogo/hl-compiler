package com.lerogo.compilar.lexer;

/**
 * @author lerogo
 * @date 2021/3/7 17:23
 * 定义token的类型 总共5种
 */
public enum TokenType {
    //关键字
    KEY_WORD,
    //标识符
    ID,
    //常量
    CONST,
    //限定符 界符
    SYMBOL,
    //运算符
    OP,
    //错误
    ERROR
}
