package com.main;

import java.awt.EventQueue;
import java.io.File;
import java.nio.file.Path;

import javax.swing.JFileChooser;
import javax.swing.SwingWorker;

import com.sun.jna.NativeLibrary;
import com.views.DipalyFram;

import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.player.embedded.FullScreenStrategy;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;

public class MyMain {

//	private static final String NATIVE_LIBRARY_SEARCH_PATH = "D:\\VideoLAN\\VLC";
	private static DipalyFram frame;
	private static String filePath;
	
	public static void main(String[] args) {
	
		//Decide the platform
		if(RuntimeUtil.isWindows())
			filePath = "D:\\VideoLAN\\VLC";
		else if(RuntimeUtil.isMac())
			filePath =  "/Applications/VLC.app/Contents/MacOS/lib";
		else if(RuntimeUtil.isNix())
			filePath = "/home/linux/vlc/install/lib";
		
		
		 NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(), filePath);
	     System.out.println(LibVlc.INSTANCE.libvlc_get_version());
	      
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new DipalyFram();
					frame.setVisible(true);
					String[] optionDecode = {"--subsdec-encoding=GB18030"};
//					frame.getMediaPlayer().playMedia("D:\\Downloads\\ELECTROSHOCK - ESMA 2011-SD.mp4",optionDecode);
					frame.getMediaPlayer().prepareMedia("D:\\Downloads\\ELECTROSHOCK - ESMA 2011-SD.mp4",optionDecode);
					new SwingWorker<String, Integer>() {

						@Override
						protected String doInBackground() throws Exception {
							while(true){
								long totalTime = frame.getMediaPlayer().getLength();
								long currentTime = frame.getMediaPlayer().getTime();
								float percent = (float)currentTime / totalTime;
								publish((int)(percent * 100));
								Thread.sleep(100);
							}
						}
						protected void process(java.util.List<Integer> chunks) {
							for(int v : chunks){
								frame.getProgressBar().setValue(v);
							}
						};
					}.execute();
					frame.getMediaPlayer().toggleFullScreen();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public static void play(){
		frame.getMediaPlayer().play();
	}
	
	public static void pause(){
		frame.getMediaPlayer().pause();
	}
	
	public static void stop(){
		frame.getMediaPlayer().stop();
	}
	
	public static void forword(float to){
		frame.getMediaPlayer().setTime((long) (to * frame.getMediaPlayer().getLength()));
	}
	
	public static void backword(){
		frame.getPlayComponent().backward(frame.getMediaPlayer());
	}
	
	
	public static void jumpTo(float to){
		frame.getMediaPlayer().setTime((long) (to * frame.getMediaPlayer().getLength()));
	}
	
	public static void setVolum(int v){
		frame.getMediaPlayer().setVolume(v);
	}
	
	public static void openVedio(){
		JFileChooser chooser = new JFileChooser();
		int v = chooser.showOpenDialog(null);
		if(v == JFileChooser.APPROVE_OPTION){
			File file = chooser.getSelectedFile();
			frame.getMediaPlayer().playMedia(file.getAbsolutePath());
		}
	}
	
	public static void openSubtitle(){
		JFileChooser chooser = new JFileChooser();
		int v = chooser.showOpenDialog(null);
		if(v == JFileChooser.APPROVE_OPTION){
			File file = chooser.getSelectedFile();
			frame.getMediaPlayer().setSubTitleFile(file);
		}
	}
	
	public static void exit(){
		frame.getMediaPlayer().release();
		System.exit(0);
	}
	
	public static void FullScreen(){
		
	}
}
