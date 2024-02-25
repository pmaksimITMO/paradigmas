% node = [key value left right height]
%%map_build(ListMap, Result)
map_build([], []).
map_build([(K, V) | T], R) :- map_build(T, R1), map_put(R1, K, V, R).

%%map_put(Tree, K, V, R)
map_put([], K, V, [K, V, [], [], 1]) :- !.
map_put([K, _, L, R, H], K, V, [K, V, L, R, H]) :- !.
map_put([K1, V1, Left, Right, H], K, V, R) :- K < K1, !, map_put(Left, K, V, NewLeft), update([K1, V1, NewLeft, Right, H], R).
map_put([K1, V1, Left, Right, H], K, V, R) :- map_put(Right, K, V, NewRight), update([K1, V1, Left, NewRight, H], R).

%%map_remove(Tree, K, R)
max_in_subtree([K, V, _, [], _], K, V) :- !.
max_in_subtree([_, _, _, R, _], K, V) :- max_in_subtree(R, K, V).

map_remove([], _, []) :- !.
map_remove([K, _, [], Right, _], K, Right) :- !.
map_remove([K, _, Left, Right, _], K, R) :- !, max_in_subtree(Left, K1, V1), map_remove(Left, K1, NewLeft), update([K1, V1, NewLeft, Right, _], R).
map_remove([K1, V1, Left, Right, H], K, R) :- K < K1, !, map_remove(Left, K, NewLeft), update([K1, V1, NewLeft, Right, H], R).
map_remove([K1, V1, Left, Right, H], K, R) :- map_remove(Right, K, NewRight), update([K1, V1, Left, NewRight, H], R).

%%map_get(Tree, K, R)
map_get([K, V, _, _, _], K, V) :- !.
map_get([K1, _, L, _, _], K, Result) :- K < K1, !, map_get(L, K, Result).
map_get([K1, _, _, R, _], K, Result) :- map_get(R, K, Result).

%Модификация
map_getCeiling([K1, V, [], _, _], K, V) :- K1 >= K, !.
map_getCeiling([K1, V, Left, _, _], K, V) :- K1 >= K, max_in_subtree(Left, LK, LV), LK < K, !.
map_getCeiling([K1, _, _, Right, _], K, V) :- K1 < K, !, map_getCeiling(Right, K, V).
map_getCeiling([K1, _, Left, _, _], K, V) :- map_getCeiling(Left, K, V).

map_putCeiling([], K, V, []) :- !.
map_putCeiling([K1, _, [], Right, H], K, V, [K1, V, [], Right, H]) :- K1 >= K, !.
map_putCeiling([K1, _, Left, Right, H], K, V, [K1, V, Left, Right, H]) :- K1 >= K, max_in_subtree(Left, LK, LV), LK < K, !.
map_putCeiling([K1, V1, Left, Right, H], K, V, [K1, V1, Left, NewRight, H]) :- K1 < K, !, map_putCeiling(Right, K, V, NewRight).
map_putCeiling([K1, V1, Left, Right, H], K, V, [K1, V1, NewLeft, Right, H]) :- map_putCeiling(Left, K, V, NewLeft).

%%Балансировка
update(Root, Result) :- update_height(Root, UpdTree), rebalance(UpdTree, Result).

max(A, B, A) :- A > B, !.
max(_, B, B).

get_h([], 0) :- !.
get_h([_, _, _, _, H], H).

update_height([K, V, [], [], _], [K, V, [], [], 1]) :- !.
update_height([K, V, L, R, _], [K, V, L, R, H1]) :- get_h(L, HL), get_h(R, HR), max(HL, HR, HM), H1 is HM + 1.

get_l([], []) :- !.
get_l([_, _, L, _, _], L).
set_l([K, V, _, R, H], L, [K, V, L, R, H]).

get_r([], []) :- !.
get_r([_, _, _, R, _], R).
set_r([K, V, L, _, H], R, [K, V, L, R, H]).

%Поворот правого ребра
rotate_right([], []) :- !.
rotate_right([K, V, L, R, H], Result) :- get_l(R, RL), update_height([K, V, L, RL, H], Tmp), set_l(R, Tmp, Root), update_height(Root, Result).
%Поворот левого ребра
rotate_left([], []) :- !.
rotate_left([K, V, L, R, H], Result) :- get_r(L, LR), update_height([K, V, LR, R, H], Tmp), set_r(L, Tmp, Root), update_height(Root, Result).

get_balance([], 0) :- !.
get_balance([K, V, L, R, H], B) :- get_h(L, HL), get_h(R, HR), B is HL - HR.
rebalance(Root, Root) :- get_balance(Root, B), B > -2, B < 2, !.
rebalance(Root, Result) :- 
	get_balance(Root, B), B = -2, get_r(Root, R), get_balance(R, BR), BR = 1, !, 
	rotate_left(R, NewR), set_r(Root, NewR, NewRoot), rotate_right(NewRoot, Result). 
rebalance(Root, Result) :- get_balance(Root, B), B = -2, !, rotate_right(Root, Result).
rebalance(Root, Result) :- 
	get_balance(Root, B), B = 2, get_l(Root, L), get_balance(L, BL), BL = -1, !,
	rotate_right(L, NewL), set_l(Root, NewL, NewRoot), rotate_left(NewRoot, Result).
rebalance(Root, Result) :- get_balance(Root, B), B = 2, rotate_left(Root, Result).