package algraph;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.lang.System.out;

public class Graph implements IGraph{

    private static final int A_ASCII = 65;
    private HashMap<Node, ArrayList<Edge>> grafo;

    //COSTRUTTORE GRAFO VUOTO
    Graph(){
        this.grafo = new HashMap<>();
    }

    //COSTRUTTORE GRAFO CON N NODI
    Graph(Integer n){
        if( (n > 0) && (n <= 10) ) {
            //crea n nodi
            this.grafo = new HashMap<>();
            for (int i = 0; i < n; i++) {
                Node u = new Node((char) (i+ A_ASCII));
                this.grafo.put(u, new ArrayList<>());
            }

            //solo n-1 archi

            //crea archi random - j char partenza
            for(int j = 0; j < n; j++) {
                //numero random di archi per il j-esimo nodo
                int nMaxArchi = (int) (Math.random() * (n-1));

                for(int i = 0; i <= nMaxArchi; i++){

                    int peso = (int) (Math.random() * 10+1);  //peso: 1-10
                    int nArrivo = (int) (Math.random() * (n));  //nArrivo = nodo arrivo

                    //controllo: char di arrivo != da j char partenza + che non esista arco
                    if( nArrivo != j && !esisteArco( (char)(nArrivo+ A_ASCII), (char)(j+ A_ASCII) ) )  //System.out.println(j + " "+ nArrivo + " "+nMaxArchi);
                        this.insertEdge((char) (j+ A_ASCII), (char) (nArrivo + A_ASCII), peso);

                }
            }
        }
        else System.out.println("Errore: troppi (troppi pochi) nodi");
    }

    //RESTITUISCE LA DIMENSIONE DEL GRAFO
    Integer size(){
        return this.grafo.size();
    }

    //CONTROLLA SE IL NODO CON IL CARATTERE c APPARTIENE AL GRAFO
    //pre: riceve il char c, campo e identificatore del nodo
    //post: return TRUE se la map ha il nodo c
    //            FALSE se la mappa non ha il nodo c
    boolean appartiene(char c){

        boolean uguale = false;

        for (Node e : this.grafo.keySet()) {
            if(e.getC() == c)
                uguale = true;
        }
        return uguale;
    }

    //INSERISCE UN NUOVO NODO NEL GRAFO (IN ORDINE DI LETTERA)
    //true: nodo inserito
    //false: raggiunto limite max di nodi
    public boolean insertNode(){

        boolean ritorno = false;

        if( this.grafo.size() < 10 ){

            boolean trovato = false;
            int i = 0;
            char c = 'w';

            //ricerca del primo char disponibile
            while ( (i < this.grafo.size()) && (!trovato) ){

                if( appartiene((char)(i + A_ASCII)) )
                    i++;
                else {
                    trovato = true;
                    c = (char)(i+ A_ASCII);
                }
            }

            if(!trovato) {
                //aggiungi un nodo alla fine
                c = (char)(i+ A_ASCII);
                Node u = new Node(c);
                this.grafo.put(u, new ArrayList<>());
            }
            else{
                //aggiungi un nodo nel mezzo
                Node u = new Node(c);
                this.grafo.put(u, new ArrayList<>());

            }
            ritorno = true;
        }
        return ritorno;
    }

    //ELIMINA IL NODO CON IL NOME c DAL GRAFO
    //true: nodo eliminato
    //false: nodo non ci sta dentro al grafo
    public boolean deleteNode(char c){

        boolean ritorno = false;

        if( appartiene(c) && size()!=1 ) {
            for (Map.Entry<Node, ArrayList<Edge>> e : this.grafo.entrySet()) {

                if (e.getKey().getC() != c) {
                    /*b = */
                    this.deleteEdge(e.getKey().getC(), c);

                }
            }
            Iterator<Node> iter = this.grafo.keySet().iterator();
            while (iter.hasNext()) {
                Node nodo = iter.next();
                if (nodo.getC() == c) {
                    iter.remove();
                }
            }
            ritorno = true;
        }
        return ritorno;
    }


    //CONTROLLA SE ESISTE UN ARCO TRA IL NODO DI LETTERA c E QUELLO DI LETTERA d
    //c nodo partenza   true: esiste
    //d nodo arrivo     false: non esiste
    private boolean esisteArco(char c, char d){

        boolean esiste = false;

        for(Map.Entry<Node, ArrayList<Edge>> entry: this.grafo.entrySet()) {

            if (entry.getKey().getC() == c) {

                int i = 0;
                while(i < entry.getValue().size() && !esiste){
                    if(entry.getValue().get(i).getDestination().getC() == d)
                        esiste = true;
                    i++;
                }

            }
        }

        return esiste;
    }


    //CREA UN NUOVO ARCO TRA IL NODO  CON NOME c E IL NODO CON NOME d DI PESO peso
    //true: aggiunto
    //false: peso sballato || c = d || nodi non appartengono || arco già esistente
    public boolean insertEdge(char c, char d, Integer peso) {

        boolean ritorno = false;

        if( (c != d) && (peso > 0) && (peso <= 10) && appartiene(c) && appartiene(d) && !esisteArco(d, c) && !esisteArco(c, d) ){

            for(Map.Entry<Node, ArrayList<Edge>> entry: this.grafo.entrySet()){

                if( entry.getKey().getC() == c ){

                    for(Node u : this.grafo.keySet()) {
                        if(u.getC() == d) {
                            entry.getValue().add(new Edge(u, peso));
                            ritorno = true;
                        }
                    }


                }
            }
        }

        return ritorno;
    }

    //ELIMINA L'ARCO TRA IL NODO DI NOME c E IL NODO DI NOME d
    //true: eliminato
    //false: c = d || nodi non appartengono || arco non esiste
    public boolean deleteEdge(char c, char d){

        boolean eliminato = false;

        if( (c != d) && (appartiene(c)) && appartiene(d) ){
            for(Map.Entry<Node, ArrayList<Edge>> e: this.grafo.entrySet() ){
                if(e.getKey().getC() == c) {
                    for(int i = 0; i < e.getValue().size(); i++){
                        if( e.getValue().get(i).getDestination().getC() == d){
                            //se trova la destinazione => elimina
                            e.getValue().remove(i);
                            eliminato = true;
                        }
                    }
                }
            }
        }

        return eliminato;
    }

    //MODIFICA IL PESO DELL'ARCO (ESISTENTE) TRA IL NODO DI LETTERA c E IL NODO DI LETTERA d E ASSEGN IL NUOVO PESO peso
    //true: modificato
    //false: peso sballato || c = d || arco non esiste
    public boolean modificaPeso(char c, char d, Integer peso){

        boolean cambiato = false;

        if( (c != d) && (peso > 0) && (peso <= 10) && appartiene(c) && appartiene(d) ){

            for(Map.Entry<Node, ArrayList<Edge>> o: this.grafo.entrySet() ){
                if(o.getKey().getC() == c) {
                    for(int i = 0; i < o.getValue().size(); i++){
                        if( o.getValue().get(i).getDestination().getC() == d){
                            o.getValue().get(i).setWeight(peso);
                            cambiato = true;
                        }
                    }
                }
            }
        }
        return cambiato;
    }

    //INVERTE LA DIREZIONE DELL'ARCO ESISTENTE TRA IL NODO DI  NOME c E IL NODO DI NOME d
    //true: invertito
    //false: c = d || arco non esiste
    public boolean invertiDirezione(char c, char d){

        Integer peso = 0;
        boolean trovato = false;

        if( appartiene(c) && appartiene(d) && (c != d) ){

            //ciclo per recuperare il peso + eliminare l'arco
            for(Map.Entry<Node, ArrayList<Edge>> e: this.grafo.entrySet() ){
                if( e.getKey().getC() == c ){

                    int i = 0;

                    while(!trovato && i < e.getValue().size()){
                        if(e.getValue().get(i).getDestination().getC() == d){
                            peso = e.getValue().get(i).getWeight();
                            trovato = true;    //arco trovato - posso cambiare direzione
                            this.deleteEdge(c, d);
                        }
                        i++;
                    }
                }
            }

            //aggiungi nuovo arco - la funz insertEdge controlla se l'arco esiste già
            if(trovato) {
                this.insertEdge(d, c, peso);
            }
        }

    return  trovato;
    }

    //STAMPA SU TERMINALE IL GRAFO
    public void print() {

        for (Map.Entry<Node, ArrayList<Edge>> e : this.grafo.entrySet()) {
            System.out.print(e.getKey().getC() + " : ");
            if(e.getValue().size() == 0) System.out.print("[]");
            else {
                for (int i = 0; i < e.getValue().size(); i++)
                    System.out.print("[" + e.getValue().get(i).getDestination().getC() + ", " + e.getValue().get(i).getWeight() + "]");
            }
            System.out.println();   //vai a capo e stampa nuovo nodo e i suoi archi
        }

    }

    //RESTITUISCE LA COORDINATA x DEL NODO CON NOME c
    double get_x(char c){

        double x = 0;

        for(Node e: this.grafo.keySet()){
            if(e.getC() == c)
                x = e.getX();
        }
        return x;
    }

    //RESTITUISCE LA COORDINATA y DEL NODO CON NOME c
    double get_y(char c){

        double y = 0;

        for(Node e: this.grafo.keySet()){
            if(e.getC() == c)
                y = e.getY();
        }
        return y;
    }

    //IMPOSTA LE COORDINATE x E y DEL NODO CON NOME c
    void set_x_y(char c, double x, double y){

        for(Node e: this.grafo.keySet()){
            if(e.getC() == c) {
                e.setX(x);
                e.setY(y);
            }
        }

    }

    //RESTITUISCE IL PESO DELL'ARCO (ESISTENTE) TRA IL NODO CON NOME c E IL NODO CON NOME d
    Integer getPeso(char c, char d){

        boolean b = false;
        Integer peso = 0;

        for(Map.Entry<Node, ArrayList<Edge>> e: this.grafo.entrySet()){
            if( e.getKey().getC() == c ){
                int i = 0;
                while( i < e.getValue().size() && (!b)){
                    if(e.getValue().get(i).getDestination().getC() == d){
                        peso = e.getValue().get(i).getWeight();
                        b = true;
                    }
                    i++;
                }
            }
        }
        return peso;
    }

    //CONTROLLA SE ESISTE UN ARCO (DIREZIONATO) TRA IL NODO DI LETTERA c E IL NODO DI LETTERA d
    //true: esiste arco che parte da c e arriva a d
    boolean collegato(char c, char d){

        boolean esiste = false;

        if( appartiene(c) && appartiene(d) ) {

            for (Map.Entry<Node, ArrayList<Edge>> e : this.grafo.entrySet()) {
                if (e.getKey().getC() == c) {

                    int i = 0;
                    while ((i < e.getValue().size()) && (!esiste)) {
                        if (e.getValue().get(i).getDestination().getC() == d) {
                            esiste = true;
                        }
                        i++;
                    }
                }
            }
        }
        return esiste;
    }

//------------------------------------------------------------------------------------------------------------------
//FUNZIONI PER IL CORRETTO FUNZIONAMENTO DI JOHNSON

    //CONTROLLA CHE I DUE NODI c E d SIANO ADIACENTI
    //true: esiste collegamento
    //false: non esiste arco
    boolean adj(Node c, Node d){

        boolean esiste = false;

        for (Map.Entry<Node, ArrayList<Edge>> e : this.grafo.entrySet()) {
            if (e.getKey() == c) {

                int i = 0;
                while ((i < e.getValue().size()) && (!esiste)) {
                    if (e.getValue().get(i).getDestination() == d) {
                        esiste = true;
                    }
                    i++;
                }
            }
        }
        return esiste;
    }

    //DATO IL CARATTERE c RESTITUISCE IL NODO CHE SI CHIAMA c
    Node getNode(char c){

        Node nodo = null;

        for( Node n: this.grafo.keySet() ){
            if( n.getC() == c){
                nodo = n;
            }
        }
        return nodo;
    }

    //RESTITUISCE UN NODO CASUALE
    Node sputaNodoRandom(){

        Node nodo = null;

        for( Node n: this.grafo.keySet() ){
            nodo = n;
        }

        return nodo;
    }

    //CONTROLLA CHE NON CI SIANO NODI ISOLATI
    boolean partenzaJohnson() {
        boolean b=true;
        for (Map.Entry<Node, ArrayList<Edge>> e : this.grafo.entrySet()) {
            b=b && (partenzaNodo(e.getKey()) || arrivaNodo(e.getKey()));
        }
        return b;
    }

    //CONTROLLA CHE IL NODO e SIA UN NODO DI ARRIVO (IN UN ARCO)
    private boolean arrivaNodo(Node e){
        boolean bool = false;
        for(Map.Entry<Node, ArrayList<Edge>> p : this.grafo.entrySet()){
            for(int i=0; i<p.getValue().size(); i++){
                if(p.getValue().get(i).getDestination()== e)
                    bool=true;
            }
        }
        return bool;
    }

    //CONTROLLA CHE IL NODO e SIA UN NODO DI ARRIVO (IN UN ARCO)
    private boolean partenzaNodo(Node e){
        boolean esiste = false;

        for(Map.Entry<Node, ArrayList<Edge>> p : this.grafo.entrySet()) {

            if( p.getKey() == e && p.getValue().size() > 0 ){
                //controllo che il vettore archi sia maggiore di 0
                esiste = true;
            }
        }
        return esiste;
    }

    //RESTITUISCE LA DISTANZA(PESO) TRA IL NODO u E IL NODO v
    Integer w(Node u, Node v){
        Integer peso = 0;
        if( adj(u, v) )
            peso = getPeso(u.getC(), v.getC());
        else if( adj(v, u) )
            peso = getPeso(v.getC(), u.getC());
        return peso;
    }

    //-------------------------------------------------------------------------------------------------------------
    // FUNZIONI SUI FILE

    //INSERISCE I NODI NEL GRAFO DATA LA STRINGA DEL FILE DI TESTO
    public void inserisciNodiDaStringaTXT(String stringa){
        for(int i = 0; i < stringa.length(); i++){
            inserisciNodoTXT(stringa.charAt(i));
        }
    }

    //INSERISCE NEL GRAFO TUTTI I NODI DI NOME c SINGOLARMENTE
    //post: restituisce true se il nodo non appartiene e lo inserisce
    // altrimenti se e' gia' presente false
    public boolean inserisciNodoTXT(char c){
        boolean b=false;
        if( this.grafo.size() < 10 && !appartiene(c) ){
            Node u = new Node(c);
            this.grafo.put(u, new ArrayList<>());
            b=true;
        }
        return b;
    }

    //PERMETTE DI ESPORTARE UN GRAFO SU FILE TXT
    String esporta(){
        StringBuilder s= new StringBuilder();
        String q;
        for(char p='A'; p<'K'; p++) {
            if (this.appartiene(p)) {
                q=p+":";
                s.append(q);
                for (char k = 'A'; k < 'K'; k++) {
                    if (this.collegato(p, k)) {
                        q =k+ String.valueOf(this.getPeso(p, k)) + ",";
                        s.append(q);
                    }
                }
                s.append(". \n");
            }
        }
        return s.toString();
    }

    //PERMETTE DI ESPORTARE IL GRAFO DEL CAMMINO MINIMO SU FILE TXT
    String esporta_cammino(){
        StringBuilder s= new StringBuilder();
        String q;
        for(char p='A'; p<'K'; p++) {
            if (this.appartiene(p)) {
                q=p+":";
                s.append(q);
                for (char k = 'A'; k < 'K'; k++) {
                    if (this.collegato(p, k)) {
                        if((this.getNode(k).getT()==this.getNode(p) || this.getNode(p).getT()==this.getNode(k)) && collegato(this.getNode(p).getC(), this.getNode(k).getC())) {
                            q = k + String.valueOf(this.getPeso(p, k)) + ",";
                            s.append(q);
                        }
                    }
                }
                s.append(". \n");
            }
        }
        return s.toString();
    }

    //SCRIVE LA STRINGA CONTENTE IL GRAFO DA ESPORTARE SUL FILE
    void SaveFile(String content, File file){
        try {
            FileWriter fileWriter = null;
            fileWriter = new FileWriter(file);
            fileWriter.write(content);
            fileWriter.close();
        } catch (IOException ex) {
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //CONTROLLA CHE IL FILE NON SIA VUOTO
    public boolean fileVuoto(File file) throws IOException {
        boolean vuoto = false;
        if(file== null)
            vuoto=true;
        else {
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            if (br.readLine() == null)
                vuoto = true;
        }
        return vuoto;
    }

    //mette in una stringa i nodi presenti nel file.txt del grafo_di_appoggio
    String nodi(File file) throws IOException {
        String stringa = "";
        String tmp;
        FileReader filereader = new FileReader(file);
        BufferedReader lettore = new BufferedReader(filereader);

        String riga = lettore.readLine();

        while (riga != null) {
            tmp = String.valueOf(riga.charAt(0));
            stringa = stringa + tmp;
            riga = lettore.readLine();
        }
        return stringa;
    }

    private String due(File file) throws IOException {
        String stringa = "";
        String tmp;
        FileReader filereader = new FileReader(file);
        BufferedReader lettore = new BufferedReader(filereader);

        String riga = lettore.readLine();

        while (riga != null) {
            tmp = String.valueOf(riga.charAt(1));
            stringa = stringa + tmp;
            riga = lettore.readLine();
        }

        return stringa;
    }

    //CONTROLLA CHE IL CARATTERE c SIA UNA LETTERA TRA A E J
    public boolean lettere(char c) {
        boolean b = true;
        if (!(c < 75 && c > 64))
            b = false;

        return b;
    }

    //CONTROLLA CHE IL CARATTERE c SIA UN NUMERO
    private boolean numero(char c) {
        boolean b = true;
        if (!(c > 47 && c < 58)) b = false;
        return b;
    }

    //CONTROLLA CHE I NODI INSERITI SIANO COMPRESI TRA A E J E CHE SIANO MENO DI 10[
    private boolean possibile(String stringa) {
        boolean esiste = true;
        int i = 0;
        while (i < stringa.length()) {
            if (!(lettere(stringa.charAt(i)))) {
                esiste = false;
            }
            i++;
        }
        if (i > 10) esiste = false;
        return esiste;
    }

    //controlla che non ci sia più di un nodo/arco
    private boolean ripetizione(String stringa) {
        boolean t=true;
        int i = 0, f=0 ,g=0;
        if (stringa != null) {
            while (i < stringa.length()) {
                f=0;
                g=0;
                while (f < stringa.length()) {
                    if (stringa.charAt(f) == stringa.charAt(i)) g++;
                    f++;
                }
                if(g!=1){ t=false; out.println(1);}
                i++;
            }
            if (stringa.length() > 10) g++;
        }

        return t;
    }

    //controlla se dopo il nodo ci siano i ':'
    private boolean duepunti(String stringa){
        boolean bool = true;
        int i=0,g=0;
        while (i<stringa.length()) {
            if (stringa.charAt(1) != 58)
                g ++;
            i++;
        }
        if(g!=0) bool=false;
        return bool;
    }

    //in String c sono contenuti i nodi del grafo_di_appoggio (prima posizione delle righe)
    //controlla se un è possibile il collegamento ad un nodo
    private boolean presente(String c, char b){
        boolean t=false;
        int i=0;
        while(i<c.length())   {  if(c.charAt(i) == b)    t=true;
            i++;
        }

        return t;
    }


    //unione controlli dei nodi
    //richiama: ripetizione && possibile && duepunti && nodi
    private boolean testa(File file) throws IOException{
        String stringa = nodi(file);
        boolean b=true;
        if (!((ripetizione(stringa)) && (possibile(stringa)) && duepunti(due(file))))
            b=false;

        return b;
    }

    //unisce i vari controlli e controlla che ci siano tutti i punti e il collegamento da 10
    //richiama:
    private boolean controll2(File file) throws IOException{
        boolean b = true;
        FileReader filereader = new FileReader(file);
        BufferedReader lettore = new BufferedReader(filereader);
        String riga = lettore.readLine();
        while ( riga != null) {
            int i = 2;
            String t = null;
            while(riga.charAt(i)!=46 /*punto*/ ) {
                if (!(riga.charAt(i) == 44/*virgola*/)) {
                    if (!((lettere(riga.charAt(i))) && ( presente( nodi(file), riga.charAt(i))) && ripetizione(t))) {
                        b = false;
                    }
                    t = t + riga.charAt(i);
                    i++;
                    if (!(numero(riga.charAt(i)))) {
                        b = false;
                    }
                    if (riga.charAt(i) == 49) {
                        i++;
                        if (riga.charAt(i) == 48) {
                            i++;
                        }
                    }
                    else i=i+2;
                }
                if((riga.charAt(i)!=46)) b=false;
            }
            riga=lettore.readLine();
        }
        return b;
    }


    //unisce i controlli dei nodi e degli archi
    //richiama: testa && controll2
    boolean controlloFinale(File file) throws IOException{
        boolean b=true;
        if (!( testa(file) && controll2(file) && punto(file))) {
            b=false; out.println(7);
        }
        return b;
    }

    boolean punto(File file)throws IOException{
        boolean b=true;
        int i=0;
        FileReader filereader = new FileReader(file);
        BufferedReader lettore = new BufferedReader(filereader);
        String riga = lettore.readLine();
        while(riga!=null){
            while(i==riga.length()) i++;

            if(riga.charAt(i)!=46){b=false;}

            riga=lettore.readLine();
        }
        return b;
    }

}