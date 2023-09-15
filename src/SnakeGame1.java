import javax.swing.*;
import java.awt.*;

public class SnakeGame1{
    public static void main(String[] args) {
        JFrame frame = new JFrame("Snake Game");
        frame.setBounds(10,10,905,700);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //changed
        Board board = new Board();
        board.setBackground(Color.DARK_GRAY);
        frame.add(board);
        frame.setVisible(true);
    }
}