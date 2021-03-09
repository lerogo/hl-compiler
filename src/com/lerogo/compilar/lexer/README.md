## 测试 ##
配置json
```json
{
  "KEY_WORD": ["class", "main", "void", "function", "static",
    "char", "int", "boolean", "double", "bool","var",
    "if", "else", "while", "do", "for",
    "this", "return", "null", "print"],
  "OP": ["+", "-", "*", "/", "+=", "-=", "*=", "/=",
          "++", "--", "&", "|", "~", "&&", "||",
          "<", ">", "=", "<=", ">=", "==", "!="],
  "SYMBOL": [".", ",", ";", "(", ")", "[", "]", "{", "}"],
  "ID": "[_A-Za-z][_A-Za-z0-9]*",
  "CONSTANT": "((\"(.*)\")|('(\\\\)?[\\p{ASCII}]')|(\\d+(\\.\\d*)?i)|(\\d*(\\.)?\\d*(E([+\\-])\\d*(\\.)?\\d*)?)|false|true)"
}
```
测试代码文件
```javascript
//hhl.hl 测试 词法 语法分析
/**
 * 多行注释
 */

function test(){
    double ccc = 1.1;
    double ccc2 = 1.1E+3.1;
    var ccc3 = 1.1-3.8i;
    var ccc4 = 1.1-3.8+4.5;
    print(123);
    return "我可以的";
}

class HHLNB{
    void main(){
            // 注释
            /*注释测试*/
        int a = 12;
        int b = 14;
        double c = a + b * 2.4 + 1.2 - 1.2E+12;
        if(a<=b || b>c && a==c){
            print("哈哈哈\n");
            print('a');
            print("");
        }
        for(int i=0;i<10;++i){
            print(i);
        }
        print(c);
        bool hahah = false;
    }
}
错误测试(哈哈)
```
结果
```text
Token{row=6, kind=KEY_WORD, val='function'}
Token{row=6, kind=ID, val='test'}
Token{row=6, kind=SYMBOL, val='('}
Token{row=6, kind=SYMBOL, val=')'}
Token{row=6, kind=SYMBOL, val='{'}
Token{row=7, kind=KEY_WORD, val='double'}
Token{row=7, kind=ID, val='ccc'}
Token{row=7, kind=OP, val='='}
Token{row=7, kind=CONST, val='1.1'}
Token{row=7, kind=SYMBOL, val=';'}
Token{row=8, kind=KEY_WORD, val='double'}
Token{row=8, kind=ID, val='ccc2'}
Token{row=8, kind=OP, val='='}
Token{row=8, kind=CONST, val='1.1E+3.1'}
Token{row=8, kind=SYMBOL, val=';'}
Token{row=9, kind=KEY_WORD, val='var'}
Token{row=9, kind=ID, val='ccc3'}
Token{row=9, kind=OP, val='='}
Token{row=9, kind=CONST, val='1.1'}
Token{row=9, kind=OP, val='-'}
Token{row=9, kind=CONST, val='3.8i'}
Token{row=9, kind=SYMBOL, val=';'}
Token{row=10, kind=KEY_WORD, val='var'}
Token{row=10, kind=ID, val='ccc4'}
Token{row=10, kind=OP, val='='}
Token{row=10, kind=CONST, val='1.1'}
Token{row=10, kind=OP, val='-'}
Token{row=10, kind=CONST, val='3.8'}
Token{row=10, kind=OP, val='+'}
Token{row=10, kind=CONST, val='4.5'}
Token{row=10, kind=SYMBOL, val=';'}
Token{row=11, kind=KEY_WORD, val='print'}
Token{row=11, kind=SYMBOL, val='('}
Token{row=11, kind=CONST, val='123'}
Token{row=11, kind=SYMBOL, val=')'}
Token{row=11, kind=SYMBOL, val=';'}
Token{row=12, kind=KEY_WORD, val='return'}
Token{row=12, kind=CONST, val='"我可以的"'}
Token{row=12, kind=SYMBOL, val=';'}
Token{row=13, kind=SYMBOL, val='}'}
Token{row=15, kind=KEY_WORD, val='class'}
Token{row=15, kind=ID, val='HHLNB'}
Token{row=15, kind=SYMBOL, val='{'}
Token{row=16, kind=KEY_WORD, val='void'}
Token{row=16, kind=KEY_WORD, val='main'}
Token{row=16, kind=SYMBOL, val='('}
Token{row=16, kind=SYMBOL, val=')'}
Token{row=16, kind=SYMBOL, val='{'}
Token{row=19, kind=KEY_WORD, val='int'}
Token{row=19, kind=ID, val='a'}
Token{row=19, kind=OP, val='='}
Token{row=19, kind=CONST, val='12'}
Token{row=19, kind=SYMBOL, val=';'}
Token{row=20, kind=KEY_WORD, val='int'}
Token{row=20, kind=ID, val='b'}
Token{row=20, kind=OP, val='='}
Token{row=20, kind=CONST, val='14'}
Token{row=20, kind=SYMBOL, val=';'}
Token{row=21, kind=KEY_WORD, val='double'}
Token{row=21, kind=ID, val='c'}
Token{row=21, kind=OP, val='='}
Token{row=21, kind=ID, val='a'}
Token{row=21, kind=OP, val='+'}
Token{row=21, kind=ID, val='b'}
Token{row=21, kind=OP, val='*'}
Token{row=21, kind=CONST, val='2.4'}
Token{row=21, kind=OP, val='+'}
Token{row=21, kind=CONST, val='1.2'}
Token{row=21, kind=OP, val='-'}
Token{row=21, kind=CONST, val='1.2E+12'}
Token{row=21, kind=SYMBOL, val=';'}
Token{row=22, kind=KEY_WORD, val='if'}
Token{row=22, kind=SYMBOL, val='('}
Token{row=22, kind=ID, val='a'}
Token{row=22, kind=OP, val='<='}
Token{row=22, kind=ID, val='b'}
Token{row=22, kind=OP, val='||'}
Token{row=22, kind=ID, val='b'}
Token{row=22, kind=OP, val='>'}
Token{row=22, kind=ID, val='c'}
Token{row=22, kind=OP, val='&&'}
Token{row=22, kind=ID, val='a'}
Token{row=22, kind=OP, val='=='}
Token{row=22, kind=ID, val='c'}
Token{row=22, kind=SYMBOL, val=')'}
Token{row=22, kind=SYMBOL, val='{'}
Token{row=23, kind=KEY_WORD, val='print'}
Token{row=23, kind=SYMBOL, val='('}
Token{row=23, kind=CONST, val='"哈哈哈\n"'}
Token{row=23, kind=SYMBOL, val=')'}
Token{row=23, kind=SYMBOL, val=';'}
Token{row=24, kind=KEY_WORD, val='print'}
Token{row=24, kind=SYMBOL, val='('}
Token{row=24, kind=CONST, val=''a''}
Token{row=24, kind=SYMBOL, val=')'}
Token{row=24, kind=SYMBOL, val=';'}
Token{row=25, kind=KEY_WORD, val='print'}
Token{row=25, kind=SYMBOL, val='('}
Token{row=25, kind=CONST, val='""'}
Token{row=25, kind=SYMBOL, val=')'}
Token{row=25, kind=SYMBOL, val=';'}
Token{row=26, kind=SYMBOL, val='}'}
Token{row=27, kind=KEY_WORD, val='for'}
Token{row=27, kind=SYMBOL, val='('}
Token{row=27, kind=KEY_WORD, val='int'}
Token{row=27, kind=ID, val='i'}
Token{row=27, kind=OP, val='='}
Token{row=27, kind=CONST, val='0'}
Token{row=27, kind=SYMBOL, val=';'}
Token{row=27, kind=ID, val='i'}
Token{row=27, kind=OP, val='<'}
Token{row=27, kind=CONST, val='10'}
Token{row=27, kind=SYMBOL, val=';'}
Token{row=27, kind=OP, val='++'}
Token{row=27, kind=ID, val='i'}
Token{row=27, kind=SYMBOL, val=')'}
Token{row=27, kind=SYMBOL, val='{'}
Token{row=28, kind=KEY_WORD, val='print'}
Token{row=28, kind=SYMBOL, val='('}
Token{row=28, kind=ID, val='i'}
Token{row=28, kind=SYMBOL, val=')'}
Token{row=28, kind=SYMBOL, val=';'}
Token{row=29, kind=SYMBOL, val='}'}
Token{row=30, kind=KEY_WORD, val='print'}
Token{row=30, kind=SYMBOL, val='('}
Token{row=30, kind=ID, val='c'}
Token{row=30, kind=SYMBOL, val=')'}
Token{row=30, kind=SYMBOL, val=';'}
Token{row=31, kind=KEY_WORD, val='bool'}
Token{row=31, kind=ID, val='hahah'}
Token{row=31, kind=OP, val='='}
Token{row=31, kind=CONST, val='false'}
Token{row=31, kind=SYMBOL, val=';'}
Token{row=32, kind=SYMBOL, val='}'}
Token{row=33, kind=SYMBOL, val='}'}
Token{row=34, kind=ERROR, val='错'}
Token{row=34, kind=ERROR, val='误'}
Token{row=34, kind=ERROR, val='测'}
Token{row=34, kind=ERROR, val='试'}
Token{row=34, kind=SYMBOL, val='('}
Token{row=34, kind=ERROR, val='哈'}
Token{row=34, kind=ERROR, val='哈'}
Token{row=34, kind=SYMBOL, val=')'}
```
