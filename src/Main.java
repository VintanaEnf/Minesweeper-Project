import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        JOptionPane.showMessageDialog(null, "You won!");
        Board x = new Board(10,9, 70);
        x.setVisible(true);
        x.setEasterEgg(true);
    }
}