import  java.lang.Math;


public class Matriz_Nao_Linear{

    public static void main(String[] args) {
        Fila f = new Fila();									//Chamada de Pilha
        //Double[][] Matriz2 = { {8.0, 7.5, 8.5, 9.0, 8.0 }, {8.9, 9.0, 8.6, 8.4, 8.0 }, {6.8, 7.1, 7.0, 7.6, 6.5 } };
        Double precisao1 = 0.0001;
        Double precisao2 = 0.0;
        Double dado1 = 1.0;
        Double dado2 = 5.0;

        Double[][] X0 = {   {dado1},
                            {dado2}};

        /*Double[][] X = {    {1.0,1.0,-3.0},             //O Ultimo Nunca Sera um X;
                            {1.0,1.0,-9.0}};*/

        /*for (int l = 0; l < Matriz2.length; l++)  {  
            for (int c = 0; c < Matriz2[0].length; c++)     { 
                System.out.print(Matriz2[l][c] + "  "); //imprime caracter a caracter
            }  
            System.out.println(" "); //muda de linha
        }*/
        System.out.println("	X1		X2	     F1	  	     F2 	 F1/ X1		 F1/X2		F2/X1	       F2/X2           S1	     S2 ");
        do{
            for(int i=0;i<X0.length;i++){
                f.inserir(X0[i][0]);
            }

            Double funcao1 = F1( X0[0][0], X0[1][0] );                              //Achando F(X0);
            Double funcao2 = F2( X0[0][0], X0[1][0] );

            Double[][] FX0 = {  {funcao1},
                                {funcao2}};                                         //Fim do Passo 1
            for(int i=0;i<FX0.length;i++){
                f.inserir(FX0[i][0]);
            }
            precisao2 = verificaPrecisao(precisao2, FX0);                           //Passo 2
            //System.out.println("\n\nP1: "+precisao1+"\n\nP2: "+precisao2+"\n\n");
                Double derF1X1 = derivadaF1X1( X0[0][0], X0[1][0], precisao1 );     //Achando Precisão
                Double derF1X2 = derivadaF1X2( X0[0][0], X0[1][0], precisao1 );
                Double derF2X1 = derivadaF2X1( X0[0][0], X0[1][0], precisao1 );
                Double derF2X2 = derivadaF2X2( X0[0][0], X0[1][0], precisao1 );

                Double[][] JX0 = {  {derF1X1, derF1X2},
                                    {derF2X1, derF2X2}};
                for(int i=0;i<JX0.length;i++){
                    for(int j=0;j<JX0[0].length;j++){
                        f.inserir(JX0[i][j]);
                    }
                }
                /*System.out.println(funcao1+ " " +funcao2);
                for (int l = 0; l < JX0.length; l++)  {  
                    for (int c = 0; c < JX0[0].length; c++)     { 
                        System.out.print(JX0[l][c] + "  "); //imprime caracter a caracter
                    }  
                    System.out.println(" "); //muda de linha
                }*/

                Double[][] S = new Double[FX0.length][FX0[0].length];

                AchaS(JX0, FX0, S);
                for(int i=0;i<S.length;i++){
                    for(int j=0;j<S[0].length;j++){
                        f.inserir(S[i][j]);
                    }
                }
                NewX(X0, S);

        while(!f.isEmpty()){
			Double num =  f.retirar();
			if(num<0.0)
            System.out.printf("|    %.6f|", num); 
				else{
						if(num<10)
							System.out.printf("|     %.6f|", num); 
							else
								System.out.printf("|    %.6f|", num); 
				}
        }
        System.out.println(" ");
        }while(precisao2 > precisao1);
    }

    public static Double verificaPrecisao(Double precisao2, Double FX0[][]){
        Double valor = 0.0;
            
        for(int l=0; l<FX0.length; l++){
            valor += Math.pow(FX0[l][0],2);
        }

        precisao2 = Math.sqrt(valor);
        valor = 0.0;
        //System.out.println("\nPrecisao: "+precisao2);
        return precisao2;
        
    }

    public static Double F1(Double x1, Double x2){
        Double resp;

        resp = x1 + x2 - 3;
        //System.out.println(resp);
        return resp;
    }

    public static Double F2(Double x1, Double x2){
        Double resp;

        resp = x1*x1 + x2*x2 - 9;
        //System.out.println(resp);
        return resp;
    }

    public static Double derivadaF1X2(Double x1, Double x2, Double precisao1){
        Double temp = x2 + precisao1;

        Double deriva = (F1(x1, temp) - F1(x1, x2))/precisao1;
        Math.floor(deriva);
        //System.out.println("F1/X2: "+deriva);
        return deriva;
    }

    public static Double derivadaF2X2(Double x1, Double x2, Double precisao1){
        Double temp = x2 + precisao1;

        Double deriva = (F2(x1, temp) - F2(x1, x2))/precisao1;
        Math.floor(deriva);
        //System.out.println("F2/X2: "+deriva);
        return deriva;
    }

    public static Double derivadaF1X1(Double x1, Double x2, Double precisao1){
        Double temp = x1 + precisao1;

        Double deriva = (F1(temp, x2) - F1(x1, x2))/precisao1;
        Math.floor(deriva);
        //System.out.println("F1/X1: "+deriva);
        return deriva;
    }

    public static Double derivadaF2X1(Double x1, Double x2, Double precisao1){
        Double temp = x1 + precisao1;

        Double deriva = (F2(temp, x2) - F2(x1, x2))/precisao1;
        Math.floor(deriva);
        //System.out.println("F2/X1: "+deriva);
        return deriva;
    }

    public static void NewX(Double X0[][], Double S[][]){
        //System.out.println("\nEncontrando os Novos Valores de 'X'\n");
        for(int l = 0; l < X0.length; l++){
            X0[l][0] = S[l][0] + X0[l][0];
            //System.out.println("X"+(l+1)+": " + X0[l][0]);
        }
    }

    public static void AchaS(Double JX0[][], Double FX0[][], Double S[][]){
        Double[][] MatrizTemp = new Double[JX0.length][(JX0[0].length + 1)];

        for(int l = 0; l < JX0.length; l++){
            for(int c = 0; c < JX0[0].length; c++){
                MatrizTemp[l][c] = JX0[l][c];
            }
            int col = (JX0[0].length);
            //System.out.println(col);
                MatrizTemp[l][col] = -FX0[l][0];
        }
        
            /*for(int l = 0; l < JX0.length; l++){
                for(int c = 0; c <= JX0[0].length; c++){
                    System.out.print("  " + MatrizTemp[l][c]);
                }
                System.out.println(" ");
            }*/
        
        for(int c = 0; c < MatrizTemp[0].length; c++){
            for(int l = c; l < (MatrizTemp.length-1); l++){
                if(MatrizTemp[l][c]==0.0){                                //Verificando se o PIVO é zero
                    for(int k = 0; k < JX0[0].length; k++){            //Contador de Linhas apartir da coluna em questão
                        Double aux = MatrizTemp[l][k];
                        if((l+1) <= JX0.length){                        //Verificando se tem como trocar com a de baixo
                            MatrizTemp[l][k] = MatrizTemp[(l+1)][k];
                            MatrizTemp[(l+1)][k] = aux;
                        } else{                                         //Caso Não tenha como trocar com a próxima, trocar com a anterior
                            MatrizTemp[l][k] = MatrizTemp[(l-1)][k];
                            MatrizTemp[(l-1)][k] = aux;
                        }
                        l--;                                            //Warning
                    }
                } else{   //Fim do teste do Pivo

                    int pos = (l+1);                                        //Salvando a Próxima linha
                        if(MatrizTemp[pos][c] != 0.0000){                    //Verificando se != de zero para poder realizar gauss
                            Double Pivo1 = MatrizTemp[l][c];
                            Double Pivo2 = MatrizTemp[pos][c];                            
                            for(int k = c; k < MatrizTemp[0].length; k++){    //Multiplicando toda a linha elo pivo
                                //System.out.print("\n\n1: "+ MatrizTemp[l][k] + "\nPivo1: " + Pivo1 + "\n3: "+MatrizTemp[pos][k]+"\nPivo2: "+Pivo2+"\n\n");
                                
                                MatrizTemp[pos][k] = (MatrizTemp[l][k]*Pivo2) - (MatrizTemp[pos][k]*Pivo1);
                            }
                        }
                }
            }
        }

        /*for(int l = 0; l < JX0.length; l++){                            //Mostrando Matriz Triangulada Pronta
            for(int c = 0; c <= JX0[0].length; c++){
                System.out.print("      " + MatrizTemp[l][c]);
            }
            System.out.println(" ");
        }*/
                                                                        //Obtendo os valores de S
        for(int i = MatrizTemp.length-1; i >= 0; i--){
            int aux;
            
            S[i][0]=MatrizTemp[i][(MatrizTemp[0].length-1)];
            aux = (MatrizTemp.length-1) - i;
                for(aux=aux; aux>0; aux--){
                    S[i][0] = S[i][0] - MatrizTemp[i][(MatrizTemp.length - aux)]*S[(MatrizTemp.length - aux)][0];
                }
            S[i][0]=S[i][0]/MatrizTemp[i][i];
            aux = 0;
        }

        /*System.out.println("\n\nS's das Funcoes\n");
        for(int i = 0; i < S.length; i++){
            System.out.println("S" + (i+1) + ": " + S[i][0]);
        }*/

    }
}
