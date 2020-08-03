var log = LoggerFactory.getLogger(__filename);

var logger_1 = function () {
    log.trace("#console.log Test");
    log.debug("#console.log Test");
    log.info("#console.log Test");
    log.warn("#console.log Test");
    log.error("#console.log Test");
}

var logger_2 = function () {
    log.info("{} + {} = {}", 3, 5, 8);
}

var logger_3 = function () {
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
    log.info("-----> 开始输出");
    log.info("打印JS变量 | undefined {} | 行尾", undefined);
    log.info("打印JS变量 | null {} | 行尾", null);
    log.info("打印JS变量 | int {} | 行尾", 1);
    log.info("打印JS变量 | float {} | 行尾", 2.2);
    log.info("打印JS变量 | boolean {} | 行尾", true);
    log.info("打印JS变量 | string {} | 行尾", "nashorn");
    log.info("打印JS变量 | date {} | 行尾", new Date());
    log.info("打印JS变量 | array {} | 行尾", array);
    log.info("打印JS变量 | object {} | 行尾", object);
    log.info("打印JS变量 | function {} | 行尾", fuc);
    log.info("打印JS变量 | JSON.stringify {} | 行尾", JSON.stringify({date: new Date()}));
}

exports.logger_1 = logger_1;
exports.logger_2 = logger_2;
exports.logger_3 = logger_3;
