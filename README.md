# hl-Compiler #

## 关于作者 ##
- [@lerogo](https://github.com/lerogo/ "@lerogo"), 我的[博客](https://blog.lerogo.com/)，如果你想[给我点钱买葡萄冻冻](https://pay.lerogo.com/)🤣
- 当前代码仓库 [hl-Compiler](https://github.com/lerogo/hl-compiler)
- 2021-04-04 星期日

---

## 介绍 ##
准备实现一个简单的编译器😄
  - 词法分析（✅）
  - 语法分析（✅）
  - 语义分析（✅）

---

## 词法分析器 ##
### 介绍 ###
需要两个文件
 - 解析的代码文件
 - 配置文件 见`src/com/lerogo/compiler/lexer/config.json` json配置
   - 关键字 KEY_WORD
   - 运算符 OP
   - 界符 SYMBOL
   - 标识符 ID （正则表达式）
   - 常量 CONSTANT （正则表达式）

### 测试demo ###
[测试demo](https://github.com/lerogo/hl-compiler/blob/master/src/com/lerogo/compiler/lexer/README.md "测试demo")

---

## 语法分析 ##
### 介绍 ###
需要上述词法分析的对象 以及配置文件
- 解析的代码文件
- 配置文件 见`src/com/lerogo/compiler/parser/config.json` json配置
- 需要配置"START"的增广文法 或者修改代码将"START"改为其他的

### 测试demo ###
[测试demo](https://github.com/lerogo/hl-compiler/blob/master/src/com/lerogo/compiler/parser/README.md "测试demo")

---

## 语义计算 ##
### 介绍 ###
需要上述词法分析的对象（主要是文件名 打印错误用）
需要上述语法分析的语法树 用于dfs处理
- 解析的代码文件
- 配置文件 无 语义分析写死在代码里面了
- 可能有bug
- 走通了一遍 并不代表能够在机器上跑 配置文件的语义太多了

### 测试demo ###
[测试demo](https://github.com/lerogo/hl-compiler/blob/master/src/com/lerogo/compiler/semantic/README.md "测试demo")

---

## 截图 ##
### 词法分析 ###
![词法分析token](https://github.com/lerogo/hl-compiler/blob/master/screenshot/1.png)
![词法分析出错](https://github.com/lerogo/hl-compiler/blob/master/screenshot/2.png)

### 语法分析 ###
![语法分析项目集族](https://github.com/lerogo/hl-compiler/blob/master/screenshot/3.png)
![语法分析ActionGoto表](https://github.com/lerogo/hl-compiler/blob/master/screenshot/4.png)
![语法分析栈](https://github.com/lerogo/hl-compiler/blob/master/screenshot/5.png)
![语法分析出错](https://github.com/lerogo/hl-compiler/blob/master/screenshot/6.png)

### 语义分析 ###
![语义分析结果](https://github.com/lerogo/hl-compiler/blob/master/screenshot/7.png)
![语义分析出错](https://github.com/lerogo/hl-compiler/blob/master/screenshot/8.png)

---

## 如何使用 ##
将lib包配置到项目文件 运行Main即可

---