:- load_library('alice.tuprolog.lib.DCGLibrary').

lookup(K, [(K, V) | _], V).
lookup(K, [_ | T], V) :- lookup(K, T, V).

convert_to_bool(A, 1) :- A > 0, !.
convert_to_bool(A, 0).

impl(1, 0, 0) :- !.
impl(A, B, 1).

iff(X, X, 1) :- !.
iff(A, B, 0).

and(1, 1, 1) :- !.
and(A, B, 0).

or(0, 0, 0) :- !.
or(A, B, 1).

xor(X, X, 0) :- !.
xor(A, B, 1).

my_not(1, 0).
my_not(0, 1).

operation(op_negate, A, R) :- R is -A.
operation(op_add, A, B, R) :- R is A + B.
operation(op_subtract, A, B, R) :- R is A - B.
operation(op_multiply, A, B, R) :- R is A * B.
operation(op_divide, A, B, R) :- R is A / B.
operation(op_not, A, R) :- convert_to_bool(A, A1), my_not(A1, R).
operation(op_and, A, B, R) :- convert_to_bool(A, A1), convert_to_bool(B, B1), and(A1, B1, R).
operation(op_or, A, B, R) :- convert_to_bool(A, A1), convert_to_bool(B, B1), or(A1, B1, R).
operation(op_xor, A, B, R) :- convert_to_bool(A, A1), convert_to_bool(B, B1), xor(A1, B1, R).
operation(op_impl, A, B, R) :- convert_to_bool(A, A1), convert_to_bool(B, B1), impl(A1, B1, R).
operation(op_iff, A, B, R) :- convert_to_bool(A, A1), convert_to_bool(B, B1), iff(A1, B1, R).

evaluate(const(Value), _, Value).
evaluate(variable(Name), Vars, R) :- atom_chars(Name, [H | _]), lookup(H, Vars, R).
evaluate(operation(Op, A), Vars, R) :- evaluate(A, Vars, AV), operation(Op, AV, R).
evaluate(operation(Op, A, B), Vars, R) :- evaluate(A, Vars, AV), evaluate(B, Vars, BV), operation(Op, AV, BV, R).

nonvar(V, _) :- var(V).
nonvar(V, T) :- nonvar(V), call(T).

expr_p(variable(Name)) --> { nonvar(Name, atom_chars(Name, Chars)) }, var_symbol_p(Chars), { Chars = [_ | _], atom_chars(Name, Chars) }.
expr_p(const(Value)) --> { nonvar(Value, number_chars(Value, Chars)) }, digits_p(Chars), { Chars = [_, _ | _], number_chars(Value, Chars) }.
expr_p(operation(Op, A)) --> op_p(Op), [' '], ws_expr(A).
expr_p(operation(Op, A, B)) --> ['('], ws_expr(A), [' '], op_p(Op), [' '], ws_expr(B), [')'].

digits_p([]) --> [].
digits_p([H | T]) --> { member(H, ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '.', '-']) }, [H], digits_p(T).

var_symbol_p([]) --> [].
var_symbol_p([H | T]) --> { member(H, ['x', 'X', 'y', 'Y', 'z', 'Z'])}, [H], var_symbol_p(T).

ws --> [].
ws --> [' '], ws.
ws_expr(Expr) --> ws, expr_p(Expr), ws.

op_p(op_negate) --> ['n', 'e', 'g', 'a', 't', 'e'].
op_p(op_add) --> ['+'].
op_p(op_subtract) --> ['-'].
op_p(op_multiply) --> ['*'].
op_p(op_divide) --> ['/'].
op_p(op_not) --> ['!'].
op_p(op_and) --> ['&', '&'].
op_p(op_or) --> ['|', '|'].
op_p(op_xor) --> ['^', '^'].
op_p(op_impl) --> ['-', '>'].
op_p(op_iff) --> ['<', '-', '>'].

infix_str(E, R) :- ground(E), phrase(ws_expr(E), C), atom_chars(R, C), !.
infix_str(E, R) :- atom(R), atom_chars(R, C), phrase(ws_expr(E), C), !.