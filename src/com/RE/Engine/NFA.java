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
    public void NFA(){
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
            getnode(s).Start = true;
    }

    private void addEnd(String s){
        if(nfa!=null)
            getnode(s).End = false;
    }

    private void addnode(NFAnode node){
        if(nfa!=null)
            nfa.add(node);
    }

    private void addnode(String s){
        if(nfa!=null)
            nfa.add(new NFAnode(s));
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
        if(nfa != null)
            for(NFAnode x:nfa)
                if(x.End)
                    return x;
        return null;

    }

    private void statesorted(){
        int i;
        for(i=0;i<nfa.size();i++)
            nfa.get(i).state = String.valueOf(i);
    }

    public void connect(NFA n1){
        if(n1 == null)
            return;
        if(nfa==null) {
            nfa = n1.nfa;
            return;
        }
        NFAnode n = this.getend();
        n.End = false;
        n1.getstart().Start = false;
        n.addEdge('\0',n1.getstart());
        for(NFAnode x : n1.nfa)
            this.addnode(x);
        this.statesorted();

    }


}
