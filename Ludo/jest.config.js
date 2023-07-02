module.exports = {
  preset: '@vue/cli-plugin-unit-jest/presets/no-babel',
  transform: {
    "^.+\\.jsx?$": "babel-jest",
    "^.+\\.js?$": "babel-jest"
  },
  transformIgnorePatterns: [
    "/node_modules/(?!(@babel/runtime-corejs3|@jest/transform)/)"
  ],
  moduleNameMapper: {
    "^@/router$": "<rootDir>/mocks/vue-router.js"
  }
}
