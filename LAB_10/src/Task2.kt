package LAB_10.Task2

fun main(args: Array<String>) {
    println("args: $args")

    val valInt: Int
    valInt = 1243
    val valDouble: Double = 3.1415
    val valString = "Some text"
    var varInt = 9999
    var varDouble: Double
    var varString:String = "Some text 2"
    varDouble = 228.6
    //valDouble = 22.0
    println("$valDouble + $varDouble = ${valDouble + varDouble}")

    varInt = varDouble.toInt()

    println("$valInt")

    var constVal: Int? = null
    constVal = 1312
    println(constVal)

    var readed = readLine()?.toBigDecimal()
    println(readed)
}
