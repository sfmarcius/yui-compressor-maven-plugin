/*
 * Copyright (c) 2013 CPD-UFSM. All rights reserved.
 */
package br.msf.maven.compressor.processor;

import org.apache.maven.plugin.logging.Log;
import org.mozilla.javascript.ErrorReporter;
import org.mozilla.javascript.EvaluatorException;

/**
 *
 * @author Marcius da Silva da Fonseca (mfonseca@ufsm.br)
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
