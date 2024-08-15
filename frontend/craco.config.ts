import * as path from "node:path";

module.exports = {
    style: {
        sass: {
            loaderOptions: {
                additionalData: `@import "@src/assets/styles/common/_variables.scss"; @import "@src/assets/styles/common/mixins.scss";`,
            }
        }
    },
    webpack: {
        alias: {
            '@src': path.resolve(__dirname, './src'),
            '@component': path.resolve(__dirname, './src/component'),
            '@page': path.resolve(__dirname, './src/page'),
            '@style': path.resolve(__dirname, './src/assets/style'),
            '@image': path.resolve(__dirname, './src/assets/image')
        }
    }
};

