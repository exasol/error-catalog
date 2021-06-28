package com.exasol.errorcodecatalog.renderer;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.exasol.errorcodecatalog.loader.LoadedReport;
import com.exsol.errorcodemodel.ErrorCodeReport;
import com.exsol.errorcodemodel.ErrorMessageDeclaration;

class ErrorCatalogRendererTest {

    @Test
    void test() {
        final ErrorMessageDeclaration errorMessageDeclaration = ErrorMessageDeclaration.builder().identifier("E-TEST-1")
                .prependMessage(
                        "Two error codes cover the same package: {{package}} was declared for {{first}} and {{second}}.")
                .build();
        final ErrorCodeReport report = new ErrorCodeReport("my-project", "1.2.3", List.of(errorMessageDeclaration));
        final LoadedReport loadedReport = new LoadedReport(report);
        new ErrorCatalogRenderer().render(List.of(loadedReport));
    }
}