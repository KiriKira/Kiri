package com.RE.Engine;

import java.util.Stack;
import java.util.StringTokenizer;
import com.RE.Engine.DFA;
import com.RE.Engine.NFA;
import com.RE.Engine.Transit;

public class Re {
    private String re;
    private DFA dfa;
    private String zhuanyi = "A.*|()\\+?";//A是为了让后面符号的索引从1开始而加的

    public Re(String s) {
        re = s;
        makeDFA();
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

    //完全是将[a-z]这种暴力替换，因此也不支持[1-9a-z]或[b-z]，请使用[1-9]\[a-z]这样
    private String fakeMacro(String s) {
        s.replace("[a-z]", "(a|b|c|d|e|f|g|h|i|j|k|l|m|o|p|q|r|s|t|u|v|w|x|y|z)");
        s.replace("[0-9]", "(0|1|2|3|4|5|6|7|8|9)");
        s.replace("[1-9]", "(1|2|3|4|5|6|7|8|9)");
        s.replace("[A-Z]", "(A|B|C|D|E|F|G|H|I|J|K|L|M|N|O|P|Q|R|S|T|U|V|W|X|Y|Z)");
        return s;
    }

    private String zhuanyi(String s) {
        for (int i=1;i < s.length(); i++) {
            if (s.charAt(i - 1) == '\\' && (s.charAt(i) == '.' || s.charAt(i) == '|' || s.charAt(i) == '\\'
                    || s.charAt(i) == '(' || s.charAt(i) == ')' || s.charAt(i) == '*')) {
                String s1 = s.substring(0, i - 1);
                String s2 = String.valueOf((char) zhuanyi.indexOf(s.charAt(i))); //转义后储存的是索引位置，而非SOH之类。
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
            if(isChar(re.charAt(i))&&(re.charAt(i-1)==')'||re.charAt(i-1)=='*'||isChar(re.charAt(i-1)))
                    ||(re.charAt(i)=='(')&&(isChar(re.charAt(i-1))||re.charAt(i-1)==')'||re.charAt(i-1)=='*'))
                sb.append('.');//中缀式毗邻运算符
            sb.append(re.charAt(i));
        }
        return sb.toString();
    }

    private String toPostfix(String expression){
        StringBuffer postfix = new StringBuffer();
        Stack<Character> operatestack = new Stack<Character>();
        StringTokenizer tokens = new StringTokenizer(expression,"()*.|",true);
        while(tokens.hasMoreTokens()){
            String token = tokens.nextToken();
            switch(token.charAt(0)){
                case '|':
                    while(!operatestack.isEmpty()&&(operatestack.peek()=='.'||operatestack.peek()=='*')){
                        postfix.append(operatestack.pop());
                    }
                    operatestack.push(token.charAt(0));
                    break;
                case '.':
                    while(!operatestack.isEmpty()&&operatestack.peek().equals('.')){
                        postfix.append(operatestack.pop());
                    }
                    operatestack.push(token.charAt(0));
                    break;
                case '*':
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
        else if(c == '.'){
            op2.connect(op1);
            operandstack.push(op2);
        }
    }

    private  NFA calExpression(String postfix){
        Stack<NFA> oprandstack = new Stack<NFA>();
        for(int i=0;i<postfix.length();i++){
            Character c = postfix.charAt(i);
            if(c == '*'){
                NFA nfa = oprandstack.pop();
                nfa.closure();
                oprandstack.push(nfa);
            }
            else if(c == '|'|| c=='.'){
                dealTwo(oprandstack,c);
            }
            else {
                System.out.println(c);
                oprandstack.push(NFA.ins(c));
            }
        }
        return oprandstack.pop();
    }

    private String preDeal(){
        return toPostfix(addDot(zhuanyi(fakeMacro(re))));
    }

    private void makeDFA(){
        String pre = preDeal();
        //System.out.println(pre);
        //System.out.println(zhuanyi(pre));
        //System.out.println(addDot(zhuanyi(pre)));
        //System.out.println(toPostfix(addDot(zhuanyi(pre))));
        NFA nfa = this.calExpression(pre);
        this.dfa = new DFA(nfa);
    }

    public boolean match(String s){
        for(int i=0;i<s.length();i++){
            if(s.charAt(i)=='.'||s.charAt(i)=='|'||s.charAt(i)=='('||s.charAt(i)==')'
                    ||s.charAt(i)=='*'||s.charAt(i)=='\\')
            {
                String s1 = s.substring(0,i);
                String s2 = String.valueOf((char)zhuanyi.indexOf(s.charAt(i)));
                String s3 = s.substring(i+1);
                s = s1 + s2 +s3;
            }
        }
        return this.dfa.match(s);
    }


}