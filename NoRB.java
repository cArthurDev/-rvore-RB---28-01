class NoRB {
    int valor;
    NoRB esq, dir, pai;
    boolean cor;

    NoRB(int valor) {
        this.valor = valor;
        this.esq = null;
        this.dir = null;
        this.pai = null;
        this.cor = true;
    }
}
