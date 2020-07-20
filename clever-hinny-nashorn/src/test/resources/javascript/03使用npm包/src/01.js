// var _trim = require('lodash/trim');
// var lodash = require('lodash');
// var moment = require('moment');
var dayjs = require('dayjs');

var trim = function (str) {
    // print("require -> ", require.toString())
    // print("_trim -> ", _trim('  a123  '))
    // print("moment  -> ", moment("2020-07-19").toDate())
    print("dayjs   -> ", dayjs('2018-05-05').locale('zh-cn').format())
    return str.trim(str);
    // return lodash.trim(str);
}

// exports._trim = _trim;
exports.trim = trim;