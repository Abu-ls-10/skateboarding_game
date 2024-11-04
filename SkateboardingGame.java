// Imports all necessary modules
import java.util.*;
import java.io.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

// Extends JFrame class on main file for handling multiple menuWindows
public class SkateboardingGame extends JFrame implements ActionListener
{
    // Initializes instace variables
    private GameScreen gameScreen;

    // Creates constructor for game menuWindow
    public SkateboardingGame() 
    {
        // Calls super to assign menuWindow title
        super("Skateboarding Game");

        // Sets attributes to game menuWindow
        setSize(900, 720);
        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        // Utilizes instance variable and adds to game menuWindow
        gameScreen = new GameScreen();
        addKeyListener(gameScreen);
        add(gameScreen);
    }

    /**
    Starts rendering and initializing components of the game. 
    Precondition: GameScreen object must be initialized.
    Postcondition: Starts game.
    */
    public void startGame() 
    {
        setVisible(true);
        gameScreen.startGame();
    }
    public static void main(String args[]) throws IOException, FontFormatException
    {
        // Creates Dimension object with width 900 and height 720   
        Dimension dim = new Dimension(900, 720);

        // Colours
        Color darkBlue = new Color(0, 0, 100);
        Color lightGrey = new Color(224, 224, 224);

        // Initializes JFrame for main menu called menuWindow
        JFrame menuWindow = new JFrame();

        // Uses JFrame class mutator methods to assign attributes to menuWindow
        menuWindow.setSize(dim);
        menuWindow.setVisible(true);
        menuWindow.setTitle("Skateboarding Game");
        menuWindow.setLocationRelativeTo(null);
        menuWindow.setResizable(false);
        menuWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Sets logo for game as game window icon
        ImageIcon gameIcon = new ImageIcon("gameIcon.png");
        Image icon = gameIcon.getImage();
        menuWindow.setIconImage(icon);

        // Initializes JPanel for menu contents
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
        menuPanel.setPreferredSize(dim);
        menuPanel.setBackground(lightGrey);
        menuPanel.setOpaque(false);
        menuWindow.setContentPane(menuPanel);

        // Creates a Dimension object for buttons
        Dimension dimButton = new Dimension(200, 50);

        // Initializes fonts for aesthetics
        Font font1 = Font.createFont(Font.TRUETYPE_FONT, new BufferedInputStream(new FileInputStream("Santa_Cruz.ttf")));
        Font font2 = Font.createFont(Font.TRUETYPE_FONT, new BufferedInputStream(new FileInputStream("SantaCruz.ttf")));

        // Adds title component to menuPanel
        JLabel title = new JLabel("Skateboarding Game");
        title.setFont(font1.deriveFont(Font.PLAIN, 75));
        title.setForeground(darkBlue);
        menuPanel.add(title);

        // Creates play JButton and listens for mouse events
        JButton play = new JButton(new AbstractAction("Play") {
            @Override
            public void actionPerformed( ActionEvent e ) 
            {
                // Instigates a pop-up window asking for username
                JFrame popUp = new JFrame();
                popUp.setVisible(true);
                popUp.setLocationRelativeTo(null);

                // Defines contents for pop-up panel
                JPanel popUpPanel = new JPanel();
                popUpPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 10));
                popUpPanel.setPreferredSize(new Dimension(200, 100));
                popUpPanel.setBackground(lightGrey);
                popUp.setContentPane(popUpPanel);

                // Adds text to popUpPanel
                JLabel text = new JLabel("Enter your username: ");
                popUpPanel.add(text);

                // Adds a text field for username
                JTextField userEntry = new JTextField(16);
                popUpPanel.add(userEntry);

                // Stores username into users.txt file when start JButton is clicked
                JButton start = new JButton(new AbstractAction("Start") {
                    @Override
                    public void actionPerformed( ActionEvent e )
                    {
                        String username = userEntry.getText();

                        // Tries writing into and appending file
                        try (FileWriter writer = new FileWriter("users.txt", true))
                    {
                        BufferedWriter bWriter = new BufferedWriter(writer);
                        PrintWriter printer = new PrintWriter(bWriter);

                        // Adds username on next line
                        printer.println(username +"\n");

                        // Establishes secure connection between streams for appending file
                        printer.close();
                        bWriter.close();
                        writer.close();
                    }

                    // Catches IOException to prevent program failure
                    catch (IOException l) 
                    {
                        System.out.println("File Error: " +l.getMessage());
                    }
                        (new SkateboardingGame()).startGame();

                    }});

                // Assigns attributes for JButton start and adds to menuPanel
                start.setFont(font2.deriveFont(Font.PLAIN, 15));
                popUpPanel.add(start);
                popUp.pack();
            }
        });

        // Assigns attributes for JButton play and adds to menuPanel
        play.setPreferredSize(dimButton);
        play.setFont(font2.deriveFont(Font.PLAIN, 20));
        menuPanel.add(play);

        // Creates JButton quit and closes menuWindow when clicked
        JButton quit = new JButton(new AbstractAction("Quit") {
            @Override
            public void actionPerformed( ActionEvent e ) 
            {
                menuWindow.dispose();
            }
        });

        // Assigns attributes for JButton quit and adds to menuPanel
        quit.setFont(font2.deriveFont(Font.PLAIN, 20));
        quit.setPreferredSize(dimButton);
        menuPanel.add(quit);

        // Creates JButton instructions and opens a pop-up window with instructions
        JButton instructions = new JButton(new AbstractAction("Instructions") {
            @Override
            public void actionPerformed( ActionEvent e ) {
                JFrame popUp = new JFrame();
                JLabel steps = new JLabel("1. Press Enter to start playing \n2. Press Spacebar to avoid the obstacles and get the coins! \n3. Press 'R' to replay");
                popUp.add(steps);
                popUp.setVisible(true);
                popUp.setLocationRelativeTo(null);
                popUp.pack();
            }
        });

        // Assigns attributes for JButton instructions and adds to menuPanel
        instructions.setFont(font2.deriveFont(Font.PLAIN, 20));
        instructions.setPreferredSize(dimButton);
        menuPanel.add(instructions);

        // Initializes ArrayList scoreList and BufferedReader bReader with access to scores.txt file
        ArrayList<Integer> scoreList = new ArrayList<Integer>();
        BufferedReader bReader = new BufferedReader(new FileReader("scores.txt"));

        // While loop ends when line is empty
        // Stores String value on each line, converts to Integer, and appends scoreList
        String line = bReader.readLine();
        while (line != null) 
        {
            scoreList.add(Integer.valueOf(line));
            line = bReader.readLine();
        }

        bReader.close();

        // Determines highest score through linear search
        int recordScore = 0;
        for (int score = 0; score < scoreList.size(); score++)
        {
            if (scoreList.get(score) > recordScore)
            {
                recordScore = scoreList.get(score);
            }
        }
        
        // Creates JLabel worldRecord and adds to menuPanel for display
        JLabel worldRecord = new JLabel("Current World Record Score: " +recordScore);
        worldRecord.setFont(new Font("Arial", Font.BOLD, 15));
        menuPanel.add(worldRecord);

        // Creates JLabel menuBackground with Image and adds to manuPanel.
        JLabel menuBackground = new JLabel(new ImageIcon("Menu.jpg"));
        menuBackground.setBounds(0, 0, 900, 720);
        menuPanel.add(menuBackground);

        // Compartmentalizes all components in menuWindow
        menuWindow.pack();
    }
}
