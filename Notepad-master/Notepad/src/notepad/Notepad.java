package notepad;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import javax.swing.filechooser.*;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class Notepad extends JFrame implements ActionListener {

    private JTextPane area;
    private JScrollPane scpane;
    String text = "";

    public Notepad() {
        super("Notepad");
        setSize(1950, 1050);

        setLayout(new BorderLayout());

        JMenuBar menuBar = new JMenuBar(); // menubar

        JMenu file = new JMenu("File"); // file menu

        JMenuItem newdoc = new JMenuItem("New");
        newdoc.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
        newdoc.addActionListener(this);

        JMenuItem open = new JMenuItem("Open");
        open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
        open.addActionListener(this);

        JMenuItem save = new JMenuItem("Save");
        save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        save.addActionListener(this);

        JMenuItem print = new JMenuItem("Print");
        print.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.CTRL_MASK));
        print.addActionListener(this);

        JMenuItem exit = new JMenuItem("Exit");
        exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0));
        exit.addActionListener(this);

        JMenu edit = new JMenu("Edit");

        JMenuItem copy = new JMenuItem("Copy");
        copy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));
        copy.addActionListener(this);

        JMenuItem paste = new JMenuItem("Paste");
        paste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.CTRL_MASK));
        paste.addActionListener(this);

        JMenuItem cut = new JMenuItem("Cut");
        cut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));
        cut.addActionListener(this);

        JMenuItem selectall = new JMenuItem("Select All");
        selectall.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK));
        selectall.addActionListener(this);

        JMenuItem bold = new JMenuItem("Bold");
        bold.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B, ActionEvent.CTRL_MASK));
        bold.addActionListener(this);

        JMenu about = new JMenu("Help");

        JMenuItem notepad = new JMenuItem("About");
        notepad.addActionListener(this);

        area = new JTextPane();
        area.setFont(new Font("SAN_SERIF", Font.PLAIN, 20));
        area.setEditorKit(new javax.swing.text.StyledEditorKit());

        scpane = new JScrollPane(area);
        // to remove border of scrollbar
        scpane.setBorder(BorderFactory.createEmptyBorder());

        setJMenuBar(menuBar);
        menuBar.add(file);
        menuBar.add(edit);
        menuBar.add(about);

        file.add(newdoc);
        file.add(open);
        file.add(save);
        file.add(print);
        file.add(exit);

        edit.add(copy);
        edit.add(paste);
        edit.add(cut);
        edit.add(selectall);
        edit.add(bold);

        about.add(notepad);

        add(scpane, BorderLayout.CENTER);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getActionCommand().equals("New")) {
            area.setText("");
        } else if (ae.getActionCommand().equals("Open")) {
            JFileChooser chooser = new JFileChooser("D:/Java");
            chooser.setAcceptAllFileFilterUsed(false);
            FileNameExtensionFilter restrict = new FileNameExtensionFilter("Only .txt files", "txt");
            chooser.addChoosableFileFilter(restrict);

            int result = chooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File file = chooser.getSelectedFile();
                try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                    area.read(br, null);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else if (ae.getActionCommand().equals("Save")) {
            JFileChooser saveAs = new JFileChooser();
            saveAs.setApproveButtonText("Save");
            int actionDialog = saveAs.showSaveDialog(this);
            if (actionDialog == JFileChooser.APPROVE_OPTION) {
                File fileName = saveAs.getSelectedFile();
                try (BufferedWriter outFile = new BufferedWriter(new FileWriter(fileName))) {
                    area.write(outFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else if (ae.getActionCommand().equals("Print")) {
            try {
                area.print();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (ae.getActionCommand().equals("Exit")) {
            System.exit(0);
        } else if (ae.getActionCommand().equals("Copy")) {
            area.copy();
        } else if (ae.getActionCommand().equals("Paste")) {
            area.paste();
        } else if (ae.getActionCommand().equals("Cut")) {
            area.cut();
        } else if (ae.getActionCommand().equals("Select All")) {
            area.selectAll();
        } else if (ae.getActionCommand().equals("Bold")) {
            int start = area.getSelectionStart();
            int end = area.getSelectionEnd();
            if (start != end) { // Check if there is a selection
                StyledDocument doc = area.getStyledDocument();
                Style style = area.addStyle("BoldStyle", null);
                boolean isBold = StyleConstants.isBold(doc.getCharacterElement(start).getAttributes());
                if (isBold) {
                    StyleConstants.setBold(style, false);
                } else {
                    StyleConstants.setBold(style, true);
                }
                doc.setCharacterAttributes(start, end - start, style, false);
            }

        } else if (ae.getActionCommand().equals("About")) {
            new About().setVisible(true);
        }
    }

    public static void main(String[] args) {
        new Notepad();
    }
}
