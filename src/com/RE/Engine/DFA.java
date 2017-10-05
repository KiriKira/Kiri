package com.RE.Engine;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Stack;
import com.RE.Engine.NFA.NFAnode;
import com.RE.Engine.Transit;

public class DFA {
        class DFAnode{
            ArrayList<Character> edge;
            ArrayList<DFAnode> desnode;
            boolean start;
            boolean end;
            HashSet<NFAnode> dstates;

            DFAnode(HashSet<NFAnode> d){
                dstates = new HashSet<>();
                edge = new ArrayList<>();
                desnode = new ArrayList<>();
                start = false ;
                end = false ;
                dstates.addAll(d);
            }

            @Override
            public int hashCode(){
                return dstates.hashCode();
            }

            public boolean haspath(Character c){
                for(Character x:edge){
                    if(x.equals(c))
                        return true;
                }
                return false;
            }

            public void addedge(Character c, DFAnode d){
                edge.add(c);
                desnode.add(d);
            }

        }

        private HashSet<DFAnode> dfa;
        public DFA(){
            dfa = new HashSet<>();
        }

        public DFAnode getNode(HashSet<NFA.NFAnode> dnode){
            if(dfa == null)
                return  null;
            for(DFAnode elem:this.dfa){
                if(elem.dstates.size()==dnode.size())
                    if(elem.dstates.hashCode()==dnode.hashCode())
                        return elem;

            }
            return null;
        }

        public void addNode(HashSet<NFAnode> dstates){
            if(getNode(dstates)==null){
                DFAnode dnode = new DFAnode(dstates);
                dfa.add(dnode);
                for(NFAnode nnode:dstates){
                    if(nnode.Start)
                        dnode.start=true;
                    if(nnode.End)
                        dnode.end = true;
                }
            }
        }

        public void addEdge(HashSet<NFAnode> from,Character edge,HashSet<NFAnode> to){
            if(this.dfa != null){
                DFAnode f = getNode(from);
                DFAnode t = getNode(to);
                f.addedge(edge,t);
            }
        }

        //将DFA转化为DFA
        public DFA(NFA nfa){
            DFA dfa1 = new DFA();
            NFAnode s0 = nfa.getstart();
            HashSet<HashSet<NFA.NFAnode>> result = new HashSet<>(); //用来暂时存放节点，用以在后面检查是否有重复闭包
            Stack<HashSet<NFA.NFAnode>> dStates = new Stack<>(); //操作栈
            HashSet<NFA.NFAnode> start = Transit.calClosure(s0);
            dStates.push(start);
            result.add(start);
            dfa1.addNode(start);

            while(!dStates.isEmpty()){
                HashSet<NFAnode> currentNode = dStates.pop();
                for(Character c:Transit.alphabet2){
                    HashSet<NFAnode> T1 = Transit.nextClosure(currentNode,c);
                    HashSet<NFAnode> Temp = Transit.calclosure(T1);
                    if(!Transit.inResult(result,Temp)){
                        dStates.push(Temp);
                        dfa1.addNode(Temp);
                        result.add(Temp);
                    }
                    if(currentNode.size()>0&&Temp.size()>0)
                        dfa1.addEdge(currentNode,c,Temp);

                }
            }
            this.dfa=dfa1.dfa;
        }

        public boolean match(String s){
            if(dfa ==null) {
                System.out.println("dfa==null!!");
                return false;
            }
            DFAnode currentNode = null;
            for(DFAnode node:dfa)
                if(node.start == true)
                    currentNode = node;

            for(Character c:s.toCharArray()){
                if(currentNode.haspath(c))
                    currentNode = currentNode.desnode.get(currentNode.edge.indexOf(c));
                else
                    return false;
            }

            if(currentNode.end==true)
                return true;
            else
                return false;
        }

        //判断是否包含满足的字段
        public boolean contain(String s){
            int index=0;
            if(dfa==null)
                return false;
            DFAnode currentNode = null;
            for(DFAnode node:dfa)
                if(node.start=true)
                    currentNode = node;

            for(Character c:s.toCharArray()){
                index++;
                if(currentNode.haspath(c)){
                    currentNode = currentNode.desnode.get(currentNode.edge.indexOf(c));
                    if(currentNode.end=true)
                        return true;//非贪婪匹配
                }
                else{
                    if(s.length()==1)
                        return false;
                    return contain(s.substring(1));
                }
            }
            return false;
        }

        //逐个匹配，操作count给下一个真正的search函数，找不到就返回false
        public boolean search(String s,int[] count){
            int index=0;
            if(dfa==null)
                return false;
            DFAnode currentNode = null;
            for(DFAnode node:dfa)
                if(node.start=true)
                    currentNode = node;

            for(Character c:s.toCharArray()){
                index++;
                if(currentNode.haspath(c)){
                    currentNode = currentNode.desnode.get(currentNode.edge.indexOf(c));
                    if(currentNode.end=true)
                        return true;//非贪婪匹配
                }
                else{
                    if(s.length()==1)
                        return false;
                    count[0]++;
                    return search(s.substring(1),count);
                }
            }
            return false;
        }

        //搜索函数，返回首个匹配上的索引位置，如果不存在就返回-1.
        public int search(String s){
            int[] count = new int[1];
            count[0]=0;
            if(search(s,count))
                return count[0];
            else
                return -1;
        }








}
