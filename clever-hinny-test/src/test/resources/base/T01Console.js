// console.log(Java.type('org.clever.hinny.api.internal.LoggerFactory'))
// console.log(Java.type('java.lang.System').exit(1))
// console.log(Java.type('java.lang.Thread'))

var log_1 = function () {
    console.log("#console.log Test");
    console.trace("#console.log Test");
    console.debug("#console.log Test");
    console.info("#console.log Test");
    console.warn("#console.log Test");
    console.error("#console.log Test");
}

var count_1 = function () {
    console.count();
    console.count();
    console.countReset();
    console.count();
    console.count();

    var label = "count_1";
    console.count(label);
    console.count(label);
    console.countReset(label);
    console.count(label);
    console.count(label);
}

var time_1 = function () {
    console.time();
    console.timeLog(null, "#time_1 Test");
    console.timeLog(null, "#time_1 Test");
    console.timeEnd();

    var label = "count_1";
    console.time(label);
    console.timeLog(label, "#time_1 Test");
    console.timeLog(label, "#time_1 Test");
    console.timeEnd(label);
}

// 各种不同的JS数据类型的打印输出
var log_2 = function () {
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

var log_3 = function () {
    console.log("Java ---> getByte ", TestInternal.getByte(), " | 行尾");
    console.log("Java ---> getShort ", TestInternal.getShort(), " | 行尾");
    console.log("Java ---> getInt ", TestInternal.getInt(), " | 行尾");
    console.log("Java ---> getLong ", TestInternal.getLong(), " | 行尾");
    console.log("Java ---> getFloat ", TestInternal.getFloat(), " | 行尾");
    console.log("Java ---> getDouble ", TestInternal.getDouble(), " | 行尾");
    console.log("Java ---> getBoolean ", TestInternal.getBoolean(), " | 行尾");
    console.log("Java ---> getChar ", TestInternal.getChar());
    console.log("Java ---> getString ", TestInternal.getString(), " | length=", TestInternal.getString().length, " | 行尾");
    console.log("Java ---> getDate ", TestInternal.getDate());
    console.log("Java ---> getArray ", TestInternal.getArray(), " | 下标访问: ", TestInternal.getArray()[0], " | 行尾");
    console.log("Java ---> getList ", TestInternal.getList(), " | 下标访问: ", TestInternal.getList()[1], " | 行尾");
    console.log("Java ---> getSet ", TestInternal.getSet(), " | 下标访问: ", TestInternal.getSet()[2], " | 行尾");
    console.log("Java ---> getMap ", TestInternal.getMap(), " | key访问: ", TestInternal.getMap().long, " | 行尾");
}

exports.log_1 = log_1;
exports.count_1 = count_1;
exports.time_1 = time_1;
exports.log_2 = log_2;
exports.log_3 = log_3;
