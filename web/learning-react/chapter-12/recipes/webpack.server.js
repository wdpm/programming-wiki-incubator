const path = require("path");
const nodeExternals = require("webpack-node-externals");

module.exports = {
  entry: "./server/index.js",

  target: "node",

  // nodeExternals will scan the node_modules folder for all node_modules names. Then, it will build an
  // external function that tells webpack not to bundle those modules or any submodules
  externals: [nodeExternals()],

  output: {
    path: path.resolve("server-build"),
    filename: "index.js"
  },

  module: {
    rules: [
      {
        test: /\.js$/,
        use: "babel-loader"
      }
    ]
  }
};
