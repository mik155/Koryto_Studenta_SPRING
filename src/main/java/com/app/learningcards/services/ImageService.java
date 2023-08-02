package com.app.learningcards.services;

import com.app.learningcards.models.recipe.Recipe;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

public class ImageService
{
    private static final String IMG_URI = "http://localhost:9000/api/v1/recipe/img/";
    private static final Path IMG_STORAGE_PATH = Paths.get("src/main/java/com/app/learningcards/img");

    public static String getImagePath(String imageName)
    {
        return IMG_URI + imageName;
    }
    public static String uploadImageToFileSystem(MultipartFile file)
    {
        String fileName = UUID.randomUUID() + ".jpg";
        Path filePath = Paths.get(IMG_STORAGE_PATH.toString(), fileName);
        try
        {
            file.transferTo(filePath);
        }
        catch(IOException e)
        {
            return "default.jpg";
        }

        return fileName;
    }

    public static byte [] downloadImageFromFileSystem(Path filePAth)
    {
        try
        {
            return Files.readAllBytes(filePAth);
        }
        catch(IOException e)
        {
            return null;
        }
    }

    public static byte [] downloadImageFromFileSystem(String fileName)
    {
        Path filePath = Paths.get(IMG_STORAGE_PATH.toString(), fileName);

        try
        {
            return Files.readAllBytes(filePath);
        }
        catch(IOException e)
        {
            return null;
        }
    }
}
