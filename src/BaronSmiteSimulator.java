 /**
 * @(#)BaronSmiteSimulator.java
 * This is a Baron Smite Simulator. This program is mostly made for people who play league of legends. If you do not, this program displays a picture of a monster called Baron Nashor.
 * When you run it, it will display the amount of health this monster has. Before you start the program you have to input some values like baron time, which calculates how much health
 * Baron has, the amount of smite damage you do, and the difficulty level. The smite skill deals damage to Baron. When you run the program baron will continuously lose health, you have to
 * use your smite skill to get the last hit on baron, if you do you win, if you smite to early you lose, if you smite too late you lose. When you run this program make sure the directory
 * of the Baron Nashor picture is correct, otherwise the picture will not display.
 * @author Venkat Korapaty
 * @version 2.2 2013/6/9
 */
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;
import java.util.Scanner;
public class BaronSmiteSimulator extends JFrame implements Runnable{
   //These are the global variables needed for my program
   public static boolean smiteDone = false, start = false;
   public static int smiteDmg = 0, maxHealth, baronTime, difficulty, outCome;
   public static double  baronHealth, hpDecreaseDec, hpDecrease;
   //***************************************************
   
   //This creates 2 rectangles which I later use to represent the health of Baron
   Rectangle r1 = new Rectangle(75, 600, 50, 500);
   Rectangle r2 = new Rectangle(75, 600, 50, 500);
   
   //This is needed to import the picture I use for the background of my program
    private Image dbImage;
    private Graphics dbg;
    Image img = Toolkit.getDefaultToolkit().createImage("D:\\eclips workspace\\BaronSmiteSimulator\\Baronnasoor.jpg");
   
    int rectX, rectY;
    /**
     * This method allows me to type printf instead of System.out.println
     * @param output 
     */
    public static void printf(String output){
    System.out.println(output);
    }
    /**
     * This method sets the parameters for the JFrame I use for the program
     */
    public BaronSmiteSimulator(){
    	setTitle("BARON SMITE GAME");
        setSize(1000,675);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
        addMouseMotionListener(new AL());
		addMouseListener(new AL());
    }
    
    @Override
    /**
     * This graphics method is where the picture I imported gets called, and also calls the other
     * graphics method I have
     */
    public void paint(Graphics g){
        dbImage = createImage(getWidth(), getHeight());
        dbg = dbImage.getGraphics();
        paintComponent(dbg);
        g.drawImage(dbImage, 0, 0, this);
    }
    /**
     * This method is what calculates the amount of health baron has and how big the health bar is.
     * It generates a random number, which varies under the set circumstances, which is the number
     * used to subtract from barons health. That number is that converted into a percentage, then multiplied by 500
     * to get the length of the health bar. This method gets called every 0.6 seconds. When Barons health decreases,
     * it is suppose to represent a player doing damage to it overtime, but since making that happen is a bit too complicated,
     * it simply subtracts an amount of his health to represent a player doing damage.
     */
    public void hp(){
    	if(!smiteDone){
            hpDecrease = (int)((Math.random()*300) + 100);
            if(baronHealth <= smiteDmg + 200)
                 hpDecrease = (int)(Math.random()*100);
	    hpDecreaseDec = ((((baronHealth-hpDecrease)/maxHealth))*500);
	    baronHealth -= hpDecrease;
    	}
    }
    /**
     * This method draws the health bar, and an outline around it. It also displays the Baron picture.
     * It also draws a few strings to tell the person how much health baron has in the for of a numeral
     * and displays whether you have successfully smited baron or not.
     * @param g 
     */
    public void paintComponent(Graphics g){
    	g.drawImage(img, 0, 0, null);
    	r2.height = (int)hpDecreaseDec;
        //Rectangle for Baron's health
        g.setColor(Color.RED);
        g.fillRect(r2.x, r2.y, r2.height, r2.width);
        //Rectangle to outline Baron's health
 	g.setColor(Color.BLACK);
        g.drawRect(r1.x, r1.y, r1.height, r1.width);
        //Displays a title
        g.setColor(Color.ORANGE);
        g.drawString("Baron Nashor Smite Simulator", 450, 100);
        //Displays amount of health baron has
        g.setColor(Color.WHITE);
        if(baronHealth >= 0)
        	g.drawString("BARON HP: " + baronHealth,80,620);
        else{
        	g.drawString("BARON HP: 0", 80, 620);
                baronHealth = 0;
        }
        g.setColor(Color.GRAY);
        g.drawString("Smite damage: " + smiteDmg, 75, 580);
        g.drawString("Click anywhere to smite", 450, 580);
        if(smiteDone){
            g.setColor(Color.WHITE);
            //This outputs whether you were successful in smitting baron or not
            if(outCome == 1){
                g.drawString("You have smited too early and the enemy has stolen Baron Nashor!",350,350);
            }
            else if(outCome == 2){
                g.drawString("You have smited Baron Nashor!",350,350);
            }
            else if(outCome == 3){
                g.drawString("The enemy has stolen Baron Nashor!",350,350);
            }
       }
        repaint();
    }
    /**
     * This allows me to get a mouse click input, which for the program
     * is how you tell the program you have "smited" Baron. When you click
     * it subtracts the health from Baron and under certain conditions, it will tell
     * you if you were successful or not.
     */
    public class AL extends MouseAdapter {

        @Override
        public void mouseMoved(MouseEvent e){

        }
        public void mousePressed(MouseEvent e){
            if(!smiteDone){
                 if(difficulty == 3){
                    if(baronHealth > smiteDmg){
                        baronHealth -= smiteDmg;
                        printf("You have smited too early and the enemy has stolen Baron Nashor!");
                        outCome = 1;
                    }
                    else if(baronHealth == smiteDmg){
                        baronHealth -= smiteDmg;
                        printf("You have smited Baron Nashor!");
                        outCome = 2;
                    }
                    else if(baronHealth <= smiteDmg && baronHealth >= smiteDmg -100){
                        baronHealth -= smiteDmg;
                        printf("You have smited Baron Nashor!");
                        outCome = 2;
                    }
                    else if(baronHealth < smiteDmg - 100){
                        baronHealth -= smiteDmg;
                        printf("The enemy has stolen Baron Nashor!");
                        outCome = 3;
                    }
                }
                else if(difficulty == 2){
                    if(baronHealth > smiteDmg){
                        baronHealth -= smiteDmg;
                        printf("You have smited too early and the enemy has stolen Baron Nashor!");
                        outCome = 1;
                    }
                    else if(baronHealth == smiteDmg){
                        baronHealth -= smiteDmg;
                        printf("You have smited Baron Nashor!");
                        outCome = 2;
                    }
                    else if(baronHealth <= smiteDmg && baronHealth >= smiteDmg - 200){
                        baronHealth -= smiteDmg;
                        printf("You have smited Baron Nashor!");
                        outCome = 2;
                    }
                    else if(baronHealth < smiteDmg - 200){
                        baronHealth -= smiteDmg;
                        printf("The enemy has stolen Baron Nashor!");
                        outCome = 3;
                    }
                }
                else if(difficulty == 1){
                    if(baronHealth > smiteDmg){
                        baronHealth -= smiteDmg;
                        printf("You have smited too early and the enemy has stolen Baron Nashor!");
                        outCome = 1;
                    }
                    else if(baronHealth == smiteDmg){
                        baronHealth -= smiteDmg;
                        printf("You have smited Baron Nashor!");
                        outCome = 2;
                    }
                    else if(baronHealth <= smiteDmg && baronHealth >= smiteDmg - 400){
                        baronHealth -= smiteDmg;
                        printf("You have smited Baron Nashor!");
                        outCome = 2;
                    }
                    else if(baronHealth < smiteDmg - 400){
                        baronHealth -= smiteDmg;
                        printf("The enemy has stolen Baron Nashor!");
                        outCome = 3;
                    }
                }
                else if(difficulty == 4){
                    if(baronHealth > smiteDmg){
                        baronHealth -= smiteDmg;
                        printf("You have smited too early and the enemy has stolen Baron Nashor!");
                        outCome = 1;
                    }
                    else if(baronHealth == smiteDmg){
                        baronHealth -= smiteDmg;
                        printf("You have smited Baron Nashor!");
                        outCome = 2;
                    }
                    else if(baronHealth <= smiteDmg && baronHealth >= smiteDmg - 1){
                        baronHealth -= smiteDmg;
                        printf("You have smited Baron Nashor!");
                        outCome = 2;
                    }
                    else if(baronHealth < smiteDmg - 1){
                        baronHealth -= smiteDmg;
                        printf("The enemy has stolen Baron Nashor!");
                        outCome = 3;
                    }
                }
	            smiteDone = true;
            } 
        }
    }
    /**
     * This method is what makes Baron's health decrease over a certain period of time. It is programmed
     * such that the lower the difficulty, the lower the rate Baron's health decreases making it easier to smite.
     */
    public void run(){
        try{
            while(baronHealth > 0){ 
                hp();
                if(difficulty == 1)
                    Thread.sleep(1000);
                else if(difficulty == 2)
                    Thread.sleep(600);
                else if(difficulty == 3)
                    Thread.sleep(350);
                else if(difficulty == 4)
                    Thread.sleep(200);
            }
        }catch(Exception e) {}
    }
    /**
     * The main method gets the amount of health baron has, the amount of damage smite will do
     * and the difficulty. It also explains what the game does and how to play it. There are also parameters
     * making sure the user does not type an unreasonable input.
     * @param args 
     */
    public static void main(String[] args) {
        
        Scanner s = new Scanner(System.in);
    	int baronTime, smiteLvl;
        
        printf("Welcome to the Baron Smite Simulator game. This game is a mini-game based off the real game League of Legends. In that game, there are two teams");
        printf("that face each other, and at some point in the game, each of the team will want to fight the monster Baron Nashor. One person on each team will have");
        printf("a skill called \"smite\" which does a certain amount of damage to a monster in the game, not to each other. When the teams fight for baron, they want to");
        printf("get the last hit on Baron, because whoever gets the last hit on Baron gives the team they are on a buff. To get the last hit people smite it, because it does");
        printf("more damage than other players skills. So it does not matter who does the most damage, as long as you get the last hit.");
    	printf("Enter the amount of time it has been from 1 - 60 minutes, this will change the amount of health Baron has. Any value greater than 60 will be treated as 60 minutes.");
        baronTime = s.nextInt();
            if(baronTime >=60){
    		baronHealth = 13500;
    		maxHealth = 13500;
    		printf("Baron health is " + baronHealth);
                printf("");
            }
            else{
    		baronHealth = 9000 + (baronTime*100);
    		maxHealth = 9000 + (baronTime*100);
                printf("Baron health is " + baronHealth);
                printf("");
            }
        //Smite Damage
        printf("Now enter what level your character is, based on that your smite damage be high or low. The lower the smite damage,the harder it will be to smite.");
        smiteLvl = 0;
            //Makes sure smite level is within parameter
            while(smiteLvl < 1 || smiteLvl > 18){
                printf("Please enter a number between 1 - 18");
        	printf("1 = 490 smite damage || 2 = 520 smite damage || 3 = 550 smite damage");
        	printf("4 = 580 smite damage || 5 = 610 smite damage || 6 = 640 smite damage");
        	printf("7 = 670 smite damage || 8 = 700 smite damage || 9 = 730 smite damage");
        	printf("10 = 760 smite damage || 11 = 790 smite damage || 12 = 820 smite damage");
        	printf("13 = 850 smite damage || 14 = 880 smite damage || 15 = 910 smite damage");
        	printf("16 = 940 smite damage || 17 = 970 smite damage || 18 = 1000 smite damage");
                printf("");
                smiteLvl = s.nextInt();
            }
       
        //Calculates the amount of smite damage the user can do
        smiteDmg = 460 + 30*smiteLvl;
        printf("Smite damage is " + smiteDmg);
        printf("Enter the difficulty you wish to play at, the harder the difficulty the faster the rate Baron's health will drop making it harder to smite,");
        printf("also it will give you a 100 hp interval to smite him at the hardest difficulty, 200 at intermediate, and 300 at easy. Meaning it will be easier to smite Baron.");
        printf("1 = easy");
        printf("2 = intermediate");
        printf("3 = hard");
        printf("4 = impossible");
        difficulty = s.nextInt();
        while(difficulty < 1 || difficulty > 4){
            printf("Please enter a value that that is either 1 for easy, 2 for intermediate, 3 for hard, or 4 for impossible..");
            difficulty = s.nextInt();
        }
        s.close();
        BaronSmiteSimulator main = new BaronSmiteSimulator();
        Thread epic = new Thread(main);
        epic.start();
    }
}