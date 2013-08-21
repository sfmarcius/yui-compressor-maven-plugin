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

import org.apache.maven.plugin.logging.Log;
import org.mozilla.javascript.ErrorReporter;
import org.mozilla.javascript.EvaluatorException;

/**
 *
 * @author Marcius da Silva da Fonseca (sf.marcius@gmail.com)
 * @version 1.0
 */
public class MavenErrorReporter implements ErrorReporter {

    private final boolean logWarnings;
    private final Log logger;

    public MavenErrorReporter(final Log log, final boolean logWarnings) {
        this.logWarnings = logWarnings;
        this.logger = log;
    }

    public void warning(final String message, final String sourceName, final int line, final String lineSource, final int lineOffset) {
        if (logWarnings) {
            logger.warn(format(message, line, lineOffset));
        }
    }

    public void error(final String message, final String sourceName, final int line, final String lineSource, final int lineOffset) {
        logger.error(format(message, line, lineOffset));
    }

    public EvaluatorException runtimeError(final String message, final String sourceName, final int line, final String lineSource, final int lineOffset) {
        error(message, sourceName, line, lineSource, lineOffset);
        return new EvaluatorException(message);
    }

    private String format(final String message, final int line, final int lineOffset) {
        if (line < 0) {
            return message;
        }
        return String.format("(%d:%d): %s", line, lineOffset, message);
    }
}
