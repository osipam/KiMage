package pl.edu.uj.kimage.awt;

import java.awt.image.BufferedImage;
import java.util.Random;
import org.junit.Test;
import pl.edu.uj.kimage.plugin.model.Image;

import static org.assertj.core.api.Assertions.assertThat;
import pl.edu.uj.kimage.plugin.model.Color;


public class ImageConverterTest {

    @Test
    public void converterFromBufferedImageToImageReturnsValidValues() {
        // given
        int imageWidth = 4;
        int imageHeight = 4;
        ImageConverter converter = new ImageConverter();
        BufferedImage bufferedImage = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB);
        Random generator = new Random();

        for(int i = 0; i < imageWidth; i++){
            for(int j =0; j < imageHeight; j++){
                bufferedImage.setRGB(i, j, generator.nextInt(100));
            }
        }
        // when
        Image image = converter.toImage(bufferedImage);

        // then
        for(int i = 0; i < imageWidth; i++){
            for(int j =0; j < imageHeight; j++){
                int argb = bufferedImage.getRGB(i, j);

                assertThat((argb >> 16) & 0x000000FF).isEqualTo(image.getColor(i, j).getRed());
                assertThat((argb >> 8) & 0x000000FF).isEqualTo(image.getColor(i, j).getGreen());
                assertThat((argb) & 0x000000FF).isEqualTo(image.getColor(i, j).getBlue());
                assertThat((argb >> 24) & 0x000000FF).isEqualTo(image.getColor(i, j).getAlpha());
            }
        }
    }

    @Test
    public void converterFromImageToBufferedImageReturnsValidValues(){
        // given
        int imageWidth = 4;
        int imageHeight = 4;
        ImageConverter converter = new ImageConverter();
        Color[] tab = new Color[imageWidth*imageHeight];
        Random generator = new Random();

        for(int i = 0; i < imageWidth; i++){
            for(int j =0; j < imageHeight; j++){
                int index = j * imageWidth + i;
                tab[index] = new Color(generator.nextInt(255),generator.nextInt(255),generator.nextInt(255),generator.nextInt(255));
            }
        }
        Image image = new Image(imageWidth, imageHeight, tab);

        // when
        BufferedImage output = converter.toBufferedImage(image);

        // then
        for(int i = 0; i < imageWidth; i++){
            for(int j =0; j < imageHeight; j++){
                int argb = output.getRGB(i, j);

                assertThat((argb >> 16) & 0x000000FF).isEqualTo(image.getColor(i, j).getRed());
                assertThat((argb >> 8) & 0x000000FF).isEqualTo(image.getColor(i, j).getGreen());
                assertThat((argb) & 0x000000FF).isEqualTo(image.getColor(i, j).getBlue());
                assertThat((argb >> 24) & 0x000000FF).isEqualTo(image.getColor(i, j).getAlpha());
            }
        }
    }

}
