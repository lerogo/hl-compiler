## 测试 ##
___变量定义错误检测有bug 需要修复 但是大体走通___

代码文件
```javascript
//hhl.hl 测试 词法 语法分析
/**
 * 多行注释
 */
function int test(int a,double b){
    double ccc = 1.1;
    double ccc2 = 1.1E+3.1;
    var ccc3 = 1.1-3.8i;
    var ccc4 = 1.1-3.8+4.5;
    print(123);
    print("我不管我最棒");
    return "我可以的"+ ++a++;
}

class HHLNB{
    public int i = 12;
    //public int i = 12;
    void main(){
            // 注释
        /* 哈哈哈哈 */
        int a,dd = 12;
        int b = 14;
        double c = a + b * 2.4 + 1.2 - 1.2E+12;
        int i;
        ++i;
        if(a<=b || b>c && a==c){
            print("哈哈哈\n");
            print('a');
            print("");
        }else{
            print("哈哈哈");
        }
        for(int i=0;i<10;++i){
            print(i);
        }
        print(c);
        bool hahah = false;
    }
}

//        dsah错误测试(哈哈)

function void main(){
    double ccc = 1.1;
    int a = ccc;
    double ccc2 = 1.1E+3.1;
    var ccc3 = 1.1-3.8i;
    var ccc4 = 1.1-3.8+4.5;
    print(123);
    print("我不管我最棒");
    return "我可以的"+ ++a;
}

```

结果

```text
double              ccc                 _                   _                   
=                   1.1                 _                   T1                  
=                   ccc                 T1                  _                   
double              ccc2                _                   _                   
=                   1.1E+3.1            _                   T4                  
=                   ccc2                T4                  _                   
var                 ccc3                _                   _                   
=                   1.1                 _                   T7                  
=                   3.8i                _                   T8                  
-                   T7                  T8                  T9                  
=                   ccc3                T9                  _                   
var                 ccc4                _                   _                   
=                   1.1                 _                   T12                 
=                   3.8                 _                   T13                 
-                   T12                 T13                 T14                 
=                   4.5                 _                   T15                 
+                   T14                 T15                 T16                 
=                   ccc4                T16                 _                   
=                   123                 _                   T18                 
print               T18                 _                   _                   
=                   "我不管我最棒"            _                   T20                 
print               T20                 _                   _                   
=                   "我可以的"              _                   T22                 
++                  a                   _                   a                   
=                   a                   _                   T24                 
++                  a                   _                   a                   
=                   T24                 _                   T26                 
+                   T22                 T26                 T27                 
return              T27                 _                   _                   
=                   12                  _                   T29                 
=                   i                   T29                 T30                 
int                 a                   _                   _                   
int                 dd                  _                   _                   
=                   12                  _                   T33                 
=                   dd                  T33                 _                   
int                 b                   _                   _                   
=                   14                  _                   T36                 
=                   b                   T36                 _                   
double              c                   _                   _                   
=                   a                   _                   T39                 
=                   b                   _                   T40                 
+                   T39                 T40                 T41                 
=                   2.4                 _                   T42                 
*                   T41                 T42                 T43                 
=                   1.2                 _                   T44                 
+                   T43                 T44                 T45                 
=                   1.2E+12             _                   T46                 
-                   T45                 T46                 T47                 
=                   c                   T47                 _                   
int                 i                   _                   _                   
++                  i                   _                   i                   
=                   a                   _                   T51                 
=                   b                   _                   T52                 
<=                  T51                 T52                 T53                 
=                   b                   _                   T54                 
||                  T53                 T54                 T55                 
=                   c                   _                   T56                 
>                   T55                 T56                 T57                 
=                   a                   _                   T58                 
&&                  T57                 T58                 T59                 
=                   c                   _                   T60                 
==                  T59                 T60                 T61                 
if                  T61                 goto                T62                 
=                   "哈哈哈\n"             _                   T63                 
print               T63                 _                   _                   
=                   'a'                 _                   T65                 
print               T65                 _                   _                   
=                   ""                  _                   T67                 
print               T67                 _                   _                   
else                _                   goto                T69                 
=                   "哈哈哈"               _                   T70                 
print               T70                 _                   _                   
int                 i                   _                   _                   
=                   0                   _                   T73                 
=                   i                   T73                 _                   
=                   i                   _                   T75                 
=                   10                  _                   T76                 
<                   T75                 T76                 T77                 
if                  T77                 goto                T78                 
=                   i                   _                   T79                 
print               T79                 _                   _                   
++                  i                   _                   i                   
if                  T77                 goto                T82                 
=                   c                   _                   T83                 
print               T83                 _                   _                   
bool                hahah               _                   _                   
=                   false               _                   T86                 
=                   hahah               T86                 _                   
double              ccc                 _                   _                   
=                   1.1                 _                   T89                 
=                   ccc                 T89                 _                   
int                 a                   _                   _                   
=                   ccc                 _                   T92                 
=                   a                   T92                 _                   
double              ccc2                _                   _                   
=                   1.1E+3.1            _                   T95                 
=                   ccc2                T95                 _                   
var                 ccc3                _                   _                   
=                   1.1                 _                   T98                 
=                   3.8i                _                   T99                 
-                   T98                 T99                 T100                
=                   ccc3                T100                _                   
var                 ccc4                _                   _                   
=                   1.1                 _                   T103                
=                   3.8                 _                   T104                
-                   T103                T104                T105                
=                   4.5                 _                   T106                
+                   T105                T106                T107                
=                   ccc4                T107                _                   
=                   123                 _                   T109                
print               T109                _                   _                   
=                   "我不管我最棒"            _                   T111                
print               T111                _                   _                   
=                   "我可以的"              _                   T113                
++                  a                   _                   a                   
+                   T113                a                   T115                
return              T115                _                   _                   

```

错误测试
```javascript
//hhl.hl 测试 词法 语法分析
/**
 * 多行注释
 */

function int test(int a,double b){
    double ccc = 1.1;
    int ccc;
    double ccc2 = 1.1E+3.1;
    var ccc3 = 1.1-3.8i;
    var ccc4 = 1.1-3.8+4.5;
    print(123);
    print("我不管我最棒");
    return "我可以的"+ ++a++;
}


class HHLNB{
    public int i = 12;
    //public int i = 12;
    void main(){
            // 注释
        /* 哈哈哈哈 */
        int a,dd = 12;
        int b = 14;
        double c = a + b * 2.4 + 1.2 - 1.2E+12;
        int i;
        ++i;
        if(a<=b || b>c && a==c){
            print("哈哈哈\n");
            print('a');
            print("");
        }else{
            print("哈哈哈");
        }
        for(int i=0;i<10;++i){
            print(i);
        }
        print(c);
        bool hahah = false;
    }
}

//        dsah错误测试(哈哈)

function void main(){
    double ccc = 1.1;
    int a = ccc;
    double ccc2 = 1.1E+3.1;
    var ccc3 = 1.1-3.8i;
    var ccc4 = 1.1-3.8+4.5;
    print(123);
    print("我不管我最棒");
    return "我可以的"+ ++a;
}

```

```text
    double ccc = 1.1;
    int ˇccc;
    double ccc2 = 1.1E+3.1;
SemanticException{row=8, col=9, val=ccc, error=变量重定义}
```