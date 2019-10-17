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

    FunctionSystemBuilder implementLowerZeroModule() {
        return implementLowerZeroModule(false);
    }

    FunctionSystemBuilder implementLowerZeroModuleWithTrigStub() {
        return implementLowerZeroModule(true);
    }

    FunctionSystemBuilder implementMoreZeroModule() {
        return implementMoreZeroModule(false);
    }

    FunctionSystemBuilder implementMoreZeroModuleWithLogStub() {
        return implementMoreZeroModule(true);
    }


    FunctionSystem build() {
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
