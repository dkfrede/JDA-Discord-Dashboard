package Main;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

import javax.security.auth.login.LoginException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;

public class Startup {
	// Making the basics, of the GUI
    static JTextField motto1 = new JTextField("Reply with");
    static JTextField motto2 = new JTextField("Command");
    
    // Creating the, Embed Description & Title.
    static JTextField Desc = new JTextField("Title");
    static JTextField Title = new JTextField("Desc");

    // Returning list with all text from, the input fields
    public static ArrayList getTextFields() {
    	// Making ArrayList
    	ArrayList<String> returnList = new ArrayList<String>();
    	
    	// Adding to ArrayList
    	returnList.add(motto1.getText());
    	returnList.add(motto2.getText());
    	returnList.add(Title.getText());
    	returnList.add(Desc.getText());
    	
    	// Returning the ArrayList
    	return returnList;
    }
    
    // Making the JFrame
    static JFrame f = new JFrame("Bot Dashboard");
    
    // Type
    public static boolean type;
    
    public static boolean getType() {
    	// Returning the current, "embed" or normal type
    	return type;
    }
    
    public static void Typeswitch(boolean switchTo) {
    	if (switchTo == true) {
    		// Setting the type to true
    		type = true;
    	 
    		// Adding the 2 input boxes to the frame
    		f.add(Title);
    	    f.add(Desc);
    	    
    	    // Settings the position, and removing "Reply with" box
    	    Title.setBounds(280, 100, 200, 30);
    	    Desc.setBounds(280, 50, 200, 30);
    	    motto1.setBounds(0,0,0, 30);
    	    
 
    	    
    	} else {
    		// Deleting and showing "Reply With" box again
    		Title.setBounds(0, 0, 0, 0);
    		Desc.setBounds(0, 0, 0, 0);
    		motto1.setBounds(30, 100, 200, 30);
    		type = false;
    	}
    }
    
    // When program run
	public static void main(String[] args) throws LoginException, IOException {
		
		// Open the file
		String token = null;
        try {
            File tokenFile = Paths.get("token.txt").toFile();
            if (!tokenFile.exists()) {
                System.out.println("[ERROR] Could not find token.txt file");
                System.out.print("Please paste in your bot token: ");
                Scanner s = new Scanner(System.in);
                token = s.nextLine();
                System.out.println();
                System.out.println("[INFO] Creating token.txt - please wait");
                if (!tokenFile.createNewFile()) {
                    System.out.println(
                            "[ERROR] Could not create token.txt - please create this file and paste in your token"
                                    + ".");
                    s.close();
                    return;
                }
                Files.write(tokenFile.toPath(), token.getBytes());
                s.close();
            }
            token = new String(Files.readAllBytes(tokenFile.toPath()));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
		
		// Logging in with token
		JDABuilder jda = JDABuilder.createDefault(token);
		// Settings the activity of the bot.
		jda.setActivity(Activity.playing("Bot by the cool Fr3ckzDK"));
		// Setting the status of the bot to online
		jda.setStatus(OnlineStatus.ONLINE);
		// Making so it used the Class commands
		jda.addEventListeners(new Commands());
		// Building the bot
		jda.build();
		
		// Making the UI
		f.getContentPane().setBackground(new Color(88, 101, 242));
		f.setSize(600, 300);
		f.setLocation(100, 150);
		f.setIconImage(null);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setDefaultLookAndFeelDecorated(true);

		// Setting the size & position of the 2 input boxes
		motto1.setBounds(30, 100, 200, 30);
		motto2.setBounds(30, 50, 200, 30);
		
		// Making the buttons
		JButton b = new JButton("Create");
		JButton b2 = new JButton("Embed");
		
		// Setting the size & position of "Create" & "Embed"
		b.setBounds(30, 150, 100, 30);
		b2.setBounds(140, 150, 100, 30);
		
		// Adding the parts to the UI
		f.add(b);
		f.add(b2);
		f.add(motto1);
		f.add(motto2);
		
		// Doing UI stuff
		f.setLayout(null);
		f.setVisible(true);
		
		// Doing when clicked "Create", calls class in Command called ButtonActive
		new Commands.ButtonActive(b);
		
		// When clicking on "embed" button
		b2.addActionListener(e ->
		{
			if (type == false) {
				Typeswitch(true);   
			} else {
				Typeswitch(false);
			}
		});
	}
}
