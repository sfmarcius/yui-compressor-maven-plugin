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

import br.msf.commons.util.ArgumentUtils;
import br.msf.commons.util.IOUtils;
import br.msf.maven.compressor.CompressorSettings;
import br.msf.maven.utils.FileCopy;

/**
 * Base class for FileProcessors.
 * <p/>
 * @author Marcius da Silva da Fonseca (mfonseca@ufsm.br)
 */
public abstract class AbstractMinifier implements Compressor {

    /**
     * Indicates the aproximate position where YuiCompressor must break line.
     * To trim all content as a single line, use -1.
     */
    protected static final int LINE_BREAK_POS = 120;

    /**
     * {@inheritDoc }
     */
    public CharSequence minify(final FileCopy fileCopy, final CompressorSettings settings) throws Exception {
        ArgumentUtils.rejectIfAnyNull(fileCopy, settings.getEncoding());
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
