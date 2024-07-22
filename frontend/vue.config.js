const { defineConfig } = require('@vue/cli-service');
const path = require('path');

module.exports = defineConfig({
  configureWebpack: {
    devtool: 'source-map'
  },
  css: {
    loaderOptions: {
      sass: {
        additionalData: `@import "@/assets/styles/_variables.scss";`
      }
    }
  },
  devServer: {
    historyApiFallback: true,
    port: 9091,
    proxy: {
      '/*': {
        target: 'http://localhost:9090',
        changeOrigin: true,
        ws: false,
      }
    },
  },
  outputDir: path.resolve(__dirname, 'dist'),
  // outputDir: path.resolve(__dirname, '../src/main/resources/static'),
  transpileDependencies: true,
});