import java.util.Random;

public interface Randoms {
    //gera aleatoriamente um tempo de espera mais um atraso na loja(caso se verifique)
    default double calculaTempo(double time) {
        Random rand = new Random();
        //15% de probabilidade de acontecer um atraso na loja
        boolean val = rand.nextInt(15)==0;
        if (val) time +=((Math.random() * (0.17 - 0.02)) + 0.02);
        else{
            //15% de probabilidade de a encomenda se ter despachado mais rapido
        boolean val1 = rand.nextInt(15)==0;
        if (val1)
        //Despacho entre 1% a 90% do tempo inicialmente esperado
        time *= 1-(((Math.random() * (0.9 - 0.01)) + 0.01));
        }
        return time;
    }
    
    
    default double calculaVelocidadeVol() {
        double v;
        v = (Math.random() * (100 - 75)) + 75;
        return v;
    }
    
    default double calculaVelocidadeTransp() {
        double v;
        v = (Math.random() * (150 - 90)) + 90;
        return v;
    }
    
    default double atraso(){
        double atraso = 0;
        Random rand = new Random();
        //20% de probabilidade de acontecer um atraso
        boolean val = rand.nextInt(20)==0;
        if (val){
            // 3 situacoes de atraso diferentes (1->atraso minimo,2->atraso medio,3->grande atraso) 
            int caso = rand.nextInt((3 - 1) + 1) + 1;
            switch (caso){
                case 1:
                //atraso de 3 a 28 minutos 
                atraso = (Math.random() * (0.47 - 0.05)) + 0.05;
                break;
                
                case 2:
                //atraso 35 a 55 minutos
                atraso = (Math.random() * (0.92 - 0.58)) + 0.58;
                break;
                
                case 3:
                //atraso de 1 hora a 4 horas 
                atraso = (Math.random() * (4 - 1)) + 1;
                break;
            }
        }
        return atraso;
    }
}
