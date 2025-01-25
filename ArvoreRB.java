import java.util.Stack;

public class ArvoreRB {
    private NoRB raiz;

    private void rotacaoEsquerda(NoRB no) {
        NoRB y = no.dir;
        no.dir = y.esq;

        if (y.esq != null)
            y.esq.pai = no;

        y.pai = no.pai;

        if (no.pai == null) {
            raiz = y;
        } else if (no == no.pai.esq) {
            no.pai.esq = y;
        } else {
            no.pai.dir = y;
        }

        y.esq = no;
        no.pai = y;
    }

    private void rotacaoDireita(NoRB no) {
        NoRB y = no.esq;
        no.esq = y.dir;

        if (y.dir != null)
            y.dir.pai = no;

        y.pai = no.pai;

        if (no.pai == null) {
            raiz = y;
        } else if (no == no.pai.dir) {
            no.pai.dir = y;
        } else {
            no.pai.esq = y;
        }

        y.dir = no;
        no.pai = y;
    }

    public void inserir(int valor) {
        NoRB novoNo = new NoRB(valor);
        raiz = inserirRec(raiz, novoNo);
        corrigirInsercao(novoNo);
    }

    private NoRB inserirRec(NoRB raiz, NoRB novoNo) {
        if (raiz == null) {
            return novoNo;
        }

        if (novoNo.valor < raiz.valor) {
            raiz.esq = inserirRec(raiz.esq, novoNo);
            raiz.esq.pai = raiz;
        } else if (novoNo.valor > raiz.valor) {
            raiz.dir = inserirRec(raiz.dir, novoNo);
            raiz.dir.pai = raiz;
        }

        return raiz;
    }

    private void corrigirInsercao(NoRB no) {
        NoRB pai, avo;

        while (no != raiz && no.cor && no.pai.cor) {
            pai = no.pai;
            avo = pai.pai;

            if (pai == avo.esq) {
                NoRB tio = avo.dir;

                if (tio != null && tio.cor) {
                    avo.cor = true;
                    pai.cor = false;
                    tio.cor = false;
                    no = avo;
                } else {
                    if (no == pai.dir) {
                        rotacaoEsquerda(pai);
                        no = pai;
                        pai = no.pai;
                    }

                    rotacaoDireita(avo);
                    boolean tempCor = pai.cor;
                    pai.cor = avo.cor;
                    avo.cor = tempCor;
                    no = pai;
                }
            } else {
                NoRB tio = avo.esq;

                if (tio != null && tio.cor) {
                    avo.cor = true;
                    pai.cor = false;
                    tio.cor = false;
                    no = avo;
                } else {
                    if (no == pai.esq) {
                        rotacaoDireita(pai);
                        no = pai;
                        pai = no.pai;
                    }

                    rotacaoEsquerda(avo);
                    boolean tempCor = pai.cor;
                    pai.cor = avo.cor;
                    avo.cor = tempCor;
                    no = pai;
                }
            }
        }

        raiz.cor = false;
    }

    public void remover(int valor) {
        NoRB noParaRemover = buscarNo(raiz, valor);
        if (noParaRemover == null) {
            System.out.println("Valor não encontrado na árvore.");
            return;
        }

        NoRB substituto;
        NoRB filho;
        boolean corOriginal = noParaRemover.cor;

        if (noParaRemover.esq == null) {
            filho = noParaRemover.dir;
            substituirNo(noParaRemover, noParaRemover.dir);
        } else if (noParaRemover.dir == null) {
            filho = noParaRemover.esq;
            substituirNo(noParaRemover, noParaRemover.esq);
        } else {
            substituto = minimo(noParaRemover.dir);
            corOriginal = substituto.cor;
            filho = substituto.dir;

            if (substituto.pai == noParaRemover) {
                if (filho != null) {
                    filho.pai = substituto;
                }
            } else {
                substituirNo(substituto, substituto.dir);
                substituto.dir = noParaRemover.dir;
                substituto.dir.pai = substituto;
            }

            substituirNo(noParaRemover, substituto);
            substituto.esq = noParaRemover.esq;
            substituto.esq.pai = substituto;
            substituto.cor = noParaRemover.cor;
        }

        if (!corOriginal) {
            corrigirRemocao(filho);
        }
    }

    private NoRB minimo(NoRB no) {
        while (no.esq != null) {
            no = no.esq;
        }
        return no;
    }

    private void substituirNo(NoRB antigo, NoRB novoNo) {
        if (antigo.pai == null) {
            raiz = novoNo;
        } else if (antigo == antigo.pai.esq) {
            antigo.pai.esq = novoNo;
        } else {
            antigo.pai.dir = novoNo;
        }

        if (novoNo != null) {
            novoNo.pai = antigo.pai;
        }
    }

    private void corrigirRemocao(NoRB no) {
        while (no != raiz && (no == null || !no.cor)) {
            if (no == no.pai.esq) {
                NoRB irmao = no.pai.dir;

                if (irmao != null && irmao.cor) {
                    irmao.cor = false;
                    no.pai.cor = true;
                    rotacaoEsquerda(no.pai);
                    irmao = no.pai.dir;
                }

                if ((irmao.esq == null || !irmao.esq.cor) &&
                        (irmao.dir == null || !irmao.dir.cor)) {
                    irmao.cor = true;
                    no = no.pai;
                } else {
                    if (irmao.dir == null || !irmao.dir.cor) {
                        if (irmao.esq != null) {
                            irmao.esq.cor = false;
                        }
                        irmao.cor = true;
                        rotacaoDireita(irmao);
                        irmao = no.pai.dir;
                    }

                    irmao.cor = no.pai.cor;
                    no.pai.cor = false;
                    if (irmao.dir != null) {
                        irmao.dir.cor = false;
                    }
                    rotacaoEsquerda(no.pai);
                    no = raiz;
                }
            } else {
                NoRB irmao = no.pai.esq;

                if (irmao != null && irmao.cor) {
                    irmao.cor = false;
                    no.pai.cor = true;
                    rotacaoDireita(no.pai);
                    irmao = no.pai.esq;
                }

                if ((irmao.dir == null || !irmao.dir.cor) &&
                        (irmao.esq == null || !irmao.esq.cor)) {
                    irmao.cor = true;
                    no = no.pai;
                } else {
                    if (irmao.esq == null || !irmao.esq.cor) {
                        if (irmao.dir != null) {
                            irmao.dir.cor = false;
                        }
                        irmao.cor = true;
                        rotacaoEsquerda(irmao);
                        irmao = no.pai.esq;
                    }

                    irmao.cor = no.pai.cor;
                    no.pai.cor = false;
                    if (irmao.esq != null) {
                        irmao.esq.cor = false;
                    }
                    rotacaoDireita(no.pai);
                    no = raiz;
                }
            }
        }

        if (no != null) {
            no.cor = false;
        }
    }

    private NoRB buscarNo(NoRB no, int valor) {
        if (no == null || no.valor == valor) {
            return no;
        }

        if (valor < no.valor) {
            return buscarNo(no.esq, valor);
        } else {
            return buscarNo(no.dir, valor);
        }
    }

    public void printTree() {
        if (raiz == null) {
            System.out.println("Árvore vazia.");
            return;
        }

        Stack<NoRB> globalStack = new Stack<>();
        globalStack.push(raiz);
        int gaps = 64;
        boolean isRowEmpty = false;
        String separator = "-----------------------------------------------------------------";
        System.out.println(separator);

        while (!isRowEmpty) {
            Stack<NoRB> localStack = new Stack<>();
            isRowEmpty = true;

            for (int j = 0; j < gaps; j++) System.out.print(' ');

            while (!globalStack.isEmpty()) {
                NoRB temp = globalStack.pop();
                if (temp != null) {
                    String cor = temp.cor ? "R" : "B";
                    System.out.print(temp.valor + "(" + cor + ")");
                    localStack.push(temp.esq);
                    localStack.push(temp.dir);

                    if (temp.esq != null || temp.dir != null) isRowEmpty = false;
                } else {
                    System.out.print("___");
                    localStack.push(null);
                    localStack.push(null);
                }

                for (int j = 0; j < gaps * 2 - 2; j++) System.out.print(' ');
            }

            System.out.println();
            gaps /= 2;

            while (!localStack.isEmpty()) globalStack.push(localStack.pop());
        }

        System.out.println(separator);
        printFilhos(raiz, "");
    }

    private void printFilhos(NoRB no, String prefixo) {
        if (no == null) return;

        String cor = no.cor ? "R" : "B";
        System.out.println(prefixo + "Nó: " + no.valor + "(" + cor + ")");
        if (no.esq != null) {
            String corEsq = no.esq.cor ? "R" : "B";
            System.out.println(prefixo + "  Esquerdo -> " + no.esq.valor + "(" + corEsq + ")");
        } else {
            System.out.println(prefixo + "  Esquerdo -> NULL");
        }

        if (no.dir != null) {
            String corDir = no.dir.cor ? "R" : "B";
            System.out.println(prefixo + "  Direito -> " + no.dir.valor + "(" + corDir + ")");
        } else {
            System.out.println(prefixo + "  Direito -> NULL");
        }

        printFilhos(no.esq, prefixo + "  ");
        printFilhos(no.dir, prefixo + "  ");
    }

}
