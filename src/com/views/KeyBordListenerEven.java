package com.views;

import java.awt.AWTEvent;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import java.awt.event.KeyEvent;

import com.main.MyMain;

/**
 * Key board listener
 * @author ganyee
 *
 */
public class KeyBordListenerEven {

	public void keyBordListner(){
		Toolkit.getDefaultToolkit().addAWTEventListener(new AWTEventListener() {
			@Override
			public void eventDispatched(AWTEvent event) {
				// TODO Auto-generated method stub
				if(((KeyEvent)event).getID()==KeyEvent.KEY_PRESSED){
					switch (((KeyEvent)event).getKeyCode()) {
					case KeyEvent.VK_RIGHT:{
						int a = MyMain.getFrame().getVolumControlerSlider().getValue();
						MyMain.getFrame().getVolumControlerSlider().setValue(a);
						MyMain.forword((float)(((MyMain.getFrame().getProgressBar().getPercentComplete() * MyMain.getFrame().getProgressBar().getWidth() + 10)) / MyMain.getFrame().getProgressBar().getWidth()));
					}
						break;
					case KeyEvent.VK_LEFT:{
						MyMain.jumpTo((float)((MyMain.getFrame().getProgressBar().getPercentComplete() * MyMain.getFrame().getProgressBar().getWidth() - 5) / MyMain.getFrame().getProgressBar().getWidth()));
					}
						break;
					case KeyEvent.VK_ESCAPE:{
						if(!MyMain.getFrame().getMediaPlayer().isFullScreen())
							MyMain.fullScreen();
						else
							MyMain.originalScreen();
						
					}
						break;
					case KeyEvent.VK_UP:{
						MyMain.getFrame().getVolumControlerSlider().setValue(MyMain.getFrame().getVolumControlerSlider().getValue() + 1);
						MyMain.getControlFrame().getVolumControlerSlider().setValue(MyMain.getFrame().getVolumControlerSlider().getValue());
//						MyMain.getFrame().getVolumLabel().setText("" + MyMain.getFrame().getVolumControlerSlider().getValue());
					}
						break;
					case KeyEvent.VK_DOWN:
						MyMain.getFrame().getVolumControlerSlider().setValue(MyMain.getFrame().getVolumControlerSlider().getValue() - 1);
						MyMain.getControlFrame().getVolumControlerSlider().setValue(MyMain.getFrame().getVolumControlerSlider().getValue());
						break;
					case KeyEvent.VK_SPACE:{
						if(MyMain.getFrame().getMediaPlayer().isPlaying()){
							MyMain.pause();
							MyMain.getControlFrame().getPlayButton().setText(MyMain.getFrame().getPlayButton().getText());
						}
						else{
							MyMain.play();
							MyMain.getControlFrame().getPlayButton().setText(MyMain.getFrame().getPlayButton().getText());
						}
					}
						break;
					}
				}
			}
		}, AWTEvent.KEY_EVENT_MASK);
	}
}
