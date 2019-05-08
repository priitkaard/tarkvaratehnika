module.exports = {
    root: true,
    env: {
        node: true
    },
    extends: ["plugin:vue/essential"],
    rules: {
        "no-console": process.env.NODE_ENV === "production" ? "error" : "off",
        "no-debugger": process.env.NODE_ENV === "production" ? "error" : "off",
        "semi": ["error", "always"],
        "quotes": ["error", "single", "avoid-escape"],
        "linebreak-style": ["error", "unix"],
        "comma-dangle": ["error", "always-multiline"],
        "comma-spacing": ["error", { "before": false, "after": true }],
        "key-spacing": ["error", { "afterColon": true }],
        "vue/script-indent": ["error", 4, { "baseIndent": 1 }],
        'max-len': ["error", { "code": 120 }]
    },
    parserOptions: {
        parser: "babel-eslint"
    }
};
