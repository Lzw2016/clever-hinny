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

exports.logger_1 = logger_1;
exports.logger_2 = logger_2;
