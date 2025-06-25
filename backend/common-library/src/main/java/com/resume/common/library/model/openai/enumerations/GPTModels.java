package com.resume.common.library.model.openai.enumerations;

public enum GPTModels {
    // GPT-4o Series
    GPT_4O("gpt-4o"),
    GPT_4O_VERSION_2024_11_20("gpt-4o-2024-11-20"),
    GPT_4O_VERSION_2024_08_06("gpt-4o-2024-08-06"),
    GPT_4O_VERSION_2024_05_13("gpt-4o-2024-05-13"),
    GPT_4O_MINI("gpt-4o-mini"),
    GPT_4O_MINI_VERSION_2024_07_18("gpt-4o-mini-2024-07-18"),

    // o1 Series
    O1("o1"),
    O1_MINI("o1-mini"),
    O1_PRO_MODE("o1-pro-mode"),

    // o3 Series
    O3("o3"),
    O3_MINI("o3-mini"),
    O3_MINI_HIGH("o3-mini-high"),

    // GPT-4 Series
    GPT_4("gpt-4"),
    GPT_4_TURBO("gpt-4-turbo"),

    // GPT-3.5 Series
    GPT_3_5("gpt-3.5"),
    GPT_3_5_TURBO("gpt-3.5-turbo");

    private final String modelName;

    GPTModels(String modelName) {
        this.modelName = modelName;
    }

    public String getModelName() {
        return modelName;
    }
}
