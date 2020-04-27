package algraph;

public interface IGraph{

    boolean insertNode();

    boolean deleteNode(char c);

    boolean insertEdge(char c, char d, Integer peso);

    boolean deleteEdge(char c, char d);

    boolean modificaPeso(char c, char d, Integer peso);

    boolean invertiDirezione(char c, char d);

    void print();
}
