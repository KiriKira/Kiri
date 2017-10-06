package com.RE.Engine;

import java.util.HashMap;
import java.util.Stack;
import java.util.StringTokenizer;

public class Re {
    private String re;
    private DFA dfa;
    private String zhuanyi = "A.*|()\\+AAAAAAA?sw";//A是为了让后面符号的索引从1开始而加的.？是第15位。
    private HashMap<String,String> macroMap = new HashMap<>(); //宏定义表

    public Re(String s) {
        re = s;
        makeDFA();
    }

    public Re(String s,String... macroset){
        re = s ;
        for(int i=0;i<macroset.length;i++){
            String[] temp = macroset[i].split(":");
            macroMap.put(temp[0].trim(),temp[1].trim());
        }
        makeDFA();
    }

    private int countAppear(String s,Character c){
        int count = 0;
        for(int i=0;i<s.length();i++){
            if(s.charAt(i)==c)
                count++;
        }
        return count;
    }

    private int lastnChar(String s,Character c,int n ){
        int index = s.lastIndexOf(c);
        if(n==1)
            return index;
        for(int i=0;i<n-1;i++){
            index = s.lastIndexOf(c,index-1);
        }
        return index;
    }


    private boolean isChar(char c) {
        for (int i = 0; i < Transit.alphabet2.length; i++)
            if (Transit.alphabet2[i] == c)
                return true;
        return false;
    }

    public String getre(){
        return re;
    }

    //完全是将[a-z]这种暴力替换，因此也支持[1-9a-z]，请使用[1-9]\[a-z]这样
    private String fakeMacro(String s) {
        for(int i=0;i<s.length();i++){
            if(s.charAt(i)=='['&&s.charAt(i+2)=='-'&&s.charAt(i+4)==']'){
                StringBuffer sInstead = new StringBuffer();
                sInstead.append('(');
                for(int j=(int)s.charAt(i+1);j<=(int)s.charAt(i+3);j++){
                    sInstead.append((char)j);
                    sInstead.append('|');
                }
                sInstead.deleteCharAt(sInstead.length()-1);
                sInstead.append(')');
                String s1 =s.substring(0,i);
                String s2 = sInstead.toString();
                String s3 = s.substring(i+5);
                s = s1 + s2 + s3;
            }
        }
        return s;
    }

    private String macroReplace(String s){
        if(macroMap.isEmpty())
            return s;
        for(HashMap.Entry<String,String> entry: macroMap.entrySet()){
            s = s.replace("{"+entry.getKey()+"}","("+entry.getValue()+")");
        }
        return s;

    }

    //尝试处理{n}
    private String candy(String s){

        while(true){
            int i1 = s.indexOf('{');
            if( i1 ==-1)
                break;
            int i2 = s.indexOf('}',i1);
            if(i2 == -1)
                break;
            String s1 = s.substring(i1+1,i2);
            for(Character c:s1.toCharArray()){
                if('0'<=c&&c<='9')
                    continue;
                else
                    return s;
            }
            int n = Integer.parseInt(s1);
            if(s.charAt(i1-1)!=')'){
                String t1 = s.substring(0,i1-1);
                StringBuffer t2 = new StringBuffer();
                for(int i=0;i<n;i++){
                    Character c = s.charAt(i1-1);
                    t2.append(c);
                }
                String t3 = t2.toString();
                String t4 = s.substring(i2+1);
                s = t1 + t3 + t4;
            }
            else {
                int l1 = s.lastIndexOf('(',i1-1);
                int n2 = countAppear(s.substring(l1+1,i1),')');
                int l2 = lastnChar(s.substring(0,i1),'(',n2);
                String temp = s.substring(l2,i1);
                String t1 = s.substring(0,l2);
                StringBuffer t2 = new StringBuffer();
                for(int j=0;j<n;j++)
                    t2.append(temp);
                String t3 = t2.toString();
                String t4 = s.substring(i2+1);
                s = t1 + t3 +t4;

            }

        }
        return s;
    }


    //完成特殊字符的转义
    private String zhuanyi(String s) {
        for (int i=1;i < s.length(); i++) {
            if (s.charAt(i - 1) == '\\' && (s.charAt(i) == '.' || s.charAt(i) == '|' || s.charAt(i) == '\\'
                    || s.charAt(i) == '(' || s.charAt(i) == ')' || s.charAt(i) == '*'||s.charAt(i)=='+'
                    ||s.charAt(i)=='s'||s.charAt(i)=='w'||s.charAt(i)=='?')) {
                String s1 = s.substring(0, i - 1);
                String s2 = String.valueOf((char) zhuanyi.indexOf(s.charAt(i))); //转义后储存的是索引位置，而非SOH等的原意。
                String s3 = s.substring(i + 1);
                s = s1 + s2 + s3;
            }
        }
        return s;
    }

    //构造中缀式
    private String addDot(String re) {
        StringBuffer sb = new StringBuffer();
        sb.append(re.charAt(0));
        for (int i = 1; i < re.length(); i++) {
            if(isChar(re.charAt(i))&&(re.charAt(i-1)==')'||re.charAt(i-1)=='*'||re.charAt(i-1)=='+'||re.charAt(i-1)=='?'||isChar(re.charAt(i-1)))
                    ||(re.charAt(i)=='(')&&(isChar(re.charAt(i-1))||re.charAt(i-1)==')'||re.charAt(i-1)=='*'||re.charAt(i-1)=='+'||re.charAt(i-1)=='?'))
                sb.append('\016');//中缀式毗邻运算符
            sb.append(re.charAt(i));
        }
        return sb.toString();
    }

    //将中缀式再转换为后缀式
    private String toPostfix(String expression){
        StringBuffer postfix = new StringBuffer();
        Stack<Character> operatestack = new Stack<Character>();
        StringTokenizer tokens = new StringTokenizer(expression,"()*\016|+?",true);
        while(tokens.hasMoreTokens()){
            String token = tokens.nextToken();
            switch(token.charAt(0)){
                case '|':
                    while(!operatestack.isEmpty()&&(operatestack.peek()=='\016'||operatestack.peek()=='*'||operatestack.peek()=='+'||operatestack.peek()=='?')){
                        postfix.append(operatestack.pop());
                    }
                    operatestack.push(token.charAt(0));
                    break;
                case '\016':
                    while(!operatestack.isEmpty()&&operatestack.peek().equals('\016')){
                        postfix.append(operatestack.pop());
                    }
                    operatestack.push(token.charAt(0));
                    break;
                case '*':
                    postfix.append(token.charAt(0));
                    break;
                case '+':
                    postfix.append(token.charAt(0));
                    break;
                case '?':
                    postfix.append(token.charAt(0));
                    break;
                case '(':
                    operatestack.push('(');
                    break;
                case ')':
                    while(!operatestack.peek().equals('(')){//一直向前寻找'('
                        postfix.append(operatestack.pop());
                    }
                    operatestack.pop();
                    break;
                default:
                    postfix.append(token);

            }
        }

        while(!operatestack.isEmpty()){
            postfix.append(operatestack.pop());
        }
        return postfix.toString();
    }

    //处理双目运算符
    private void dealTwo(Stack<NFA> operandstack , Character c){
        NFA op1 = operandstack.pop();
        NFA op2 = operandstack.pop();
        if(c=='|'){
            op2.combine(op1);
            operandstack.push(op2);
        }
        else if(c == '\016'){
            op2.connect(op1);
            operandstack.push(op2);
        }
    }

    //构造NFA
    private  NFA calExpression(String postfix){
        Stack<NFA> oprandstack = new Stack<NFA>();
        for(int i=0;i<postfix.length();i++){
            Character c = postfix.charAt(i);
            if(c == '*'){
                NFA nfa = oprandstack.pop();
                nfa.closure();
                oprandstack.push(nfa);
            }
            else if(c=='?'){
                NFA nfa = oprandstack.pop();
                nfa.addedge(nfa.getstart().state,'\0',nfa.getend().state);
                oprandstack.push(nfa);
            }
            else if(c == '+'){
                NFA nfa1 = oprandstack.pop();
                NFA nfa2 = new NFA();
                nfa2.copy(nfa1);
                nfa2.closure();
                nfa1.connect(nfa2);

                oprandstack.push(nfa1);

            }
            else if(c == '|'|| c=='\016'){
                dealTwo(oprandstack,c);
            }
            else if(c == '.'){
                oprandstack.push(NFA.insAny());
            }
            else if(c == '\020'){
                oprandstack.push(NFA.insFors());
            }
            else if(c == '\021'){
                oprandstack.push(NFA.insForw());
            }
            else {
                oprandstack.push(NFA.ins(c));
            }
        }
        return oprandstack.pop();
    }

    private String preDeal(){
        return toPostfix(addDot(candy(zhuanyi(fakeMacro(macroReplace(re))))));
    }

    private void makeDFA(){
        String pre = preDeal();
        //System.out.println(pre);
        //System.out.println(fakeMacro(re));
        //System.out.println(zhuanyi(re));
        //System.out.println(addDot(zhuanyi(pre)));
        //System.out.println(toPostfix(addDot(zhuanyi(pre))));
        NFA nfa = this.calExpression(pre);
        this.dfa = new DFA(nfa);
    }

    public boolean match(String s){
        for(int i=0;i<s.length();i++){
            if(s.charAt(i)=='.'||s.charAt(i)=='|'||s.charAt(i)=='('||s.charAt(i)==')'
                    ||s.charAt(i)=='*'||s.charAt(i)=='\\'||s.charAt(i)=='+'||s.charAt(i)=='?')
            {
                String s1 = s.substring(0,i);
                String s2 = String.valueOf((char)zhuanyi.indexOf(s.charAt(i)));
                String s3 = s.substring(i+1);
                s = s1 + s2 +s3;
            }
        }
        return this.dfa.match(s);
    }

    public boolean contains(String s){
        return dfa.contain(s);
    }

    public int search(String s){
        return dfa.search(s);
    }


}