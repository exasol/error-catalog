package com.exasol.errorcodecatalog.loader;

import com.exsol.errorcodemodel.ErrorCodeReport;

/**
 * This recorde represents a loaded error-code report.
 */
public final class LoadedReport {
    private final ErrorCodeReport report;

    /**
     * Create a new instance of {@link LoadedReport}.
     * 
     * @param report report
     */
    public LoadedReport(final ErrorCodeReport report) {
        this.report = report;
    }

    public ErrorCodeReport report() {
        return this.report;
    }

    @Override
    public boolean equals(final java.lang.Object obj) {
        if (obj == this)
            return true;
        if (obj == null || obj.getClass() != this.getClass())
            return false;
        final var that = (LoadedReport) obj;
        return java.util.Objects.equals(this.report, that.report);
    }

    @java.lang.Override
    public int hashCode() {
        return java.util.Objects.hash(this.report);
    }

    @java.lang.Override
    public String toString() {
        return "LoadedReport[" + "report=" + this.report + ']';
    }
}
