package com.lerogo.compiler.utils.exception.semantic;

/**
 * @author lerogo
 * @date 2021/3/27 18:38
 */
public abstract class SemanticException extends Exception {
    SemanticException(String remindException) {
        super(remindException);
    }

    SemanticException(String remindException, String val, int row, int col) {
        super(
                "SemanticException{" +
                        "row=" + row +
                        ", col=" + col +
                        ", val=" + val +
                        ", error=" + remindException +
                        "}"
        );
    }
}

class RedeclarationVoid extends SemanticException {
    private static final String REMIND_EXCEPTION = "函数重定义";

    RedeclarationVoid() {
        super(REMIND_EXCEPTION);
    }

    RedeclarationVoid(String val, int row, int col) {
        super(REMIND_EXCEPTION, val, row, col);
    }
}

class RedeclarationID extends SemanticException {
    private static final String REMIND_EXCEPTION = "变量重定义";

    RedeclarationID() {
        super(REMIND_EXCEPTION);
    }

    RedeclarationID(String val, int row, int col) {
        super(REMIND_EXCEPTION, val, row, col);
    }
}

class NotDeclaredType extends SemanticException {
    private static final String REMIND_EXCEPTION = "类型未定义";

    NotDeclaredType() {
        super(REMIND_EXCEPTION);
    }

    NotDeclaredType(String val, int row, int col) {
        super(REMIND_EXCEPTION, val, row, col);
    }
}

class NotDeclaredID extends SemanticException {
    private static final String REMIND_EXCEPTION = "变量未定义";

    NotDeclaredID() {
        super(REMIND_EXCEPTION);
    }

    NotDeclaredID(String val, int row, int col) {
        super(REMIND_EXCEPTION, val, row, col);
    }
}

class MethodTypeError extends SemanticException {
    private static final String REMIND_EXCEPTION = "函数类型错误";

    MethodTypeError() {
        super(REMIND_EXCEPTION);
    }

    MethodTypeError(String val, int row, int col) {
        super(REMIND_EXCEPTION, val, row, col);
    }
}

class ReturnTypeError extends SemanticException {
    private static final String REMIND_EXCEPTION = "返回值类型错误";

    ReturnTypeError() {
        super(REMIND_EXCEPTION);
    }

    ReturnTypeError(String val, int row, int col) {
        super(REMIND_EXCEPTION, val, row, col);
    }
}

class ArgsNumError extends SemanticException {
    private static final String REMIND_EXCEPTION = "参数数量错误";

    ArgsNumError() {
        super(REMIND_EXCEPTION);
    }

    ArgsNumError(String val, int row, int col) {
        super(REMIND_EXCEPTION, val, row, col);
    }
}

class NotEnterMethod extends SemanticException {
    private static final String REMIND_EXCEPTION = "找不到入口函数";

    NotEnterMethod() {
        super(REMIND_EXCEPTION);
    }

    NotEnterMethod(String val, int row, int col) {
        super(REMIND_EXCEPTION, val, row, col);
    }
}

class EnterMethodError extends SemanticException {
    private static final String REMIND_EXCEPTION = "主函数错误";

    EnterMethodError() {
        super(REMIND_EXCEPTION);
    }

    EnterMethodError(String val, int row, int col) {
        super(REMIND_EXCEPTION, val, row, col);
    }
}

class RedeclarationClass extends SemanticException {
    private static final String REMIND_EXCEPTION = "类重定义";

    RedeclarationClass() {
        super(REMIND_EXCEPTION);
    }

    RedeclarationClass(String val, int row, int col) {
        super(REMIND_EXCEPTION, val, row, col);
    }
}
