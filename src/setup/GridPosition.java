package setup;

public class GridPosition {
	int row, col;
	char value;
	
	public GridPosition(int row, int col) {
		this(row, col, ' ');
	}
	
	private GridPosition(int row, int col, char value) {
		this.row = row;
		this.col = col;
		this.value = value;
	}
	
	public boolean isEmpty() {
		return value == ' ';
	}
	
	public GridPosition copy() {
		return new GridPosition(row, col, value);
	}
}
