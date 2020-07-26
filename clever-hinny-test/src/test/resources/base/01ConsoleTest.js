// 各种不同的JS数据类型的打印输出
var print_1 = function () {
    var fuc = function () {
        return 1 + 6;
    };
    var array = [1, 2.2, true, "nashorn", null, new Date(), undefined, fuc];
    var object = {
        "string": "aaa",
        "number1": 12,
        "number2": 12.345,
        "boolean": true,
        "null": null,
        "date": new Date(),
        "undefined": undefined,
        "function": fuc
    };
    console.log("打印JS变量 | undefined ", undefined, " | 行尾");
    console.log("打印JS变量 | null ", null, " | 行尾");
    console.log("打印JS变量 | int ", 1, " | 行尾");
    console.log("打印JS变量 | float ", 2.2, " | 行尾");
    console.log("打印JS变量 | boolean ", true, " | 行尾");
    console.log("打印JS变量 | string ", "nashorn", " | 行尾");
    console.log("打印JS变量 | date ", new Date(), " | 行尾");
    console.log("打印JS变量 | array ", array, " | 行尾");
    console.log("打印JS变量 | object ", object, " | 行尾");
    console.log("打印JS变量 | function ", fuc, " | 行尾");
    console.log("打印JS变量 | JSON.stringify ", JSON.stringify({date: new Date()}), " | 行尾");
}

exports.print_1 = print_1;