
public class Fila {

    private Double[] valores;
    private int primeiro;
    private int ultimo;
    private Double total;

    public Fila(){
        valores = new Double[100];
        primeiro = 0;
        ultimo = 0;
        total = 0.0;
    }

    public void inserir(Double elemento) {
        if(isFull()){
            throw new RuntimeException("\n\nFila cheia!\n\n");
        }
        valores[ultimo] = elemento;
        ultimo = (ultimo+1) % valores.length;
        total++;
    }

    public Double retirar(){
        if(isEmpty()){
            throw new RuntimeException("\n\nFila vazia!\n\n");
        }
        Double elemento = valores[primeiro];
        primeiro = (primeiro + 1) % valores.length;
        total--;
        return elemento;
    }

    public Boolean isEmpty(){
        return total == 0;
    }

    public Boolean isFull(){
        return total==valores.length;
    }


}