项目总结：  
这是本人自八月中旬接触编程以来完成的第一个完整项目，完全可以说给我带来了很宝贵的经验。  
首先当然是满打满算才学过一个半月py就用一天学java的经验了（笑），可以说一边搭积木一边google语法来学的过程还是蛮有趣的，比盯着课本看着学要好的多。  
其次是从零开始搭建项目的经验，算法、数据结构、代码实现，环环相扣，每个都至关重要。我在这次历程中，因为之前对这些东西一无所知，头几天看了一下dfa/nfa的概念，对算法的理解过于浅薄，然后就急于上手，搭建的数据结构粗糙简陋，给项目后续的工作带来了很大的困难。最后的成品其实也完全是个玩具而已，连第二阶段的任务要求都还没能完全实现。    
但是不管怎么说，这样的结果已经令我相当满意。尽管学长们说这个项目是特意选的让每个人都从零开始，但是无论如何有基础和没基础之间差距都是巨大的。有学过算法原理、编译原理的，有做过项目的经验的，一定会比一个初来乍到的菜鸡强，这点是毫无疑问的。  
即使这次被刷，我也是心服口服的，技不如人，甘拜下风，光是在群里的聊天就能看出来和其他人的差距，可不是说补齐就补齐的。  
总之漫漫的学习之路才开始一个半月，接下来的路还长呢~  
  


下面是日记：    
  

  



DAY1：  
搭建JAVA开发环境 √  
选择IDE：IDEA  √  
选择教材：菜鸟教程  √（本来想选择《JAVA编程思想》的，但是感觉太厚了不适合速成）  
向代码之神致敬：HelloWorld  √  
将Java的基本语法过了一遍，尽管看的并不如何仔细（尤其是内存控制的部分，判断对这次项目帮助不大）  
截至此时，看到了教程重写与重载一章。  
因为错过飞机导致整个人精神崩溃，后半天的学习质量自己也不能确定。。。总之应该还算正常的开头吧，希望能坚持下来。   
  
  
DAY2：  
今天除了吃饭和睡觉以及飞机上禁止用电子产品的时间，大部分时间是在不停地谷歌和看资料中度过的。  
先看了任务介绍下面的参考资料网页，然后看了一下龙书感觉并不太好理解（术语太多了，感觉光是寻章摘句地看这种著作是没有意义的），于是就找了篇通俗易懂论文，大致理解了一半NFA和DFA的原理，(我理解为一个根据命令不断向后走的有向图，不知道有没有偏差)，不过对如何让这个走的过程实现感觉依然很迷茫。  
总之，先试着做了NFA的数据结构(搭建NFA类)，目前写到连接部分，思路是将它做成一个动态数组，数组元素包含两个基本属性：状态值和指向的下一个节点(当然也包括边属性)。  
JAVA的学习暂时搁置，因为感觉在某种程度上就像是面向对象的C(等等这不是C++吗)，然后面向对象的结构在Python里已经有所熟悉，所以一边搭积木一边学吧~~  
(不过讲真，java真是好麻烦啊。。。还是喜欢PY)  
  

DAY3：
今天取得的实际进展挺有限地，学了一下将正则转换为NFA的方法，准备采取正则表达式→预处理→NFA→DFA→模拟DFA的方法，不过其中每一个算法的具体实现都还没有一点B数，明天就到了一半的日期了，我真的没什么把握能把这个项目做完，而群里大佬却已经有实质性进展了，哇。。。。。。。。。。。。  
另外今天把昨天遗留的NFA数据结构姑且做完了，不过在看完算法介绍的资料之后感觉很可能要重构。。。  
希望能做完吧，不然被刷岂不是相当于这个假期都白白浪费了。  
恩，明天，干巴爹！一定要啃完一份代码！！  

DAY4：  
今天的进展主要就是搭建了DFA的数据结构，同时理清楚了从NFA到DFA的过程。NFA的数据结构我做成了有向图的形式，以ArrayList的方式存储每个节点，节点信息包括状态名和与下一个节点连接的边；而DFA则做成了HashList，将DFA节点“近似的”理解为NFA的闭包，从一个闭包滚向下一个闭包，相当于同时进行几条NFA。不知道中间算法有没有bug，不过估计有我回头改可能也来不及了，先走一步看一步吧。  
明天希望能搞定正则表达式的预处理部分，先转化为中缀式再转化为后缀式（逆波兰式）。   

DAY5：  
今天总算做完了引擎的主体部分，包括预处理正则表达式（转义、转换为中缀式、转换为逆波兰式），然后生成nfa再转化成dfa。借鉴了大量的源码，话说不会被追究抄袭吧（怂了  
然而做完之后又修了一天的bug，几乎每个步骤都有bug，现在修到从nfa到dfa还有不可思议的bug不知道能不能修好。。。  
另外，今天其实我也领悟了一件事。其实满打满算，从八月中旬开始记，我也才学了一个半月的编程，而java更是只学了一天。在此之前，什么计算机原理、编译原理、算法、框架碰都没碰过，假如我能把bug修好，即使功能不完善也很满足了。  
退一步讲，即使我没能被录用，或者干脆bug都没修好，也已经是宝贵的经验了，技不如人甘拜下风毫无怨言。  
明天继续，干巴爹~~~  
  
DAY6：  
修好了所有现阶段的bug，超爽der!!!
总结一下目前的工作吧：实现了parser,matcher和search以及contains，支持的语法包括闭包（即小括号）、串联、并联（即或'|'），以及 . * + [a-z] \s \w，不过^和$我没支持，因为我认为我的matcher就相当于加了开始符和结束符^$，而没有不带开始和结束的用search或者contains方法就行了。  
明天的话准备研究一下语法糖和宏替换，还有？，不过限定符{m,n}我就没有能完成的逼数了。。。总之能做多少送多少啦，到现在已经很让我自己满意了~  
另外提一下今天遇到的情况吧，今天基本是修了一整天的bug，前几个都是一些细节错误导致的，用ide逐步调试找到错误原因；但是最后一个bug折腾了我很久，涉及到了语言的底层设定，反复查询错误原因以后发现时因为java除了基本类以外，=号都是只能起引用作用的，新对象和旧对象共用同一个内存，导致我不能单独对新对象进行操作。理解了原因之后解决办法就很简单粗暴了，写了一个copy方法，把实例的属性逐个复制到新类里，完成实例的克隆和新内存的开辟。这个解决过程让我对java和面向对象设计的理解进了一大步（果然一天速成还是不太靠谱的呀~），等这个项目结束看来还是要把面向对象系统的了解一下。  
  
DAY7:
作为倒数第二天，我已经不可能再大改数据结构了，既因为我重写代码来不及，也因为我自己对算法的理解还是不够深入、不够高明。对于不当的算法和数据结构会带来怎样的负面影响这一点，在今天的工作中我深刻地体会到了。  
今天主要做的就是支持？，支持宏替换（其实我也不知道我理解的宏替换对不对。。。），支持{n}。前两个还是挺简单的，但是第三个，我发现由于我所构造的数据结构里nfa的边只能是单个字符，而引擎处理/匹配字符串的时候也只能一个字符一个字符的读入，这导致后期工作非常的不方便。比方说，为了支持{n},我只有两条路可选，一是将{n}整体代以另一个操作符，像'+'这样的（说起来，我处理转义的方法也非常丑陋），然而操作符终归是有限地，况且我用的是ASCII编码，能用的字符更是少得可怜；另一条路是简单粗暴地把{n}对应的操作数重复n遍，也就是预处理层面的支持。  
最后采取的第二条路，代码的质量也非常之低下。  
并且在预处理层面的话，完成{n,}还好说，如果要完成{m,n}，那复杂度简直要上天了，我需要把{n}和{n+1}...一直到{m}并联，才能实现功能。因此在这里，我的数据结构处理是非常失败的。  
明天的话，我最多再实现{n,}，而{m,n}真的不想做了，即使做出来了效率也太低了！作为一个玩具，以过度的牺牲效率为代价来实现一个功能，我认为是没有必要的，还不如多学一会知识，以后试着再做一次更好的。贪婪非贪婪、捕获之类的靠明天一天也是不可能做的了。  
长路漫漫，明天之后，要学的东西还非常非常非常多。  

