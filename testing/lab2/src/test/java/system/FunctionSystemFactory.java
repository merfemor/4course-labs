package system;

public class FunctionSystemFactory {
    private boolean stubLower = true;
    private boolean stubMore = true;

    private boolean stubTrig = false;
    private boolean stubLog = false;

    private FunctionSystemFactory implementLowerZeroModule(boolean stubTrig) {
        this.stubLower = false;
        this.stubTrig = stubTrig;
        return this;
    }

    private FunctionSystemFactory implementMoreZeroModule(boolean stubLog) {
        this.stubMore = false;
        this.stubLog = stubLog;
        return this;
    }

    FunctionSystemFactory implementLowerZeroModule() {
        return implementLowerZeroModule(false);
    }

    FunctionSystemFactory implementLowerZeroModuleWithTrigStub() {
        return implementLowerZeroModule(true);
    }

    FunctionSystemFactory implementMoreZeroModule() {
        return implementMoreZeroModule(false);
    }

    FunctionSystemFactory implementMoreZeroModuleWithLogStub() {
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
