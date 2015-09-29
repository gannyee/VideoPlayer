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

public class DipalyFram extends JFrame {

	private JPanel contentPane;

	EmbeddedMediaPlayerComponent playerComponent;
	private JPanel panel;
	private JButton stopButton;
	private JButton playButton;
	// private JButton pauseButton;
	private JPanel controlPanel;
	private JProgressBar progressBar;
	private JSlider VolumControlerSlider;
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
				String btnText = "Play";
				@Override
				public void mouseClicked(MouseEvent e) {
					super.mouseClicked(e);
					if(e.getClickCount() == 1){
//						MyMain.pause();
						if (btnText == "Play") {
							MyMain.play();
							btnText = "Pause";
							playButton.setText(btnText);
						} else {
							MyMain.pause();
							btnText = "Play";
							playButton.setText(btnText);
						}
						
					}else if(e.getClickCount() == 2){
						playerComponent.getMediaPlayer().toggleFullScreen();
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
				
			}
		});
		controlPanel.add(stopButton);

		playButton = new JButton("Play");
		playButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		playButton.addMouseListener(new MouseAdapter() {
			String btnText = "Play";

			@Override
			public void mouseClicked(MouseEvent e) {
				if (btnText == "Play") {
					MyMain.play();
					btnText = "Pause";
					playButton.setText(btnText);
				} else {
					MyMain.pause();
					btnText = "Play";
					playButton.setText(btnText);
				}

			}
		});
		
		backwordButton = new JButton("<<");
		backwordButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				MyMain.jumpTo((float)(((progressBar.getPercentComplete() * progressBar.getWidth() - 5)) / progressBar.getWidth()));
			}
		});
		controlPanel.add(backwordButton);
		controlPanel.add(playButton);

		/*
		 * pauseButton = new JButton("Pause"); pauseButton.addActionListener(new
		 * ActionListener() { public void actionPerformed(ActionEvent e) { } });
		 * pauseButton.addMouseListener(new MouseAdapter() {
		 * 
		 * @Override public void mouseClicked(MouseEvent e) { MyMain.pause(); }
		 * }); controlPanel.add(pauseButton);
		 */

		VolumControlerSlider = new JSlider();
		VolumControlerSlider.setValue(100);
		VolumControlerSlider.setMaximum(120);
		VolumControlerSlider.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				MyMain.setVolum(VolumControlerSlider.getValue());
			}
		});
		
		forwardButton = new JButton(">>");
		forwardButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//System.out.println("curr " + playerComponent.getMediaPlayer().getTime() + "///: " + progressBar.getWidth());
				//System.out.println("curr percent: " + progressBar.getPercentComplete() + "other: " + progressBar.getPercentComplete()* progressBar.getWidth());
				MyMain.jumpTo((float)((10 + progressBar.getPercentComplete() * progressBar.getWidth()) / progressBar.getWidth()));
			}
		});
		controlPanel.add(forwardButton);
		
		FullScreenButton = new JButton("Full");
		controlPanel.add(FullScreenButton);

		controlPanel.add(VolumControlerSlider);

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
