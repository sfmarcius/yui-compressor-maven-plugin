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
 * @author Marcius da Silva da Fonseca (mfonseca@ufsm.br)
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
