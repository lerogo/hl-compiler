package com.lerogo.compilar;

import com.lerogo.compilar.lexer.Lexer;
import com.lerogo.compilar.parser.AnalysisList;

/**
 * @author lerogo
 * @date 2021/3/7 17:23
 */
public class Main {
    public static void main(String[] args) {
        try{
            String projectLocation = System.getProperty("user.dir");

            String lexerConfigPath = projectLocation + "/src/com/lerogo/compilar/lexer/config.json";
            String fileName = projectLocation + "/main.hl";
            String parserConfigPath = projectLocation + "/src/com/lerogo/compilar/parser/config.json";

            Lexer lexer = new Lexer(lexerConfigPath, fileName);
            lexer.printToken();
            AnalysisList analysisList = new AnalysisList(parserConfigPath);
            analysisList.printItemSetList();
            analysisList.printActionGotoTable();
            analysisList.analyse(lexer);
            System.out.println();
            analysisList.printAnalysisTable();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

    }
}


