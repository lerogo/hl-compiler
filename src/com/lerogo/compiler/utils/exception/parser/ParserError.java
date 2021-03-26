package com.lerogo.compiler.utils.exception.parser;

import com.lerogo.compiler.lexer.Token;
import com.lerogo.compiler.utils.file.FileReader;

import java.util.Set;

/**
 * @author lerogo
 * @date 2021/3/26 11:13
 */
public class ParserError {
    private final FileReader fileReader;

    public ParserError(FileReader fileReader) {
        this.fileReader = fileReader;
    }

    public void cheekGrammar(Set<String> keys, Token t) throws GrammarException {
        if (fileReader == null) {
            if (t == null) {
                throw new GrammarException(keys, 0);
            } else {
                throw new GrammarException(keys, t);
            }
        } else {
            int codeSize = this.fileReader.getReadRow().size();
            if (t == null) {
                for (int i = Math.max(codeSize - 2, 0); i < codeSize; ++i) {
                    System.err.println(this.fileReader.getIndRow(i));
                }
                System.err.print("ˇ");
                System.err.println();
                throw new GrammarException(keys, codeSize);
            } else {
                int codeRow = t.getRow();
                int codeCol = t.getCol();
                if (codeRow == 1) {
                    String str = this.fileReader.getIndRow(0);
                    for (int i = 0; i < str.length(); ++i) {
                        if (i == codeCol - 1) {
                            System.err.print("ˇ");
                        }
                        System.err.print(str.charAt(i));
                    }
                } else {
                    System.err.println(this.fileReader.getIndRow(codeRow - 2));
                    String str = this.fileReader.getIndRow(codeRow - 1);
                    for (int i = 0; i < str.length(); ++i) {
                        if (i == codeCol - 1) {
                            System.err.print("ˇ");
                        }
                        System.err.print(str.charAt(i));
                    }
                }
                System.err.println();
                if (codeRow < codeSize) {
                    System.err.println(this.fileReader.getIndRow(codeRow));
                }
                throw new GrammarException(keys, t);
            }
        }
    }
}













