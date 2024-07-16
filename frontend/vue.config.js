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
      '/*': {
        target: 'http://localhost:9090',
        changeOrigin: true
      }
    },
    port: 9091
  },
  css: {
    loaderOptions: {
      sass: {
        additionalData: `@import "@/assets/styles/_variables.scss";`
      }
    }
  }
});