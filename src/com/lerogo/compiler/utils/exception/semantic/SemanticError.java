package com.lerogo.compiler.utils.exception.semantic;

import com.lerogo.compiler.lexer.Token;
import com.lerogo.compiler.utils.file.FileReader;

/**
 * @author lerogo
 * @date 2021/3/27 18:37
 */
public class SemanticError {
    private static FileReader fileReader = null;

    public static void setFileReader(FileReader fileReader) {
        SemanticError.fileReader = fileReader;
    }

    public SemanticError(Token token, ErrorType type) throws SemanticException {
        if (token != null) {
            if (fileReader != null) {
                int codeSize = fileReader.getReadRow().size();
                int codeRow = token.getRow();
                int codeCol = token.getCol();
                if (codeRow == 1) {
                    String str = fileReader.getIndRow(0);
                    for (int i = 0; i < str.length(); ++i) {
                        if (i == codeCol - 1) {
                            System.err.print("ˇ");
                        }
                        System.err.print(str.charAt(i));
                    }
                } else {
                    System.err.println(fileReader.getIndRow(codeRow - 2));
                    String str = fileReader.getIndRow(codeRow - 1);
                    for (int i = 0; i < str.length(); ++i) {
                        if (i == codeCol - 1) {
                            System.err.print("ˇ");
                        }
                        System.err.print(str.charAt(i));
                    }
                }
                System.err.println();
                if (codeRow < codeSize) {
                    System.err.println(fileReader.getIndRow(codeRow));
                }
            }
            switch (type) {
                case ArgsNumError:
                    throw new ArgsNumError(token.getVal(), token.getRow(), token.getCol());
                case EnterMethodError:
                    throw new EnterMethodError(token.getVal(), token.getRow(), token.getCol());
                case MethodTypeError:
                    throw new MethodTypeError(token.getVal(), token.getRow(), token.getCol());
                case NotDeclaredID:
                    throw new NotDeclaredID(token.getVal(), token.getRow(), token.getCol());
                case NotDeclaredType:
                    throw new NotDeclaredType(token.getVal(), token.getRow(), token.getCol());
                case NotEnterMethod:
                    throw new NotEnterMethod(token.getVal(), token.getRow(), token.getCol());
                case RedeclarationClass:
                    throw new RedeclarationClass(token.getVal(), token.getRow(), token.getCol());
                case RedeclarationID:
                    throw new RedeclarationID(token.getVal(), token.getRow(), token.getCol());
                case RedeclarationVoid:
                    throw new RedeclarationVoid(token.getVal(), token.getRow(), token.getCol());
                case ReturnTypeError:
                    throw new ReturnTypeError(token.getVal(), token.getRow(), token.getCol());
                default:
                    break;
            }
        } else {
            switch (type) {
                case ArgsNumError:
                    throw new ArgsNumError();
                case EnterMethodError:
                    throw new EnterMethodError();
                case MethodTypeError:
                    throw new MethodTypeError();
                case NotDeclaredID:
                    throw new NotDeclaredID();
                case NotDeclaredType:
                    throw new NotDeclaredType();
                case NotEnterMethod:
                    throw new NotEnterMethod();
                case RedeclarationClass:
                    throw new RedeclarationClass();
                case RedeclarationID:
                    throw new RedeclarationID();
                case RedeclarationVoid:
                    throw new RedeclarationVoid();
                case ReturnTypeError:
                    throw new ReturnTypeError();
                default:
                    break;
            }
        }
    }
}
