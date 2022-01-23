/* eslint-disable @typescript-eslint/no-var-requires */
const tsConfig = require('./tsconfig.prod.json');
const tsConfigPaths = require('tsconfig-paths');

const { paths } = tsConfig.compilerOptions;
tsConfigPaths.register({ baseUrl: './', paths });
