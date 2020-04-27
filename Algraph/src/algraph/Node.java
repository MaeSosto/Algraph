package algraph;

import java.util.Comparator;

class Node{
    private char c;     //identificatore
    private boolean visitato;
    private double x;
    private double y;
    private int priority;
    private boolean b; //se il nodo è stato valutato o no dall'heap
    private int d; //distanza totale
    private Node T;


    //COSTRUTTORE
    Node(char c){
        this.c = c;
        this.visitato = false;
        this.x = 0;
        this.y = 0;
        this.priority=101;
        this.b=false;
        this.d=100;
        this.T= null;
    }

    //GETTERS AND SETTERS
    char getC() {
        return c;
    }

    void setC(char c) {
        this.c = c;
    }

    boolean isVisitato() {
        return visitato;
    }

    void setVisitato(boolean isVisitato) {
        this.visitato = isVisitato;
    }

    double getX() {
        return x;
    }

    void setX(double x) {
        this.x = x;
    }

    double getY() {
        return y;
    }

    void setY(double y) {
        this.y = y;
    }

    int getPriority() {
        return priority;
    }

    void setPriority(int priority) {
        this.priority = priority;
    }

    boolean isB() {
        return b;
    }

    void setB(boolean b) {
        this.b = b;
    }

    int getD() {
        return d;
    }

    void setD(int d) {
        this.d = d;
    }

    Node getT() {
        return T;
    }

    void setT(Node t) {
        T = t;
    }


  /* //PARAGONA I NODI PER METTERLI IN ORDINE DI PRIORITA'
   static Comparator<Node> idComparator = new Comparator<Node>(){

        @Override
        public int compare(Node c1, Node c2) {
            return (int) (c1.getPriority() - c2.getPriority());
        }
    };
*/

}