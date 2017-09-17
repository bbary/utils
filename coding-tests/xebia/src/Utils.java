
enum orientationEnum{
    N, E, S, W;

    static public orientationEnum parse(char or){
        switch(or){
            case 'N':
                return N;
            case 'E':
                return E;
            case 'S':
                return S;
            case 'W':
                return W;
            default:
                System.out.println("expected N, E, S or W but found "+ or);
                System.exit(1);

        }
        return null;
    }

    public orientationEnum rotateD(){
        switch(this){
            case N:
                return E;
            case E:
                return S;
            case S:
                return W;
            case W:
                return N;
            default:
                return null;
        }
    }

    public orientationEnum rotateG(){
        switch(this){
            case N:
                return W;
            case W:
                return S;
            case S:
                return E;
            case E:
                return N;
            default:
                return null;
        }
    }
}

enum moveEnum{
    D, G, A;

    static public moveEnum parse(char move){
        switch(move){
            case 'D':
                return D;
            case 'G':
                return G;
            case 'A':
                return A;
            default:
                System.out.println("expected D, G or A but found "+ move);
                System.exit(1);

        }
        return null;
    }
}


