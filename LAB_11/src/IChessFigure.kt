interface IChessFigure {
    var x: Char
        get
    var y: Int
        get
    var sideColor: SideColor
        get

    fun MoveTo(to_x: Char, to_y: Int): IChessFigure?
    fun CanMoveTo(to_x: Char, to_y: Int): Boolean
    fun GetFigureImage(): Char {
        return '*'
    }
}