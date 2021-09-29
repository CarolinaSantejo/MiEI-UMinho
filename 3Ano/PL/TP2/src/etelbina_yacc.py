import ply.yacc as yacc
from etelbina_lex import tokens


def p_Aplicacao(p):
    "Aplicacao : Declaracoes Content"
    fwrite.write(f"{p[1]}{p[2]}")

def p_Content(p):
    "Content : function Instrucoes end"
    p[0] = "start\n" + str(p[2]) + "stop\n"

def p_Declaracoes(p):
    " Declaracoes : Declaracao '.' Declaracoes"
    p[0] = p[1] + p[3]

def p_DeclaracoesEmpty(p):
    "Declaracoes : "
    p[0] = ""

def p_Declaracao(p):
    "Declaracao : int var"
    if p[2] in p.parser.variaveis:
        p[0] = f"err \"ERROR. Variavel ja declarada:\"\n"
        print(f"ERROR. Variável já declarada:'{p[2]}'")
        parser.success = False
    else:
        p.parser.variaveis[p[2]] = ["int",p.parser.sp]
        p[0]=("pushi 0\n")
        p.parser.sp+=1

def p_Declaracao_Array(p):
    "Declaracao : int var '[' num ']'"
    if p[2] in p.parser.variaveis:
        p[0] = f"err \"ERROR. Variavel ja declarada:\"\n"
        print(f"ERROR. Variável já declarada:'{p[2]}'")
        parser.success = False
    else:
        p[0]=(f"pushn {int(p[4])}\n")
        p.parser.variaveis[p[2]] = ["array",int(p[4]),p.parser.sp]
        p.parser.sp+=int(p[4])

def p_Declaracao_DoubleArray(p):
    "Declaracao : int var '[' num ']' '[' num ']'"
    if p[2] in p.parser.variaveis:
        p[0] = f"err \"ERROR. Variavel ja declarada:\"\n"
        print(f"ERROR. Variável já declarada:'{p[2]}'")
        parser.success = False
    else:
        p[0]=(f"pushn {(int(p[4])*int(p[7]))}\n")
        p.parser.variaveis[p[2]] = ["array",(int(p[4])*int(p[7])),int(p[4]),int(p[7]),p.parser.sp]
        p.parser.sp+=(int(p[4])*int(p[7]))


def p_Declaracao_atribuicao(p):
    "Declaracao : int var '=' num"
    if p[2] in p.parser.variaveis:
        p[0] = f"err \"ERROR. Variavel ja declarada:\"\n"
        print(f"ERROR. Variável já declarada:'{p[2]}'")
        parser.success = False
    else:
        p.parser.variaveis[p[2]] = ["int",p.parser.sp]
        p[0]=(f"pushi {p[4]}\n")
        p.parser.sp+=1

def p_Declaracao_Array_Atribuicao(p):
    "Declaracao : int var '[' num ']' '=' '{' Array '}'"
    if p[2] in p.parser.variaveis:
        p[0] = f"err \"ERROR. Variavel ja declarada:\"\n"
        print(f"ERROR. Variável já declarada:'{p[2]}'")
        parser.success = False
    else:
        p[0] = p[8]
        if p.parser.elemsCount == int(p[4]):
            p.parser.variaveis[p[2]] = ["array",int(p[4]),p.parser.sp]
            p.parser.sp+=int(p[4])
        else:
            p[0] = f"err \"ERROR. Elementos do array nao coincide com a declaracao\"\n"
            print(f"ERROR. Elementos do array nao coincide com a declaracao'{p[2]}'")
            parser.success = False
    p.parser.elemsCount = 0


def p_Declaracao_DoubleArray_Atribuicao(p):
    "Declaracao : int var '[' num ']' '[' num ']' '=' '{' DoubleArray '}'"
    if p[2] in p.parser.variaveis:
        p[0] = f"err \"ERROR. Variavel ja declarada:\"\n"
        print(f"ERROR. Variável já declarada:'{p[2]}'")
        parser.success = False
    else:
        p[0] = p[11]
        i = int(p[4])
        j = int(p[7])
        print(p.parser.elems2Count)
        print(p.parser.elemsCount)
        print(p.parser.colCount)
        if (i==p.parser.elems2Count)and(j==p.parser.colCount)and(i*j==p.parser.elemsCount):
            p.parser.variaveis[p[2]] = ["array",i*j,i,j,p.parser.sp]
            p.parser.sp+=(i*j)
        else:
            p[0] = f"err \"ERROR. Elementos do array nao coincide com a declaracao\"\n"
            print(f"ERROR. Elementos do array nao coincide com a declaracao'{p[2]}'")
            parser.success = False
    p.parser.elemsCount = 0
    p.parser.elems2Count = 0
    p.parser.bool = 1


# ------------------ ARRAY/DOUBLE-ARRAY --------------------------

def p_DoubleArray(p):
    "DoubleArray : '{' Array '}' ElemsDArray"
    p[0] = p[2] + p[4]
    p.parser.elems2Count +=1

def p_ElemsDArray(p):
    "ElemsDArray : ',' '{' Array '}' ElemsDArray "
    p[0] = p[3] + p[5]
    p.parser.elems2Count +=1

def p_ElemsDArrayEmpty(p):
    "ElemsDArray : "
    p[0] = ""

def p_Array(p):
    "Array : num Elems"
    p[0] = f"pushi {p[1]}\n" + p[2]
    p.parser.elemsCount +=1
    if(p.parser.bool==1):
        p.parser.colCount = p.parser.elemsCount
        p.parser.bool=0

def p_Elems(p):
    "Elems : ',' num Elems"
    p[0] = f"pushi {p[2]}\n" + p[3]
    p.parser.elemsCount +=1

def p_Elems_Empty(p):
    "Elems : "
    p[0] = ""
    
    



# ----------------- INSTRUCOES ----------------------
def p_Instrucoes(p):
    "Instrucoes : Instrucao '.' Instrucoes"
    p[0] = p[1] + p[3]

def p_Instrucoes_Empty(p):
    "Instrucoes : "
    p[0] = ""

# ----------------- INSTRUÇÃO -------------------------

def p_Instrucao_Loop(p):
    "Instrucao : Loop"
    p[0] = p[1]

def p_Instrucao_Condition(p):
    "Instrucao : Condition"
    p[0]=(f"{p[1]}")


def p_Instrucao_Print(p):
    "Instrucao : Print"
    p[0]=(f"{p[1]}")
    

def p_Instrucao_Read(p):
    "Instrucao : var '=' read "
    if p[1] not in p.parser.variaveis:
        p[0] = f"err \"ERROR. Variavel nao declarada:\"\n"
        print(f"ERROR. Variável não declarada:'{p[1]}'")
        parser.success = False
    else:
        p[0]=("read\n")
        #fwrite.write(f"storeg {p.parser.variaveis[p[1]]}\n")
        #p.parser.sp+=2


def p_Instrucao_ExpressaoArray(p):
    "Instrucao : var '[' num ']' '=' Expressao"
    if p[1] not in p.parser.variaveis:
        p[0] = f"err \"ERROR. Variavel nao declarada:\"\n"
        print(f"ERROR. Variável não declarada:'{p[1]}'")
        parser.success = False
    else:
        if(int(p[3])<p.parser.variaveis[p[1]][1]):
            p[0] = f"{p[6]}storeg {(p.parser.variaveis[p[1]][2]+int(p[3]))}\n"
        else:
            p[0] = f"err \"ERROR. Out of range\"\n"
            print(f"ERROR. Out of range'{p[1]}'")
            parser.success = False

def p_Instrucao_ExpressaoDoubleArray(p):
    "Instrucao : var '[' num ']' '[' num ']' '=' Expressao"
    i = int(p[3])
    j = int(p[6])
    if p[1] not in p.parser.variaveis:
        p[0] = f"err \"ERROR. Variavel nao declarada:\"\n"
        print(f"ERROR. Variável não declarada:'{p[1]}'")
        parser.success = False
    else:
        col = p.parser.variaveis[p[1]][3]
        lin = p.parser.variaveis[p[1]][2]
        if(i<lin)and(j<col):
            p[0] = f"{p[9]}storeg {(p.parser.variaveis[p[1]][4]+(i*col)+j)}\n"
        else :
            p[0] = f"err \"ERROR. Out of range.\"\n"
            print(f"ERROR.  Out of range.'{p[1]}'")
            parser.success = False

def p_Instrucao_Expressao(p):
    "Instrucao : var '=' Expressao"
    if p[1] not in p.parser.variaveis:
        p[0] = f"err \"ERROR. Variavel nao declarada:\"\n"
        print(f"ERROR. Variável não declarada:'{p[1]}'")
        parser.success = False
    else:
        p[0]=(f"{p[3]}storeg {p.parser.variaveis[p[1]]}\n")

def p_Instrucao_MaisMais(p):
    "Instrucao : var inc"
    if p[1] not in p.parser.variaveis:
        p[0] = f"err \"ERROR. Variavel nao declarada:\"\n"
        print(f"ERROR. Variável não declarada:'{p[1]}'")
        parser.success = False
    else:
        p[0] = f"pushg {p.parser.variaveis[p[1]]}\npushi 1\nadd\nstoreg {p.parser.variaveis[p[1]]}\n"

def p_Instrucao_MenosMenos(p):
    "Instrucao : var dec"
    if p[1] not in p.parser.variaveis:
        p[0] = f"err \"ERROR. Variavel nao declarada:\"\n"
        print(f"ERROR. Variável não declarada:'{p[1]}'")
        parser.success = False
    else:
        p[0] = f"pushg {p.parser.variaveis[p[1]]}\npushi 1\nsub\nstoreg {p.parser.variaveis[p[1]]}\n"

# ----------------- LOOP ---------------------------
def p_Loop(p):
    "Loop : repeat '{' Instrucoes '}' until Verifica"
    p[0] = f"repeat{p.parser.loopCount}:\n{p[3]}{p[6]}not\njz repeat{p.parser.loopCount}\n"
    p.parser.loopCount +=1


#----------------IF ELSE------------------------

def p_Condition(p):
    "Condition : if Verifica '{' Instrucoes '}' "
    p[0] = f"{p[2]}jz if{p.parser.labelCount}\n{p[4]}if{p.parser.labelCount}:\n"
    p.parser.labelCount+=1
    

def p_Condition_else(p):
    "Condition : if Verifica '{' Instrucoes '}' else '{' Instrucoes	'}' "
    p[0] = f"{p[2]}jz if{p.parser.labelCount}\n{p[4]}if{p.parser.labelCount}:{p[8]}\n"

    

#-------------------- VERIFICA ----------------------
def p_Verifica_cond(p):
    "Verifica : '(' Cond ')' "
    p[0] = p[2]

def p_Verifica_naocond(p):
    "Verifica : '!' '(' Cond ')' "
    p[0] = f"{p[3]}not\n"

def p_Verifica_And(p):
    "Verifica : Verifica and Verifica"
    p[0] = f"{p[1]}{p[3]}mul\n"

def p_Verifica_Or(p):
    "Verifica : Verifica or Verifica"
    p[0] = f"{p[1]}{p[3]}add\n{p[1]}{p[3]}mul\nsub\n"


# ----------------- COND ----------------------------
def p_Cond_Equals(p):
    "Cond : Expressao equals Expressao"
    p[0] = f"{p[1]}{p[3]}equal\n"

def p_Cond_LessEq(p):
    "Cond : Expressao lessEq Expressao"
    p[0] = f"{p[1]}{p[3]}infeq\n"

def p_Cond_MoreEq(p):
    "Cond : Expressao moreEq Expressao"
    p[0] = f"{p[1]}{p[3]}supeq\n"

def p_Cond_Menor(p):
    "Cond : Expressao '<' Expressao"
    p[0] = f"{p[1]}{p[3]}inf\n"

def p_Cond_Maior(p):
    "Cond : Expressao '>' Expressao"
    p[0] = f"{p[1]}{p[3]}sup\n"

def p_Cond_Verifica(p):
    "Cond : Verifica"
    p[0] = p[1]


#------------------- PRINT -----------------------------
def p_Print_var(p):
    "Print : print var"
    if p[2] not in p.parser.variaveis:
        p[0] = f"err \"ERROR. Variavel nao declarada:\"\n"
        print(f"ERROR. Variável não declarada:'{p[2]}'")
        parser.success = False
    else:
        p[0] = (f"pushg {p.parser.variaveis[p[2]]}\nwritei\n")

def p_Print_string(p):
    "Print : print string"
    p[0] = f"pushs {p[2]}\nwrites\n"



#------------------------ EXPRESSAO -------------------------

def p_Expressao_mais(p):
    "Expressao : Expressao '+' Termo"
    p[0] = p[1] + p[3] + "add\n"

def p_Expressao_menos(p):
    "Expressao : Expressao '-' Termo"
    p[0] = p[1] + p[3] + "sub\n"

def p_Expressao_Termo(p):
    "Expressao : Termo"
    p[0] = p[1]

def p_Termo_multi(p):
    "Termo : Termo '*' Fator"
    p[0] = p[1] + p[3] + "mul\n"
  
def p_Termo_div(p):
    "Termo : Termo '/' Fator"
    p[0] = p[1] + p[3] + "div\n"

def p_Termo_fator(p):
    "Termo : Fator"
    p[0] = p[1]


def p_Fator_varArr(p):
    "Fator : var '[' num ']'"
    if p[1] not in p.parser.variaveis:
        p[0] = f"err \"ERROR. Variavel nao declarada:\"\n"
        print(f"ERROR. Variavel nao declarada:'{p[1]}'")
        parser.success = False
    else:
        if(int(p[3])<p.parser.variaveis[p[1]][1]):
            p[0] = f"pushg {(p.parser.variaveis[p[1]][2]+int(p[3]))}\n"
        else:
            p[0] = f"err \"ERROR. Out of range\"\n"
            print(f"ERROR. Out of range'{p[1]}'")
            parser.success = False


def p_Fator_varDoubleArr(p):
    "Fator : var '[' num ']' '[' num ']'"
    i = int(p[3])
    j = int(p[6])
    if p[1] not in p.parser.variaveis:
        p[0] = f"err \"ERROR. Variavel nao declarada:\"\n"
        print(f"ERROR. Variavel nao declarada:'{p[1]}'")
        parser.success = False
    else:
        col = p.parser.variaveis[p[1]][3]
        lin = p.parser.variaveis[p[1]][2]
        if(i<lin)and(j<col):
             p[0] = f"pushg {(p.parser.variaveis[p[1]][4]+(i*col)+j)}\n"
        else :
            p[0] = f"err \"ERROR. Out of range.\"\n"
            print(f"ERROR.  Out of range.'{p[1]}'")
            parser.success = False

def p_Fator_var(p):
    "Fator : var"
    if p[1] not in p.parser.variaveis:
        p[0] = f"err \"ERROR. Variavel nao declarada:\"\n"
        print(f"ERROR. Variavel nao declarada:'{p[1]}'")
        parser.success = False
    else:
        p[0] = f"pushg {p.parser.variaveis[p[1]]}\n"


def p_Fator_num(p):
    "Fator : num"
    p[0] = f"pushi {p[1]}\n"


def p_Fator_expressao(p):
    "Fator : '(' Expressao ')' "
    p[0] = p[2]


def p_error(p):
    print('Erro sintático: ', p)
    parser.success = False


# Build the parser
parser = yacc.yacc()
parser.variaveis = {}
parser.sp = 0
parser.labelCount = 0
parser.loopCount = 0
parser.elemsCount = 0
parser.elems2Count = 0
parser.bool = 1


# Read line from input and parser it
import sys


print("Insert name of the input file: \n")
fInput = input()
try:
    fread = open(fInput, "r")
    print("Insert name of the output file: \n")
    fOutput = input()
    fwrite= open(fOutput,"w+")
    content = fread.read()
    res = parser.parse(content)
    print(parser.variaveis)
    fwrite.close()
except:
    print('Error. Input file does not exist.')

  
