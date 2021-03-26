package com.lerogo.compiler.lexer;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;
import java.util.regex.Pattern;

/**
 * 定义一些关键词等数据
 *
 * @author lerogo
 * @date 2021/3/8 15:19
 */
class JudgeType {
    private List<String> KEY_WORD;

    private List<String> OP;

    private List<String> SYMBOL;

    /**
     * 正常数字正则 复数正则 字符串正则 字符正则
     */
    private Pattern CONSTANT;
    /**
     * 标识符 正则表达式
     */
    private Pattern ID;

    @JSONField(name = "KEY_WORD")
    public void setKEY_WORD(List<String> KEY_WORD) {
        this.KEY_WORD = KEY_WORD;
    }

    @JSONField(name = "OP")
    public void setOP(List<String> OP) {
        this.OP = OP;
    }

    @JSONField(name = "SYMBOL")
    public void setSYMBOL(List<String> SYMBOL) {
        this.SYMBOL = SYMBOL;
    }

    @JSONField(name = "CONSTANT")
    public void setCONSTANT(String CONSTANT) {
        this.CONSTANT = Pattern.compile("^"+CONSTANT);
    }

    @JSONField(name = "ID")
    public void setID(String ID) {
        this.ID = Pattern.compile("^"+ID);
    }

    public List<String> getKEY_WORD() {
        return KEY_WORD;
    }


    public List<String> getOP() {
        return OP;
    }


    public List<String> getSYMBOL() {
        return SYMBOL;
    }


    public Pattern getCONSTANT() {
        return CONSTANT;
    }


    public Pattern getID() {
        return ID;
    }


    TokenType getTokenType(String str) {
        // 判断为 关键字
        if (KEY_WORD.contains(str)) {
            return TokenType.KEY_WORD;
        }
        // 判断为 运算符
        if (OP.contains(str)) {
            return TokenType.OP;
        }
        //判断为界符
        if (SYMBOL.contains((str))) {
            return TokenType.SYMBOL;
        }
        //判断为常量 类似与DFA的判断
        if (Pattern.matches(CONSTANT.pattern(), str)) {
            return TokenType.CONST;
        }
        //判断为标识符 类似与DFA的判断
        if (Pattern.matches(ID.pattern(), str)) {
            return TokenType.ID;
        }
        //否则
        return TokenType.ERROR;
    }

    @Override
    public String toString() {
        return "JudgeType{" +
                "KEY_WORD=" + KEY_WORD +
                ", OP=" + OP +
                ", SYMBOL=" + SYMBOL +
                ", CONSTANT=" + CONSTANT +
                ", ID=" + ID +
                '}';
    }
}
