package LAB_10.Task4

fun main(args: Array<String>) {
    //aTask()
    bTask()
}

fun aTask() {
    val enter = readLine().toString().toInt()
    println("$enter! = ${factorial(enter)}")
    println("$enter! = ${factorialRecursive(enter)}")
}

fun bTask() {
    var counter = 0
    val target = 10000

    val simples = primeList(target)
    var listCounter = 0
    for (i in 0 until target - 1) {
        val prime = simples[i]
        //val text = if (prime) "простое" else "составное"
        //println("$i - $text")
        if (prime) listCounter++
    }
    println("Простых чисел меньше $target: $listCounter (разовый расчёт)")

    val simpleList = ArrayList<Int>(20)
    val simpleArray = IntArray(10)
    for (i in 2 until target) {
        val prime = isPrime(i)
        //val text = if (prime) "простое" else "составное"
        //println("$i - $text")
        if (prime) counter++

        if (prime && counter <= 20)
            simpleList.add(i)
        else if (prime && counter <= 30)
            simpleArray[counter - 21] = i
    }
    println("Простых чисел меньше $target: $counter (многоразовый расчёт)")

    print("Список: ")
    printArray(simpleList.toIntArray())
    println()
    print("Массив: ")
    printArray(simpleArray)
}

fun factorial(n: Int): Double {
    var fact = 1.0;
    for (v in 1..n) {
        fact *= v
    }
    return fact
}

fun factorialRecursive(n: Int): Double {
    return if (n == 1)
        n.toDouble();
    else
        factorial(n - 1) * n
}

fun isPrime(value: Int): Boolean {
    val range = IntArray(value - 1)
    for (i in range.indices) {
        range[i] = i + 2
    }
    val simple = BooleanArray(value - 1)
    simple.fill(true)
    for (i in range.indices) {
        if (i >= value / 2) break
        if (!simple[i]) continue
        for (j in (i + 1)..range.lastIndex) {
            if (!simple[j]) continue
            if (range[j] % range[i] == 0)
                simple[j] = false
        }
    }
    return if (simple.isEmpty()) true
    else
        simple[simple.lastIndex]
}

fun primeList(value: Int): BooleanArray {
    val range = IntArray(value - 1)
    for (i in range.indices) {
        range[i] = i + 2
    }
    val simple = BooleanArray(value - 1)
    simple.fill(true)
    for (i in range.indices) {
        if (i >= value / 2) break
        if (!simple[i]) continue
        for (j in (i + 1)..range.lastIndex) {
            if (!simple[j]) continue
            if (range[j] % range[i] == 0)
                simple[j] = false
        }
    }
    return simple
}

fun printArray(a: IntArray) {
    for (i in a.indices)
        print("${a[i]} ")
}