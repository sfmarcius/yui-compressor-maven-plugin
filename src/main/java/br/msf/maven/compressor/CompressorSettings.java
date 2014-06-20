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
package br.msf.maven.compressor;

import java.io.File;
import java.nio.charset.Charset;
import java.util.Arrays;
import org.apache.commons.lang3.StringUtils;
import org.apache.maven.plugin.logging.Log;

/**
 * Class that acts like a FileProcessor Factory
 * <p/>
 * @author Marcius da Silva da Fonseca (sf.marcius@gmail.com)
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
        return StringUtils.isNotBlank(getMinifiedSuffix());
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
