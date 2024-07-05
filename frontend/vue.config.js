const { defineConfig } = require('@vue/cli-service');
const path = require('path');

module.exports = defineConfig({
  transpileDependencies: true,
  outputDir: path.resolve(__dirname, '../src/main/resources/static'),
  devServer: {
    proxy: {
      '/api': {
        target: 'http://localhost:8080',
        changeOrigin: true
      }
    }
  },
  css: {
    loaderOptions: {
      sass: {
        // You can inject shared variables, mixins, functions globally here
        additionalData: `@import "@/assets/styles/_variables.scss";`
      }
    }
  }
});