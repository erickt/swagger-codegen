package io.swagger.codegen.rust;

import io.swagger.codegen.CodegenOperation;
import io.swagger.codegen.DefaultCodegen;
import io.swagger.codegen.languages.RustCodegen;
import io.swagger.models.Operation;
import io.swagger.models.Swagger;
import io.swagger.parser.SwaggerParser;
import org.testng.Assert;
import org.testng.annotations.Test;

public class RustCodegenTest {

    RustCodegen rustCodegen = new RustCodegen();

    @Test
    public void testCapitalizedReservedWord() throws Exception {
        Assert.assertEquals(rustCodegen.toEnumVarName("AS", null), "_as");
    }

    @Test
    public void testReservedWord() throws Exception {
        Assert.assertEquals(rustCodegen.toEnumVarName("Public", null), "_public");
    }

    @Test
    public void shouldNotBreakNonReservedWord() throws Exception {
        Assert.assertEquals(rustCodegen.toEnumVarName("Error", null), "error");
    }

    @Test
    public void shouldNotBreakCorrectName() throws Exception {
        Assert.assertEquals(rustCodegen.toEnumVarName("EntryName", null), "entryName");
    }

    @Test
    public void testSingleWordAllCaps() throws Exception {
        Assert.assertEquals(rustCodegen.toEnumVarName("VALUE", null), "value");
    }

    @Test
    public void testSingleWordLowercase() throws Exception {
        Assert.assertEquals(rustCodegen.toEnumVarName("value", null), "value");
    }

    @Test
    public void testCapitalsWithUnderscore() throws Exception {
        Assert.assertEquals(rustCodegen.toEnumVarName("ENTRY_NAME", null), "entryName");
    }

    @Test
    public void testCapitalsWithDash() throws Exception {
        Assert.assertEquals(rustCodegen.toEnumVarName("ENTRY-NAME", null), "entryName");
    }

    @Test
    public void testCapitalsWithSpace() throws Exception {
        Assert.assertEquals(rustCodegen.toEnumVarName("ENTRY NAME", null), "entryName");
    }

    @Test
    public void testLowercaseWithUnderscore() throws Exception {
        Assert.assertEquals(rustCodegen.toEnumVarName("entry_name", null), "entryName");
    }

    @Test
    public void testStartingWithNumber() throws Exception {
        Assert.assertEquals(rustCodegen.toEnumVarName("123EntryName", null), "_123entryName");
        Assert.assertEquals(rustCodegen.toEnumVarName("123Entry_name", null), "_123entryName");
        Assert.assertEquals(rustCodegen.toEnumVarName("123EntryName123", null), "_123entryName123");
    }

    @Test(description = "returns NSData when response format is binary")
    public void binaryDataTest() {
        final Swagger model = new SwaggerParser().read("src/test/resources/2_0/binaryDataTest.json");
        final DefaultCodegen codegen = new RustCodegen();
        final String path = "/tests/binaryResponse";
        final Operation p = model.getPaths().get(path).getPost();
        final CodegenOperation op = codegen.fromOperation(path, "post", p, model.getDefinitions());

        Assert.assertEquals(op.returnType, "Data");
        Assert.assertEquals(op.bodyParam.dataType, "Data");
        Assert.assertTrue(op.bodyParam.isBinary);
        Assert.assertTrue(op.responses.get(0).isBinary);
    }

    @Test
    public void testDefaultPodAuthors() throws Exception {
        // Given

        // When
        rustCodegen.processOpts();

        // Then
        final String podAuthors = (String) rustCodegen.additionalProperties().get(RustCodegen.POD_AUTHORS);
        Assert.assertEquals(podAuthors, RustCodegen.DEFAULT_POD_AUTHORS);
    }

    @Test
    public void testPodAuthors() throws Exception {
        // Given
        final String swaggerDevs = "Swagger Devs";
        rustCodegen.additionalProperties().put(RustCodegen.POD_AUTHORS, swaggerDevs);

        // When
        rustCodegen.processOpts();

        // Then
        final String podAuthors = (String) rustCodegen.additionalProperties().get(RustCodegen.POD_AUTHORS);
        Assert.assertEquals(podAuthors, swaggerDevs);
    }

}