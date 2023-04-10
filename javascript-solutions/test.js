// "use strict";
//
// let cnst = value => () => value;
// let one = cnst(1);
// let two = cnst(2);
// let textCnst = {
//     "one": one,
//     "two": two
// }
//
// let variables = new Map([["x", 0], ["y", 1], ["z", 2]]);
// let variable = name => (...args) => args[variables.get(name)];
//
// let binaryOperation = f => (left, right) => (x, y, z) => f(left(x, y, z), right(x, y, z));
// let add = binaryOperation((a, b) => a + b);
// let subtract = binaryOperation((a, b) => a - b);
// let multiply = binaryOperation((a, b) => a * b);
// let divide = binaryOperation((a, b) => a / b);
//
// let negate = f => (x, y, z) => -f(x, y, z);
//
// let evaluate = (...args) => (x, y, z) => {
//     let intArr = [];
//     for (let i = 0; i < args.length; i++) {
//         intArr.push(args[i](x, y, z));
//     }
//     return intArr;
// }
// let indexFunc = f => (...args) => (x, y, z) => {
//     let intArr = evaluate(...args)(x, y, z);
//     return intArr.indexOf(f(...intArr));
// }
// let argMin = indexFunc((...args) => Math.min(...args));
// let argMax = indexFunc((...args) => Math.max(...args));
//
// let operations = {
//     "negate": [negate, 1],
//     "+": [add, 2],
//     "-": [subtract, 2],
//     "*": [multiply, 2],
//     "/": [divide, 2],
//     "argMin3": [argMin, 3],
//     "argMin5": [argMin, 5],
//     "argMax3": [argMax, 3],
//     "argMax5": [argMax, 5]
// }
//
// let parse = str => {
//     let elements = str.trim().split(/\s+/);
//     let stack = [];
//     for (const element of elements) {
//         if (element in textCnst) {
//             stack.push(textCnst[element]);
//         } else if (variables.has(element)) {
//             stack.push(variable(element));
//         } else if (element in operations) {
//             let operation = operations[element];
//             let args = stack.splice(-operation[1]);
//             stack.push(operation[0](...args));
//         } else {
//             stack.push(cnst(+element));
//         }
//     }
//     return stack.pop();
// }
//
// // console.log(parse("3 4 1 argMin3")(5, 0, 0));