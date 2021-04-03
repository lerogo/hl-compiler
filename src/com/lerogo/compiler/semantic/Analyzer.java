package com.lerogo.compiler.semantic;

import com.lerogo.compiler.lexer.Lexer;
import com.lerogo.compiler.lexer.Token;
import com.lerogo.compiler.parser.SyncTreeNode;
import com.lerogo.compiler.utils.exception.file.ReadFileException;
import com.lerogo.compiler.utils.exception.parser.ParserError;
import com.lerogo.compiler.utils.exception.semantic.ErrorType;
import com.lerogo.compiler.utils.exception.semantic.SemanticError;
import com.lerogo.compiler.utils.exception.semantic.SemanticException;
import com.lerogo.compiler.utils.file.FileReader;

import java.util.*;

/**
 * @author lerogo
 * @date 2021/3/27 20:00
 */
public class Analyzer {
    /**
     * 语法树
     */
    private final SyncTreeNode syncTreeNode;

    /**
     * 生成的四元式序列
     */
    private final CodeList codeList = CodeList.CODE_LIST;

    /**
     * 符号表
     */
    private final SymbolTable symbolTable = new SymbolTable();

    public Analyzer(Lexer lexer, SyncTreeNode treeNode) {
        this.syncTreeNode = treeNode;
        FileReader fileReader = null;
        try {
            fileReader = new FileReader(lexer.getFileName());
        } catch (ReadFileException readFileException) {
            readFileException.printStackTrace();
        }
        SemanticError.setFileReader(fileReader);
    }

    /**
     * 符号表生成 静态语义检查 代码生成
     */
    public void cheek() throws SemanticException {
        this.genGlobalTable(this.syncTreeNode);
        this.cheekGlobalTable();
        this.genNodeList(this.syncTreeNode);
    }

    public CodeList getCodeList() {
        return this.codeList;
    }

    public void printCodeList() {
        for (var c : this.codeList.list) {
            System.out.println(c);
        }
    }

    /**
     * 递归生成
     *
     * @param node 初始节点
     * @throws SemanticException 语义错误
     */
    private void genNodeList(SyncTreeNode node) throws SemanticException {
        if (node == null) {
            // 如果为null
            return;
        }
        String nodeVal = node.getVal();
        // 找到需要新建符号表的节点
        boolean canOpTable = SyncTreeNodeKind.CLASS_BODY.equals(nodeVal)
                || SyncTreeNodeKind.FUNC_S.equals(nodeVal)
                || SyncTreeNodeKind.METHOD_CLASS.equals(nodeVal)
                || SyncTreeNodeKind.FOR_STMT.equals(nodeVal)
                // 包含if/while等
                || SyncTreeNodeKind.BLOCK_STMT.equals(nodeVal);
        if (canOpTable) {
            this.symbolTable.mkTable();
        }
        if (SyncTreeNodeKind.ARGS.equals(nodeVal)) {
            // 如果遇到函数  int(int a,double b) 这样的
            this.processArgs(node);
        } else if (SyncTreeNodeKind.CLASS_BODY.equals(nodeVal)) {
            // 处理类体
            this.processClassBody(node);
        } else if (SyncTreeNodeKind.DECLARE_INTER.equals(nodeVal)) {
            // 函数中定义的变量
            this.processDeclareInter(node);
        } else if (SyncTreeNodeKind.DECLARE_CLASS.equals(nodeVal)) {
            // 类中变量 域
            this.processDeclareClass(node);
        } else if (SyncTreeNodeKind.METHOD_CLASS.equals(nodeVal)) {
            // 类的函数 method
            this.processMethodClass(node);
        } else if (SyncTreeNodeKind.IF_STMT.equals(nodeVal)) {
            // if语句
            this.processIfStmt(node);
        } else if (SyncTreeNodeKind.FOR_STMT.equals(nodeVal)) {
            // for 语句
            this.processForStmt(node);
        } else if (SyncTreeNodeKind.WHILE_STMT.equals(nodeVal)) {
            // while 语句
            this.processWhileStmt(node);
        } else if (SyncTreeNodeKind.DO_WHILE_STMT.equals(nodeVal)) {
            // do-while语句
            this.processDoWhileStmt(node);
        } else if (SyncTreeNodeKind.PRINT_STMT.equals(nodeVal)) {
            // print语句
            this.processPrintStmt(node);
        } else if (SyncTreeNodeKind.RETURN_STMT.equals(nodeVal)) {
            // return 语句
            this.processReturnStmt(node);
        } else if (SyncTreeNodeKind.EXPRESSION.equals(nodeVal)) {
            //如果遇到表达式 处理方案
            this.processExpression(node);
        } else if (node.getToken() == null) {
            // 没有获取到了token 递归调用儿子节点
            for (SyncTreeNode node1 : node.getChild()) {
                genNodeList(node1);
            }
        }

        // 如果建立了符号表 则需要删除
        if (canOpTable) {
            this.symbolTable.rmTable();
        }
    }


    /**
     * 处理do-while语句
     *
     * @param node 节点
     * @throws SemanticException 语义错误
     */
    private void processDoWhileStmt(SyncTreeNode node) throws SemanticException {
        this.symbolTable.mkTable();
        List<SyncTreeNode> nodeChild = node.getChild();
        String lastResult = this.codeList.getLastResult();
        this.processClassBody(nodeChild.get(2));
        this.processExpression(nodeChild.get(6));
        this.codeList.add("if", lastResult, "goto");
        this.symbolTable.rmTable();
    }

    /**
     * 处理返回值
     *
     * @param node 节点
     * @throws SemanticException 语义错误
     */
    private void processReturnStmt(SyncTreeNode node) throws SemanticException {
        List<SyncTreeNode> nodeChild = node.getChild();
        this.processExpression(nodeChild.get(1));
        String lastResult = this.codeList.getLastResult();
        this.codeList.add("return", lastResult, "_", "_");
    }

    /**
     * 处理打印语句
     *
     * @param node 节点
     * @throws SemanticException 语义错误
     */
    private void processPrintStmt(SyncTreeNode node) throws SemanticException {
        List<SyncTreeNode> nodeChild = node.getChild();
        this.processExpression(nodeChild.get(2));
        String lastResult = this.codeList.getLastResult();
        this.codeList.add("print", lastResult, "_", "_");
    }

    /**
     * 处理while语句
     *
     * @param node 节点
     * @throws SemanticException 语义错误
     */
    private void processWhileStmt(SyncTreeNode node) throws SemanticException {
        this.symbolTable.mkTable();
        List<SyncTreeNode> nodeChild = node.getChild();
        this.processExpression(nodeChild.get(2));
        String lastResult = this.codeList.getLastResult();
        this.codeList.add("if", lastResult, "goto");
        this.processBlockStmt(nodeChild.get(5));
        this.codeList.add("if", lastResult, "goto");
        this.symbolTable.rmTable();
    }

    /**
     * 处理for语句
     *
     * @param node 节点
     * @throws SemanticException 语义错误
     */
    private void processForStmt(SyncTreeNode node) throws SemanticException {
        this.symbolTable.mkTable();
        List<SyncTreeNode> nodeChild = node.getChild();
        SyncTreeNode node1 = nodeChild.get(2);
        this.symbolTable.mkTable();
        if (node1.getChild().size() == 1) {
            this.processExpression(node1.getChild().get(0));
        } else {
            this.processDeclareInter(node1);
        }
        this.symbolTable.rmTable();
        this.processExpression(nodeChild.get(4));
        String lastResult = this.codeList.getLastResult();
        this.codeList.add("if", lastResult, "goto");
        this.processBlockStmt(nodeChild.get(9));
        this.processExpression(nodeChild.get(6));
        this.codeList.add("if", lastResult, "goto");
        this.symbolTable.rmTable();
    }

    /**
     * 处理if语句
     *
     * @param node 节点
     * @throws SemanticException 语义错误
     */
    private void processIfStmt(SyncTreeNode node) throws SemanticException {
        this.symbolTable.mkTable();
        List<SyncTreeNode> nodeChild = node.getChild();
        this.processExpression(nodeChild.get(2));
        this.codeList.add("if", this.codeList.getLastResult(), "goto");
        this.processBlockStmt(nodeChild.get(5));
        List<SyncTreeNode> child1 = nodeChild.get(nodeChild.size() - 1).getChild();
        if (child1.size() == 2) {
            this.codeList.add("else_if", this.codeList.getLastResult(), "goto");
            this.processIfStmt(child1.get(1));
        } else if (child1.size() == 4) {
            this.codeList.add("else", this.codeList.getLastResult(), "goto");
            this.processBlockStmt(child1.get(2));
        }
        this.symbolTable.rmTable();
    }

    /**
     * 处理类
     *
     * @param node 节点
     * @throws SemanticException 语义错误
     */
    private void processClassBody(SyncTreeNode node) throws SemanticException {
        this.symbolTable.mkTable();
        List<SyncTreeNode> nodeChild = node.getChild();
        while (nodeChild.size() != 0) {
            SyncTreeNode node1 = nodeChild.get(0).getChild().get(0);
            if (SyncTreeNodeKind.DECLARE_CLASS.equals(node1.getVal())) {
                this.processDeclareClass(node1);
            } else {
                this.processMethodClass(node1);
            }
            nodeChild = nodeChild.get(1).getChild();
        }
        this.symbolTable.rmTable();
    }

    /**
     * 处理类中的方法
     *
     * @param node 节点
     * @throws SemanticException 语义错误
     */
    private void processMethodClass(SyncTreeNode node) throws SemanticException {
        this.symbolTable.mkTable();
        List<SyncTreeNode> nodeChild = node.getChild();
        // 获取定义的类型
        Token typeToken = nodeChild.get(2).getToken();
        // 定义的名字
        Token nameToken = nodeChild.get(3).getToken();
        this.processTypeDefId(typeToken, nameToken);
        //处理 ARGS
        this.processArgs(nodeChild.get(5));
        // 处理BLOCK_STMT
        this.processBlockStmt(nodeChild.get(8));
        this.symbolTable.rmTable();
    }

    /**
     * 处理代码块
     *
     * @param node 节点
     * @throws SemanticException 语义错误
     */
    private void processBlockStmt(SyncTreeNode node) throws SemanticException {
        this.symbolTable.mkTable();
        List<SyncTreeNode> child = node.getChild();
        while (child.size() != 0) {
            SyncTreeNode node1 = child.get(0);
            SyncTreeNode node2 = node1.getChild().get(0);
            if (SyncTreeNodeKind.DECLARE_INTER.equals(node2.getVal())) {
                this.processDeclareInter(node2);
            } else if (SyncTreeNodeKind.EXPRESSION.equals(node2.getVal())) {
                this.processExpression(node2);
            } else if (SyncTreeNodeKind.IF_STMT.equals(node2.getVal())) {
                this.processIfStmt(node2);
            } else if (SyncTreeNodeKind.FOR_STMT.equals(node2.getVal())) {
                this.processForStmt(node2);
            } else if (SyncTreeNodeKind.WHILE_STMT.equals(node2.getVal())) {
                this.processWhileStmt(node2);
            } else if (SyncTreeNodeKind.PRINT_STMT.equals(node2.getVal())) {
                this.processPrintStmt(node2);
            } else if (SyncTreeNodeKind.RETURN_STMT.equals(node2.getVal())) {
                this.processReturnStmt(node2);
            }
            child = child.get(1).getChild();
        }
        this.symbolTable.rmTable();
    }


    /**
     * 处理类中的域
     *
     * @param node 节点
     * @throws SemanticException 语义错误
     */
    @SuppressWarnings("DuplicatedCode")
    private void processDeclareClass(SyncTreeNode node) throws SemanticException {
        List<SyncTreeNode> nodeChild = node.getChild();
        // 获取定义的类型
        Token typeToken = nodeChild.get(2).getToken();
        // 定义的名字
        Token nameToken = nodeChild.get(3).getToken();
        this.processTypeDefId(typeToken, nameToken);
        if (nodeChild.get(4).getChild().size() != 0) {
            // 如果有赋值
            this.processExpression(nodeChild.get(4).getChild().get(1));
            this.codeList.add("=", nameToken.getVal(), this.codeList.getLastResult());
        }
        SyncTreeNode node1 = nodeChild.get(5);
        // 连续定义 逗号 ,
        while (node1.getChild().size() != 0) {
            nameToken = node1.getChild().get(1).getToken();
            this.processTypeDefId(typeToken, nameToken);
            // 如果有赋值
            if (node1.getChild().get(2).getChild().size() != 0) {
                this.processExpression(node1.getChild().get(2).getChild().get(1));
                this.codeList.add("=", nameToken.getVal(), this.codeList.getLastResult());
            }
            node1 = node1.getChild().get(5);
        }
    }

    /**
     * 处理代码快中定义的变量
     *
     * @param node 节点
     * @throws SemanticException 语义错误
     */
    private void processDeclareInter(SyncTreeNode node) throws SemanticException {
        List<SyncTreeNode> nodeChild = node.getChild();
        // 获取定义的类型
        Token typeToken = nodeChild.get(0).getToken();
        // 定义的名字
        Token nameToken = nodeChild.get(1).getToken();
        this.processTypeDefId(typeToken, nameToken);
        this.codeList.add(typeToken.getVal(), nameToken.getVal(), "_", "_");
        if (nodeChild.get(2).getChild().size() != 0) {
            // 如果有赋值
            this.processExpression(nodeChild.get(2).getChild().get(1));
            this.codeList.add("=", nameToken.getVal(), this.codeList.getLastResult(), "_");
        }
        SyncTreeNode node1 = nodeChild.get(3);
        // 连续定义 逗号 ,
        while (node1.getChild().size() != 0) {
            nameToken = node1.getChild().get(1).getToken();
            this.processTypeDefId(typeToken, nameToken);
            this.codeList.add(typeToken.getVal(), nameToken.getVal(), "_", "_");
            if (node1.getChild().get(2).getChild().size() != 0) {
                // 如果有赋值
                this.processExpression(node1.getChild().get(2).getChild().get(1));
                this.codeList.add("=", nameToken.getVal(), this.codeList.getLastResult(), "_");
            }
            node1 = node1.getChild().get(3);
        }
    }

    /**
     * 操作符号表 定义一个变量
     *
     * @param typeToken 类型
     * @param nameToken 变量
     * @throws SemanticException 语义错误
     */
    private void processTypeDefId(Token typeToken, Token nameToken) throws SemanticException {
        String name = nameToken.getVal();
        // 如果定义类型为非常量类型 则检查是否定义过
        if (SyncTreeNodeKind.ID.equals(typeToken.getKind().name()) && !this.symbolTable.search(typeToken.getVal(), Kind.CLASS)) {
            // 如果没有找到 报错
            new SemanticError(typeToken, ErrorType.NotDeclaredType);
        }
        // 检查现在定义的名字是否定义过
        if (this.symbolTable.searchLast(name)) {
            new SemanticError(nameToken, ErrorType.RedeclarationID);
        }
        Info tmp = new Info();
        // 其他 则加入符号表
        tmp.name = name;
        tmp.type = typeToken.getVal();
        tmp.kind = Kind.VAR;
        tmp.token = nameToken;
        this.symbolTable.enterInfo(tmp);
    }

    /**
     * 对表达式进行处理
     *
     * @param node 节点
     * @throws SemanticException 语义错误
     */
    private void processExpression(SyncTreeNode node) throws SemanticException {
        List<SyncTreeNode> nodeChild = node.getChild();
        if (nodeChild.size() == 1) {
            //说明只有一个儿子 value
            this.processValue(nodeChild.get(0));
        } else if (SyncTreeNodeKind.EXPRESSION.equals(nodeChild.get(0).getVal())) {
            // 处理 i++ ++i这样的情况
            this.processExpression(nodeChild.get(0));
            String arg1 = this.codeList.getLastResult();
            this.processExpression(nodeChild.get(2));
            String arg2 = this.codeList.getLastResult();
            this.codeList.add(nodeChild.get(1).getToken().getVal(), arg1, arg2);
        } else {
            // 其他正常情况 + - * /等
            this.processExpression(nodeChild.get(1));
        }
    }

    /**
     * 对一个单独的值进行处理
     *
     * @param node 节点
     * @throws SemanticException 语义错误
     */
    private void processValue(SyncTreeNode node) throws SemanticException {
        List<SyncTreeNode> nodeChild = node.getChild();
        if (nodeChild.size() == 1) {
            //只是一个常量
            this.codeList.add("=", nodeChild.get(0).getToken().getVal(), "_");
        } else {
            SyncTreeNode node0 = nodeChild.get(0);
            String idVal = nodeChild.get(1).getToken().getVal();
            // 检查ID是否已经定义过了
            if (!this.symbolTable.search(idVal)) {
                new SemanticError(nodeChild.get(1).getToken(), ErrorType.NotDeclaredID);
            }
            SyncTreeNode node2 = nodeChild.get(2);
            if (node0.getChild().size() != 0) {
                this.codeList.add(node0.getToken().getVal(), idVal, "_", idVal);
            }
            // 如果第一个是id  则为 i++
            if (node2.getChild().size() != 0) {
                // 保存上一次的值
                this.codeList.add("=", idVal, "_");
                String lastStr = this.codeList.getLastResult();
                this.codeList.add(node2.getToken().getVal(), idVal, "_", idVal);
                // 弄出来作为最后一个
                this.codeList.add("=", lastStr, "_");
            }
            if (node0.getChild().size() == 0 && node2.getChild().size() == 0) {
                this.codeList.add("=", idVal, "_");
            }
        }
    }

    /**
     * 对 函数中的变量声明进行处理
     *
     * @param node 处理 args
     * @throws SemanticException 语义错误
     */
    @SuppressWarnings("DuplicatedCode")
    private void processArgs(SyncTreeNode node) throws SemanticException {
        SyncTreeNode node1 = node;
        List<SyncTreeNode> node1Child = node1.getChild();
        // 看看是不是空 如果不是
        if (node1Child.size() != 0) {
            // 获取定义的类型
            Token typeToken = node1Child.get(0).getToken();
            Token nameToken = node1Child.get(1).getToken();
            this.processTypeDefId(typeToken, nameToken);

            // args 跳转到 arg
            node1 = node1Child.get(2);
            node1Child = node1.getChild();
            while (node1Child.size() != 0) {
                // 获取定义的类型
                typeToken = node1Child.get(1).getToken();
                nameToken = node1Child.get(2).getToken();
                this.processTypeDefId(typeToken, nameToken);
                node1 = node1Child.get(3);
                node1Child = node1.getChild();
            }
        }
    }

    /**
     * 生成全局的table 检查类/函数
     *
     * @param node 语法树
     */
    private void genGlobalTable(SyncTreeNode node) {
        if (node == null) {
            // 如果为null
            return;
        }
        String nodeVal = node.getVal();
        if (SyncTreeNodeKind.START.equals(nodeVal) || SyncTreeNodeKind.S.equals(nodeVal)) {
            //如果为start / s 直接检查儿子
            if (SyncTreeNodeKind.START.equals(nodeVal)) {
                // start 新建符号表  表示全局
                this.symbolTable.mkTable();
            }
            for (SyncTreeNode n : node.getChild()) {
                genGlobalTable(n);
            }
            return;
        }
        if (SyncTreeNodeKind.FUNC_S.equals(nodeVal) || SyncTreeNodeKind.CLASS_S.equals(nodeVal)) {
            // 如果是全局的 函数 / 类
            Info info = new Info();
            List<SyncTreeNode> child = node.getChild();
            String type = null;
            var indToken = child.get(2).getToken();
            String name = indToken.getVal();
            if (SyncTreeNodeKind.FUNC_S.equals(nodeVal)) {
                /*
                 * 全局函数
                 * 根据 { "left": "FUNC_S", "right": ["function","TYPEDEF","ID","(","ARGS",")","{","BLOCK_STMT","}"] },
                 * 从而得到对应的info
                 *  */
                type = child.get(1).getToken().getVal();
                List<SyncTreeNode> ARGS = child.get(4).getChild();
                List<String> args = new ArrayList<>();
                if (ARGS.size() != 0) {
                    args.add(ARGS.get(0).getToken().getVal());
                    var argsChild = ARGS.get(2).getChild();
                    while (argsChild.size() != 0) {
                        args.add(argsChild.get(1).getToken().getVal());
                        argsChild = argsChild.get(3).getChild();
                    }
                }
                info.kind = Kind.FUNCTION;
                info.type = type;
                info.name = name;
                info.args = args;
            } else {
                // 类
                if (child.get(0).getChild().size() != 0) {
                    type = child.get(0).getToken().getVal();
                }
                info.kind = Kind.CLASS;
                info.type = type;
                info.name = name;
            }
            info.token = indToken;
            this.symbolTable.enterInfo(info);
        }
    }

    /**
     * 检查类/函数
     *
     * @throws SemanticException 语义错误
     */
    private void cheekGlobalTable() throws SemanticException {
        List<Info> globalInfo = this.symbolTable.table.get(0);
        Map<String, Info> functionMap = new HashMap<>();
        Map<String, Info> classMap = new HashMap<>();
        for (Info info : globalInfo) {
            if (Kind.FUNCTION.equals(info.kind)) {
                // 如果是函数 查看是否重新定义
                String s = info.name + info.args.toString();
                if (functionMap.containsKey(s)) {
                    new SemanticError(info.token, ErrorType.RedeclarationVoid);
                } else {
                    functionMap.put(s, info);
                }
            } else {
                // 类
                if (classMap.containsKey(info.name)) {
                    new SemanticError(info.token, ErrorType.RedeclarationClass);
                } else {
                    classMap.put(info.name, info);
                }
            }
        }
        // 检查主函数
        boolean isOk = false;
        for (String str : functionMap.keySet()) {
            Info info = functionMap.get(str);
            if ("main".equals(info.name)) {
                if (info.args.size() == 0) {
                    isOk = true;
                    break;
                }
            }
        }
        if (!isOk) {
            new SemanticError(null, ErrorType.EnterMethodError);
        }
    }
}
