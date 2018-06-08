import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import javax.swing.text.*;

//Simple Text Editor similar to notepad

public class textEditor extends JFrame
{
	
	private JTextArea text = new JTextArea(50, 120);
	private String currentFile = new String("Untitled");
	private JFileChooser dialog = new JFileChooser(System.getProperty("user.dir"));
	private boolean changed = false;
	
	
	//Constructor, right now, we just make a window where we can type things, but nothing else.
	public textEditor()
	{
		//Create the text area and scrollbar for the program
		text.setFont(new Font("Monospaced", Font.PLAIN, 12));
		JScrollPane scroll = new JScrollPane(text, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		add(scroll, BorderLayout.CENTER);
		
		//Create the menu bar and the File and Edit menus
		JMenuBar bar = new JMenuBar();
		JMenu file = new JMenu("File");
		JMenu edit = new JMenu("Edit");
		bar.add(file);
		bar.add(edit);
		setJMenuBar(bar);
		
		//Add options for file menu
		file.add(New);
		file.add(Open);
		file.add(SaveAs);
		file.add(Quit);
		
		for(int i=0; i<4; i++)
			file.getItem(i).setIcon(null);
		
		//Add options for edit menu
		edit.add(Cut);
		edit.add(Copy);
		edit.add(Paste);
		
		edit.getItem(0).setText("Cut");
		edit.getItem(1).setText("Copy");
		edit.getItem(2).setText("Paste");
		
		//Create the toolbar and add several buttons to it
		JToolBar tool = new JToolBar();
		add(tool, BorderLayout.NORTH);
		
		tool.add(New);
		tool.add(Open);
		tool.add(Save);
		tool.addSeparator();
		
		JButton cut = tool.add(Cut), copy = tool.add(Copy), paste = tool.add(Paste); 
		
		//Set icons for the toolbar buttons
		cut.setText(null); 
		cut.setIcon(new ImageIcon("cut.png"));
		copy.setText(null); 
		copy.setIcon(new ImageIcon("copy.png"));
		paste.setText(null); 
		paste.setIcon(new ImageIcon("paste.png"));
		
		//Initially disable the ability for the user to save the file, until something is typed
		Save.setEnabled(false);
		SaveAs.setEnabled(false);
		
		
		text.addKeyListener(keyL);
		setTitle("My Text Editor - " + currentFile);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		pack();
		setVisible(true);
	}
	
	//Key listener that enables the save options once any key is pressed
	private KeyListener keyL = new KeyAdapter()
	{
		public void keyPressed(KeyEvent e)
		{
			Save.setEnabled(true);
			SaveAs.setEnabled(true);
			changed = true;
		}
	};
	
	//Next are the actions for various menu options
	Action New = new AbstractAction("New", new ImageIcon("new.png"))
	{
		public void actionPerformed(ActionEvent e)
		{
			new textEditor();
		}
	};
	
	Action Quit = new AbstractAction("Quit")
	{
		public void actionPerformed(ActionEvent e)
		{
			saveOld();
			System.exit(0);
		}
	};
	
	Action SaveAs = new AbstractAction("Save As...")
	{
		public void actionPerformed(ActionEvent e)
		{
			saveFileAs();
		}
	};
	
	Action Save = new AbstractAction("Save", new ImageIcon("save.png"))
	{
		public void actionPerformed(ActionEvent e)
		{
			if(!currentFile.equals("Untitled"))
				saveFile(currentFile);
			else
				saveFileAs();
		}
	};
	
	Action Open = new AbstractAction("Open", new ImageIcon("open.png"))
	{
		public void actionPerformed(ActionEvent e)
		{
			if(changed)
				saveOld();
			if(dialog.showOpenDialog(null)==JFileChooser.APPROVE_OPTION)
				readInFile(dialog.getSelectedFile().getAbsolutePath());
		}
	};
	
	//For cut, copy, and paste, we use the default actions from DefaultEditorKit
	ActionMap map = text.getActionMap();
	Action Cut = map.get(DefaultEditorKit.cutAction);
	Action Copy = map.get(DefaultEditorKit.copyAction);
	Action Paste = map.get(DefaultEditorKit.pasteAction);
	
	//Several methods used to save and read files as needed
	private void saveOld()
	{
		if(JOptionPane.showConfirmDialog(this, "Would you like to save "+ currentFile +"?","Save",JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
			saveFile(currentFile);
	}
	
	private void saveFileAs()
	{
		if(dialog.showSaveDialog(null) == JFileChooser.APPROVE_OPTION)
			saveFile(dialog.getSelectedFile().getAbsolutePath());
	}
	
	private void saveFile(String file)
	{
		try
		{
			FileWriter writer = new FileWriter(file);
			text.write(writer);
			writer.close();
			currentFile = file;
			setTitle("My Text Editor - " + file);
			changed = false;
			Save.setEnabled(false);
		}
		catch(IOException e)
		{
			
		}
	}
	
	private void readInFile(String file)
	{
		try
		{
			FileReader reader = new FileReader(file);
			text.read(reader, null);
			reader.close();
			currentFile = file;
			setTitle("My Text Editor - " + currentFile);
			changed = false;
		}
		catch(IOException e)
		{
			JOptionPane.showMessageDialog(this,"Editor can't find the file called " + file);
		}
	}
	
	public static void main(String [] args)
	{
		new textEditor();
	}
}
