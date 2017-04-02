package utility;

import static org.lwjgl.opengl.GL11.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;

import org.lwjgl.BufferUtils;

import de.matthiasmann.twl.utils.PNGDecoder;
import de.matthiasmann.twl.utils.PNGDecoder.Format;

public class TextureLoader {
	
	public static int loadTexture(String location, int target, int filtering) throws FileNotFoundException, IOException {
		//Initialises the argument 'texture' as an opengl texture, and binds it to GL_TEXTURE_2D
		int texture = glGenTextures();
		glBindTexture(target, texture);
		
		//Initialises the decoder using the file at 'location'
		PNGDecoder decoder = new PNGDecoder(new FileInputStream(location));
		
		//Initialises the byte buffer to store the color data of the pixels
		ByteBuffer pixelData = BufferUtils.createByteBuffer(decoder.getWidth() * decoder.getHeight() * 4);
		
		//Decode the png and store pixel data in the buffer 'pixelData' and make the buffer readible for opengl
		decoder.decodeFlipped(pixelData, decoder.getWidth() * 4, Format.RGBA);
		pixelData.flip();
		
		//Apply the texture data to 'texture'
		glTexParameteri(target, GL_TEXTURE_MAG_FILTER, filtering);
		glTexParameteri(target, GL_TEXTURE_MIN_FILTER, filtering);
		
		glTexImage2D(target, 0, GL_RGBA, decoder.getWidth(), decoder.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, pixelData);
		
		//unbind the texture to GL_TEXTURE_2D
		glBindTexture(target, 0);
		
		return texture;		
	}
	
	public static int loadTexture(ByteBuffer buffer, int width, int height, int target, int filtering) throws FileNotFoundException, IOException {
		//Initialises the argument 'texture' as an opengl texture, and binds it to GL_TEXTURE_2D
		int texture = glGenTextures();
		glBindTexture(target, texture);
		
		//Apply the texture data to 'texture'
		glTexParameteri(target, GL_TEXTURE_MAG_FILTER, filtering);
		glTexParameteri(target, GL_TEXTURE_MIN_FILTER, filtering);
		
		glTexImage2D(target, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
		
		//unbind the texture to GL_TEXTURE_2D
		glBindTexture(target, 0);

		return texture;
	}
}
