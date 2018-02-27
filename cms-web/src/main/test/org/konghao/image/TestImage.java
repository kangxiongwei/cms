package org.konghao.image;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.Thumbnails.Builder;
import net.coobird.thumbnailator.geometry.Positions;
import net.coobird.thumbnailator.resizers.configurations.ScalingMode;

import org.junit.Test;

public class TestImage {

	@Test
	public void test01() {
		try {
			Thumbnails.of(new File("e:/br/01.jpg"))
			.size(330, 10000)
			.scalingMode(ScalingMode.BILINEAR)
			.toFile(new File("e:/br/01_1.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void test02() {
		try {
			Thumbnails.of(new File("e:/br/01.jpg"))
			.scale(0.3f)
			.toFile(new File("e:/br/01_1.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void test03() {
		try {
			BufferedImage bi = Thumbnails.of(new File("e:/br/01.jpg"))
			.scale(0.3f)
			.asBufferedImage();
			ImageIO.write(bi, "jpg",new FileOutputStream("e:/br/011.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void test04() {
		try {
			BufferedImage bi = Thumbnails.of(new File("e:/br/01.jpg"))
					.scale(0.2f)
			.asBufferedImage();
			Thumbnails.of(bi)
				.sourceRegion(Positions.CENTER, 300, 300)
				.scale(1.0f)
				.toFile(new File("e:/br/033.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void test05() {
		try {
//			BufferedImage bi = Thumbnails.of(new FileInputStream("e:/br/02.jpg")).scale(1.0f).asBufferedImage();
			BufferedImage bi = ImageIO.read(new FileInputStream("e:/br/02.jpg"));
			System.out.println(bi.getWidth()+","+bi.getHeight());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void test06() {
		try {
			int width = 900;
			int thumb = 100;
			BufferedImage bi = ImageIO.read(new File("e:/br/04.jpg"));
			Builder<BufferedImage> bf = Thumbnails.of(bi);
			bf.scale((double)width/(double)bi.getWidth())
			.toFile(new File("e:/br/04_1.jpg"));
			BufferedImage bi2 = Thumbnails.of(bi).scale((thumb*1.2)/bi.getWidth()).asBufferedImage();
			Thumbnails.of(bi2).scale(1.0f).sourceRegion(Positions.CENTER,100, 80).toFile(new File("e:/br/04_2.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
