class PawnFigure(x: Char, y: Int, sideColor: SideColor) : IChessFigure {
    private val name: String = "Pawn Figure"
    private var firstMove: Boolean = false
    override var x: Char = x
        get
    override var y: Int = y
        get
    override var sideColor: SideColor = sideColor
        get

    constructor(x: Char, y: Int) : this(x = x, y = y, SideColor.White) {}

    constructor(sideColor: SideColor) : this(x = 'A', y = 1, sideColor) {}

    override fun MoveTo(to_x: Char, to_y: Int): Boolean {
        if (CanMoveTo(to_x, to_y)) {
            x = to_x
            y = to_y
            firstMove = true
            return true
        }
        return false
    }

    override fun CanMoveTo(to_x: Char, to_y: Int): Boolean {

        if (to_x in 'A'..'H' && to_y in 1..8) {

        }
        else return false;

        var hasEnemy = false
        if (chessCell.figure != null && chessCell.figure?.sideColor == this.sideColor)
            return false
        else if (chessCell.figure != null && chessCell.figure?.sideColor != this.sideColor)
            hasEnemy = true

        if (sideColor == SideColor.White) {
            if (chessCell.x == x) {
                if (y + 2 == chessCell.y && !firstMove && !hasEnemy)
                    return true
                else if (y + 1 == chessCell.y  && !hasEnemy)
                    return true
            }
            else if (hasEnemy && y + 1 == chessCell.y && (x - 1 == chessCell.x || x + 1 == chessCell.x))
                return true
        }
        else if (sideColor == SideColor.Black) {
            if (chessCell.x == x) {
                if (y - 2 == chessCell.y && !firstMove && !hasEnemy)
                    return true
                else if (y - 1 == chessCell.y && !hasEnemy)
                    return true
            }
            else if (hasEnemy && y - 1 == chessCell.y && (x - 1 == chessCell.x || x + 1 == chessCell.x))
                return true
        }
        return false
    }

    override fun GetFigureImage(): Char {
        if (sideColor == SideColor.White)
            return 'P'
        else
            return 'p'
    }

    override fun toString(): String {
        return "$name - ${sideColor.name} - $x$y"
    }
}