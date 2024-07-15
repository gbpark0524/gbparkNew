const { defineConfig } = require('@vue/cli-service');
const path = require('path');

module.exports = defineConfig({
  transpileDependencies: true,
  // outputDir: path.resolve(__dirname, '../src/main/resources/static'),
  configureWebpack: {
    devtool: 'source-map'
  },
  outputDir: path.resolve(__dirname, 'dist'),
  devServer: {
    proxy: {
      '/api': {
        target: 'http://localhost:8080',
        changeOrigin: true
      }
    },
    port: 8090
  },
  css: {
    loaderOptions: {
      sass: {
        additionalData: `@import "@/assets/styles/_variables.scss";`
      }
    }
  }
});