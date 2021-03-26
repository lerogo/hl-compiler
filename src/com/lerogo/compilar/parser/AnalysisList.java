package com.lerogo.compilar.parser;

import com.alibaba.fastjson.JSON;
import com.lerogo.compilar.lexer.Lexer;
import com.lerogo.compilar.utils.exception.file.ReadFileException;
import com.lerogo.compilar.utils.exception.parser.GrammarException;
import com.lerogo.compilar.utils.exception.parser.ParserError;
import com.lerogo.compilar.utils.file.FileReader;
import org.apache.commons.io.IOUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @author lerogo
 * @date 2021/3/24 10:12
 */
public class AnalysisList {
    /**
     * 配置文件的路径
     */
    private final String configPath;

    /**
     * 使用json读取产生式
     */
    private final Production production;

    /**
     * 生成的action goto表等
     */
    private final SyncTable syncTable;

    /**
     * 读取的fileReader 用于错误打印输入文本
     */
    private FileReader fileReader = null;


    /**
     * @param configPath 配置文件地址（文法产生式）
     * @throws IOException  读取文件出错
     */
    public AnalysisList(String configPath) throws IOException {
        this.configPath = configPath;
        String jsonText = IOUtils.toString(new FileInputStream(configPath), StandardCharsets.UTF_8);
        this.production = JSON.parseObject(jsonText, Production.class);
        this.syncTable = new SyncTable(this.production);
    }

    /**
     * @param lexer 传入的lexer对象 包含token列表
     * @return 分析成功与否
     * @throws GrammarException 语法出错
     */
    public boolean analyse(Lexer lexer) throws GrammarException {
        try {
            this.fileReader = new FileReader(lexer.getFileName());
        } catch (ReadFileException readFileException) {
            readFileException.printStackTrace();
        }
        return this.syncTable.syncTokenList(lexer, new ParserError(this.fileReader));
    }

    /**
     * 打印根据语法生成的 项目集族 DFA
     */
    public void printItemSetList() {
        System.out.println(this.syncTable.getItemSetList());
    }

    /**
     * 打印生成的action goto 表
     */
    public void printActionGotoTable() {
        System.out.println(this.syncTable.getAgList());
    }

    /**
     * 打印分析栈
     */
    public void printAnalysisTable() {
        System.out.println(this.syncTable.getAnalysisTable());
    }

}
