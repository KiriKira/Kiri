package com.RE.Engine;

import java.util.ArrayList;

public class NFA {

    private ArrayList<NFAnode> nfa;

    class NFAnode implements Comparable {
        String state; // The State Of The Node
        ArrayList<NFAnode> desnodes; //desnodes ==destination nodes
        ArrayList<Character> edge; //Which connect two nodes
        boolean Start;
        boolean End;

        NFAnode(String state) {
            this.state = state;
            edge = new ArrayList<>();
            desnodes = new ArrayList<>();
            Start = false;
            End = false;
        }

        void addEdge(Character c, NFAnode d) {
            edge.add(c);
            desnodes.add(d);
        }

        boolean hasPath(Character c) {
            for (Character x : edge) {
                if (x.equals(c))
                    return true;
            }
            return false;
        }

        @Override
        public int compareTo(Object o) {
            if (o instanceof NFAnode) {
                NFAnode onode = (NFAnode) o;
                return this.state.compareTo(onode.state);
            } else {
                throw new ClassCastException("Cannot do compareTo");
            }
        }
    }
    public NFA(){
        nfa = new ArrayList<>();
    }

    public int size(){
        return nfa.size();
    }

    public NFAnode getnode(String s){
        if(nfa == null)
            return null;
        for(NFAnode x:nfa){
            if(x.state.equals(s))
                return x;
        }
        return null;
    }

    private void addStart(String s){
        if(nfa!=null)
            if(getnode(s)!=null)
                getnode(s).Start = true;
            else
                System.out.println("不存在这样的节点");
    }

    private void addEnd(String s){
        if(nfa!=null)
            getnode(s).End = false;
    }

    private void addnode(NFAnode node){
        if(nfa!=null) {
            nfa.add(node);
        }
    }

    private NFAnode addnode(String s){
            NFAnode n1 = new NFAnode(s);
            nfa.add(n1);
            return n1;
    }

    private void addedge(String src,Character c,String des){
        NFAnode from = getnode(src);
        NFAnode to = getnode(des);
        from.addEdge(c,to);
    }

    public NFAnode getstart(){
        if(nfa != null)
            for(NFAnode x:nfa)
                if(x.Start)
                    return x;
        return null;

    }

    public NFAnode getend(){
        if(nfa == null){
            System.out.println("nfa is null!");
            return null;
        }
        for(NFAnode x:nfa)
            if(x.End == true)
                return x;
        return null;

    }

    private void statesorted(){
        int i;
        for(i=0;i<nfa.size();i++)
            nfa.get(i).state = String.valueOf(i);
    }

    //连接两个NFA，即串联
    public void connect(NFA n1){
        if(n1 == null)
            return;
        if(nfa==null) {
            nfa = n1.nfa;
            return;
        }
        NFAnode n = getend();
        n.End = false;
        n1.getstart().Start = false;
        n.addEdge('\0',n1.getstart());
        for(NFAnode x : n1.nfa)
            this.addnode(x);
        this.statesorted();

    }

    //合并两个NFA，即并联
    public void combine(NFA n1){
        if(n1==null)
            return;
        if(nfa==null){
            nfa = n1.nfa;
            return;
        }
        NFA n0 = new NFA();
        NFAnode s = this.getstart();
        NFAnode s1 = n1.getstart();
        NFAnode e = this.getend();
        NFAnode e1 = n1.getend();
        s.Start = false;
        s1.Start = false ;
        e.End = false ;
        e1.End = false ;
        n0.addnode("S");
        n0.addStart("S");
        n0.addnode("E");
        n0.addEnd("E");
        n0.getstart().addEdge('\0',s);
        n0.getstart().addEdge('\0',s1);
        e.addEdge('\0',n0.getend());
        e1.addEdge('\0',n0.getend());
        for(NFAnode x:nfa)
            n0.addnode(x);
        for(NFAnode x:n1.nfa)
            n0.addnode(x);
        n0.statesorted();
        this.nfa = n0.nfa;
    }

    //创建实例
    public static NFA ins(Character c)
    {
        NFA n = new NFA();
        NFAnode n1 = n.addnode("S");
        n1.Start = true;
        NFAnode n2 = n.addnode("E");
        n2.End = true;
        n.addedge("S", c, "E");
        n.statesorted();
        return n;
    }

    //添加闭包
    public void closure(){
        NFA n = new NFA();
        n.addnode("S");
        n.addnode("E");
        n.addStart("S");
        n.addEnd("E");
        NFAnode s0 = this.getstart();
        NFAnode e0 = this.getend();
        s0.Start = false;
        s0.End = false;
        n.getstart().addEdge('\0',s0);
        n.getstart().addEdge('\0',n.getend());
        e0.addEdge('\0',s0);
        e0.addEdge('\0',n.getend());
        for(NFAnode x:this.nfa)
            n.addnode(x);
        n.statesorted();
        this.nfa = n.nfa;
    }


    public static void main(String[] args) {
        char c = (char)2;
        NFA n1 = NFA.ins(c);
        NFAnode node = n1.getstart();

        System.out.println(node.hasPath(c));
    }


}
