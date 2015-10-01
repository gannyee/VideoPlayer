package com.play_list;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Rectangle;
import java.awt.Toolkit;

import javax.print.attribute.standard.Media;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.main.MyMain;
import com.views.DisplayFram;

import java.awt.Window.Type;
import javax.swing.JList;
import javax.swing.JScrollBar;
import java.awt.event.AdjustmentListener;
import java.awt.event.AdjustmentEvent;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.FlowLayout;

public class PlayListFrame extends JFrame {

	private JPanel contentPane;
	private int flag = 0;
	private JList list = new JList();
	private JScrollPane scrollPane;
	private JPanel panel;
	private JButton historyClearButton;
	private DefaultListModel dlm = new DefaultListModel();
	private JButton searchButton;
	private JTextField textField;
	private JPanel panel_1;
	/**
	 * Create the frame.
	 */
	public PlayListFrame() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				flag = 1;
				MyMain.getFrame().getListButton().setText("List>>");
				MyMain.getControlFrame().getListButton().setText(MyMain.getFrame().getListButton().getText());
			}
		});
		setType(Type.UTILITY);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setMaximizedBounds(new Rectangle((int)Toolkit.getDefaultToolkit().getScreenSize().getWidth() - 230, 0, 230,(int)Toolkit.getDefaultToolkit().getScreenSize().getHeight()));
		//setBounds(100, 100, 229, 394);
		try {
			setList(MyMain.getListHistory().readHistory());
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		scrollPane = new JScrollPane();
		scrollPane.setEnabled(false);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		list.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount() == 2){
					String name = MyMain.getListView().getList().get(MyMain.getListView().getList().size() - 1 - list.getSelectedIndex());
					System.out.println("list hight: " + list.getSelectedIndex());
					System.out.println("item: " + name);
					MyMain.openVedioFromList(name);
					setList(MyMain.getListView().getList());
					getScrollPane().setViewportView(getList());
				}
			}
		});
		contentPane.add(scrollPane, BorderLayout.CENTER);
		scrollPane.setViewportView(getList());
		
		panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);
		panel.setLayout(new BorderLayout(0, 0));
		
		panel_1 = new JPanel();
		panel.add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		textField = new JTextField();
		panel_1.add(textField);
		textField.setColumns(10);
		
		searchButton = new JButton("Search History");
		panel_1.add(searchButton);
		
		historyClearButton = new JButton("Clear History");
		panel_1.add(historyClearButton);
		historyClearButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
			}
		});

	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public JList getList(){
		
		return list;
	}

	public void setList(ArrayList<String> arrayList) {
		
		dlm = new DefaultListModel();
		for(int i = arrayList.size() - 1; i >= 0;i --){
			dlm.addElement(arrayList.get(i));
		}
		
		list.setModel(dlm);
	}

	public JScrollPane getScrollPane() {
		return scrollPane;
	}
	
	
}
