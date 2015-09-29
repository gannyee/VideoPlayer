package com.views;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.main.MyMain;

import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import javax.swing.JButton;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;
import javax.swing.Action;
import java.awt.event.ActionListener;
import javax.swing.JProgressBar;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JSlider;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.SwingConstants;
import javax.swing.Timer;

public class DipalyFram extends JFrame {

	private JPanel contentPane;

	EmbeddedMediaPlayerComponent playerComponent;
	private JPanel panel;
	private JButton stopButton;
	private JButton playButton;
	// private JButton pauseButton;
	private JPanel controlPanel;
	private JProgressBar progressBar;
	private JSlider volumControlerSlider;
	private JMenuBar menuBar;
	private JMenu mnFile;
	private JMenuItem mntmOpenVideo;
	private JMenuItem mntmOpenSubtitle;
	private JMenuItem mntmExit;
	private JButton forwardButton;
	private JButton backwordButton;
	private JButton FullScreenButton;
	/**
	 * Launch the application.
	 */
	/*
	 * public static void main(String[] args) {
	 * 
	 * }
	 */

	/**
	 * Create the frame.
	 */
	public DipalyFram() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 565, 394);

		menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		mnFile = new JMenu("File");
		menuBar.add(mnFile);

		mntmOpenVideo = new JMenuItem("Open Video");
		mnFile.add(mntmOpenVideo);

		mntmOpenSubtitle = new JMenuItem("Open Subtitle");
		mnFile.add(mntmOpenSubtitle);

		mntmExit = new JMenuItem("Exit");
		mnFile.add(mntmExit);

		mntmOpenVideo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				MyMain.openVedio();
			}
		});

		mntmOpenSubtitle.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				MyMain.openSubtitle();
			}
		});

		mntmExit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				MyMain.exit();
			}
		});

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JPanel videoPanel = new JPanel();
		contentPane.add(videoPanel, BorderLayout.CENTER);
		videoPanel.setLayout(new BorderLayout(0, 0));

		playerComponent = new EmbeddedMediaPlayerComponent();
		Canvas videoSurface = playerComponent.getVideoSurface();
		videoSurface.addMouseListener(new MouseAdapter() {
				String btnText = ">";
				String btnText1 = "Full";
				Timer mouseTime;
				@Override
				public void mouseClicked(MouseEvent e) {
					if(e.getClickCount() == 1){
						mouseTime = new Timer(350,new ActionListener() {
							
							@Override
							public void actionPerformed(ActionEvent e) {
								// TODO Auto-generated method stub
								if (playButton.getText() == ">") {
									MyMain.play();
									btnText = "||";
									playButton.setText(btnText);
								} else {
									MyMain.pause();
									btnText = ">";
									playButton.setText(btnText);
								}
								mouseTime.stop();
							}
						});
						mouseTime.restart();
					}else if(e.getClickCount() == 2 && mouseTime.isRunning()){
						mouseTime.stop();
						if (FullScreenButton.getText() == "Full") {
							MyMain.fullScreen();
							btnText1 = "Small";
							FullScreenButton.setText(btnText1);
							
						} else {
							MyMain.originalScreen();
							btnText1 = "Full";
							FullScreenButton.setText(btnText1);
						}
					}
				}

		});
		videoPanel.add(playerComponent, BorderLayout.CENTER);
		

		panel = new JPanel();
		videoPanel.add(panel, BorderLayout.SOUTH);
		panel.setLayout(new BorderLayout(0, 0));

		controlPanel = new JPanel();
		panel.add(controlPanel);

		stopButton = new JButton("Stop");
		stopButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				MyMain.stop();
				playButton.setText(">");
			}
		});
		controlPanel.add(stopButton);

		playButton = new JButton(">");
		playButton.addMouseListener(new MouseAdapter() {
			String btnText = ">";
			@Override
			public void mouseClicked(MouseEvent e) {
				if (playButton.getText() == ">") {
					MyMain.play();
					btnText = "||";
					playButton.setText(btnText);
				} else {
					MyMain.pause();
					btnText = ">";
					playButton.setText(btnText);
				}

			}
		});
		
		backwordButton = new JButton("<<");
		backwordButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				MyMain.jumpTo((float)((progressBar.getPercentComplete() * progressBar.getWidth() - 5) / progressBar.getWidth()));
			}
		});
		controlPanel.add(backwordButton);
		controlPanel.add(playButton);

		volumControlerSlider = new JSlider();
		volumControlerSlider.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				volumControlerSlider.setValue((int)(e.getX() * ((float)volumControlerSlider.getMaximum() / volumControlerSlider.getWidth())));
			}
		});
		volumControlerSlider.setValue(100);
		volumControlerSlider.setMaximum(120);
		volumControlerSlider.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				MyMain.setVolum(volumControlerSlider.getValue());
			}
		});
		
		forwardButton = new JButton(">>");
		forwardButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				MyMain.jumpTo((float)(((progressBar.getPercentComplete() * progressBar.getWidth() + 15)) / progressBar.getWidth()));
			}
		});
		controlPanel.add(forwardButton);
		
		FullScreenButton = new JButton("Full");
		FullScreenButton.addMouseListener(new MouseAdapter() {
			String btnText = "Full";
			@Override
			public void mouseClicked(MouseEvent e) {
				if (FullScreenButton.getText() == "Full") {
					MyMain.fullScreen();
					btnText = "Small";
					FullScreenButton.setText(btnText);
				} else {
					MyMain.originalScreen();
					btnText = "Full";
					FullScreenButton.setText(btnText);
				}
				
			}
		});
		controlPanel.add(FullScreenButton);

		controlPanel.add(volumControlerSlider);

		progressBar = new JProgressBar();
		progressBar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int x = e.getX();
				MyMain.jumpTo(((float) x / progressBar.getWidth()));

			}
		});
		progressBar.setStringPainted(true);
		panel.add(progressBar, BorderLayout.NORTH);
	}

	// Get the video
	public EmbeddedMediaPlayer getMediaPlayer() {
		return playerComponent.getMediaPlayer();
	}

	public JProgressBar getProgressBar() {
		return progressBar;
	}
	
	public EmbeddedMediaPlayerComponent getPlayComponent(){
		return playerComponent;
	}
}
