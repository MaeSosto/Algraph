package algraph;

import java.util.Comparator;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Queue;


class NodeComparator implements Comparator<Node> {
    public int compare(Node e1, Node e2) {
        if (e1.getPriority() < e2.getPriority())
            return 1;
        else if (e1.getPriority() > e2.getPriority())
            return -1;
        return 0;
    }
}

public class Johnson{

    private Graph grafo= new Graph(); //GRAFO DI APPOGGIO
    private Queue<Node> Heap = new PriorityQueue<>(10, new NodeComparator ()); //HEAP
    private Node uno= null;
    private Node due;

    //COSTRUISCE UN GRAFO DI APPOGGIO PER CREARE IL GRAFO DEL CAMMINO MINIMO CON JOHNSON
    Johnson(Graph g){
        this.grafo=g;
        Node e = grafo.sputaNodoRandom();
        e.setPriority(0);
        e.setD(0);
        Heap.add(e);
    }

    //STAMPA SU TERMINALE L'HEAP
    public void printHeap(){
        Iterator<Node> it= Heap.iterator();
        System.out.println("Coda di priorità attuale: ");
        while (it.hasNext()) {
            Node e= it.next();
            System.out.println("Nodo: "+e.getC()+" con priorità "+e.getPriority());
        }
        System.out.println("------------------------------------");
    }

    //RESTITUISCE ED ELIMINA IL NODO CON PRIORITA' PIU' BASSA PRESENTE NELL'HEAP
    public Node pop (Queue<Node> Heap){
        boolean b= false;
        int n=0;
        Node poll=null;
        Iterator<Node> it= Heap.iterator();
        while (it.hasNext()) {
            Node e= it.next();
            if(b==false) {
                n = e.getPriority();
                poll=e;
                b=true;
            }
            if(e.getPriority()<=n) {
                poll = e;
                n = e.getPriority();
            }
        }
        Heap.remove(poll);
        return poll;
    }

    //FINCHE' CI SONO DEI NODI NELL'HEAP ALLORA SI ESEGUE UN NUOVO PASSO DELL'ALGORITMO DI JOHNSON
    void shortestPath() {
       do{
           nextpasso();
       }while(!Heap.isEmpty());
    }

    //ESEGUE UN PASSO DI ALGORITMO
    boolean nextpasso() {
        boolean b= false;
        Node v;
        if (!Heap.isEmpty()) { //controlla che ci siano nodi nell'heap
            Node u = pop(Heap); //assegna a u il nodo con priorità maggiore (valore più basso)
            u.setVisitato(true); //imposta il flag del visitato come true (il nodo non sarà più valutato)
            u.setB(false); //
            for (char i = 'A'; i <='J'; i++) { //scorro tutti i nodi nel grafo
                v = grafo.getNode(i); //assegno l'i-esimo nodo a v
                if (grafo.adj(v, u) || grafo.adj(u, v)) { //controllo se l'i-esimo nodo è adiacente a u
                    if (u.getD() + grafo.w(u, v) < v.getD()) { //se la distanza dal nodo di partenza u sommata alla distanza tra il nodo u e v è minore di infinito (100)
                        if (!v.isB()){ //se il nodo non è ancora stato valutato nell'heap (non è ancora entrato nell'heap)
                            v.setPriority(u.getD() + grafo.w(u, v)); //aggiorna la distanza per arrivare al nodo v
                            v.setC(i);
                            Heap.add(v); //aggiungo il nodo all'heap
                            v.setB(true); //il nodo deve essere ancora valutato dall'heap
                        } else {
                            v.setPriority(u.getD() + grafo.w(u, v)); //se il nodo è già nell'heap allora aggiorni la sua priorità
                        }
                        v.setT(u); //imposta che il nodo precedentemente visitato a v è u
                        v.setD(u.getD() + grafo.w(u, v)); // aggiorna la nuova distanza tra v e il nodo iniziale
                    }
                }
            }
            b=true;
        }
        printHeap();
        return b; //se b è = false allora non ci sono altri passi da fare
    }
}