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
 * @author Marcius da Silva da Fonseca (mfonseca@ufsm.br)
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
