/*
 * yui-compressor-maven-plugin - Copyright (C) 2009-2013 Marcius da Silva da Fonseca. All rights reserved.
 * 
 * This library is free software; you can redistribute it and/or modify it under the terms of the GNU Lesser General
 * Public License as published by the Free Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * 
 * This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU Lesser General Public License along with this library; if not, write to
 * the Free Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.
 */
package br.msf.maven.utils;

import br.msf.commons.util.CharSequenceUtils;
import br.msf.commons.util.IOUtils;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

/**
 * Class that contains the original input file and the target processed file
 * descriptors.
 * <p/>
 * @author Marcius da Silva da Fonseca (sf.marcius@gmail.com)
 */
public class FileCopy {

    private final File baseDir;
    private final File input;
    private final File output;

    public FileCopy(final String relativeFileName, final File inputDir, final File outputDir) {
        if (CharSequenceUtils.isBlankOrNull(relativeFileName) || !IOUtils.isDirectory(inputDir) || !IOUtils.isDirectory(outputDir)) {
            throw new IllegalArgumentException("Invalid argument");
        }
        this.baseDir = inputDir;
        this.input = new File(inputDir, relativeFileName);
        this.output = new File(outputDir, relativeFileName);
    }

    public File getBaseDir() {
        return baseDir;
    }

    public File getInput() {
        return input;
    }

    public File getOutput() {
        return output;
    }

    public StringBuilder readContents(final Charset encoding) throws IOException {
        BufferedReader reader = null;
        try {
            final StringBuilder fileData = new StringBuilder(1000);
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(getInput()), encoding));
            char[] buffer = new char[16384]; // 16k chars buffer
            int nRead;
            while ((nRead = reader.read(buffer)) != -1) {
                String readData = String.valueOf(buffer, 0, nRead);
                fileData.append(readData);
                buffer = new char[16384];
            }
            return fileData;
        } finally {
            IOUtils.closeQuietly(reader);
        }
    }
}
