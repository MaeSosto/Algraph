package algraph;

public class Edge {

    private Integer weight;
    private Node destination;

    //COSTRUTTORE
    Edge(Node arrivo, Integer peso){
        this.destination = arrivo;
        this.weight = peso;
    }

    //GETTERS AND SETTERS
    Integer getWeight() {
        return weight;
    }

    void setWeight(Integer weight) {
        this.weight = weight;
    }

    Node getDestination() {
        return destination;
    }

    public void setDestination(Node destination) {
        this.destination = destination;
    }
}
