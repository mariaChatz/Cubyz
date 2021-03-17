package io.cubyz;

import java.nio.charset.Charset;

import io.cubyz.api.Side;
import io.cubyz.world.LocalWorld;

/**
 * A set of constant like version or math.
 */

public class Constants {
	
	//world stuff
	public static LocalWorld world;
	public static Class<?> chunkProvider;
	public static boolean multiplayer = false;
	
	public static final int defaultPort = 3201;

	public static final String CHARSET_NAME = "UTF-8";
	public static final Charset CHARSET = Charset.forName(CHARSET_NAME);
	
	public static final String GAME_BUILD_TYPE = "ALPHA";
	
	// WARNING! Both brand name and version cannot contain ';' inside!
	public static final String GAME_VERSION = "0.7.0";
	public static final int GAME_PROTOCOL_VERSION = 1;
	public static final String GAME_BRAND = "cubyz";
	
	/**float math constants*/
	public static final float	PI = (float)Math.PI,
								PI_HALF = PI/2;
	
	
	static Side currentSide = null;
	
	public static Side getGameSide() {
		return currentSide;
	}
	
	public static void setGameSide(Side side) {
		currentSide = side;
	}
	
}
