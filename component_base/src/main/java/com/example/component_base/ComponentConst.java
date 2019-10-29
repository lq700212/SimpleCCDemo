package com.example.component_base;

public class ComponentConst {
    public interface Component_A{
        String NAME = "Component_A";

        public interface Action{
            String SHOW = "Component_A_show";
            String HIDE = "Component_A_hide";
        }
    }

    public interface Component_B{
        String NAME = "Component_B";

        public interface Action{
            String SHOW = "Component_B_show";
            String HIDE = "Component_B_hide";
        }
    }

    public interface Component_C{
        String NAME = "Component_C";

        public interface Action{
            String SHOW = "Component_C_show";
            String HIDE = "Component_C_hide";
            String CONTENT = "setContent";
        }
    }
}
