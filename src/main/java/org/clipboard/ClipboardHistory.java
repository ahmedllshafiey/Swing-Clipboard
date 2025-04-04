package org.clipboard;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.ListSelectionModel;

public class ClipboardHistory extends JPanel implements ClipboardListeners.EntryListener {
    
    private JList<String> list;
    private DefaultListModel<String> listModel;
    private ListSelectionModel listSelectionModel;

    public ClipboardHistory() {
        super(new BorderLayout());
        listModel = new DefaultListModel<String>();
        list = new JList<String>(listModel);
        listSelectionModel = list.getSelectionModel();
        listSelectionModel.setSelectionMode(listSelectionModel.SINGLE_SELECTION);

        JScrollPane listPane = new JScrollPane(list);
        JPanel controlPane = new JPanel();

        final JButton button = new JButton("Copy");
        button.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                String value = (String) list.getSelectedValue();
                int index = list.getSelectedIndex();
                listModel.remove(index);
                copyToClipboard(value);
            }
        });
        controlPane.add(button);

        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        add(splitPane, BorderLayout.CENTER);

        JPanel topHalf = new JPanel();
        topHalf.setLayout(new BoxLayout(topHalf, BoxLayout.LINE_AXIS));
        JPanel listContainer = new JPanel(new GridLayout(1, 1));
        listContainer.setBorder(BorderFactory.createTitledBorder("Entries"));
        listContainer.add(listPane);

        topHalf.setBorder(BorderFactory.createEmptyBorder(5, 5, 0, 5));
        topHalf.add(listContainer);
        topHalf.setMinimumSize(new Dimension(100,50));
        topHalf.setPreferredSize(new Dimension(100,250));
        splitPane.add(topHalf);

        JPanel bottomHalf = new JPanel(new BorderLayout());
        bottomHalf.add(controlPane, BorderLayout.CENTER);
        bottomHalf.setPreferredSize(new Dimension(450,30));
        splitPane.add(bottomHalf);
    }

    public void copyToClipboard(String value) {
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        StringSelection data = new StringSelection(value);
        clipboard.setContents(data, data);
    }

    public void createAndShowGUI() {
        JFrame frame = new JFrame ("Clipboard History");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setOpaque(true);
        frame.setContentPane(this);
        frame.setVisible(true);
        frame.pack();

        ClipboardListeners clipboardListener = new ClipboardListeners();
        clipboardListener.setEntryListener(this);
        clipboardListener.start();
    }


    @Override
    public void onCopy(String data) {
        listModel.add(0, data);
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                ClipboardHistory clipboardHistory = new ClipboardHistory();
                clipboardHistory.createAndShowGUI();
            }
        });
    }
}