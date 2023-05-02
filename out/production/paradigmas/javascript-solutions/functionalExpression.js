"use strict";

let cnst = value => () => value;
let one = cnst(1);
let two = cnst(2);
let textCnst = {
    "one": one,
    "two": two
}

let variables = new Map([["x", 0], ["y", 1], ["z", 2]]);
let variable = name => (...args) => args[variables.get(name)];

let binaryOperation = f => (...args) => (x, y, z) => f(...args.map(arg => arg(x, y, z)));
let add = binaryOperation((a, b) => a + b);
let subtract = binaryOperation((a, b) => a - b);
let multiply = binaryOperation((a, b) => a * b);
let divide = binaryOperation((a, b) => a / b);

let negate = f => (x, y, z) => -f(x, y, z);

let indexFunc = f => (...args) => (x, y, z) => {
    let intArr = args.map(arg => arg(x, y, z));
    return intArr.indexOf(f(...intArr));
}
let argMin3 = indexFunc((a, b, c) => Math.min(a, b, c));
let argMin5 = indexFunc((a, b, c, d, e) => Math.min(a, b, c, d, e));
let argMax3 = indexFunc((a, b, c) => Math.max(a, b, c));
let argMax5 = indexFunc((a, b, c, d, e) => Math.max(a, b, c, d, e));

let operations = {
    "negate": [negate, 1],
    "+": [add, 2],
    "-": [subtract, 2],
    "*": [multiply, 2],
    "/": [divide, 2],
    "argMin3": [argMin3, 3],
    "argMin5": [argMin5, 5],
    "argMax3": [argMax3, 3],
    "argMax5": [argMax5, 5]
}

let parse = str => {
    let elements = str.trim().split(/\s+/);
    let ans = elements.reduce(
        function (stack, element) {
            if (element in textCnst) {
                stack.push(textCnst[element]);
            } else if (variables.has(element)) {
                stack.push(variable(element));
            } else if (element in operations) {
                let operation = operations[element];
                let args = stack.splice(-operation[1]);
                stack.push(operation[0](...args));
            } else {
                stack.push(cnst(+element));
            }
            return stack;
        }, []
    );
    return ans.pop();
}

// console.log(parse("x 2 +")(0,0,0));