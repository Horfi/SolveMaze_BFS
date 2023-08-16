package p;

import java.util.Objects;

public class Node {
    int row;
    int col;

    public Node(int row, int col) {
        this.row = row;
        this.col = col;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Node other = (Node) obj;
        return row == other.row && col == other.col;
    }
    @Override
    public int hashCode() {
        return Objects.hash(row, col);
    }

    public String toString() {
        return "(" + row + ", " + col + ")";
    }
}
