package com.lerogo.compilar.parser;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;

import java.util.*;


/**
 * @author lerogo
 * @date 2021/3/24 14:54
 */
public class Production {


    /**
     * 产生式的非终结符
     */
    private final Set<String> nonTerminal = new HashSet<>();

    /**
     * 产生式的终结符
     */
    private final Set<String> terminal = new HashSet<>();

    /**
     * 产生式 列表
     */
    private final List<ProductionAtom> productionList = new ArrayList<>();

    /**
     * 所有终结符/非终结符 first集合
     */
    private final Map<String, FirstSetData> firstSet = new HashMap<>();

    /**
     * 产生式 快速拿到产生式
     */
    private final Map<String, List<ProductionAtom>> mpList = new HashMap<>();


    public Set<String> getNonTerminal() {
        return nonTerminal;
    }

    public Set<String> getTerminal() {
        return terminal;
    }

    public List<ProductionAtom> getProductionList() {
        return productionList;
    }

    public Map<String, FirstSetData> getFirstSet() {
        return firstSet;
    }

    public List<ProductionAtom> getLeftMpList(String left) {
        return this.mpList.getOrDefault(left, null);
    }


    /**
     * 生成产生式
     *
     * @param production json读取
     */
    @JSONField(name = "production")
    public void setProduction(JSONArray production) {
        for (Object o : production) {
            if (o instanceof JSONObject) {
                JSONObject jo = (JSONObject) o;
                productionList.add(new ProductionAtom(
                        jo.getString("left"),
                        jo.getJSONArray("right")
                ));
            }
        }
        this.genNonTerminalTerminal();
        this.genFirstSet();
    }

    /**
     * 生成终结符/非终结符/map查找
     */
    private void genNonTerminalTerminal() {
        for (ProductionAtom t : this.productionList) {
            this.nonTerminal.add(t.getLeft());
            this.terminal.addAll(t.getRight());
        }
        this.terminal.removeAll(this.nonTerminal);

        for (String s : this.nonTerminal) {
            List<ProductionAtom> pro = new ArrayList<>();
            for (ProductionAtom t : this.productionList) {
                if (t.getLeft().equals(s)) {
                    pro.add(t);
                }
            }
            this.mpList.put(s, pro);
        }
    }

    /**
     * 生成first集合
     */
    private void genFirstSet() {
        for (String str : this.terminal) {
            getFirstData(str);
        }
        for (String str : this.nonTerminal) {
            getFirstData(str);
        }
    }

    /**
     * 对某一个 (非)终结符 生成first集合
     */
    private FirstSetData getFirstData(String str) {
        FirstSetData d = new FirstSetData();
        if (this.firstSet.containsKey(str)) {
            return this.firstSet.get(str);
        }
        // 马上保存 避免死递归
        this.firstSet.put(str, d);
        if (this.terminal.contains(str)) {
            // 如果是终结符
            d.add(str);
            d.setEndToken(false);
        } else {
            // 如果是非终结符
            boolean canGetEnd = false;
            // 对于str为left的产生式
            for (ProductionAtom p : this.mpList.get(str)) {
                List<String> tmpRight = p.getRight();
                // 如果右边为空 则判断为 #
                if (tmpRight.size() == 0) {
                    canGetEnd = true;
                } else {
                    // 对于每一个右边的字符
                    for (String tmpStr : tmpRight) {
                        // 如果是终结符 添加 并 结束
                        if (this.terminal.contains(tmpStr)) {
                            d.add(tmpStr);
                            break;
                        } else {
                            // 如果是非终结符 添加 递归添加
                            FirstSetData firstData = this.getFirstData(tmpStr);
                            d.add(firstData);
                            //  如果可以为空 则继续看下一个 否则结束
                            if (!firstData.isEndToken()) {
                                break;
                            }
                        }
                        // 最后一个符号 此时所有的都能推出空 则该str也能为空
                        if (tmpStr.equals(tmpRight.get(tmpRight.size() - 1))) {
                            canGetEnd = true;
                        }
                    }
                }

            }
            d.setEndToken(canGetEnd);
        }
        return d;
    }


    @Override
    public String toString() {
        return "Production{" +
                "productionList=" + productionList +
                "}";
    }

}

class ProductionAtom {
    /**
     * 产生式的左边
     */
    private final String left;

    /**
     * 产生式的右边 有顺序
     */
    private final List<String> right;

    public ProductionAtom(String left, JSONArray right) {
        this.left = left;
        this.right = right.toJavaList(String.class);
    }

    public String getLeft() {
        return left;
    }

    public List<String> getRight() {
        return right;
    }

    @Override
    public String toString() {
        return "ProductionAtom{" +
                "left='" + left + '\'' +
                ", right=" + right +
                "}";
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof ProductionAtom)) {
            return false;
        }
        ProductionAtom tmp = (ProductionAtom) obj;
        return this.left.equals(tmp.left) && this.right.equals(tmp.right);
    }
}


class FirstSetData {
    /**
     * first集合
     */
    private Set<String> data = new HashSet<>();
    /**
     * 标识是否可以接收 结束字符
     */
    private boolean endToken = false;

    public FirstSetData() {

    }

    public FirstSetData(Set<String> data, boolean endToken) {
        this.data = data;
        this.endToken = endToken;
    }

    public Set<String> getData() {
        return data;
    }

    public void setData(Set<String> data) {
        this.data = data;
    }

    public boolean isEndToken() {
        return endToken;
    }

    public void setEndToken(boolean endToken) {
        this.endToken = endToken;
    }

    /**
     * @param str 添加的first集
     */
    public void add(String str) {
        this.data.add(str);
    }

    /**
     * @param setData 添加的first集
     */
    public void add(FirstSetData setData) {
        this.data.addAll(setData.getData());
    }

    /**
     * @param str 移除的first集
     */
    public void remove(String str) {
        this.data.remove(str);
    }

    /**
     * 清空data
     */
    public void clear() {
        this.data.clear();
    }

    @Override
    public String toString() {
        return "FirstSetData{" +
                "data=" + data +
                ", endToken=" + endToken +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof FirstSetData)) {
            return false;
        }
        FirstSetData tmp = (FirstSetData) obj;
        if (this.endToken == tmp.endToken && this.data == tmp.data) {
            return true;
        }
        if (this.data.size() != tmp.data.size()) {
            return false;
        }
        return this.endToken == tmp.endToken && this.data.containsAll(tmp.data);
    }
}





