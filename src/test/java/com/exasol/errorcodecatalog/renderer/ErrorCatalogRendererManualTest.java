package com.exasol.errorcodecatalog.renderer;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.nio.file.Path;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.exasol.errorcodecatalog.loader.LoadedReport;
import com.exsol.errorcodemodel.ErrorCodeReport;
import com.exsol.errorcodemodel.ErrorMessageDeclaration;

/**
 * This is manual test that writes an example catalog to /tmp/catalog/. Developers can use it to check the output.
 */
class ErrorCatalogRendererManualTest {
    private static final Path TARGET_DIR = Path.of("/tmp/catalog/");

    @Test
    void test() {
        final ErrorMessageDeclaration errorMessageDeclaration = ErrorMessageDeclaration.builder().identifier("E-TEST-1")
                .prependMessage(
                        "Two error codes cover the same package: {{package}} was declared for {{first}} and {{second}}.")
                .build();
        final ErrorCodeReport report = new ErrorCodeReport("my-project", "1.2.3", List.of(errorMessageDeclaration));
        final LoadedReport loadedReport = new LoadedReport(report);
        assertDoesNotThrow(() -> new ErrorCatalogRenderer(TARGET_DIR).render(List.of(loadedReport)));
    }
}