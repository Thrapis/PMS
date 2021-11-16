public class ChessBoard {

    private var board = mutableListOf<IChessFigure>()

    constructor() {
        for (i in 'A'..'H') {
            for (j in 1..8) {
                if (j == 1)
                    board.add(PawnFigure(i, j, SideColor.White))
                else if (j == 6)
                    board.add(PawnFigure(i, j, SideColor.Black))
            }
        }
        //board[3][3].PlaceFigure(PawnFigure('D', 4))
    }

    fun CorrectCountOfFigure(figure: IChessFigure): Boolean {
        var counter = 0
        for (i in 0..7) {
            for (j in 0..7) {
                var figureInBoard = GetFigure(i, j)
                if (figureInBoard != null && figureInBoard is PawnFigure) {
                    if (figureInBoard.sideColor == figure.sideColor)
                        counter++
                }
            }
        }
        if (figure is PawnFigure && counter > 8) {
            println("Too much Pawn figures of ${figure.sideColor}! ($counter/8)")
            return false
        }
        return true
    }


    fun HasFigure(x: Char, y: Int): Boolean {
        for (i in board.indices) {
            val figure: IChessFigure = board.get(i);
            if (figure.x == x && figure.y == y)
                return true
        }
        return false
    }

    fun GetFigure(x: Char, y: Int): IChessFigure? {
        for (i in board.indices) {
            val figure: IChessFigure = board.get(i);
            if (figure.x == x && figure.y == y)
                return figure
        }
        return null
    }

    fun MakeTurn(x_from: Char, y_from: Int, x_to: Char, y_to: Int ) {
        val from_cell = GetCell(x_from, y_from)
        if (from_cell.figure == null) return
        else {
            val to_cell = GetCell(x_to, y_to)
            if (from_cell.figure!!.CanMoveTo(to_cell))
                from_cell.TurnTo(to_cell)
        }
    }

    fun PrintBoard() {
        for (j in 8 downTo -2){
            for (i in -2..8) {
                if (j == -2 && i >= 0 && i < 8)
                    print('A' + i)
                else if (i == -2 && j >= 0 && j < 8)
                    print(j + 1)
                else if (i == -1 || i == 8 || j == -1 || j == 8)
                    print('.')
                else if (i < 0 || i >= 8 || j < 0 || j >= 8)
                    print(' ')
                else if (HasFigure('A' + i, j + 1))
                    print(GetFigure('A' + i, j + 1)?.GetFigureImage())
                else print(' ')
            }
            println()
        }
        println()
    }

    /*fun PrintBoardWithSelection(x: Char, y: Int) {
        var x_p = x - 'A'
        var y_p = y - 1
        var selectedFigure = GetFigure(x, y)
        for (j in 8 downTo -2){
            for (i in -2..8) {
                if (j == -2 && i >= 0 && i < 8)
                    print('A' + i)
                else if (i == -2 && j >= 0 && j < 8)
                    print(j + 1)
                else if (i == -1 || i == 8 || j == -1 || j == 8)
                    print('.')
                else if (i < 0 || i >= 8 || j < 0 || j >= 8)
                    print(' ')
                else {
                    if (selectedFigure != null) {
                        if (selectedFigure.CanMoveTo(board[i][j])) {
                            print('✖')
                            continue
                        }
                    }
                    print(board[i][j].GetCellImage())
                }
            }
            println()
        }
        println()
    }*/
}