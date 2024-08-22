import * as path from "node:path";

module.exports = {
    style: {
        sass: {
            loaderOptions: {
                additionalData: `@import "@styles/common/_variables.scss"; @import "@styles/common/mixins.scss";`,
            }
        }
    },
    webpack: {
        alias: {
            '@': path.resolve(__dirname, './src'),
            '@assets': path.resolve(__dirname, './src/assets'),
            '@component': path.resolve(__dirname, './src/component'),
            '@page': path.resolve(__dirname, './src/page'),
            '@styles': path.resolve(__dirname, './src/assets/styles'),
            '@image': path.resolve(__dirname, './src/assets/image')
        }
    }
};

