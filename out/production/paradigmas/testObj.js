"use strict"

function AbstractOperation(type, f, diffFunc, ...args) {
    this.type = type + (type === "sumrec" || type === "hmean" ? args.length : "");
    this.f = f;
    this.diffFunc = diffFunc;
    this.args = args;
}

AbstractOperation.prototype.evaluate = function (...vars) {
    return this.f(...this.args.map(arg => arg.evaluate(...vars)));
}
AbstractOperation.prototype.toString = function () {
    return this.args.map(item => item.toString()).join(" ") + " " + this.type;
}
AbstractOperation.prototype.diff = function (varName) {
    return this.diffFunc(varName, ...this.args);
}
AbstractOperation.prototype.prefix = function () {
    return "(" + this.type + " " + this.args.map(item => item.prefix()).join(" ") + ")";
}
AbstractOperation.prototype.postfix = function () {
    return "(" + this.args.map(item => item.postfix()).join(" ") + " " + this.type + ")";
}

function createOperation(type, f, diffFunc) {
    function Operation(...args) {
        AbstractOperation.call(this, type, f, diffFunc, ...args);
    }

    Operation.prototype = Object.create(AbstractOperation.prototype);
    return Operation;
}

let Add = createOperation(
    "+", (a, b) => a + b, (varName, ...args) => new Add(args[0].diff(varName), args[1].diff(varName))
);

let Subtract = createOperation(
    "-", (a, b) => a - b, (varName, ...args) => new Subtract(args[0].diff(varName), args[1].diff(varName))
);

let Multiply = createOperation(
    "*", (a, b) => a * b,
    (varName, ...args) => new Add(new Multiply(args[0].diff(varName), args[1]), new Multiply(args[0], args[1].diff(varName)))
);

let Divide = createOperation(
    "/", (a, b) => a / b,
    (varName, ...args) => new Divide(
        new Subtract(new Multiply(args[0].diff(varName), args[1]), new Multiply(args[0], args[1].diff(varName))),
        new Multiply(args[1], args[1])
    )
);

let Negate = createOperation(
    "negate", (a) => -a,
    (varName, ...args) => new Negate(args[0].diff(varName))
);

let SumrecN = createOperation(
    "sumrec",
    (...args) => args.reduce((sum, cur) => sum + 1 / cur, 0),
    (varName, ...args) => args.reduce(
        (prev, cur) => new Add(prev, new Divide(new Const(1), cur)), new Const(0)
    ).diff(varName)
);
let Sumrec2 = SumrecN;
let Sumrec3 = SumrecN;
let Sumrec4 = SumrecN;
let Sumrec5 = SumrecN;

let HMeanN = createOperation(
    "hmean",
    (...args) => args.length / args.reduce((sum, cur) => sum + 1 / cur, 0),
    (varName, ...args) => {
        let sum = new SumrecN(...args);
        return new Divide(new Multiply(new Const(-args.length), sum.diff(varName)), new Multiply(sum, sum));
    }
);
let HMean2 = HMeanN;
let HMean3 = HMeanN;
let HMean4 = HMeanN;
let HMean5 = HMeanN;

let Meansq = createOperation(
    "meansq",
    (...args) => args.reduce((sum, cur) => sum + cur * cur, 0) / args.length,
    (varName, ...args) => args.reduce(
        (prev, cur) => new Add(prev, new Multiply(new Const(2), cur)), new Const(0)
    ).diff(varName)
);

let RMS = createOperation(
    "rms",
    (...args) => Math.sqrt(args.reduce((sum, cur) => sum + cur * cur, 0) / args.length),
    (varName, ...args) => new Divide(
        new Meansq(args).diff(varName),
        new Multiply(new Const(2), new RMS(args))
    )
);

let AbstractElement = {
    prefix() {
        return this.toString();
    },
    postfix() {
        return this.toString();
    },
    toString() {
        return this.arg.toString();
    }
}

function createElement(constr, evaluate, diffFunc) {
    let Element = constr;
    Element.prototype = Object.create(AbstractElement);
    Element.prototype.diff = diffFunc;
    Element.prototype.evaluate = evaluate;
    return Element;
}

let Const = createElement(
    function (arg) {
        this.arg = arg;
    },
    function () {
        return this.arg;
    },
    function () {
        return new Const(0);
    }
);

let Variable = createElement(
    function (arg) {
        this.arg = arg;
    },
    function (...vars) {
        return vars[Variable.varNames.get(this.arg)]
    },
    function (varName) {
        return varName === this.arg ? new Const(1) : new Const(0)
    }
);
Variable.varNames = new Map([["x", 0], ["y", 1], ["z", 2]]);

function createParseException(type, printFunc) {
    function ParseException(result, expect, position, text) {
        this.message = type + ": " + printFunc(result);
        if (position !== -1) {
            this.message += "Expect \"" + expect + "\" in position " + position + " in text:\n" + text + "\n";
        }
    }

    ParseException.prototype = Object.create(Error.prototype);
    return ParseException;
}

let IllegalArgument = createParseException(
    "IllegalArgumentException",
    (arg) => "Illegal argument. Found: \"" + arg + "\"."
);
let IllegalArgumentCount = createParseException(
    "IllegalArgumentCountException",
    (arg) => "Missed some arguments. Found: \"" + arg + "\"."
);
let BracketNotFound = createParseException(
    "BracketNotFoundException",
    (arg) => "Miss a bracket. Found: \"" + arg + "\"."
);
let OperationMismatch = createParseException(
    "OperationMismatchException",
    (arg) => "Operation missed. Found: \"" + arg + "\"."
);

let operations = {
    "negate": [Negate, 1],
    "+": [Add, 2],
    "-": [Subtract, 2],
    "*": [Multiply, 2],
    "/": [Divide, 2],
    "sumrec2": [Sumrec2, 2],
    "sumrec3": [Sumrec3, 3],
    "sumrec4": [Sumrec4, 4],
    "sumrec5": [Sumrec5, 5],
    "hmean2": [HMean2, 2],
    "hmean3": [HMean3, 3],
    "hmean4": [HMean4, 4],
    "hmean5": [HMean5, 5],
    "meansq": [Meansq, -1],
    "rms": [RMS, -1]
}

const strAddSpace = function (str) {
    let resStr = ""
    for (let i = 0; i < str.length; i++) {
        if (str[i] === '(') {
            resStr += ' ' + str[i]
        } else if (str[i] === ')') {
            resStr += str[i] + ' '
        } else {
            resStr += str[i]
        }
    }
    return resStr
}

let parseVariant = (str, isPrefix) => {
    console.dir('|' + isPrefix + '| ' + str);
    let elements = str.split(/\s+|([()])/).filter(a => a);
    if (isPrefix) {
        elements = elements.reverse();
    }
    let openBracket = (isPrefix ? ')' : '(');
    let closeBracket = (isPrefix ? '(' : ')');
    let balance = 0;
    let countArgs = 0;
    let ans = elements.reduce(
        function (stack, element, position) {
            if (balance < 0) {
                throw new BracketNotFound(element, openBracket, position, str);
            }

            if (element === openBracket) {
                balance++;
            } else if (element === closeBracket) {
                balance--;
            } else if (position + 1 < elements.length && elements[position + 1] === closeBracket && !(element in operations)) {
                throw new OperationMismatch(element, "operation", position, str);
            } else {
                if (Variable.varNames.has(element)) {
                    stack.push(new Variable(element));
                } else if (element in operations) {
                    let operation = operations[element];

                    if (operation[1] > stack.length || stack.length === 0) {
                        throw new IllegalArgumentCount(stack.length, operation[1], position, str);
                    }

                    let args = stack.splice(operation[1] === -1 ? -stack.length: -operation[1]);
                    if (isPrefix) {
                        args = args.reverse();
                    }
                    stack.push(new operation[0](...args));
                } else {
                    if (isNaN(+element)) {
                        throw new IllegalArgument(element, "any number", position, str);
                    }

                    stack.push(new Const(+element));
                }
            }
            return stack;
        }, []
    );

    if (balance !== 0) {
        throw new BracketNotFound('', closeBracket, str.length, str);
    }
    if (ans.length !== 1) {
        throw new IllegalArgumentCount(ans.length, 1, -1, str);
    }

    return ans.pop();
}

let parsePrefix = str => parseVariant(str, true);
let parsePostfix = str => parseVariant(str, false);
let parse = str => parseVariant(str, false);

let func = str => strAddSpace(str).split(' ').filter(expr => expr).reverse();
console.log(func("(rms y(meansq y z 893)x z)"));