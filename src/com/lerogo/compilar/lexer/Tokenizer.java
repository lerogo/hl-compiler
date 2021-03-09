package com.lerogo.compilar.lexer;

import com.alibaba.fastjson.JSON;
import com.lerogo.compilar.utils.exception.file.ReadFileError;
import com.lerogo.compilar.utils.file.FileReader;
import org.apache.commons.io.IOUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 将代码转为TOKEN的列表
 * NFA DFA处理等
 *
 * @author lerogo
 * @date 2021/3/7 19:31
 */
class Tokenizer {
    /**
     * 配置文件的路径
     */
    private final String configPath;

    /**
     * 判断类型的类 使用json获取配置
     */
    private final JudgeType jp;

    private final FileReader codeRow;

    /**
     * 生成的token列表
     */
    private final List<Token> tokens = new ArrayList<>();


    /**
     * @return tokens 获取的token
     */
    public List<Token> getTokens() {
        return tokens;
    }


    /**
     * 必须传入配置文件和代码文件
     *
     * @param configPath
     * @param fileName
     * @throws ReadFileError
     * @throws IOException
     */
    Tokenizer(String configPath, String fileName) throws ReadFileError, IOException {
        this.codeRow = new FileReader(fileName);
        this.configPath = configPath;
        String jsonText = IOUtils.toString(new FileInputStream(configPath), StandardCharsets.UTF_8);
        this.jp = JSON.parseObject(jsonText, JudgeType.class);
        this.genTokens();
    }

    /**
     * 生成token
     */
    void genTokens() {
        String code;
        boolean isNoteState = false;
        while ((code = codeRow.nextRow()) != null) {
            StringBuilder word = new StringBuilder();
            for (int i = 0; i < code.length(); ++i) {
                char c = code.charAt(i);
                // 去掉空字符
                while (this.isBlank(c)) {
                    i++;
                    c = code.charAt(i);
                }
                //注释处理 这里也可以使用正则进行ignore
                if (!isNoteState && i < code.length() - 1 && c == '/' && code.charAt(i + 1) == '*') {
                    isNoteState = true;
                    i++;
                    if (word.length() != 0) {
                        tokens.add(new Token(codeRow.getRowInd(), this.jp.getTokenType(word.toString()), word.toString()));
                        word = new StringBuilder();
                    }
                    continue;
                }
                if (isNoteState) {
                    if (i < code.length() - 1 && c == '*' && code.charAt(i + 1) == '/') {
                        isNoteState = false;
                        break;
                    } else {
                        continue;
                    }
                }
                if (i < code.length() - 1 && c == '/' && code.charAt(i + 1) == '/') {
                    if (word.length() != 0) {
                        tokens.add(new Token(codeRow.getRowInd(), this.jp.getTokenType(word.toString()), word.toString()));
                    }
                    break;
                }

                /*
                 * 先是constant的处理 然后是标识符的处理
                 * 其中关键字满足标识符要求 所以就可以在此把标识符号一起处理了
                 * */
                boolean flag = true;
                List<Pattern> tmpPlist = Arrays.asList(this.jp.getCONSTANT(), this.jp.getID());
                for (Pattern tmpPattern : tmpPlist) {
                    //满足的表达式
                    int tmp = PatternLastInd(tmpPattern, code.substring(i));
                    if (tmp != 0) {
                        flag = false;
                        String tmpStr = code.substring(i, i + tmp);
                        tokens.add(new Token(codeRow.getRowInd(), this.jp.getTokenType(tmpStr), tmpStr));
                        i = i + tmp - 1;
                        //要是word还有符号 那么写入token
                        if (word.length() != 0) {
                            tokens.add(new Token(codeRow.getRowInd(), this.jp.getTokenType(word.toString()), word.toString()));
                            word = new StringBuilder();
                        }
                        break;
                    }
                }
                // 这里是符号处理 包括两个符号的配置 所以有 i < code.length() - 1
                if (flag) {
                    word.append(c);
                    if (i < code.length() - 1 && this.jp.getOP().contains(word.toString() + code.charAt(i + 1))) {
                        tokens.add(new Token(codeRow.getRowInd(), this.jp.getTokenType(word.toString() + code.charAt(i + 1)), word.toString() + code.charAt(i + 1)));
                        i++;
                    } else {
                        tokens.add(new Token(codeRow.getRowInd(), this.jp.getTokenType(word.toString()), word.toString()));
                    }
                    word = new StringBuilder();
                }
            }
        }
    }

    /**
     * 匹配得到的结果
     *
     * @param p
     * @param str
     * @return
     */
    int PatternLastInd(Pattern p, String str) {
        Matcher matcher = p.matcher(str);
        if (matcher.find()) {
            return matcher.end();
        } else {
            return 0;
        }
    }

    /**
     * @param c 是否是空白的字符
     * @return
     */
    boolean isBlank(char c) {
        return c == ' ' || c == '\t' || c == '\n';
    }

}










