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

import br.msf.commons.util.IOUtils;
import br.msf.maven.compressor.CompressorSettings;
import java.io.File;
import java.io.Reader;
import java.io.Writer;
import org.apache.commons.io.IOCase;
import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.apache.commons.io.input.CharSequenceReader;
import org.apache.commons.io.output.StringBuilderWriter;

/**
 * Class that trims a css (*.css) using the yui compressor library.
 * <p/>
 * @author Marcius da Silva da Fonseca (sf.marcius@gmail.com)
 */
public class CssCompressor extends AbstractMinifier {

    protected static final String[] CSS_WILDCARDS = new String[]{"*.css"};

    public Type getType() {
        return Type.CSS;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public boolean accept(final File inputFile) {
        return IOUtils.isFile(inputFile) && (new WildcardFileFilter(CSS_WILDCARDS, IOCase.INSENSITIVE)).accept(inputFile);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    protected CharSequence proccessMinify(final CharSequence originalContent, final CompressorSettings settings) throws Exception {
        Reader reader = null;
        Writer writer = null;

        try {
            reader = new CharSequenceReader(originalContent);
            com.yahoo.platform.yui.compressor.CssCompressor compressor = new com.yahoo.platform.yui.compressor.CssCompressor(reader);

            final StringBuilder out = new StringBuilder(originalContent.length());
            writer = new StringBuilderWriter(out);
            compressor.compress(writer, LINE_BREAK_POS);
            writer.flush();
            return out;
        } finally {
            IOUtils.closeQuietly(reader);
            IOUtils.closeQuietly(writer);
        }
    }
}
