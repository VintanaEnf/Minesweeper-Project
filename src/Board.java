import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;

/**
 *
 * @author vintana
 */
public class Board extends JFrame {

    public int Cols;
    public int Rows;
    public int ButtonSize;
    public JButton[][] field;
    private JButton Smiley;
    public Minesweeper msw;
    ImageIcon SmileyIMG, FlagIMG, BombIMG;

    public String WinMessage;
    Board(int Rows, int Cols, int ButtonSize){

        innitGuiTheme();
        setEasterEgg(false);


        this.Cols = Cols;
        this.Rows = Rows;
        this.ButtonSize = ButtonSize;
        field = new JButton[Rows][Cols];

        innitFrame();

        JLabel Score = new JLabel("Well done, comrade!");
        Score.setBounds(20, 0, 200, 50);
        this.add(Score);

        innitField();
        innitSmiley();
        innitGame();
    }

    private void innitGame(){
        reprintButtons();
        msw = new Minesweeper(this.Rows, this.Cols, 5);
        for (int i = 0; i < this.Rows; i++) {
            for (int j = 0; j < this.Cols; j++) {
                field[i][j].setText("");
                field[i][j].setEnabled(true);
            }
        }
        innitActions();
    }

    private void innitFrame(){
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLayout(null);
        this.setTitle("Minesweeper");
        this.setSize(58 + ButtonSize * this.Cols, 90 + (ButtonSize) + ButtonSize * this.Rows);
    }

    public void setEasterEgg(boolean OUR){
        if(OUR){
            //Just for laughs.
            SmileyIMG = new ImageIcon(getClass().getResource("images/Stalin.png"));
            FlagIMG = new ImageIcon(getClass().getResource("images/OurFlag.png"));
            BombIMG = new ImageIcon(getClass().getResource("images/Bomb.png"));
            Smiley.setIcon(SmileyIMG);
            this.setTitle("\"OUR\" Minesweeper");
            WinMessage = "Congratulations Comrade!";
        }

        else{
            //Default Version
            SmileyIMG = new ImageIcon(getClass().getResource("images/Smiley.png"));
            FlagIMG = new ImageIcon(getClass().getResource("images/Flag.png"));
            BombIMG = new ImageIcon(getClass().getResource("images/Bomb.png"));
            this.setTitle("Minesweeper");
            WinMessage = "You Won!";
        }

        this.revalidate();
        this.repaint();
    }

    //Private Innit Methods.

    private void innitGuiTheme(){
        try  {
            // Set the Look and Feel to Nimbus
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
            System.out.println("Loaded");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                 | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
            System.out.println("Can't Load Themes");
        }
    }

    private void innitActions(){
        for (int i = 0; i < this.Rows; i++) {
            for (int j = 0; j < this.Cols; j++) {
                int TRows = i;
                int TCols = j;
                field[i][j].addActionListener(new ActionListener(){
                    public void actionPerformed(ActionEvent e){
                        //This will loop through all the buttons.

                        field[TRows][TCols].setIcon(null);
                        msw.touch(TRows, TCols);
                        field[TRows][TCols].setText(msw.getBoardAt(TRows, TCols)+"");

                        if(msw.getBoardAt(TRows, TCols) == 9){
                            field[TRows][TCols].setText("");
                            field[TRows][TCols].setIcon(BombIMG);
                            System.out.println(field[TRows][TCols].getText());
                        }
                        field[TRows][TCols].setEnabled(false);
                        autoTouchGUI();

                        if(msw.isWon()){
                            JOptionPane.showMessageDialog(null, WinMessage);
                        }
                    }
                });

                field[i][j].addMouseListener(new MouseAdapter() {
                    @Override public void mouseClicked(MouseEvent e) {
                        if (SwingUtilities.isRightMouseButton(e)) {
                            msw.flag(TRows, TCols);

                            if(msw.getGameAt(TRows, TCols)==0){
                                field[TRows][TCols].setIcon(null);
                            }

                            else if(msw.getGameAt(TRows, TCols)==2){
                                field[TRows][TCols].setIcon(FlagIMG);
                            }

                        }
                    }
                });
            }
        }
    }

    private void innitSmiley(){
        Smiley = new JButton();
        float Half;
        Half = 20+ButtonSize*(this.Cols/2);
        Smiley.setBounds((int)Half,10,ButtonSize, ButtonSize);
        Smiley.setIcon(SmileyIMG);
        this.add(Smiley);
        Smiley.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                System.out.println("New Game!");
                innitGame();
            }
        });
    }

    private void innitField(){
        for (int i = 0; i < Rows; i++) {
            for (int j = 0; j < Cols; j++) {
                field[i][j] = new JButton();
                this.add(field[i][j]);
                field[i][j].setBounds((ButtonSize*(j)) + 20, (ButtonSize*(i))+ 20 + ButtonSize,ButtonSize,ButtonSize);
                field[i][j].setFocusPainted(false);
            }
        }
    }

    private void reprintButtons(){
        for (int i = 0; i < this.Rows; i++) {
            for (int j = 0; j < this.Cols; j++) {
                this.remove(field[i][j]);
            }
        }

        innitField();

        this.revalidate();
        this.repaint();
    }

    private void autoTouchGUI(){
        for (int i = 0; i < this.Rows; i++) {
            for (int j = 0; j < this.Cols; j++) {
                if(msw.getGameAt(i, j) == 1){
                    field[i][j].setText(msw.getBoardAt(i, j)+"");
                    field[i][j].setEnabled(false);

                    if(msw.getBoardAt(i, j) == 9){
                        field[i][j].setText("");
                    }
                }
            }
        }
    }

}
