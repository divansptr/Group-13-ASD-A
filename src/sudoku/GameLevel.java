package sudoku;

public enum GameLevel {
    EASY(2),       // 2 empty cells
    INTERMEDIATE(10), // 10 empty cells
    DIFFICULT(20); // 20 empty cells

    private final int emptyCells;

    GameLevel(int emptyCells) {
        this.emptyCells = emptyCells;
    }

    public int getEmptyCells() {
        return emptyCells;
    }
}
