

def print_menu():
    print("\n")
    print("\033[36m\033[1m---------------------------------------- MENU ----------------------------------------\033[0m")
    print("\033[1m\033[36m(A)\033[0m Nº de processos por ano. Intervalo de datas. Nº de Séculos.")
    print("\033[1m\033[36m(B)\033[0m Frequência dos nomes próprios e apelidos (global) e os Top 5 de cada século.")
    print("\033[1m\033[36m(C)\033[0m Número de candidatos que têm parentes eclesiásticos. Parentesco mais frequente.")
    print("\033[1m\033[36m(D)\033[0m Verificar se o mesmo pai/mãe têm mais do que um filho candidato.")
    print("\033[1m\033[36m(E)\033[0m Desenho de todas as árvores genealogicas dos candidatos de um ano.")
    print("\033[91m(0) Sair\033[0m")



while(True):
    print_menu()
    print("\n\033[1mEscolha uma opção:\033[0m ")
    op = input()
    op=op.upper()
    if (op=='A'):
        exec(open("a).py").read())
    elif (op=='B'):
        exec(open("b).py").read())
    elif (op == 'C'):
        exec(open("c).py").read())
    elif (op == 'D'):
        exec(open("d).py").read())
    elif (op == 'E'):
        exec(open("e).py").read())
    elif (op=='0'):
        break
    else: print("\033[91m\033[1mOpção inválida.\033[0m")
