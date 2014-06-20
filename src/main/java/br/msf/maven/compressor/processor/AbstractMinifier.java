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
import br.msf.maven.utils.IOUtils;

/**
 * Base class for FileProcessors.
 * <p/>
 * @author Marcius da Silva da Fonseca (sf.marcius@gmail.com)
 */
public abstract class AbstractMinifier implements Compressor {

    /**
     * Indicates the aproximate position where YuiCompressor must break line.
     * To trim all content as a single line, use -1.
     */
    protected static final int LINE_BREAK_POS = 120;

    public CharSequence minify(final FileCopy fileCopy, final CompressorSettings settings) throws Exception {
        if (fileCopy == null || settings.getEncoding() == null) {
            throw new IllegalArgumentException("Cannot be null.");
        }
        if (!accept(fileCopy.getInput())) {
            return null;
        }
        // read original file contents
        final StringBuilder contents = fileCopy.readContents(settings.getEncoding());
        if (settings.isVerbose()) {
            settings.getLog().info("Compressing <" + IOUtils.getRelativePath(fileCopy.getBaseDir(), fileCopy.getInput()) + ">");
        }
        return proccessMinify(contents, settings);
    }

    /**
     * Do the minification process.
     *
     * @param originalContent The original content of the file to be minified.
     * @param settings        Settings to be used on the minify proccess.
     * @return The minified content.
     * @throws Exception If anything go wrong.
     */
    protected abstract CharSequence proccessMinify(final CharSequence originalContent, final CompressorSettings settings) throws Exception;
}
