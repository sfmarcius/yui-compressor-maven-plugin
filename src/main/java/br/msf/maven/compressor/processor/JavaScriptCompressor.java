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
import br.msf.maven.compressor.exception.YUICompressorException;
import java.io.File;
import java.io.Reader;
import java.io.Writer;
import org.apache.commons.io.IOCase;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.apache.commons.io.input.CharSequenceReader;
import org.apache.commons.io.output.StringBuilderWriter;

/**
 * Class that trims a javascript (*.js or *.json) using the yui compressor library.
 * <p/>
 * @author Marcius da Silva da Fonseca (sf.marcius@gmail.com)
 */
public class JavaScriptCompressor extends AbstractMinifier {

    protected static final String[] JAVASCRIPT_WILDCARDS = new String[]{"*.js", "*.json"};

    public Type getType() {
        return Type.JAVASCRPT;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public boolean accept(final File inputFile) {
        return inputFile != null && inputFile.isFile() && (new WildcardFileFilter(JAVASCRIPT_WILDCARDS, IOCase.INSENSITIVE)).accept(inputFile);
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
            com.yahoo.platform.yui.compressor.JavaScriptCompressor compressor = new com.yahoo.platform.yui.compressor.JavaScriptCompressor(reader, new MavenErrorReporter(settings.getLog(), settings.isShowJsWarnings()));

            final StringBuilder out = new StringBuilder(originalContent.length());
            writer = new StringBuilderWriter(out);
            try {
                compressor.compress(writer, LINE_BREAK_POS, false, settings.isVerbose(), true, false);
            } catch (Exception e) {
                throw new YUICompressorException("An Error has occurred while compressing javascript. Probably there is a bad practice on the source...", e);
            }
            writer.flush();
            return out;
        } finally {
            IOUtils.closeQuietly(reader);
            IOUtils.closeQuietly(writer);
        }
    }
}
