import ply.lex as lex

tokens = ['and','or', 'lessEq', 'moreEq','equals', 'int', 'print', 'read', 'if',
 'else', 'end', 'repeat', 'until', 'num', 
 'var', 'string', 'function','inc','dec']
literals = ['*', '+' ,'/', '-', '=', '(', ')', '.', '<', '>', ',', '!', '{', '}', '[', ']']

t_and = r'and'
t_lessEq = r'lessEq'
t_moreEq = r'moreEq'
t_equals = r'equals'
t_inc = r'\+\+'
t_dec = r'--'
t_int = r'int'
t_print = r'print'
t_read = r'read'
t_else = r'else'
t_end = r'end'
t_repeat = r'repeat'
t_until = r'until'



def t_num(t):
    r'-?\d+'
    #t.value = int(t.value)
    return t

def t_if(t):
    r'if'
    return t

def t_or(t):
    r'or'
    return t

def t_function(t):
    r'[a-z]\w+\(\):'
    return t

t_string = r'"[^"]+"'
t_var = r'\w+'


t_ignore = " \t\n"

def t_error(t):
    print('Illegal character: ', t.value[0])
    t.lexer.skip(1)


lexer = lex.lex()