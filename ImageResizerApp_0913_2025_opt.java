// 代码生成时间: 2025-09-13 20:25:11
import com.dropwizard.Application;
import com.dropwizard.configuration.Environment;
import com.dropwizard.configuration.YamlConfigurationFactory;
import com.dropwizard.setup.Bootstrap;
import com.dropwizard.setup.Environment;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import io.dropwizard.views.ViewBundle;
import net.sourceforge.argparse4j.inf.Namespace;
import net.sourceforge.argparse4j.inf.Subparsers;
import net.sourceforge.argparse4j.ArgumentParsers;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ImageResizerApp extends Application<ImageResizerConfig> {

    public static void main(String[] args) throws Exception {
        new ImageResizerApp().run(args);
    }

    @Override
    public void initialize(Bootstrap<ImageResizerConfig> bootstrap) {
        // nothing to do here
    }

    @Override
    public void run(ImageResizerConfig configuration, Environment environment) {
        try {
            // Get the directory containing images to resize
            Path inputDir = Paths.get(configuration.getInputDirectory());
            // Get the resized image directory
            Path outputDir = Paths.get(configuration.getOutputDirectory());
            // Get the desired dimensions for resizing
            int width = configuration.getWidth();
            int height = configuration.getHeight();

            // Check if input and output directories exist
            if (!Files.exists(inputDir) || !Files.isDirectory(inputDir)) {
                throw new IllegalArgumentException("Input directory does not exist or is not a directory.");
            }
            if (!Files.exists(outputDir) || !Files.isDirectory(outputDir)) {
                throw new IllegalArgumentException("Output directory does not exist or is not a directory.");
            }

            // Process each image file in the input directory
            try (Stream<Path> paths = Files.walk(inputDir)) {
                List<Path> imageFiles = paths
                        .filter(Files::isRegularFile)
                        .filter(p -> p.toString().endsWith(".jpg") || p.toString().endsWith(".png"))
                        .collect(Collectors.toList());

                for (Path imageFile : imageFiles) {
                    BufferedImage originalImage = ImageIO.read(imageFile.toFile());
                    BufferedImage resizedImage = new BufferedImage(width, height, originalImage.getType());
                    resizedImage.getGraphics().drawImage(originalImage.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH), 0, 0, null);
                    ImageIO.write(resizedImage, "png", new File(outputDir.resolve(imageFile.getFileName()).toFile()));
                }
            } catch (IOException e) {
                throw new RuntimeException("Error reading or writing images", e);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class ImageResizerConfig extends Configuration {
    // Configuration for input and output directories and image dimensions
    private String inputDirectory;
    private String outputDirectory;
    private int width;
    private int height;

    public String getInputDirectory() {
        return inputDirectory;
    }

    public void setInputDirectory(String inputDirectory) {
        this.inputDirectory = inputDirectory;
    }

    public String getOutputDirectory() {
        return outputDirectory;
    }

    public void setOutputDirectory(String outputDirectory) {
        this.outputDirectory = outputDirectory;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
