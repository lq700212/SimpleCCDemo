package com.example.component_base;

public class ComponentConst {
    public interface Component_A {
        String NAME = "Component_A";

        interface Action {
            String GET = "Component_A_get";
            String HIDE = "Component_A_hide";
        }
    }

    public interface Component_B {
        String NAME = "Component_B";

        interface Action {
            String GET = "Component_B_get";
            String HIDE = "Component_B_hide";
        }
    }

    public interface Component_C {
        String NAME = "Component_C";

        interface Action {
            String GET = "Component_C_get";
            String HIDE = "Component_C_hide";
            String CONTENT = "setContent";
        }
    }

    public interface Component_login {
        String NAME = "Component_Login";

        interface Action {
            String FORCEGETLOGINUSER = "Component_Login_ForceGetLoginUser";
            String OPENLOGINACTIVITY = "Component_Login_OpenLoginActivity";
        }
    }

    public interface Component_order {
        String NAME = "Component_order";

        interface Action {
            String OPENORDERACTIVITY = "Component_Order_OpenOrderActivity";
        }
    }
}
