package ru.system;

public class FunctionSystemBuilder {
    private boolean stubLower = true;
    private boolean stubMore = true;

    private boolean stubTrig = false;
    private boolean stubLog = false;

    private FunctionSystemBuilder implementLowerZeroModule(boolean stubTrig) {
        this.stubLower = false;
        this.stubTrig = stubTrig;
        return this;
    }

    private FunctionSystemBuilder implementMoreZeroModule(boolean stubLog) {
        this.stubMore = false;
        this.stubLog = stubLog;
        return this;
    }

    public FunctionSystemBuilder implementLowerZeroModule() {
        return implementLowerZeroModule(false);
    }

    public FunctionSystemBuilder implementLowerZeroModuleWithTrigStub() {
        return implementLowerZeroModule(true);
    }

    public FunctionSystemBuilder implementMoreZeroModule() {
        return implementMoreZeroModule(false);
    }

    public FunctionSystemBuilder implementMoreZeroModuleWithLogStub() {
        return implementMoreZeroModule(true);
    }

    public FunctionSystem build() {
        XLowerZeroFunction xLowerZeroFunction;
        if (stubLower) {
            xLowerZeroFunction = ModulesFactory.createXLowerZeroFunctionModuleStub();
        } else {
            xLowerZeroFunction = ModulesFactory.createXLowerZeroFunctionModule(stubTrig);
        }
        XMoreZeroFunction xMoreZeroFunction;
        if (stubMore) {
            xMoreZeroFunction = ModulesFactory.createXMoreZeroFunctionModuleStub();
        } else {
            xMoreZeroFunction = ModulesFactory.createXMoreZeroFunctionModule(stubLog);
        }
        return new FunctionSystem(xLowerZeroFunction, xMoreZeroFunction);
    }
}
