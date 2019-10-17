package ru.system;

public class FunctionSystemBuilder {
    private boolean stubLower = true;
    private boolean stubMore = true;

    private boolean stubSinus = false;
    private boolean stubLog = false;
    private boolean stubOtherTrig = false;

    private FunctionSystemBuilder implementLowerZeroModule(boolean stubSinus, boolean stubOtherTrig) {
        this.stubOtherTrig = stubOtherTrig;
        this.stubLower = false;
        this.stubSinus = stubSinus;
        return this;
    }

    private FunctionSystemBuilder implementMoreZeroModule(boolean stubLog) {
        this.stubMore = false;
        this.stubLog = stubLog;
        return this;
    }

    public FunctionSystemBuilder implementLowerZeroModule() {
        return implementLowerZeroModule(false, false);
    }

    public FunctionSystemBuilder implementLowerZeroModuleWithTrigSinusStub() {
        return implementLowerZeroModule(true, false);
    }

    public FunctionSystemBuilder implementLowerZeroModuleWithFullTrigStub() {
        return implementLowerZeroModule(true, true);
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
            xLowerZeroFunction = ModulesFactory.createXLowerZeroFunctionModule(stubSinus, stubOtherTrig);
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
