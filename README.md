# Simple_Java_Regex_Engine
It's a project which shows how I learn Java and try to create an engine of regex. Probably it won't be compeleted(x).  
惊了，一不小心把项目名称打成昵称了，算了。。。  
----------------------------------------------------------------------------------  
使用说明：  
初始化：  
提供两种初始化方法，  
1. Re re = new Re(expression);  
2. Re re = new Re(expression,"macroname1 : macrovalue","macrovalue2 : macrovalue",....);  
其中第二种用于宏定义，例如如果传入" D : a|b"，那么表达式中的所有{D}都会被替换为(a|b)。  
即：  
Re re = new Re("{D}a"," D : a|b");
等价于  
Re re = new Re("(a|b)a");
   
支持的方法：  
match方法：用于检查传入字符串是否“完全”匹配正则表达式。需要注意的是，match方法相当于自带开始符和结束符(^和$)，因此不再特意支持^$.  cotains方法：用于检查传入字符串中是否包含匹配表达式的子字符串，相当于没有开始结束符的match。  
search方法：查找并返回第一个匹配上表达式的子字符串首字母索引位置。  
getre方法：查看表达式。
支持的语法：  
支持'()'、连接和'|'。  
支持.（匹配任意字符），\s（匹配空白符），\w（匹配字母、数字和下划线）。  
支持宏定义，见初始化。  
重复限定支持* + ？，支持{n},{n,}，但不支持{m,n}，原因可见DAY7的日记。  
支持[0-9],[c-h],[G-Y],但不支持将它们写在同一个方框里，即[a-z0-9],请使用'|'符号，即[a-z]|[0-9]。  
支持操作符的转义，即\+等。  
效率：  
效率？不存在的。  
使用示例：  
见com.RE.Engine.Test.java  
缺点：  
数不胜数。支持的功能太少，效率太低，只能作为玩具。没有学java的错误捕获，因此正则表达式的语法正确性完全由使用者判断。  
开发及测试运行环境：  
JDK9 On Windows10.  
  
