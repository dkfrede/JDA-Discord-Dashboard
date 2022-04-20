package Main;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Commands extends ListenerAdapter {
	// Setting prefix
	public static String prefix = "!";
	
	// Making ArrayList's (not the best i know lmfao)
	static ArrayList<String> commands = new ArrayList<String>();
	static ArrayList<String> cmdReply = new ArrayList<String>();
	static ArrayList<String> cmdEmbedTitle = new ArrayList<String>();
	static ArrayList<String> cmdEmbedDesc = new ArrayList<String>();
	
	// Making the class of the Button press on the "Create" button
	static class ButtonActive implements ActionListener {
		// Doing stuff
		public ButtonActive(JButton button) {
			button.addActionListener(this);
		}
		// Making the function that is gonna be runned on button press
		@Override
		public void actionPerformed(ActionEvent e) {
			// Gets text of text fields from the function in Startup
			ArrayList<String> data = Startup.getTextFields();
			// Adding the command to our "commands" list, with our prefix
			commands.add(prefix + data.get(1));
			
			// Checking if the mode is on "embed"
			if (Startup.getType() == true) {
				// Adding the 2 data of the ArrayList to our "Embed Title's"
				cmdEmbedTitle.add(data.get(2));
				//  Adding the 3 data of the ArrayList to our "Embed Description's"
				cmdEmbedDesc.add(data.get(3));
				// Making the "cmdreply" blank, because its a embed
				cmdReply.add(" ");
				
			} else { // If not, its gonna run this :D
				cmdEmbedTitle.add("nothing");
				cmdEmbedDesc.add("nothing");
				cmdReply.add(data.get(0));
			}
		}
	}
	
	
	// If command "bob" is 0, its title, description, and "reply with message" is 0. This is how we get the data, from a command.
	public String getMessageReply(String message) {
		String content = null;
		if (commands.contains(message)) {
			for(int i = 0; i < commands.size(); i++) {;
				if (commands.get(i).contains(message)) {
					content = cmdReply.get(i);;
				}
			}
		}
		return content;
		
	}
	// The same, but for embeds, iknow its bad coded
	public ArrayList getEmbeds(String message) {
		ArrayList<String> returnList = new ArrayList<String>();
		if (commands.contains(message)) {
			for(int i = 0; i < commands.size(); i++) {;
				if (commands.get(i).contains(message)) {
					returnList.add(cmdEmbedTitle.get(i));
					returnList.add(cmdEmbedDesc.get(i));
				}
			}
		}
		return returnList;
		
	}
	
	@Override // On message event
	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
		// Splitting the message up in args
		String[] args = event.getMessage().getContentRaw().split(" ");
		// Checking if message contains a command from our list
		if (commands.contains(args[0])) {
			// Checking if the data from the "reply with" is nothing, then we know its a embed
			if (getMessageReply(args[0]) == " ") {
				// Making the embed
				EmbedBuilder eb = new EmbedBuilder();
				// Making a list, of all the embed data from the command
				ArrayList<String> hej = getEmbeds(args[0]);
				// Setting the title of the embed to, number 1 in our ArrayList
				eb.setTitle(hej.get(1), null);
				// Setting the description of the embed to, number 0 in our ArrayList
				eb.setDescription(hej.get(0));
				// Setting the color to blue
				eb.setColor(Color.blue);
				// Sending the embed in the target channel.
				event.getChannel().sendMessage((eb.build())).queue();
			} else { // If not, its gonna send the "reply with" data.
				event.getChannel().sendMessage(getMessageReply(args[0])).queue();
			}
		}
	}
}
