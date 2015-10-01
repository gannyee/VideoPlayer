package com.views;

import com.main.MyMain;

import uk.co.caprica.vlcj.binding.internal.libvlc_logo_position_e;
import uk.co.caprica.vlcj.player.Logo;

/**
 * A test for add logo to video
 * @author ganyee
 *
 */
public class MyLogo {
	private Logo logo = Logo.logo()
			.file("picture/logo.png")
			.position(libvlc_logo_position_e.top_left)
			.opacity(0.2f)
			.enable();

	public Logo getLogo() {
		
		return logo;
	}
	
	
}
