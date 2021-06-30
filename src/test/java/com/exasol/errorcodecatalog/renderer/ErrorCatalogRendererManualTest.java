package com.exasol.errorcodecatalog.renderer;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.nio.file.Path;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.exsol.errorcodemodel.ErrorCodeReport;
import com.exsol.errorcodemodel.ErrorMessageDeclaration;

/**
 * This is manual test that writes an example catalog to /tmp/catalog/. Developers can use it to check the output.
 */
class ErrorCatalogRendererManualTest {
    private static final Path TARGET_DIR = Path.of("/tmp/catalog/");

    @Test
    void test() {
        final ErrorMessageDeclaration errorMessageDeclaration1 = ErrorMessageDeclaration.builder()
                .identifier("E-TEST-1")
                .prependMessage(
                        "Two error codes cover the same package: {{package}} was declared for {{first}} and {{second}}.")
                .appendMitigation("Rename one of the codes.")
                .addParameter("package", "The package name that bother error codes declare").addParameter("first", "")
                .addParameter("second", "").build();
        final ErrorMessageDeclaration errorMessageDeclaration2 = ErrorMessageDeclaration.builder()
                .identifier("F-TEST-2").prependMessage("Download of {{url}} failed.")
                .appendMitigation("Check your internet connection.").appendMitigation("Check your firewall settings.")
                .build();
        final ErrorCodeReport report = new ErrorCodeReport("my-project", "1.2.3", List.of(errorMessageDeclaration1));
        final ErrorCodeReport olderReport = new ErrorCodeReport("my-project", "1.2.0",
                List.of(errorMessageDeclaration1, errorMessageDeclaration2));
        assertDoesNotThrow(() -> new ErrorCatalogRenderer(TARGET_DIR).render(List.of(report, olderReport)));
    }
}