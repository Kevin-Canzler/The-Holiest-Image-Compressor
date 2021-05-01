import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.SampleModel;
import java.awt.image.WritableRaster;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import javax.imageio.ImageIO;

public class Main {
	public static void main(String[] args) {
		
	}
	
	public static byte[] readImage(String Path) {
		BufferedImage bufI = null;
		try {
			bufI = ImageIO.read(new ByteArrayInputStream(Files.readAllBytes(Paths.get(Path))));
		} catch (IOException e) {
			e.printStackTrace();
		}

		byte[] actualPixels = ((DataBufferByte)bufI.getRaster().getDataBuffer()).getData();
		int tempInt = 0;
		for(int i = 0; i < bufI.getWidth() * bufI.getHeight(); i++) {
			byte tempRed = actualPixels[tempInt+2];
			actualPixels[tempInt+2] = actualPixels[tempInt];
			actualPixels[tempInt] = tempRed;
			tempInt += 3;
		}
		return actualPixels;
	}
	
	public static int returnColorInt(byte red, byte green, byte blue) {
		return (red&0xFF<<16 + green&0xFF<<8 + blue)|0xFF000000;
	}
}