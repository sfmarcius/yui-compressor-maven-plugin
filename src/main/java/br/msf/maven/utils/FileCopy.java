/*
 * Copyright 2001-2005 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
 * @author Marcius da Silva da Fonseca (mfonseca@ufsm.br)
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
