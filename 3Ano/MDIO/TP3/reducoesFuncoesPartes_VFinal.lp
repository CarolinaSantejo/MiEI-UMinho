/* Objective function */
min:custo0 + custo1 + custo3 + custo4 + custo5 + custo6 + custo8 + custo9 + custo10 + custo11 ;

//Atividade 0
R01: y01+y02<=1;
R02: r0=r0_1+r0_2;

R03: 0*y01<=r0_1;
R04: r0_1<=0.5*y01;
R05: 0.5*y02<=r0_2;
R06: r0_2<=1*y02;

custo0=0*y01 + 200*r0_1 + 50*y02 + 100*r0_2;

//Atividade 1

R11: y11+y12<=1;
R12: r1=r1_1+r1_2;

R13: 0*y11<=r1_1;
R14: r1_1<=1*y11;
R15: 1*y12<=r1_2;
R16: r1_2<=2*y12;

custo1= 0*y11 + 600 * r1_1 + 300*y12 + 300r1_2;

//Atividade 3

R31: y31+y32<=1;
R32: r3=r3_1+r3_2;

R33: 0*y31<=r3_1;
R34: r3_1<=0.5*y31;
R35: 0.5*y32<=r3_2;
R36: r3_2<=1*y32;

custo3= 0*y31 + 200 * r3_1 + 50*y32 + 100r3_2;

//Atividade 4

R41: y41+y42<=11;
R42: r4=r4_1+r4_2;

R43: 0*y41<=r4_1;
R44: r4_1<=2*y41;
R45: 2*y42<=r4_2;
R46: r4_2<=3*y42;

custo4= 0*y41 + 800*r4_1 + 800*y42 + 400r4_2;


//Atividade 5

R51: y51+y52<=1;
R52: r5=r5_1+r5_2;

R53: 0*y51<=r5_1;
R54: r5_1<=0.5*y51;
R55: 0.5*y52<=r5_2;
R56: r5_2<=1*y52;

custo5= 0*y51 + 1600*r5_1 + 1200*y52 + 800r5_2;

//Atividade 6

R61: y61+y62<=1;
R62: r6=r6_1+r6_2;

R63: 0*y61<=r6_1;
R64: r6_1<=1*y61;
R65: 1*y62<=r6_2;
R66: r6_2<=2*y62;

custo6= 0*y61 + 180*r6_1 + 90*y62 + 90r6_2;

//Atividade 8

R81: y81+y82<=1;
R82: r8=r8_1+r8_2;

R83: 0*y81<=r8_1;
R84: r8_1<=0.5*y81;
R85: 0.5*y82<=r8_2;
R86: r8_2<=1*y82;

custo8=0*y81 + 200*r8_1 + 50*y82 + 100*r8_2;


//Atividade 9

R91: y91 + y92 <= 1;
R92: r9 = y91 + 2*y92;

custo9= 200*y91 + 400*y92;


//Atividade 10

R101: y101+y102<=1;
R102: r10=r10_1+r10_2;

R103: 0*y101<=r10_1;
R104: r10_1<=0.5*y101;
R105: 0.5*y102<=r10_2;
R106: r10_2<=1*y102;

custo10= 0*y101 + 1000*r10_1 + 250*y102 + 500r10_2;

//Atividade 11

R111: y111+y112<=1;
R112: r11=r11_1+r11_2;

R113: 0*y111<=r11_1;
R114: r11_1<=1*y111;
R115: 1*y112<=r11_2;
R116: r11_2<=2*y112;

custo11= 0*y111 + 600 * r11_1 + 300*y112 + 300r11_2;


Rf: tf <= 20;

// relações de precedência
// na restrição tj >= ti - ri + di, a fun<ão ti - ri + di designa
// o tempo de conclusão da actividade i após a redução da duração,
// de di para -ri + di

arco_01: t1 >= t0 - r0_1 - r0_2 + 4 ;
arco_i0: t0 >= ti + 0 ;
arco_13: t3>= t1 - r1_1 - r1_2 + 6;
arco_04: t4 >= t0 - r0_1 - r0_2 + 4 ;
arco_53: t3 >= t5 - r5_1 - r5_2 + 4 ;
arco_3f: tf >= t3 - r3_1 - r3_2 + 2 ;
arco_45: t5 >= t4 - r4_1 - r4_2 + 9 ;
arco_43: t3 >= t4 - r4_1 - r4_2 + 9 ;
arco_64: t4 >= t6 - r6_1 - r6_2 + 5;
arco_68: t8 >= t6 - r6_1 - r6_2 + 5;
arco_5f: tf >= t5 - r5_1 - r5_2 + 4 ;
arco_i6: t6 >= ti + 0 ;
arco_85: t5 >= t8 - r8_1 - r8_2 + 4 ;
arco_9f: tf >= t9 - r9_1 - r9_2 + 2 ;
arco_89: t9 >= t8 - r8_1 - r8_2 + 4 ;
arco_610: t10 >= t6 - r6_1 - r6_2 + 5 ;
arco_108: t8 >= t10 - r10_1 - r10_2 + 8 ;
arco_119: t9 >= t11 - r11_1 - r11_2 + 7 ;
arco_1011: t11 >= t10 - r10_1 - r10_2 + 8;


bin y01,y02,y11,y12,y31,y32,y41,y42,y51,y52,y61,y62,y81,y82,y91,y92,y101,y102,y111,y112;
