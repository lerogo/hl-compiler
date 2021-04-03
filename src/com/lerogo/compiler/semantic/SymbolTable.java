package com.lerogo.compiler.semantic;

import com.lerogo.compiler.lexer.Token;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * @author lerogo
 * @date 2021/3/27 19:25
 * 符号表 全局 类/方法 类内/方法内
 */
public class SymbolTable {
    Stack<List<Info>> table = new Stack<>();

    public SymbolTable() {
    }

    public void mkTable() {
        table.push(new ArrayList<>());
    }

    public void rmTable() {
        table.pop();
    }

    public void enterInfo(Info info) {
        table.peek().add(info);
    }

    public boolean search(String name) {
        for (var x : this.table) {
            for (var y : x) {
                if (y.name.equals(name)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean searchLast(String name) {
        for (int i = 1; i < 3 && this.table.size() - i >= 0; ++i) {
            var infos = this.table.get(this.table.size() - i);
            for (var y : infos) {
                if (y.name.equals(name)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean search(String name, String kind) {
        for (var x : this.table) {
            for (var y : x) {
                if (y.name.equals(name) && y.kind.equals(kind)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "SymbolTable{" +
                "table=" + table +
                '}';
    }
}


/**
 * 表中元素的信息
 */
class Info {
    /**
     * 定义的名字
     */
    String name;
    /**
     * 类型
     */
    String type;
    /**
     * 种类属性
     */
    String kind;

    /**
     * 如果是函数 函数的参数
     */
    List<String> args;

    Token token;

    Info() {
        this.kind = Kind.NONE;
        this.args = new ArrayList<>();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Info)) {
            return false;
        }
        Info i = (Info) obj;
        return this.name.equals(i.name) && this.type.equals(i.type) && this.kind.equals(i.kind) && this.args.equals(i.args);
    }

    @Override
    public String toString() {
        return "Info{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", kind='" + kind + '\'' +
                ", args=" + args +
                '}';
    }
}

/**
 * 种属
 */
class Kind {
    public final static String VAR = "VAR";
    public final static String ARRAY = "ARRAY";
    public final static String FUNCTION = "FUNCTION";
    public final static String CLASS = "CLASS";
    public final static String METHOD = "METHOD";
    public final static String CONSTRUCTOR = "CONSTRUCTOR";
    public final static String NONE = "NONE";
    public final static String FIELD = "FIELD";
    public final static String STATIC = "STATIC";
}