package com.drawing;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureData;
import com.jogamp.opengl.util.texture.TextureIO;


public class GTextureUtil {

	public static Texture loadSimpleTexture(String name) {
		String texPath = System.getProperty("user.dir") + name;

		try {
			File file = new File(texPath);
			Texture t = TextureIO.newTexture(file, true);
			return t;
		} catch (IOException exc) {
			System.out.println("Could not load " + texPath);
			// exc.printStackTrace();
			System.exit(1);
		}

		// to make the compiler happy
		return null;
		// texture = t.getTextureObject(gl);
	}

	public static Texture loadBufferedTexture(String name, String type) {
		String texPath = System.getProperty("user.dir") + name;
		try {
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			ImageIO.write(ImageIO.read(new File(texPath)), "png", os);
			InputStream fis = new ByteArrayInputStream(os.toByteArray());
			return TextureIO.newTexture(fis, true, TextureIO.PNG);
		} catch (IOException exc) {
			System.out.println("Could not load " + texPath);
			// exc.printStackTrace();
			System.exit(1);
		}

		// to make the compiler happy
		return null;
	}

	public static Texture loadTextureProjectDir(final GL2 gl, String name, String type) {

		// the folders should be placed in the directory of the project
		String texPath = System.getProperty("user.dir") + name;

		try {
			File initialFile = new File(texPath); // create a file
			// convert it into stream
			InputStream stream = new FileInputStream(initialFile);


			GLProfile profile = gl.getGLProfile();
			// load texture data from the input stream
			TextureData data = null;
			
			if (type.equalsIgnoreCase("png") == true)
				data = TextureIO.newTextureData(profile, stream, true, TextureIO.PNG);
			else if (type.equalsIgnoreCase("jpg") == true)
				data = TextureIO.newTextureData(profile, stream, true, TextureIO.JPG);

			else if (type.equalsIgnoreCase("tga") == true)
				data = TextureIO.newTextureData(profile, stream, true, TextureIO.TGA);

			// return the texture object
			return TextureIO.newTexture(data);

		} catch (IOException exc) {
			System.out.println("Could not load " + texPath);
			// exc.printStackTrace();
			System.exit(1);
		}

		// to make the compiler happy
		return null;
	}

	// this method loads a texture specified in the filename from the project
	// directory
	public static Texture loadTextureProjectDir(String name, String fileType) {

		// the folders should be placed in the directory of the project
		String texPath = System.getProperty("user.dir") + name;

		try {
			File initialFile = new File(texPath); // create a file
			// convert it into stream
			InputStream stream = new FileInputStream(initialFile);
			// load texture data from the input stream
			TextureData data = TextureIO.newTextureData(GLProfile.get(GLProfile.GL2), stream, true, fileType);
			// return the texture object
			return TextureIO.newTexture(data);

		} catch (IOException exc) {
			System.out.println("Could not load " + texPath);
			// exc.printStackTrace();
			System.exit(1);
		}

		// to make the compiler happy
		return null;
	}

}
