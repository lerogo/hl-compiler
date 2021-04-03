package com.lerogo.compiler.parser;

import com.lerogo.compiler.lexer.Lexer;
import com.lerogo.compiler.lexer.Token;
import com.lerogo.compiler.lexer.TokenType;
import com.lerogo.compiler.utils.exception.parser.GrammarException;
import com.lerogo.compiler.utils.exception.parser.ParserError;

import java.util.*;

/**
 * @author lerogo
 * @date 2021/3/24 15:03
 */

public class SyncTable {

    /**
     * 文法产生式的开始符号 必须在文法中定义 START_OF_GRAMMAR 的增广文法
     */
    private final String START_OF_GRAMMAR;

    /**
     * 生成的项目集族
     */
    private final List<ItemSet> itemSetList = new ArrayList<>();

    /**
     * 生成的项目集族
     */
    private final List<ActionGoto> agList = new ArrayList<>();

    /**
     * 传递的产生式类
     */
    private final Production production;

    /**
     * 符号栈 / 状态栈 分析表
     */
    private final List<String> analysisTable = new ArrayList<>();

    public SyncTable(Production production) {
        this("START", production);
    }

    public SyncTable(String START_OF_GRAMMAR, Production production) {
        this.START_OF_GRAMMAR = START_OF_GRAMMAR;
        this.production = production;
        // 生成 DFA 项目集族
        this.genItemSetList();
        // 利用 项目集族 生成 action goto表
        this.genActionGotoList();

    }

    public String getSTART_OF_GRAMMAR() {
        return START_OF_GRAMMAR;
    }

    /**
     * @return 项目集族 String
     */
    public String getItemSetList() {
        StringBuilder str = new StringBuilder();
        for (ItemSet i : itemSetList) {
            str.append(i.toString()).append("\n");
        }
        return str.toString();
    }

    /**
     * @return action goto 表 String
     */
    public String getAgList() {
        StringBuilder str = new StringBuilder();
        for (ActionGoto a : agList) {
            str.append(a.toString()).append("\n");
        }
        return str.toString();
    }

    public List<String> getAnalysisTable() {
        return analysisTable;
    }

    /**
     * @param lexer 输入的lexer词
     * @return 返回是否符合语法
     * 期间打印输出 分析栈
     */
    public boolean syncTokenList(Lexer lexer, ParserError parserError) throws GrammarException {
        // 状态栈
        Stack<Integer> state = new Stack<>();
        // 符号栈
        Stack<String> symbol = new Stack<>();
        // 输入符号
        List<Token> tokens = lexer.getTokens();
        // 初始化
        state.push(0);
        for (int tokenInd = 0; 0 <= tokenInd && tokenInd < tokens.size(); ++tokenInd) {
            // 面临的符号
            Token t = tokens.get(tokenInd);
            // 是否为空 然后还没完成
            if (state.peek() == null) {
                parserError.cheekGrammar(null, t);
                return false;
            }
            // 看一下目前状态
            int index = state.peek();
            // 从actionGoto表里面找到对应行
            ActionGoto actionGoto = agList.get(index);
            // 看下一个符号是什么
            String val;
            if (t.getKind() == TokenType.ID) {
                // 如果是标识符 则识别为ID
                val = "ID";
            } else if (t.getKind() == TokenType.CONST) {
                // 如果是常量 则识别为CONSTANT
                val = "CONSTANT";
            } else {
                // 否则就是当前符号 如keyword/op
                val = t.getVal();
            }
            this.addStateAndSymbol(state, symbol, val);
            // 要么移进 要么规约
            // actionGoto表里面有没有
            Object o = actionGoto.get(val);
            if (o == null) {
                // actionGoto表没有的话 看能否规约
                o = actionGoto.get(null);
            }
            if (o instanceof Integer) {
                // 移进
                state.push((Integer) o);
                symbol.push(val);
            } else if (o instanceof ProductionAtom) {
                // 规约
                reduceToken(state, symbol, (ProductionAtom) o);
                tokenInd--;
            } else {
                // 出错处理
                parserError.cheekGrammar(actionGoto.getKeys(), t);
                return false;
            }
        }
        // 最后只进行规约 并判断是否可行
        while (!(symbol.size() == 1 && this.START_OF_GRAMMAR.equals(symbol.peek()))) {
            this.addStateAndSymbol(state, symbol, null);
            // 是否为空 然后还没完成
            if (state.peek() == null) {
                parserError.cheekGrammar(null, null);
                return false;
            }
            // 看一下目前状态
            int index = state.peek();
            // 从actionGoto表里面找到对应行
            ActionGoto actionGoto = agList.get(index);
            Object o = actionGoto.get(null);
            if (o == null || o instanceof Integer) {
                // 出错处理
                parserError.cheekGrammar(actionGoto.getKeys(), null);
                return false;
            } else {
                // 规约
                reduceToken(state, symbol, (ProductionAtom) o);
            }
        }
        this.addStateAndSymbol(state, symbol, null);
        if (symbol.size() == 1 && this.START_OF_GRAMMAR.equals(symbol.peek())) {
            return true;
        }
        //出错处理
        parserError.cheekGrammar(agList.get(state.peek()).getKeys(), null);
        return false;
    }

    /**
     * 进行规约
     *
     * @param state  状态栈
     * @param symbol 符号栈
     * @param pAtom  规约产生式
     */
    private void reduceToken(Stack<Integer> state, Stack<String> symbol, ProductionAtom pAtom) {
        int len = pAtom.getRight().size();
        // 弹出栈
        for (int i = 0; i < len; ++i) {
            state.pop();
            symbol.pop();
        }
        // 规约
        String endStr = pAtom.getLeft();
        Integer index = (Integer) agList.get(state.peek()).get(endStr);
        state.push(index);
        symbol.push(endStr);
    }


    /**
     * @param state  状态栈
     * @param symbol 符号栈
     * @param next   下一个字符
     */
    private void addStateAndSymbol(Stack<Integer> state, Stack<String> symbol, String next) {
        this.analysisTable.add(String.format("%-100s\t%-100s\t%s%n", state, symbol, next));
    }

    /**
     * 生成action goto表
     */
    private void genActionGotoList() {
        // 从DFA 项目集族 转 actionGoto表
        for (ItemSet itemSet : this.itemSetList) {
            ActionGoto actionGoto = new ActionGoto(itemSet.id);
            // 移动的 直接put 状态都是int
            actionGoto.putAll(itemSet.shiftItem);
            for (Item i : itemSet.itemList) {
                if (i.index == i.p.getRight().size()) {
                    if (i.forward == null) {
                        // 如果可以接收 # 结束符号
                        actionGoto.put(null, i.p);
                    } else {
                        if (i.forward.isEndToken()) {
                            actionGoto.put(null, i.p);
                        }
                        // 将 first集合都传入进去 面对的符号
                        actionGoto.putKeys(i.forward.getData(), i.p);
                    }
                }
            }
            agList.add(actionGoto);
        }
    }

    /**
     * 生成状态集族 转换图
     */
    private void genItemSetList() {
        // 找到第一条产生式
        ProductionAtom startProduction = null;
        for (ProductionAtom i : this.production.getProductionList()) {
            if (this.START_OF_GRAMMAR.equals(i.getLeft())) {
                startProduction = i;
                break;
            }
        }
        // 初始化项目集族
        ItemSet startItem = new ItemSet();
        this.itemSetList.add(startItem);
        startItem.add(new Item(startProduction));
        // 循环遍历每一个项目 this.itemSetList.size()在不断变化
        for (int i = 0; i < this.itemSetList.size(); ++i) {
            // 计算其closure
            this.closure(this.itemSetList.get(i));
            // 向前搜索
            this.searchForward(this.itemSetList.get(i));
        }
    }

    /**
     * @param itemSet 传递一个 项目 计算闭包
     */
    private void closure(ItemSet itemSet) {
        // 读取出该项目里面的产生式
        List<Item> item = itemSet.itemList;
        // 对于每一个产生式 从头到尾
        for (int i = 0; i < item.size(); ++i) {
            Item tmp = item.get(i);
            if (tmp.index == tmp.p.getRight().size() - 1) {
                // 点 后面只有一个符号 是否是非终结符
                String mayLeft = tmp.p.getRight().get(tmp.index);
                List<ProductionAtom> leftMpList = this.production.getLeftMpList(mayLeft);
                if (leftMpList != null) {
                    // 是非终结符 把产生式列表加入 项目
                    for (ProductionAtom p : leftMpList) {
                        Item item1 = new Item(p, 0, tmp.forward);
                        // 如果该项目无这一条 则添加
                        if (!item.contains(item1)) {
                            item.add(item1);
                        }
                    }
                }
            } else if (tmp.index < tmp.p.getRight().size() - 1) {
                // 点 后面有超过一个符号 是否是非终结符
                String mayLeft = tmp.p.getRight().get(tmp.index);
                List<ProductionAtom> leftMpList = this.production.getLeftMpList(mayLeft);
                if (leftMpList != null) {
                    String secondStr = tmp.p.getRight().get(tmp.index + 1);
                    // 是非终结符 把产生式列表加入 项目
                    for (ProductionAtom p : leftMpList) {
                        Item item1 = new Item(p);
                        item1.forward = this.production.getFirstSet().getOrDefault(secondStr, null);
                        // 如果该项目无这一条 则添加
                        if (!item.contains(item1)) {
                            item.add(item1);
                        }
                    }
                }
            }
        }
    }


    /**
     * @param itemSet 向前搜索 计算闭包
     */
    private void searchForward(ItemSet itemSet) {
        // 读取出该项目里面的产生式
        List<Item> item = itemSet.itemList;
        //是否已经创建新的状态
        Map<String, ItemSet> sMap = new HashMap<>(item.size());
        List<String> keys = new ArrayList<>();
        // 对于每一个产生式 从头到尾
        for (Item tmp : item) {
            if (tmp.index < tmp.p.getRight().size()) {
                // 点 后面有符号
                String str = tmp.p.getRight().get(tmp.index);
                ItemSet itemSet1;
                if (sMap.containsKey(str)) {
                    // 已经创建了
                    itemSet1 = sMap.get(str);
                    itemSet1.itemList.add(new Item(tmp.p, tmp.index + 1, tmp.forward));
                } else {
                    // 没有创建
                    itemSet1 = new ItemSet();
                    itemSet1.itemList.add(new Item(tmp.p, tmp.index + 1, tmp.forward));
                    sMap.put(str, itemSet1);
                    keys.add(str);
                }
            }
        }
        // 解决冲突 重复问题
        int index = this.itemSetList.size();
        for (String key : keys) {
            ItemSet itemSet1 = sMap.get(key);
            this.closure(itemSet1);
            int ind = this.isConflict(itemSet1);
            if (ind != -1) {
                itemSet.shiftItem.put(key, ind);
                ItemSet.itemSetCnt--;
            } else {
                itemSet1.id = index++;
                itemSet.shiftItem.put(key, itemSet1.id);
                this.itemSetList.add(itemSet1);
            }
        }

    }


    /**
     * @param it 传入一个项目
     * @return 项目集族 存在该项目 的索引
     */
    public int isConflict(ItemSet it) {
        for (int i = 0; i < this.itemSetList.size(); ++i) {
            if (this.itemSetList.get(i).equals(it)) {
                return i;
            }
        }
        return -1;
    }
}

class ItemSet {
    /**
     * 项目集族编号
     */
    int id;

    /**
     * 项目数量
     */
    static int itemSetCnt = 0;
    /**
     * 识别一个符号 转到 另一个项目
     */
    Map<String, Integer> shiftItem = new HashMap<>();
    /**
     * 多个产生式
     */
    List<Item> itemList = new ArrayList<>();

    ItemSet() {
        this.id = itemSetCnt++;
    }

    public void add(Item i) {
        this.itemList.add(i);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof ItemSet)) {
            return false;
        }
        ItemSet tmp = (ItemSet) obj;
        if (this.itemList == tmp.itemList) {
            return true;
        }
        if (this.itemList.size() != tmp.itemList.size()) {
            return false;
        }

        return this.itemList.containsAll(tmp.itemList);
    }

    @Override
    public String toString() {
        return "ItemSet{" +
                "id=" + id +
                ", shiftItem=" + shiftItem +
                ", itemList=" + itemList +
                '}';
    }
}

class Item {
    /**
     * 向前看的单词位置
     */
    int index;
    /**
     * 对应产生式
     */
    ProductionAtom p;
    /**
     * 向前搜索符
     */
    FirstSetData forward;

    Item(ProductionAtom p) {
        this.index = 0;
        this.p = p;
        this.forward = null;
    }

    Item(ProductionAtom p, int index, FirstSetData forward) {
        this.index = index;
        this.p = p;
        this.forward = forward;
    }


    /**
     * @param obj 用于解决冲突/判断是否一样 避免死循环
     * @return 是否相等
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Item)) {
            return false;
        }
        Item tmp = (Item) obj;
        if (this.index != tmp.index) {
            return false;
        }
        boolean ispEqual = (this.p == tmp.p || this.p.equals(tmp.p));

        if (this.forward == tmp.forward) {
            return ispEqual;
        }
        if (this.forward != null && tmp.forward != null) {
            return ispEqual && this.forward.equals(tmp.forward);
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return "Item{" +
                "index=" + index +
                ", p=" + p +
                ", forward=" + forward +
                "}\n";
    }
}

class ActionGoto {
    /**
     * 状态符
     */
    private final int id;
    /**
     * 操作
     */
    private final Map<String, Object> op = new HashMap<>();

    ActionGoto(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    /**
     * @param key 传入符号
     * @return 动作
     */
    public Object get(String key) {
        return this.op.getOrDefault(key, null);
    }

    /**
     * @return 期望符号
     */
    public Set<String> getKeys() {
        return this.op.keySet();
    }

    /**
     * @param key 传入符号
     * @param a   动作
     * @param <T> int / ProductionAtom
     */
    public <T> void put(String key, T a) {
        if (a instanceof Integer || a instanceof ProductionAtom) {
            this.op.put(key, a);
        }
    }

    public <T> void putKeys(Set<String> keys, T a) {
        if (a instanceof Integer || a instanceof ProductionAtom) {
            for (String k : keys) {
                this.op.put(k, a);
            }
        }
    }

    public <T> void replace(String key, T a) {
        if (a instanceof Integer || a instanceof ProductionAtom) {
            this.op.replace(key, a);
        }
    }

    public void remove(String key) {
        this.op.remove(key);
    }

    public void putAll(Map<String, Integer> shiftItem) {
        this.op.putAll(shiftItem);
    }

    @Override
    public String toString() {
        return "ActionGoto{" +
                "id=" + id +
                ", op=" + op +
                '}';
    }
}
