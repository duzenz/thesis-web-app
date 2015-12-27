package com.duzenz.recommender.components;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;

import org.springframework.stereotype.Component;

@Component
public class Util {

    public void deleteFile(String path) {
        File file = new File(path);
        try {
            Files.delete(file.toPath());
            System.out.println("File in path : " + path + " deleted");
        } catch (NoSuchFileException x) {
            System.err.format("%s: no such" + " file or directory%n", path);
        } catch (DirectoryNotEmptyException x) {
            System.err.format("%s not empty%n", path);
        } catch (IOException x) {
            System.err.println(x);
        }
    }

}