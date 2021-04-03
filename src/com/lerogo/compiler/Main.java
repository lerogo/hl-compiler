package com.lerogo.compiler;

import com.lerogo.compiler.lexer.Lexer;
import com.lerogo.compiler.parser.AnalysisList;
import com.lerogo.compiler.parser.SyncTreeNode;
import com.lerogo.compiler.semantic.Analyzer;

/**
 * @author lerogo
 * @date 2021/3/7 17:23
 */
public class Main {
    public static void main(String[] args) {
        try {
            String projectLocation = System.getProperty("user.dir");

            String lexerConfigPath = projectLocation + "/src/com/lerogo/compiler/lexer/config.json";
            String fileName = projectLocation + "/test/main.hl";
            String parserConfigPath = projectLocation + "/src/com/lerogo/compiler/parser/config.json";

            // 词法分析
            Lexer lexer = new Lexer(lexerConfigPath, fileName);
            // 打印token表
            lexer.printToken();

            // 语法分析
            AnalysisList analysisList = new AnalysisList(parserConfigPath);
            // 打印项目集族
            analysisList.printItemSetList();
            // 打印goto表
            analysisList.printActionGotoTable();
            // 是否分析成功 不成功会报错 自动终止
            boolean analyseOk = analysisList.analyse(lexer);
            // 打印分析栈
            analysisList.printAnalysisTable();
            // 拿到语法树
            SyncTreeNode treeNode = analysisList.getTreeNode();

            // 语义分析
            Analyzer analyzer = new Analyzer(lexer, treeNode);
            // 开始分析
            analyzer.cheek();
            // 打印四元式
            analyzer.printCodeList();

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

    }
}


