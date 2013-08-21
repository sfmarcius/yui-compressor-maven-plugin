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

import br.msf.commons.util.IOUtils;
import java.io.File;
import org.apache.commons.lang.ArrayUtils;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.codehaus.plexus.util.DirectoryScanner;

/**
 * Goal which compresses web source files.
 *
 * @goal compressResources
 */
public class CompressorMojo extends AbstractMojo {

    /**
     * Trim includes used WHEN ITS NOT defined on the pom.
     */
    private static final String[] DEFAULT_MINIFY_INCLUDES = new String[]{
        "**/*.js", "**/*.json", "**/*.css",
        "**/*.htm", "**/*.html", "**/*.xhtm", "**/*.xhtml", "**/*.php",
        "**/*.jsp", "**/*.jspf", "**/*.jsf", "**/*.tld", "**/*.tag",
        "**/*.xml"
    };
    /**
     * Excludes ALWAYS added to the ones defined on the pom.
     * Contains patterns for files like ["/.metadata/*", "/.svn/*", "/.git/*", "/CVS/*", etc...]
     * <p/>
     * @see DirectoryScanner#DEFAULTEXCLUDES
     */
    private static final String[] DEFAULT_EXCLUDES = (String[]) ArrayUtils.addAll(DirectoryScanner.DEFAULTEXCLUDES, new String[]{"**/*-min-*.*", "**/*-min.*", "**/*.min.*"} );
    /**
     * Encoding to be used while reading/writting file contents.
     * <p/>
     * Default value is the configured ${project.build.sourceEncoding}.
     *
     * @parameter expression="${compressResources.encoding}" default-value="${project.build.sourceEncoding}"
     * @required
     */
    private String encoding;
    /**
     * Location for the input resource files.
     *
     * @parameter expression="${compressResources.sourceDirectory}" default-value="${project.basedir}/src/main/webapp"
     * @required
     */
    private File sourceDirectory;
    /**
     * Location for the output minified resource files.
     *
     * @parameter expression="${compressResources.outputDirectory}" default-value="${project.build.directory}/${project.build.finalName}"
     * @required
     */
    private File outputDirectory;
    /**
     * Files to be included on the task.
     * <p/>
     * If not defined, will be assumed {@link #DEFAULT_MINIFY_INCLUDES default includes}.
     *
     * @parameter expression="${compressResources.includes}"
     */
    private String[] includes;
    /**
     * Files to be excluded of the task.
     * <p/>
     * The given patterns are ALWAYS joined with the {@link #DEFAULT_EXCLUDES default ones}.
     *
     * @parameter expression="${compressResources.excludes}"
     */
    private String[] excludes;
    /**
     * Defines the suffix that is appended to the filename, just before the extension.
     *
     * @parameter expression="${compressResources.minifiedSuffix}" default-value="-min"
     * @required
     */
    private String minifiedSuffix;
    /**
     * Defines the name of the target file that will contain all javascript files concated.
     * Null or blank means no css concat at all.
     *
     * @parameter expression="${compressResources.jsConcatFile}"
     */
    private File jsConcatFile;
    /**
     * Defines the name of the target file that will contain all css files concated.
     * Null or blank means no css concat at all.
     *
     * @parameter expression="${compressResources.cssConcatFile}"
     */
    private File cssConcatFile;
    /**
     * Defines if it is to just concat all sources in one single file, without minifying.
     *
     * @parameter expression="${compressResources.writeIndividualFiles}" default-value="false"
     * @required
     */
    private boolean writeIndividualFiles;
    /**
     * Defines if it is to just concat all sources in one single file, without minifying.
     *
     * @parameter expression="${compressResources.compress}" default-value="true"
     * @required
     */
    private boolean compress;
    /**
     * Defines if it must show debugging logs.
     *
     * @parameter expression="${compressResources.verbose}" default-value="false"
     * @required
     */
    private boolean verbose;
    /**
     * Defines if it must show warnings of the js minify process.
     *
     * @parameter expression="${compressResources.showJsWarnings}" default-value="false"
     * @required
     */
    private boolean showJsWarnings;

    public void execute() throws MojoExecutionException {
        try {
            if (IOUtils.isDirectory(sourceDirectory)) {
                IOUtils.makeDirs(outputDirectory);
                (new CompressorService(packSettings())).doTheTrick();
            }
        } catch (Exception ex) {
            throw new MojoExecutionException("Error while attempting to minify scripts...", ex);
        }
    }

    private CompressorSettings packSettings() {
        CompressorSettings settings = new CompressorSettings();
        settings.setEncoding(IOUtils.getCharset(encoding));
        settings.setSourceDirectory(this.sourceDirectory);
        settings.setOutputDirectory(this.outputDirectory);
        settings.setIncludes((ArrayUtils.isEmpty(includes)) ? DEFAULT_MINIFY_INCLUDES : includes);
        settings.setExcludes((ArrayUtils.isEmpty(excludes)) ? DEFAULT_EXCLUDES : (String[]) ArrayUtils.addAll(DEFAULT_EXCLUDES, excludes));
        settings.setMinifiedSuffix(minifiedSuffix);
        settings.setJsConcatFile(jsConcatFile);
        settings.setCssConcatFile(cssConcatFile);
        settings.setWriteIndividualFiles(writeIndividualFiles);
        settings.setCompress(compress);
        settings.setShowJsWarnings(showJsWarnings);
        settings.setVerbose(verbose);
        settings.setLog(getLog());
        return settings;
    }
}
