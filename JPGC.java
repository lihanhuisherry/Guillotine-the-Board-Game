package Indy;

import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

/**
 * This class is a side tool to compress all jpg into png; otherwise we would 
 * get a java outofMemory exception.
 */
public class JPGC {

	private final static int FACT = 8;
	private final static int FACTS = FACT * FACT;

	public JPGC(String filePath) {
		try {
			File input = Paths.get(filePath + "jpg").toFile();
			BufferedImage img = ImageIO.read(input);
			int SigmaY = img.getHeight() / FACT;
			int SigmaX = img.getWidth() / FACT;
			BufferedImage output = new BufferedImage(SigmaX, SigmaY, BufferedImage.TYPE_INT_RGB);
			for (int x = 0; x < SigmaX; x++) {
				for (int y = 0; y < SigmaY; y++) {
					int a = 0, r = 0, g = 0, b = 0;
					for (int i = 0; i < FACT; i++) {
						for (int j = 0; j < FACT; j++) {
							int p = img.getRGB(i + FACT * x, j + FACT * y);
							a += ((p >> 24) & 0xff);
							r += ((p >> 16) & 0xff);
							g += ((p >> 8) & 0xff);
							b += (p & 0xff);
						}
					}
					a /= FACTS;
					r /= FACTS;
					g /= FACTS;
					b /= FACTS;
					output.setRGB(x, y, (a << 24) | (r << 16) | (g << 8) | b);
				}
			}
			ImageIO.write(output, "png", new File(filePath + "png"));
		} catch (Exception e) {
			System.out.println(filePath);
			e.printStackTrace();
			System.exit(1);
		}
	}

	public static void main(String[] args) {
		for (String arg : args) {
			System.out.println();
			new JPGC(arg.substring(0, arg.length() - 3));
		}
	}

}
