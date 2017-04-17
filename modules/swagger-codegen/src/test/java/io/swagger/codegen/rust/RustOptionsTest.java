package io.swagger.codegen.rust;

import io.swagger.codegen.AbstractOptionsTest;
import io.swagger.codegen.CodegenConfig;
import io.swagger.codegen.languages.RustCodegen;
import io.swagger.codegen.options.RustOptionsProvider;
import mockit.Expectations;
import mockit.Tested;

public class RustOptionsTest extends AbstractOptionsTest {

    @Tested
    private RustCodegen clientCodegen;

    public RustOptionsTest() {
        super(new RustOptionsProvider());
    }

    @Override
    protected CodegenConfig getCodegenConfig() {
        return clientCodegen;
    }

    @SuppressWarnings("unused")
    @Override
    protected void setExpectations() {
        new Expectations(clientCodegen) {{
            clientCodegen.setSortParamsByRequiredFlag(Boolean.valueOf(RustOptionsProvider.SORT_PARAMS_VALUE));
            times = 1;
            clientCodegen.setProjectName(RustOptionsProvider.PROJECT_NAME_VALUE);
            times = 1;
            clientCodegen.setResponseAs(RustOptionsProvider.RESPONSE_AS_VALUE.split(","));
            times = 1;
            clientCodegen.setUnwrapRequired(Boolean.valueOf(RustOptionsProvider.UNWRAP_REQUIRED_VALUE));
            times = 1;
        }};
    }
}
