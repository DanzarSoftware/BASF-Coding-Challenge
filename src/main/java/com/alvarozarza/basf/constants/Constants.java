package com.alvarozarza.basf.constants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Constants {
    public static final List<String> notValidValues = Collections.unmodifiableList(
            new ArrayList<String>() {{
                add("plate");
                add("plates");
                add("trim");
                add("vane");
                add("vanes");
                add("U-shaped");
                add("ID");
                add("AC");
                add("DC");
                add("steam");
            }});
}
