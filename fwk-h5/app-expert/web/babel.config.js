module.exports = {
  "presets": [
    [
      "@vue/babel-preset-app",
      {
        "modules": false,
        "useBuiltIns": "entry"
      },
      "stage-2"
    ]
  ],
  "plugins": [
    "@babel/transform-runtime"
  ],
  "env": {
    "test": {
      "presets": ["env", "stage-2"],
      "plugins": ["istanbul"]
    }
  }
}