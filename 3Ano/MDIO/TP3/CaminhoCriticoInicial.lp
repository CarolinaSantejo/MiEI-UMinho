// xij -> variáveis binárias que indicam se o arco correspondente pertence ao caminho crítico
max: 4 x01 + 4 x04 + 5 x64 + 5 x68 + 5 x6_10 + 6 x13 + 9 x43 + 4 x53
   + 9 x45 + 4 x85 + 4 x89 + 8 x10_8 + 8 x10_11 + 7 x11_9 + 2 x3f + 4 x5f + 2 x9f;

//fluxo que entra no vértice = fluxo que sai
vertice_i: xi0 + xi6 = 1;
vertice_0: xi0 = x01 + x04;
vertice_1: x01 = x13;
vertice_3: x13 + x43 + x53 = x3f;
vertice_4: x04 + x64 = x45 + x43;
vertice_5: x45 + x85 = x53 + x5f;
vertice_6: xi6 = x64 + x68+ x6_10;
vertice_8: x68 + x10_8 = x85 + x89;
vertice_9: x89 + x11_9 = x9f;
vertice_10: x6_10 = x10_8 + x10_11;
vertice_11: x10_11 = x11_9;


bin xi0 , xi6 , x01 , x04 , x64 , x68 , x6_10 , x13;
bin x45, x43 , x85 , x89 , x10_8 , x10_4 , x11_9 , x3f , x5f , x9f;