package com.lerogo.compilar;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.*;

import com.alibaba.fastjson.*;
import com.lerogo.compilar.lexer.Lexer;
import com.lerogo.compilar.lexer.Token;

/**
 * @author lerogo
 * @date 2021/3/7 17:23
 */
public class Main {
    public static void main(String[] args) throws IOException {
        String lexerConfigPath = "";
        String fileName = "";
        // token测试
        List<Token> tokens = new Lexer(lexerConfigPath, fileName).getTokens();
        for (Token a:tokens){
            System.out.println(a);
        }
        // pass
    }
}
