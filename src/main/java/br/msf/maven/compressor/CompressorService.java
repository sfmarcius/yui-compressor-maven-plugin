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

import br.msf.commons.text.EnhancedStringBuilder;
import br.msf.commons.text.IStringBuilder;
import br.msf.commons.util.CollectionUtils;
import br.msf.commons.util.IOUtils;
import br.msf.maven.compressor.processor.CssCompressor;
import br.msf.maven.compressor.processor.Compressor;
import br.msf.maven.compressor.processor.JavaScriptCompressor;
import br.msf.maven.utils.FileCopy;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import org.codehaus.plexus.util.DirectoryScanner;

/**
 * Class that acts like a FileProcessor Factory
 * <p/>
 * @author Marcius da Silva da Fonseca (sf.marcius@gmail.com)
 */
public class CompressorService {

    private final CompressorSettings settings;
    /**
     * Collection of specific Minifiers.
     */
    private List<Compressor> avaliableMinifiers = new ArrayList<Compressor>(3);

    public CompressorService(final CompressorSettings settings) {
        this.settings = settings;
        this.avaliableMinifiers.add(new JavaScriptCompressor());
        this.avaliableMinifiers.add(new CssCompressor());
        //this.avaliableMinifiers.add(new MarkupMinifier());
    }

    public void doTheTrick() throws Exception {
        final Collection<FileCopy> toMinify = getIncludedFiles();
        final Collection<FileCopy> minified = new ArrayList<FileCopy>(CollectionUtils.size(toMinify));
        info("Compressing " + toMinify.size() + " files...");
        this.settings.logSettings();
        IStringBuilder jsConcatContent = new EnhancedStringBuilder();
        IStringBuilder cssConcatContent = new EnhancedStringBuilder();
        for (FileCopy current : toMinify) {
            final CharSequence content;
            final Compressor minifier = getMinifierFor(current.getInput());
            if (minifier != null) {
                if (settings.isCompress()) {
                    content = minifier.minify(current, this.settings);
                } else {
                    content = current.readContents(this.settings.getEncoding());
                }
                if (minifier.getType().isCss() && this.settings.isCssConcat()) {
                    cssConcatContent.appendln(content);
                    if (this.settings.isWriteIndividualFiles()) {
                        writeContents(content, getMinifiedFile(current.getOutput()), settings.getEncoding());
                    }
                } else if (minifier.getType().isJavascrpt() && this.settings.isJsConcat()) {
                    jsConcatContent.appendln(content);
                    if (this.settings.isWriteIndividualFiles()) {
                        writeContents(content, getMinifiedFile(current.getOutput()), settings.getEncoding());
                    }
                } else {
                    writeContents(content, getMinifiedFile(current.getOutput()), settings.getEncoding());
                }
                minified.add(current);
            } else {
                warn("No compressor compatible for " + current.getInput().getName() + ". Ignoring file...");
            }
        }
        if (!cssConcatContent.isEmpty()) {
            writeContents(cssConcatContent, getMinifiedFile(this.settings.getCssConcatFile()), this.settings.getEncoding());
        }
        if (!jsConcatContent.isEmpty()) {
            writeContents(jsConcatContent, getMinifiedFile(this.settings.getJsConcatFile()), this.settings.getEncoding());
        }
        info("Minified files........: " + minified.size());
    }

    private Collection<FileCopy> getIncludedFiles() {
        final Collection<String> sourceFiles = scanForFiles(this.settings.getSourceDirectory());
        final Collection<FileCopy> repo = new ArrayList<FileCopy>(CollectionUtils.size(sourceFiles));
        for (String string : sourceFiles) {
            repo.add(new FileCopy(string, this.settings.getSourceDirectory(), this.settings.getOutputDirectory()));
        }
        return repo;
    }

    private Compressor getMinifierFor(final File file) {
        if (IOUtils.isFile(file)) {
            for (Compressor minifier : avaliableMinifiers) {
                if (minifier.accept(file)) {
                    return minifier;
                }
            }
        }
        return null;
    }

    private Collection<String> scanForFiles(final File inputDir) {
        if (IOUtils.notExists(inputDir)) {
            if (settings.isVerbose()) {
                settings.getLog().info("dir " + inputDir + " doesnt exists.");
            }
            return CollectionUtils.EMPTY_COLLECTION;
        }
        if (IOUtils.isNotDirectory(inputDir)) {
            throw new IllegalArgumentException("Invalid input dir.");
        }
        DirectoryScanner scanner = new DirectoryScanner();
        scanner.setCaseSensitive(false);
        scanner.setIncludes(this.settings.getIncludes());
        scanner.setExcludes(this.settings.getExcludes());
        scanner.setBasedir(inputDir);
        scanner.scan();
        return Arrays.asList(scanner.getIncludedFiles());
    }

    private void info(final CharSequence string) {
        this.settings.getLog().info(string);
    }

    private void warn(final CharSequence string) {
        this.settings.getLog().warn(string);
    }

    private static void writeContents(final CharSequence content, final File f, final Charset encoding) throws Exception {
        if (f == null) {
            return;
        }
        final byte[] bytes = content.toString().getBytes(encoding);
        OutputStream os = null;
        try {
            if (!f.exists()) {
                f.getParentFile().mkdirs(); // create path directories
                f.createNewFile(); // create empty file
            }
            os = new FileOutputStream(f);
            os.write(bytes);
            os.flush();
        } finally {
            IOUtils.closeQuietly(os);
        }
    }

    private File getMinifiedFile(final File originalFile) {
        if (!settings.isMinifiedSuffixed() || !settings.isCompress()) {
            return originalFile;
        }
        final File parentDir = originalFile.getParentFile();
        final int extensionIndex = originalFile.getName().lastIndexOf(".");
        final String extension = extensionIndex > 0 ? originalFile.getName().substring(extensionIndex) : "";
        final String fileName = extensionIndex > 0 ? originalFile.getName().substring(0, extensionIndex) : originalFile.getName();
        if (fileName.endsWith(settings.getMinifiedSuffix())) {
            return originalFile;
        } else {
            return new File(parentDir, fileName + settings.getMinifiedSuffix() + extension);
        }
    }
}
