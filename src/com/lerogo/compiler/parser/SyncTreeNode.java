package com.lerogo.compiler.parser;

import com.lerogo.compiler.lexer.Token;

import java.util.List;

/**
 * @author lerogo
 * @date 2021/3/27 17:07
 */
public class SyncTreeNode {
    private static int cnt = 0;
    private final int id;
    /**
     * 值
     */
    private final String val;
    private SyncTreeNode father = null;
    private final List<SyncTreeNode> child;
    /**
     * 终结符 token 更多信息
     */
    private final Token token;

    public SyncTreeNode() {
        this(null);
    }

    public SyncTreeNode(String val) {
        this(val, null, null);
    }

    public SyncTreeNode(String val, Token token) {
        this(val, null, token);
    }

    public SyncTreeNode(String val, List<SyncTreeNode> child) {
        this(val, child, null);
    }


    public SyncTreeNode(String val, List<SyncTreeNode> child, Token token) {
        this.val = val;
        this.child = child;
        this.id = cnt++;
        this.token = token;
    }


    public int getId() {
        return id;
    }

    public String getVal() {
        return val;
    }

    public List<SyncTreeNode> getChild() {
        return child;
    }

    public SyncTreeNode getFather() {
        return father;
    }

    public Token getToken() {
        return token;
    }

    public void setFather(SyncTreeNode father) {
        this.father = father;
    }

    @Override
    public String toString() {
        return "SyncTreeNode{" +
                "id=" + id +
                ", val='" + val + '\'' +
                ", father=" + (father == null ? null : father.getVal()) +
                ", token=" + token +
                ", child=" + child +
                '}';
    }
}
