var print_1 = function () {
    print("#print Test");
    print("# -> ", 1, " | ", 2.2, " | ", true, " | ", undefined, " | ", null, " | ", new Date(), " |");
    print("%d + %d = %s", 3, 5, 8);
}

exports.print_1 = print_1;