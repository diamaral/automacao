package automacao.utils;

import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Base64;

import javax.imageio.ImageIO;

import org.openqa.selenium.WebDriver;

import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

public class ScreenShotUtils {

	private static String reportsPath = System.getProperty("user.dir")+"/report/";
	
	public static void captureEvidence(String test, WebDriver driver) {
		BufferedImage image = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(500)).takeScreenshot(driver).getImage();
		try {
			ImageIO.write(image, "PNG", new File(reportsPath+test+".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String captureImage(WebDriver driver) throws IOException {
		RenderedImage image = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(500)).takeScreenshot(driver).getImage();
		return imgToBase64String(image, "PNG");
	}
	
	public static String imgToBase64String(final RenderedImage img, final String formatName) throws IOException{
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		ImageIO.write(img, formatName, os);
		return "data:image/png;base64," + Base64.getEncoder().encodeToString(os.toByteArray());
	}
}
