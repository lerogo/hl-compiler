package com.lerogo.compiler.semantic;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lerogo
 * @date 2021/4/3 20:25
 */
public class CodeList {
    public static int id = 0;
    final List<Code> list = new ArrayList<>();
    public final static CodeList CODE_LIST = new CodeList();

    private CodeList() {
    }

    public void add(Code c) {
        list.add(c);
        ++id;
    }

    public void add(String op) {
        this.add(op, "_", "_", "T" + id);
    }

    public void add(String op, String arg1, String arg2, String arg3) {
        this.add(new Code(op, arg1, arg2, arg3));
    }

    public void add(String op, String arg1, String arg2) {
        this.add(op, arg1, arg2, "T" + id);
    }

    public List<Code> getList() {
        return list;
    }

    public String getLastResult() {
        return list.get(list.size() - 1).arg3;
    }

    @Override
    public String toString() {
        return "CodeList{" +
                "list=" + list +
                '}';
    }
}

class Code {
    String op, arg1, arg2, arg3;

    public Code(String op, String arg1, String arg2, String arg3) {
        this.op = op;
        this.arg1 = arg1;
        this.arg2 = arg2;
        this.arg3 = arg3;
    }

    @Override
    public String toString() {
        return String.format("%-20s%-20s%-20s%-20s", op, arg1, arg2, arg3);
    }
}
