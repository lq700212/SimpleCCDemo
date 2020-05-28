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
        String KEY_USER_ID = "key_user_id";
        String KEY_OBSERVER_COMPONENT_NAME = "key_observer_component_name";
        String KEY_OBSERVER_ACTION_NAME = "key_observer_action_name";

        interface Action {
            String FORCEGETLOGINUSER = "Component_Login_ForceGetLoginUser";
            String OPENLOGINACTIVITY = "Component_Login_OpenLoginActivity";
            String ACTION_ADD_LOGIN_OBSERVER = "action_add_login_observer";
            String ACTION_DEL_LOGIN_OBSERVER = "action_del_login_observer";
        }
    }

    public interface Component_order {
        String NAME = "Component_order";

        interface Action {
            String OPENORDERACTIVITY = "Component_Order_OpenOrderActivity";
        }
    }

    public interface Component_view {
        String NAME = "Component_view";

        interface Action {
            String OPENVIEWACTIVITY = "Component_View_OpenViewActivity";
            String OPENVIEWACTIVITYANDWAITFRAGMENTCREATE = "Component_View_OpenViewActivity_And_WaitFragmentCreate";
            String CHANGEFRAGMENTTEXT = "Component_View_ChangeFragmentText";
            String CHANGEFRAGMENTCOLOR = "Component_View_ChangeFragmentColor";
            String GETFRAGMENT = "Component_View_GetFragment";
        }
    }

    public interface Dynamic_component {
        String NAME = "Dynamic_component";

        interface Action {
            String OPEN_ACTIVITY = "open_activity";
        }
    }
}
