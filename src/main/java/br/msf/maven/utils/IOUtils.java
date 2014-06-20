/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.msf.maven.utils;

import java.io.File;
import java.nio.charset.Charset;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author mfonseca
 */
public class IOUtils extends org.apache.commons.io.IOUtils {
    
 /**
     * O logger para acompanhar eventos da classe.
     */
    private static final Logger LOGGER = Logger.getLogger(IOUtils.class.getName());
    public static final Charset ISO_8859_1 = Charset.forName("ISO-8859-1");
    public static final Charset UTF_8 = Charset.forName("UTF-8");
    
    public static boolean isFile(final File file) {
        return file != null && file.isFile();
    }

    public static boolean isNotFile(final File file) {
        return file == null || !file.isFile();
    }

    public static boolean exists(final File file) {
        return file != null && file.exists();
    }

    public static boolean notExists(final File file) {
        return file == null || !file.exists();
    }

    public static boolean isDirectory(final File file) {
        return file != null && file.isDirectory();
    }

    public static boolean isNotDirectory(final File file) {
        return file == null || !file.isDirectory();
    }

    public static boolean isHidden(final File file) {
        return file != null && file.isHidden();
    }

    public static boolean isNotHidden(final File file) {
        return file == null || !file.isHidden();
    }

    public static boolean isAbsolute(final File file) {
        return file != null && file.isAbsolute();
    }

    public static boolean isNotAbsolute(final File file) {
        return file == null || !file.isAbsolute();
    }

    public static boolean makeDirs(final File file) {
        if (notExists(file)) {
            return file.mkdirs();
        }
        return false;
    }

    public static Charset getCharset(final String charsetName) {
        if (StringUtils.isBlank(charsetName)) {
            LOGGER.log(Level.WARNING, "Charset name is blank. Assuming default charset!");
            return Charset.defaultCharset();
        }
        return Charset.forName(charsetName);
    }

    public static String getRelativePath(final File base, final File child) {
        if (base == null || child == null) {
            throw new IllegalArgumentException("Cannot be null.");
        }
        return base.toURI().relativize(child.toURI()).getPath();
    }
}
