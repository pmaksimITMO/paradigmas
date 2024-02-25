%Precalculating primes using Eratosthene`s sieve
mark_non_prime(S, N, STEP) :-
	S < N, !,
	assert(composite(S)),
	S1 is S + STEP,
	mark_non_prime(S1, N, STEP).
mark_non_prime(S, N, STEP).

eratosthenes_sieve_step(S, N) :-
	prime(S), !,
	S1 is S * S,
	mark_non_prime(S1, N, S).
eratosthenes_sieve_step(S, N).

build_primes(S, N) :- S * S < N, 2 is S, !, eratosthenes_sieve_step(S, N),	S1 is S + 1, build_primes(S1, N).
build_primes(S, N) :- S * S < N, !, eratosthenes_sieve_step(S, N),	S1 is S + 2, build_primes(S1, N).

init(MAXN) :- N is MAXN + 1, build_primes(2, N).

%Get list of prime divisors
factorize(N, D, [N]) :- D * D > N, !.
factorize(N, D, [D1 | T]) :- 0 is mod(N, D), !, D1 is D, N1 is div(N, D), factorize(N1, D, T).
factorize(N, D, R) :- D1 is D + 1, factorize(N, D1, R).

%Get number from list od prime divisors
make_number(1, []) :- !.
make_number(N, [N]) :- !.
make_number(N, [D1, D2 | T]) :- D1 =< D2, !, make_number(N1, [D2 | T]), N is N1 * D1.

prime(2) :- !.
prime(N) :- N > 2, \+ composite(N).

prime_divisors(1, []) :- !.
prime_divisors(N, R) :- number(N), !, factorize(N, 2, R).
prime_divisors(N, Divisors) :- make_number(N, Divisors).

%Modification
calc(N, I, []) :- I * I > N, !.
calc(N, I, [R | T]) :- 0 is mod(N, I), I == div(N, I), !, prime_divisors(I, R), I1 is I + 1, calc(N, I1, T).
calc(N, I, [R1, R2 | T]) :- 0 is mod(N, I), !, prime_divisors(I, R1), D2 is div(N, I), prime_divisors(D2, R2), I1 is I + 1, calc(N, I1, T).
calc(N, I, R) :- I1 is I + 1, calc(N, I1, R).

divisors_divisors(N, Divisors) :- calc(N, 1, Divisors).