package com.lerogo.compiler.utils.exception.semantic;

/**
 * @author lerogo
 * @date 2021/4/2 17:16
 */
public enum ErrorType {
    /**
     * 参数数量错误
     */
    ArgsNumError,
    /**
     * 主函数错误
     */
    EnterMethodError,
    /**
     * 函数类型错误
     */
    MethodTypeError,
    /**
     * 变量未定义
     */
    NotDeclaredID,
    /**
     * 类型未定义
     */
    NotDeclaredType,
    /**
     * 找不到入口函数
     */
    NotEnterMethod,
    /**
     * 变量重定义
     */
    RedeclarationID,
    /**
     * 类重定义
     */
    RedeclarationClass,
    /**
     * 函数重定义
     */
    RedeclarationVoid,
    /**
     * 返回值类型错误
     */
    ReturnTypeError,
}
