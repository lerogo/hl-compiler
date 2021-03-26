package com.lerogo.compiler;

import com.lerogo.compiler.lexer.Lexer;
import com.lerogo.compiler.parser.AnalysisList;
/**
 * @author lerogo
 * @date 2021/3/7 17:23
 */
public class Main {
    public static void main(String[] args) {
        try{
            String projectLocation = System.getProperty("user.dir");

            String lexerConfigPath = projectLocation + "/src/com/lerogo/compiler/lexer/config.json";
            String fileName = projectLocation + "/test/main.hl";
            String parserConfigPath = projectLocation + "/src/com/lerogo/compiler/parser/config.json";

            Lexer lexer = new Lexer(lexerConfigPath, fileName);
            lexer.printToken();
            AnalysisList analysisList = new AnalysisList(parserConfigPath);
            System.out.println(analysisList.analyse(lexer));

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

    }
}


