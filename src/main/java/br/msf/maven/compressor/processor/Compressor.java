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
package br.msf.maven.compressor.processor;

import br.msf.maven.compressor.CompressorSettings;
import br.msf.maven.utils.FileCopy;
import java.io.File;

/**
 * Interface for standard FileProcessors.
 * <p/>
 * @author Marcius da Silva da Fonseca (sf.marcius@gmail.com)
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
