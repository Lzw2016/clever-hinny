package org.clever.hinny.graaljs;

/**
 * 作者：lizw <br/>
 * 创建时间：2020/03/28 12:20 <br/>
 */
public class JsSource {

    public static final int WARM_UP = 15;
    public static final int ITERATIONS = 30;

    public static final String SOURCE_1 = ""
            + "var N = 2000;\n"
            + "var EXPECTED = 17393;\n"
            + "\n"
            + "function Natural() {\n"
            + "    x = 2;\n"
            + "    return {\n"
            + "        'next' : function() { return x++; }\n"
            + "    };\n"
            + "}\n"
            + "\n"
            + "function Filter(number, filter) {\n"
            + "    var self = this;\n"
            + "    this.number = number;\n"
            + "    this.filter = filter;\n"
            + "    this.accept = function(n) {\n"
            + "      var filter = self;\n"
            + "      for (;;) {\n"
            + "          if (n % filter.number === 0) {\n"
            + "              return false;\n"
            + "          }\n"
            + "          filter = filter.filter;\n"
            + "          if (filter === null) {\n"
            + "              break;\n"
            + "          }\n"
            + "      }\n"
            + "      return true;\n"
            + "    };\n"
            + "    return this;\n"
            + "}\n"
            + "\n"
            + "function Primes(natural) {\n"
            + "    var self = this;\n"
            + "    this.natural = natural;\n"
            + "    this.filter = null;\n"
            + "\n"
            + "    this.next = function() {\n"
            + "        for (;;) {\n"
            + "            var n = self.natural.next();\n"
            + "            if (self.filter === null || self.filter.accept(n)) {\n"
            + "                self.filter = new Filter(n, self.filter);\n"
            + "                return n;\n"
            + "            }\n"
            + "        }\n"
            + "    };\n"
            + "}\n"
            + "\n"
            + "function primesMain() {\n"
            + "    var primes = new Primes(Natural());\n"
            + "    var primArray = [];\n"
            + "    for (var i=0;i<=N;i++) { primArray.push(primes.next()); }\n"
            + "    if (primArray[N] != EXPECTED) { throw new Error('wrong prime found: '+primArray[N]); }\n"
            + "}\n";

    public static final String SOURCE_2 = "" +
            "var test = function (a, b) {\n" +
            "    var sum = 0;\n" +
            "    for (var i = 0; i < a; i++) {\n" +
            "        sum += i;\n" +
            "        for (var j = 0; j < b; j++) {\n" +
            "            sum += j;\n" +
            "        }\n" +
            "    }\n" +
            "    return sum;\n" +
            "};";

    public static final String SOURCE_3 = "" +
            "var test = function () {\n" +
            "    function factorial(n) {\n" +
            "        return n === 1 ? n : n * factorial(--n);\n" +
            "    }\n" +
            "    var i = 0;\n" +
            "    while (i++ < 1e6) {\n" +
            "        factorial(10);\n" +
            "    }\n" +
            "    return i;\n" +
            "};";
}
