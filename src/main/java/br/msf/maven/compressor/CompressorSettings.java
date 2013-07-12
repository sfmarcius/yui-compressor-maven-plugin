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
package br.msf.maven.compressor;

import br.msf.commons.util.CharSequenceUtils;
import java.io.File;
import java.nio.charset.Charset;
import java.util.Arrays;
import org.apache.maven.plugin.logging.Log;

/**
 * Class that acts like a FileProcessor Factory
 * <p/>
 * @author Marcius da Silva da Fonseca (mfonseca@ufsm.br)
 */
public class CompressorSettings {

    private File sourceDirectory;
    private File outputDirectory;
    private String[] includes;
    private String[] excludes;
    private Charset encoding;
    private String minifiedSuffix;
    private File jsConcatFile;
    private File cssConcatFile;
    private boolean writeIndividualFiles;
    private boolean compress;
    private boolean showJsWarnings;
    private boolean verbose;
    private Log log;

    public File getSourceDirectory() {
        return sourceDirectory;
    }

    public void setSourceDirectory(final File sourceDirectory) {
        this.sourceDirectory = sourceDirectory;
    }

    public File getOutputDirectory() {
        return outputDirectory;
    }

    public void setOutputDirectory(final File outputDirectory) {
        this.outputDirectory = outputDirectory;
    }

    public String[] getIncludes() {
        return includes;
    }

    public void setIncludes(final String[] includes) {
        this.includes = includes;
    }

    public String[] getExcludes() {
        return excludes;
    }

    public void setExcludes(final String[] excludes) {
        this.excludes = excludes;
    }

    public Charset getEncoding() {
        return encoding;
    }

    public void setEncoding(final Charset encoding) {
        this.encoding = encoding;
    }

    public String getMinifiedSuffix() {
        return minifiedSuffix;
    }

    public void setMinifiedSuffix(final String minifiedSuffix) {
        this.minifiedSuffix = minifiedSuffix;
    }

    public File getJsConcatFile() {
        return jsConcatFile;
    }

    public void setJsConcatFile(final File jsConcatFile) {
        this.jsConcatFile = jsConcatFile;
    }

    public File getCssConcatFile() {
        return cssConcatFile;
    }

    public void setCssConcatFile(final File cssConcatFile) {
        this.cssConcatFile = cssConcatFile;
    }

    public boolean isWriteIndividualFiles() {
        return writeIndividualFiles;
    }

    public void setWriteIndividualFiles(final boolean writeIndividualFiles) {
        this.writeIndividualFiles = writeIndividualFiles;
    }

    public boolean isCompress() {
        return compress;
    }

    public void setCompress(final boolean compress) {
        this.compress = compress;
    }

    public boolean isShowJsWarnings() {
        return showJsWarnings;
    }

    public void setShowJsWarnings(boolean showJsWarnings) {
        this.showJsWarnings = showJsWarnings;
    }

    public boolean isVerbose() {
        return verbose;
    }

    public void setVerbose(final boolean verbose) {
        this.verbose = verbose;
    }

    public Log getLog() {
        return log;
    }

    public void setLog(final Log log) {
        this.log = log;
    }

    public boolean isMinifiedSuffixed() {
        return CharSequenceUtils.isNotBlank(getMinifiedSuffix());
    }

    public boolean isJsConcat() {
        return getJsConcatFile() != null;
    }

    public boolean isCssConcat() {
        return getCssConcatFile() != null;
    }

    public void logSettings() {
        if (this.isVerbose()) {
            getLog().info("Settings................................... ");
            getLog().info("Encoding: " + getEncoding().displayName());
            getLog().info("SourceDirectory: " + getSourceDirectory());
            getLog().info("OutputDirectory: " + getOutputDirectory());
            getLog().info("Includes: " + Arrays.toString(getIncludes()));
            getLog().info("Excludes: " + Arrays.toString(getExcludes()));
            getLog().info("MinifiedSuffix: " + getMinifiedSuffix());
            getLog().info("CssConcatFile: " + getCssConcatFile());
            getLog().info("JsConcatFile: " + getJsConcatFile());
        }
    }
}
