package LAB_10.Task5

fun main(args: Array<String>) {
    //aTask()
    bTask()
    //cTask()
}

fun aTask() {
    fun biggerThan5(collection: Collection<Int>): Boolean = collection.size > 5
    fun oddContainsIn(collection: Collection<Int>): Boolean = collection.any { t -> t % 2 == 1 }
    val arr = arrayListOf(2, 4, 1, 8, 10, 12)
    println(">5: ${biggerThan5(arr)}")
    println("has odd: ${oddContainsIn(arr)}")
}

fun bTask() {
    val list = arrayListOf(2, 4, 1, 8, 10, 2, 12, 10)
    printArray(list.toIntArray())
    println("-------------------")
    list.add(1)
    printArray(list.toIntArray())
    println("-------------------")
    list += 23
    printArray(list.toIntArray())
    println("-------------------")
    val newList = list.distinct()
    printArray(newList.toIntArray())
    println("-------------------")
    newList.toMutableList().removeIf{it % 2 == 0}
    newList.forEach{t -> print("$t ")}
    println()
    println("-------------------")
    val numbers = listOf(1, 2, 3, 4, 5)
    println(numbers.filter(::isPrime))
    println("-------------------")
}

fun cTask() {

}

fun printArray(a: IntArray) {
    for (i in a.indices)
        print("${a[i]} ")
    println()
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