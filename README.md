# hl-Compiler #

## 介绍 ##
准备实现一个简单的编译器😄
  - 词法分析（✅）
  - 语法分析（✅）
  - 语义计算（❎）
  - 优化（❎）

---

## 词法分析器 ##
### 介绍 ###
需要两个文件
 - 解析的代码文件
 - 配置文件 见`src/com/lerogo/compilar/lexer/config.json` json配置
   - 关键字 KEY_WORD
   - 运算符 OP
   - 界符 SYMBOL
   - 标识符 ID （正则表达式）
   - 常量 CONSTANT （正则表达式）

### 测试demo ###
[测试demo](https://github.com/lerogo/hl-compiler/blob/master/src/com/lerogo/compilar/lexer/README.md "测试demo")

---

## 语法分析 ##
### 介绍 ###
需要上述词法分析的对象 以及配置文件
- 解析的代码文件
- 配置文件 见`src/com/lerogo/compilar/parser/config.json` json配置
- 需要配置"START"的增广文法 或者修改代码将"START"改为其他的

### 测试demo ###
[测试demo](https://github.com/lerogo/hl-compiler/blob/master/src/com/lerogo/compilar/parser/README.md "测试demo")

---

## 语义计算 ##
待实现

---

## 如何使用 ##
将lib包配置到项目文件 运行Main即可

---

## 关于作者 ##
- [@lerogo](https://github.com/lerogo/ "@lerogo"), 我的[博客](https://blog.lerogo.com/)，如果你想[给我点钱买葡萄冻冻](https://pay.lerogo.com/)🤣
- 当前代码仓库 [hl-Compiler](https://github.com/lerogo/hl-compiler)
- 2021-03-26 星期五
