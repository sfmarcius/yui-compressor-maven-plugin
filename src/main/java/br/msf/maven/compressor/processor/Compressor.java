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
package br.msf.maven.compressor.processor;

import br.msf.maven.compressor.CompressorSettings;
import br.msf.maven.utils.FileCopy;
import java.io.File;

/**
 * Interface for standard FileProcessors.
 * <p/>
 * @author Marcius da Silva da Fonseca (mfonseca@ufsm.br)
 */
public interface Compressor {

    public Type getType();

    /**
     * Returns true if this FileProcessor is designed to process the guiven file.
     * <p/>
     * @param inputFile The file you want to know if this FileProcessor can handle.
     * @return
     */
    public boolean accept(final File inputFile);

    /**
     * Reads the <tt>fileCopy.input</tt> contents, apply a transformation and writes the result on
     * <tt>fileCopy.output</tt>.
     * <p/>
     * Both read and write uses the given encoding.
     * <p/>
     * @param fileCopy Object containing the input and output file descriptors.
     * @param settings Settings to be used on the minify proccess.
     * @return The minified content.
     * @throws Exception If anything go wrong.
     */
    public CharSequence minify(final FileCopy fileCopy, final CompressorSettings settings) throws Exception;

    public enum Type {

        CSS,
        JAVASCRPT,
        HTML,
        JSP;

        public boolean isCss() {
            return CSS.equals(this);
        }

        public boolean isJavascrpt() {
            return JAVASCRPT.equals(this);
        }

        public boolean isHtml() {
            return HTML.equals(this);
        }

        public boolean isJsp() {
            return JSP.equals(this);
        }
    }
}
